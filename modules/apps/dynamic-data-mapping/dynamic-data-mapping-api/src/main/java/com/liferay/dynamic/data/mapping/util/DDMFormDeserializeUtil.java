/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;

/**
 * @author Marcos Martins
 */
public class DDMFormDeserializeUtil {

	public static DDMForm deserialize(
			DDMFormDeserializer ddmFormDeserializer, String definition)
		throws Exception {

		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				ddmFormDeserializer.deserialize(
					DDMFormDeserializerDeserializeRequest.Builder.newBuilder(
						definition
					).build());

		Exception exception =
			ddmFormDeserializerDeserializeResponse.getException();

		if (exception != null) {
			throw exception;
		}

		return ddmFormDeserializerDeserializeResponse.getDDMForm();
	}

}