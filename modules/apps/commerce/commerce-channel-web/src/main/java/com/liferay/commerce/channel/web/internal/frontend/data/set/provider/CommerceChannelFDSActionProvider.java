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

package com.liferay.commerce.channel.web.internal.frontend.data.set.provider;

import com.liferay.commerce.channel.web.internal.constants.CommerceChannelFDSNames;
import com.liferay.commerce.channel.web.internal.model.Channel;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.permission.CommerceChannelPermission;
import com.liferay.frontend.data.set.provider.FDSActionProvider;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletQName;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pedro Victor Silvestre
 */
@Component(
	property = "fds.data.provider.key=" + CommerceChannelFDSNames.CHANNEL,
	service = FDSActionProvider.class
)
public class CommerceChannelFDSActionProvider implements FDSActionProvider {

	@Override
	public List<DropdownItem> getDropdownItems(
			long groupId, HttpServletRequest httpServletRequest, Object model)
		throws PortalException {

		Channel channel = (Channel)model;

		return DropdownItemListBuilder.add(
			() -> _commerceChannelPermission.contains(
				PermissionThreadLocal.getPermissionChecker(),
				channel.getChannelId(), ActionKeys.UPDATE),
			dropdownItem -> {
				PortletURL portletURL = _portal.getControlPanelPortletURL(
					httpServletRequest, CPPortletKeys.COMMERCE_CHANNELS,
					PortletRequest.RENDER_PHASE);

				portletURL.setParameter("backURL", portletURL.toString());
				portletURL.setParameter(
					"commerceChannelId",
					String.valueOf(channel.getChannelId()));
				portletURL.setParameter(
					"mvcRenderCommandName",
					"/commerce_channels/edit_commerce_channel");

				dropdownItem.setHref(portletURL.toString());

				dropdownItem.setLabel(
					_language.get(httpServletRequest, "edit"));
			}
		).add(
			() -> _commerceChannelPermission.contains(
				PermissionThreadLocal.getPermissionChecker(),
				channel.getChannelId(), ActionKeys.PERMISSIONS),
			dropdownItem -> {
				dropdownItem.setHref(
					_getManageChannelPermissionsURL(
						channel, httpServletRequest));
				dropdownItem.setLabel(
					_language.get(httpServletRequest, "permissions"));
				dropdownItem.setTarget("modal-permissions");
			}
		).add(
			() -> _commerceChannelPermission.contains(
				PermissionThreadLocal.getPermissionChecker(),
				channel.getChannelId(), ActionKeys.DELETE),
			dropdownItem -> {
				PortletURL deleteURL = PortletURLBuilder.create(
					_portal.getControlPanelPortletURL(
						httpServletRequest, CPPortletKeys.COMMERCE_CHANNELS,
						PortletRequest.ACTION_PHASE)
				).setActionName(
					"/commerce_channels/edit_commerce_channel"
				).setCMD(
					Constants.DELETE
				).buildPortletURL();

				String redirect = ParamUtil.getString(
					httpServletRequest, "currentUrl",
					_portal.getCurrentURL(httpServletRequest));

				deleteURL.setParameter("redirect", redirect);

				deleteURL.setParameter(
					"commerceChannelId",
					String.valueOf(channel.getChannelId()));

				dropdownItem.setHref(deleteURL);
				dropdownItem.setLabel(
					_language.get(httpServletRequest, "delete"));
			}
		).build();
	}

	private PortletURL _getManageChannelPermissionsURL(
			Channel channel, HttpServletRequest httpServletRequest)
		throws PortalException {

		PortletURL portletURL = PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest,
				"com_liferay_portlet_configuration_web_portlet_" +
					"PortletConfigurationPortlet",
				ActionRequest.RENDER_PHASE)
		).setMVCPath(
			"/edit_permissions.jsp"
		).setParameter(
			PortletQName.PUBLIC_RENDER_PARAMETER_NAMESPACE + "backURL",
			ParamUtil.getString(
				httpServletRequest, "currentUrl",
				_portal.getCurrentURL(httpServletRequest))
		).setParameter(
			"modelResource", CommerceChannel.class.getName()
		).setParameter(
			"modelResourceDescription", channel.getName()
		).setParameter(
			"resourcePrimKey", channel.getChannelId()
		).buildPortletURL();

		try {
			portletURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (WindowStateException windowStateException) {
			throw new PortalException(windowStateException);
		}

		return portletURL;
	}

	@Reference
	private CommerceChannelPermission _commerceChannelPermission;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}