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

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.ServiceReference;

/**
 * @author Carlos Sierra Andrés
 */
public class ServiceReferenceServiceTuple<SR, TS, K>
	extends com.liferay.osgi.service.tracker.collections.internal.common.
		ServiceReferenceServiceTuple<SR, TS> {

	public ServiceReferenceServiceTuple(
		ServiceReference<SR> serviceReference, TS service) {

		super(serviceReference, service);
	}

	public void addEmittedKey(K key) {
		_emittedKeys.add(key);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof ServiceReferenceServiceTuple)) {
			return false;
		}

		ServiceReferenceServiceTuple<SR, TS, K> serviceReferenceServiceTuple =
			(ServiceReferenceServiceTuple<SR, TS, K>)obj;

		return getServiceReference().equals(
			serviceReferenceServiceTuple.getServiceReference());
	}

	public List<K> getEmittedKeys() {
		return _emittedKeys;
	}

	@Override
	public int hashCode() {
		return getServiceReference().hashCode();
	}

	private final List<K> _emittedKeys = new ArrayList<>();

}