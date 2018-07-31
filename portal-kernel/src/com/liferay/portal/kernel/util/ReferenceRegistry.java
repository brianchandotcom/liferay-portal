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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.lang.reflect.Field;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Shuyang Zhou
 */
public class ReferenceRegistry {

	public static void registerReference(
		Class<?> clazz, Object object, String fieldName) {

		try {
			Field field = clazz.getDeclaredField(fieldName);

			ReferenceEntry referenceEntry = new ReferenceEntry(object, field);

			_referenceEntries.add(referenceEntry);
		}
		catch (SecurityException se) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Not allowed to get field ", fieldName, " for ",
						String.valueOf(clazz)));
			}
		}
		catch (Exception e) {
			_log.error(
				StringBundler.concat(
					"Unable to get field ", fieldName, " for ",
					String.valueOf(clazz)));
		}
	}

	public static void registerReference(Class<?> clazz, String fieldName) {
		registerReference(clazz, null, fieldName);
	}

	public static void registerReference(Field field) {
		ReferenceEntry referenceEntry = new ReferenceEntry(field);

		_referenceEntries.add(referenceEntry);
	}

	public static void registerReference(Object object, Field field) {
		ReferenceEntry referenceEntry = new ReferenceEntry(object, field);

		_referenceEntries.add(referenceEntry);
	}

	public static void releaseReferences() {
		for (ReferenceEntry referenceEntry : _referenceEntries) {
			try {
				referenceEntry.setValue(null);
			}
			catch (Exception e) {
				_log.error(
					"Failed to release reference for " + referenceEntry, e);
			}
		}

		_referenceEntries.clear();
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public interface PACL {

		public ReferenceEntry getReferenceEntry(
				Class<?> clazz, Object object, String fieldName)
			throws NoSuchFieldException, SecurityException;

	}

	private static final Log _log = LogFactoryUtil.getLog(
		ReferenceRegistry.class);

	private static final Set<ReferenceEntry> _referenceEntries =
		Collections.newSetFromMap(new ConcurrentHashMap<>());

}