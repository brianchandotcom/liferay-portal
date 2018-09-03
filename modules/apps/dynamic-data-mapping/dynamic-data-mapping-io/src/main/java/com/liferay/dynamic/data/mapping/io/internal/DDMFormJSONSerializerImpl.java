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

package com.liferay.dynamic.data.mapping.io.internal;

import com.liferay.dynamic.data.mapping.io.DDMFormJSONSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializerTracker;
import com.liferay.dynamic.data.mapping.model.DDMForm;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 * @deprecated As of Judson (7.1.x), replaced by {@link com.liferay.dynamic.data.mapping.io.internal.DDMFormJSONSerializer}
 */
@Component(immediate = true)
@Deprecated
public class DDMFormJSONSerializerImpl implements DDMFormJSONSerializer {

	@Override
	public String serialize(DDMForm ddmForm) {
		DDMFormSerializer ddmFormSerializer =
			_ddmFormSerializerTracker.getDDMFormSerializer("json");

		DDMFormSerializerSerializeRequest.Builder builder =
			DDMFormSerializerSerializeRequest.Builder.newBuilder(ddmForm);

		DDMFormSerializerSerializeResponse ddmFormSerializerSerializeResponse =
			ddmFormSerializer.serialize(builder.build());

		return ddmFormSerializerSerializeResponse.getContent();
	}

	@Reference(unbind = "-")
	protected void setDDMFormSerializerTracker(
		DDMFormSerializerTracker ddmFormSerializerTracker) {

		_ddmFormSerializerTracker = ddmFormSerializerTracker;
	}

	private DDMFormSerializerTracker _ddmFormSerializerTracker;

}