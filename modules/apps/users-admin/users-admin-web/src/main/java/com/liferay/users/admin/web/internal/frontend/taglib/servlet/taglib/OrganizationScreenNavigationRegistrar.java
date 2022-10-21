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

package com.liferay.users.admin.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.service.permission.GroupPermission;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.users.admin.constants.UserScreenNavigationEntryConstants;
import com.liferay.users.admin.web.internal.frontend.taglib.servlet.taglib.OrganizationScreenNavigationEntry.Mod;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Drew Brokke
 */
@Component(service = {})
public class OrganizationScreenNavigationRegistrar {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_registerService(
			ScreenNavigationCategory.class, 10,
			new OrganizationScreenNavigationCategory(
				UserScreenNavigationEntryConstants.CATEGORY_KEY_GENERAL));

		_registerService(
			ScreenNavigationCategory.class, 20,
			new OrganizationScreenNavigationCategory(
				UserScreenNavigationEntryConstants.CATEGORY_KEY_CONTACT));

		Mod categoryGeneralMod = Mod.combine(
			_dependenciesMod,
			Mod.withCategoryKey(
				UserScreenNavigationEntryConstants.CATEGORY_KEY_GENERAL));

		_registerService(
			ScreenNavigationEntry.class, 10,
			OrganizationScreenNavigationEntry.of(
				categoryGeneralMod,
				Mod.withEntryKey("information"),
				Mod.withJspPath("/organization/information.jsp"),
				Mod.withMVCActionCommandName("/users_admin/edit_organization"),
				Mod.withVisibleBiFunction((user, organization) -> true)));
		_registerService(
			ScreenNavigationEntry.class, 20,
			OrganizationScreenNavigationEntry.of(
				categoryGeneralMod,
				Mod.withEntryKey("organization-site"),
				Mod.withJspPath("/organization/organization_site.jsp"),
				Mod.withMVCActionCommandName(
					"/users_admin/update_organization_organization_site"),
				Mod.withHideControls(),
				Mod.withVisibleBiFunction(
					(user, organization) -> {
						if (organization == null) {
							return false;
						}

						try {
							if (!_groupPermission.contains(
								PermissionThreadLocal.getPermissionChecker(),
								organization.getGroup(), ActionKeys.UPDATE)) {

								return false;
							}
						}
						catch (Exception exception) {
							if (_log.isDebugEnabled()) {
								_log.debug(exception);
							}

							return false;
						}

						return true;
					}
				)
			));
		_registerService(
			ScreenNavigationEntry.class, 30,
			OrganizationScreenNavigationEntry.of(
				categoryGeneralMod,
				Mod.withEntryKey("security-questions"),
				Mod.withJspPath("/organization/reminder_queries.jsp"),
				Mod.withMVCActionCommandName(
					"/users_admin/update_organization_reminder_queries")));

		Mod categoryContactMod = Mod.combine(
			_dependenciesMod,
			Mod.withCategoryKey(
				UserScreenNavigationEntryConstants.CATEGORY_KEY_CONTACT),
			Mod.withHideControls());

		_registerService(
			ScreenNavigationEntry.class, 10,
			OrganizationScreenNavigationEntry.of(
				categoryContactMod,
				Mod.withEntryKey("addresses"),
				Mod.withJspPath("/organization/addresses.jsp"),
				Mod.withMVCActionCommandName(
					"/users_admin/update_contact_information"),
				Mod.withHideTitle()));
		_registerService(
			ScreenNavigationEntry.class, 20,
			OrganizationScreenNavigationEntry.of(
				categoryContactMod,
				Mod.withEntryKey("contact-information"),
				Mod.withJspPath("/organization/contact_information.jsp"),
				Mod.withMVCActionCommandName(
					"/users_admin/update_contact_information")));
		_registerService(
			ScreenNavigationEntry.class, 30,
			OrganizationScreenNavigationEntry.of(
				categoryContactMod,
				Mod.withEntryKey("opening-hours"),
				Mod.withJspPath("/organization/opening_hours.jsp"),
				Mod.withMVCActionCommandName(
					"/users_admin/update_contact_information"),
				Mod.withHideTitle()));

	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistrations.forEach(ServiceRegistration::unregister);

		_serviceRegistrations.clear();
	}

	private <T> void _registerService(
		Class<T> clazz, int order, T serviceObject) {

		_serviceRegistrations.add(
			_bundleContext.registerService(
				clazz, serviceObject,
				HashMapDictionaryBuilder.<String, Object>put(
					"screen.navigation.category.order", order
				).put(
					"screen.navigation.entry.order", order
				).build()));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OrganizationScreenNavigationRegistrar.class);

	private BundleContext _bundleContext;

	@Reference
	private GroupPermission _groupPermission;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private OrganizationService _organizationService;

	private final List<ServiceRegistration<?>> _serviceRegistrations =
		new ArrayList<>();

	private final Mod _dependenciesMod = info -> {
		info._organizationService = _organizationService;
		info._jspRenderer = _jspRenderer;
	};

}