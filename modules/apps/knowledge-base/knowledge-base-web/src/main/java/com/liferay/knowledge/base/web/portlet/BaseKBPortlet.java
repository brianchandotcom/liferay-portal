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

package com.liferay.knowledge.base.web.portlet;

import com.liferay.asset.kernel.exception.AssetCategoryException;
import com.liferay.asset.kernel.exception.AssetTagException;
import com.liferay.document.library.kernel.exception.DuplicateFileException;
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
import com.liferay.knowledge.base.service.util.KnowledgeBaseConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.LiferayFileItemException;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.upload.UploadRequestSizeException;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.IOException;
import java.io.InputStream;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseKBPortlet extends MVCPortlet {

	public void addTempAttachment(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			portal.getUploadPortletRequest(actionRequest);

		checkExceededSizeLimit(actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long resourcePrimKey = ParamUtil.getLong(
			actionRequest, "resourcePrimKey");
		String sourceFileName = uploadPortletRequest.getFileName("file");

		InputStream inputStream = null;

		try {
			inputStream = uploadPortletRequest.getFileAsStream("file");

			String mimeType = uploadPortletRequest.getContentType("file");

			kbArticleService.addTempAttachment(
				themeDisplay.getScopeGroupId(), resourcePrimKey, sourceFileName,
				KnowledgeBaseConstants.TEMP_FOLDER_NAME, inputStream, mimeType);
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}
	}

	public void deleteKBArticle(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long resourcePrimKey = ParamUtil.getLong(
			actionRequest, "resourcePrimKey");

		kbArticleService.deleteKBArticle(resourcePrimKey);
	}

	public void deleteKBComment(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!themeDisplay.isSignedIn()) {
			return;
		}

		long kbCommentId = ParamUtil.getLong(actionRequest, "kbCommentId");

		kbCommentService.deleteKBComment(kbCommentId);

		SessionMessages.add(actionRequest, "suggestionDeleted");
	}

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

		long resourcePrimKey = ParamUtil.getLong(
			actionRequest, "resourcePrimKey");
		String fileName = ParamUtil.getString(actionRequest, "fileName");

		JSONObject jsonObject = jsonFactory.createJSONObject();

		try {
			kbArticleService.deleteTempAttachment(
				themeDisplay.getScopeGroupId(), resourcePrimKey, fileName,
				KnowledgeBaseConstants.TEMP_FOLDER_NAME);

			jsonObject.put("deleted", Boolean.TRUE);
		}
		catch (Exception e) {
			String errorMessage = themeDisplay.translate(
				"an-unexpected-error-occurred-while-deleting-the-file");

			jsonObject.put("deleted", Boolean.FALSE);
			jsonObject.put("errorMessage", errorMessage);
		}

		writeJSON(actionRequest, actionResponse, jsonObject);
	}

	public void moveKBObject(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long resourceClassNameId = ParamUtil.getLong(
			actionRequest, "resourceClassNameId");
		long resourcePrimKey = ParamUtil.getLong(
			actionRequest, "resourcePrimKey");
		long parentResourceClassNameId = ParamUtil.getLong(
			actionRequest, "parentResourceClassNameId",
			portal.getClassNameId(KBFolderConstants.getClassName()));
		long parentResourcePrimKey = ParamUtil.getLong(
			actionRequest, "parentResourcePrimKey",
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID);
		double priority = ParamUtil.getDouble(actionRequest, "priority");

		long kbArticleClassNameId = portal.getClassNameId(
			KBArticleConstants.getClassName());

		if (resourceClassNameId == kbArticleClassNameId) {
			kbArticleService.moveKBArticle(
				resourcePrimKey, parentResourceClassNameId,
				parentResourcePrimKey, priority);
		}
		else {
			kbFolderService.moveKBFolder(
				resourcePrimKey, parentResourcePrimKey);
		}
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

			if (resourceID.equals("kbArticleRSS")) {
				serveKBArticleRSS(resourceRequest, resourceResponse);
			}
		}
		catch (IOException ioe) {
			throw ioe;
		}
		catch (PortletException pe) {
			throw pe;
		}
		catch (Exception e) {
			throw new PortletException(e);
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

		String portletId = portal.getPortletId(actionRequest);

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		long resourcePrimKey = ParamUtil.getLong(
			actionRequest, "resourcePrimKey");
		long parentResourceClassNameId = ParamUtil.getLong(
			actionRequest, "parentResourceClassNameId",
			portal.getClassNameId(KBFolderConstants.getClassName()));
		long parentResourcePrimKey = ParamUtil.getLong(
			actionRequest, "parentResourcePrimKey",
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		String title = ParamUtil.getString(actionRequest, "title");
		String urlTitle = ParamUtil.getString(actionRequest, "urlTitle");
		String content = ParamUtil.getString(actionRequest, "content");
		String description = ParamUtil.getString(actionRequest, "description");
		String sourceURL = ParamUtil.getString(actionRequest, "sourceURL");
		String[] sections = actionRequest.getParameterValues("sections");
		String[] selectedFileNames = ParamUtil.getParameterValues(
			actionRequest, "selectedFileName");
		long[] removeFileEntryIds = ParamUtil.getLongValues(
			actionRequest, "removeFileEntryIds");
		int workflowAction = ParamUtil.getInteger(
			actionRequest, "workflowAction");

		KBArticle kbArticle = null;

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			KBArticle.class.getName(), actionRequest);

		if (cmd.equals(Constants.ADD)) {
			kbArticle = kbArticleService.addKBArticle(
				portletId, parentResourceClassNameId, parentResourcePrimKey,
				title, urlTitle, content, description, sourceURL, sections,
				selectedFileNames, serviceContext);
		}
		else if (cmd.equals(Constants.REVERT)) {
			int version = ParamUtil.getInteger(
				actionRequest, "version", KBArticleConstants.DEFAULT_VERSION);

			kbArticle = kbArticleService.revertKBArticle(
				resourcePrimKey, version, serviceContext);
		}
		else if (cmd.equals(Constants.UPDATE)) {
			kbArticle = kbArticleService.updateKBArticle(
				resourcePrimKey, title, content, description, sourceURL,
				sections, selectedFileNames, removeFileEntryIds,
				serviceContext);
		}

		if (!cmd.equals(Constants.ADD) && !cmd.equals(Constants.UPDATE)) {
			return;
		}

		if (workflowAction == WorkflowConstants.ACTION_SAVE_DRAFT) {
			String editURL = buildEditURL(
				actionRequest, actionResponse, kbArticle);

			actionRequest.setAttribute(WebKeys.REDIRECT, editURL);
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

		long kbCommentId = ParamUtil.getLong(actionRequest, "kbCommentId");

		long classNameId = ParamUtil.getLong(actionRequest, "classNameId");
		long classPK = ParamUtil.getLong(actionRequest, "classPK");
		String content = ParamUtil.getString(actionRequest, "content");
		int status = ParamUtil.getInteger(
			actionRequest, "status", KBCommentConstants.STATUS_ANY);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			KBComment.class.getName(), actionRequest);

		if (cmd.equals(Constants.ADD)) {
			kbCommentLocalService.addKBComment(
				themeDisplay.getUserId(), classNameId, classPK, content,
				serviceContext);
		}
		else if (cmd.equals(Constants.UPDATE)) {
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

		editURL = HttpUtil.setParameter(
			editURL, "p_p_id", portletDisplay.getId());
		editURL = HttpUtil.setParameter(
			editURL, actionResponse.getNamespace() + "mvcPath",
			templatePath + "edit_article.jsp");
		editURL = HttpUtil.setParameter(
			editURL, actionResponse.getNamespace() + "redirect",
			getRedirect(actionRequest, actionResponse));
		editURL = HttpUtil.setParameter(
			editURL, actionResponse.getNamespace() + "resourcePrimKey",
			kbArticle.getResourcePrimKey());
		editURL = HttpUtil.setParameter(
			editURL, actionResponse.getNamespace() + "status",
			WorkflowConstants.STATUS_ANY);

		return editURL;
	}

	protected void checkExceededSizeLimit(PortletRequest portletRequest)
		throws PortalException {

		UploadException uploadException =
			(UploadException)portletRequest.getAttribute(
				WebKeys.UPLOAD_EXCEPTION);

		if (uploadException != null) {
			Throwable cause = uploadException.getCause();

			if (uploadException.isExceededFileSizeLimit()) {
				throw new FileSizeException(cause);
			}

			if (uploadException.isExceededLiferayFileItemSizeLimit()) {
				throw new LiferayFileItemException(cause);
			}

			if (uploadException.isExceededUploadRequestSizeLimit()) {
				throw new UploadRequestSizeException(cause);
			}

			throw new PortalException(cause);
		}
	}

	@Override
	protected boolean isSessionErrorException(Throwable cause) {
		if (cause instanceof AssetCategoryException ||
			cause instanceof AssetTagException ||
			cause instanceof DuplicateFileException ||
			cause instanceof FileNameException ||
			cause instanceof FileSizeException ||
			cause instanceof KBArticleContentException ||
			cause instanceof KBArticlePriorityException ||
			cause instanceof KBArticleTitleException ||
			cause instanceof KBCommentContentException ||
			cause instanceof NoSuchArticleException ||
			cause instanceof NoSuchCommentException ||
			cause instanceof NoSuchFileException ||
			cause instanceof PrincipalException ||
			super.isSessionErrorException(cause)) {

			return true;
		}

		return false;
	}

	@Reference(unbind = "-")
	protected void setJSONFactory(JSONFactory jsonFactory) {
		this.jsonFactory = jsonFactory;
	}

	@Reference(unbind = "-")
	protected void setKBArticleService(KBArticleService kbArticleService) {
		this.kbArticleService = kbArticleService;
	}

	@Reference(unbind = "-")
	protected void setKBCommentLocalService(
		KBCommentLocalService kbCommentLocalService) {

		this.kbCommentLocalService = kbCommentLocalService;
	}

	@Reference(unbind = "-")
	protected void setKBCommentService(KBCommentService kbCommentService) {
		this.kbCommentService = kbCommentService;
	}

	@Reference(unbind = "-")
	protected void setKBFolderService(KBFolderService kbFolderService) {
		this.kbFolderService = kbFolderService;
	}

	@Reference(unbind = "-")
	protected void setKBTemplateService(KBTemplateService kbTemplateService) {
		this.kbTemplateService = kbTemplateService;
	}

	@Reference(unbind = "-")
	protected void setPortal(Portal portal) {
		this.portal = portal;
	}

	protected JSONFactory jsonFactory;
	protected KBArticleService kbArticleService;
	protected KBCommentLocalService kbCommentLocalService;
	protected KBCommentService kbCommentService;
	protected KBFolderService kbFolderService;
	protected KBTemplateService kbTemplateService;
	protected Portal portal;

}