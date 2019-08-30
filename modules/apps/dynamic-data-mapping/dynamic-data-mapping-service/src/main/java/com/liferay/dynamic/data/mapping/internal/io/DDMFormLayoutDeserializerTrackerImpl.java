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
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializerTracker;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 * @deprecated As of Mueller (7.2.x), with no direct replacement
 */
@Component(service = DDMFormLayoutDeserializerTracker.class)
@Deprecated
public class DDMFormLayoutDeserializerTrackerImpl
	implements DDMFormLayoutDeserializerTracker {

	@Override
	public DDMFormLayoutDeserializer getDDMFormLayoutDeserializer(String type) {
		if (Objects.equals(type, "json")) {
			return _jsonDDMFormLayoutDeserializer;
		}

		return null;
	}

	@Reference(target = "(ddm.form.layout.deserializer.type=json)")
	private DDMFormLayoutDeserializer _jsonDDMFormLayoutDeserializer;

}