/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.roles.admin.web.internal.change.tracking.spi.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
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
import com.liferay.portal.kernel.util.Portal;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = CTDisplayRenderer.class)
public class ResourcePermissionCTDisplayRenderer
	extends BaseCTDisplayRenderer<ResourcePermission> {

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
						_log.warn(portalException);
					}
				}
			}
		}

		if (arguments.size() == 1) {
			return _language.format(
				locale, "x-permissions", arguments.toArray(new String[0]),
				false);
		}
		else if (arguments.size() == 2) {
			return _language.format(
				locale, "x-permissions-for-x", arguments.toArray(new String[0]),
				false);
		}
		else if (arguments.size() == 3) {
			return _language.format(
				locale, "x-permissions-for-x-x",
				arguments.toArray(new String[0]), false);
		}

		return _language.format(
			locale, "x-permissions-for-x-x-x", arguments.toArray(new String[0]),
			false);
	}

	@Override
	public boolean isHideable(ResourcePermission resourcePermission) {
		return true;
	}

	@Override
	protected void buildDisplay(
		DisplayBuilder<ResourcePermission> displayBuilder) {

		ResourcePermission resourcePermission = displayBuilder.getModel();

		Map<String, ResourceAction> resourceActionMap = new TreeMap<>();

		for (ResourceAction resourceAction :
				_resourceActionLocalService.getResourceActions(
					resourcePermission.getName())) {

			String actionLabel = _resourceActions.getAction(
				displayBuilder.getLocale(), resourceAction.getActionId());

			resourceActionMap.put(actionLabel, resourceAction);
		}

		String granted = _language.get(displayBuilder.getLocale(), "granted");
		String notGranted = _language.get(
			displayBuilder.getLocale(), "not-granted");

		for (Map.Entry<String, ResourceAction> entry :
				resourceActionMap.entrySet()) {

			displayBuilder.display(
				entry.getKey(),
				() -> {
					if (resourcePermission.hasAction(entry.getValue())) {
						return granted;
					}

					return notGranted;
				});
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ResourcePermissionCTDisplayRenderer.class);

	@Reference
	private GroupLocalService _groupLocalService;

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