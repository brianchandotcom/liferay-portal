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

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.sites.kernel.util.SitesUtil;

/**
 * @author Adolfo Pérez
 */
public abstract class BasePortletLayoutFinder implements PortletLayoutFinder {

	@Override
	public Result find(ThemeDisplay themeDisplay, long groupId)
		throws PortalException {

		String[] portletIds = getPortletIds();

		if ((themeDisplay.getPlid() != LayoutConstants.DEFAULT_PLID) &&
			(groupId == themeDisplay.getScopeGroupId())) {

			try {
				Layout layout = LayoutLocalServiceUtil.getLayout(
					themeDisplay.getPlid());

				LayoutTypePortlet layoutTypePortlet =
					(LayoutTypePortlet)layout.getLayoutType();

				for (String portletId : portletIds) {
					if (!layoutTypePortlet.hasPortletId(portletId, false) ||
						!LayoutPermissionUtil.contains(
							themeDisplay.getPermissionChecker(), layout,
							ActionKeys.VIEW)) {

						continue;
					}

					portletId = getPortletId(layoutTypePortlet, portletId);

					return new ResultImpl(themeDisplay.getPlid(), portletId);
				}
			}
			catch (NoSuchLayoutException nsle) {

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(nsle, nsle);
				}
			}
		}

		Object[] plidAndPortletId = fetchPlidAndPortletId(
			themeDisplay.getPermissionChecker(), groupId, portletIds);

		if ((plidAndPortletId == null) &&
			SitesUtil.isUserGroupLayoutSetViewable(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroup())) {

			plidAndPortletId = fetchPlidAndPortletId(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), portletIds);
		}

		if (plidAndPortletId != null) {
			return new ResultImpl(
				(long)plidAndPortletId[0], (String)plidAndPortletId[1]);
		}

		StringBundler sb = new StringBundler(portletIds.length * 2 + 5);

		sb.append("{groupId=");
		sb.append(groupId);
		sb.append(", plid=");
		sb.append(themeDisplay.getPlid());

		for (String portletId : portletIds) {
			sb.append(", portletId=");
			sb.append(portletId);
		}

		sb.append("}");

		throw new NoSuchLayoutException(sb.toString());
	}

	protected Object[] fetchPlidAndPortletId(
			PermissionChecker permissionChecker, long groupId,
			String[] portletIds)
		throws PortalException {

		for (String portletId : portletIds) {
			long plid = PortalUtil.getPlidFromPortletId(groupId, portletId);

			if (plid == LayoutConstants.DEFAULT_PLID) {
				continue;
			}

			Layout layout = LayoutLocalServiceUtil.getLayout(plid);

			if (!LayoutPermissionUtil.contains(
					permissionChecker, layout, ActionKeys.VIEW)) {

				continue;
			}

			LayoutTypePortlet layoutTypePortlet =
				(LayoutTypePortlet)layout.getLayoutType();

			portletId = getPortletId(layoutTypePortlet, portletId);

			return new Object[] {plid, portletId};
		}

		return null;
	}

	protected String getPortletId(
		LayoutTypePortlet layoutTypePortlet, String portletId) {

		for (String curPortletId : layoutTypePortlet.getPortletIds()) {
			String curRootPortletId = PortletIdCodec.decodePortletName(
				curPortletId);

			if (portletId.equals(curRootPortletId)) {
				return curPortletId;
			}
		}

		return portletId;
	}

	protected abstract String[] getPortletIds();

	protected class ResultImpl implements PortletLayoutFinder.Result {

		public ResultImpl(long plid, String portletId) {
			_plid = plid;
			_portletId = portletId;
		}

		@Override
		public long getPlid() {
			return _plid;
		}

		@Override
		public String getPortletId() {
			return _portletId;
		}

		private final long _plid;
		private final String _portletId;

	}

	private static final Log _log = LogFactoryUtil.getLog(
		BasePortletLayoutFinder.class);

}