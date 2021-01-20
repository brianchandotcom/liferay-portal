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

package com.liferay.layout.content.page.editor.web.internal.portlet.configuration.icon;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.configuration.kernel.util.PortletConfigurationApplicationType;

import java.util.Objects;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = PortletConfigurationIcon.class)
public class PortletPermissionsPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	@Override
	public String getMessage(PortletRequest portletRequest) {
		return LanguageUtil.get(
			getResourceBundle(getLocale(portletRequest)), "permissions");
	}

	@Override
	public String getOnClick(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		try {
			StringBundler sb = new StringBundler(5);

			sb.append("Liferay.Util.openModal({title: '");
			sb.append(HtmlUtil.escapeJS(getMessage(portletRequest)));
			sb.append("', url: '");
			sb.append(_generatePermissionURL(portletRequest));
			sb.append("'});");

			return sb.toString();
		}
		catch (Exception exception) {
		}

		return StringPool.BLANK;
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return "javascript:;";
	}

	@Override
	public double getWeight() {
		return 13.0;
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		HttpServletRequest originalHttpServletRequest =
			_portal.getOriginalServletRequest(
				_portal.getHttpServletRequest(portletRequest));

		String layoutMode = ParamUtil.getString(
			originalHttpServletRequest, "p_l_mode", Constants.VIEW);

		if (!Objects.equals(layoutMode, Constants.EDIT)) {
			return false;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		String portletId = portletDisplay.getId();

		if (Validator.isNotNull(portletDisplay.getPortletResource())) {
			portletId = portletDisplay.getPortletResource();
		}

		boolean showPermissionsIcon = false;

		Layout layout = themeDisplay.getLayout();

		Group group = themeDisplay.getScopeGroup();

		if (_STAGING_LIVE_GROUP_LOCKING_ENABLED || !group.hasStagingGroup()) {
			try {
				if (PortletPermissionUtil.contains(
						themeDisplay.getPermissionChecker(), layout, portletId,
						ActionKeys.PERMISSIONS)) {

					showPermissionsIcon = true;
				}
			}
			catch (PortalException portalException) {

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(portalException, portalException);
				}

				showPermissionsIcon = false;
			}
		}

		if (layout.isLayoutPrototypeLinkActive()) {
			showPermissionsIcon = false;
		}

		if (layout.isTypeControlPanel()) {
			showPermissionsIcon = false;
		}

		if (isEmbeddedPersonalApplicationLayout(layout)) {
			showPermissionsIcon = false;
		}

		return showPermissionsIcon;
	}

	@Override
	public boolean isShowInEditMode(PortletRequest portletRequest) {
		return true;
	}

	@Override
	public boolean isUseDialog() {
		return false;
	}

	@Reference(unbind = "-")
	protected void setPortletLocalService(
		PortletLocalService portletLocalService) {

		_portletLocalService = portletLocalService;
	}

	private String _generatePermissionURL(PortletRequest portletRequest)
		throws PortalException, WindowStateException {

		String returnToFullPageURL = ParamUtil.getString(
			portletRequest, "returnToFullPageURL");

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			portletRequest,
			PortletConfigurationApplicationType.PortletConfiguration.CLASS_NAME,
			PortletProvider.Action.VIEW);

		portletURL.setParameter("mvcPath", "/edit_permissions.jsp");
		portletURL.setParameter("returnToFullPageURL", returnToFullPageURL);
		portletURL.setParameter(
			"portletConfiguration", Boolean.TRUE.toString());

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		portletURL.setParameter("portletResource", portletDisplay.getId());
		portletURL.setParameter(
			"resourcePrimKey",
			PortletPermissionUtil.getPrimaryKey(
				themeDisplay.getPlid(), portletDisplay.getId()));

		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return portletURL.toString();
	}

	private static final boolean _STAGING_LIVE_GROUP_LOCKING_ENABLED =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.STAGING_LIVE_GROUP_LOCKING_ENABLED));

	private static final Log _log = LogFactoryUtil.getLog(
		PortletPermissionsPortletConfigurationIcon.class);

	@Reference
	private Portal _portal;

	private PortletLocalService _portletLocalService;

}