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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.sites.kernel.util.SitesUtil;

import java.util.Arrays;
import java.util.List;

import javax.portlet.PortletPreferences;

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

		Group scopeGroup = themeDisplay.getScopeGroup();

		if ((plidAndPortletId == null) &&
			(scopeGroup.isSite() ||
			 SitesUtil.isUserGroupLayoutSetViewable(
				 themeDisplay.getPermissionChecker(), scopeGroup))) {

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
			ObjectValuePair<Long, String> plidAndPortletIdObjectValuePair =
				_getPlidPortletIdObjectValuePair(groupId, portletId);

			long plid = plidAndPortletIdObjectValuePair.getKey();

			if (plid == LayoutConstants.DEFAULT_PLID) {
				continue;
			}

			if (!LayoutPermissionUtil.contains(
					permissionChecker, LayoutLocalServiceUtil.getLayout(plid),
					ActionKeys.VIEW)) {

				continue;
			}

			return new Object[] {
				plid, plidAndPortletIdObjectValuePair.getValue()
			};
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

		return null;
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

	private ObjectValuePair<Long, String> _getPlidPortletIdObjectValuePair(
			long groupId, long scopeGroupId, String portletId)
		throws PortalException {

		for (boolean privateLayout : Arrays.asList(false, true)) {
			List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
				groupId, privateLayout, LayoutConstants.TYPE_PORTLET);

			for (Layout layout : layouts) {
				LayoutTypePortlet layoutTypePortlet =
					(LayoutTypePortlet)layout.getLayoutType();

				String candidatePortletId = getPortletId(
					layoutTypePortlet, portletId);

				if (Validator.isNotNull(candidatePortletId) &&
					(_getScopeGroupId(layout, candidatePortletId) ==
						scopeGroupId)) {

					return new ObjectValuePair<>(
						layout.getPlid(), candidatePortletId);
				}
			}
		}

		return new ObjectValuePair<>(
			LayoutConstants.DEFAULT_PLID, StringPool.BLANK);
	}

	private ObjectValuePair<Long, String> _getPlidPortletIdObjectValuePair(
			long scopeGroupId, String portletId)
		throws PortalException {

		Group group = GroupLocalServiceUtil.getGroup(scopeGroupId);

		long groupId = group.getGroupId();

		if (group.isLayout()) {
			Layout scopeLayout = LayoutLocalServiceUtil.getLayout(
				group.getClassPK());

			groupId = scopeLayout.getGroupId();
		}

		return _getPlidPortletIdObjectValuePair(
			groupId, scopeGroupId, portletId);
	}

	private long _getScopeGroupId(Layout layout, String portletId)
		throws PortalException {

		PortletPreferences portletSetup =
			PortletPreferencesFactoryUtil.getStrictLayoutPortletSetup(
				layout, portletId);

		String scopeType = GetterUtil.getString(
			portletSetup.getValue("lfrScopeType", null));

		if (Validator.isNull(scopeType)) {
			return layout.getGroupId();
		}

		if (scopeType.equals("company")) {
			Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(
				layout.getCompanyId());

			return companyGroup.getGroupId();
		}

		String scopeLayoutUuid = GetterUtil.getString(
			portletSetup.getValue("lfrScopeLayoutUuid", null));

		Layout scopeLayout = LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
			scopeLayoutUuid, layout.getGroupId(), layout.isPrivateLayout());

		Group scopeGroup = scopeLayout.getScopeGroup();

		return scopeGroup.getGroupId();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BasePortletLayoutFinder.class);

}