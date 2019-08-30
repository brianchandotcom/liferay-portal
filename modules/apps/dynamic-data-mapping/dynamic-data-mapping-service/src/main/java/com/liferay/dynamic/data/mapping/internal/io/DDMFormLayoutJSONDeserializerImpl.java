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

package com.liferay.dynamic.data.mapping.internal.io;

import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutJSONDeserializer;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author     Marcellus Tavares
 * @deprecated As of Judson (7.1.x), replaced by {@link
 *             com.liferay.dynamic.data.mapping.internal.io.DDMFormLayoutJSONDeserializer}
 */
@Component(service = DDMFormLayoutJSONDeserializer.class)
@Deprecated
public class DDMFormLayoutJSONDeserializerImpl
	implements DDMFormLayoutJSONDeserializer {

	@Override
	public DDMFormLayout deserialize(String serializedDDMFormLayout) {
		DDMFormLayoutDeserializerDeserializeRequest.Builder builder =
			DDMFormLayoutDeserializerDeserializeRequest.Builder.newBuilder(
				serializedDDMFormLayout);

		DDMFormLayoutDeserializerDeserializeResponse
			ddmFormLayoutDeserializerDeserializeResponse =
				_jsonDDMFormLayoutDeserializer.deserialize(builder.build());

		return ddmFormLayoutDeserializerDeserializeResponse.getDDMFormLayout();
	}

	@Reference(target = "(ddm.form.layout.deserializer.type=json)")
	private DDMFormLayoutDeserializer _jsonDDMFormLayoutDeserializer;

}