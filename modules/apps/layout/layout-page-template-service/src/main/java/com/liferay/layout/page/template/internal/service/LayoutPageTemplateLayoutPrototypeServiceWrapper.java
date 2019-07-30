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

package com.liferay.layout.page.template.internal.service;

import com.liferay.layout.page.template.constants.LayoutPageTemplateActionKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.GuestOrUserUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.LayoutPrototypeLocalService;
import com.liferay.portal.kernel.service.LayoutPrototypeService;
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
@Component(immediate = true, service = ServiceWrapper.class)
public class LayoutPageTemplateLayoutPrototypeServiceWrapper
	extends LayoutPrototypeServiceWrapper {

	public LayoutPageTemplateLayoutPrototypeServiceWrapper() {
		super(null);
	}

	public LayoutPageTemplateLayoutPrototypeServiceWrapper(
		LayoutPrototypeService layoutPrototypeService) {

		super(layoutPrototypeService);
	}

	@Override
	public LayoutPrototype addLayoutPrototype(
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			boolean active, ServiceContext serviceContext)
		throws PortalException {

		try {
			return super.addLayoutPrototype(
				nameMap, descriptionMap, active, serviceContext);
		}
		catch (PrincipalException pe) {
			if (!_portletResourcePermission.contains(
					GuestOrUserUtil.getPermissionChecker(),
					serviceContext.getScopeGroupId(),
					LayoutPageTemplateActionKeys.
						ADD_LAYOUT_PAGE_TEMPLATE_ENTRY)) {

				throw pe;
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