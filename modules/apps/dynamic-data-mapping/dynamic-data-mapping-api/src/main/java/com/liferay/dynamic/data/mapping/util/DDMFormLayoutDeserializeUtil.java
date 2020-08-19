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