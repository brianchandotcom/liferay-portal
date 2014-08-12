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

package com.liferay.portal.service;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Brian Wing Shun Chan
 * @author Connor McKay
 */
public class PersistedModelLocalServiceRegistryImpl
	implements PersistedModelLocalServiceRegistry {

	@Override
	public PersistedModelLocalService getPersistedModelLocalService(
		String className) {

		return _persistedModelLocalServices.get(className);
	}

	@Override
	public List<PersistedModelLocalService> getPersistedModelLocalServices() {
		return ListUtil.fromMapValues(_persistedModelLocalServices);
	}

	@Override
	public boolean isPermissionedModelLocalService(String className) {
		PersistedModelLocalService persistedModelLocalService =
			getPersistedModelLocalService(className);

		if (persistedModelLocalService == null) {
			return false;
		}

		if (persistedModelLocalService instanceof
				PermissionedModelLocalService) {

			return true;
		}

		return false;
	}

	@Override
	public void register(
		String className,
		PersistedModelLocalService persistedModelLocalService) {

		PersistedModelLocalService oldPersistedModelLocalService =
			_persistedModelLocalServices.put(
				className, persistedModelLocalService);

		if ((oldPersistedModelLocalService != null) && _log.isWarnEnabled()) {
			_log.warn("Duplicate class name " + className);
		}
	}

	@Override
	public void unregister(String className) {
		_persistedModelLocalServices.remove(className);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PersistedModelLocalServiceRegistryImpl.class);

	private final Map<String, PersistedModelLocalService>
		_persistedModelLocalServices =
			new ConcurrentHashMap<String, PersistedModelLocalService>();

}