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

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ResourceBundleLoader;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseMultipleEntryBulkSelection<T>
	implements BulkSelection<T> {

	public BaseMultipleEntryBulkSelection(
		long[] entryIds, Map<String, String[]> parameterMap) {

		_entryIds = entryIds;
		_parameterMap = parameterMap;
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #BaseMultipleEntryBulkSelection(long[], Map)}
	 */
	@Deprecated
	public BaseMultipleEntryBulkSelection(
		long[] entryIds, Map<String, String[]> parameterMap,
		ResourceBundleLoader resourceBundleLoader, Language language) {

		this(entryIds, parameterMap);
	}

	@Override
	public <E extends PortalException> void forEach(
			UnsafeConsumer<T, E> unsafeConsumer)
		throws PortalException {

		for (long entryId : _entryIds) {
			unsafeConsumer.accept(fetchEntry(entryId));
		}
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return _parameterMap;
	}

	@Override
	public long getSize() {
		return _entryIds.length;
	}

	@Override
	public Serializable serialize() {
		return StringUtil.merge(_entryIds, StringPool.COMMA);
	}

	protected abstract T fetchEntry(long entryId);

	private final long[] _entryIds;
	private final Map<String, String[]> _parameterMap;

}