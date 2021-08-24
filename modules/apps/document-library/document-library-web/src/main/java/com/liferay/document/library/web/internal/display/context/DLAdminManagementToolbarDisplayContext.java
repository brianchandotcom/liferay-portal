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

package com.liferay.document.library.web.internal.display.context;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyServiceUtil;
import com.liferay.digital.signature.configuration.DigitalSignatureConfiguration;
import com.liferay.digital.signature.configuration.DigitalSignatureConfigurationUtil;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.document.library.web.internal.constants.DLWebKeys;
import com.liferay.document.library.web.internal.display.context.logic.DLPortletInstanceSettingsHelper;
import com.liferay.document.library.web.internal.display.context.util.DLRequestHelper;
import com.liferay.document.library.web.internal.helper.DLTrashHelper;
import com.liferay.document.library.web.internal.settings.DLPortletInstanceSettings;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.toolbar.contributor.PortletToolbarContributor;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.staging.StagingGroupHelperUtil;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class DLAdminManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public DLAdminManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		DLAdminDisplayContext dlAdminDisplayContext,
		DLTrashHelper dlTrashHelper) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			dlAdminDisplayContext.getSearchContainer());

		_httpServletRequest = httpServletRequest;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_dlAdminDisplayContext = dlAdminDisplayContext;
		_dlTrashHelper = dlTrashHelper;

		_currentURLObj = PortletURLUtil.getCurrent(
			liferayPortletRequest, liferayPortletResponse);

		_dlRequestHelper = new DLRequestHelper(_httpServletRequest);

		_dlPortletInstanceSettingsHelper = new DLPortletInstanceSettingsHelper(
			_dlRequestHelper);

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		if (!_dlPortletInstanceSettingsHelper.isShowActions()) {
			return null;
		}

		DigitalSignatureConfiguration digitalSignatureConfiguration =
			DigitalSignatureConfigurationUtil.getDigitalSignatureConfiguration(
				_themeDisplay.getCompanyId(), _themeDisplay.getSiteGroupId());
		boolean enableOnBulk = _isEnableOnBulk();
		boolean stagedActions = _isStagedActions();
		User user = _themeDisplay.getUser();

		return DropdownItemListBuilder.add(
			() -> digitalSignatureConfiguration.enabled() && stagedActions,
			dropdownItem -> {
				dropdownItem.putData("action", "collectDigitalSignature");
				dropdownItem.setIcon("signature");
				dropdownItem.setLabel(
					LanguageUtil.get(
						_httpServletRequest, "collect-digital-signature"));
				dropdownItem.setQuickAction(true);
			}
		).add(
			() -> stagedActions,
			dropdownItem -> {
				dropdownItem.putData("action", "download");
				dropdownItem.setIcon("download");
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "download"));
				dropdownItem.setQuickAction(true);
			}
		).add(
			() -> stagedActions && !user.isDefaultUser(),
			dropdownItem -> {
				dropdownItem.putData("action", "move");
				dropdownItem.setIcon("move-folder");
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "move"));
				dropdownItem.setQuickAction(true);
			}
		).add(
			() -> stagedActions && !user.isDefaultUser(),
			dropdownItem -> {
				dropdownItem.putData("action", "editTags");

				if (enableOnBulk) {
					dropdownItem.putData(
						"enableOnBulk", Boolean.TRUE.toString());
				}

				dropdownItem.setIcon("tag");
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "edit-tags"));
				dropdownItem.setQuickAction(true);
			}
		).add(
			() ->
				stagedActions && !user.isDefaultUser() &&
				_hasValidAssetVocabularies(_themeDisplay.getScopeGroupId()),
			dropdownItem -> {
				dropdownItem.putData("action", "editCategories");

				if (enableOnBulk) {
					dropdownItem.putData(
						"enableOnBulk", Boolean.TRUE.toString());
				}

				dropdownItem.setIcon("categories");
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "edit-categories"));
				dropdownItem.setQuickAction(true);
			}
		).add(
			() -> !user.isDefaultUser(),
			dropdownItem -> {
				dropdownItem.putData("action", "deleteEntries");

				Group scopeGroup = _themeDisplay.getScopeGroup();

				if (_dlTrashHelper.isTrashEnabled(
						scopeGroup.getGroupId(), _getRepositoryId())) {

					dropdownItem.setIcon("trash");
					dropdownItem.setLabel(
						LanguageUtil.get(
							_httpServletRequest, "move-to-recycle-bin"));
				}
				else {
					dropdownItem.setIcon("times-circle");
					dropdownItem.setLabel(
						LanguageUtil.get(_httpServletRequest, "delete"));
				}

				dropdownItem.setQuickAction(true);
			}
		).add(
			() -> stagedActions && !user.isDefaultUser(),
			dropdownItem -> {
				dropdownItem.putData("action", "checkin");
				dropdownItem.setIcon("unlock");
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "checkin"));
				dropdownItem.setQuickAction(false);
			}
		).add(
			() -> stagedActions && !user.isDefaultUser(),
			dropdownItem -> {
				dropdownItem.putData("action", "checkout");
				dropdownItem.setIcon("lock");
				dropdownItem.setLabel(
					LanguageUtil.get(
						_httpServletRequest, "checkout[document]"));
				dropdownItem.setQuickAction(false);
			}
		).build();
	}

	@Override
	public String getClearResultsURL() {
		return PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setMVCRenderCommandName(
			"/document_library/view"
		).setParameter(
			"folderId", _getFolderId()
		).buildString();
	}

	@Override
	public String getComponentId() {
		return _liferayPortletResponse.getNamespace() +
			"entriesManagementToolbar";
	}

	@Override
	public CreationMenu getCreationMenu() {
		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		String rootPortletId = portletDisplay.getRootPortletId();

		if (rootPortletId.equals(DLPortletKeys.MEDIA_GALLERY_DISPLAY)) {
			return null;
		}

		PortletToolbarContributor dlPortletToolbarContributor =
			(PortletToolbarContributor)_httpServletRequest.getAttribute(
				DLWebKeys.DOCUMENT_LIBRARY_PORTLET_TOOLBAR_CONTRIBUTOR);

		List<Menu> menus = dlPortletToolbarContributor.getPortletTitleMenus(
			_liferayPortletRequest, _liferayPortletResponse);

		if (menus.isEmpty()) {
			return null;
		}

		CreationMenu creationMenu = new CreationMenu();

		creationMenu.setItemsIconAlignment("left");

		for (Menu menu : menus) {
			List<URLMenuItem> urlMenuItems =
				(List<URLMenuItem>)(List<?>)menu.getMenuItems();

			for (URLMenuItem urlMenuItem : urlMenuItems) {
				creationMenu.addDropdownItem(
					dropdownItem -> {
						dropdownItem.setData(urlMenuItem.getData());
						dropdownItem.setHref(urlMenuItem.getURL());
						dropdownItem.setIcon(urlMenuItem.getIcon());
						dropdownItem.setLabel(urlMenuItem.getLabel());
						dropdownItem.setSeparator(urlMenuItem.hasSeparator());
					});
			}
		}

		return creationMenu;
	}

	@Override
	public String getDefaultEventHandler() {
		return liferayPortletResponse.getNamespace() + "DocumentLibrary";
	}

	@Override
	public List<DropdownItem> getFilterDropdownItems() {
		if (_isSearch()) {
			return null;
		}

		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					_getFilterNavigationDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(
						_httpServletRequest, "filter-by-navigation"));
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(_getOrderByDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "order-by"));
			}
		).build();
	}

	@Override
	public List<LabelItem> getFilterLabelItems() {
		long fileEntryTypeId = _getFileEntryTypeId();

		return LabelItemListBuilder.add(
			() -> fileEntryTypeId != -1,
			labelItem -> {
				labelItem.putData(
					"removeLabelURL",
					PortletURLBuilder.create(
						PortletURLUtil.clone(
							_currentURLObj, _liferayPortletResponse)
					).setParameter(
						"fileEntryTypeId", (String)null
					).buildString());

				labelItem.setCloseable(true);

				String fileEntryTypeName = LanguageUtil.get(
					_httpServletRequest, "basic-document");

				if (fileEntryTypeId !=
						DLFileEntryTypeConstants.
							FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT) {

					DLFileEntryType fileEntryType =
						DLFileEntryTypeLocalServiceUtil.getFileEntryType(
							fileEntryTypeId);

					fileEntryTypeName = fileEntryType.getName(
						_httpServletRequest.getLocale());
				}

				String label = String.format(
					"%s: %s",
					LanguageUtil.get(_httpServletRequest, "document-type"),
					HtmlUtil.escape(fileEntryTypeName));

				labelItem.setLabel(label);
			}
		).add(
			() -> Objects.equals(_getNavigation(), "mine"),
			labelItem -> {
				labelItem.putData(
					"removeLabelURL",
					PortletURLBuilder.create(
						PortletURLUtil.clone(
							_currentURLObj, _liferayPortletResponse)
					).setNavigation(
						(String)null
					).buildString());

				labelItem.setCloseable(true);

				User user = _themeDisplay.getUser();

				String label = String.format(
					"%s: %s", LanguageUtil.get(_httpServletRequest, "owner"),
					HtmlUtil.escape(user.getFullName()));

				labelItem.setLabel(label);
			}
		).build();
	}

	@Override
	public String getInfoPanelId() {
		return "infoPanelId";
	}

	@Override
	public String getSearchActionURL() {
		long repositoryId = _getRepositoryId();

		PortletURL searchURL = PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setMVCRenderCommandName(
			"/document_library/search"
		).setParameter(
			"repositoryId", repositoryId
		).buildPortletURL();

		long searchRepositoryId = ParamUtil.getLong(
			_httpServletRequest, "searchRepositoryId", repositoryId);

		searchURL.setParameter(
			"searchRepositoryId", String.valueOf(searchRepositoryId));

		long folderId = _getFolderId();

		searchURL.setParameter("folderId", String.valueOf(folderId));

		long searchFolderId = ParamUtil.getLong(
			_httpServletRequest, "searchFolderId", folderId);

		searchURL.setParameter(
			"searchFolderId", String.valueOf(searchFolderId));

		searchURL.setParameter("showSearchInfo", Boolean.TRUE.toString());

		return searchURL.toString();
	}

	@Override
	public String getSearchContainerId() {
		return "entries";
	}

	@Override
	public String getSortingOrder() {
		if (_isSearch()) {
			return null;
		}

		return _dlAdminDisplayContext.getOrderByType();
	}

	@Override
	public String getSortingURL() {
		if (_isSearch()) {
			return null;
		}

		return PortletURLBuilder.create(
			_getCurrentSortingURL()
		).setParameter(
			"orderByType",
			Objects.equals(_getOrderByType(), "asc") ? "desc" : "asc"
		).buildString();
	}

	@Override
	public Boolean getSupportsBulkActions() {
		return true;
	}

	@Override
	public List<ViewTypeItem> getViewTypeItems() {
		if (_isSearch()) {
			return null;
		}

		long folderId = _getFolderId();

		String keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		String mvcRenderCommandName = "/document_library/search";

		if (Validator.isNull(keywords)) {
			if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
				mvcRenderCommandName = "/document_library/view";
			}
			else {
				mvcRenderCommandName = "/document_library/view_folder";
			}
		}

		PortletURL displayStyleURL = PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setMVCRenderCommandName(
			mvcRenderCommandName
		).setNavigation(
			() -> {
				String navigation = ParamUtil.getString(
					_httpServletRequest, "navigation", "home");

				return HtmlUtil.escapeJS(navigation);
			}
		).setParameter(
			"curEntry",
			() -> {
				int curEntry = ParamUtil.getInteger(
					_httpServletRequest, "curEntry");

				if (curEntry > 0) {
					return curEntry;
				}

				return null;
			}
		).setParameter(
			"deltaEntry",
			() -> {
				int deltaEntry = ParamUtil.getInteger(
					_httpServletRequest, "deltaEntry");

				if (deltaEntry > 0) {
					return deltaEntry;
				}

				return null;
			}
		).setParameter(
			"fileEntryTypeId",
			() -> {
				long fileEntryTypeId = _getFileEntryTypeId();

				if (fileEntryTypeId != -1) {
					return fileEntryTypeId;
				}

				return null;
			}
		).setParameter(
			"folderId", folderId
		).buildPortletURL();

		return new ViewTypeItemList(displayStyleURL, _getDisplayStyle()) {
			{
				String[] displayViews = _getDisplayViews();

				for (String displayView : displayViews) {
					if (displayView.equals("icon")) {
						addCardViewTypeItem();
					}
					else if (displayView.equals("descriptive")) {
						addListViewTypeItem();
					}
					else if (displayView.equals("list")) {
						addTableViewTypeItem();
					}
				}
			}
		};
	}

	@Override
	public Boolean isDisabled() {
		try {
			int count =
				DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(
					_getRepositoryId(), _getFolderId(),
					WorkflowConstants.STATUS_ANY, true);

			if (count <= 0) {
				return true;
			}

			return false;
		}
		catch (PortalException portalException) {
			throw new SystemException(portalException);
		}
	}

	@Override
	public Boolean isShowInfoButton() {
		return true;
	}

	@Override
	public Boolean isShowSearch() {
		if (_dlPortletInstanceSettingsHelper.isShowSearch() &&
			super.isShowSearch()) {

			return true;
		}

		return false;
	}

	private PortletURL _getCurrentSortingURL() {
		int deltaEntry = ParamUtil.getInteger(
			_httpServletRequest, "deltaEntry");

		PortletURL sortingURL = _liferayPortletResponse.createRenderURL();

		long folderId = _getFolderId();

		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			sortingURL.setParameter(
				"mvcRenderCommandName", "/document_library/view");
		}
		else {
			sortingURL.setParameter(
				"mvcRenderCommandName", "/document_library/view_folder");
		}

		sortingURL.setParameter("navigation", _getNavigation());

		if (deltaEntry > 0) {
			sortingURL.setParameter("deltaEntry", String.valueOf(deltaEntry));
		}

		sortingURL.setParameter("folderId", String.valueOf(folderId));
		sortingURL.setParameter(
			"fileEntryTypeId", String.valueOf(_getFileEntryTypeId()));

		return sortingURL;
	}

	private String _getDisplayStyle() {
		return _dlAdminDisplayContext.getDisplayStyle();
	}

	private String[] _getDisplayViews() {
		DLPortletInstanceSettings dlPortletInstanceSettings =
			_dlRequestHelper.getDLPortletInstanceSettings();

		return dlPortletInstanceSettings.getDisplayViews();
	}

	private long _getFileEntryTypeId() {
		return ParamUtil.getLong(_httpServletRequest, "fileEntryTypeId", -1);
	}

	private List<DropdownItem> _getFilterNavigationDropdownItems() {
		long fileEntryTypeId = _getFileEntryTypeId();
		String navigation = ParamUtil.getString(
			_httpServletRequest, "navigation", "home");

		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setActive(
					navigation.equals("home") && (fileEntryTypeId == -1));

				dropdownItem.setHref(
					PortletURLBuilder.create(
						PortletURLUtil.clone(
							_currentURLObj, _liferayPortletResponse)
					).setMVCRenderCommandName(
						"/document_library/view"
					).setNavigation(
						"home"
					).setParameter(
						"browseBy", (String)null
					).setParameter(
						"fileEntryTypeId", (String)null
					).buildPortletURL());

				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "all"));
			}
		).add(
			dropdownItem -> {
				dropdownItem.setActive(navigation.equals("recent"));

				dropdownItem.setHref(
					PortletURLBuilder.create(
						PortletURLUtil.clone(
							_currentURLObj, _liferayPortletResponse)
					).setMVCRenderCommandName(
						"/document_library/view"
					).setNavigation(
						"recent"
					).buildPortletURL());

				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "recent"));
			}
		).add(
			_themeDisplay::isSignedIn,
			dropdownItem -> {
				dropdownItem.setActive(navigation.equals("mine"));

				dropdownItem.setHref(
					PortletURLBuilder.create(
						PortletURLUtil.clone(
							_currentURLObj, _liferayPortletResponse)
					).setMVCRenderCommandName(
						"/document_library/view"
					).setNavigation(
						"mine"
					).buildPortletURL());

				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "mine"));
			}
		).add(
			dropdownItem -> {
				dropdownItem.setActive(fileEntryTypeId != -1);

				dropdownItem.putData("action", "openDocumentTypesSelector");

				String label = LanguageUtil.get(
					_httpServletRequest, "document-type");

				if (fileEntryTypeId != -1) {
					String fileEntryTypeName = LanguageUtil.get(
						_httpServletRequest, "basic-document");

					if (fileEntryTypeId !=
							DLFileEntryTypeConstants.
								FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT) {

						DLFileEntryType fileEntryType =
							DLFileEntryTypeLocalServiceUtil.getFileEntryType(
								fileEntryTypeId);

						fileEntryTypeName = fileEntryType.getName(
							_httpServletRequest.getLocale());
					}

					label = String.format("%s: %s", label, fileEntryTypeName);
				}

				dropdownItem.setLabel(label);
			}
		).build();
	}

	private long _getFolderId() {
		if (_isSearch()) {
			return ParamUtil.getLong(_httpServletRequest, "folderId");
		}

		return _dlAdminDisplayContext.getFolderId();
	}

	private String _getNavigation() {
		return ParamUtil.getString(_httpServletRequest, "navigation", "home");
	}

	private String _getOrderByCol() {
		return _dlAdminDisplayContext.getOrderByCol();
	}

	private List<DropdownItem> _getOrderByDropdownItems() {
		Map<String, String> orderColumns = HashMapBuilder.put(
			"creationDate", "create-date"
		).put(
			"downloads",
			() -> {
				if (_getFileEntryTypeId() == -1) {
					return "downloads";
				}

				return null;
			}
		).put(
			"modifiedDate", "modified-date"
		).put(
			"size", "size"
		).put(
			"title", "name"
		).build();

		return new DropdownItemList() {
			{
				for (Map.Entry<String, String> orderByColEntry :
						orderColumns.entrySet()) {

					String orderByCol = orderByColEntry.getKey();

					add(
						dropdownItem -> {
							dropdownItem.setActive(
								orderByCol.equals(_getOrderByCol()));
							dropdownItem.setHref(
								_getCurrentSortingURL(), "orderByCol",
								orderByCol);
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest,
									orderByColEntry.getValue()));
						});
				}
			}
		};
	}

	private String _getOrderByType() {
		return _dlAdminDisplayContext.getOrderByType();
	}

	private long _getRepositoryId() {
		return _dlAdminDisplayContext.getRepositoryId();
	}

	private boolean _hasValidAssetVocabularies(long scopeGroupId)
		throws PortalException {

		if (_hasValidAssetVocabularies != null) {
			return _hasValidAssetVocabularies;
		}

		List<AssetVocabulary> assetVocabularies =
			AssetVocabularyServiceUtil.getGroupVocabularies(
				PortalUtil.getCurrentAndAncestorSiteGroupIds(scopeGroupId));

		Stream<AssetVocabulary> stream = assetVocabularies.stream();

		_hasValidAssetVocabularies = stream.anyMatch(
			assetVocabulary -> {
				if (!assetVocabulary.isAssociatedToClassNameId(
						ClassNameLocalServiceUtil.getClassNameId(
							DLFileEntry.class.getName()))) {

					return false;
				}

				int count =
					AssetCategoryServiceUtil.getVocabularyCategoriesCount(
						assetVocabulary.getGroupId(),
						assetVocabulary.getVocabularyId());

				if (count > 0) {
					return true;
				}

				return false;
			});

		return _hasValidAssetVocabularies;
	}

	private boolean _hasWorkflowDefinitionLink(
		long folderId, long fileEntryTypeId) {

		return DLUtil.hasWorkflowDefinitionLink(
			_themeDisplay.getCompanyId(), _themeDisplay.getScopeGroupId(),
			folderId, fileEntryTypeId);
	}

	private boolean _isEnableOnBulk() {
		long folderId = ParamUtil.getLong(_httpServletRequest, "folderId");

		if (_hasWorkflowDefinitionLink(
				folderId, DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL)) {

			return false;
		}

		return true;
	}

	private boolean _isSearch() {
		return _dlAdminDisplayContext.isSearch();
	}

	private boolean _isStagedActions() {
		Group scopeGroup = _themeDisplay.getScopeGroup();
		StagingGroupHelper stagingGroupHelper =
			StagingGroupHelperUtil.getStagingGroupHelper();

		if (!stagingGroupHelper.isLiveGroup(scopeGroup) ||
			!stagingGroupHelper.isStagedPortlet(
				scopeGroup, DLPortletKeys.DOCUMENT_LIBRARY)) {

			return true;
		}

		return false;
	}

	private final PortletURL _currentURLObj;
	private final DLAdminDisplayContext _dlAdminDisplayContext;
	private final DLPortletInstanceSettingsHelper
		_dlPortletInstanceSettingsHelper;
	private final DLRequestHelper _dlRequestHelper;
	private final DLTrashHelper _dlTrashHelper;
	private Boolean _hasValidAssetVocabularies;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final ThemeDisplay _themeDisplay;

}