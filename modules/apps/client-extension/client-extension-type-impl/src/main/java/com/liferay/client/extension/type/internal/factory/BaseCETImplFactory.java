/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type.internal.factory;

import com.liferay.client.extension.type.CET;
import com.liferay.client.extension.type.annotation.CETProperty;
import com.liferay.client.extension.type.factory.CETImplFactory;

import java.lang.reflect.Method;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Iván Zaera Avellón
 */
public abstract class BaseCETImplFactory<T extends CET>
	implements CETImplFactory<T> {

	public BaseCETImplFactory(Class<T> cetClass) {
		_urlCETPropertyNames = _getURLCETPropertyNames(cetClass);
	}

	@Override
	public boolean isURLCETPropertyName(String name) {
		return _urlCETPropertyNames.contains(name);
	}

	private Set<String> _getURLCETPropertyNames(Class<? extends CET> cetClass) {
		Set<String> urlCETPropertyNames = new HashSet<>();

		for (Method method : cetClass.getDeclaredMethods()) {
			CETProperty cetProperty = method.getAnnotation(CETProperty.class);

			CETProperty.Type type = cetProperty.type();

			if (type.isURL()) {
				urlCETPropertyNames.add(cetProperty.name());
			}
		}

		return urlCETPropertyNames;
	}

	private final Set<String> _urlCETPropertyNames;

}