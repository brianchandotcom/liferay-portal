/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.scope.internal.liferay;

import com.liferay.oauth2.provider.scope.internal.constants.OAuth2ProviderScopeConstants;
import com.liferay.oauth2.provider.scope.liferay.spi.ScopeDescriptorLocator;
import com.liferay.oauth2.provider.scope.spi.scope.descriptor.ScopeDescriptor;
import com.liferay.osgi.service.tracker.collections.map.ScopedServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ScopedServiceTrackerMapFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.util.GetterUtil;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Sierra Andrés
 */
@Component(service = ScopeDescriptorLocator.class)
public class ScopeDescriptorLocatorImpl implements ScopeDescriptorLocator {

	@Override
	public ScopeDescriptor getScopeDescriptor(long companyId) {
		ScopeDescriptor scopeDescriptor =
			_scopeDescriptorServiceTrackerMap.getService(companyId);

		if (scopeDescriptor == null) {
			scopeDescriptor = _scopeDescriptorServiceTrackerMap.getService(0L);
		}

		return scopeDescriptor;
	}

	@Override
	public ScopeDescriptor getScopeDescriptor(
		long companyId, String applicationName) {

		return _scopedServiceTrackerMap.getService(companyId, applicationName);
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	@Override
	public ScopeDescriptor getScopeDescriptor(String applicationName) {
		return _scopedServiceTrackerMap.getService(0, applicationName);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_scopeDescriptorServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, ScopeDescriptor.class,
				"(&(!(" + OAuth2ProviderScopeConstants.OSGI_JAXRS_NAME +
					"=*))(companyId=*))",
				(serviceReference, emitter) -> emitter.emit(
					GetterUtil.getLong(
						serviceReference.getProperty("companyId"))));
		_scopedServiceTrackerMap = ScopedServiceTrackerMapFactory.create(
			bundleContext, ScopeDescriptor.class,
			OAuth2ProviderScopeConstants.OSGI_JAXRS_NAME,
			() -> _defaultScopeDescriptor);
	}

	@Deactivate
	protected void deactivate() {
		_scopeDescriptorServiceTrackerMap.close();
		_scopedServiceTrackerMap.close();
	}

	@Reference(target = "(default=true)")
	private ScopeDescriptor _defaultScopeDescriptor;

	private ServiceTrackerMap<Long, ScopeDescriptor>
		_scopeDescriptorServiceTrackerMap;
	private ScopedServiceTrackerMap<ScopeDescriptor> _scopedServiceTrackerMap;

}