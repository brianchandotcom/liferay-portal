/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.auto.tagger.internal.text.extractor;

import com.liferay.asset.auto.tagger.text.extractor.TextExtractor;
import com.liferay.asset.auto.tagger.text.extractor.TextExtractorRegistry;
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
@Component(service = TextExtractorRegistry.class)
public class TextExtractorRegistryImpl implements TextExtractorRegistry {

	@Override
	public TextExtractor<?> getTextExtractor(String className) {
		return _serviceTrackerMap.getService(className);
	}

	@Override
	public List<TextExtractor<?>> getTextExtractors() {
		return new ArrayList<>(_serviceTrackerMap.values());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap =
			(ServiceTrackerMap<String, TextExtractor<?>>)
				(ServiceTrackerMap)ServiceTrackerMapFactory.openSingleValueMap(
					bundleContext, TextExtractor.class, null,
					ServiceReferenceMapperFactory.create(
						bundleContext,
						(textExtractor, emitter) -> emitter.emit(
							textExtractor.getClassName())));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, TextExtractor<?>> _serviceTrackerMap;

}