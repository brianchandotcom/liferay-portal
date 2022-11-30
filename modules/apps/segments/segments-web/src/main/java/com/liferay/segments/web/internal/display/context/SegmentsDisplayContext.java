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

package com.liferay.segments.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.SearchDisplayStyleUtil;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.roles.item.selector.RoleItemSelectorCriterion;
import com.liferay.segments.configuration.provider.SegmentsConfigurationProvider;
import com.liferay.segments.constants.SegmentsActionKeys;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.constants.SegmentsPortletKeys;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryService;
import com.liferay.segments.web.internal.constants.SegmentsWebKeys;
import com.liferay.segments.web.internal.security.permission.resource.SegmentsEntryPermission;
import com.liferay.segments.web.internal.security.permission.resource.SegmentsResourcePermission;
import com.liferay.segments.web.internal.util.comparator.SegmentsEntryModifiedDateComparator;
import com.liferay.segments.web.internal.util.comparator.SegmentsEntryNameComparator;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eduardo García
 */
public class SegmentsDisplayContext {

	public SegmentsDisplayContext(
		GroupLocalService groupLocalService, Language language, Portal portal,
		PrefsProps prefsProps, RenderRequest renderRequest,
		RenderResponse renderResponse,
		SegmentsConfigurationProvider segmentsConfigurationProvider,
		SegmentsEntryService segmentsEntryService) {

		_groupLocalService = groupLocalService;
		_language = language;
		_portal = portal;
		_prefsProps = prefsProps;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_segmentsConfigurationProvider = segmentsConfigurationProvider;
		_segmentsEntryService = segmentsEntryService;

		_httpServletRequest = portal.getHttpServletRequest(renderRequest);

		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_permissionChecker = _themeDisplay.getPermissionChecker();
	}

	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.putData("action", "deleteSegmentsEntries");
				dropdownItem.setIcon("times-circle");
				dropdownItem.setLabel(
					_language.get(_httpServletRequest, "delete"));
				dropdownItem.setQuickAction(true);
			}
		).build();
	}

	public Map<String, Object> getAssignUserRolesDataMap(
		SegmentsEntry segmentsEntry) {

		return HashMapBuilder.<String, Object>put(
			"itemSelectorURL",
			() -> {
				ItemSelector itemSelector =
					(ItemSelector)_httpServletRequest.getAttribute(
						SegmentsWebKeys.ITEM_SELECTOR);

				RoleItemSelectorCriterion roleItemSelectorCriterion =
					new RoleItemSelectorCriterion(RoleConstants.TYPE_SITE);

				roleItemSelectorCriterion.setCheckedRoleIds(
					segmentsEntry.getRoleIds());
				roleItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
					new UUIDItemSelectorReturnType());
				roleItemSelectorCriterion.setExcludedRoleNames(
					(String[])_httpServletRequest.getAttribute(
						SegmentsWebKeys.EXCLUDED_ROLE_NAMES));

				PortletURL portletURL = itemSelector.getItemSelectorURL(
					RequestBackedPortletURLFactoryUtil.create(_renderRequest),
					(String)_httpServletRequest.getAttribute(
						"view.jsp-eventName"),
					roleItemSelectorCriterion);

				return portletURL.toString();
			}
		).put(
			"segmentsEntryId", segmentsEntry.getSegmentsEntryId()
		).build();
	}

	public String getAssignUserRolesLinkCss(SegmentsEntry segmentsEntry) {
		StringBuilder sb = new StringBuilder(_ASSIGN_USER_ROLES_LINK_CSS);

		if (!isRoleSegmentationEnabled(segmentsEntry.getCompanyId())) {
			sb.append(" action disabled");
		}

		return sb.toString();
	}

	public String getAvailableActions(SegmentsEntry segmentsEntry)
		throws PortalException {

		if (SegmentsEntryPermission.contains(
				_themeDisplay.getPermissionChecker(), segmentsEntry,
				ActionKeys.DELETE)) {

			return "deleteSegmentsEntries";
		}

		return StringPool.BLANK;
	}

	public String getClearResultsURL() {
		return PortletURLBuilder.create(
			_getPortletURL()
		).setKeywords(
			StringPool.BLANK
		).buildString();
	}

	public CreationMenu getCreationMenu() {
		return CreationMenuBuilder.addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref(
					_renderResponse.createRenderURL(), "mvcRenderCommandName",
					"/segments/edit_segments_entry", "type",
					User.class.getName());
				dropdownItem.setLabel(
					_language.get(_httpServletRequest, "add-new-user-segment"));
			}
		).build();
	}

	public String getDeleteURL(SegmentsEntry segmentsEntry) {
		return PortletURLBuilder.createActionURL(
			_renderResponse
		).setActionName(
			"/segments/delete_segments_entry"
		).setRedirect(
			_portal.getCurrentURL(_renderRequest)
		).setParameter(
			"segmentsEntryId", segmentsEntry.getSegmentsEntryId()
		).buildString();
	}

	public String getDisplayStyle() {
		if (_displayStyle != null) {
			return _displayStyle;
		}

		_displayStyle = SearchDisplayStyleUtil.getDisplayStyle(
			_renderRequest, SegmentsPortletKeys.SEGMENTS, "list");

		return _displayStyle;
	}

	public String getEditURL(SegmentsEntry segmentsEntry) {
		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCRenderCommandName(
			"/segments/edit_segments_entry"
		).setRedirect(
			_portal.getCurrentURL(_renderRequest)
		).setParameter(
			"segmentsEntryId", segmentsEntry.getSegmentsEntryId()
		).buildString();
	}

	public List<DropdownItem> getFilterItemsDropdownItems() {
		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					_getFilterNavigationDropdownItems());
				dropdownGroupItem.setLabel(
					_language.get(_httpServletRequest, "filter-by-navigation"));
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(_getOrderByDropdownItems());
				dropdownGroupItem.setLabel(
					_language.get(_httpServletRequest, "order-by"));
			}
		).build();
	}

	public String getOrderByType() {
		if (_orderByType != null) {
			return _orderByType;
		}

		_orderByType = SearchOrderByUtil.getOrderByType(
			_renderRequest, SegmentsPortletKeys.SEGMENTS, "asc");

		return _orderByType;
	}

	public String getPermissionURL(SegmentsEntry segmentsEntry) {
		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				_httpServletRequest,
				"com_liferay_portlet_configuration_web_portlet_" +
					"PortletConfigurationPortlet",
				ActionRequest.RENDER_PHASE)
		).setMVCPath(
			"/edit_permissions.jsp"
		).setRedirect(
			_portal.getCurrentURL(_renderRequest)
		).setParameter(
			"modelResource", SegmentsEntry.class.getName()
		).setParameter(
			"modelResourceDescription",
			segmentsEntry.getName(_themeDisplay.getLocale())
		).setParameter(
			"resourcePrimKey", segmentsEntry.getSegmentsEntryId()
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();
	}

	public String getPreviewMembersURL(SegmentsEntry segmentsEntry) {
		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCRenderCommandName(
			"/segments/preview_segments_entry_users"
		).setRedirect(
			_portal.getCurrentURL(_renderRequest)
		).setParameter(
			"clearSessionCriteria", true
		).setParameter(
			"segmentsEntryId", segmentsEntry.getSegmentsEntryId()
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();
	}

	public String getScopeName(SegmentsEntry segmentsEntry) {
		if (_themeDisplay.getCompanyGroupId() == segmentsEntry.getGroupId()) {
			return _language.get(_themeDisplay.getLocale(), "global");
		}

		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-166954"))) {
			if (segmentsEntry.getGroupId() == _themeDisplay.getScopeGroupId()) {
				return _language.get(_themeDisplay.getLocale(), "current-site");
			}

			return _language.get(_themeDisplay.getLocale(), "parent-site");
		}

		Group group = _groupLocalService.fetchGroup(segmentsEntry.getGroupId());

		if (group == null) {
			return StringPool.BLANK;
		}

		try {
			String descriptiveName = group.getDescriptiveName(
				_themeDisplay.getLocale());

			if (descriptiveName != null) {
				return descriptiveName;
			}

			return group.getName(_themeDisplay.getLocale());
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			return group.getName(_themeDisplay.getLocale());
		}
	}

	public String getSearchActionURL() {
		return String.valueOf(_getPortletURL());
	}

	public SearchContainer<SegmentsEntry> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		SearchContainer<SegmentsEntry> searchContainer = new SearchContainer(
			_renderRequest, _getPortletURL(), null, "there-are-no-segments");

		searchContainer.setId("segmentsEntries");
		searchContainer.setOrderByCol(_getOrderByCol());
		searchContainer.setOrderByComparator(_getOrderByComparator());
		searchContainer.setOrderByType(getOrderByType());

		if (_isSearch()) {
			if (!GetterUtil.getBoolean(
					PropsUtil.get("feature.flag.LPS-166954"))) {

				searchContainer.setResultsAndTotal(
					_segmentsEntryService.searchSegmentsEntries(
						_themeDisplay.getCompanyId(),
						_themeDisplay.getScopeGroupId(), _getKeywords(), true,
						searchContainer.getStart(), searchContainer.getEnd(),
						_getSort()));
			}
			else {
				searchContainer.setResultsAndTotal(
					_segmentsEntryService.searchSegmentsEntries(
						_themeDisplay.getCompanyId(), _getKeywords(),
						searchContainer.getStart(), searchContainer.getEnd(),
						_getSort()));
			}
		}
		else {
			if (!GetterUtil.getBoolean(
					PropsUtil.get("feature.flag.LPS-166954"))) {

				searchContainer.setResultsAndTotal(
					() -> _segmentsEntryService.getSegmentsEntries(
						_themeDisplay.getScopeGroupId(), true,
						searchContainer.getStart(), searchContainer.getEnd(),
						searchContainer.getOrderByComparator()),
					_segmentsEntryService.getSegmentsEntriesCount(
						_themeDisplay.getScopeGroupId(), true));
			}
			else {
				searchContainer.setResultsAndTotal(
					() -> _segmentsEntryService.getSegmentsEntries(
						_themeDisplay.getCompanyId(),
						searchContainer.getStart(), searchContainer.getEnd(),
						searchContainer.getOrderByComparator()),
					_segmentsEntryService.getSegmentsEntriesCount(
						_themeDisplay.getCompanyId()));
			}
		}

		searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));

		_searchContainer = searchContainer;

		return _searchContainer;
	}

	public String getSegmentsCompanyConfigurationURL(
		HttpServletRequest httpServletRequest) {

		try {
			return _segmentsConfigurationProvider.getCompanyConfigurationURL(
				httpServletRequest);
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return StringPool.BLANK;
	}

	public String getSegmentsEntryURL(SegmentsEntry segmentsEntry) {
		if (segmentsEntry == null) {
			return StringPool.BLANK;
		}

		if (Objects.equals(
				segmentsEntry.getSource(),
				SegmentsEntryConstants.SOURCE_ASAH_FARO_BACKEND)) {

			String asahFaroURL = _prefsProps.getString(
				segmentsEntry.getCompanyId(), "liferayAnalyticsURL");

			if (Validator.isNull(asahFaroURL)) {
				return StringPool.BLANK;
			}

			return asahFaroURL + "/contacts/segments/" +
				segmentsEntry.getSegmentsEntryKey();
		}

		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCRenderCommandName(
			"/segments/edit_segments_entry"
		).setRedirect(
			_portal.getCurrentURL(_renderRequest)
		).setParameter(
			"segmentsEntryId", segmentsEntry.getSegmentsEntryId()
		).setParameter(
			"showInEditMode", false
		).buildString();
	}

	public String getSegmentsEntryURLTarget(SegmentsEntry segmentsEntry) {
		if (Objects.equals(
				segmentsEntry.getSource(),
				SegmentsEntryConstants.SOURCE_ASAH_FARO_BACKEND)) {

			return "_blank";
		}

		return "_self";
	}

	public String getSortingURL() {
		return PortletURLBuilder.create(
			_getPortletURL()
		).setParameter(
			"orderByType",
			Objects.equals(getOrderByType(), "asc") ? "desc" : "asc"
		).buildString();
	}

	public int getTotalItems() throws PortalException {
		SearchContainer<?> searchContainer = getSearchContainer();

		return searchContainer.getTotal();
	}

	public boolean isAsahEnabled(long companyId) {
		if (Validator.isNotNull(
				_prefsProps.getString(companyId, "liferayAnalyticsURL"))) {

			return true;
		}

		return false;
	}

	public boolean isDisabledManagementBar() throws PortalException {
		if (_hasResults() || _isSearch()) {
			return false;
		}

		return true;
	}

	public boolean isRoleSegmentationEnabled(long companyId) {
		try {
			return _segmentsConfigurationProvider.isRoleSegmentationEnabled(
				companyId);
		}
		catch (ConfigurationException configurationException) {
			_log.error(configurationException);
		}

		return false;
	}

	public boolean isSegmentationEnabled(long companyId) {
		try {
			return _segmentsConfigurationProvider.isSegmentationEnabled(
				companyId);
		}
		catch (ConfigurationException configurationException) {
			_log.error(configurationException);
		}

		return false;
	}

	public boolean isShowAssignUserRolesAction(SegmentsEntry segmentsEntry) {
		try {
			Group group = _groupLocalService.getGroup(
				segmentsEntry.getGroupId());

			if (!group.isCompany() &&
				SegmentsEntryPermission.contains(
					_permissionChecker, segmentsEntry,
					ActionKeys.ASSIGN_USER_ROLES)) {

				return true;
			}

			return false;
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return false;
	}

	public boolean isShowCreationMenu() {
		if (SegmentsResourcePermission.contains(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getScopeGroupId(),
				SegmentsActionKeys.MANAGE_SEGMENTS_ENTRIES)) {

			return true;
		}

		return false;
	}

	public boolean isShowDeleteAction(SegmentsEntry segmentsEntry) {
		try {
			if (!GetterUtil.getBoolean(
					PropsUtil.get("feature.flag.LPS-166954"))) {

				if ((segmentsEntry.getGroupId() ==
						_themeDisplay.getScopeGroupId()) &&
					SegmentsEntryPermission.contains(
						_permissionChecker, segmentsEntry, ActionKeys.DELETE)) {

					return true;
				}
			}
			else {
				if (SegmentsEntryPermission.contains(
						_permissionChecker, segmentsEntry, ActionKeys.DELETE)) {

					return true;
				}
			}

			return false;
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return false;
	}

	public boolean isShowPermissionAction(SegmentsEntry segmentsEntry) {
		try {
			return SegmentsEntryPermission.contains(
				_permissionChecker, segmentsEntry, ActionKeys.PERMISSIONS);
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return false;
	}

	public boolean isShowUpdateAction(SegmentsEntry segmentsEntry) {
		try {
			return SegmentsEntryPermission.contains(
				_permissionChecker, segmentsEntry, ActionKeys.UPDATE);
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return false;
	}

	public boolean isShowViewAction(SegmentsEntry segmentsEntry) {
		try {
			return SegmentsEntryPermission.contains(
				_permissionChecker, segmentsEntry, ActionKeys.VIEW);
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return false;
	}

	private List<DropdownItem> _getFilterNavigationDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setActive(true);
				dropdownItem.setHref(_renderResponse.createRenderURL());
				dropdownItem.setLabel(
					_language.get(_httpServletRequest, "all"));
			}
		).build();
	}

	private String _getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		return _keywords;
	}

	private String _getOrderByCol() {
		if (_orderByCol != null) {
			return _orderByCol;
		}

		_orderByCol = SearchOrderByUtil.getOrderByCol(
			_renderRequest, SegmentsPortletKeys.SEGMENTS, "modified-date");

		return _orderByCol;
	}

	private OrderByComparator<SegmentsEntry> _getOrderByComparator() {
		boolean orderByAsc = false;

		if (Objects.equals(getOrderByType(), "asc")) {
			orderByAsc = true;
		}

		String orderByCol = _getOrderByCol();

		OrderByComparator<SegmentsEntry> orderByComparator = null;

		if (orderByCol.equals("modified-date")) {
			orderByComparator = new SegmentsEntryModifiedDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("name")) {
			orderByComparator = new SegmentsEntryNameComparator(orderByAsc);
		}

		return orderByComparator;
	}

	private List<DropdownItem> _getOrderByDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setActive(
					Objects.equals(_getOrderByCol(), "modified-date"));
				dropdownItem.setHref(
					_getPortletURL(), "orderByCol", "modified-date");
				dropdownItem.setLabel(
					_language.get(_httpServletRequest, "modified-date"));
			}
		).add(
			dropdownItem -> {
				dropdownItem.setActive(
					Objects.equals(_getOrderByCol(), "name"));
				dropdownItem.setHref(_getPortletURL(), "orderByCol", "name");
				dropdownItem.setLabel(
					_language.get(_httpServletRequest, "name"));
			}
		).build();
	}

	private PortletURL _getPortletURL() {
		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCPath(
			"/view.jsp"
		).setKeywords(
			() -> {
				String keywords = _getKeywords();

				if (Validator.isNotNull(keywords)) {
					return keywords;
				}

				return null;
			}
		).setParameter(
			"displayStyle", getDisplayStyle()
		).setParameter(
			"orderByCol", _getOrderByCol()
		).setParameter(
			"orderByType", getOrderByType()
		).buildPortletURL();
	}

	private Sort _getSort() {
		boolean orderByAsc = false;

		if (Objects.equals(getOrderByType(), "asc")) {
			orderByAsc = true;
		}

		Sort sort = null;

		if (Objects.equals(_getOrderByCol(), "name")) {
			sort = new Sort(
				Field.getSortableFieldName(
					"localized_name_".concat(_themeDisplay.getLanguageId())),
				Sort.STRING_TYPE, !orderByAsc);
		}
		else {
			sort = new Sort(Field.MODIFIED_DATE, Sort.LONG_TYPE, !orderByAsc);
		}

		return sort;
	}

	private boolean _hasResults() throws PortalException {
		if (getTotalItems() > 0) {
			return true;
		}

		return false;
	}

	private boolean _isSearch() {
		if (Validator.isNotNull(_getKeywords())) {
			return true;
		}

		return false;
	}

	private static final String _ASSIGN_USER_ROLES_LINK_CSS =
		"assign-site-roles-link dropdown-item";

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsDisplayContext.class);

	private String _displayStyle;
	private final GroupLocalService _groupLocalService;
	private final HttpServletRequest _httpServletRequest;
	private String _keywords;
	private final Language _language;
	private String _orderByCol;
	private String _orderByType;
	private final PermissionChecker _permissionChecker;
	private final Portal _portal;
	private final PrefsProps _prefsProps;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private SearchContainer<SegmentsEntry> _searchContainer;
	private final SegmentsConfigurationProvider _segmentsConfigurationProvider;
	private final SegmentsEntryService _segmentsEntryService;
	private final ThemeDisplay _themeDisplay;

}