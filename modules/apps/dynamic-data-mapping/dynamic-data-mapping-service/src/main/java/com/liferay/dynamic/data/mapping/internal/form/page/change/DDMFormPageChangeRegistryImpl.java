/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.form.page.change;

import com.liferay.dynamic.data.mapping.form.page.change.DDMFormPageChange;
import com.liferay.dynamic.data.mapping.form.page.change.DDMFormPageChangeRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Bruno Oliveira
 * @author Carolina Barbosa
 */
@Component(service = DDMFormPageChangeRegistry.class)
public class DDMFormPageChangeRegistryImpl
	implements DDMFormPageChangeRegistry {

	@Override
	public DDMFormPageChange getDDMFormPageChangeByDDMFormInstanceId(
		String ddmFormInstanceId) {

		return _serviceTrackerMap.getService(ddmFormInstanceId);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, DDMFormPageChange.class, "ddm.form.instance.id");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, DDMFormPageChange> _serviceTrackerMap;

}