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

package com.liferay.roles.admin.web.internal.change.tracking.spi.display;

import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.change.tracking.spi.display.context.DisplayContext;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.io.Writer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = CTDisplayRenderer.class)
public class ResourcePermissionCTDisplayRenderer
	implements CTDisplayRenderer<ResourcePermission> {

	@Override
	public String getEditURL(
		HttpServletRequest httpServletRequest,
		ResourcePermission resourcePermission) {

		return null;
	}

	@Override
	public Class<ResourcePermission> getModelClass() {
		return ResourcePermission.class;
	}

	@Override
	public String getTitle(Locale locale, ResourcePermission resourcePermission)
		throws PortalException {

		List<String> arguments = new ArrayList<>(4);

		Role role = _roleLocalService.getRole(resourcePermission.getRoleId());

		arguments.add(role.getTitle(locale));

		String name = resourcePermission.getName();

		if (name.indexOf(CharPool.PERIOD) < 0) {
			String primKey = resourcePermission.getPrimKey();

			int index = primKey.indexOf(PortletConstants.LAYOUT_SEPARATOR);

			if (index > 0) {
				Layout layout = _layoutLocalService.fetchLayout(
					GetterUtil.getLong(primKey.substring(0, index)));

				if (layout != null) {
					Group group = _groupLocalService.getGroup(
						layout.getGroupId());

					arguments.add(group.getDescriptiveName(locale));

					arguments.add(layout.getName(locale));
				}
			}

			Portlet portlet = _portletLocalService.fetchPortletById(
				resourcePermission.getCompanyId(),
				PortletIdCodec.decodePortletName(name));

			if (portlet != null) {
				arguments.add(_portal.getPortletTitle(portlet, locale));
			}
		}
		else {
			String resourceName = _resourceActions.getModelResource(
				locale, resourcePermission.getName());

			if (!resourceName.startsWith(
					_resourceActions.getModelResourceNamePrefix())) {

				arguments.add(resourceName);
			}

			int scope = resourcePermission.getScope();

			if (scope == ResourceConstants.SCOPE_COMPANY) {
				arguments.add(
					_language.get(locale, "all-sites-and-asset-libraries"));
			}
			else if (scope == ResourceConstants.SCOPE_GROUP) {
				try {
					Group group = _groupLocalService.getGroup(
						resourcePermission.getPrimKeyId());

					arguments.add(group.getDescriptiveName(locale));
				}
				catch (PortalException portalException) {
					if (_log.isWarnEnabled()) {
						_log.warn(portalException, portalException);
					}
				}
			}
		}

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, ResourcePermissionCTDisplayRenderer.class);

		if (arguments.size() == 1) {
			return _language.format(
				resourceBundle, "x-permissions",
				arguments.toArray(new String[0]), false);
		}
		else if (arguments.size() == 2) {
			return _language.format(
				resourceBundle, "x-permissions-for-x",
				arguments.toArray(new String[0]), false);
		}
		else if (arguments.size() == 3) {
			return _language.format(
				resourceBundle, "x-permissions-for-x-x",
				arguments.toArray(new String[0]), false);
		}
		else {
			return _language.format(
				resourceBundle, "x-permissions-for-x-x-x",
				arguments.toArray(new String[0]), false);
		}
	}

	@Override
	public String getTypeName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, ResourcePermissionCTDisplayRenderer.class);

		return _language.get(
			resourceBundle,
			"model.resource.com.liferay.portal.kernel.model." +
				"ResourcePermission");
	}

	@Override
	public boolean isHideable(ResourcePermission resourcePermission) {
		return true;
	}

	@Override
	public void render(DisplayContext<ResourcePermission> displayContext)
		throws Exception {

		Map<String, ResourceAction> resourceActionMap = new TreeMap<>();

		Locale locale = _portal.getLocale(
			displayContext.getHttpServletRequest());
		ResourcePermission resourcePermission = displayContext.getModel();

		for (ResourceAction resourceAction :
				_resourceActionLocalService.getResourceActions(
					resourcePermission.getName())) {

			String actionLabel = _resourceActions.getAction(
				locale, resourceAction.getActionId());

			resourceActionMap.put(actionLabel, resourceAction);
		}

		HttpServletResponse httpServletResponse =
			displayContext.getHttpServletResponse();

		Writer writer = httpServletResponse.getWriter();

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, ResourcePermissionCTDisplayRenderer.class);

		String granted = _language.get(resourceBundle, "granted");
		String notGranted = _language.get(resourceBundle, "not-granted");

		for (Map.Entry<String, ResourceAction> entry :
				resourceActionMap.entrySet()) {

			writer.write("<p><b>");
			writer.write(entry.getKey());
			writer.write("</b>: ");

			if (resourcePermission.hasAction(entry.getValue())) {
				writer.write(granted);
			}
			else {
				writer.write(notGranted);
			}

			writer.write("</p>");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ResourcePermissionCTDisplayRenderer.class);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Html _html;

	@Reference
	private Language _language;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private ResourceActionLocalService _resourceActionLocalService;

	@Reference
	private ResourceActions _resourceActions;

	@Reference
	private RoleLocalService _roleLocalService;

}