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

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.asset.display.page.util.AssetDisplayPageHelper;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.web.asset.model.JournalArticleAssetRenderer;
import com.liferay.journal.web.configuration.JournalWebConfiguration;
import com.liferay.journal.web.internal.portlet.JournalPortlet;
import com.liferay.journal.web.internal.security.permission.resource.JournalArticlePermission;
import com.liferay.journal.web.internal.security.permission.resource.JournalFolderPermission;
import com.liferay.journal.web.util.JournalUtil;
import com.liferay.petra.function.UnsafeConsumer;
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
import com.liferay.portal.kernel.util.HttpUtil;
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
		AssetDisplayPageFriendlyURLProvider assetDisplayPageFriendlyURLProvider,
		TrashHelper trashHelper) {

		_article = article;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_assetDisplayPageFriendlyURLProvider =
			assetDisplayPageFriendlyURLProvider;
		_trashHelper = trashHelper;

		_journalWebConfiguration =
			(JournalWebConfiguration)_liferayPortletRequest.getAttribute(
				JournalWebConfiguration.class.getName());
		_request = PortalUtil.getHttpServletRequest(liferayPortletRequest);
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

					UnsafeConsumer<DropdownItem, Exception>
						viewContentArticleAction =
							_getViewContentArticleActionUnsafeConsumer();

					if (viewContentArticleAction != null) {
						add(viewContentArticleAction);
					}

					UnsafeConsumer<DropdownItem, Exception>
						previewContentArticleAction =
							_getPreviewArticleActionUnsafeConsumer();

					if (previewContentArticleAction != null) {
						add(previewContentArticleAction);
					}

					if (JournalArticlePermission.contains(
							_themeDisplay.getPermissionChecker(), _article,
							ActionKeys.UPDATE)) {

						add(_getViewHistoryArticleActionUnsafeConsumer());
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

					UnsafeConsumer<DropdownItem, Exception>
						previewContentArticleAction =
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

				if (JournalArticlePermission.contains(
						_themeDisplay.getPermissionChecker(), _article,
						ActionKeys.EXPIRE) &&
					(_article.getStatus() ==
						WorkflowConstants.STATUS_APPROVED)) {

					add(
						_getExpireArticleActionConsumer(
							_article.getArticleId() +
								JournalPortlet.VERSION_SEPARATOR +
									_article.getVersion()));
				}

				add(_getCompareArticleVersionsActionUnsafeConsumer());

				if (JournalArticlePermission.contains(
						_themeDisplay.getPermissionChecker(), _article,
						ActionKeys.DELETE)) {

					add(
						_getDeleteArticleAction(
							_article.getArticleId() +
								JournalPortlet.VERSION_SEPARATOR +
									_article.getVersion()));
				}
			}
		};
	}

	public List<DropdownItem> getArticleVersionActionDropdownItems()
		throws Exception {

		return new DropdownItemList() {
			{
				if (JournalArticlePermission.contains(
						_themeDisplay.getPermissionChecker(), _article,
						ActionKeys.UPDATE)) {

					add(_getEditArticleActionUnsafeConsumer());
				}

				if (JournalArticlePermission.contains(
						_themeDisplay.getPermissionChecker(), _article,
						ActionKeys.VIEW)) {

					add(_getPreviewArticleActionUnsafeConsumer());
				}

				if (JournalFolderPermission.contains(
						_themeDisplay.getPermissionChecker(),
						_themeDisplay.getScopeGroupId(), _article.getFolderId(),
						ActionKeys.ADD_ARTICLE)) {

					add(_getAutoCopyArticleActionUnsafeConsumer());
				}

				if (JournalArticlePermission.contains(
						_themeDisplay.getPermissionChecker(), _article,
						ActionKeys.EXPIRE) &&
					(_article.getStatus() ==
						WorkflowConstants.STATUS_APPROVED)) {

					add(
						_getExpireArticleActionConsumer(
							_article.getArticleId() +
								JournalPortlet.VERSION_SEPARATOR +
									_article.getVersion()));
				}

				add(_getCompareArticleVersionsActionUnsafeConsumer());

				if (JournalArticlePermission.contains(
						_themeDisplay.getPermissionChecker(), _article,
						ActionKeys.DELETE)) {

					add(
						_getDeleteArticleAction(
							_article.getArticleId() +
								JournalPortlet.VERSION_SEPARATOR +
									_article.getVersion()));
				}
			}
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getAutoCopyArticleActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.setHref(
				_liferayPortletResponse.createRenderURL(), "mvcPath",
				"/copy_article.jsp", "redirect", _getRedirect(), "groupId",
				_article.getGroupId(), "oldArticleId", _article.getArticleId(),
				"version", _article.getVersion());
			dropdownItem.setLabel(LanguageUtil.get(_request, "copy"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
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

		redirectURL.setParameter("mvcPath", "/compare_versions.jsp");
		redirectURL.setParameter("redirect", _getRedirect());
		redirectURL.setParameter(
			"groupId", String.valueOf(_article.getGroupId()));
		redirectURL.setParameter("articleId", _article.getArticleId());

		return dropdownItem -> {
			dropdownItem.putData("action", "compareVersions");
			dropdownItem.putData(
				"compareVersionsURL", compareVersionsURL.toString());
			dropdownItem.putData("redirectURL", redirectURL.toString());
			dropdownItem.setLabel(LanguageUtil.get(_request, "compare-to"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getCopyArticleActionUnsafeConsumer() {

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
				dropdownItem.setLabel(LanguageUtil.get(_request, "copy"));
			};
		}

		return _getAutoCopyArticleActionUnsafeConsumer();
	}

	private UnsafeConsumer<DropdownItem, Exception> _getDeleteArticleAction(
		String articleId) {

		PortletURL deleteURL = _liferayPortletResponse.createActionURL();

		deleteURL.setParameter(ActionRequest.ACTION_NAME, "deleteArticle");

		deleteURL.setParameter("redirect", _getRedirect());
		deleteURL.setParameter(
			"groupId", String.valueOf(_article.getGroupId()));
		deleteURL.setParameter("articleId", articleId);

		return dropdownItem -> {
			dropdownItem.putData("action", "delete");
			dropdownItem.putData("deleteURL", deleteURL.toString());
			dropdownItem.setLabel(LanguageUtil.get(_request, "delete"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getEditArticleActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.setHref(
				_liferayPortletResponse.createRenderURL(), "mvcPath",
				"/edit_article.jsp", "redirect", _getRedirect(),
				"referringPortletResource", _getReferringPortletResource(),
				"groupId", _article.getGroupId(), "folderId",
				_article.getFolderId(), "articleId", _article.getArticleId(),
				"version", _article.getVersion());
			dropdownItem.setIcon("edit");
			dropdownItem.setLabel(LanguageUtil.get(_request, "edit"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getExpireArticleActionConsumer(String articleId) {

		PortletURL expireURL = _liferayPortletResponse.createActionURL();

		expireURL.setParameter(ActionRequest.ACTION_NAME, "expireArticles");
		expireURL.setParameter("redirect", _getRedirect());
		expireURL.setParameter(
			"groupId", String.valueOf(_article.getGroupId()));
		expireURL.setParameter("articleId", articleId);

		return dropdownItem -> {
			dropdownItem.putData("action", "expireArticles");
			dropdownItem.putData("expireURL", expireURL.toString());
			dropdownItem.setLabel(LanguageUtil.get(_request, "expire"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getMoveArticleActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.setHref(
				_liferayPortletResponse.createRenderURL(), "mvcPath",
				"/move_entries.jsp", "redirect", _getRedirect(),
				"referringPortletResource", _getReferringPortletResource(),
				"rowIdsJournalArticle", _article.getArticleId());
			dropdownItem.setIcon("move");
			dropdownItem.setLabel(LanguageUtil.get(_request, "move"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getMoveToTrashArticleActionUnsafeConsumer() {

		PortletURL deleteURL = _liferayPortletResponse.createActionURL();

		deleteURL.setParameter(ActionRequest.ACTION_NAME, "moveToTrash");

		deleteURL.setParameter("redirect", _getRedirect());
		deleteURL.setParameter(
			"groupId", String.valueOf(_article.getGroupId()));
		deleteURL.setParameter("articleId", _article.getArticleId());

		return dropdownItem -> {
			dropdownItem.putData("action", "delete");
			dropdownItem.putData("deleteURL", deleteURL.toString());
			dropdownItem.setLabel(
				LanguageUtil.get(_request, "move-to-recycle-bin"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
			_getPermissionsArticleActionUnsafeConsumer()
		throws Exception {

		String permissionsURL = PermissionsURLTag.doTag(
			StringPool.BLANK, JournalArticle.class.getName(),
			HtmlUtil.escape(_article.getTitle(_themeDisplay.getLocale())), null,
			String.valueOf(_article.getResourcePrimKey()),
			LiferayWindowState.POP_UP.toString(), null, _request);

		return dropdownItem -> {
			dropdownItem.putData("action", "permissions");
			dropdownItem.putData("permissionsURL", permissionsURL);
			dropdownItem.setLabel(LanguageUtil.get(_request, "permissions"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
			_getPreviewArticleActionUnsafeConsumer()
		throws Exception {

		String previewURL = _getPreviewURL();

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

			dropdownItem.setLabel(LanguageUtil.get(_request, status));
		};
	}

	private String _getPreviewURL() throws Exception {
		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClass(
				JournalArticle.class);

		AssetEntry assetEntry = assetRendererFactory.getAssetEntry(
			JournalArticle.class.getName(), _article.getResourcePrimKey());

		if (AssetDisplayPageHelper.hasAssetDisplayPage(
				_themeDisplay.getScopeGroupId(), assetEntry)) {

			StringBundler sb = new StringBundler(3);

			sb.append(
				_assetDisplayPageFriendlyURLProvider.getFriendlyURL(
					assetEntry.getClassName(), assetEntry.getClassPK(),
					_themeDisplay));
			sb.append(StringPool.SLASH);
			sb.append(_article.getId());

			return HttpUtil.addParameter(
				sb.toString(), "p_p_state",
				LiferayWindowState.POP_UP.toString());
		}

		if (Validator.isNull(_article.getDDMTemplateKey())) {
			return StringPool.BLANK;
		}

		PortletURL portletURL = _liferayPortletResponse.createLiferayPortletURL(
			JournalUtil.getPreviewPlid(_article, _themeDisplay),
			JournalPortletKeys.JOURNAL, PortletRequest.RENDER_PHASE);

		Map<String, String[]> parameters = new HashMap<>();

		parameters.put("articleId", new String[] {_article.getArticleId()});
		parameters.put(
			"groupId", new String[] {String.valueOf(_article.getGroupId())});
		parameters.put(
			"mvcPath", new String[] {"/preview_article_content.jsp"});
		parameters.put(
			"version", new String[] {String.valueOf(_article.getVersion())});

		portletURL.setParameters(parameters);

		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return portletURL.toString();
	}

	private UnsafeConsumer<DropdownItem, Exception>
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
				LanguageUtil.get(_request, "publish-to-live"));
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

	private UnsafeConsumer<DropdownItem, Exception>
		_getSubscribeArticleActionUnsafeConsumer() {

		if (JournalUtil.isSubscribedToArticle(
				_article.getCompanyId(), _themeDisplay.getScopeGroupId(),
				_themeDisplay.getUserId(), _article.getResourcePrimKey())) {

			PortletURL unsubscribeArticleURL =
				_liferayPortletResponse.createActionURL();

			unsubscribeArticleURL.setParameter(
				ActionRequest.ACTION_NAME, "unsubscribeArticle");

			unsubscribeArticleURL.setParameter("redirect", _getRedirect());
			unsubscribeArticleURL.setParameter(
				"articleId", String.valueOf(_article.getResourcePrimKey()));

			return dropdownItem -> {
				dropdownItem.putData("action", "unsubscribeArticle");
				dropdownItem.putData(
					"unsubscribeArticleURL", unsubscribeArticleURL.toString());
				dropdownItem.setLabel(
					LanguageUtil.get(_request, "unsubscribe"));
			};
		}

		PortletURL subscribeArticleURL =
			_liferayPortletResponse.createActionURL();

		subscribeArticleURL.setParameter(
			ActionRequest.ACTION_NAME, "subscribeArticle");

		subscribeArticleURL.setParameter("redirect", _getRedirect());
		subscribeArticleURL.setParameter(
			"articleId", String.valueOf(_article.getResourcePrimKey()));

		return dropdownItem -> {
			dropdownItem.putData("action", "subscribeArticle");
			dropdownItem.putData(
				"subscribeArticleURL", subscribeArticleURL.toString());
			dropdownItem.setLabel(LanguageUtil.get(_request, "subscribe"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
			_getViewContentArticleActionUnsafeConsumer()
		throws Exception {

		String viewContentURL = _getViewContentURL();

		if (Validator.isNull(viewContentURL)) {
			return null;
		}

		return dropdownItem -> {
			dropdownItem.setHref(viewContentURL);
			dropdownItem.setLabel(LanguageUtil.get(_request, "view-content"));
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

	private UnsafeConsumer<DropdownItem, Exception>
		_getViewHistoryArticleActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.setHref(
				_liferayPortletResponse.createRenderURL(), "mvcPath",
				"/view_article_history.jsp", "redirect", _getRedirect(),
				"backURL", _getRedirect(), "referringPortletResource",
				_getReferringPortletResource(), "articleId",
				_article.getArticleId());
			dropdownItem.setLabel(LanguageUtil.get(_request, "view-history"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getViewUsagesArticleActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.setHref(
				_liferayPortletResponse.createRenderURL(), "mvcPath",
				"/view_usages.jsp", "redirect", _getRedirect(), "groupId",
				_article.getGroupId(), "articleId", _article.getArticleId());
			dropdownItem.setLabel(LanguageUtil.get(_request, "view-usages"));
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

		if (AssetDisplayPageHelper.hasAssetDisplayPage(
				_themeDisplay.getScopeGroupId(), assetEntry)) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleActionDropdownItemsProvider.class);

	private final JournalArticle _article;
	private final AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;
	private final JournalWebConfiguration _journalWebConfiguration;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _redirect;
	private String _referringPortletResource;
	private final HttpServletRequest _request;
	private final ThemeDisplay _themeDisplay;
	private final TrashHelper _trashHelper;

}