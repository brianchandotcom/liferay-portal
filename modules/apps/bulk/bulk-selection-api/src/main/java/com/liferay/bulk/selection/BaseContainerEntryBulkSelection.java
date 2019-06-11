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

package com.liferay.bulk.selection;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ResourceBundleLoader;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseContainerEntryBulkSelection<T>
	implements BulkSelection<T> {

	public BaseContainerEntryBulkSelection(
		long containerId, Map<String, String[]> parameterMap) {

		_containerId = containerId;
		_parameterMap = parameterMap;
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #BaseContainerEntryBulkSelection(long, Map)}
	 */
	@Deprecated
	public BaseContainerEntryBulkSelection(
		long containerId, Map<String, String[]> parameterMap,
		ResourceBundleLoader resourceBundleLoader, Language language) {

		this(containerId, parameterMap);
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return _parameterMap;
	}

	@Override
	public Serializable serialize() {
		return "all:" + _containerId;
	}

	private final long _containerId;
	private final Map<String, String[]> _parameterMap;

}