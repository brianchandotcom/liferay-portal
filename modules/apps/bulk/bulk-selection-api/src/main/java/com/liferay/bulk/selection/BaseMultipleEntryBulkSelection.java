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

import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ResourceBundleLoader;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseMultipleEntryBulkSelection<T>
	implements BulkSelection<T> {

	public BaseMultipleEntryBulkSelection(
		long[] entryIds, Map<String, String[]> parameterMap,
		ResourceBundleLoader resourceBundleLoader, Language language) {

		_entryIds = entryIds;
		_parameterMap = parameterMap;
		_resourceBundleLoader = resourceBundleLoader;
		_language = language;
	}

	@Override
	public String describe(Locale locale) {
		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale);

		return _language.format(
			resourceBundle, "these-changes-will-be-applied-to-x-items",
			_entryIds.length);
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return _parameterMap;
	}

	@Override
	public boolean isMultiple() {
		return true;
	}

	@Override
	public Serializable serialize() {
		return StringUtil.merge(_entryIds, StringPool.COMMA);
	}

	@Override
	public Stream<T> stream() {
		LongStream longStream = Arrays.stream(_entryIds);

		return longStream.mapToObj(
			this::fetchEntry
		).filter(
			Objects::nonNull
		);
	}

	protected abstract T fetchEntry(long entryId);

	private final long[] _entryIds;
	private final Language _language;
	private final Map<String, String[]> _parameterMap;
	private final ResourceBundleLoader _resourceBundleLoader;

}