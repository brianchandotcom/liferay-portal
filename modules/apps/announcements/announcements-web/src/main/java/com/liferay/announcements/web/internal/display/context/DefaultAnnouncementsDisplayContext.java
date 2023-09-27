/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.announcements.web.internal.display.context;

import com.liferay.announcements.constants.AnnouncementsPortletKeys;
import com.liferay.announcements.kernel.model.AnnouncementsEntry;
import com.liferay.announcements.kernel.model.AnnouncementsFlagConstants;
import com.liferay.announcements.kernel.service.AnnouncementsEntryLocalServiceUtil;
import com.liferay.announcements.kernel.util.AnnouncementsUtil;
import com.liferay.announcements.web.internal.display.context.helper.AnnouncementsRequestHelper;
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
import com.liferay.portal.kernel.service.permission.UserGroupPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
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
public class DefaultAnnouncementsDisplayContext
	implements AnnouncementsDisplayContext {

	public DefaultAnnouncementsDisplayContext(
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

	@Override
	public LinkedHashMap<Long, long[]> getAnnouncementScopes()
		throws PortalException {

		if (_announcementScopes != null) {
			return _announcementScopes;
		}

		_announcementScopes = new LinkedHashMap<>();

		if (isCustomizeAnnouncementsDisplayed()) {
			long[] selectedScopeGroupIdsArray = GetterUtil.getLongValues(
				StringUtil.split(_getSelectedScopeGroupIds()));
			long[] selectedScopeOrganizationIdsArray = GetterUtil.getLongValues(
				StringUtil.split(_getSelectedScopeOrganizationIds()));
			long[] selectedScopeRoleIdsArray = GetterUtil.getLongValues(
				StringUtil.split(_getSelectedScopeRoleIds()));
			long[] selectedScopeUserGroupIdsArray = GetterUtil.getLongValues(
				StringUtil.split(_getSelectedScopeUserGroupIds()));

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

	@Override
	public Format getDateFormatDate() {
		ThemeDisplay themeDisplay =
			_announcementsRequestHelper.getThemeDisplay();

		return FastDateFormatFactoryUtil.getDate(
			DateFormat.FULL, themeDisplay.getLocale(),
			themeDisplay.getTimeZone());
	}

	@Override
	public List<Group> getGroups() throws PortalException {
		if (!isCustomizeAnnouncementsDisplayed() ||
			StringUtil.equals(
				_announcementsRequestHelper.getPortletId(),
				AnnouncementsPortletKeys.ANNOUNCEMENTS_ADMIN)) {

			return AnnouncementsUtil.getGroups(
				_announcementsRequestHelper.getThemeDisplay());
		}

		List<Group> selectedGroups = new ArrayList<>();

		String[] selectedScopeGroupIds = StringUtil.split(
			_getSelectedScopeGroupIds());

		for (String selectedScopeGroupId : selectedScopeGroupIds) {
			long groupId = Long.valueOf(selectedScopeGroupId);

			if (GroupPermissionUtil.contains(
					_announcementsRequestHelper.getPermissionChecker(), groupId,
					ActionKeys.MANAGE_ANNOUNCEMENTS)) {

				selectedGroups.add(GroupLocalServiceUtil.getGroup(groupId));
			}
		}

		return selectedGroups;
	}

	@Override
	public List<Organization> getOrganizations() throws PortalException {
		if (!isCustomizeAnnouncementsDisplayed() ||
			StringUtil.equals(
				_announcementsRequestHelper.getPortletId(),
				AnnouncementsPortletKeys.ANNOUNCEMENTS_ADMIN)) {

			return AnnouncementsUtil.getOrganizations(
				_announcementsRequestHelper.getThemeDisplay());
		}

		List<Organization> selectedOrganizations = new ArrayList<>();

		String[] selectedScopeOrganizationIds = StringUtil.split(
			_getSelectedScopeOrganizationIds());

		for (String selectedScopeOrganizationId :
				selectedScopeOrganizationIds) {

			long organizationId = Long.valueOf(selectedScopeOrganizationId);

			if (OrganizationPermissionUtil.contains(
					_announcementsRequestHelper.getPermissionChecker(),
					organizationId, ActionKeys.MANAGE_ANNOUNCEMENTS)) {

				selectedOrganizations.add(
					OrganizationLocalServiceUtil.getOrganization(
						organizationId));
			}
		}

		return selectedOrganizations;
	}

	@Override
	public int getPageDelta() {
		PortletPreferences portletPreferences =
			_announcementsRequestHelper.getPortletPreferences();

		return GetterUtil.getInteger(
			portletPreferences.getValue(
				"pageDelta", String.valueOf(SearchContainer.DEFAULT_DELTA)));
	}

	@Override
	public List<Role> getRoles() throws PortalException {
		if (!isCustomizeAnnouncementsDisplayed() ||
			StringUtil.equals(
				_announcementsRequestHelper.getPortletId(),
				AnnouncementsPortletKeys.ANNOUNCEMENTS_ADMIN)) {

			return AnnouncementsUtil.getRoles(
				_announcementsRequestHelper.getThemeDisplay());
		}

		List<Role> selectedRoles = new ArrayList<>();

		String[] selectedScopeRoleIds = StringUtil.split(
			_getSelectedScopeRoleIds());

		for (String selectedScopeRoleId : selectedScopeRoleIds) {
			Role role = RoleLocalServiceUtil.getRole(
				Long.valueOf(selectedScopeRoleId));

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

	@Override
	public String getTabs1Names() {
		return "unread,read";
	}

	@Override
	public String getTabs1PortletURL() {
		return PortletURLBuilder.createRenderURL(
			_announcementsRequestHelper.getLiferayPortletResponse()
		).setMVCRenderCommandName(
			"/announcements/view"
		).setTabs1(
			_announcementsRequestHelper.getTabs1()
		).buildString();
	}

	@Override
	public List<UserGroup> getUserGroups() throws PortalException {
		if (!isCustomizeAnnouncementsDisplayed() ||
			StringUtil.equals(
				_announcementsRequestHelper.getPortletId(),
				AnnouncementsPortletKeys.ANNOUNCEMENTS_ADMIN)) {

			return AnnouncementsUtil.getUserGroups(
				_announcementsRequestHelper.getThemeDisplay());
		}

		List<UserGroup> selectedUserGroups = new ArrayList<>();

		String[] selectedScopeUserGroupIds = StringUtil.split(
			_getSelectedScopeUserGroupIds());

		for (String selectedScopeUserGroupId : selectedScopeUserGroupIds) {
			long userGroupId = Long.valueOf(selectedScopeUserGroupId);

			if (UserGroupPermissionUtil.contains(
					_announcementsRequestHelper.getPermissionChecker(),
					userGroupId, ActionKeys.MANAGE_ANNOUNCEMENTS)) {

				selectedUserGroups.add(
					UserGroupLocalServiceUtil.getUserGroup(userGroupId));
			}
		}

		return selectedUserGroups;
	}

	@Override
	public UUID getUuid() {
		return _UUID;
	}

	@Override
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

	@Override
	public boolean isScopeGroupSelected(Group scopeGroup) {
		String selectedScopeGroupIds = _getSelectedScopeGroupIds();

		return selectedScopeGroupIds.contains(
			String.valueOf(scopeGroup.getGroupId()));
	}

	@Override
	public boolean isScopeOrganizationSelected(Organization organization) {
		String selectedScopeOrganizationIds =
			_getSelectedScopeOrganizationIds();

		return selectedScopeOrganizationIds.contains(
			String.valueOf(organization.getOrganizationId()));
	}

	@Override
	public boolean isScopeRoleSelected(Role role) {
		String selectedScopeRoleIds = _getSelectedScopeRoleIds();

		return selectedScopeRoleIds.contains(String.valueOf(role.getRoleId()));
	}

	@Override
	public boolean isScopeUserGroupSelected(UserGroup userGroup) {
		String selectedScopeUserGroupIds = _getSelectedScopeUserGroupIds();

		return selectedScopeUserGroupIds.contains(
			String.valueOf(userGroup.getUserGroupId()));
	}

	@Override
	public boolean isShowReadEntries() {
		String tabs1 = _announcementsRequestHelper.getTabs1();

		return tabs1.equals("read");
	}

	@Override
	public boolean isShowScopeName() {
		String mvcRenderCommandName = ParamUtil.getString(
			_announcementsRequestHelper.getRequest(), "mvcRenderCommandName");

		return mvcRenderCommandName.equals("/announcements/edit_entry");
	}

	@Override
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

	private String _getSelectedScopeGroupIds() {
		Layout layout = _announcementsRequestHelper.getLayout();

		return PrefsParamUtil.getString(
			_announcementsRequestHelper.getPortletPreferences(),
			_announcementsRequestHelper.getRequest(), "selectedScopeGroupIds",
			String.valueOf(layout.getGroupId()));
	}

	private String _getSelectedScopeOrganizationIds() {
		return PrefsParamUtil.getString(
			_announcementsRequestHelper.getPortletPreferences(),
			_announcementsRequestHelper.getRequest(),
			"selectedScopeOrganizationIds", StringPool.BLANK);
	}

	private String _getSelectedScopeRoleIds() {
		return PrefsParamUtil.getString(
			_announcementsRequestHelper.getPortletPreferences(),
			_announcementsRequestHelper.getRequest(), "selectedScopeRoleIds",
			StringPool.BLANK);
	}

	private String _getSelectedScopeUserGroupIds() {
		return PrefsParamUtil.getString(
			_announcementsRequestHelper.getPortletPreferences(),
			_announcementsRequestHelper.getRequest(),
			"selectedScopeUserGroupIds", StringPool.BLANK);
	}

	private static final UUID _UUID = UUID.fromString(
		"CD705D0E-7DB4-430C-9492-F1FA25ACE02E");

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultAnnouncementsDisplayContext.class);

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