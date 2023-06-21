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

package com.liferay.dynamic.data.mapping.form.field.type.internal.security.permission;

import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.dynamic.data.mapping.security.permission.DDMFormsPortletPermissionChecker;
import com.liferay.dynamic.data.mapping.security.permission.DDMFormsPortletPermissionCheckerRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Roberto Díaz
 */
@Component(service = DDMFormsPortletPermissionCheckerRegistry.class)
public class DDMFormsPortletPermissionCheckerRegistryImpl
	implements DDMFormsPortletPermissionCheckerRegistry {

	@Override
	public DDMFormsPortletPermissionChecker getDDMPortletPermissionChecker(
		String portletId) {

		DDMFormsPortletPermissionChecker ddmFormsPortletPermissionChecker =
			_serviceTrackerMap.getService(portletId);

		if (ddmFormsPortletPermissionChecker == null) {
			ddmFormsPortletPermissionChecker =
				new DefaultDDMFormsPermissionChecker();
		}

		return ddmFormsPortletPermissionChecker;
	}

	@Activate
	@Modified
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, DDMFormsPortletPermissionChecker.class,
			"javax.portlet.name");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private volatile ServiceTrackerMap<String, DDMFormsPortletPermissionChecker>
		_serviceTrackerMap;

	private class DefaultDDMFormsPermissionChecker
		implements DDMFormsPortletPermissionChecker {

		@Override
		public boolean containsPermission(
			DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

			return true;
		}

	}

}