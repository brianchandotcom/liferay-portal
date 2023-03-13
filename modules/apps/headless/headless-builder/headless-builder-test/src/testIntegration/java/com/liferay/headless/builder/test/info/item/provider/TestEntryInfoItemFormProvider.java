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
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.provider.InfoItemFormProvider;

/**
 * @author Alejandro Tardín
 */
public class TestEntryInfoItemFormProvider
	implements InfoItemFormProvider<TestEntry> {

	@Override
	public InfoForm getInfoForm() {
		return InfoForm.builder(
		).infoFieldSetEntry(
			InfoFieldSet.builder(
			).infoFieldSetEntry(
				TestEntryInfoItemFields.dateFieldInfoField
			).name(
				"dateField"
			).build()
		).infoFieldSetEntry(
			InfoFieldSet.builder(
			).infoFieldSetEntry(
				TestEntryInfoItemFields.numberFieldInfoField
			).name(
				"numberField"
			).build()
		).name(
			TestEntry.class.getName()
		).build();
	}

}