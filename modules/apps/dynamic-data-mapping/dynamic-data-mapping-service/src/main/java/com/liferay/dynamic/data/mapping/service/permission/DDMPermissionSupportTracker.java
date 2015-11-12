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

package com.liferay.dynamic.data.mapping.service.permission;

import com.liferay.dynamic.data.mapping.util.DDMStructurePermissionSupport;
import com.liferay.dynamic.data.mapping.util.DDMTemplatePermissionSupport;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.util.PortalUtil;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = DDMPermissionSupportTracker.class)
public class DDMPermissionSupportTracker {

	public ServiceWrapper<DDMStructurePermissionSupport>
			getDDMStructurePermissionSupportServiceWrapper(long classNameId)
		throws PortalException {

		String className = PortalUtil.getClassName(classNameId);

		ServiceWrapper<DDMStructurePermissionSupport>
			ddmStructurePermissionSupportServiceWrapper =
				_ddmStructurePermissionSupportServiceTrackerMap.getService(
					className);

		if (ddmStructurePermissionSupportServiceWrapper == null) {
			throw new PortalException(
				"The model does not support DDMStructure permission checking " +
					className);
		}

		return ddmStructurePermissionSupportServiceWrapper;
	}

	public ServiceWrapper<DDMTemplatePermissionSupport>
			getDDMTemplatePermissionSupportServiceWrapper(
				long resourceClassNameId)
		throws PortalException {

		String className = PortalUtil.getClassName(resourceClassNameId);

		ServiceWrapper<DDMTemplatePermissionSupport>
			ddmTemplatePermissionSupportServiceWrapper =
				_ddmTemplatePermissionSupportServiceTrackerMap.getService(
					className);

		if (ddmTemplatePermissionSupportServiceWrapper == null) {
			throw new PortalException(
				"The model does not support DDMTemplate permission checking " +
					className);
		}

		return ddmTemplatePermissionSupportServiceWrapper;
	}

	@Activate
	protected void activate(BundleContext bundleContext)
		throws InvalidSyntaxException {

		_ddmStructurePermissionSupportServiceTrackerMap =
			ServiceTrackerMapFactory.singleValueMap(
				bundleContext, DDMStructurePermissionSupport.class,
				"model.class.name",
				ServiceTrackerCustomizerFactory.
					<DDMStructurePermissionSupport>serviceWrapper(
						bundleContext));

		_ddmStructurePermissionSupportServiceTrackerMap.open();

		_ddmTemplatePermissionSupportServiceTrackerMap =
			ServiceTrackerMapFactory.singleValueMap(
				bundleContext, DDMTemplatePermissionSupport.class,
				"model.class.name",
					ServiceTrackerCustomizerFactory.
						<DDMTemplatePermissionSupport>serviceWrapper(
							bundleContext));

		_ddmTemplatePermissionSupportServiceTrackerMap.open();
	}

	@Deactivate
	protected void deactivate() {
		_ddmStructurePermissionSupportServiceTrackerMap.close();

		_ddmTemplatePermissionSupportServiceTrackerMap.close();
	}

	private ServiceTrackerMap
		<String, ServiceWrapper<DDMStructurePermissionSupport>>
		_ddmStructurePermissionSupportServiceTrackerMap;
	private ServiceTrackerMap
		<String, ServiceWrapper<DDMTemplatePermissionSupport>>
		_ddmTemplatePermissionSupportServiceTrackerMap;

}