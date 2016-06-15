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

package com.liferay.dynamic.data.mapping.data.provider;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marcellus Tavares
 */
public class DDMDataProviderResponse implements Serializable {

	public void addDDMDataProviderResponseEntry(
		DDMDataProviderResponseEntry ddmDataProviderResponseEntry) {

		_ddmDataProviderResponseEntries.add(ddmDataProviderResponseEntry);
	}

	public List<DDMDataProviderResponseEntry>
		getDDMDataProviderResponseEntries() {

		return _ddmDataProviderResponseEntries;
	}

	public void setDDMDataProviderResponseEntries(
		List<DDMDataProviderResponseEntry> ddmDataProviderResponseEntries) {

		_ddmDataProviderResponseEntries = ddmDataProviderResponseEntries;
	}

	private List<DDMDataProviderResponseEntry> _ddmDataProviderResponseEntries =
		new ArrayList<>();

}