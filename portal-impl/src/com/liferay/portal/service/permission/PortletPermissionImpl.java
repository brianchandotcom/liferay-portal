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

package com.liferay.portal.service.permission;

import com.liferay.exportimport.kernel.staging.permission.StagingPermissionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.model.impl.VirtualLayout;
import com.liferay.portal.kernel.portlet.ControlPanelEntry;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.service.permission.PortletPermission;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.PortletCategoryKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.sites.kernel.util.SitesUtil;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.portlet.PortletMode;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class PortletPermissionImpl implements PortletPermission {

	@Override
	public void check(
			PermissionChecker permissionChecker, Layout layout,
			String portletId, String actionId)
		throws PortalException {

		if (!contains(
				permissionChecker, 0, layout, portletId, actionId,
				_STRICT_DEFAULT)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, Portlet.class.getName(), portletId,
				actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, Layout layout,
			String portletId, String actionId, boolean strict)
		throws PortalException {

		if (!contains(
				permissionChecker, 0, layout, portletId, actionId, strict)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, Portlet.class.getName(), portletId,
				actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long groupId, Layout layout,
			String portletId, String actionId)
		throws PortalException {

		if (!contains(
				permissionChecker, groupId, layout, portletId, actionId,
				_STRICT_DEFAULT)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, Portlet.class.getName(), portletId,
				actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long groupId, Layout layout,
			String portletId, String actionId, boolean strict)
		throws PortalException {

		check(
			permissionChecker, groupId, layout, portletId, actionId, strict,
			_CHECK_STAGING_PERMISSION_DEFAULT);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long groupId, Layout layout,
			String portletId, String actionId, boolean strict,
			boolean checkStagingPermission)
		throws PortalException {

		if (!contains(
				permissionChecker, groupId, layout, portletId, actionId, strict,
				checkStagingPermission)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, Portlet.class.getName(), portletId,
				actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long groupId, long plid,
			String portletId, String actionId)
		throws PortalException {

		check(
			permissionChecker, groupId, plid, portletId, actionId,
			_STRICT_DEFAULT);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long groupId, long plid,
			String portletId, String actionId, boolean strict)
		throws PortalException {

		if (!contains(
				permissionChecker, groupId, plid, portletId, actionId,
				strict)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, Portlet.class.getName(), portletId,
				actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long plid, String portletId,
			String actionId)
		throws PortalException {

		check(permissionChecker, plid, portletId, actionId, _STRICT_DEFAULT);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long plid, String portletId,
			String actionId, boolean strict)
		throws PortalException {

		if (!contains(permissionChecker, plid, portletId, actionId, strict)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, Portlet.class.getName(), portletId,
				actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, String portletId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, portletId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, Portlet.class.getName(), portletId,
				actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, Layout layout, Portlet portlet,
			String actionId)
		throws PortalException {

		return contains(
			permissionChecker, layout, portlet, actionId, _STRICT_DEFAULT);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, Layout layout, Portlet portlet,
			String actionId, boolean strict)
		throws PortalException {

		return contains(
			permissionChecker, 0, layout, portlet, actionId, strict);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, Layout layout,
			String portletId, String actionId)
		throws PortalException {

		return contains(
			permissionChecker, layout, portletId, actionId, _STRICT_DEFAULT);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, Layout layout,
			String portletId, String actionId, boolean strict)
		throws PortalException {

		return contains(
			permissionChecker, 0, layout, portletId, actionId, strict);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long groupId, Layout layout,
			Portlet portlet, String actionId)
		throws PortalException {

		return contains(
			permissionChecker, groupId, layout, portlet, actionId,
			_STRICT_DEFAULT);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long groupId, Layout layout,
			Portlet portlet, String actionId, boolean strict)
		throws PortalException {

		if (portlet.isUndeployedPortlet()) {
			return false;
		}

		return contains(
			permissionChecker, groupId, layout, portlet, actionId, strict,
			_CHECK_STAGING_PERMISSION_DEFAULT);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long groupId, Layout layout,
			Portlet portlet, String actionId, boolean strict,
			boolean checkStagingPermission)
		throws PortalException {

		long plid = -1;
		long layoutMvccVersion = -1;

		if (layout != null) {
			plid = layout.getPlid();
			layoutMvccVersion = layout.getMvccVersion();
		}

		Map<Object, Object> permissionChecksMap =
			permissionChecker.getPermissionChecksMap();

		CacheKey cacheKey = new CacheKey(
			groupId, plid, layoutMvccVersion, portlet.getPortletId(),
			portlet.getMvccVersion(), actionId, strict, checkStagingPermission);

		Boolean contains = (Boolean)permissionChecksMap.get(cacheKey);

		if (contains == null) {
			contains = _contains(
				permissionChecker, groupId, layout, portlet, actionId, strict,
				checkStagingPermission);

			permissionChecksMap.put(cacheKey, contains);
		}

		return contains;
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long groupId, Layout layout,
			String portletId, String actionId)
		throws PortalException {

		return contains(
			permissionChecker, groupId, layout, portletId, actionId,
			_STRICT_DEFAULT);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long groupId, Layout layout,
			String portletId, String actionId, boolean strict)
		throws PortalException {

		return contains(
			permissionChecker, groupId, layout, portletId, actionId, strict,
			_CHECK_STAGING_PERMISSION_DEFAULT);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long groupId, Layout layout,
			String portletId, String actionId, boolean strict,
			boolean checkStagingPermission)
		throws PortalException {

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			permissionChecker.getCompanyId(), portletId);

		if ((portlet == null) || portlet.isUndeployedPortlet()) {
			return false;
		}

		return contains(
			permissionChecker, groupId, layout, portlet, actionId, strict,
			checkStagingPermission);
	}

	public boolean contains(
			PermissionChecker permissionChecker, long groupId, long plid,
			Portlet portlet, String actionId)
		throws PortalException {

		Layout layout = LayoutLocalServiceUtil.fetchLayout(plid);

		return contains(
			permissionChecker, groupId, layout, portlet, actionId,
			_STRICT_DEFAULT);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long groupId, long plid,
			Portlet portlet, String actionId, boolean strict)
		throws PortalException {

		Layout layout = LayoutLocalServiceUtil.fetchLayout(plid);

		return contains(
			permissionChecker, groupId, layout, portlet, actionId, strict);
	}

	public boolean contains(
			PermissionChecker permissionChecker, long groupId, long plid,
			String portletId, String actionId)
		throws PortalException {

		Layout layout = LayoutLocalServiceUtil.fetchLayout(plid);

		return contains(
			permissionChecker, groupId, layout, portletId, actionId,
			_STRICT_DEFAULT);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long groupId, long plid,
			String portletId, String actionId, boolean strict)
		throws PortalException {

		Layout layout = LayoutLocalServiceUtil.fetchLayout(plid);

		return contains(
			permissionChecker, groupId, layout, portletId, actionId, strict);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long plid, Portlet portlet,
			String actionId)
		throws PortalException {

		Layout layout = LayoutLocalServiceUtil.fetchLayout(plid);

		return contains(
			permissionChecker, layout, portlet, actionId, _STRICT_DEFAULT);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long plid, Portlet portlet,
			String actionId, boolean strict)
		throws PortalException {

		Layout layout = LayoutLocalServiceUtil.fetchLayout(plid);

		return contains(
			permissionChecker, 0, layout, portlet, actionId, strict);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long plid, String portletId,
			String actionId)
		throws PortalException {

		Layout layout = LayoutLocalServiceUtil.fetchLayout(plid);

		return contains(
			permissionChecker, layout, portletId, actionId, _STRICT_DEFAULT);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long plid, String portletId,
			String actionId, boolean strict)
		throws PortalException {

		Layout layout = LayoutLocalServiceUtil.fetchLayout(plid);

		return contains(
			permissionChecker, 0, layout, portletId, actionId, strict);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, String portletId,
			String actionId)
		throws PortalException {

		return contains(permissionChecker, 0, portletId, actionId);
	}

	@Override
	public String getPrimaryKey(long plid, String portletId) {
		return String.valueOf(plid).concat(
			PortletConstants.LAYOUT_SEPARATOR).concat(portletId);
	}

	@Override
	public boolean hasAccessPermission(
			PermissionChecker permissionChecker, long scopeGroupId,
			Layout layout, Portlet portlet, PortletMode portletMode)
		throws PortalException {

		if ((layout != null) && layout.isTypeControlPanel()) {
			String category = portlet.getControlPanelEntryCategory();

			if (StringUtil.startsWith(
					category, PortletCategoryKeys.SITE_ADMINISTRATION)) {

				layout = null;
			}
		}

		boolean access = contains(
			permissionChecker, scopeGroupId, layout, portlet, ActionKeys.VIEW);

		if (access && portletMode.equals(PortletMode.EDIT)) {
			access = contains(
				permissionChecker, scopeGroupId, layout, portlet,
				ActionKeys.PREFERENCES);
		}

		return access;
	}

	@Override
	public boolean hasConfigurationPermission(
			PermissionChecker permissionChecker, long groupId, Layout layout,
			String actionId)
		throws PortalException {

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		for (Portlet portlet : layoutTypePortlet.getAllPortlets(false)) {
			if (contains(
					permissionChecker, groupId, layout, portlet.getPortletId(),
					actionId)) {

				return true;
			}

			if (contains(
					permissionChecker, groupId, null,
					portlet.getRootPortletId(), actionId)) {

				return true;
			}
		}

		return false;
	}

	@Override
	public boolean hasControlPanelAccessPermission(
			PermissionChecker permissionChecker, long groupId,
			Collection<Portlet> portlets)
		throws PortalException {

		for (Portlet portlet : portlets) {
			if (hasControlPanelAccessPermission(
					permissionChecker, groupId, portlet)) {

				return true;
			}
		}

		return false;
	}

	@Override
	public boolean hasControlPanelAccessPermission(
			PermissionChecker permissionChecker, long scopeGroupId,
			Portlet portlet)
		throws PortalException {

		Group group = GroupLocalServiceUtil.getGroup(scopeGroupId);

		ControlPanelEntry controlPanelEntry =
			portlet.getControlPanelEntryInstance();

		try {
			return controlPanelEntry.hasAccessPermission(
				permissionChecker, group, portlet);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Cannot process control panel access permission", e);
			}

			return false;
		}
	}

	@Override
	public boolean hasControlPanelAccessPermission(
			PermissionChecker permissionChecker, long scopeGroupId,
			String portletId)
		throws PortalException {

		Portlet portlet = PortletLocalServiceUtil.getPortletById(portletId);

		return hasControlPanelAccessPermission(
			permissionChecker, scopeGroupId, portlet);
	}

	@Override
	public boolean hasLayoutManagerPermission(
		String portletId, String actionId) {

		try {
			portletId = PortletIdCodec.decodePortletName(portletId);

			List<String> layoutManagerActions =
				ResourceActionsUtil.getPortletResourceLayoutManagerActions(
					portletId);

			return layoutManagerActions.contains(actionId);
		}
		catch (Exception e) {
			_log.error(e, e);

			return false;
		}
	}

	protected boolean hasAddToPagePermission(
			PermissionChecker permissionChecker, Layout layout,
			String portletId, String actionId)
		throws PortalException {

		if (LayoutPermissionUtil.contains(
				permissionChecker, layout, ActionKeys.CUSTOMIZE)) {

			return contains(
				permissionChecker, portletId, ActionKeys.ADD_TO_PAGE);
		}

		return false;
	}

	protected boolean hasConfigurePermission(
			PermissionChecker permissionChecker, Layout layout, Portlet portlet,
			String actionId)
		throws PortalException {

		if (!actionId.equals(ActionKeys.CONFIGURATION) &&
			!actionId.equals(ActionKeys.PREFERENCES) &&
			!actionId.equals(ActionKeys.GUEST_PREFERENCES)) {

			return false;
		}

		if (portlet.isPreferencesUniquePerLayout()) {
			return LayoutPermissionUtil.contains(
				permissionChecker, layout, ActionKeys.CONFIGURE_PORTLETS);
		}

		return GroupPermissionUtil.contains(
			permissionChecker, layout.getGroupId(),
			ActionKeys.CONFIGURE_PORTLETS);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #hasConfigurePermission(
	 *             PermissionChecker, Layout, Portlet, String)}
	 */
	@Deprecated
	protected boolean hasConfigurePermission(
			PermissionChecker permissionChecker, Layout layout,
			String portletId, String actionId)
		throws PortalException {

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			layout.getCompanyId(), portletId);

		return hasConfigurePermission(
			permissionChecker, layout, portlet, actionId);
	}

	protected boolean hasCustomizePermission(
			PermissionChecker permissionChecker, Layout layout, Portlet portlet,
			String actionId)
		throws PortalException {

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		if (layoutTypePortlet.isCustomizedView() &&
			layoutTypePortlet.isPortletCustomizable(portlet.getPortletId()) &&
			LayoutPermissionUtil.contains(
				permissionChecker, layout, ActionKeys.CUSTOMIZE)) {

			if (actionId.equals(ActionKeys.VIEW)) {
				return true;
			}
			else if (actionId.equals(ActionKeys.CONFIGURATION)) {
				if (portlet.isInstanceable() ||
					portlet.isPreferencesUniquePerLayout()) {

					return true;
				}
			}
		}

		return false;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #hasCustomizePermission(
	 *             PermissionChecker, Layout, Portlet, String)}
	 */
	@Deprecated
	protected boolean hasCustomizePermission(
			PermissionChecker permissionChecker, Layout layout,
			String portletId, String actionId)
		throws PortalException {

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			layout.getCompanyId(), portletId);

		return hasCustomizePermission(
			permissionChecker, layout, portlet, actionId);
	}

	private boolean _contains(
			PermissionChecker permissionChecker, long groupId, Layout layout,
			Portlet portlet, String actionId, boolean strict,
			boolean checkStagingPermission)
		throws PortalException {

		String portletId = portlet.getPortletId();

		String name = null;
		String resourcePermissionPrimKey = null;

		if (layout == null) {
			name = portletId;
			resourcePermissionPrimKey = portletId;

			return permissionChecker.hasPermission(
				groupId, name, resourcePermissionPrimKey, actionId);
		}

		Group group = null;

		if (groupId > 0) {
			group = GroupLocalServiceUtil.fetchGroup(groupId);
		}

		if (group == null) {
			group = layout.getGroup();

			groupId = layout.getGroupId();
		}

		if ((group.isControlPanel() || layout.isTypeControlPanel()) &&
			actionId.equals(ActionKeys.VIEW)) {

			return true;
		}

		if (layout instanceof VirtualLayout) {
			if (layout.isCustomizable() && !actionId.equals(ActionKeys.VIEW)) {
				if (actionId.equals(ActionKeys.ADD_TO_PAGE)) {
					return hasAddToPagePermission(
						permissionChecker, layout, portletId, actionId);
				}

				return hasCustomizePermission(
					permissionChecker, layout, portlet, actionId);
			}

			VirtualLayout virtualLayout = (VirtualLayout)layout;

			layout = virtualLayout.getSourceLayout();
		}

		if (!group.isLayoutSetPrototype() &&
			actionId.equals(ActionKeys.CONFIGURATION) &&
			!SitesUtil.isLayoutUpdateable(layout)) {

			return false;
		}

		String rootPortletId = PortletIdCodec.decodePortletName(portletId);

		if (checkStagingPermission) {
			Boolean hasPermission = StagingPermissionUtil.hasPermission(
				permissionChecker, group, rootPortletId, groupId, rootPortletId,
				actionId);

			if (hasPermission != null) {
				return hasPermission.booleanValue();
			}
		}

		resourcePermissionPrimKey = getPrimaryKey(layout.getPlid(), portletId);

		if (strict) {
			return permissionChecker.hasPermission(
				groupId, rootPortletId, resourcePermissionPrimKey, actionId);
		}

		if (hasConfigurePermission(
				permissionChecker, layout, portlet, actionId) ||
			hasCustomizePermission(
				permissionChecker, layout, portlet, actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			group, rootPortletId, resourcePermissionPrimKey, actionId);
	}

	private static final boolean _CHECK_STAGING_PERMISSION_DEFAULT = true;

	private static final boolean _STRICT_DEFAULT = false;

	private static final Log _log = LogFactoryUtil.getLog(
		PortletPermissionImpl.class);

	private static class CacheKey {

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}

			if (!(obj instanceof CacheKey)) {
				return false;
			}

			CacheKey cacheKey = (CacheKey)obj;

			if ((_groupId == cacheKey._groupId) && (_plid == cacheKey._plid) &&
				(_layoutMvccVersion == cacheKey._layoutMvccVersion) &&
				Objects.equals(_portletId, cacheKey._portletId) &&
				(_portletMvccVersion == cacheKey._portletMvccVersion) &&
				Objects.equals(_actionId, cacheKey._actionId) &&
				(_strict == cacheKey._strict) &&
				(_checkStagingPermission == cacheKey._checkStagingPermission)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			int hash = HashUtil.hash(0, _groupId);

			hash = HashUtil.hash(hash, _plid);
			hash = HashUtil.hash(hash, _layoutMvccVersion);
			hash = HashUtil.hash(hash, _portletId);
			hash = HashUtil.hash(hash, _portletMvccVersion);
			hash = HashUtil.hash(hash, _actionId);
			hash = HashUtil.hash(hash, _strict);

			return HashUtil.hash(hash, _checkStagingPermission);
		}

		private CacheKey(
			long groupId, long plid, long layoutMvccVersion, String portletId,
			long portletMvccVersion, String actionId, boolean strict,
			boolean checkStagingPermission) {

			_groupId = groupId;
			_plid = plid;
			_layoutMvccVersion = layoutMvccVersion;
			_portletId = portletId;
			_portletMvccVersion = portletMvccVersion;
			_actionId = actionId;
			_strict = strict;
			_checkStagingPermission = checkStagingPermission;
		}

		private final String _actionId;
		private final boolean _checkStagingPermission;
		private final long _groupId;
		private final long _layoutMvccVersion;
		private final long _plid;
		private final String _portletId;
		private final long _portletMvccVersion;
		private final boolean _strict;

	}

}