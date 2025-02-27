/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.jaxrs.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import com.liferay.petra.function.UnsafeSupplier;

/**
 * @author Alberto Javier Moreno Lage
 * @author Carlos Correa
 */
public class UnsafeSupplierJsonSerializer
	extends JsonSerializer<UnsafeSupplier<Object, Exception>> {

	@Override
	public void serialize(
		UnsafeSupplier<Object, Exception> unsafeSupplier,
		JsonGenerator jsonGenerator, SerializerProvider serializerProvider) {

		try {
			Object value = unsafeSupplier.get();

			if (value == null) {
				jsonGenerator.writeNull();

				return;
			}

			JsonSerializer<Object> jsonSerializer =
				serializerProvider.findValueSerializer(value.getClass(), null);

			jsonSerializer.serialize(value, jsonGenerator, serializerProvider);
		}
		catch (Throwable throwable) {
			throw new RuntimeException(throwable);
		}
	}

}