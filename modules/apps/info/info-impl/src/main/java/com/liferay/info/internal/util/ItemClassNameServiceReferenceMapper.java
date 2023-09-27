/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.internal.util;

import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.petra.reflect.GenericUtil;

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
			emitter.emit(GenericUtil.getGenericClassName(serviceObject));
		}
		finally {
			_bundleContext.ungetService(serviceReference);
		}
	}

	private static final String _PROPERTY_NAME = "item.class.name";

	private final BundleContext _bundleContext;
	private final PropertyServiceReferenceMapper<String, Object>
		_propertyServiceReferenceMapper = new PropertyServiceReferenceMapper<>(
			_PROPERTY_NAME);

}