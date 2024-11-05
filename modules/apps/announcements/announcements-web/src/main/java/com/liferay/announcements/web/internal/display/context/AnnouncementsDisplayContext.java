/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.announcements.web.internal.display.context;

import com.liferay.announcements.constants.AnnouncementsPortletKeys;
import com.liferay.announcements.kernel.model.AnnouncementsEntry;
import com.liferay.announcements.kernel.model.AnnouncementsFlagConstants;
import com.liferay.announcements.kernel.service.AnnouncementsEntryLocalServiceUtil;
import com.liferay.announcements.web.internal.display.context.helper.AnnouncementsRequestHelper;
import com.liferay.announcements.web.internal.util.AnnouncementsUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.service.permission.OrganizationPermissionUtil;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.service.permission.UserGroupPermissionUtil;
import com.liferay.segments.SegmentsEntryRetriever;
import com.liferay.segments.configuration.provider.SegmentsConfigurationProvider;
import com.liferay.segments.context.RequestContextMapper;
import com.liferay.segments.model.SegmentsEntryRole;
import com.liferay.segments.service.SegmentsEntryRoleLocalServiceUtil;

import java.text.DateFormat;
import java.text.Format;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 * @author Roberto Díaz
 */
public class AnnouncementsDisplayContext {

	public AnnouncementsDisplayContext(
		AnnouncementsRequestHelper announcementsRequestHelper,
		HttpServletRequest httpServletRequest, String portletName,
		RenderRequest renderRequest, RenderResponse renderResponse,
		RequestContextMapper requestContextMapper,
		SegmentsEntryRetriever segmentsEntryRetriever,
		SegmentsConfigurationProvider segmentsConfigurationProvider) {

		_announcementsRequestHelper = announcementsRequestHelper;
		_httpServletRequest = httpServletRequest;
		_portletName = portletName;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_requestContextMapper = requestContextMapper;
		_segmentsEntryRetriever = segmentsEntryRetriever;
		_segmentsConfigurationProvider = segmentsConfigurationProvider;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public LinkedHashMap<Long, long[]> getAnnouncementScopes()
		throws PortalException {

		if (_announcementScopes != null) {
			return _announcementScopes;
		}

		_announcementScopes = new LinkedHashMap<>();

		if (isCustomizeAnnouncementsDisplayed()) {
			long[] selectedScopeGroupIdsArray = ListUtil.toLongArray(
				_getSelectedScopeGroups(), Group.GROUP_ID_ACCESSOR);
			long[] selectedScopeOrganizationIdsArray = ListUtil.toLongArray(
				_getSelectedScopeOrganizations(),
				Organization.ORGANIZATION_ID_ACCESSOR);
			long[] selectedScopeRoleIdsArray = ListUtil.toLongArray(
				_getSelectedScopeRoles(), Role.ROLE_ID_ACCESSOR);
			long[] selectedScopeUserGroupIdsArray = ListUtil.toLongArray(
				_getSelectedScopeUserGroups(),
				UserGroup.USER_GROUP_ID_ACCESSOR);

			if (selectedScopeGroupIdsArray.length != 0) {
				_announcementScopes.put(
					PortalUtil.getClassNameId(Group.class.getName()),
					selectedScopeGroupIdsArray);
			}

			if (selectedScopeOrganizationIdsArray.length != 0) {
				_announcementScopes.put(
					PortalUtil.getClassNameId(Organization.class.getName()),
					selectedScopeOrganizationIdsArray);
			}

			if (selectedScopeRoleIdsArray.length != 0) {
				_announcementScopes.put(
					PortalUtil.getClassNameId(Role.class.getName()),
					selectedScopeRoleIdsArray);
			}

			if (selectedScopeUserGroupIdsArray.length != 0) {
				_announcementScopes.put(
					PortalUtil.getClassNameId(UserGroup.class.getName()),
					selectedScopeUserGroupIdsArray);
			}
		}
		else {
			LinkedHashMap<Long, long[]> announcementScopes =
				AnnouncementsUtil.getAnnouncementScopes(
					_announcementsRequestHelper.getUser());

			if (_segmentsConfigurationProvider.isRoleSegmentationEnabled(
					_announcementsRequestHelper.getCompanyId())) {

				long roleClassNameId = PortalUtil.getClassNameId(
					Role.class.getName());

				Set<Long> roleIds = SetUtil.fromArray(
					announcementScopes.get(roleClassNameId));

				long[] segmentsEntryIds =
					_segmentsEntryRetriever.getSegmentsEntryIds(
						_announcementsRequestHelper.getScopeGroupId(),
						_themeDisplay.getUserId(),
						_requestContextMapper.map(_httpServletRequest),
						new long[0]);

				for (long segmentsEntryId : segmentsEntryIds) {
					List<SegmentsEntryRole> segmentsEntryRoles =
						SegmentsEntryRoleLocalServiceUtil.getSegmentsEntryRoles(
							segmentsEntryId);

					for (SegmentsEntryRole segmentsEntryRole :
							segmentsEntryRoles) {

						roleIds.add(segmentsEntryRole.getRoleId());
					}
				}

				announcementScopes.put(
					roleClassNameId, ArrayUtil.toLongArray(roleIds));
			}

			_announcementScopes = announcementScopes;
		}

		_announcementScopes.put(0L, new long[] {0});

		return _announcementScopes;
	}

	public Format getDateFormat() {
		ThemeDisplay themeDisplay =
			_announcementsRequestHelper.getThemeDisplay();

		return FastDateFormatFactoryUtil.getDate(
			DateFormat.FULL, themeDisplay.getLocale(),
			themeDisplay.getTimeZone());
	}

	public List<Group> getGroups() throws PortalException {
		if (!isCustomizeAnnouncementsDisplayed() ||
			StringUtil.equals(
				_announcementsRequestHelper.getPortletId(),
				AnnouncementsPortletKeys.ANNOUNCEMENTS_ADMIN)) {

			return AnnouncementsUtil.getGroups(
				_announcementsRequestHelper.getThemeDisplay());
		}

		List<Group> selectedGroups = new ArrayList<>();

		for (Group group : _getSelectedScopeGroups()) {
			if (GroupPermissionUtil.contains(
					_announcementsRequestHelper.getPermissionChecker(), group,
					ActionKeys.MANAGE_ANNOUNCEMENTS)) {

				selectedGroups.add(group);
			}
		}

		return selectedGroups;
	}

	public List<Organization> getOrganizations() throws PortalException {
		if (!isCustomizeAnnouncementsDisplayed() ||
			StringUtil.equals(
				_announcementsRequestHelper.getPortletId(),
				AnnouncementsPortletKeys.ANNOUNCEMENTS_ADMIN)) {

			return AnnouncementsUtil.getOrganizations(
				_announcementsRequestHelper.getThemeDisplay());
		}

		List<Organization> selectedOrganizations = new ArrayList<>();

		for (Organization organization : _getSelectedScopeOrganizations()) {
			if (OrganizationPermissionUtil.contains(
					_announcementsRequestHelper.getPermissionChecker(),
					organization, ActionKeys.MANAGE_ANNOUNCEMENTS)) {

				selectedOrganizations.add(organization);
			}
		}

		return selectedOrganizations;
	}

	public int getPageDelta() {
		PortletPreferences portletPreferences =
			_announcementsRequestHelper.getPortletPreferences();

		return GetterUtil.getInteger(
			portletPreferences.getValue(
				"pageDelta", String.valueOf(SearchContainer.DEFAULT_DELTA)));
	}

	public List<Role> getRoles() throws PortalException {
		if (!isCustomizeAnnouncementsDisplayed() ||
			StringUtil.equals(
				_announcementsRequestHelper.getPortletId(),
				AnnouncementsPortletKeys.ANNOUNCEMENTS_ADMIN)) {

			return AnnouncementsUtil.getRoles(
				_announcementsRequestHelper.getThemeDisplay());
		}

		List<Role> selectedRoles = new ArrayList<>();

		for (Role role : _getSelectedScopeRoles()) {
			if (AnnouncementsUtil.hasManageAnnouncementsPermission(
					role, _announcementsRequestHelper.getPermissionChecker())) {

				selectedRoles.add(role);
			}
		}

		return selectedRoles;
	}

	public SearchContainer<AnnouncementsEntry> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer(
			_renderRequest, null, null, "cur1", getPageDelta(),
			_getPortletURL(), null, "no-entries-were-found");

		_searchContainer.setResultsAndTotal(
			() -> AnnouncementsEntryLocalServiceUtil.getEntries(
				_themeDisplay.getUserId(), getAnnouncementScopes(),
				_portletName.equals(AnnouncementsPortletKeys.ALERTS),
				_getFlag(), _searchContainer.getStart(),
				_searchContainer.getEnd()),
			AnnouncementsEntryLocalServiceUtil.getEntriesCount(
				_themeDisplay.getUserId(), getAnnouncementScopes(),
				_portletName.equals(AnnouncementsPortletKeys.ALERTS),
				_getFlag()));

		return _searchContainer;
	}

	public String getTabs1Names() {
		return "unread,read";
	}

	public String getTabs1PortletURL() {
		return PortletURLBuilder.createRenderURL(
			_announcementsRequestHelper.getLiferayPortletResponse()
		).setMVCRenderCommandName(
			"/announcements/view"
		).setTabs1(
			_announcementsRequestHelper.getTabs1()
		).buildString();
	}

	public List<UserGroup> getUserGroups() throws PortalException {
		if (!isCustomizeAnnouncementsDisplayed() ||
			StringUtil.equals(
				_announcementsRequestHelper.getPortletId(),
				AnnouncementsPortletKeys.ANNOUNCEMENTS_ADMIN)) {

			return AnnouncementsUtil.getUserGroups(
				_announcementsRequestHelper.getThemeDisplay());
		}

		List<UserGroup> selectedUserGroups = new ArrayList<>();

		for (UserGroup userGroup : _getSelectedScopeUserGroups()) {
			if (UserGroupPermissionUtil.contains(
					_announcementsRequestHelper.getPermissionChecker(),
					userGroup.getUserGroupId(),
					ActionKeys.MANAGE_ANNOUNCEMENTS)) {

				selectedUserGroups.add(userGroup);
			}
		}

		return selectedUserGroups;
	}

	public UUID getUuid() {
		return _UUID;
	}

	public boolean hasAddAnnouncementsEntryPermission() {
		try {
			if (GroupPermissionUtil.contains(
					_themeDisplay.getPermissionChecker(),
					_themeDisplay.getScopeGroupId(),
					ActionKeys.MANAGE_ANNOUNCEMENTS) ||
				PortletPermissionUtil.hasControlPanelAccessPermission(
					_themeDisplay.getPermissionChecker(),
					_themeDisplay.getScopeGroupId(),
					AnnouncementsPortletKeys.ANNOUNCEMENTS_ADMIN)) {

				return true;
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return false;
	}

	public boolean isCustomizeAnnouncementsDisplayed() {
		String portletName = _announcementsRequestHelper.getPortletName();

		if (portletName.equals(AnnouncementsPortletKeys.ALERTS)) {
			return false;
		}

		Group scopeGroup = _announcementsRequestHelper.getScopeGroup();

		return PrefsParamUtil.getBoolean(
			_announcementsRequestHelper.getPortletPreferences(),
			_announcementsRequestHelper.getRequest(),
			"customizeAnnouncementsDisplayed", !scopeGroup.isUser());
	}

	public boolean isScopeGroupSelected(Group scopeGroup) {
		String selectedScopeGroupExternalReferenceCodes =
			_getSelectedScopeGroupExternalReferenceCodes();

		return selectedScopeGroupExternalReferenceCodes.contains(
			scopeGroup.getExternalReferenceCode());
	}

	public boolean isScopeOrganizationSelected(Organization organization) {
		String selectedScopeOrganizationExternalReferenceCodes =
			_getSelectedScopeOrganizationExternalReferenceCodes();

		return selectedScopeOrganizationExternalReferenceCodes.contains(
			organization.getExternalReferenceCode());
	}

	public boolean isScopeRoleSelected(Role role) {
		String selectedScopeRoleExternalReferenceCodes =
			_getSelectedScopeRoleExternalReferenceCodes();

		return selectedScopeRoleExternalReferenceCodes.contains(
			role.getExternalReferenceCode());
	}

	public boolean isScopeUserGroupSelected(UserGroup userGroup) {
		String selectedScopeUserGroupExternalReferenceCodes =
			_getSelectedScopeUserGroupExternalReferenceCodes();

		return selectedScopeUserGroupExternalReferenceCodes.contains(
			userGroup.getExternalReferenceCode());
	}

	public boolean isShowReadEntries() {
		String tabs1 = _announcementsRequestHelper.getTabs1();

		return tabs1.equals("read");
	}

	public boolean isShowScopeName() {
		String mvcRenderCommandName = ParamUtil.getString(
			_announcementsRequestHelper.getRequest(), "mvcRenderCommandName");

		return mvcRenderCommandName.equals("/announcements/edit_entry");
	}

	public boolean isTabs1Visible() {
		String portletName = _announcementsRequestHelper.getPortletName();

		ThemeDisplay themeDisplay =
			_announcementsRequestHelper.getThemeDisplay();

		try {
			if (!portletName.equals(AnnouncementsPortletKeys.ALERTS) ||
				(portletName.equals(AnnouncementsPortletKeys.ALERTS) &&
				 PortletPermissionUtil.hasControlPanelAccessPermission(
					 _announcementsRequestHelper.getPermissionChecker(),
					 themeDisplay.getScopeGroupId(),
					 AnnouncementsPortletKeys.ANNOUNCEMENTS_ADMIN))) {

				return true;
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return false;
	}

	private int _getFlag() {
		if (_flag != null) {
			return _flag;
		}

		_flag = isShowReadEntries() ? AnnouncementsFlagConstants.HIDDEN :
			AnnouncementsFlagConstants.NOT_HIDDEN;

		return _flag;
	}

	private PortletURL _getPortletURL() {
		if (_portletURL != null) {
			return _portletURL;
		}

		_portletURL = PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCRenderCommandName(
			"/announcements/view"
		).setTabs1(
			_announcementsRequestHelper.getTabs1()
		).buildPortletURL();

		return _portletURL;
	}

	private String _getSelectedScopeGroupExternalReferenceCodes() {
		Layout layout = _announcementsRequestHelper.getLayout();

		Group group = layout.getGroup();

		return PrefsParamUtil.getString(
			_announcementsRequestHelper.getPortletPreferences(),
			_announcementsRequestHelper.getRequest(),
			"selectedScopeGroupExternalReferenceCodes",
			group.getExternalReferenceCode());
	}

	private List<Group> _getSelectedScopeGroups() throws PortalException {
		List<Group> groups = new ArrayList<>();

		for (String externalReferenceCode :
				StringUtil.split(
					_getSelectedScopeGroupExternalReferenceCodes())) {

			groups.add(
				GroupLocalServiceUtil.getGroupByExternalReferenceCode(
					externalReferenceCode,
					_announcementsRequestHelper.getCompanyId()));
		}

		return groups;
	}

	private String _getSelectedScopeOrganizationExternalReferenceCodes() {
		return PrefsParamUtil.getString(
			_announcementsRequestHelper.getPortletPreferences(),
			_announcementsRequestHelper.getRequest(),
			"selectedScopeOrganizationExternalReferenceCodes",
			StringPool.BLANK);
	}

	private List<Organization> _getSelectedScopeOrganizations()
		throws PortalException {

		List<Organization> organizations = new ArrayList<>();

		for (String externalReferenceCode :
				StringUtil.split(
					_getSelectedScopeOrganizationExternalReferenceCodes())) {

			organizations.add(
				OrganizationLocalServiceUtil.
					getOrganizationByExternalReferenceCode(
						externalReferenceCode,
						_announcementsRequestHelper.getCompanyId()));
		}

		return organizations;
	}

	private String _getSelectedScopeRoleExternalReferenceCodes() {
		return PrefsParamUtil.getString(
			_announcementsRequestHelper.getPortletPreferences(),
			_announcementsRequestHelper.getRequest(),
			"selectedScopeRoleExternalReferenceCodes", StringPool.BLANK);
	}

	private List<Role> _getSelectedScopeRoles() throws PortalException {
		List<Role> roles = new ArrayList<>();

		for (String externalReferenceCode :
				StringUtil.split(
					_getSelectedScopeRoleExternalReferenceCodes())) {

			roles.add(
				RoleLocalServiceUtil.getRoleByExternalReferenceCode(
					externalReferenceCode,
					_announcementsRequestHelper.getCompanyId()));
		}

		return roles;
	}

	private String _getSelectedScopeUserGroupExternalReferenceCodes() {
		return PrefsParamUtil.getString(
			_announcementsRequestHelper.getPortletPreferences(),
			_announcementsRequestHelper.getRequest(),
			"selectedScopeUserGroupExternalReferenceCodes", StringPool.BLANK);
	}

	private List<UserGroup> _getSelectedScopeUserGroups()
		throws PortalException {

		List<UserGroup> userGroups = new ArrayList<>();

		for (String externalReferenceCode :
				StringUtil.split(
					_getSelectedScopeUserGroupExternalReferenceCodes())) {

			userGroups.add(
				UserGroupLocalServiceUtil.getUserGroupByExternalReferenceCode(
					externalReferenceCode,
					_announcementsRequestHelper.getCompanyId()));
		}

		return userGroups;
	}

	private static final UUID _UUID = UUID.fromString(
		"CD705D0E-7DB4-430C-9492-F1FA25ACE02E");

	private static final Log _log = LogFactoryUtil.getLog(
		AnnouncementsDisplayContext.class);

	private LinkedHashMap<Long, long[]> _announcementScopes;
	private final AnnouncementsRequestHelper _announcementsRequestHelper;
	private Integer _flag;
	private final HttpServletRequest _httpServletRequest;
	private final String _portletName;
	private PortletURL _portletURL;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final RequestContextMapper _requestContextMapper;
	private SearchContainer<AnnouncementsEntry> _searchContainer;
	private final SegmentsConfigurationProvider _segmentsConfigurationProvider;
	private final SegmentsEntryRetriever _segmentsEntryRetriever;
	private final ThemeDisplay _themeDisplay;

}