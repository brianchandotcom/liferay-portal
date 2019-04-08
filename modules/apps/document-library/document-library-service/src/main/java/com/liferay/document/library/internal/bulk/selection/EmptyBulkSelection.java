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

package com.liferay.document.library.internal.bulk.selection;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionFactory;
import com.liferay.petra.string.StringPool;

import java.io.Serializable;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author Adolfo Pérez
 */
public class EmptyBulkSelection<T> implements BulkSelection<T> {

	@Override
	public String describe(Locale locale) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Class<? extends BulkSelectionFactory>
		getBulkSelectionFactoryClass() {

		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isMultiple() {
		return false;
	}

	@Override
	public Serializable serialize() {
		return StringPool.BLANK;
	}

	@Override
	public Stream<T> stream() {
		List<T> list = Collections.emptyList();

		return list.stream();
	}

	@Override
	public BulkSelection<AssetEntry> toAssetEntryBulkSelection() {
		return new EmptyBulkSelection<>();
	}

}