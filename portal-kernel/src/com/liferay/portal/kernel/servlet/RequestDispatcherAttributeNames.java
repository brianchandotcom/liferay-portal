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

package com.liferay.portal.kernel.servlet;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.JavaConstants;

import java.lang.reflect.Method;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.portlet.MimeResponse;

/**
 * @author Shuyang Zhou
 */
public class RequestDispatcherAttributeNames {

	public static boolean contains(String name) {
		return _attributeNames.contains(name);
	}

	private static int _caculateOptimalHashSetSize(
		int maxSize, String... strings) {

		Method hashMethod = null;

		try {
			hashMethod = ReflectionUtil.getDeclaredMethod(
				HashMap.class, "hash", Object.class);

			if (hashMethod.getReturnType() != int.class) {
				hashMethod = null;

				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"Current JDK HashMap's hash(Object) method: \"",
							hashMethod,
							"\" is not returning int. Fallback to regular ",
							"HashSet creation."));
				}
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Current JDK HashMap does not have hash(Object) method. " +
						"Fallback to regular HashSet creation.",
					e);
			}
		}

		int size = 16;

		if (hashMethod == null) {
			return size;
		}

		try {
			Set<Integer> hashCodes = new HashSet<>();
			Set<Integer> positions = new HashSet<>();

			for (String s : strings) {
				hashCodes.add(s.hashCode());
			}

			iterate:
			while (size < maxSize) {
				for (Integer hashCode : hashCodes) {
					int pos =
						(size - 1) & (int)hashMethod.invoke(null, hashCode);

					if (!positions.add(pos)) {
						if (size > (maxSize / 2)) {
							break iterate;
						}

						size *= 2;

						positions.clear();

						continue iterate;
					}
				}

				break;
			}
		}
		catch (ReflectiveOperationException roe) {
			_log.error("Unable to get hash code", roe);
		}

		return size;
	}

	private static Set<String> _createConstantSet(
		int maxSize, String... strings) {

		Set<String> set = new HashSet<>(
			_caculateOptimalHashSetSize(maxSize, strings));

		Collections.addAll(set, strings);

		return set;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RequestDispatcherAttributeNames.class);

	private static final Set<String> _attributeNames = _createConstantSet(
		2048, JavaConstants.JAVAX_SERVLET_FORWARD_CONTEXT_PATH,
		JavaConstants.JAVAX_SERVLET_FORWARD_PATH_INFO,
		JavaConstants.JAVAX_SERVLET_FORWARD_QUERY_STRING,
		JavaConstants.JAVAX_SERVLET_FORWARD_REQUEST_URI,
		JavaConstants.JAVAX_SERVLET_FORWARD_SERVLET_PATH,
		JavaConstants.JAVAX_SERVLET_INCLUDE_CONTEXT_PATH,
		JavaConstants.JAVAX_SERVLET_INCLUDE_PATH_INFO,
		JavaConstants.JAVAX_SERVLET_INCLUDE_QUERY_STRING,
		JavaConstants.JAVAX_SERVLET_INCLUDE_REQUEST_URI,
		JavaConstants.JAVAX_SERVLET_INCLUDE_SERVLET_PATH,
		MimeResponse.MARKUP_HEAD_ELEMENT);

}