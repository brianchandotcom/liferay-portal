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

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.portal.kernel.exception.PortalException;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author Adolfo Pérez
 */
public interface BulkSelection<T> {

	public String describe(Locale locale) throws PortalException;

	public Class<? extends BulkSelectionFactory> getBulkSelectionFactoryClass();

	public Map<String, String[]> getParameterMap();

	public boolean isMultiple();

	public Serializable serialize();

	public Stream<T> stream() throws PortalException;

	public BulkSelection<AssetEntry> toAssetEntryBulkSelection();

}