/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.action;

import com.liferay.portal.LocaleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.FileItem;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.asset.AssetCategoryException;
import com.liferay.portlet.asset.AssetTagException;
import com.liferay.portlet.assetpublisher.util.AssetPublisherUtil;
import com.liferay.portlet.journal.ArticleContentException;
import com.liferay.portlet.journal.ArticleContentSizeException;
import com.liferay.portlet.journal.ArticleDisplayDateException;
import com.liferay.portlet.journal.ArticleExpirationDateException;
import com.liferay.portlet.journal.ArticleIdException;
import com.liferay.portlet.journal.ArticleSmallImageNameException;
import com.liferay.portlet.journal.ArticleSmallImageSizeException;
import com.liferay.portlet.journal.ArticleTitleException;
import com.liferay.portlet.journal.ArticleTypeException;
import com.liferay.portlet.journal.ArticleVersionException;
import com.liferay.portlet.journal.DuplicateArticleIdException;
import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.NoSuchStructureException;
import com.liferay.portlet.journal.NoSuchTemplateException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleServiceUtil;
import com.liferay.portlet.journal.service.JournalContentSearchLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalFolderServiceUtil;
import com.liferay.portlet.journal.service.JournalStructureLocalServiceUtil;
import com.liferay.portlet.journal.util.JournalUtil;

import java.io.File;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.WindowState;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Eduardo Lundgren
 * @author Juan Fernández
 */
public class EditArticleAction extends PortletAction {

	public static final String VERSION_SEPARATOR = "_version_";

	@Override
	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		JournalArticle article = null;
		String oldUrlTitle = StringPool.BLANK;

		try {
			if (Validator.isNull(cmd)) {
				UploadException uploadException =
					(UploadException)actionRequest.getAttribute(
						WebKeys.UPLOAD_EXCEPTION);

				if (uploadException != null) {
					if (uploadException.isExceededSizeLimit()) {
						throw new ArticleContentSizeException();
					}

					throw new PortalException(uploadException.getCause());
				}

				return;
			}
			else if (cmd.equals(Constants.ADD) ||
					 cmd.equals(Constants.TRANSLATE) ||
					 cmd.equals(Constants.UPDATE)) {

				Object[] returnValue = updateArticle(actionRequest);

				article = (JournalArticle)returnValue[0];
				oldUrlTitle = ((String)returnValue[1]);
			}
			else if (cmd.equals(Constants.DELETE) ||
					 cmd.equals(Constants.DELETE_VERSIONS)) {

				deleteArticles(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE_TRANSLATION)) {
				removeArticlesLocale(actionRequest);
			}
			else if (cmd.equals(Constants.EXPIRE)) {
				expireArticles(actionRequest);
			}
			else if (cmd.equals(Constants.SUBSCRIBE)) {
				subscribeArticles(actionRequest);
			}
			else if (cmd.equals(Constants.UNSUBSCRIBE)) {
				unsubscribeArticles(actionRequest);
			}

			String redirect = ParamUtil.getString(actionRequest, "redirect");

			int workflowAction = ParamUtil.getInteger(
				actionRequest, "workflowAction",
				WorkflowConstants.ACTION_PUBLISH);

			if ((article != null) &&
				(workflowAction == WorkflowConstants.ACTION_SAVE_DRAFT)) {

				redirect = getSaveAndContinueRedirect(
					portletConfig, actionRequest, article, redirect);
			}

			if (redirect.contains("/content/" + oldUrlTitle + "?")) {
				int pos = redirect.indexOf("?");

				if (pos == -1) {
					pos = redirect.length();
				}

				String newRedirect = redirect.substring(
					0, pos - oldUrlTitle.length());

				newRedirect += article.getUrlTitle();

				if (oldUrlTitle.contains("/maximized")) {
					newRedirect += "/maximized";
				}

				if (pos < redirect.length()) {
					newRedirect += "?" + redirect.substring(pos + 1);
				}

				redirect = newRedirect;
			}

			WindowState windowState = actionRequest.getWindowState();

			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			Layout layout = themeDisplay.getLayout();

			if (cmd.equals(Constants.DELETE_VERSIONS) &&
				hasArticle(actionRequest)) {

				redirect = ParamUtil.getString(
					actionRequest, "originalRedirect");
			}

			if (cmd.equals(Constants.DELETE_TRANSLATION) ||
				cmd.equals(Constants.TRANSLATE)) {

				setForward(
					actionRequest,
					"portlet.journal.update_translation_redirect");
			}
			else if (!windowState.equals(LiferayWindowState.POP_UP) &&
					 layout.isTypeControlPanel()) {

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else {
				redirect = PortalUtil.escapeRedirect(redirect);

				if (Validator.isNotNull(redirect)) {
					actionResponse.sendRedirect(redirect);
				}
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchArticleException ||
				e instanceof NoSuchStructureException ||
				e instanceof NoSuchTemplateException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.journal.error");
			}
			else if (e instanceof ArticleContentException ||
					 e instanceof ArticleContentSizeException ||
					 e instanceof ArticleDisplayDateException ||
					 e instanceof ArticleExpirationDateException ||
					 e instanceof ArticleIdException ||
					 e instanceof ArticleSmallImageNameException ||
					 e instanceof ArticleSmallImageSizeException ||
					 e instanceof ArticleTitleException ||
					 e instanceof ArticleTypeException ||
					 e instanceof ArticleVersionException ||
					 e instanceof DuplicateArticleIdException ||
					 e instanceof LocaleException) {

				SessionErrors.add(actionRequest, e.getClass());
			}
			else if (e instanceof AssetCategoryException ||
					 e instanceof AssetTagException) {

				SessionErrors.add(actionRequest, e.getClass(), e);
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getArticle(renderRequest);
		}
		catch (NoSuchArticleException nsae) {

			// Let this slide because the user can manually input a article id
			// for a new article that does not yet exist.

		}
		catch (Exception e) {
			if (//e instanceof NoSuchArticleException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return mapping.findForward("portlet.journal.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(renderRequest, "portlet.journal.edit_article"));
	}

	@Override
	public void serveResource(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		PortletContext portletContext = portletConfig.getPortletContext();

		PortletRequestDispatcher portletRequestDispatcher =
			portletContext.getRequestDispatcher(
				"/html/portlet/journal/editor.jsp");

		portletRequestDispatcher.include(resourceRequest, resourceResponse);
	}

	protected void deleteArticles(ActionRequest actionRequest)
		throws Exception {

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		String[] deleteArticleIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "deleteArticleIds"));

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			JournalArticle.class.getName(), actionRequest);

		for (String deleteArticleId : deleteArticleIds) {
			int pos = deleteArticleId.lastIndexOf(VERSION_SEPARATOR);

			String articleId = deleteArticleId;

			String articleURL = ParamUtil.getString(
				actionRequest, "articleURL");

			double version = 0;

			try {
				if (pos == -1) {
					JournalArticleServiceUtil.deleteArticle(
						groupId, articleId, articleURL, serviceContext);
				}
				else {
					articleId = articleId.substring(0, pos);
					version = GetterUtil.getDouble(
						deleteArticleId.substring(
							pos + VERSION_SEPARATOR.length()));

					JournalArticleServiceUtil.deleteArticle(
						groupId, articleId, version, articleURL,
						serviceContext);
				}

				JournalUtil.removeRecentArticle(
					actionRequest, articleId, version);
			}
			catch (NoSuchArticleException nsae) {
				long deleteFolderId = GetterUtil.getLong(deleteArticleId);

				JournalFolderServiceUtil.deleteFolder(deleteFolderId);
			}
		}
	}

	protected void expireArticles(ActionRequest actionRequest)
		throws Exception {

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		String[] expireArticleIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "expireArticleIds"));

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			JournalArticle.class.getName(), actionRequest);

		for (String expireArticleId : expireArticleIds) {
			int pos = expireArticleId.lastIndexOf(VERSION_SEPARATOR);

			String articleId = expireArticleId;

			String articleURL = ParamUtil.getString(
				actionRequest, "articleURL");

			double version = 0;

			try {
				if (pos == -1) {
					JournalArticleServiceUtil.expireArticle(
						groupId, articleId, articleURL, serviceContext);
				}
				else {
					articleId = articleId.substring(0, pos);
					version = GetterUtil.getDouble(
						expireArticleId.substring(
							pos + VERSION_SEPARATOR.length()));

					JournalArticleServiceUtil.expireArticle(
						groupId, articleId, version, articleURL,
						serviceContext);
				}
			}
			catch (NoSuchArticleException nsae) {
				long expireFolderId = GetterUtil.getLong(expireArticleId);

				expireFolder(groupId, expireFolderId, serviceContext);
			}
		}
	}

	protected void expireFolder(
			long groupId, long parentFolderId, ServiceContext serviceContext)
		throws Exception {

		List<JournalFolder> folders = JournalFolderServiceUtil.getFolders(
			groupId, parentFolderId);

		for (JournalFolder folder : folders) {
			expireFolder(groupId, folder.getFolderId(), serviceContext);
		}

		List<JournalArticle> articles = JournalArticleServiceUtil.getArticles(
			groupId, parentFolderId);

		for (JournalArticle article : articles) {
			JournalArticleServiceUtil.expireArticle(
				groupId, article.getArticleId(), null, serviceContext);
		}

	}

	protected Map<String, byte[]> getImages(
			UploadPortletRequest uploadPortletRequest)
		throws Exception {

		Map<String, byte[]> images = new HashMap<String, byte[]>();

		Map<String, FileItem[]> multipartParameterMap =
			uploadPortletRequest.getMultipartParameterMap();

		String imagePrefix = "structure_image_";

		for (String name : multipartParameterMap.keySet()) {
			if (name.startsWith(imagePrefix)) {
				File file = uploadPortletRequest.getFile(name);
				byte[] bytes = FileUtil.getBytes(file);

				if ((bytes != null) && (bytes.length > 0)) {
					name = name.substring(imagePrefix.length());

					images.put(name, bytes);
				}
			}
		}

		return images;
	}

	protected String getSaveAndContinueRedirect(
			PortletConfig portletConfig, ActionRequest actionRequest,
			JournalArticle article, String redirect)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String originalRedirect = ParamUtil.getString(
			actionRequest, "originalRedirect");

		String languageId = ParamUtil.getString(actionRequest, "languageId");

		PortletURLImpl portletURL = new PortletURLImpl(
			actionRequest, portletConfig.getPortletName(),
			themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);

		portletURL.setWindowState(actionRequest.getWindowState());

		portletURL.setParameter("struts_action", "/journal/edit_article");
		portletURL.setParameter(Constants.CMD, Constants.UPDATE, false);
		portletURL.setParameter("redirect", redirect, false);
		portletURL.setParameter("originalRedirect", originalRedirect, false);
		portletURL.setParameter(
			"groupId", String.valueOf(article.getGroupId()), false);
		portletURL.setParameter("articleId", article.getArticleId(), false);
		portletURL.setParameter(
			"version", String.valueOf(article.getVersion()), false);
		portletURL.setParameter("languageId", languageId, false);

		return portletURL.toString();
	}

	protected boolean hasArticle(ActionRequest actionRequest) throws Exception {
		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		String articleId = ParamUtil.getString(actionRequest, "articleId");

		try {
			JournalArticleLocalServiceUtil.getArticle(groupId, articleId);
		}
		catch (NoSuchArticleException nsae) {
			return true;
		}

		return false;
	}

	protected void removeArticlesLocale(ActionRequest actionRequest)
		throws Exception {

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		String[] removeArticleLocaleIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "deleteArticleIds"));

		for (String removeArticleLocaleId : removeArticleLocaleIds) {
			int pos = removeArticleLocaleId.lastIndexOf(VERSION_SEPARATOR);

			String articleId = removeArticleLocaleId.substring(0, pos);
			double version = GetterUtil.getDouble(
				removeArticleLocaleId.substring(
					pos + VERSION_SEPARATOR.length()));
			String languageId = ParamUtil.getString(
				actionRequest, "languageId");

			JournalArticleServiceUtil.removeArticleLocale(
				groupId, articleId, version, languageId);
		}
	}

	protected void subscribeArticles(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		JournalArticleServiceUtil.subscribe(themeDisplay.getScopeGroupId());
	}

	protected void unsubscribeArticles(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		JournalArticleServiceUtil.unsubscribe(themeDisplay.getScopeGroupId());
	}

	protected Object[] updateArticle(ActionRequest actionRequest)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			PortalUtil.getUploadPortletRequest(actionRequest);

		String cmd = ParamUtil.getString(uploadPortletRequest, Constants.CMD);

		long groupId = ParamUtil.getLong(uploadPortletRequest, "groupId");
		long folderId = ParamUtil.getLong(uploadPortletRequest, "folderId");
		long classNameId = ParamUtil.getLong(
			uploadPortletRequest, "classNameId");
		long classPK = ParamUtil.getLong(uploadPortletRequest, "classPK");
		String articleId = ParamUtil.getString(
			uploadPortletRequest, "articleId");
		boolean autoArticleId = ParamUtil.getBoolean(
			uploadPortletRequest, "autoArticleId");
		double version = ParamUtil.getDouble(uploadPortletRequest, "version");
		boolean localized = ParamUtil.getBoolean(
			uploadPortletRequest, "localized");

		String defaultLanguageId = ParamUtil.getString(
			uploadPortletRequest, "defaultLanguageId");

		Locale defaultLocale = LocaleUtil.fromLanguageId(defaultLanguageId);

		String toLanguageId = ParamUtil.getString(
			uploadPortletRequest, "toLanguageId");

		Locale toLocale = null;

		String title = StringPool.BLANK;
		String description = StringPool.BLANK;

		if (Validator.isNull(toLanguageId)) {
			title = ParamUtil.getString(
				uploadPortletRequest, "title_" + defaultLanguageId);
			description = ParamUtil.getString(
				uploadPortletRequest, "description_" + defaultLanguageId);
		}
		else {
			toLocale = LocaleUtil.fromLanguageId(toLanguageId);

			title = ParamUtil.getString(
				uploadPortletRequest, "title_" + toLanguageId);
			description = ParamUtil.getString(
				uploadPortletRequest, "description_" + toLanguageId);
		}

		String content = ParamUtil.getString(uploadPortletRequest, "content");

		Boolean fileItemThresholdSizeExceeded =
				(Boolean)uploadPortletRequest.getAttribute(
			WebKeys.FILE_ITEM_THRESHOLD_SIZE_EXCEEDED);

		if ((fileItemThresholdSizeExceeded != null) &&
			fileItemThresholdSizeExceeded.booleanValue()) {

			throw new ArticleContentSizeException();
		}

		String type = ParamUtil.getString(uploadPortletRequest, "type");
		String structureId = ParamUtil.getString(
			uploadPortletRequest, "structureId");
		String templateId = ParamUtil.getString(
			uploadPortletRequest, "templateId");
		String layoutUuid = ParamUtil.getString(
			uploadPortletRequest, "layoutUuid");

		// The target page and the article must belong to the same group

		Layout targetLayout =
			LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
				layoutUuid, groupId);

		if (targetLayout == null) {
			layoutUuid = null;
		}

		int displayDateMonth = ParamUtil.getInteger(
			uploadPortletRequest, "displayDateMonth");
		int displayDateDay = ParamUtil.getInteger(
			uploadPortletRequest, "displayDateDay");
		int displayDateYear = ParamUtil.getInteger(
			uploadPortletRequest, "displayDateYear");
		int displayDateHour = ParamUtil.getInteger(
			uploadPortletRequest, "displayDateHour");
		int displayDateMinute = ParamUtil.getInteger(
			uploadPortletRequest, "displayDateMinute");
		int displayDateAmPm = ParamUtil.getInteger(
			uploadPortletRequest, "displayDateAmPm");

		if (displayDateAmPm == Calendar.PM) {
			displayDateHour += 12;
		}

		int expirationDateMonth = ParamUtil.getInteger(
			uploadPortletRequest, "expirationDateMonth");
		int expirationDateDay = ParamUtil.getInteger(
			uploadPortletRequest, "expirationDateDay");
		int expirationDateYear = ParamUtil.getInteger(
			uploadPortletRequest, "expirationDateYear");
		int expirationDateHour = ParamUtil.getInteger(
			uploadPortletRequest, "expirationDateHour");
		int expirationDateMinute = ParamUtil.getInteger(
			uploadPortletRequest, "expirationDateMinute");
		int expirationDateAmPm = ParamUtil.getInteger(
			uploadPortletRequest, "expirationDateAmPm");
		boolean neverExpire = ParamUtil.getBoolean(
			uploadPortletRequest, "neverExpire");

		if (expirationDateAmPm == Calendar.PM) {
			expirationDateHour += 12;
		}

		int reviewDateMonth = ParamUtil.getInteger(
			uploadPortletRequest, "reviewDateMonth");
		int reviewDateDay = ParamUtil.getInteger(
			uploadPortletRequest, "reviewDateDay");
		int reviewDateYear = ParamUtil.getInteger(
			uploadPortletRequest, "reviewDateYear");
		int reviewDateHour = ParamUtil.getInteger(
			uploadPortletRequest, "reviewDateHour");
		int reviewDateMinute = ParamUtil.getInteger(
			uploadPortletRequest, "reviewDateMinute");
		int reviewDateAmPm = ParamUtil.getInteger(
			uploadPortletRequest, "reviewDateAmPm");
		boolean neverReview = ParamUtil.getBoolean(
			uploadPortletRequest, "neverReview");

		if (reviewDateAmPm == Calendar.PM) {
			reviewDateHour += 12;
		}

		boolean indexable = ParamUtil.getBoolean(
			uploadPortletRequest, "indexable");

		boolean smallImage = ParamUtil.getBoolean(
			uploadPortletRequest, "smallImage");
		String smallImageURL = ParamUtil.getString(
			uploadPortletRequest, "smallImageURL");
		File smallFile = uploadPortletRequest.getFile("smallFile");

		Map<String, byte[]> images = getImages(uploadPortletRequest);

		String articleURL = ParamUtil.getString(
			uploadPortletRequest, "articleURL");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			JournalArticle.class.getName(), actionRequest);

		serviceContext.setAttribute("defaultLanguageId", defaultLanguageId);

		JournalArticle article = null;
		String oldUrlTitle = StringPool.BLANK;

		if (cmd.equals(Constants.ADD)) {
			Map<Locale, String> titleMap = new HashMap<Locale, String>();

			titleMap.put(defaultLocale, title);

			Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

			descriptionMap.put(defaultLocale, description);

			if (Validator.isNull(structureId)) {
				content = LocalizationUtil.updateLocalization(
					StringPool.BLANK, "static-content", content,
					defaultLanguageId, defaultLanguageId, true, localized);
			}

			// Add article

			article = JournalArticleServiceUtil.addArticle(
				groupId, folderId, classNameId, classPK, articleId,
				autoArticleId, titleMap, descriptionMap, content, type,
				structureId, templateId, layoutUuid, displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute, expirationDateMonth, expirationDateDay,
				expirationDateYear, expirationDateHour, expirationDateMinute,
				neverExpire, reviewDateMonth, reviewDateDay, reviewDateYear,
				reviewDateHour, reviewDateMinute, neverReview, indexable,
				smallImage, smallImageURL, smallFile, images, articleURL,
				serviceContext);

			AssetPublisherUtil.addAndStoreSelection(
				actionRequest, JournalArticle.class.getName(),
				article.getResourcePrimKey(), -1);
		}
		else {

			// Merge current content with new content

			JournalArticle curArticle = JournalArticleServiceUtil.getArticle(
				groupId, articleId, version);

			if (Validator.isNull(structureId)) {
				if (!curArticle.isTemplateDriven()) {
					String curContent = StringPool.BLANK;

					curContent = curArticle.getContent();

					if (cmd.equals(Constants.TRANSLATE)) {
						content = LocalizationUtil.updateLocalization(
							curContent, "static-content", content, toLanguageId,
							defaultLanguageId, true, true);
					}
					else {
						content = LocalizationUtil.updateLocalization(
							curContent, "static-content", content,
							defaultLanguageId, defaultLanguageId, true,
							localized);
					}
				}
			}
			else {
				if (curArticle.isTemplateDriven()) {
					JournalStructure structure =
						JournalStructureLocalServiceUtil.getStructure(
							groupId, structureId, true);

					content = JournalUtil.mergeArticleContent(
						curArticle.getContent(), content, true);
					content = JournalUtil.removeOldContent(
						content, structure.getMergedXsd());
				}
			}

			// Update article

			article = JournalArticleServiceUtil.getArticle(
				groupId, articleId, version);

			Map<Locale, String> titleMap = article.getTitleMap();
			Map<Locale, String> descriptionMap = article.getDescriptionMap();

			String tempOldUrlTitle = article.getUrlTitle();

			if (cmd.equals(Constants.UPDATE)) {
				titleMap.put(defaultLocale, title);
				descriptionMap.put(defaultLocale, description);

				article = JournalArticleServiceUtil.updateArticle(
					groupId, folderId, articleId, version, titleMap,
					descriptionMap, content, type, structureId, templateId,
					layoutUuid, displayDateMonth, displayDateDay,
					displayDateYear, displayDateHour, displayDateMinute,
					expirationDateMonth, expirationDateDay, expirationDateYear,
					expirationDateHour, expirationDateMinute, neverExpire,
					reviewDateMonth, reviewDateDay, reviewDateYear,
					reviewDateHour, reviewDateMinute, neverReview, indexable,
					smallImage, smallImageURL, smallFile, images, articleURL,
					serviceContext);
			}
			else if (cmd.equals(Constants.TRANSLATE)) {
				article = JournalArticleServiceUtil.updateArticleTranslation(
					groupId, articleId, version, toLocale, title, description,
					content, images, serviceContext);
			}

			if (!tempOldUrlTitle.equals(article.getUrlTitle())) {
				oldUrlTitle = tempOldUrlTitle;
			}
		}

		// Recent articles

		JournalUtil.addRecentArticle(actionRequest, article);

		// Journal content

		String portletResource = ParamUtil.getString(
			uploadPortletRequest, "portletResource");

		if (Validator.isNotNull(portletResource)) {
			PortletPreferences preferences =
				PortletPreferencesFactoryUtil.getPortletSetup(
					uploadPortletRequest, portletResource);

			preferences.setValue(
				"groupId", String.valueOf(article.getGroupId()));
			preferences.setValue("articleId", article.getArticleId());

			preferences.store();

			updateContentSearch(
				actionRequest, portletResource, article.getArticleId());
		}

		return new Object[] {article, oldUrlTitle};
	}

	protected void updateContentSearch(
			ActionRequest actionRequest, String portletResource,
			String articleId)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		JournalContentSearchLocalServiceUtil.updateContentSearch(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			portletResource, articleId, true);
	}

}