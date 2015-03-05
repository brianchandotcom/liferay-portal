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

package com.liferay.portlet.journal;

import com.liferay.portal.LocaleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.LiferayFileItemException;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.TrashedModel;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.asset.AssetCategoryException;
import com.liferay.portlet.asset.AssetTagException;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.FileSizeException;
import com.liferay.portlet.dynamicdatamapping.NoSuchStructureException;
import com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException;
import com.liferay.portlet.dynamicdatamapping.StorageFieldRequiredException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.journal.action.ActionUtil;
import com.liferay.portlet.journal.asset.JournalArticleAssetRenderer;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFeed;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.model.JournalFolderConstants;
import com.liferay.portlet.journal.service.JournalArticleServiceUtil;
import com.liferay.portlet.journal.service.JournalContentSearchLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalFeedServiceUtil;
import com.liferay.portlet.journal.service.JournalFolderServiceUtil;
import com.liferay.portlet.journal.util.JournalRSSUtil;
import com.liferay.portlet.journal.util.JournalUtil;
import com.liferay.portlet.trash.util.TrashUtil;
import com.liferay.util.RSSUtil;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.WindowState;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Eduardo Garcia
 */
public class JournalPortlet extends MVCPortlet {

	public static final String VERSION_SEPARATOR = "_version_";

	public void addArticle(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		updateArticle(actionRequest, actionResponse);
	}

	public void addFeed(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		updateFeed(actionRequest, actionResponse);
	}

	public void addFolder(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		updateFolder(actionRequest, actionResponse);
	}

	public void deleteArticles(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		doDeleteArticles(actionRequest, actionResponse, false);
	}

	public void deleteEntries(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		doDeleteEntries(actionRequest, actionResponse, false);
	}

	public void deleteFeeds(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		String[] deleteFeedIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "deleteFeedIds"));

		for (int i = 0; i < deleteFeedIds.length; i++) {
			JournalFeedServiceUtil.deleteFeed(groupId, deleteFeedIds[i]);
		}
	}

	public void deleteFolders(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		doDeleteFolders(actionRequest, actionResponse, false);
	}

	public void expireArticles(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String articleId = ParamUtil.getString(actionRequest, "articleId");

		if (Validator.isNotNull(articleId)) {
			ActionUtil.expireArticle(actionRequest, articleId);
		}
		else {
			String[] expireArticleIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "expireArticleIds"));

			for (String expireArticleId : expireArticleIds) {
				ActionUtil.expireArticle(
					actionRequest, HtmlUtil.unescape(expireArticleId));
			}
		}

		sendEditArticleRedirect(actionRequest, actionResponse);
	}

	public void expireEntries(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long[] expireFolderIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "folderIds"), 0L);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			JournalArticle.class.getName(), actionRequest);

		for (long expireFolderId : expireFolderIds) {
			ActionUtil.expireFolder(
				themeDisplay.getScopeGroupId(), expireFolderId, serviceContext);
		}

		String[] expireArticleIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "articleIds"));

		for (String expireArticleId : expireArticleIds) {
			ActionUtil.expireArticle(
				actionRequest, HtmlUtil.unescape(expireArticleId));
		}

		sendEditEntryRedirect(actionRequest, actionResponse);
	}

	public void moveArticlesToTrash(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		doDeleteArticles(actionRequest, actionResponse, true);
	}

	public void moveEntries(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long newFolderId = ParamUtil.getLong(actionRequest, "newFolderId");

		long[] folderIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "folderIds"), 0L);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			JournalArticle.class.getName(), actionRequest);

		for (long folderId : folderIds) {
			JournalFolderServiceUtil.moveFolder(
				folderId, newFolderId, serviceContext);
		}

		List<String> invalidArticleIds = new ArrayList<>();

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String[] articleIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "articleIds"));

		for (String articleId : articleIds) {
			try {
				JournalArticleServiceUtil.moveArticle(
					themeDisplay.getScopeGroupId(),
					HtmlUtil.unescape(articleId), newFolderId, serviceContext);
			}
			catch (InvalidDDMStructureException idse) {
				invalidArticleIds.add(articleId);
			}
		}

		if (!invalidArticleIds.isEmpty()) {
			throw new InvalidDDMStructureException();
		}

		sendEditEntryRedirect(actionRequest, actionResponse);
	}

	public void moveEntriesToTrash(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		doDeleteEntries(actionRequest, actionResponse, true);
	}

	public void moveFoldersToTrash(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		doDeleteFolders(actionRequest, actionResponse, true);
	}

	public void previewArticle(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		updateArticle(actionRequest, actionResponse);
	}

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		String resourceID = GetterUtil.getString(
			resourceRequest.getResourceID());

		if (resourceID.equals("rss")) {
			HttpServletRequest request = PortalUtil.getHttpServletRequest(
				resourceRequest);

			HttpServletResponse response = PortalUtil.getHttpServletResponse(
				resourceResponse);

			try {
				byte[] xml = JournalRSSUtil.getRSS(
					resourceRequest, resourceResponse);

				ServletResponseUtil.sendFile(
					request, response, null, xml, ContentTypes.TEXT_XML_UTF8);
			}
			catch (Exception e) {
				try {
					PortalUtil.sendError(e, request, response);
				}
				catch (ServletException se) {
				}
			}
		}
		else {
			super.serveResource(resourceRequest, resourceResponse);
		}
	}

	public void subscribeFolder(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long folderId = ParamUtil.getLong(actionRequest, "folderId");

		JournalFolderServiceUtil.subscribe(
			themeDisplay.getScopeGroupId(), folderId);
	}

	public void subscribeStructure(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long ddmStructureId = ParamUtil.getLong(
			actionRequest, "ddmStructureId");

		JournalArticleServiceUtil.subscribeStructure(
			themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
			ddmStructureId);

		sendEditArticleRedirect(actionRequest, actionResponse);
	}

	public void unsubscribeFolder(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long folderId = ParamUtil.getLong(actionRequest, "folderId");

		JournalFolderServiceUtil.unsubscribe(
			themeDisplay.getScopeGroupId(), folderId);
	}

	public void unsubscribeStructure(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long ddmStructureId = ParamUtil.getLong(
			actionRequest, "ddmStructureId");

		JournalArticleServiceUtil.unsubscribeStructure(
			themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
			ddmStructureId);

		sendEditArticleRedirect(actionRequest, actionResponse);
	}

	public void updateArticle(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		UploadException uploadException =
			(UploadException)actionRequest.getAttribute(
				WebKeys.UPLOAD_EXCEPTION);

		if (uploadException != null) {
			if (uploadException.isExceededLiferayFileItemSizeLimit()) {
				throw new LiferayFileItemException();
			}
			else if (uploadException.isExceededSizeLimit()) {
				throw new ArticleContentSizeException();
			}

			throw new PortalException(uploadException.getCause());
		}

		UploadPortletRequest uploadPortletRequest =
			PortalUtil.getUploadPortletRequest(actionRequest);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Updating article " +
					MapUtil.toString(uploadPortletRequest.getParameterMap()));
		}

		String actionName = ParamUtil.getString(
			actionRequest, ActionRequest.ACTION_NAME);

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

		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "title");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			JournalArticle.class.getName(), uploadPortletRequest);

		String ddmStructureKey = ParamUtil.getString(
			uploadPortletRequest, "ddmStructureKey");

		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.getStructure(
			PortalUtil.getSiteGroupId(groupId),
			PortalUtil.getClassNameId(JournalArticle.class), ddmStructureKey,
			true);

		Object[] contentAndImages = ActionUtil.getContentAndImages(
			ddmStructure, serviceContext);

		String content = (String)contentAndImages[0];
		Map<String, byte[]> images =
			(HashMap<String, byte[]>)contentAndImages[1];

		Boolean fileItemThresholdSizeExceeded =
			(Boolean)uploadPortletRequest.getAttribute(
				WebKeys.FILE_ITEM_THRESHOLD_SIZE_EXCEEDED);

		if ((fileItemThresholdSizeExceeded != null) &&
			fileItemThresholdSizeExceeded.booleanValue()) {

			throw new ArticleContentSizeException();
		}

		String ddmTemplateKey = ParamUtil.getString(
			uploadPortletRequest, "ddmTemplateKey");
		String layoutUuid = ParamUtil.getString(
			uploadPortletRequest, "layoutUuid");

		Layout targetLayout = JournalUtil.getArticleLayout(layoutUuid, groupId);

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

		String articleURL = ParamUtil.getString(
			uploadPortletRequest, "articleURL");

		JournalArticle article = null;
		String oldUrlTitle = StringPool.BLANK;

		if (actionName.equals("addArticle")) {

			// Add article

			article = JournalArticleServiceUtil.addArticle(
				groupId, folderId, classNameId, classPK, articleId,
				autoArticleId, titleMap, descriptionMap, content,
				ddmStructureKey, ddmTemplateKey, layoutUuid, displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute, expirationDateMonth, expirationDateDay,
				expirationDateYear, expirationDateHour, expirationDateMinute,
				neverExpire, reviewDateMonth, reviewDateDay, reviewDateYear,
				reviewDateHour, reviewDateMinute, neverReview, indexable,
				smallImage, smallImageURL, smallFile, images, articleURL,
				serviceContext);
		}
		else {

			// Update article

			article = JournalArticleServiceUtil.getArticle(
				groupId, articleId, version);

			String tempOldUrlTitle = article.getUrlTitle();

			if (actionName.equals("previewArticle") ||
				actionName.equals("updateArticle")) {

				article = JournalArticleServiceUtil.updateArticle(
					groupId, folderId, articleId, version, titleMap,
					descriptionMap, content, ddmStructureKey, ddmTemplateKey,
					layoutUuid, displayDateMonth, displayDateDay,
					displayDateYear, displayDateHour, displayDateMinute,
					expirationDateMonth, expirationDateDay, expirationDateYear,
					expirationDateHour, expirationDateMinute, neverExpire,
					reviewDateMonth, reviewDateDay, reviewDateYear,
					reviewDateHour, reviewDateMinute, neverReview, indexable,
					smallImage, smallImageURL, smallFile, images, articleURL,
					serviceContext);
			}

			if (!tempOldUrlTitle.equals(article.getUrlTitle())) {
				oldUrlTitle = tempOldUrlTitle;
			}
		}

		// Recent articles

		JournalUtil.addRecentArticle(actionRequest, article);

		// Journal content

		String portletResource = ParamUtil.getString(
			actionRequest, "portletResource");

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.getStrictPortletSetup(
				themeDisplay.getLayout(), portletResource);

		if (portletPreferences != null) {
			portletPreferences.setValue(
				"groupId", String.valueOf(article.getGroupId()));
			portletPreferences.setValue("articleId", article.getArticleId());

			portletPreferences.store();

			updateContentSearch(
				actionRequest, portletResource, article.getArticleId());
		}

		sendEditArticleRedirect(
			actionRequest, actionResponse, article, oldUrlTitle);
	}

	public void updateFeed(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String actionName = ParamUtil.getString(
			actionRequest, ActionRequest.ACTION_NAME);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		String feedId = ParamUtil.getString(actionRequest, "feedId");
		boolean autoFeedId = ParamUtil.getBoolean(actionRequest, "autoFeedId");

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		String ddmStructureKey = ParamUtil.getString(
			actionRequest, "ddmStructureKey");
		String ddmTemplateKey = ParamUtil.getString(
			actionRequest, "ddmTemplateKey");
		String ddmRendererTemplateKey = ParamUtil.getString(
			actionRequest, "ddmRendererTemplateKey");
		int delta = ParamUtil.getInteger(actionRequest, "delta");
		String orderByCol = ParamUtil.getString(actionRequest, "orderByCol");
		String orderByType = ParamUtil.getString(actionRequest, "orderByType");
		String targetLayoutFriendlyUrl = ParamUtil.getString(
			actionRequest, "targetLayoutFriendlyUrl");
		String targetPortletId = ParamUtil.getString(
			actionRequest, "targetPortletId");
		String contentField = ParamUtil.getString(
			actionRequest, "contentField");
		String feedType = ParamUtil.getString(
			actionRequest, "feedType", RSSUtil.FEED_TYPE_DEFAULT);

		String feedFormat = RSSUtil.getFeedTypeFormat(feedType);
		double feedVersion = RSSUtil.getFeedTypeVersion(feedType);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			JournalFeed.class.getName(), actionRequest);

		if (actionName.equals("addFeed")) {

			// Add feed

			JournalFeedServiceUtil.addFeed(
				groupId, feedId, autoFeedId, name, description, ddmStructureKey,
				ddmTemplateKey, ddmRendererTemplateKey, delta, orderByCol,
				orderByType, targetLayoutFriendlyUrl, targetPortletId,
				contentField, feedFormat, feedVersion, serviceContext);
		}
		else {

			// Update feed

			JournalFeedServiceUtil.updateFeed(
				groupId, feedId, name, description, ddmStructureKey,
				ddmTemplateKey, ddmRendererTemplateKey, delta, orderByCol,
				orderByType, targetLayoutFriendlyUrl, targetPortletId,
				contentField, feedFormat, feedVersion, serviceContext);
		}
	}

	public void updateFolder(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long folderId = ParamUtil.getLong(actionRequest, "folderId");

		long parentFolderId = ParamUtil.getLong(
			actionRequest, "parentFolderId");
		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");

		boolean mergeWithParentFolder = ParamUtil.getBoolean(
			actionRequest, "mergeWithParentFolder");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			JournalFolder.class.getName(), actionRequest);

		if (folderId <= 0) {

			// Add folder

			JournalFolderServiceUtil.addFolder(
				serviceContext.getScopeGroupId(), parentFolderId, name,
				description, serviceContext);
		}
		else {

			// Update folder

			long[] ddmStructureIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "ddmStructuresSearchContainerPrimaryKeys"),
				0L);
			int restrinctionType = ParamUtil.getInteger(
				actionRequest, "restrictionType");

			JournalFolderServiceUtil.updateFolder(
				folderId, parentFolderId, name, description, ddmStructureIds,
				restrinctionType, mergeWithParentFolder, serviceContext);
		}
	}

	public void updateWorkflowDefinitions(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long[] ddmStructureIds = StringUtil.split(
				ParamUtil.getString(
						actionRequest,
						"ddmStructuresSearchContainerPrimaryKeys"),
				0L);
		int restrinctionType = ParamUtil.getInteger(
			actionRequest, "restrictionType");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			JournalFolder.class.getName(), actionRequest);

		JournalFolderServiceUtil.updateFolder(
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, null, null,
			ddmStructureIds, restrinctionType, false, serviceContext);
	}

	protected void doDeleteArticles(
			ActionRequest actionRequest, ActionResponse actionResponse,
			boolean moveToTrash)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String[] deleteArticleIds = null;

		String articleId = ParamUtil.getString(actionRequest, "articleId");

		if (Validator.isNotNull(articleId)) {
			deleteArticleIds = new String[] {articleId};
		}
		else {
			deleteArticleIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "articleIds"));
		}

		List<TrashedModel> trashedModels = new ArrayList<>();

		for (String deleteArticleId : deleteArticleIds) {
			if (moveToTrash) {
				JournalArticle article =
					JournalArticleServiceUtil.moveArticleToTrash(
						themeDisplay.getScopeGroupId(),
						HtmlUtil.unescape(deleteArticleId));

				trashedModels.add(article);
			}
			else {
				ActionUtil.deleteArticle(
					actionRequest, HtmlUtil.unescape(deleteArticleId));
			}
		}

		if (moveToTrash && !trashedModels.isEmpty()) {
			TrashUtil.addTrashSessionMessages(actionRequest, trashedModels);

			hideDefaultSuccessMessage(actionRequest);
		}

		sendEditArticleRedirect(actionRequest, actionResponse);
	}

	protected void doDeleteEntries(
			ActionRequest actionRequest, ActionResponse actionResponse,
			boolean moveToTrash)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<TrashedModel> trashedModels = new ArrayList<>();

		long[] deleteFolderIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "folderIds"), 0L);

		for (long deleteFolderId : deleteFolderIds) {
			if (moveToTrash) {
				JournalFolder folder =
					JournalFolderServiceUtil.moveFolderToTrash(deleteFolderId);

				trashedModels.add(folder);
			}
			else {
				JournalFolderServiceUtil.deleteFolder(deleteFolderId);
			}
		}

		String[] deleteArticleIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "articleIds"));

		for (String deleteArticleId : deleteArticleIds) {
			if (moveToTrash) {
				JournalArticle article =
					JournalArticleServiceUtil.moveArticleToTrash(
						themeDisplay.getScopeGroupId(),
						HtmlUtil.unescape(deleteArticleId));

				trashedModels.add(article);
			}
			else {
				ActionUtil.deleteArticle(
					actionRequest, HtmlUtil.unescape(deleteArticleId));
			}
		}

		if (moveToTrash && !trashedModels.isEmpty()) {
			TrashUtil.addTrashSessionMessages(actionRequest, trashedModels);

			hideDefaultSuccessMessage(actionRequest);
		}

		sendEditEntryRedirect(actionRequest, actionResponse);
	}

	protected void doDeleteFolders(
			ActionRequest actionRequest, ActionResponse actionResponse,
			boolean moveToTrash)
		throws Exception {

		long[] deleteFolderIds = null;

		long folderId = ParamUtil.getLong(actionRequest, "folderId");

		if (folderId > 0) {
			deleteFolderIds = new long[] {folderId};
		}
		else {
			deleteFolderIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "folderIds"), 0L);
		}

		List<TrashedModel> trashedModels = new ArrayList<>();

		for (long deleteFolderId : deleteFolderIds) {
			if (moveToTrash) {
				JournalFolder folder =
					JournalFolderServiceUtil.moveFolderToTrash(deleteFolderId);

				trashedModels.add(folder);
			}
			else {
				JournalFolderServiceUtil.deleteFolder(deleteFolderId);
			}
		}

		if (moveToTrash && !trashedModels.isEmpty()) {
			TrashUtil.addTrashSessionMessages(actionRequest, trashedModels);

			hideDefaultSuccessMessage(actionRequest);
		}
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (SessionErrors.contains(
				renderRequest, NoSuchArticleException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, NoSuchFeedException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, NoSuchFolderException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, NoSuchStructureException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, NoSuchTemplateException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, PrincipalException.class.getName())) {

			include(
				"/portlet/journal/html/error.jsp", renderRequest,
				renderResponse);
		}
		else {
			super.doDispatch(renderRequest, renderResponse);
		}
	}

	protected String getSaveAndContinueRedirect(
			ActionRequest actionRequest, JournalArticle article,
			String redirect)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String referringPortletResource = ParamUtil.getString(
			actionRequest, "referringPortletResource");

		PortletURLImpl portletURL = new PortletURLImpl(
			actionRequest, PortletKeys.JOURNAL, themeDisplay.getPlid(),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcPath", "edit_article.jsp");
		portletURL.setParameter("redirect", redirect, false);
		portletURL.setParameter(
			"referringPortletResource", referringPortletResource, false);
		portletURL.setParameter(
			"resourcePrimKey", String.valueOf(article.getResourcePrimKey()),
			false);
		portletURL.setParameter(
			"groupId", String.valueOf(article.getGroupId()), false);
		portletURL.setParameter(
			"folderId", String.valueOf(article.getFolderId()), false);
		portletURL.setParameter("articleId", article.getArticleId(), false);
		portletURL.setParameter(
			"version", String.valueOf(article.getVersion()), false);
		portletURL.setWindowState(actionRequest.getWindowState());

		return portletURL.toString();
	}

	@Override
	protected boolean isSessionErrorException(Throwable cause) {
		if (cause instanceof ArticleContentException ||
			cause instanceof ArticleContentSizeException ||
			cause instanceof ArticleDisplayDateException ||
			cause instanceof ArticleExpirationDateException ||
			cause instanceof ArticleIdException ||
			cause instanceof ArticleSmallImageNameException ||
			cause instanceof ArticleSmallImageSizeException ||
			cause instanceof ArticleTitleException ||
			cause instanceof ArticleVersionException ||
			cause instanceof AssetCategoryException ||
			cause instanceof AssetTagException ||
			cause instanceof DuplicateArticleIdException ||
			cause instanceof DuplicateFileException ||
			cause instanceof DuplicateFolderNameException ||
			cause instanceof DuplicateFeedIdException ||
			cause instanceof FeedContentFieldException ||
			cause instanceof FeedIdException ||
			cause instanceof FeedNameException ||
			cause instanceof FeedTargetLayoutFriendlyUrlException ||
			cause instanceof FeedTargetPortletIdException ||
			cause instanceof FolderNameException ||
			cause instanceof InvalidDDMStructureException ||
			cause instanceof FileSizeException ||
			cause instanceof LiferayFileItemException ||
			cause instanceof LocaleException ||
			cause instanceof StorageFieldRequiredException ||
			super.isSessionErrorException(cause)) {

			return true;
		}

		return false;
	}

	protected void sendEditArticleRedirect(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		sendEditArticleRedirect(
			actionRequest, actionResponse, null, StringPool.BLANK);
	}

	protected void sendEditArticleRedirect(
			ActionRequest actionRequest, ActionResponse actionResponse,
			JournalArticle article, String oldUrlTitle)
		throws Exception {

		String actionName = ParamUtil.getString(
			actionRequest, ActionRequest.ACTION_NAME);

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		int workflowAction = ParamUtil.getInteger(
			actionRequest, "workflowAction", WorkflowConstants.ACTION_PUBLISH);

		String portletId = HttpUtil.getParameter(redirect, "p_p_id", false);

		String namespace = PortalUtil.getPortletNamespace(portletId);

		if (Validator.isNotNull(oldUrlTitle)) {
			String oldRedirectParam = namespace + "redirect";

			String oldRedirect = HttpUtil.getParameter(
				redirect, oldRedirectParam, false);

			if (Validator.isNotNull(oldRedirect)) {
				String newRedirect = HttpUtil.decodeURL(oldRedirect);

				newRedirect = StringUtil.replace(
					newRedirect, oldUrlTitle, article.getUrlTitle());
				newRedirect = StringUtil.replace(
					newRedirect, oldRedirectParam, "redirect");

				redirect = StringUtil.replace(
					redirect, oldRedirect, newRedirect);
			}
		}

		if (actionName.equals("deleteArticle") &&
			!ActionUtil.hasArticle(actionRequest)) {

			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

			PortletURL portletURL = PortletURLFactoryUtil.create(
				actionRequest, portletDisplay.getPortletName(),
				themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);

			redirect = portletURL.toString();
		}

		if ((article != null) &&
			(workflowAction == WorkflowConstants.ACTION_SAVE_DRAFT)) {

			redirect = getSaveAndContinueRedirect(
				actionRequest, article, redirect);

			if (actionName.equals("previewArticle")) {
				SessionMessages.add(actionRequest, "previewRequested");

				hideDefaultSuccessMessage(actionRequest);
			}
		}
		else {
			WindowState windowState = actionRequest.getWindowState();

			if (windowState.equals(LiferayWindowState.POP_UP)) {
				redirect = PortalUtil.escapeRedirect(redirect);

				if (Validator.isNotNull(redirect)) {
					if (actionName.equals("addArticle") && (article != null)) {
						redirect = HttpUtil.addParameter(
							redirect, namespace + "className",
							JournalArticle.class.getName());
						redirect = HttpUtil.addParameter(
							redirect, namespace + "classPK",
							JournalArticleAssetRenderer.getClassPK(article));
					}

					actionResponse.sendRedirect(redirect);
				}
			}
		}

		actionRequest.setAttribute(WebKeys.REDIRECT, redirect);
	}

	protected void sendEditEntryRedirect(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String redirect = PortalUtil.escapeRedirect(
			ParamUtil.getString(actionRequest, "redirect"));

		WindowState windowState = actionRequest.getWindowState();

		if (!windowState.equals(LiferayWindowState.POP_UP)) {
			sendRedirect(actionRequest, actionResponse);
		}
		else if (Validator.isNotNull(redirect)) {
			actionResponse.sendRedirect(redirect);
		}
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

	private static final Log _log = LogFactoryUtil.getLog(JournalPortlet.class);

}