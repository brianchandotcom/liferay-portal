/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.admin.web.internal.display.context;

import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemFormVariation;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.layout.page.template.admin.constants.LayoutPageTemplateAdminPortletKeys;
import com.liferay.layout.page.template.admin.web.internal.util.LayoutPageTemplatePortletUtil;
import com.liferay.layout.page.template.constants.LayoutPageTemplateConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.info.item.capability.DisplayPageInfoItemCapability;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalServiceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryServiceUtil;
import com.liferay.layout.page.template.util.comparator.LayoutPageTemplateCollectionLayoutPageTemplateEntryCreateDateComparator;
import com.liferay.layout.page.template.util.comparator.LayoutPageTemplateCollectionLayoutPageTemplateEntryModifiedDateComparator;
import com.liferay.layout.page.template.util.comparator.LayoutPageTemplateCollectionLayoutPageTemplateEntryNameComparator;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.navigation.taglib.servlet.taglib.util.BreadcrumbEntryBuilder;
import com.liferay.site.navigation.taglib.servlet.taglib.util.BreadcrumbEntryListBuilder;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class DisplayPageDisplayContext {

	public DisplayPageDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_infoItemServiceRegistry =
			(InfoItemServiceRegistry)httpServletRequest.getAttribute(
				InfoItemServiceRegistry.class.getName());
		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public boolean existsMappedContentType(
		LayoutPageTemplateEntry layoutPageTemplateEntry) {

		if (!FeatureFlagManagerUtil.isEnabled("LPS-195263")) {
			return true;
		}

		if (layoutPageTemplateEntry.getClassNameId() == 0) {
			return false;
		}

		Map<Long, Long[]> classNameIdsMap = _getClassNameIdsMap();

		if (!classNameIdsMap.containsKey(
				layoutPageTemplateEntry.getClassNameId())) {

			return false;
		}

		Long[] classTypeIds = classNameIdsMap.get(
			layoutPageTemplateEntry.getClassNameId());

		if ((layoutPageTemplateEntry.getClassTypeId() == 0) &&
			ArrayUtil.isEmpty(classTypeIds)) {

			return true;
		}

		if (!ArrayUtil.contains(
				classTypeIds, layoutPageTemplateEntry.getClassTypeId())) {

			return false;
		}

		return true;
	}

	public SearchContainer<?> getDisplayPagesSearchContainer() {
		if (_displayPagesSearchContainer != null) {
			return _displayPagesSearchContainer;
		}

		if (!FeatureFlagManagerUtil.isEnabled("LPS-189856")) {
			SearchContainer<LayoutPageTemplateEntry>
				displayPagesSearchContainer = new SearchContainer<>(
					_renderRequest, getPortletURL(), null,
					"there-are-no-display-page-templates");

			displayPagesSearchContainer.setOrderByCol(getOrderByCol());
			displayPagesSearchContainer.setOrderByComparator(
				LayoutPageTemplatePortletUtil.
					getLayoutPageTemplateEntryOrderByComparator(
						getOrderByCol(), getOrderByType()));
			displayPagesSearchContainer.setOrderByType(getOrderByType());

			if (isSearch()) {
				displayPagesSearchContainer.setResultsAndTotal(
					() ->
						LayoutPageTemplateEntryServiceUtil.
							getLayoutPageTemplateEntries(
								_themeDisplay.getScopeGroupId(), getKeywords(),
								LayoutPageTemplateEntryTypeConstants.
									DISPLAY_PAGE,
								displayPagesSearchContainer.getStart(),
								displayPagesSearchContainer.getEnd(),
								displayPagesSearchContainer.
									getOrderByComparator()),
					LayoutPageTemplateEntryServiceUtil.
						getLayoutPageTemplateEntriesCount(
							_themeDisplay.getScopeGroupId(), getKeywords(),
							LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE));
			}
			else {
				displayPagesSearchContainer.setResultsAndTotal(
					() ->
						LayoutPageTemplateEntryServiceUtil.
							getLayoutPageTemplateEntries(
								_themeDisplay.getScopeGroupId(),
								LayoutPageTemplateEntryTypeConstants.
									DISPLAY_PAGE,
								displayPagesSearchContainer.getStart(),
								displayPagesSearchContainer.getEnd(),
								displayPagesSearchContainer.
									getOrderByComparator()),
					LayoutPageTemplateEntryServiceUtil.
						getLayoutPageTemplateEntriesCount(
							_themeDisplay.getScopeGroupId(),
							LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE));
			}

			displayPagesSearchContainer.setRowChecker(
				new EmptyOnClickRowChecker(_renderResponse));

			_displayPagesSearchContainer = displayPagesSearchContainer;

			return _displayPagesSearchContainer;
		}

		if (isSearch()) {
			SearchContainer<LayoutPageTemplateEntry>
				displayPagesSearchContainer = new SearchContainer<>(
					_renderRequest, getPortletURL(), null,
					"there-are-no-display-page-templates");

			displayPagesSearchContainer.setOrderByCol(getOrderByCol());
			displayPagesSearchContainer.setOrderByComparator(
				LayoutPageTemplatePortletUtil.
					getLayoutPageTemplateEntryOrderByComparator(
						getOrderByCol(), getOrderByType()));
			displayPagesSearchContainer.setOrderByType(getOrderByType());

			displayPagesSearchContainer.setResultsAndTotal(
				() ->
					LayoutPageTemplateEntryServiceUtil.
						getLayoutPageTemplateEntries(
							_themeDisplay.getScopeGroupId(), getKeywords(),
							LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE,
							displayPagesSearchContainer.getStart(),
							displayPagesSearchContainer.getEnd(),
							displayPagesSearchContainer.getOrderByComparator()),
				LayoutPageTemplateEntryServiceUtil.
					getLayoutPageTemplateEntriesCount(
						_themeDisplay.getScopeGroupId(), getKeywords(),
						LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE));

			displayPagesSearchContainer.setRowChecker(
				new EmptyOnClickRowChecker(_renderResponse));

			_displayPagesSearchContainer = displayPagesSearchContainer;

			return _displayPagesSearchContainer;
		}

		SearchContainer<Object> displayPagesSearchContainer =
			new SearchContainer<>(
				_renderRequest, getPortletURL(), null,
				"there-are-no-display-page-templates");

		displayPagesSearchContainer.setOrderByCol(getOrderByCol());
		displayPagesSearchContainer.setOrderByComparator(
			_getOrderByComparator());
		displayPagesSearchContainer.setOrderByType(getOrderByType());

		displayPagesSearchContainer.setResultsAndTotal(
			() ->
				LayoutPageTemplateEntryServiceUtil.
					getLayoutPageCollectionsAndLayoutPageTemplateEntries(
						_themeDisplay.getScopeGroupId(),
						_getLayoutPageTemplateCollectionId(),
						LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE,
						displayPagesSearchContainer.getStart(),
						displayPagesSearchContainer.getEnd(),
						displayPagesSearchContainer.getOrderByComparator()),
			LayoutPageTemplateEntryServiceUtil.
				getLayoutPageCollectionsAndLayoutPageTemplateEntriesCount(
					_themeDisplay.getScopeGroupId(),
					_getLayoutPageTemplateCollectionId(),
					LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE));

		displayPagesSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));

		_displayPagesSearchContainer = displayPagesSearchContainer;

		return _displayPagesSearchContainer;
	}

	public String getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		return _keywords;
	}

	public List<BreadcrumbEntry> getLayoutPageTemplateBreadcrumbEntries() {
		return BreadcrumbEntryListBuilder.add(
			breadcrumbEntry -> {
				breadcrumbEntry.setTitle(
					LanguageUtil.get(_httpServletRequest, "home"));
				breadcrumbEntry.setURL(
					PortletURLBuilder.createRenderURL(
						_renderResponse
					).setTabs1(
						"display-page-templates"
					).setParameter(
						"layoutPageTemplateCollectionId",
						LayoutPageTemplateConstants.
							PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT
					).buildString());
			}
		).addAll(
			() ->
				_getLayoutPageTemplateCollectionId() !=
					LayoutPageTemplateConstants.
						PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
			() -> {
				LayoutPageTemplateCollection layoutPageTemplateCollection =
					LayoutPageTemplateCollectionLocalServiceUtil.
						fetchLayoutPageTemplateCollection(
							_getLayoutPageTemplateCollectionId());

				List<LayoutPageTemplateCollection>
					layoutPageTemplateCollections =
						layoutPageTemplateCollection.getAncestors();

				Collections.reverse(layoutPageTemplateCollections);

				return TransformUtil.transform(
					layoutPageTemplateCollections,
					curLayoutPageTemplateCollection ->
						BreadcrumbEntryBuilder.setTitle(
							curLayoutPageTemplateCollection.getName()
						).setURL(
							PortletURLBuilder.createRenderURL(
								_renderResponse
							).setTabs1(
								"display-page-templates"
							).setParameter(
								"layoutPageTemplateCollectionId",
								curLayoutPageTemplateCollection.
									getLayoutPageTemplateCollectionId()
							).buildString()
						).build());
			}
		).build();
	}

	public long getLayoutPageTemplateEntryId() {
		if (Validator.isNotNull(_layoutPageTemplateEntryId)) {
			return _layoutPageTemplateEntryId;
		}

		_layoutPageTemplateEntryId = ParamUtil.getLong(
			_httpServletRequest, "layoutPageTemplateEntryId");

		return _layoutPageTemplateEntryId;
	}

	public JSONArray getMappingTypesJSONArray() {
		if (_mappingTypesJSONArray != null) {
			return _mappingTypesJSONArray;
		}

		JSONArray mappingTypesJSONArray = JSONFactoryUtil.createJSONArray();

		for (InfoItemClassDetails infoItemClassDetails :
				_infoItemServiceRegistry.getInfoItemClassDetails(
					_themeDisplay.getScopeGroupId(),
					DisplayPageInfoItemCapability.KEY,
					_themeDisplay.getPermissionChecker())) {

			mappingTypesJSONArray.put(
				JSONUtil.put(
					"id",
					String.valueOf(
						PortalUtil.getClassNameId(
							infoItemClassDetails.getClassName()))
				).put(
					"label",
					infoItemClassDetails.getLabel(_themeDisplay.getLocale())
				).put(
					"subtypes",
					_getMappingFormVariationsJSONArray(infoItemClassDetails)
				));
		}

		_mappingTypesJSONArray = mappingTypesJSONArray;

		return _mappingTypesJSONArray;
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = SearchOrderByUtil.getOrderByCol(
			_httpServletRequest,
			LayoutPageTemplateAdminPortletKeys.LAYOUT_PAGE_TEMPLATES,
			"display-page-order-by-col", "create-date");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = SearchOrderByUtil.getOrderByType(
			_httpServletRequest,
			LayoutPageTemplateAdminPortletKeys.LAYOUT_PAGE_TEMPLATES,
			"display-page-order-by-type", "asc");

		return _orderByType;
	}

	public PortletURL getPortletURL() {
		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCPath(
			"/view_display_pages.jsp"
		).setRedirect(
			_themeDisplay.getURLCurrent()
		).setKeywords(
			() -> {
				String keywords = getKeywords();

				if (Validator.isNotNull(keywords)) {
					return keywords;
				}

				return null;
			}
		).setTabs1(
			"display-page-templates"
		).setParameter(
			"orderByCol",
			() -> {
				String orderByCol = getOrderByCol();

				if (Validator.isNotNull(orderByCol)) {
					return orderByCol;
				}

				return null;
			}
		).setParameter(
			"orderByType",
			() -> {
				String orderByType = getOrderByType();

				if (Validator.isNotNull(orderByType)) {
					return orderByType;
				}

				return null;
			}
		).buildPortletURL();
	}

	public boolean isSearch() {
		if (Validator.isNotNull(getKeywords())) {
			return true;
		}

		return false;
	}

	private Map<Long, Long[]> _getClassNameIdsMap() {
		if (_classNameIdsMap != null) {
			return _classNameIdsMap;
		}

		Map<Long, Long[]> classNameIdsMap = new HashMap<>();

		for (InfoItemClassDetails infoItemClassDetails :
				_infoItemServiceRegistry.getInfoItemClassDetails(
					DisplayPageInfoItemCapability.KEY)) {

			classNameIdsMap.put(
				PortalUtil.getClassNameId(infoItemClassDetails.getClassName()),
				_getInfoFormVariationIds(infoItemClassDetails));
		}

		_classNameIdsMap = classNameIdsMap;

		return _classNameIdsMap;
	}

	private Long[] _getInfoFormVariationIds(
		InfoItemClassDetails infoItemClassDetails) {

		InfoItemFormVariationsProvider<?> infoItemFormVariationsProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemFormVariationsProvider.class,
				infoItemClassDetails.getClassName());

		if (infoItemFormVariationsProvider == null) {
			return new Long[0];
		}

		return ListUtil.toArray(
			ListUtil.fromCollection(
				infoItemFormVariationsProvider.getInfoItemFormVariations(
					_themeDisplay.getScopeGroupId())),
			new Accessor<InfoItemFormVariation, Long>() {

				@Override
				public Long get(InfoItemFormVariation infoItemFormVariation) {
					return Long.valueOf(infoItemFormVariation.getKey());
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<InfoItemFormVariation> getTypeClass() {
					return InfoItemFormVariation.class;
				}

			});
	}

	private long _getLayoutPageTemplateCollectionId() {
		if (_layoutPageTemplateCollectionId != null) {
			return _layoutPageTemplateCollectionId;
		}

		_layoutPageTemplateCollectionId = ParamUtil.getLong(
			_httpServletRequest, "layoutPageTemplateCollectionId",
			LayoutPageTemplateConstants.
				PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT);

		return _layoutPageTemplateCollectionId;
	}

	private JSONArray _getMappingFormVariationsJSONArray(
		InfoItemClassDetails infoItemClassDetails) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		InfoItemFormVariationsProvider<?> infoItemFormVariationsProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemFormVariationsProvider.class,
				infoItemClassDetails.getClassName());

		if (infoItemFormVariationsProvider == null) {
			return jsonArray;
		}

		Collection<InfoItemFormVariation> infoItemFormVariations =
			infoItemFormVariationsProvider.getInfoItemFormVariations(
				_themeDisplay.getScopeGroupId());

		for (InfoItemFormVariation infoItemFormVariation :
				infoItemFormVariations) {

			jsonArray.put(
				JSONUtil.put(
					"id", String.valueOf(infoItemFormVariation.getKey())
				).put(
					"label",
					() -> {
						InfoLocalizedValue<String> labelInfoLocalizedValue =
							infoItemFormVariation.getLabelInfoLocalizedValue();

						return labelInfoLocalizedValue.getValue(
							_themeDisplay.getLocale());
					}
				));
		}

		return jsonArray;
	}

	private OrderByComparator<Object> _getOrderByComparator() {
		boolean orderByAsc = false;

		if (Objects.equals(getOrderByType(), "asc")) {
			orderByAsc = true;
		}

		if (Objects.equals(getOrderByCol(), "create-date")) {
			return new LayoutPageTemplateCollectionLayoutPageTemplateEntryCreateDateComparator(
				orderByAsc);
		}
		else if (Objects.equals(getOrderByCol(), "modified-date")) {
			return new LayoutPageTemplateCollectionLayoutPageTemplateEntryModifiedDateComparator(
				orderByAsc);
		}
		else if (Objects.equals(getOrderByCol(), "name")) {
			return new LayoutPageTemplateCollectionLayoutPageTemplateEntryNameComparator(
				orderByAsc);
		}

		return null;
	}

	private Map<Long, Long[]> _classNameIdsMap;
	private SearchContainer<?> _displayPagesSearchContainer;
	private final HttpServletRequest _httpServletRequest;
	private final InfoItemServiceRegistry _infoItemServiceRegistry;
	private String _keywords;
	private Long _layoutPageTemplateCollectionId;
	private Long _layoutPageTemplateEntryId;
	private JSONArray _mappingTypesJSONArray;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}