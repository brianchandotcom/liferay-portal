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

package com.liferay.info.internal.util;

import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author Jorge Ferrer
 */
public class ItemClassNameServiceReferenceMapper
	implements ServiceReferenceMapper<String, Object> {

	public ItemClassNameServiceReferenceMapper(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Override
	public void map(
		ServiceReference<Object> serviceReference, Emitter<String> emitter) {

		Object itemClassName = serviceReference.getProperty(_PROPERTY_NAME);

		if (itemClassName != null) {
			_propertyServiceReferenceMapper.map(serviceReference, emitter);

			return;
		}

		Object serviceObject = _bundleContext.getService(serviceReference);

		try {
			emitter.emit(GenericsUtil.getItemClassName(serviceObject));
		}
		finally {
			_bundleContext.ungetService(serviceReference);
		}
	}

	private static final String _PROPERTY_NAME = "item.class.name";

	private final BundleContext _bundleContext;
	private PropertyServiceReferenceMapper<String, Object>
		_propertyServiceReferenceMapper = new PropertyServiceReferenceMapper<>(
			_PROPERTY_NAME);

}