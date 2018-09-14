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
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorConstants;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.util.Dictionary;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component
public class OrganizationScreenNavigationRegistrar {

	@Activate
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		registerScreenNavigationCategories();

		registerScreenNavigationEntries();
	}

	@Deactivate
	public void deactivate() {
		_screenNavigationEntryServiceRegistrations.forEach(
			ServiceRegistration::unregister);

		_screenNavigationEntryServiceRegistrations.clear();

		_screenNavigationCategoryServiceRegistrations.forEach(
			ServiceRegistration::unregister);

		_screenNavigationCategoryServiceRegistrations.clear();
	}

	protected void registerScreenNavigationCategories() {
		_registerScreenNavigationCategory(
			_organizationScreenNavigationFactory.createScreenNavigationCategory(
				_CATEGORY_ORGANIZATION_INFORMATION),
			10);
		_registerScreenNavigationCategory(
			_organizationScreenNavigationFactory.createScreenNavigationCategory(
				_CATEGORY_MISCELLANEOUS),
			20);
	}

	protected void registerScreenNavigationEntries() {
		_registerScreenNavigationEntry(
			_organizationScreenNavigationFactory.createScreenNavigationEntry(
				"general", _CATEGORY_ORGANIZATION_INFORMATION,
				"/organization/general.jsp", "/users_admin/edit_organization"),
			10);
		_registerScreenNavigationEntry(
			_organizationScreenNavigationFactory.
				createUpdateOnlyScreenNavigationEntry(
					"reminder-queries", _CATEGORY_MISCELLANEOUS,
					"/organization/reminder_queries.jsp",
					"/users_admin/organization/update_reminder_queries"),
			20);
	}

	private Dictionary _getProperties() {
		return _getProperties(null);
	}

	private Dictionary _getProperties(Integer serviceRanking) {
		return new HashMapDictionary<String, Object>() {
			{
				if (serviceRanking != null) {
					put("screen.navigation.category.order", serviceRanking);
					put("screen.navigation.entry.order", serviceRanking);
				}
			}
		};
	}

	private void _registerScreenNavigationCategory(
		ScreenNavigationCategory screenNavigationCategory) {

		_registerScreenNavigationCategory(
			screenNavigationCategory, _getProperties());
	}

	private void _registerScreenNavigationCategory(
		ScreenNavigationCategory screenNavigationCategory,
		Dictionary<String, Object> properties) {

		_screenNavigationCategoryServiceRegistrations.add(
			_bundleContext.registerService(
				ScreenNavigationCategory.class, screenNavigationCategory,
				properties));
	}

	private void _registerScreenNavigationCategory(
		ScreenNavigationCategory screenNavigationCategory,
		Integer serviceRanking) {

		_registerScreenNavigationCategory(
			screenNavigationCategory, _getProperties(serviceRanking));
	}

	private void _registerScreenNavigationEntry(
		ScreenNavigationEntry screenNavigationEntry) {

		_registerScreenNavigationEntry(screenNavigationEntry, _getProperties());
	}

	private void _registerScreenNavigationEntry(
		ScreenNavigationEntry screenNavigationEntry,
		Dictionary<String, Object> properties) {

		_screenNavigationEntryServiceRegistrations.add(
			_bundleContext.registerService(
				ScreenNavigationEntry.class, screenNavigationEntry,
				properties));
	}

	private void _registerScreenNavigationEntry(
		ScreenNavigationEntry screenNavigationEntry, Integer serviceRanking) {

		_registerScreenNavigationEntry(
			screenNavigationEntry, _getProperties(serviceRanking));
	}

	private static final String _CATEGORY_MISCELLANEOUS =
		FormNavigatorConstants.CATEGORY_KEY_ORGANIZATION_MISCELLANEOUS;

	private static final String _CATEGORY_ORGANIZATION_INFORMATION =
		FormNavigatorConstants.
			CATEGORY_KEY_ORGANIZATION_ORGANIZATION_INFORMATION;

	private BundleContext _bundleContext;

	@Reference
	private OrganizationScreenNavigationFactory
		_organizationScreenNavigationFactory;

	private final List<ServiceRegistration<ScreenNavigationCategory>>
		_screenNavigationCategoryServiceRegistrations =
			new CopyOnWriteArrayList<>();
	private final List<ServiceRegistration<ScreenNavigationEntry>>
		_screenNavigationEntryServiceRegistrations =
			new CopyOnWriteArrayList<>();

}