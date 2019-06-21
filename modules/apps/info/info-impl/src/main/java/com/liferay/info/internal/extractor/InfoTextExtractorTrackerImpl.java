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

package com.liferay.info.internal.extractor;

import com.liferay.info.extractor.InfoTextExtractor;
import com.liferay.info.extractor.InfoTextExtractorTracker;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alejandro Tardín
 */
@Component(service = InfoTextExtractorTracker.class)
public class InfoTextExtractorTrackerImpl implements InfoTextExtractorTracker {

	public InfoTextExtractor getInfoTextExtractor(String className) {
		return _serviceTrackerMap.getService(className);
	}

	public List<InfoTextExtractor> getInfoTextExtractors() {
		return new ArrayList<>(_serviceTrackerMap.values());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap =
			(ServiceTrackerMap<String, InfoTextExtractor<?>>)
				(ServiceTrackerMap)ServiceTrackerMapFactory.openSingleValueMap(
					bundleContext, InfoTextExtractor.class, null,
					ServiceReferenceMapperFactory.create(
						bundleContext,
						(infoTextExtractor, emitter) -> emitter.emit(
							infoTextExtractor.getClassName())));
	}

	@Deactivate
	protected synchronized void deactivate() {
		_serviceTrackerMap.close();
	}

	private volatile ServiceTrackerMap<String, InfoTextExtractor<?>>
		_serviceTrackerMap;

}