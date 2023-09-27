/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osgi.service.tracker.collections.map;

import com.liferay.osgi.service.tracker.collections.ServiceReferenceServiceTuple;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.ServiceReference;

/**
 * @author Carlos Sierra Andrés
 */
public class KeyedServiceReferenceServiceTuple<SR, TS, K>
	extends ServiceReferenceServiceTuple<SR, TS> {

	public KeyedServiceReferenceServiceTuple(
		ServiceReference<SR> serviceReference, TS service) {

		super(serviceReference, service);
	}

	public void addEmittedKey(K key) {
		_emittedKeys.add(key);
	}

	public List<K> getEmittedKeys() {
		return _emittedKeys;
	}

	private final List<K> _emittedKeys = new ArrayList<>();

}