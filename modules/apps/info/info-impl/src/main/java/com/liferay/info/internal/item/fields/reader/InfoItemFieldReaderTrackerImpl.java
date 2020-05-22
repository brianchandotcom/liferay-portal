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

package com.liferay.info.internal.item.fields.reader;

import com.liferay.info.internal.util.GenericsUtil;
import com.liferay.info.item.fields.reader.InfoItemFieldReader;
import com.liferay.info.item.fields.reader.InfoItemFieldReaderTracker;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = InfoItemFieldReaderTracker.class)
public class InfoItemFieldReaderTrackerImpl
	implements InfoItemFieldReaderTracker {

	@Override
	public List<InfoItemFieldReader> getInfoItemFieldReaders(
		String itemClassName) {

		List<InfoItemFieldReader> infoItemRenderers =
			_itemClassNameInfoItemFieldReaderServiceTrackerMap.getService(
				itemClassName);

		if (infoItemRenderers != null) {
			return new ArrayList<>(infoItemRenderers);
		}

		return Collections.emptyList();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_itemClassNameInfoItemFieldReaderServiceTrackerMap =
			ServiceTrackerMapFactory.openMultiValueMap(
				bundleContext, InfoItemFieldReader.class, null,
				new ServiceReferenceMapper<String, InfoItemFieldReader>() {

					@Override
					public void map(
						ServiceReference<InfoItemFieldReader> serviceReference,
						Emitter<String> emitter) {

						InfoItemFieldReader infoItemRenderer =
							bundleContext.getService(serviceReference);

						String className = GenericsUtil.getItemClassName(
							infoItemRenderer);

						emitter.emit(className);
					}

				},
				Collections.reverseOrder(
					new PropertyServiceReferenceComparator(
						"info.item.field.order")));
	}

	private ServiceTrackerMap<String, List<InfoItemFieldReader>>
		_itemClassNameInfoItemFieldReaderServiceTrackerMap;

}