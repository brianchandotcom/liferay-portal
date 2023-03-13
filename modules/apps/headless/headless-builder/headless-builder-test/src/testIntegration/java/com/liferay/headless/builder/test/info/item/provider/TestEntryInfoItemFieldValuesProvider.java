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

package com.liferay.headless.builder.test.info.item.provider;

import com.liferay.headless.builder.test.info.item.TestEntryInfoItemFields;
import com.liferay.headless.builder.test.model.TestEntry;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;

import java.util.Arrays;

/**
 * @author Alejandro Tardín
 */
public class TestEntryInfoItemFieldValuesProvider
	implements InfoItemFieldValuesProvider<TestEntry> {

	@Override
	public InfoItemFieldValues getInfoItemFieldValues(TestEntry testEntry) {
		return InfoItemFieldValues.builder(
		).infoFieldValues(
			Arrays.asList(
				new InfoFieldValue<>(
					TestEntryInfoItemFields.dateFieldInfoField,
					testEntry.getDateField()),
				new InfoFieldValue<>(
					TestEntryInfoItemFields.numberFieldInfoField,
					testEntry.getLongField()))
		).infoItemReference(
			new InfoItemReference(
				TestEntry.class.getName(), testEntry.getTestEntryId())
		).build();
	}

}