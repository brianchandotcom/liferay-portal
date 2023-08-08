/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.display.context;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyServiceUtil;
import com.liferay.asset.tags.item.selector.AssetTagsItemSelectorReturnType;
import com.liferay.asset.tags.item.selector.criterion.AssetTagsItemSelectorCriterion;
import com.liferay.digital.signature.configuration.DigitalSignatureConfiguration;
import com.liferay.digital.signature.configuration.DigitalSignatureConfigurationUtil;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.document.library.web.internal.constants.DLWebKeys;
import com.liferay.document.library.web.internal.display.context.helper.DLPortletInstanceSettingsHelper;
import com.liferay.document.library.web.internal.display.context.helper.DLRequestHelper;
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
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.criteria.InfoItemItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.item.selector.criteria.file.criterion.FileExtensionItemSelectorCriterion;
import com.liferay.item.selector.criteria.info.item.criterion.InfoItemItemSelectorCriterion;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.toolbar.contributor.PortletToolbarContributor;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.staging.StagingGroupHelperUtil;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.portlet.PortletException;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class DLAdminManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public DLAdminManagementToolbarDisplayContext(
		DLAdminDisplayContext dlAdminDisplayContext,
		DLTrashHelper dlTrashHelper, HttpServletRequest httpServletRequest,
		ItemSelector itemSelector, LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			dlAdminDisplayContext.getSearchContainer());

		_dlAdminDisplayContext = dlAdminDisplayContext;
		_dlTrashHelper = dlTrashHelper;
		_httpServletRequest = httpServletRequest;
		_itemSelector = itemSelector;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_currentURLObj = PortletURLUtil.getCurrent(
			liferayPortletRequest, liferayPortletResponse);

		_dlRequestHelper = new DLRequestHelper(_httpServletRequest);

		_dlPortletInstanceSettingsHelper = new DLPortletInstanceSettingsHelper(
			_dlRequestHelper);

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
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
			() ->
				stagedActions && !user.isGuestUser() &&
				FeatureFlagManagerUtil.isEnabled("LPS-182512"),
			dropdownItem -> {
				dropdownItem.putData("action", "copy");
				dropdownItem.setIcon("copy");
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "copy-to"));
				dropdownItem.setQuickAction(false);
			}
		).add(
			() -> stagedActions && !user.isGuestUser(),
			dropdownItem -> {
				dropdownItem.putData("action", "move");
				dropdownItem.setIcon("move-folder");
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "move"));
				dropdownItem.setQuickAction(true);
			}
		).add(
			() -> stagedActions && !user.isGuestUser(),
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
				stagedActions && !user.isGuestUser() &&
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
			() -> !user.isGuestUser(),
			dropdownItem -> {
				dropdownItem.putData("action", "deleteEntries");
				dropdownItem.setIcon("trash");
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "delete"));
				dropdownItem.setQuickAction(true);
			}
		).add(
			() -> stagedActions && !user.isGuestUser(),
			dropdownItem -> {
				dropdownItem.putData("action", "checkin");
				dropdownItem.setIcon("unlock");
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "checkin"));
				dropdownItem.setQuickAction(false);
			}
		).add(
			() -> stagedActions && !user.isGuestUser(),
			dropdownItem -> {
				dropdownItem.putData("action", "checkout");
				dropdownItem.setIcon("lock");
				dropdownItem.setLabel(
					LanguageUtil.get(
						_httpServletRequest, "checkout[document]"));
				dropdownItem.setQuickAction(false);
			}
		).add(
			() -> stagedActions && !user.isGuestUser(),
			dropdownItem -> {
				dropdownItem.putData("action", "permissions");
				dropdownItem.setIcon("password-policies");
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "permissions"));
				dropdownItem.setMultipleTypesBulkActionDisabled(true);
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
		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					_getFilterNavigationDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "filter-by") +
						StringPool.TRIPLE_PERIOD);
			}
		).addGroup(
			() ->
				!FeatureFlagManagerUtil.isEnabled("LPS-144527") &&
				!_dlAdminDisplayContext.isNavigationRecent(),
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(_getOrderByDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "order-by"));
			}
		).build();
	}

	@Override
	public List<LabelItem> getFilterLabelItems() {
		if (_filterLabelItems == null) {
			_filterLabelItems = _getFilterLabelItems();
		}

		return _filterLabelItems;
	}

	@Override
	public String getInfoPanelId() {
		return "infoPanelId";
	}

	@Override
	public List<DropdownItem> getOrderDropdownItems() {
		if (!FeatureFlagManagerUtil.isEnabled("LPS-144527")) {
			return null;
		}

		return _getOrderByDropdownItems();
	}

	@Override
	public String getSearchActionURL() {
		PortletURL searchURL = _dlAdminDisplayContext.getSearchRenderURL();

		return searchURL.toString();
	}

	@Override
	public String getSearchContainerId() {
		return "entries";
	}

	@Override
	public String getSortingOrder() {
		return _dlAdminDisplayContext.getOrderByType();
	}

	@Override
	public String getSortingURL() {
		return PortletURLBuilder.create(
			_getCurrentRenderURL()
		).setParameter(
			"orderByType",
			Objects.equals(_dlAdminDisplayContext.getOrderByType(), "asc") ?
				"desc" : "asc"
		).buildString();
	}

	@Override
	public Boolean getSupportsBulkActions() {
		return true;
	}

	@Override
	public List<ViewTypeItem> getViewTypeItems() {
		PortletURL renderURL = _getCurrentRenderURL();

		int curEntry = ParamUtil.getInteger(_httpServletRequest, "curEntry");

		if (curEntry > 0) {
			renderURL.setParameter("curEntry", String.valueOf(curEntry));
		}
		else {
			renderURL.setParameter("curEntry", (String)null);
		}

		return new ViewTypeItemList(renderURL, _getDisplayStyle()) {
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
					_dlAdminDisplayContext.getRepositoryId(), _getFolderId(),
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

	private void _addAssetCategoriesFilterLabelItems(
		LabelItemListBuilder.LabelItemListWrapper labelItemListWrapper) {

		Set<Long> assetCategoryIds = SetUtil.fromArray(_getAssetCategoryIds());

		for (Long assetCategoryId : assetCategoryIds) {
			labelItemListWrapper.add(
				labelItem -> {
					labelItem.putData(
						"removeLabelURL",
						_getRemoveLabelURL(
							"assetCategoryId",
							() -> TransformUtil.transformToArray(
								assetCategoryIds,
								curAssetCategoryId -> {
									if (Objects.equals(
											assetCategoryId,
											curAssetCategoryId)) {

										return null;
									}

									return String.valueOf(curAssetCategoryId);
								},
								String.class)));
					labelItem.setCloseable(true);

					String title = StringPool.BLANK;

					AssetCategory assetCategory =
						AssetCategoryServiceUtil.fetchCategory(assetCategoryId);

					if (assetCategory != null) {
						title = assetCategory.getTitle(
							_httpServletRequest.getLocale());
					}

					labelItem.setLabel(_getLabel("category", title));
				});
		}
	}

	private void _addAssetTagsFilterLabelItems(
		LabelItemListBuilder.LabelItemListWrapper labelItemListWrapper) {

		Set<String> assetTagIds = SetUtil.fromArray(_getAssetTagIds());

		for (String assetTagId : assetTagIds) {
			labelItemListWrapper.add(
				labelItem -> {
					labelItem.putData(
						"removeLabelURL",
						_getRemoveLabelURL(
							"assetTagId",
							() -> TransformUtil.transformToArray(
								assetTagIds,
								curAssetTagId -> {
									if (Objects.equals(
											assetTagId, curAssetTagId)) {

										return null;
									}

									return curAssetTagId;
								},
								String.class)));
					labelItem.setCloseable(true);
					labelItem.setLabel(_getLabel("tag", assetTagId));
				});
		}
	}

	private void _addExtensionFilterLabelItems(
		LabelItemListBuilder.LabelItemListWrapper labelItemListWrapper) {

		String[] extensions = _getExtensions();

		if (ArrayUtil.isEmpty(extensions)) {
			return;
		}

		for (String extension : extensions) {
			labelItemListWrapper.add(
				labelItem -> {
					labelItem.putData(
						"removeLabelURL",
						_getRemoveLabelURL(
							"extension",
							() -> ArrayUtil.remove(extensions, extension)));
					labelItem.setCloseable(true);
					labelItem.setLabel(_getLabel("extension", extension));
				});
		}
	}

	private long[] _getAssetCategoryIds() {
		return _dlAdminDisplayContext.getAssetCategoryIds();
	}

	private String _getAssetCategorySelectorURL() throws PortalException {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(_liferayPortletRequest);

		InfoItemItemSelectorCriterion itemSelectorCriterion =
			new InfoItemItemSelectorCriterion();

		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new InfoItemItemSelectorReturnType());
		itemSelectorCriterion.setItemType(AssetCategory.class.getName());
		itemSelectorCriterion.setMultiSelection(true);

		return PortletURLBuilder.create(
			_itemSelector.getItemSelectorURL(
				requestBackedPortletURLFactory, themeDisplay.getScopeGroup(),
				themeDisplay.getScopeGroupId(),
				_liferayPortletResponse.getNamespace() +
					"selectedAssetCategory",
				itemSelectorCriterion)
		).setParameter(
			"selectedCategoryIds",
			StringUtil.merge(_getAssetCategoryIds(), StringPool.COMMA)
		).setParameter(
			"vocabularyIds",
			StringUtil.merge(
				AssetVocabularyServiceUtil.getGroupsVocabularies(
					PortalUtil.getCurrentAndAncestorSiteGroupIds(
						_themeDisplay.getScopeGroupId()),
					DLFileEntryConstants.getClassName()),
				assetVocabulary -> String.valueOf(
					assetVocabulary.getVocabularyId()),
				StringPool.COMMA)
		).buildString();
	}

	private String[] _getAssetTagIds() {
		return _dlAdminDisplayContext.getAssetTagIds();
	}

	private String _getAssetTagSelectorURL() throws PortalException {
		AssetTagsItemSelectorCriterion assetTagsItemSelectorCriterion =
			new AssetTagsItemSelectorCriterion();

		assetTagsItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new AssetTagsItemSelectorReturnType());
		assetTagsItemSelectorCriterion.setGroupIds(
			PortalUtil.getCurrentAndAncestorSiteGroupIds(
				_themeDisplay.getScopeGroupId()));
		assetTagsItemSelectorCriterion.setMultiSelection(true);

		return String.valueOf(
			_itemSelector.getItemSelectorURL(
				RequestBackedPortletURLFactoryUtil.create(
					_liferayPortletRequest),
				_liferayPortletResponse.getNamespace() + "selectTag",
				assetTagsItemSelectorCriterion));
	}

	private PortletURL _getCurrentRenderURL() {
		return _dlAdminDisplayContext.getCurrentRenderURL();
	}

	private String _getDisplayStyle() {
		if (_isSearch()) {
			return _dlAdminDisplayContext.getSearchDisplayStyle();
		}

		return _dlAdminDisplayContext.getDisplayStyle();
	}

	private String[] _getDisplayViews() {
		DLPortletInstanceSettings dlPortletInstanceSettings =
			_dlRequestHelper.getDLPortletInstanceSettings();

		return dlPortletInstanceSettings.getDisplayViews();
	}

	private String[] _getExtensions() {
		return _dlAdminDisplayContext.getExtensions();
	}

	private String _getExtensionsItemSelectorURL() {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(_liferayPortletRequest);

		FileExtensionItemSelectorCriterion fileExtensionItemSelectorCriterion =
			new FileExtensionItemSelectorCriterion();

		fileExtensionItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			Collections.singletonList(new UUIDItemSelectorReturnType()));
		fileExtensionItemSelectorCriterion.setSelectedGroupIds(
			new long[] {_themeDisplay.getScopeGroupId()});

		PortletResponse portletResponse =
			(PortletResponse)_httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		ItemSelector itemSelector =
			(ItemSelector)_httpServletRequest.getAttribute(
				ItemSelector.class.getName());

		return PortletURLBuilder.create(
			itemSelector.getItemSelectorURL(
				requestBackedPortletURLFactory,
				portletResponse.getNamespace() + "selectedFileExtension",
				fileExtensionItemSelectorCriterion)
		).setParameter(
			"checkedFileExtensions", () -> _getExtensions()
		).buildString();
	}

	private long _getFileEntryTypeId() {
		return _dlAdminDisplayContext.getFileEntryTypeId();
	}

	private List<LabelItem> _getFilterLabelItems() {
		long fileEntryTypeId = _getFileEntryTypeId();

		LabelItemListBuilder.LabelItemListWrapper labelItemListWrapper =
			new LabelItemListBuilder.LabelItemListWrapper();

		_addAssetCategoriesFilterLabelItems(labelItemListWrapper);

		_addExtensionFilterLabelItems(labelItemListWrapper);

		_addAssetTagsFilterLabelItems(labelItemListWrapper);

		labelItemListWrapper.add(
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
						_themeDisplay.getLocale());
				}

				labelItem.setLabel(
					_getLabel("document-type", fileEntryTypeName));
			});

		labelItemListWrapper.add(
			_dlAdminDisplayContext::isNavigationMine,
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

				labelItem.setLabel(_getLabel("owner", user.getFullName()));
			});

		labelItemListWrapper.add(
			_dlAdminDisplayContext::isNavigationRecent,
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

				labelItem.setLabel(
					LanguageUtil.get(httpServletRequest, "recent"));
			});

		return labelItemListWrapper.build();
	}

	private List<DropdownItem> _getFilterNavigationDropdownItems() {
		boolean assetCategoryIdsIsEmpty = ArrayUtil.isEmpty(
			_getAssetCategoryIds());
		boolean assetTagIdsIsEmpty = ArrayUtil.isEmpty(_getAssetTagIds());
		boolean extensionsIsEmpty = ArrayUtil.isEmpty(_getExtensions());
		long fileEntryTypeId = _getFileEntryTypeId();

		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setActive(
					extensionsIsEmpty && (fileEntryTypeId == -1) &&
					_dlAdminDisplayContext.isNavigationHome() &&
					assetCategoryIdsIsEmpty && assetTagIdsIsEmpty);
				dropdownItem.setHref(
					PortletURLBuilder.create(
						PortletURLUtil.clone(
							_currentURLObj, _liferayPortletResponse)
					).setMVCRenderCommandName(
						"/document_library/view"
					).setNavigation(
						"home"
					).setParameter(
						"assetCategoryId", (String)null
					).setParameter(
						"assetTagId", (String)null
					).setParameter(
						"browseBy", (String)null
					).setParameter(
						"extension", (String)null
					).setParameter(
						"fileEntryTypeId", (String)null
					).buildPortletURL());
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "all"));
			}
		).add(
			dropdownItem -> {
				dropdownItem.setActive(
					_dlAdminDisplayContext.isNavigationRecent());
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
				dropdownItem.setActive(
					_dlAdminDisplayContext.isNavigationMine());
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
				dropdownItem.putData("action", "openCategoriesSelector");
				dropdownItem.putData(
					"categoriesFilterURL", _getAssetCategorySelectorURL());
				dropdownItem.setActive(!assetCategoryIdsIsEmpty);
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "categories") +
						StringPool.TRIPLE_PERIOD);
			}
		).add(
			dropdownItem -> {
				dropdownItem.setActive(fileEntryTypeId != -1);

				dropdownItem.putData("action", "openDocumentTypesSelector");

				String label =
					LanguageUtil.get(_httpServletRequest, "type") +
						StringPool.TRIPLE_PERIOD;

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
		).add(
			dropdownItem -> {
				dropdownItem.putData("action", "openExtensionSelector");
				dropdownItem.putData(
					"extensionsFilterURL", _getExtensionsItemSelectorURL());
				dropdownItem.setActive(!extensionsIsEmpty);
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "extension") +
						StringPool.TRIPLE_PERIOD);
			}
		).add(
			dropdownItem -> {
				dropdownItem.putData("action", "openTagsSelector");
				dropdownItem.putData(
					"tagsFilterURL", _getAssetTagSelectorURL());
				dropdownItem.setActive(!assetTagIdsIsEmpty);
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "tags") +
						StringPool.TRIPLE_PERIOD);
			}
		).build();
	}

	private long _getFolderId() {
		if (_isSearch()) {
			return ParamUtil.getLong(_httpServletRequest, "folderId");
		}

		return _dlAdminDisplayContext.getFolderId();
	}

	private String _getLabel(String key, String value) {
		return String.format(
			"%s: %s", LanguageUtil.get(_httpServletRequest, key),
			HtmlUtil.escape(value));
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
								_getCurrentRenderURL(), "orderByCol",
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

	private String _getRemoveLabelURL(
			String key,
			PortletURLBuilder.UnsafeSupplier<Object, Exception>
				valueUnsafeSupplier)
		throws PortletException {

		return PortletURLBuilder.create(
			PortletURLUtil.clone(_currentURLObj, _liferayPortletResponse)
		).setParameter(
			key, valueUnsafeSupplier
		).buildString();
	}

	private boolean _hasValidAssetVocabularies(long scopeGroupId)
		throws PortalException {

		if (_hasValidAssetVocabularies != null) {
			return _hasValidAssetVocabularies;
		}

		List<AssetVocabulary> assetVocabularies =
			AssetVocabularyServiceUtil.getGroupVocabularies(
				PortalUtil.getCurrentAndAncestorSiteGroupIds(scopeGroupId));

		for (AssetVocabulary assetVocabulary : assetVocabularies) {
			if (!assetVocabulary.isAssociatedToClassNameId(
					ClassNameLocalServiceUtil.getClassNameId(
						DLFileEntry.class.getName()))) {

				continue;
			}

			int count = AssetCategoryServiceUtil.getVocabularyCategoriesCount(
				assetVocabulary.getGroupId(),
				assetVocabulary.getVocabularyId());

			if (count > 0) {
				_hasValidAssetVocabularies = true;

				return _hasValidAssetVocabularies;
			}
		}

		_hasValidAssetVocabularies = false;

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
	private List<LabelItem> _filterLabelItems;
	private Boolean _hasValidAssetVocabularies;
	private final HttpServletRequest _httpServletRequest;
	private final ItemSelector _itemSelector;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final ThemeDisplay _themeDisplay;

}