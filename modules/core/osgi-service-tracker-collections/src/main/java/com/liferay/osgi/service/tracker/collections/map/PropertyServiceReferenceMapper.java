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

package com.liferay.osgi.service.tracker.collections.map;

import org.osgi.framework.ServiceReference;

/**
 * @author Carlos Sierra Andrés
 */
public class PropertyServiceReferenceMapper<T, S>
	implements ServiceReferenceMapper<T, S> {

	public PropertyServiceReferenceMapper(String propertyKey) {
		_propertyKey = propertyKey;
	}

	@Override
	public void map(ServiceReference<S> serviceReference, Emitter<T> emitter) {
		Object propertyValue = serviceReference.getProperty(_propertyKey);

		if (propertyValue == null) {
			return;
		}

		if (propertyValue instanceof Object[]) {
			for (T t : (T[])propertyValue) {
				emitter.emit(t);
			}
		}
		else {
			emitter.emit((T)propertyValue);
		}
	}

	private final String _propertyKey;

}