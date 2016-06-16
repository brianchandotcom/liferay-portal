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

package com.liferay.product.navigation.site.administration.display.context;

import com.liferay.application.list.GroupProvider;
import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.constants.ApplicationListWebKeys;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.application.list.display.context.logic.PanelCategoryHelper;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.product.navigation.product.menu.web.display.context.ProductMenuDisplayContext;
import com.liferay.product.navigation.site.administration.application.list.SiteAdministrationPanelCategory;
import com.liferay.product.navigation.site.administration.constants.SiteAdministrationWebKeys;
import com.liferay.site.util.GroupURLProvider;
import com.liferay.site.util.RecentGroupManager;

import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Julio Camarero
 */
public class SiteAdministrationPanelCategoryDisplayContext {

	public SiteAdministrationPanelCategoryDisplayContext(
			PortletRequest portletRequest, PortletResponse portletResponse,
			Group group)
		throws PortalException {

		_portletRequest = portletRequest;
		_portletResponse = portletResponse;

		if (group != null) {
			_group = group;
		}

		_groupProvider = (GroupProvider)portletRequest.getAttribute(
			ApplicationListWebKeys.GROUP_PROVIDER);
		_groupURLProvider = (GroupURLProvider)portletRequest.getAttribute(
			SiteAdministrationWebKeys.GROUP_URL_PROVIDER);
		_panelCategory = (PanelCategory)_portletRequest.getAttribute(
			ApplicationListWebKeys.PANEL_CATEGORY);
		_panelCategoryHelper =
			(PanelCategoryHelper)_portletRequest.getAttribute(
				ApplicationListWebKeys.PANEL_CATEGORY_HELPER);
		_recentGroupManager = (RecentGroupManager)portletRequest.getAttribute(
			SiteAdministrationWebKeys.RECENT_GROUP_MANAGER);
		_themeDisplay = (ThemeDisplay)_portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public Group getGroup() {
		if (_group != null) {
			return _group;
		}

		_group = _groupProvider.getGroup(
			PortalUtil.getHttpServletRequest(_portletRequest));

		if (_group != null) {
			updateLatentGroup(_group.getGroupId());
		}

		return _group;
	}

	public String getGroupName() throws PortalException {
		if (_groupName != null) {
			return _groupName;
		}

		Group group = getGroup();

		if (group == null) {
			_groupName = StringPool.BLANK;
		}
		else {
			if (group.isUser()) {
				if (group.getClassPK() == _themeDisplay.getUserId()) {
					_groupName = LanguageUtil.get(
						_themeDisplay.getRequest(), "my-site");
				}
				else {
					User user = UserLocalServiceUtil.getUser(
						group.getClassPK());

					_groupName = LanguageUtil.format(
						getResourceBundle(), "x-site", user.getFullName());
				}
			}
			else {
				_groupName = group.getDescriptiveName(
					_themeDisplay.getLocale());
			}
		}

		return _groupName;
	}

	public String getGroupURL() {
		if (_groupURL != null) {
			return _groupURL;
		}

		_groupURL = StringPool.BLANK;

		Group group = getGroup();

		return _groupURLProvider.getGroupURL(group, _portletRequest);
	}

	public String getGroupURL(boolean privateLayout) {
		Group group = getGroup();

		return _groupURLProvider.getGroupLayoutsURL(
			group, privateLayout, _portletRequest);
	}

	public String getLiveGroupLabel() {
		Group group = getGroup();

		if (group.isStagedRemotely()) {
			return "remote-live";
		}

		return "live";
	}

	public String getLiveGroupURL() {
		if (_liveGroupURL != null) {
			return _liveGroupURL;
		}

		_liveGroupURL = StringPool.BLANK;

		Group group = getGroup();

		if (group.isStagedRemotely()) {
			Layout layout = _themeDisplay.getLayout();

			try {
				_liveGroupURL = StagingUtil.getRemoteSiteURL(
					group, layout.isPrivateLayout());
			}
			catch (PortalException pe) {
				_log.error(pe);
			}
		}
		else if (group.isStagingGroup()) {
			Group liveGroup = StagingUtil.getLiveGroup(group.getGroupId());

			if (liveGroup != null) {
				_liveGroupURL = _groupURLProvider.getLiveGroupURL(
					liveGroup, _portletRequest);
			}
		}

		return _liveGroupURL;
	}

	public String getLogoURL() {
		if (Validator.isNotNull(_logoURL)) {
			return _logoURL;
		}

		_logoURL = StringPool.BLANK;

		Company company = _themeDisplay.getCompany();

		if (company.isSiteLogo()) {
			Group group = getGroup();

			if (group == null) {
				return _logoURL;
			}

			_logoURL = group.getLogoURL(_themeDisplay, false);
		}
		else {
			_logoURL = _themeDisplay.getCompanyLogo();
		}

		return _logoURL;
	}

	public List<Group> getMySites() throws PortalException {
		if (_mySites != null) {
			return _mySites;
		}

		User user = _themeDisplay.getUser();

		_mySites = user.getMySiteGroups(
			new String[] {
				Company.class.getName(), Group.class.getName(),
				Organization.class.getName()
			},
			PropsValues.MY_SITES_MAX_ELEMENTS);

		return _mySites;
	}

	public int getNotificationsCount() {
		if (_notificationsCount != null) {
			return _notificationsCount.intValue();
		}

		_notificationsCount = 0;

		Group group = getGroup();

		if (group == null) {
			return _notificationsCount;
		}

		SiteAdministrationPanelCategory siteAdministrationPanelCategory =
			(SiteAdministrationPanelCategory)_portletRequest.getAttribute(
				ApplicationListWebKeys.PANEL_CATEGORY);

		_notificationsCount = _panelCategoryHelper.getNotificationsCount(
			siteAdministrationPanelCategory.getKey(),
			_themeDisplay.getPermissionChecker(), getGroup(),
			_themeDisplay.getUser());

		return _notificationsCount;
	}

	public PanelCategory getPanelCategory() {
		return _panelCategory;
	}

	public String getStagingGroupURL() {
		if (_stagingGroupURL != null) {
			return _stagingGroupURL;
		}

		_stagingGroupURL = StringPool.BLANK;

		Group group = getGroup();

		if (!group.isStagedRemotely() && group.hasStagingGroup()) {
			Group stagingGroup = StagingUtil.getStagingGroup(
				group.getGroupId());

			if (stagingGroup != null) {
				_stagingGroupURL = _groupURLProvider.getGroupURL(
					stagingGroup, _portletRequest);
			}
		}

		return _stagingGroupURL;
	}

	public String getStagingLabel() throws PortalException {
		if (_stagingLabel != null) {
			return _stagingLabel;
		}

		Group group = getGroup();

		_stagingLabel = StringPool.BLANK;

		if (isShowStagingInfo()) {
			if (group.isStagingGroup()) {
				_stagingLabel = "staging";
			}
			else if (group.hasStagingGroup()) {
				_stagingLabel = "live";
			}
		}

		return _stagingLabel;
	}

	public boolean isCollapsedPanel() throws PortalException {
		if (_collapsedPanel != null) {
			return _collapsedPanel;
		}

		ProductMenuDisplayContext productMenuDisplayContext =
			new ProductMenuDisplayContext(_portletRequest, _portletResponse);

		_collapsedPanel = Objects.equals(
			_panelCategory.getKey(),
			productMenuDisplayContext.getRootPanelCategoryKey());

		return _collapsedPanel;
	}

	public boolean isDisplaySiteLink() {
		Group group = getGroup();

		if (group.hasPrivateLayouts() || group.hasPublicLayouts()) {
			return true;
		}

		return false;
	}

	public boolean isShowSiteAdministration() throws PortalException {
		Group group = getGroup();

		if (group == null) {
			return false;
		}

		if (GroupPermissionUtil.contains(
				_themeDisplay.getPermissionChecker(), group,
				ActionKeys.VIEW_SITE_ADMINISTRATION)) {

			return true;
		}

		return false;
	}

	public boolean isShowSiteSelector() throws PortalException {
		List<Group> mySites = getMySites();

		if (mySites.isEmpty()) {
			return false;
		}

		return true;
	}

	public boolean isShowStagingInfo() throws PortalException {
		if (_showStagingInfo != null) {
			return _showStagingInfo.booleanValue();
		}

		_showStagingInfo = false;

		Group group = getGroup();

		if (group == null) {
			return _showStagingInfo;
		}

		if (!group.isStaged() && !group.isStagingGroup()) {
			return _showStagingInfo;
		}

		if (!hasStagingPermission()) {
			return _showStagingInfo;
		}

		_showStagingInfo = true;

		return _showStagingInfo;
	}

	protected String getGroupAdministrationURL(Group group) {
		PortletURL groupAdministrationURL = null;

		if (_panelCategoryHelper == null) {
			return null;
		}

		String portletId = _panelCategoryHelper.getFirstPortletId(
			PanelCategoryKeys.SITE_ADMINISTRATION,
			_themeDisplay.getPermissionChecker(), group);

		if (Validator.isNotNull(portletId)) {
			groupAdministrationURL = PortalUtil.getControlPanelPortletURL(
				_portletRequest, group, portletId, 0, 0,
				PortletRequest.RENDER_PHASE);

			if (groupAdministrationURL != null) {
				return groupAdministrationURL.toString();
			}
		}

		return null;
	}

	protected ResourceBundle getResourceBundle() {
		return ResourceBundleUtil.getBundle(
			"content.Language", _themeDisplay.getLocale(), getClass());
	}

	protected boolean hasStagingPermission() throws PortalException {
		if (!GroupPermissionUtil.contains(
				_themeDisplay.getPermissionChecker(), getGroup(),
				ActionKeys.MANAGE_STAGING)) {

			return false;
		}

		if (!GroupPermissionUtil.contains(
				_themeDisplay.getPermissionChecker(), getGroup(),
				ActionKeys.PUBLISH_STAGING)) {

			return false;
		}

		if (!GroupPermissionUtil.contains(
				_themeDisplay.getPermissionChecker(), getGroup(),
				ActionKeys.VIEW_STAGING)) {

			return false;
		}

		return true;
	}

	protected void updateLatentGroup(long groupId) {
		if (groupId <= 0) {
			return;
		}

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			_portletRequest);

		_recentGroupManager.addRecentGroup(request, groupId);

		_groupProvider.setGroup(request, _group);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SiteAdministrationPanelCategoryDisplayContext.class);

	private Boolean _collapsedPanel;
	private Group _group;
	private String _groupName;
	private final GroupProvider _groupProvider;
	private String _groupURL;
	private final GroupURLProvider _groupURLProvider;
	private String _liveGroupURL;
	private String _logoURL;
	private List<Group> _mySites;
	private Integer _notificationsCount;
	private final PanelCategory _panelCategory;
	private final PanelCategoryHelper _panelCategoryHelper;
	private final PortletRequest _portletRequest;
	private final PortletResponse _portletResponse;
	private final RecentGroupManager _recentGroupManager;
	private Boolean _showStagingInfo;
	private String _stagingGroupURL;
	private String _stagingLabel;
	private final ThemeDisplay _themeDisplay;

}