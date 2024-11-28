/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.remote.json.web.service.web.internal.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.MethodParameter;

import java.io.IOException;
import java.io.InputStream;

import java.lang.reflect.Method;

import java.util.List;

import org.objectweb.asm.ClassReader;

/**
 * @author Tamas Biro
 */
public class MethodParameterExtractor {

	public static List<MethodParameter> getMethodParameters(
			Class<?> clazz, Method method)
		throws PortalException {

		String resourceName = clazz.getName(
		).replace(
			StringPool.PERIOD, StringPool.SLASH
		);

		resourceName = resourceName + ".class";

		InputStream classInputStream = clazz.getClassLoader(
		).getResourceAsStream(
			resourceName
		);

		if (classInputStream == null) {
			throw new IllegalArgumentException(
				"Class not found: " + clazz.getName());
		}

		ClassReader classReader = null;

		try {
			classReader = new ClassReader(classInputStream);
		}
		catch (IOException ioException) {
			throw new PortalException(
				"Unable to readClass to extract parameters ", ioException);
		}

		MethodParameterClassVisitor classVisitor =
			new MethodParameterClassVisitor(clazz.getClassLoader(), method);

		classReader.accept(classVisitor, ClassReader.SKIP_FRAMES);

		return classVisitor.getExtractedParameters();
	}

}