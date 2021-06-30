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

package com.liferay.object.internal.instance.lifecycle;

import com.liferay.object.system.SystemObjectDefinitionMetadata;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.CompanyLocalService;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class SystemObjectDefinitionMetadataPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) {
		for (SystemObjectDefinitionMetadata systemObjectDefinitionMetadata :
				_systemObjectDefinitionMetadatas) {

			_apply(company.getCompanyId(), systemObjectDefinitionMetadata);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_systemObjectDefinitionMetadatas = ServiceTrackerListFactory.open(
			bundleContext, SystemObjectDefinitionMetadata.class, null,
			new ServiceTrackerCustomizer
				<SystemObjectDefinitionMetadata,
				 SystemObjectDefinitionMetadata>() {

				@Override
				public SystemObjectDefinitionMetadata addingService(
					ServiceReference<SystemObjectDefinitionMetadata>
						serviceReference) {

					SystemObjectDefinitionMetadata
						systemObjectDefinitionMetadata =
							bundleContext.getService(serviceReference);

					_companyLocalService.forEachCompanyId(
						companyId -> _apply(
							companyId, systemObjectDefinitionMetadata));

					return systemObjectDefinitionMetadata;
				}

				@Override
				public void modifiedService(
					ServiceReference<SystemObjectDefinitionMetadata>
						serviceReference,
					SystemObjectDefinitionMetadata
						systemObjectDefinitionMetadata) {
				}

				@Override
				public void removedService(
					ServiceReference<SystemObjectDefinitionMetadata>
						serviceReference,
					SystemObjectDefinitionMetadata
						systemObjectDefinitionMetadata) {

					bundleContext.ungetService(serviceReference);
				}

			});
	}

	@Deactivate
	protected void deactivate() {
		_systemObjectDefinitionMetadatas.close();
	}

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.object.service)(release.schema.version>=1.0.0))",
		unbind = "-"
	)
	protected void setRelease(Release release) {
	}

	private void _apply(
		long companyId,
		SystemObjectDefinitionMetadata systemObjectDefinitionMetadata) {

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Applying ", systemObjectDefinitionMetadata, " to company ",
					companyId));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SystemObjectDefinitionMetadataPortalInstanceLifecycleListener.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	private ServiceTrackerList
		<SystemObjectDefinitionMetadata, SystemObjectDefinitionMetadata>
			_systemObjectDefinitionMetadatas;

}