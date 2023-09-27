/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.security.permission.support;

import com.liferay.dynamic.data.mapping.util.DDMStructurePermissionSupport;
import com.liferay.dynamic.data.mapping.util.DDMTemplatePermissionSupport;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Portal;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(service = DDMPermissionSupportRegistry.class)
public class DDMPermissionSupportRegistry {

	public ServiceWrapper<DDMStructurePermissionSupport>
		getDDMStructurePermissionSupportServiceWrapper(long classNameId) {

		return getDDMStructurePermissionSupportServiceWrapper(
			_portal.getClassName(classNameId));
	}

	public ServiceWrapper<DDMStructurePermissionSupport>
		getDDMStructurePermissionSupportServiceWrapper(String className) {

		return _ddmStructurePermissionSupportServiceTrackerMap.getService(
			className);
	}

	public ServiceWrapper<DDMTemplatePermissionSupport>
			getDDMTemplatePermissionSupportServiceWrapper(
				long resourceClassNameId)
		throws PortalException {

		return getDDMTemplatePermissionSupportServiceWrapper(
			_portal.getClassName(resourceClassNameId));
	}

	public ServiceWrapper<DDMTemplatePermissionSupport>
			getDDMTemplatePermissionSupportServiceWrapper(
				String resourceClassName)
		throws PortalException {

		ServiceWrapper<DDMTemplatePermissionSupport>
			ddmTemplatePermissionSupportServiceWrapper =
				_ddmTemplatePermissionSupportServiceTrackerMap.getService(
					resourceClassName);

		if (ddmTemplatePermissionSupportServiceWrapper == null) {
			throw new PortalException(
				"The model does not support DDMTemplate permission checking " +
					resourceClassName);
		}

		return ddmTemplatePermissionSupportServiceWrapper;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_ddmStructurePermissionSupportServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, DDMStructurePermissionSupport.class,
				"model.class.name",
				ServiceTrackerCustomizerFactory.
					<DDMStructurePermissionSupport>serviceWrapper(
						bundleContext));

		_ddmTemplatePermissionSupportServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, DDMTemplatePermissionSupport.class,
				"model.class.name",
				ServiceTrackerCustomizerFactory.
					<DDMTemplatePermissionSupport>serviceWrapper(
						bundleContext));
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

	@Reference
	private Portal _portal;

}