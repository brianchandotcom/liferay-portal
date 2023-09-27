/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;

/**
 * @author Marcos Martins
 */
public class DDMFormValuesDeserializeUtil {

	public static DDMFormValues deserialize(
			String content, DDMForm ddmForm,
			DDMFormValuesDeserializer ddmFormValuesDeserializer)
		throws Exception {

		DDMFormValuesDeserializerDeserializeRequest.Builder builder =
			DDMFormValuesDeserializerDeserializeRequest.Builder.newBuilder(
				content, ddmForm);

		DDMFormValuesDeserializerDeserializeResponse
			ddmFormValuesDeserializerDeserializeResponse =
				ddmFormValuesDeserializer.deserialize(builder.build());

		Exception exception =
			ddmFormValuesDeserializerDeserializeResponse.getException();

		if (exception != null) {
			throw exception;
		}

		return ddmFormValuesDeserializerDeserializeResponse.getDDMFormValues();
	}

}