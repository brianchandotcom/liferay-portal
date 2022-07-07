/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.knowledge.base.web.internal.portlet;

import com.liferay.asset.kernel.exception.AssetCategoryException;
import com.liferay.asset.kernel.exception.AssetTagException;
import com.liferay.document.library.kernel.exception.FileNameException;
import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.document.library.kernel.exception.NoSuchFileException;
import com.liferay.knowledge.base.constants.KBArticleConstants;
import com.liferay.knowledge.base.constants.KBCommentConstants;
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.exception.KBArticleContentException;
import com.liferay.knowledge.base.exception.KBArticlePriorityException;
import com.liferay.knowledge.base.exception.KBArticleTitleException;
import com.liferay.knowledge.base.exception.KBCommentContentException;
import com.liferay.knowledge.base.exception.NoSuchArticleException;
import com.liferay.knowledge.base.exception.NoSuchCommentException;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBComment;
import com.liferay.knowledge.base.service.KBArticleService;
import com.liferay.knowledge.base.service.KBCommentLocalService;
import com.liferay.knowledge.base.service.KBCommentService;
import com.liferay.knowledge.base.service.KBFolderService;
import com.liferay.knowledge.base.service.KBTemplateService;
import com.liferay.knowledge.base.util.AdminHelper;
import com.liferay.knowledge.base.web.internal.constants.KBWebKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseKBPortlet extends MVCPortlet {

	public void deleteKBComments(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!themeDisplay.isSignedIn()) {
			return;
		}

		long[] deleteKBCommentIds = ParamUtil.getLongValues(
			actionRequest, "rowIdsKBComment");

		for (long deleteKBCommentId : deleteKBCommentIds) {
			kbCommentService.deleteKBComment(deleteKBCommentId);
		}

		SessionMessages.add(actionRequest, "suggestionsDeleted");
	}

	public void deleteTempAttachment(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			long resourcePrimKey = ParamUtil.getLong(
				actionRequest, "resourcePrimKey");
			String fileName = ParamUtil.getString(actionRequest, "fileName");

			kbArticleService.deleteTempAttachment(
				themeDisplay.getScopeGroupId(), resourcePrimKey, fileName,
				KBWebKeys.TEMP_FOLDER_NAME);

			writeJSON(
				actionRequest, actionResponse,
				JSONUtil.put("deleted", Boolean.TRUE));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			writeJSON(
				actionRequest, actionResponse,
				JSONUtil.put(
					"deleted", Boolean.FALSE
				).put(
					"errorMessage",
					themeDisplay.translate(
						"an-unexpected-error-occurred-while-deleting-the-file")
				));
		}
	}

	public void moveKBObject(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long resourceClassNameId = ParamUtil.getLong(
			actionRequest, "resourceClassNameId");
		long resourcePrimKey = ParamUtil.getLong(
			actionRequest, "resourcePrimKey");
		long parentResourcePrimKey = ParamUtil.getLong(
			actionRequest, "parentResourcePrimKey",
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		long kbArticleClassNameId = portal.getClassNameId(
			KBArticleConstants.getClassName());

		if (resourceClassNameId == kbArticleClassNameId) {
			long parentResourceClassNameId = ParamUtil.getLong(
				actionRequest, "parentResourceClassNameId",
				portal.getClassNameId(KBFolderConstants.getClassName()));
			double priority = ParamUtil.getDouble(actionRequest, "priority");

			kbArticleService.moveKBArticle(
				resourcePrimKey, parentResourceClassNameId,
				parentResourcePrimKey, priority);
		}
		else {
			kbFolderService.moveKBFolder(
				resourcePrimKey, parentResourcePrimKey);
		}
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		String cmd = ParamUtil.getString(renderRequest, Constants.CMD);

		if (Validator.isNotNull(cmd) && cmd.equals("compareVersions")) {
			_compareVersions(renderRequest);
		}

		doRender(renderRequest, renderResponse);

		super.render(renderRequest, renderResponse);
	}

	public void serveKBArticleRSS(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		PortletPreferences portletPreferences =
			resourceRequest.getPreferences();

		boolean enableRss = GetterUtil.getBoolean(
			portletPreferences.getValue("enableRss", null), true);

		if (!portal.isRSSFeedsEnabled() || !enableRss) {
			portal.sendRSSFeedsDisabledError(resourceRequest, resourceResponse);

			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long resourcePrimKey = ParamUtil.getLong(
			resourceRequest, "resourcePrimKey");

		int rssDelta = ParamUtil.getInteger(resourceRequest, "rssDelta");
		String rssDisplayStyle = ParamUtil.getString(
			resourceRequest, "rssDisplayStyle");
		String rssFormat = ParamUtil.getString(resourceRequest, "rssFormat");

		String rss = kbArticleService.getKBArticleRSS(
			resourcePrimKey, WorkflowConstants.STATUS_APPROVED, rssDelta,
			rssDisplayStyle, rssFormat, themeDisplay);

		PortletResponseUtil.sendFile(
			resourceRequest, resourceResponse, null,
			rss.getBytes(StringPool.UTF8), ContentTypes.TEXT_XML_UTF8);
	}

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		try {
			String resourceID = resourceRequest.getResourceID();

			if (resourceID.equals("compareVersions")) {
				try {
					long resourcePrimKey = ParamUtil.getLong(
						resourceRequest, "resourcePrimKey");
					double sourceVersion = ParamUtil.getDouble(
						resourceRequest, "filterSourceVersion");
					double targetVersion = ParamUtil.getDouble(
						resourceRequest, "filterTargetVersion");

					String diffHtmlResults = adminHelper.getKBArticleDiff(
						resourcePrimKey, GetterUtil.getInteger(sourceVersion),
						GetterUtil.getInteger(targetVersion), "content");

					resourceRequest.setAttribute(
						WebKeys.DIFF_HTML_RESULTS, diffHtmlResults);

					PortletContext portletContext =
						resourceRequest.getPortletContext();

					PortletRequestDispatcher portletRequestDispatcher =
						portletContext.getRequestDispatcher(
							"/admin/common/compare_versions_diff_html.jsp");

					portletRequestDispatcher.include(
						resourceRequest, resourceResponse);
				}
				catch (Exception exception) {
					PortalUtil.sendError(
						exception,
						PortalUtil.getHttpServletRequest(resourceRequest),
						PortalUtil.getHttpServletResponse(resourceResponse));
				}
			}
			else if (resourceID.equals("kbArticleRSS")) {
				serveKBArticleRSS(resourceRequest, resourceResponse);
			}
		}
		catch (IOException | PortletException exception) {
			throw exception;
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}
	}

	public void subscribeKBArticle(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long resourcePrimKey = ParamUtil.getLong(
			actionRequest, "resourcePrimKey");

		kbArticleService.subscribeKBArticle(
			themeDisplay.getScopeGroupId(), resourcePrimKey);
	}

	public void unsubscribeKBArticle(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long resourcePrimKey = ParamUtil.getLong(
			actionRequest, "resourcePrimKey");

		kbArticleService.unsubscribeKBArticle(resourcePrimKey);
	}

	public void updateKBArticle(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		long resourcePrimKey = ParamUtil.getLong(
			actionRequest, "resourcePrimKey");

		String title = ParamUtil.getString(actionRequest, "title");
		String content = ParamUtil.getString(actionRequest, "content");
		String description = ParamUtil.getString(actionRequest, "description");
		String sourceURL = ParamUtil.getString(actionRequest, "sourceURL");
		String[] sections = actionRequest.getParameterValues("sections");
		String[] selectedFileNames = ParamUtil.getParameterValues(
			actionRequest, "selectedFileName");

		KBArticle kbArticle = null;

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			KBArticle.class.getName(), actionRequest);

		if (cmd.equals(Constants.ADD)) {
			long parentResourceClassNameId = ParamUtil.getLong(
				actionRequest, "parentResourceClassNameId",
				portal.getClassNameId(KBFolderConstants.getClassName()));
			long parentResourcePrimKey = ParamUtil.getLong(
				actionRequest, "parentResourcePrimKey",
				KBFolderConstants.DEFAULT_PARENT_FOLDER_ID);
			String urlTitle = ParamUtil.getString(actionRequest, "urlTitle");

			kbArticle = kbArticleService.addKBArticle(
				null, portal.getPortletId(actionRequest),
				parentResourceClassNameId, parentResourcePrimKey, title,
				urlTitle, content, description, sourceURL, sections,
				selectedFileNames, serviceContext);
		}
		else if (cmd.equals(Constants.REVERT)) {
			int version = ParamUtil.getInteger(
				actionRequest, "version", KBArticleConstants.DEFAULT_VERSION);

			kbArticle = kbArticleService.revertKBArticle(
				resourcePrimKey, version, serviceContext);
		}
		else if (cmd.equals(Constants.UPDATE)) {
			long[] removeFileEntryIds = ParamUtil.getLongValues(
				actionRequest, "removeFileEntryIds");

			kbArticle = kbArticleService.updateKBArticle(
				resourcePrimKey, title, content, description, sourceURL,
				sections, selectedFileNames, removeFileEntryIds,
				serviceContext);
		}

		if (!cmd.equals(Constants.ADD) && !cmd.equals(Constants.UPDATE)) {
			return;
		}

		int workflowAction = ParamUtil.getInteger(
			actionRequest, "workflowAction");

		if (workflowAction == WorkflowConstants.ACTION_SAVE_DRAFT) {
			String editURL = buildEditURL(
				actionRequest, actionResponse, kbArticle);

			actionRequest.setAttribute(WebKeys.REDIRECT, editURL);
		}
		else {
			String redirect = PortalUtil.escapeRedirect(
				ParamUtil.getString(actionRequest, "redirect"));

			if (cmd.equals(Constants.ADD) && Validator.isNotNull(redirect)) {
				actionRequest.setAttribute(
					WebKeys.REDIRECT,
					_getContentRedirect(
						KBArticle.class, kbArticle.getResourcePrimKey(),
						redirect));
			}
		}
	}

	public void updateKBComment(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!themeDisplay.isSignedIn()) {
			return;
		}

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		long classNameId = ParamUtil.getLong(actionRequest, "classNameId");
		long classPK = ParamUtil.getLong(actionRequest, "classPK");
		String content = ParamUtil.getString(actionRequest, "content");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			KBComment.class.getName(), actionRequest);

		if (cmd.equals(Constants.ADD)) {
			kbCommentLocalService.addKBComment(
				themeDisplay.getUserId(), classNameId, classPK, content,
				serviceContext);
		}
		else if (cmd.equals(Constants.UPDATE)) {
			long kbCommentId = ParamUtil.getLong(actionRequest, "kbCommentId");

			int status = ParamUtil.getInteger(
				actionRequest, "status", KBCommentConstants.STATUS_ANY);

			if (status == KBCommentConstants.STATUS_ANY) {
				KBComment kbComment = kbCommentService.getKBComment(
					kbCommentId);

				status = kbComment.getStatus();
			}

			kbCommentLocalService.updateKBComment(
				kbCommentId, classNameId, classPK, content, status,
				serviceContext);
		}

		SessionMessages.add(actionRequest, "suggestionSaved");
	}

	public void updateKBCommentStatus(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortalException {

		long kbCommentId = ParamUtil.getLong(actionRequest, "kbCommentId");

		int status = ParamUtil.getInteger(actionRequest, "kbCommentStatus");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			KBComment.class.getName(), actionRequest);

		kbCommentService.updateStatus(kbCommentId, status, serviceContext);

		SessionMessages.add(actionRequest, "suggestionStatusUpdated");
	}

	protected String buildEditURL(
			ActionRequest actionRequest, ActionResponse actionResponse,
			KBArticle kbArticle)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		String editURL = portal.getLayoutFullURL(themeDisplay);

		editURL = HttpComponentsUtil.setParameter(
			editURL, "p_p_id", portletDisplay.getId());
		editURL = HttpComponentsUtil.setParameter(
			editURL, actionResponse.getNamespace() + "mvcPath",
			templatePath + "edit_article.jsp");
		editURL = HttpComponentsUtil.setParameter(
			editURL, actionResponse.getNamespace() + "redirect",
			getRedirect(actionRequest, actionResponse));
		editURL = HttpComponentsUtil.setParameter(
			editURL, actionResponse.getNamespace() + "resourcePrimKey",
			kbArticle.getResourcePrimKey());
		editURL = HttpComponentsUtil.setParameter(
			editURL, actionResponse.getNamespace() + "status",
			WorkflowConstants.STATUS_ANY);

		return editURL;
	}

	protected abstract void doRender(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException;

	@Override
	protected boolean isSessionErrorException(Throwable throwable) {
		if (throwable instanceof AssetCategoryException ||
			throwable instanceof AssetTagException ||
			throwable instanceof FileNameException ||
			throwable instanceof FileSizeException ||
			throwable instanceof KBArticleContentException ||
			throwable instanceof KBArticlePriorityException ||
			throwable instanceof KBArticleTitleException ||
			throwable instanceof KBCommentContentException ||
			throwable instanceof NoSuchArticleException ||
			throwable instanceof NoSuchCommentException ||
			throwable instanceof NoSuchFileException ||
			throwable instanceof PrincipalException ||
			super.isSessionErrorException(throwable)) {

			return true;
		}

		return false;
	}

	@Reference
	protected AdminHelper adminHelper;

	@Reference
	protected KBArticleService kbArticleService;

	@Reference
	protected KBCommentLocalService kbCommentLocalService;

	@Reference
	protected KBCommentService kbCommentService;

	@Reference
	protected KBFolderService kbFolderService;

	@Reference
	protected KBTemplateService kbTemplateService;

	@Reference
	protected Portal portal;

	private void _compareVersions(RenderRequest renderRequest)
		throws PortletException {

		long resourcePrimKey = ParamUtil.getLong(
			renderRequest, "resourcePrimKey");
		double sourceVersion = ParamUtil.getDouble(
			renderRequest, "sourceVersion");
		double targetVersion = ParamUtil.getDouble(
			renderRequest, "targetVersion");

		String diffHtmlResults = null;

		try {
			diffHtmlResults = adminHelper.getKBArticleDiff(
				resourcePrimKey, GetterUtil.getInteger(sourceVersion),
				GetterUtil.getInteger(targetVersion), "content");
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}

		renderRequest.setAttribute(WebKeys.DIFF_HTML_RESULTS, diffHtmlResults);
	}

	private String _getContentRedirect(
		Class<?> clazz, long classPK, String redirect) {

		String portletId = HttpComponentsUtil.getParameter(
			redirect, "portletResource", false);

		String namespace = PortalUtil.getPortletNamespace(portletId);

		if (Validator.isNotNull(portletId)) {
			redirect = HttpComponentsUtil.addParameter(
				redirect, namespace + "className", clazz.getName());
			redirect = HttpComponentsUtil.addParameter(
				redirect, namespace + "classPK", classPK);
		}

		return redirect;
	}

	private static final Log _log = LogFactoryUtil.getLog(BaseKBPortlet.class);

}