/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;

/**
 * @author Marcos Martins
 */
public class DDMFormLayoutDeserializeUtil {

	public static DDMFormLayout deserialize(
			DDMFormLayoutDeserializer ddmFormLayoutDeserializer,
			String definition)
		throws Exception {

		DDMFormLayoutDeserializerDeserializeResponse
			ddmFormLayoutDeserializerDeserializeResponse =
				ddmFormLayoutDeserializer.deserialize(
					DDMFormLayoutDeserializerDeserializeRequest.Builder.
						newBuilder(
							definition
						).build());

		Exception exception =
			ddmFormLayoutDeserializerDeserializeResponse.getException();

		if (exception != null) {
			throw exception;
		}

		return ddmFormLayoutDeserializerDeserializeResponse.getDDMFormLayout();
	}

}