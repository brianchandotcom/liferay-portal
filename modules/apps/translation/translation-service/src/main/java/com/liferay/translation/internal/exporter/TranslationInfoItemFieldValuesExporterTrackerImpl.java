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

package com.liferay.translation.internal.exporter;

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.translation.exporter.TranslationInfoItemFieldValuesExporter;
import com.liferay.translation.exporter.TranslationInfoItemFieldValuesExporterTracker;

import java.util.Collection;
import java.util.Optional;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alejandro Tardín
 */
@Component(service = TranslationInfoItemFieldValuesExporterTracker.class)
public class TranslationInfoItemFieldValuesExporterTrackerImpl
	implements TranslationInfoItemFieldValuesExporterTracker {

	@Override
	public Collection<TranslationInfoItemFieldValuesExporter>
		getTranslationInfoItemFieldValueExporters() {

		return _serviceTrackerMap.values();
	}

	@Override
	public Optional<TranslationInfoItemFieldValuesExporter>
		getTranslationInfoItemFieldValuesExporterOptional(String mimeType) {

		return Optional.ofNullable(_serviceTrackerMap.getService(mimeType));
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, TranslationInfoItemFieldValuesExporter.class, null,
			ServiceReferenceMapperFactory.create(
				bundleContext,
				(translationInfoItemFieldValuesExporter, emitter) ->
					emitter.emit(
						translationInfoItemFieldValuesExporter.getMimeType())));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private volatile ServiceTrackerMap
		<String, TranslationInfoItemFieldValuesExporter> _serviceTrackerMap;

}