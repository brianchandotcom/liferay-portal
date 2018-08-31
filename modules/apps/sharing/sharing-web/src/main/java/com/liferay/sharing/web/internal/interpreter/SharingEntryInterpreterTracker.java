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

package com.liferay.sharing.web.internal.interpreter;

import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.sharing.interpreter.SharingEntryInterpreter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(service = SharingEntryInterpreterTracker.class)
public class SharingEntryInterpreterTracker {

	@Activate
	public void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, SharingEntryInterpreter.class,
			"(model.class.name=*)",
			(serviceReference, emitter) -> {
				emitter.emit(
					_classNameLocalService.getClassNameId(
						(String)serviceReference.getProperty(
							"model.class.name")));
			},
			new PropertyServiceReferenceComparator<>("service.ranking"));
	}

	@Deactivate
	public void deactivate() {
		_serviceTrackerMap.close();
	}

	public Optional<SharingEntryInterpreter> getSharingEntryInterpreter(
		long classNameId) {

		List<SharingEntryInterpreter> sharingEntryInterpreters =
			_serviceTrackerMap.getService(classNameId);

		Stream<SharingEntryInterpreter> stream =
			sharingEntryInterpreters.stream();

		return stream.findFirst();
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	private ServiceTrackerMap<Long, List<SharingEntryInterpreter>>
		_serviceTrackerMap;

}