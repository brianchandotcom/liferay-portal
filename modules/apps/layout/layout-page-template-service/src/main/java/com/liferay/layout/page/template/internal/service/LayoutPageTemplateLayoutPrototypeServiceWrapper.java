/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.internal.service;

import com.liferay.layout.page.template.constants.LayoutPageTemplateActionKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.GuestOrUserUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.LayoutPrototypeLocalService;
import com.liferay.portal.kernel.service.LayoutPrototypeServiceWrapper;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Couso
 */
@Component(service = ServiceWrapper.class)
public class LayoutPageTemplateLayoutPrototypeServiceWrapper
	extends LayoutPrototypeServiceWrapper {

	@Override
	public LayoutPrototype addLayoutPrototype(
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			boolean active, ServiceContext serviceContext)
		throws PortalException {

		try {
			return super.addLayoutPrototype(
				nameMap, descriptionMap, active, serviceContext);
		}
		catch (PrincipalException principalException) {
			if (!_portletResourcePermission.contains(
					GuestOrUserUtil.getPermissionChecker(),
					serviceContext.getScopeGroupId(),
					LayoutPageTemplateActionKeys.
						ADD_LAYOUT_PAGE_TEMPLATE_ENTRY)) {

				throw principalException;
			}

			User user = GuestOrUserUtil.getGuestOrUser();

			return _layoutPrototypeLocalService.addLayoutPrototype(
				user.getUserId(), user.getCompanyId(), nameMap, descriptionMap,
				active, serviceContext);
		}
	}

	@Reference
	private LayoutPrototypeLocalService _layoutPrototypeLocalService;

	@Reference(
		target = "(component.name=com.liferay.layout.page.template.internal.security.permission.resource.LayoutPageTemplatePortletResourcePermission)"
	)
	private PortletResourcePermission _portletResourcePermission;

}