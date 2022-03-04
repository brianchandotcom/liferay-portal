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

package com.liferay.content.dashboard.web.internal.display.context;

import com.liferay.asset.categories.configuration.AssetCategoriesCompanyConfiguration;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.content.dashboard.web.internal.item.ContentDashboardItem;
import com.liferay.content.dashboard.web.internal.item.selector.criteria.content.dashboard.file.extension.criterion.ContentDashboardFileExtensionItemSelectorCriterion;
import com.liferay.content.dashboard.web.internal.item.selector.criteria.content.dashboard.type.criterion.ContentDashboardItemSubtypeItemSelectorCriterion;
import com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemSubtype;
import com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemSubtypeFactoryTracker;
import com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemSubtypeUtil;
import com.liferay.content.dashboard.web.internal.model.AssetVocabularyMetric;
import com.liferay.content.dashboard.web.internal.servlet.taglib.util.ContentDashboardDropdownItemsProvider;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.info.item.InfoItemReference;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.item.selector.criteria.group.criterion.GroupItemSelectorCriterion;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.portlet.url.builder.ResourceURLBuilder;
import com.liferay.petra.reflect.GenericUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.users.admin.item.selector.UserItemSelectorCriterion;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.ActionURL;
import javax.portlet.WindowStateException;

/**
 * @author Cristina González
 */
public class ContentDashboardAdminDisplayContext {

	public ContentDashboardAdminDisplayContext(
		List<AssetVocabulary> assetVocabularies,
		AssetVocabularyMetric assetVocabularyMetric,
		ContentDashboardDropdownItemsProvider
			contentDashboardDropdownItemsProvider,
		ContentDashboardItemSubtypeFactoryTracker
			contentDashboardItemSubtypeFactoryTracker,
		ItemSelector itemSelector, String languageDirection,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse, Portal portal,
		ResourceBundle resourceBundle,
		SearchContainer<ContentDashboardItem<?>> searchContainer) {

		_assetVocabularies = assetVocabularies;
		_assetVocabularyMetric = assetVocabularyMetric;
		_contentDashboardDropdownItemsProvider =
			contentDashboardDropdownItemsProvider;
		_contentDashboardItemSubtypeFactoryTracker =
			contentDashboardItemSubtypeFactoryTracker;
		_itemSelector = itemSelector;
		_languageDirection = languageDirection;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_portal = portal;
		_resourceBundle = resourceBundle;
		_searchContainer = searchContainer;
	}

	public List<Long> getAssetCategoryIds() {
		if (_assetCategoryIds != null) {
			return _assetCategoryIds;
		}

		_assetCategoryIds = Arrays.asList(
			ArrayUtil.toLongArray(
				ParamUtil.getLongValues(
					_liferayPortletRequest, "assetCategoryId")));

		return _assetCategoryIds;
	}

	public List<String> getAssetCategoryTitles(
		ContentDashboardItem contentDashboardItem, long assetVocabularyId) {

		List<AssetCategory> assetCategories =
			contentDashboardItem.getAssetCategories(assetVocabularyId);

		Stream<AssetCategory> stream = assetCategories.stream();

		Locale locale = _portal.getLocale(_liferayPortletRequest);

		return stream.map(
			assetCategory -> assetCategory.getTitle(locale)
		).collect(
			Collectors.toList()
		);
	}

	public Set<String> getAssetTagIds() {
		if (_assetTagIds != null) {
			return _assetTagIds;
		}

		_assetTagIds = new HashSet(
			Arrays.asList(
				ArrayUtil.toStringArray(
					ParamUtil.getStringValues(
						_liferayPortletRequest, "assetTagId"))));

		return _assetTagIds;
	}

	public List<AssetVocabulary> getAssetVocabularies() {
		return _assetVocabularies;
	}

	public String getAuditGraphTitle() {
		List<String> vocabularyNames =
			_assetVocabularyMetric.getVocabularyNames();

		if (vocabularyNames.size() == 2) {
			return ResourceBundleUtil.getString(
				_resourceBundle, "content-per-x-and-x", vocabularyNames.get(0),
				vocabularyNames.get(1));
		}
		else if (vocabularyNames.size() == 1) {
			return ResourceBundleUtil.getString(
				_resourceBundle, "content-per-x", vocabularyNames.get(0));
		}
		else {
			return ResourceBundleUtil.getString(_resourceBundle, "content");
		}
	}

	public List<Long> getAuthorIds() {
		if (_authorIds != null) {
			return _authorIds;
		}

		_authorIds = Arrays.asList(
			ArrayUtil.toLongArray(
				ParamUtil.getLongValues(_liferayPortletRequest, "authorIds")));

		return _authorIds;
	}

	public String getAuthorItemSelectorURL() throws PortalException {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(_liferayPortletRequest);

		UserItemSelectorCriterion userItemSelectorCriterion =
			new UserItemSelectorCriterion();

		userItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			Collections.singletonList(new UUIDItemSelectorReturnType()));

		return PortletURLBuilder.create(
			_itemSelector.getItemSelectorURL(
				requestBackedPortletURLFactory,
				_liferayPortletResponse.getNamespace() + "selectedAuthorItem",
				userItemSelectorCriterion)
		).setParameter(
			"checkedUserIds", StringUtil.merge(getAuthorIds())
		).setParameter(
			"checkedUserIdsEnabled", Boolean.TRUE
		).buildString();
	}

	public String getContentDashboardItemSubtypeItemSelectorURL() {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(_liferayPortletRequest);

		ContentDashboardItemSubtypeItemSelectorCriterion
			contentDashboardItemSubtypeItemSelectorCriterion =
				new ContentDashboardItemSubtypeItemSelectorCriterion();

		contentDashboardItemSubtypeItemSelectorCriterion.
			setDesiredItemSelectorReturnTypes(
				Collections.singletonList(new UUIDItemSelectorReturnType()));

		return PortletURLBuilder.create(
			_itemSelector.getItemSelectorURL(
				requestBackedPortletURLFactory,
				_liferayPortletResponse.getNamespace() +
					"selectedContentDashboardItemSubtype",
				contentDashboardItemSubtypeItemSelectorCriterion)
		).setParameter(
			"checkedContentDashboardItemSubtypesPayload",
			() -> {
				List<? extends ContentDashboardItemSubtype>
					contentDashboardItemSubtypes =
						getContentDashboardItemSubtypes();

				Stream<? extends ContentDashboardItemSubtype> stream =
					contentDashboardItemSubtypes.stream();

				return stream.map(
					contentDashboardItemSubtype -> {
						InfoItemReference infoItemReference =
							contentDashboardItemSubtype.getInfoItemReference();

						Class<?> genericClass = GenericUtil.getGenericClass(
							contentDashboardItemSubtype);

						return JSONUtil.put(
							"className", infoItemReference.getClassName()
						).put(
							"classPK", infoItemReference.getClassPK()
						).put(
							"entryClassName", genericClass.getName()
						).toString();
					}
				).toArray(
					String[]::new
				);
			}
		).buildString();
	}

	public List<? extends ContentDashboardItemSubtype>
		getContentDashboardItemSubtypes() {

		if (_contentDashboardItemSubtypePayloads != null) {
			return _contentDashboardItemSubtypePayloads;
		}

		String[] contentDashboardItemSubtypePayloads =
			ParamUtil.getParameterValues(
				_liferayPortletRequest, "contentDashboardItemSubtypePayload",
				new String[0], false);

		if (ArrayUtil.isEmpty(contentDashboardItemSubtypePayloads)) {
			_contentDashboardItemSubtypePayloads = Collections.emptyList();
		}
		else {
			return Stream.of(
				contentDashboardItemSubtypePayloads
			).map(
				contentDashboardItemSubtypePayload ->
					ContentDashboardItemSubtypeUtil.
						toContentDashboardItemSubtypeOptional(
							_contentDashboardItemSubtypeFactoryTracker,
							contentDashboardItemSubtypePayload)
			).filter(
				Optional::isPresent
			).map(
				Optional::get
			).collect(
				Collectors.toList()
			);
		}

		return _contentDashboardItemSubtypePayloads;
	}

	public Map<String, Object> getData() {
		if (_data != null) {
			return _data;
		}

		_data = HashMapBuilder.<String, Object>put(
			"context", _getContext()
		).put(
			"props", _getProps()
		).build();

		return _data;
	}

	public List<DropdownItem> getDropdownItems(
		ContentDashboardItem contentDashboardItem) {

		return _contentDashboardDropdownItemsProvider.getDropdownItems(
			contentDashboardItem);
	}

	public String getFileExtensionItemSelectorURL() {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(_liferayPortletRequest);

		ContentDashboardFileExtensionItemSelectorCriterion
			contentDashboardFileExtensionItemSelectorCriterion =
				new ContentDashboardFileExtensionItemSelectorCriterion();

		contentDashboardFileExtensionItemSelectorCriterion.
			setDesiredItemSelectorReturnTypes(
				Collections.singletonList(new UUIDItemSelectorReturnType()));

		return PortletURLBuilder.create(
			_itemSelector.getItemSelectorURL(
				requestBackedPortletURLFactory,
				_liferayPortletResponse.getNamespace() +
					"selectedFileExtension",
				contentDashboardFileExtensionItemSelectorCriterion)
		).setParameter(
			"checkedFileExtensions",
			() -> {
				List<String> fileExtensions = getFileExtensions();

				return fileExtensions.toArray(new String[0]);
			}
		).buildString();
	}

	public List<String> getFileExtensions() {
		return Arrays.asList(
			ParamUtil.getStringValues(_liferayPortletRequest, "fileExtension"));
	}

	public String getOnClickConfiguration() throws WindowStateException {
		StringBundler sb = new StringBundler(13);

		sb.append("Liferay.Portlet.openModal({namespace: '");

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		sb.append(portletDisplay.getNamespace());

		sb.append("', onClose: function() {Liferay.Portlet.refresh('#p_p_id_");
		sb.append(portletDisplay.getId());
		sb.append("_')}, portletSelector: '#p_p_id_");
		sb.append(portletDisplay.getId());
		sb.append("_', portletId: '");
		sb.append(portletDisplay.getId());
		sb.append("', title: '");
		sb.append(
			ResourceBundleUtil.getString(_resourceBundle, "configuration"));
		sb.append("', url: '");

		sb.append(
			HtmlUtil.escapeJS(
				PortletURLBuilder.createRenderURL(
					_liferayPortletResponse
				).setMVCRenderCommandName(
					"/content_dashboard/edit_content_dashboard_configuration"
				).setWindowState(
					LiferayWindowState.POP_UP
				).buildString()));

		sb.append("'}); return false;");

		return sb.toString();
	}

	public long getScopeId() {
		if (_scopeId > 0) {
			return _scopeId;
		}

		_scopeId = ParamUtil.getLong(_liferayPortletRequest, "scopeId");

		return _scopeId;
	}

	public String getScopeIdItemSelectorURL() throws PortalException {
		GroupItemSelectorCriterion groupItemSelectorCriterion =
			new GroupItemSelectorCriterion();

		groupItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new URLItemSelectorReturnType());
		groupItemSelectorCriterion.setIncludeAllVisibleGroups(true);

		return String.valueOf(
			_itemSelector.getItemSelectorURL(
				RequestBackedPortletURLFactoryUtil.create(
					_liferayPortletRequest),
				_liferayPortletResponse.getNamespace() + "selectedScopeIdItem",
				groupItemSelectorCriterion));
	}

	public SearchContainer<ContentDashboardItem<?>> getSearchContainer() {
		return _searchContainer;
	}

	public Integer getStatus() {
		if (_status != null) {
			return _status;
		}

		_status = ParamUtil.getInteger(
			_liferayPortletRequest, "status", WorkflowConstants.STATUS_ANY);

		return _status;
	}

	public ActionURL getSwapConfigurationURL() {
		return PortletURLBuilder.createActionURL(
			_liferayPortletResponse
		).setActionName(
			"/content_dashboard/swap_content_dashboard_configuration"
		).buildActionURL();
	}

	public long getUserId() {
		if (_userId > 0) {
			return _userId;
		}

		_userId = _portal.getUserId(_liferayPortletRequest);

		return _userId;
	}

	public HashMap<String, Object> getXlsProps() {
		return HashMapBuilder.<String, Object>put(
			"fileURL",
			() -> ResourceURLBuilder.createResourceURL(
				_liferayPortletResponse
			).setBackURL(
				_portal.getCurrentURL(_liferayPortletRequest)
			).setResourceID(
				"/content_dashboard/get_content_dashboard_items_xls"
			).buildString()
		).put(
			"total", _searchContainer.getTotal()
		).build();
	}

	public boolean isSwapConfigurationEnabled() {
		if (_swapConfigurationEnabled != null) {
			return _swapConfigurationEnabled;
		}

		List<String> vocabularyNames =
			_assetVocabularyMetric.getVocabularyNames();

		if (vocabularyNames.size() == 2) {
			_swapConfigurationEnabled = true;
		}
		else {
			_swapConfigurationEnabled = false;
		}

		return _swapConfigurationEnabled;
	}

	private Map<String, Object> _getContext() {
		return HashMapBuilder.<String, Object>put(
			"languageDirection", _languageDirection
		).put(
			"namespace", _liferayPortletResponse.getNamespace()
		).build();
	}

	private Map<String, Object> _getProps() {
		return HashMapBuilder.<String, Object>put(
			"learnHowURL",
			() -> {
				ThemeDisplay themeDisplay =
					(ThemeDisplay)_liferayPortletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				AssetCategoriesCompanyConfiguration
					assetCategoriesCompanyConfiguration =
						ConfigurationProviderUtil.getCompanyConfiguration(
							AssetCategoriesCompanyConfiguration.class,
							themeDisplay.getCompanyId());

				return assetCategoriesCompanyConfiguration.
					linkToDocumentationURL();
			}
		).put(
			"vocabularies", _assetVocabularyMetric.toJSONArray()
		).build();
	}

	private List<Long> _assetCategoryIds;
	private Set<String> _assetTagIds;
	private final List<AssetVocabulary> _assetVocabularies;
	private final AssetVocabularyMetric _assetVocabularyMetric;
	private List<Long> _authorIds;
	private final ContentDashboardDropdownItemsProvider
		_contentDashboardDropdownItemsProvider;
	private final ContentDashboardItemSubtypeFactoryTracker
		_contentDashboardItemSubtypeFactoryTracker;
	private List<ContentDashboardItemSubtype>
		_contentDashboardItemSubtypePayloads;
	private Map<String, Object> _data;
	private final ItemSelector _itemSelector;
	private final String _languageDirection;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final Portal _portal;
	private final ResourceBundle _resourceBundle;
	private long _scopeId;
	private final SearchContainer<ContentDashboardItem<?>> _searchContainer;
	private Integer _status;
	private Boolean _swapConfigurationEnabled;
	private long _userId;

}