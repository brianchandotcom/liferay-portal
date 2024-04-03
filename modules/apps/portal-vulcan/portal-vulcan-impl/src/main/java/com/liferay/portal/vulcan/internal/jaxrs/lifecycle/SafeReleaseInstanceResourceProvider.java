/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.jaxrs.lifecycle;

import java.util.HashSet;
import java.util.Set;

import org.apache.cxf.jaxrs.lifecycle.ResourceProvider;
import org.apache.cxf.message.Message;

/**
 * @author Alejandro Tardín
 */
public class SafeReleaseInstanceResourceProvider implements ResourceProvider {

	public SafeReleaseInstanceResourceProvider(
		ResourceProvider resourceProvider) {

		_resourceProvider = resourceProvider;
	}

	@Override
	public Object getInstance(Message message) {
		Object instance = _resourceProvider.getInstance(message);

		_instances.add(instance);

		return instance;
	}

	@Override
	public Class<?> getResourceClass() {
		return _resourceProvider.getResourceClass();
	}

	public boolean isSingleton() {
		return _resourceProvider.isSingleton();
	}

	@Override
	public void releaseInstance(Message message, Object object) {
		if (_instances.remove(object)) {
			_resourceProvider.releaseInstance(message, object);
		}
	}

	private final Set<Object> _instances = new HashSet<>();
	private final ResourceProvider _resourceProvider;

}