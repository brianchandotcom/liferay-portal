/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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