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