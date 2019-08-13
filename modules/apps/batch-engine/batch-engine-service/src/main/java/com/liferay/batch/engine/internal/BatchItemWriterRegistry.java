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

package com.liferay.batch.engine.internal;

import com.liferay.batch.engine.BatchItemWriter;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 */
@Component(service = BatchItemWriterRegistry.class)
public class BatchItemWriterRegistry {

	@Activate
	public void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, BatchItemWriter.class, null,
			(serviceReference, emitter) -> {
				Object className = serviceReference.getProperty("class.name");

				if (className == null) {
					return;
				}

				Object version = serviceReference.getProperty("version");

				if (version == null) {
					return;
				}

				emitter.emit(
					StringBundler.concat(className, StringPool.POUND, version));
			});
	}

	public <T> BatchItemWriter<T> get(String className, String version) {
		@SuppressWarnings("unchecked")
		BatchItemWriter<T> batchItemWriter =
			(BatchItemWriter<T>)_serviceTrackerMap.getService(
				StringBundler.concat(className, StringPool.POUND, version));

		if (batchItemWriter == null) {
			throw new IllegalStateException(
				StringBundler.concat(
					"Unknown class name : ", className, " and version : ",
					version));
		}

		return batchItemWriter;
	}

	@SuppressWarnings("unchecked")
	private ServiceTrackerMap<String, BatchItemWriter> _serviceTrackerMap;

}