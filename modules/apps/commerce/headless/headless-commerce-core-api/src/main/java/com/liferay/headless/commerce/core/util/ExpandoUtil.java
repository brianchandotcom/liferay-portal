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

package com.liferay.headless.commerce.core.util;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.util.Enumeration;
import java.util.Map;

/**
 * @author Igor Beslic
 */
public class ExpandoUtil {

	public static void updateExpando(
		long companyId, Class<?> clazz, long classPK,
		Map<String, ?> expandoAttributes) {

		ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(
			companyId, clazz.getName(), classPK);

		Enumeration<String> attributeNames = expandoBridge.getAttributeNames();

		while (attributeNames.hasMoreElements()) {
			String attributeName = attributeNames.nextElement();

			if (!expandoAttributes.containsKey(attributeName)) {
				continue;
			}

			expandoBridge.setAttribute(
				attributeName,
				(Serializable)expandoAttributes.get(attributeName));
		}
	}

}