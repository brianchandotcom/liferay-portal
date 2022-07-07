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

package com.liferay.info.test.util.info.item.provider;

import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.test.util.model.MockObject;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

/**
 * @author Lourdes Fernández Besada
 */
public class MockInfoItemFormProvider
	implements InfoItemFormProvider<MockObject> {

	public MockInfoItemFormProvider(InfoFieldSet fieldSetEntry) {
		_fieldSetEntry = fieldSetEntry;
	}

	@Override
	public InfoForm getInfoForm() {
		return InfoForm.builder(
		).infoFieldSetEntry(
			_fieldSetEntry
		).labelInfoLocalizedValue(
			InfoLocalizedValue.<String>builder(
			).values(
				RandomTestUtil.randomLocaleStringMap()
			).build()
		).name(
			MockObject.class.getName()
		).build();
	}

	private final InfoFieldSet _fieldSetEntry;

}