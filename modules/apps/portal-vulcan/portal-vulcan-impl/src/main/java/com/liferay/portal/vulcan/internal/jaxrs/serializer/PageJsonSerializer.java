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

package com.liferay.portal.vulcan.internal.jaxrs.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;

import com.liferay.portal.vulcan.pagination.Page;

import java.io.IOException;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;

/**
 * @author Javier Gamarra
 */
public class PageJsonSerializer extends JsonSerializer<Page> {

	@Override
	public void serialize(
			Page page, JsonGenerator jsonGenerator, SerializerProvider provider)
		throws IOException {

		ToXmlGenerator toXmlGenerator = (ToXmlGenerator)jsonGenerator;

		toXmlGenerator.writeStartObject();

		toXmlGenerator.writeFieldName("items");

		toXmlGenerator.writeStartObject();

		toXmlGenerator.writeFieldName("items");

		toXmlGenerator.writeStartArray();

		for (Object item : page.getItems()) {
			Class<?> clazz = item.getClass();

			Class<?> superClass = clazz.getSuperclass();

			XmlRootElement xmlRootElement = superClass.getAnnotation(
				XmlRootElement.class);

			if (xmlRootElement != null) {
				toXmlGenerator.setNextName(new QName(xmlRootElement.name()));
			}

			toXmlGenerator.writeObject(item);
		}

		toXmlGenerator.writeEndArray();

		toXmlGenerator.writeEndObject();

		toXmlGenerator.writeObjectField("lastPage", page.getLastPage());

		toXmlGenerator.writeObjectField("page", page.getPage());

		toXmlGenerator.writeObjectField("pageSize", page.getPageSize());

		toXmlGenerator.writeObjectField("totalCount", page.getTotalCount());

		toXmlGenerator.writeEndObject();
	}

}