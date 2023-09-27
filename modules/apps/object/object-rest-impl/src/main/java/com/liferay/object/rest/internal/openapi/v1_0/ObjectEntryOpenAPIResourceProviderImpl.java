/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.openapi.v1_0;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.openapi.v1_0.ObjectEntryOpenAPIResource;
import com.liferay.object.rest.openapi.v1_0.ObjectEntryOpenAPIResourceProvider;
import com.liferay.osgi.service.tracker.collections.map.ScopedServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ScopedServiceTrackerMapFactory;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Carlos Correa
 */
@Component(service = ObjectEntryOpenAPIResourceProvider.class)
public class ObjectEntryOpenAPIResourceProviderImpl
	implements ObjectEntryOpenAPIResourceProvider {

	@Override
	public ObjectEntryOpenAPIResource getObjectEntryOpenAPIResource(
		ObjectDefinition objectDefinition) {

		return _scopedServiceTrackerMap.getService(
			objectDefinition.getCompanyId(),
			objectDefinition.getOSGiJaxRsName("ObjectEntryOpenAPIResource"));
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_scopedServiceTrackerMap = ScopedServiceTrackerMapFactory.create(
			bundleContext, ObjectEntryOpenAPIResource.class,
			"openapi.resource.key", () -> null);
	}

	@Deactivate
	protected void deactivate() {
		_scopedServiceTrackerMap.close();
	}

	private ScopedServiceTrackerMap<ObjectEntryOpenAPIResource>
		_scopedServiceTrackerMap;

}