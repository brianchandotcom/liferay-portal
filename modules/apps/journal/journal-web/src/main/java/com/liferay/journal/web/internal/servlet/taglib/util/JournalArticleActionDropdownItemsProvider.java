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

package com.liferay.journal.web.internal.servlet.taglib.util;

import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalServiceUtil;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.web.asset.JournalArticleAssetRenderer;
import com.liferay.journal.web.configuration.JournalWebConfiguration;
import com.liferay.journal.web.internal.portlet.JournalPortlet;
import com.liferay.journal.web.internal.security.permission.resource.JournalArticlePermission;
import com.liferay.journal.web.internal.security.permission.resource.JournalFolderPermission;
import com.liferay.journal.web.util.JournalUtil;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.staging.StagingGroupHelperUtil;
import com.liferay.taglib.security.PermissionsURLTag;
import com.liferay.trash.TrashHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class JournalArticleActionDropdownItemsProvider {

	public JournalArticleActionDropdownItemsProvider(
		JournalArticle article, LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		TrashHelper trashHelper) {

		_article = article;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_trashHelper = trashHelper;

		_journalWebConfiguration =
			(JournalWebConfiguration)_liferayPortletRequest.getAttribute(
				JournalWebConfiguration.class.getName());
		_httpServletRequest = PortalUtil.getHttpServletRequest(
			liferayPortletRequest);
		_themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() throws Exception {
		return new DropdownItemList() {
			{
				if (JournalArticlePermission.contains(
						_themeDisplay.getPermissionChecker(), _article,
						ActionKeys.UPDATE)) {

					add(_getEditArticleActionUnsafeConsumer());
					add(_getMoveArticleActionUnsafeConsumer());
				}

				if (JournalArticlePermission.contains(
						_themeDisplay.getPermissionChecker(), _article,
						ActionKeys.PERMISSIONS)) {

					add(_getPermissionsArticleActionUnsafeConsumer());
				}

				if (JournalArticlePermission.contains(
						_themeDisplay.getPermissionChecker(), _article,
						ActionKeys.VIEW)) {

					if (JournalArticlePermission.contains(
							_themeDisplay.getPermissionChecker(), _article,
							ActionKeys.SUBSCRIBE)) {

						add(_getSubscribeArticleActionUnsafeConsumer());
					}

					Consumer<DropdownItem> viewContentArticleAction =
						_getViewContentArticleActionUnsafeConsumer();

					if (viewContentArticleAction != null) {
						add(viewContentArticleAction);
					}

					Consumer<DropdownItem> previewContentArticleAction =
						_getPreviewArticleActionUnsafeConsumer();

					if (previewContentArticleAction != null) {
						add(previewContentArticleAction);
					}

					if (JournalArticlePermission.contains(
							_themeDisplay.getPermissionChecker(), _article,
							ActionKeys.UPDATE)) {

						add(_getViewHistoryArticleActionUnsafeConsumer());
					}

					String[] availableLanguageIds =
						_article.getAvailableLanguageIds();

					if (availableLanguageIds.length > 1) {
						add(
							_getDeleteArticleTranslationsActionUnsafeConsumer());
					}
				}

				add(_getViewUsagesArticleActionUnsafeConsumer());

				if (JournalFolderPermission.contains(
						_themeDisplay.getPermissionChecker(),
						_themeDisplay.getScopeGroupId(), _article.getFolderId(),
						ActionKeys.ADD_ARTICLE)) {

					add(_getCopyArticleActionUnsafeConsumer());
				}

				if (JournalArticlePermission.contains(
						_themeDisplay.getPermissionChecker(), _article,
						ActionKeys.EXPIRE) &&
					_article.hasApprovedVersion()) {

					add(
						_getExpireArticleActionConsumer(
							_article.getArticleId()));
				}

				if (JournalArticlePermission.contains(
						_themeDisplay.getPermissionChecker(), _article,
						ActionKeys.DELETE)) {

					if (_trashHelper.isTrashEnabled(
							_themeDisplay.getScopeGroupId())) {

						add(_getMoveToTrashArticleActionUnsafeConsumer());
					}
					else {
						add(_getDeleteArticleAction(_article.getArticleId()));
					}
				}

				Group group = _themeDisplay.getScopeGroup();

				if (_isShowPublishArticleAction() && !group.isLayout()) {
					add(_getPublishToLiveArticleActionUnsafeConsumer());
				}
			}
		};
	}

	public List<DropdownItem> getArticleHistoryActionDropdownItems()
		throws Exception {

		return new DropdownItemList() {
			{
				if (JournalArticlePermission.contains(
						_themeDisplay.getPermissionChecker(), _article,
						ActionKeys.VIEW)) {

					Consumer<DropdownItem> previewContentArticleAction =
						_getPreviewArticleActionUnsafeConsumer();

					if (previewContentArticleAction != null) {
						add(previewContentArticleAction);
					}
				}

				if (JournalFolderPermission.contains(
						_themeDisplay.getPermissionChecker(),
						_themeDisplay.getScopeGroupId(), _article.getFolderId(),
						ActionKeys.ADD_ARTICLE)) {

					add(_getAutoCopyArticleActionUnsafeConsumer());
				}

				String articleId =
					_article.getArticleId() + JournalPortlet.VERSION_SEPARATOR +
						_article.getVersion();

				if (JournalArticlePermission.contains(
						_themeDisplay.getPermissionChecker(), _article,
						ActionKeys.EXPIRE) &&
					(_article.getStatus() ==
						WorkflowConstants.STATUS_APPROVED)) {

					add(
						_getExpireArticleActionConsumer(
							articleId, _themeDisplay.getURLCurrent()));
				}

				add(_getCompareArticleVersionsActionUnsafeConsumer());

				if (JournalArticlePermission.contains(
						_themeDisplay.getPermissionChecker(), _article,
						ActionKeys.DELETE)) {

					add(
						_getDeleteArticleAction(
							articleId, _themeDisplay.getURLCurrent()));
				}
			}
		};
	}

	public String getArticleTranslationActionDropdownItems() throws Exception {
		if (JournalArticlePermission.contains(
				_themeDisplay.getPermissionChecker(), _article,
				ActionKeys.VIEW)) {

			PortletURL selectArticleTranslationsURL =
				_liferayPortletResponse.createRenderURL();

			selectArticleTranslationsURL.setParameter(
				"mvcPath", "/select_article_translations.jsp");
			selectArticleTranslationsURL.setParameter(
				"redirect", _getRedirect());
			selectArticleTranslationsURL.setParameter(
				"backURL", _getRedirect());
			selectArticleTranslationsURL.setParameter(
				"articleId", _article.getArticleId());

			PortletURL deleteArticleTranslationsURL =
				_liferayPortletResponse.createActionURL();

			deleteArticleTranslationsURL.setParameter(
				ActionRequest.ACTION_NAME,
				"/journal/delete_article_translations");
			deleteArticleTranslationsURL.setParameter(
				"articleId", _article.getArticleId());
			deleteArticleTranslationsURL.setParameter(
				"redirect", selectArticleTranslationsURL.toString());

			return deleteArticleTranslationsURL.toString();
		}

		return StringPool.BLANK;
	}

	public List<DropdownItem> getArticleVersionActionDropdownItems()
		throws Exception {

		DropdownItemList dropdownItems = new DropdownItemList() {
			{
				if (JournalArticlePermission.contains(
						_themeDisplay.getPermissionChecker(), _article,
						ActionKeys.UPDATE)) {

					add(_getEditArticleActionUnsafeConsumer());
				}
			}
		};

		dropdownItems.addAll(getArticleHistoryActionDropdownItems());

		return dropdownItems;
	}

	public String getPreviewURL(JournalArticle article) throws Exception {
		AssetDisplayPageEntry assetDisplayPageEntry =
			AssetDisplayPageEntryLocalServiceUtil.fetchAssetDisplayPageEntry(
				_themeDisplay.getScopeGroupId(),
				PortalUtil.getClassNameId(JournalArticle.class),
				article.getResourcePrimKey());

		if ((assetDisplayPageEntry != null) &&
			(assetDisplayPageEntry.getLayoutPageTemplateEntryId() > 0)) {

			AssetRendererFactory assetRendererFactory =
				AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClass(
					JournalArticle.class);

			AssetEntry assetEntry = assetRendererFactory.getAssetEntry(
				JournalArticle.class.getName(), article.getResourcePrimKey());

			StringBundler sb = new StringBundler(4);

			sb.append(
				PortalUtil.getGroupFriendlyURL(
					_themeDisplay.getLayoutSet(), _themeDisplay));
			sb.append("/a/");
			sb.append(assetEntry.getEntryId());
			sb.append("?p_p_state=pop_up");

			return sb.toString();
		}

		if (Validator.isNull(article.getDDMTemplateKey())) {
			return StringPool.BLANK;
		}

		PortletURL portletURL = _liferayPortletResponse.createLiferayPortletURL(
			JournalUtil.getPreviewPlid(article, _themeDisplay),
			JournalPortletKeys.JOURNAL, PortletRequest.RENDER_PHASE);

		Map<String, String[]> parameters = new HashMap<>();

		parameters.put("articleId", new String[] {article.getArticleId()});
		parameters.put(
			"groupId", new String[] {String.valueOf(article.getGroupId())});
		parameters.put(
			"mvcPath", new String[] {"/preview_article_content.jsp"});
		parameters.put(
			"version", new String[] {String.valueOf(article.getVersion())});

		portletURL.setParameters(parameters);

		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return portletURL.toString();
	}

	private Consumer<DropdownItem> _getAutoCopyArticleActionUnsafeConsumer() {
		return dropdownItem -> {
			dropdownItem.setHref(
				_liferayPortletResponse.createRenderURL(), "mvcPath",
				"/copy_article.jsp", "redirect", _getRedirect(), "groupId",
				_article.getGroupId(), "oldArticleId", _article.getArticleId(),
				"version", _article.getVersion());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "copy"));
		};
	}

	private Consumer<DropdownItem>
			_getCompareArticleVersionsActionUnsafeConsumer()
		throws Exception {

		PortletURL compareVersionsURL =
			_liferayPortletResponse.createRenderURL();

		compareVersionsURL.setParameter("mvcPath", "/select_version.jsp");
		compareVersionsURL.setParameter(
			"groupId", String.valueOf(_article.getGroupId()));
		compareVersionsURL.setParameter("articleId", _article.getArticleId());
		compareVersionsURL.setParameter(
			"sourceVersion", String.valueOf(_article.getVersion()));
		compareVersionsURL.setWindowState(LiferayWindowState.POP_UP);

		PortletURL redirectURL = _liferayPortletResponse.createRenderURL();

		redirectURL.setParameter(
			"mvcRenderCommandName", "/journal/compare_versions");
		redirectURL.setParameter("redirect", _getRedirect());
		redirectURL.setParameter(
			"groupId", String.valueOf(_article.getGroupId()));
		redirectURL.setParameter("articleId", _article.getArticleId());

		return dropdownItem -> {
			dropdownItem.putData("action", "compareVersions");
			dropdownItem.putData(
				"compareVersionsURL", compareVersionsURL.toString());
			dropdownItem.putData("redirectURL", redirectURL.toString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "compare-to"));
		};
	}

	private Consumer<DropdownItem> _getCopyArticleActionUnsafeConsumer() {
		if (_journalWebConfiguration.journalArticleForceAutogenerateId()) {
			PortletURL copyArticleURL =
				_liferayPortletResponse.createActionURL();

			copyArticleURL.setParameter(
				ActionRequest.ACTION_NAME, "copyArticle");

			copyArticleURL.setParameter("redirect", _getRedirect());
			copyArticleURL.setParameter(
				"groupId", String.valueOf(_article.getGroupId()));
			copyArticleURL.setParameter(
				"oldArticleId", _article.getArticleId());
			copyArticleURL.setParameter(
				"version", String.valueOf(_article.getVersion()));
			copyArticleURL.setParameter(
				"autoArticleId", Boolean.TRUE.toString());

			return dropdownItem -> {
				dropdownItem.putData("action", "copyArticle");
				dropdownItem.putData(
					"copyArticleURL", copyArticleURL.toString());
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "copy"));
			};
		}

		return _getAutoCopyArticleActionUnsafeConsumer();
	}

	private Map<Long, LayoutPageTemplateEntry>
		_getDefaultLayoutPageTemplateEntriesMap() {

		if (_defaultLayoutPageTemplateEntriesMap != null) {
			return _defaultLayoutPageTemplateEntriesMap;
		}

		Map<Long, LayoutPageTemplateEntry> layoutPageTemplateEntriesMap =
			new HashMap<>();

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			JournalArticle.class.getName(),
			JournalArticleAssetRenderer.getClassPK(_article));

		List<LayoutPageTemplateEntry> layoutPageTemplateEntries =
			LayoutPageTemplateEntryServiceUtil.getLayoutPageTemplateEntries(
				_themeDisplay.getScopeGroupId(),
				PortalUtil.getClassNameId(JournalArticle.class.getName()),
				assetEntry.getClassTypeId(),
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE);

		for (LayoutPageTemplateEntry layoutPageTemplateEntry :
				layoutPageTemplateEntries) {

			layoutPageTemplateEntriesMap.put(
				layoutPageTemplateEntry.getClassTypeId(),
				layoutPageTemplateEntry);
		}

		_defaultLayoutPageTemplateEntriesMap = layoutPageTemplateEntriesMap;

		return _defaultLayoutPageTemplateEntriesMap;
	}

	private Consumer<DropdownItem> _getDeleteArticleAction(String articleId) {
		return _getDeleteArticleAction(articleId, _getRedirect());
	}

	private Consumer<DropdownItem> _getDeleteArticleAction(
		String articleId, String redirect) {

		PortletURL deleteURL = _liferayPortletResponse.createActionURL();

		deleteURL.setParameter(
			ActionRequest.ACTION_NAME, "/journal/delete_article");

		deleteURL.setParameter("redirect", redirect);
		deleteURL.setParameter(
			"groupId", String.valueOf(_article.getGroupId()));
		deleteURL.setParameter("articleId", articleId);

		return dropdownItem -> {
			dropdownItem.putData("action", "delete");
			dropdownItem.putData("deleteURL", deleteURL.toString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "delete"));
		};
	}

	private Consumer<DropdownItem>
			_getDeleteArticleTranslationsActionUnsafeConsumer()
		throws Exception {

		PortletURL selectArticleTranslationsURL =
			_liferayPortletResponse.createRenderURL();

		selectArticleTranslationsURL.setParameter(
			"mvcPath", "/select_article_translations.jsp");
		selectArticleTranslationsURL.setParameter("redirect", _getRedirect());
		selectArticleTranslationsURL.setParameter("backURL", _getRedirect());
		selectArticleTranslationsURL.setParameter(
			"articleId", _article.getArticleId());

		selectArticleTranslationsURL.setWindowState(LiferayWindowState.POP_UP);

		PortletURL deleteArticleTranslationsURL =
			_liferayPortletResponse.createActionURL();

		deleteArticleTranslationsURL.setParameter(
			ActionRequest.ACTION_NAME, "/journal/delete_article_translations");
		deleteArticleTranslationsURL.setParameter(
			"articleId", _article.getArticleId());

		return dropdownItem -> {
			dropdownItem.setHref(deleteArticleTranslationsURL.toString());
			dropdownItem.putData("action", "deleteArticleTranslations");
			dropdownItem.putData(
				"deleteArticleTranslationsURL",
				deleteArticleTranslationsURL.toString());
			dropdownItem.putData(
				"selectArticleTranslationsURL",
				selectArticleTranslationsURL.toString());
			dropdownItem.putData(
				"title",
				LanguageUtil.get(_httpServletRequest, "delete-translations") +
					StringPool.TRIPLE_PERIOD);

			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "delete-translations") +
					StringPool.TRIPLE_PERIOD);
		};
	}

	private Consumer<DropdownItem> _getEditArticleActionUnsafeConsumer() {
		return dropdownItem -> {
			dropdownItem.setHref(
				_liferayPortletResponse.createRenderURL(), "mvcPath",
				"/edit_article.jsp", "redirect", _getRedirect(),
				"referringPortletResource", _getReferringPortletResource(),
				"groupId", _article.getGroupId(), "folderId",
				_article.getFolderId(), "articleId", _article.getArticleId(),
				"version", _article.getVersion());
			dropdownItem.setIcon("edit");
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "edit"));
		};
	}

	private Consumer<DropdownItem> _getExpireArticleActionConsumer(
		String articleId) {

		return _getExpireArticleActionConsumer(articleId, _getRedirect());
	}

	private Consumer<DropdownItem> _getExpireArticleActionConsumer(
		String articleId, String redirect) {

		PortletURL expireURL = _liferayPortletResponse.createActionURL();

		expireURL.setParameter(
			ActionRequest.ACTION_NAME, "/journal/expire_articles");
		expireURL.setParameter("redirect", redirect);
		expireURL.setParameter(
			"groupId", String.valueOf(_article.getGroupId()));
		expireURL.setParameter("articleId", articleId);

		return dropdownItem -> {
			dropdownItem.putData("action", "expireArticles");
			dropdownItem.putData("expireURL", expireURL.toString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "expire"));
		};
	}

	private Consumer<DropdownItem> _getMoveArticleActionUnsafeConsumer() {
		return dropdownItem -> {
			dropdownItem.setHref(
				_liferayPortletResponse.createRenderURL(), "mvcPath",
				"/move_entries.jsp", "redirect", _getRedirect(),
				"referringPortletResource", _getReferringPortletResource(),
				"rowIdsJournalArticle", _article.getArticleId());
			dropdownItem.setIcon("move");
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "move"));
		};
	}

	private Consumer<DropdownItem>
		_getMoveToTrashArticleActionUnsafeConsumer() {

		PortletURL deleteURL = _liferayPortletResponse.createActionURL();

		deleteURL.setParameter(
			ActionRequest.ACTION_NAME, "/journal/move_to_trash");

		deleteURL.setParameter("redirect", _getRedirect());
		deleteURL.setParameter(
			"groupId", String.valueOf(_article.getGroupId()));
		deleteURL.setParameter("articleId", _article.getArticleId());

		return dropdownItem -> {
			dropdownItem.putData("action", "delete");
			dropdownItem.putData("deleteURL", deleteURL.toString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "move-to-recycle-bin"));
		};
	}

	private Consumer<DropdownItem> _getPermissionsArticleActionUnsafeConsumer()
		throws Exception {

		String permissionsURL = PermissionsURLTag.doTag(
			StringPool.BLANK, JournalArticle.class.getName(),
			HtmlUtil.escape(_article.getTitle(_themeDisplay.getLocale())), null,
			String.valueOf(_article.getResourcePrimKey()),
			LiferayWindowState.POP_UP.toString(), null, _httpServletRequest);

		return dropdownItem -> {
			dropdownItem.putData("action", "permissions");
			dropdownItem.putData("permissionsURL", permissionsURL);
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "permissions"));
		};
	}

	private Consumer<DropdownItem> _getPreviewArticleActionUnsafeConsumer()
		throws Exception {

		String previewURL = getPreviewURL(_article);

		if (Validator.isNull(previewURL)) {
			return null;
		}

		return dropdownItem -> {
			dropdownItem.putData("action", "preview");
			dropdownItem.putData(
				"title",
				HtmlUtil.escape(_article.getTitle(_themeDisplay.getLocale())));
			dropdownItem.putData("previewURL", previewURL);

			String status = "preview";

			if (_article.isDraft()) {
				status = "preview-draft";
			}

			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, status));
		};
	}

	private Consumer<DropdownItem>
		_getPublishToLiveArticleActionUnsafeConsumer() {

		PortletURL publishArticleURL =
			_liferayPortletResponse.createActionURL();

		publishArticleURL.setParameter(
			ActionRequest.ACTION_NAME, "/journal/publish_article");

		publishArticleURL.setParameter("backURL", _getRedirect());
		publishArticleURL.setParameter(
			"groupId", String.valueOf(_article.getGroupId()));
		publishArticleURL.setParameter("articleId", _article.getArticleId());

		return dropdownItem -> {
			dropdownItem.putData("action", "publishToLive");
			dropdownItem.putData(
				"publishArticleURL", publishArticleURL.toString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "publish-to-live"));
		};
	}

	private String _getRedirect() {
		if (_redirect != null) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(
			_liferayPortletRequest, "redirect", _themeDisplay.getURLCurrent());

		return _redirect;
	}

	private String _getReferringPortletResource() {
		if (_referringPortletResource != null) {
			return _referringPortletResource;
		}

		_referringPortletResource = ParamUtil.getString(
			_liferayPortletRequest, "referringPortletResource");

		return _referringPortletResource;
	}

	private Consumer<DropdownItem> _getSubscribeArticleActionUnsafeConsumer() {
		if (JournalUtil.isSubscribedToArticle(
				_article.getCompanyId(), _themeDisplay.getScopeGroupId(),
				_themeDisplay.getUserId(), _article.getResourcePrimKey())) {

			PortletURL unsubscribeArticleURL =
				_liferayPortletResponse.createActionURL();

			unsubscribeArticleURL.setParameter(
				ActionRequest.ACTION_NAME, "/journal/unsubscribe_article");

			unsubscribeArticleURL.setParameter("redirect", _getRedirect());
			unsubscribeArticleURL.setParameter(
				"articleId", String.valueOf(_article.getResourcePrimKey()));

			return dropdownItem -> {
				dropdownItem.putData("action", "unsubscribeArticle");
				dropdownItem.putData(
					"unsubscribeArticleURL", unsubscribeArticleURL.toString());
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "unsubscribe"));
			};
		}

		PortletURL subscribeArticleURL =
			_liferayPortletResponse.createActionURL();

		subscribeArticleURL.setParameter(
			ActionRequest.ACTION_NAME, "/journal/subscribe_article");

		subscribeArticleURL.setParameter("redirect", _getRedirect());
		subscribeArticleURL.setParameter(
			"articleId", String.valueOf(_article.getResourcePrimKey()));

		return dropdownItem -> {
			dropdownItem.putData("action", "subscribeArticle");
			dropdownItem.putData(
				"subscribeArticleURL", subscribeArticleURL.toString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "subscribe"));
		};
	}

	private Consumer<DropdownItem> _getViewContentArticleActionUnsafeConsumer()
		throws Exception {

		String viewContentURL = _getViewContentURL();

		if (Validator.isNull(viewContentURL)) {
			return null;
		}

		return dropdownItem -> {
			dropdownItem.setHref(viewContentURL);
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "view-content"));
		};
	}

	private String _getViewContentURL() throws PortalException {
		if (!_isShowViewContentURL()) {
			return StringPool.BLANK;
		}

		AssetRendererFactory<JournalArticle> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClass(
				JournalArticle.class);

		AssetRenderer<JournalArticle> assetRenderer =
			assetRendererFactory.getAssetRenderer(
				_article.getResourcePrimKey());

		String viewContentURL = StringPool.BLANK;

		try {
			viewContentURL = assetRenderer.getURLViewInContext(
				_liferayPortletRequest, _liferayPortletResponse,
				_getRedirect());
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return viewContentURL;
	}

	private Consumer<DropdownItem>
		_getViewHistoryArticleActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.setHref(
				_liferayPortletResponse.createRenderURL(), "mvcPath",
				"/view_article_history.jsp", "redirect", _getRedirect(),
				"backURL", _getRedirect(), "referringPortletResource",
				_getReferringPortletResource(), "articleId",
				_article.getArticleId());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "view-history"));
		};
	}

	private Consumer<DropdownItem> _getViewUsagesArticleActionUnsafeConsumer() {
		return dropdownItem -> {
			dropdownItem.setHref(
				_liferayPortletResponse.createRenderURL(), "mvcPath",
				"/view_asset_entry_usages.jsp", "redirect", _getRedirect(),
				"groupId", _article.getGroupId(), "articleId",
				_article.getArticleId());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "view-usages"));
		};
	}

	private boolean _isShowPublishAction() {
		PermissionChecker permissionChecker =
			_themeDisplay.getPermissionChecker();

		long scopeGroupId = _themeDisplay.getScopeGroupId();

		StagingGroupHelper stagingGroupHelper =
			StagingGroupHelperUtil.getStagingGroupHelper();

		try {
			if (GroupPermissionUtil.contains(
					permissionChecker, scopeGroupId,
					ActionKeys.EXPORT_IMPORT_PORTLET_INFO) &&
				stagingGroupHelper.isStagingGroup(scopeGroupId) &&
				stagingGroupHelper.isStagedPortlet(
					scopeGroupId, JournalPortletKeys.JOURNAL)) {

				return true;
			}

			return false;
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"An exception occured when checking if the publish " +
						"action should be displayed",
					pe);
			}

			return false;
		}
	}

	private boolean _isShowPublishArticleAction() {
		if (_article == null) {
			return false;
		}

		StagedModelDataHandler<JournalArticle> stagedModelDataHandler =
			(StagedModelDataHandler<JournalArticle>)
				StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(
					JournalArticle.class.getName());

		if (_isShowPublishAction() &&
			ArrayUtil.contains(
				stagedModelDataHandler.getExportableStatuses(),
				_article.getStatus())) {

			return true;
		}

		return false;
	}

	private boolean _isShowViewContentURL() throws PortalException {
		if (_article == null) {
			return false;
		}

		if (!_article.hasApprovedVersion()) {
			return false;
		}

		JournalArticle curArticle = _article;

		if (!_article.isApproved()) {
			curArticle =
				JournalArticleLocalServiceUtil.getPreviousApprovedArticle(
					_article);
		}

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			JournalArticle.class.getName(),
			JournalArticleAssetRenderer.getClassPK(curArticle));

		if (assetEntry == null) {
			return false;
		}

		AssetDisplayPageEntry assetDisplayPageEntry =
			AssetDisplayPageEntryLocalServiceUtil.fetchAssetDisplayPageEntry(
				assetEntry.getGroupId(), assetEntry.getClassNameId(),
				assetEntry.getClassPK());

		if ((assetDisplayPageEntry == null) ||
			(assetDisplayPageEntry.getType() ==
				AssetDisplayPageConstants.TYPE_NONE)) {

			return false;
		}

		if (assetDisplayPageEntry.getType() ==
				AssetDisplayPageConstants.TYPE_SPECIFIC) {

			return true;
		}

		Map<Long, LayoutPageTemplateEntry> defaultLayoutPageTemplateEntriesMap =
			_getDefaultLayoutPageTemplateEntriesMap();

		LayoutPageTemplateEntry defaultLayoutPageTemplateEntry =
			defaultLayoutPageTemplateEntriesMap.get(
				assetEntry.getClassTypeId());

		if (defaultLayoutPageTemplateEntry != null) {
			return true;
		}

		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.fetchStructure(
			_themeDisplay.getSiteGroupId(),
			PortalUtil.getClassNameId(JournalArticle.class),
			_article.getDDMStructureKey(), true);

		if (ddmStructure == null) {
			return false;
		}

		defaultLayoutPageTemplateEntry =
			defaultLayoutPageTemplateEntriesMap.get(
				ddmStructure.getStructureId());

		if (defaultLayoutPageTemplateEntry != null) {
			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleActionDropdownItemsProvider.class);

	private final JournalArticle _article;
	private Map<Long, LayoutPageTemplateEntry>
		_defaultLayoutPageTemplateEntriesMap;
	private final HttpServletRequest _httpServletRequest;
	private final JournalWebConfiguration _journalWebConfiguration;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _redirect;
	private String _referringPortletResource;
	private final ThemeDisplay _themeDisplay;
	private final TrashHelper _trashHelper;

}