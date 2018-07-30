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

package com.liferay.portal.kernel.service;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Connor McKay
 */
public class PersistedModelLocalServiceRegistryUtil {

	public static PersistedModelLocalService getPersistedModelLocalService(
		String className) {

		return getPersistedModelLocalServiceRegistry().
			getPersistedModelLocalService(className);
	}

	public static PersistedModelLocalServiceRegistry
		getPersistedModelLocalServiceRegistry() {

		return _persistedModelLocalServiceRegistry;
	}

	public static List<PersistedModelLocalService>
		getPersistedModelLocalServices() {

		return getPersistedModelLocalServiceRegistry().
			getPersistedModelLocalServices();
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public static boolean isPermissionedModelLocalService(String className) {
		return getPersistedModelLocalServiceRegistry().
			isPermissionedModelLocalService(className);
	}

	public static void register(
		String className,
		PersistedModelLocalService persistedModelLocalService) {

		getPersistedModelLocalServiceRegistry().register(
			className, persistedModelLocalService);
	}

	public static void unregister(String className) {
		getPersistedModelLocalServiceRegistry().unregister(className);
	}

	public void setPersistedModelLocalServiceRegistry(
		PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry) {

		_persistedModelLocalServiceRegistry =
			persistedModelLocalServiceRegistry;
	}

	private static PersistedModelLocalServiceRegistry
		_persistedModelLocalServiceRegistry;

}