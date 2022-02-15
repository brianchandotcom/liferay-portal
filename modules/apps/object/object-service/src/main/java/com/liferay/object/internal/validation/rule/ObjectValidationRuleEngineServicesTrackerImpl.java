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

package com.liferay.object.internal.validation.rule;

import com.liferay.object.validation.rule.ObjectValidationRuleEngine;
import com.liferay.object.validation.rule.ObjectValidationRuleEngineServicesTracker;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true, service = ObjectValidationRuleEngineServicesTracker.class
)
public class ObjectValidationRuleEngineServicesTrackerImpl
	implements ObjectValidationRuleEngineServicesTracker {

	@Override
	public ObjectValidationRuleEngine getObjectValidationRuleEngine(
		String key) {

		return _serviceTrackerMap.getService(key);
	}

	@Override
	public List<ObjectValidationRuleEngine> getObjectValidationRuleEngines() {
		return new ArrayList(_serviceTrackerMap.values());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ObjectValidationRuleEngine.class, null,
			new ServiceReferenceMapper<String, ObjectValidationRuleEngine>() {

				@Override
				public void map(
					ServiceReference<ObjectValidationRuleEngine>
						serviceReference,
					Emitter<String> emitter) {

					ObjectValidationRuleEngine objectValidationRuleEngine =
						bundleContext.getService(serviceReference);

					emitter.emit(objectValidationRuleEngine.getName());
				}

			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, ObjectValidationRuleEngine>
		_serviceTrackerMap;

}