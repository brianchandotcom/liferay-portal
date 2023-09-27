/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.test.util.info.item.provider;

import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.test.util.model.MockObject;

import java.util.Map;

/**
 * @author Lourdes Fernández Besada
 */
public class MockInfoItemFieldValuesProvider
	implements InfoItemFieldValuesProvider<MockObject> {

	@Override
	public InfoItemFieldValues getInfoItemFieldValues(MockObject mockObject) {
		InfoItemFieldValues.Builder builder = InfoItemFieldValues.builder();

		Map<InfoField<?>, Object> infoFieldsMap = mockObject.getInfoFieldsMap();

		for (Map.Entry<InfoField<?>, Object> entry : infoFieldsMap.entrySet()) {
			builder = builder.infoFieldValue(
				new InfoFieldValue<>(entry.getKey(), entry.getValue()));
		}

		return builder.build();
	}

}