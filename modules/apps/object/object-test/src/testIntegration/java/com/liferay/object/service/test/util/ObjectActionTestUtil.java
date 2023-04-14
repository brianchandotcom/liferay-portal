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

package com.liferay.object.service.test.util;

import com.liferay.object.action.executor.ObjectActionExecutor;
import com.liferay.object.internal.action.executor.test.ObjectActionExecutorRegistryImplTest;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.util.List;
import java.util.Objects;

/**
 * @author Feliphe Marinho
 */
public class ObjectActionTestUtil {

	public static ObjectActionExecutor createProxyObjectActionExecutor(
		long companyId, String key, List<String> objectDefinitionNames) {

		return (ObjectActionExecutor)ProxyUtil.newProxyInstance(
			ObjectActionExecutorRegistryImplTest.class.getClassLoader(),
			new Class<?>[] {ObjectActionExecutor.class},
			(proxy, method, arguments) -> {
				if (Objects.equals(method.getName(), "getCompanyId")) {
					return companyId;
				}
				else if (Objects.equals(method.getName(), "getKey")) {
					return key;
				}
				else if (Objects.equals(
							method.getName(), "getObjectDefinitionNames")) {

					return objectDefinitionNames;
				}
				else if (Objects.equals(method.getName(), "toString")) {
					return ObjectActionExecutor.class.toString();
				}

				throw new UnsupportedOperationException(method.getName());
			});
	}

}