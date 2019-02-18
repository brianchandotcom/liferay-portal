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

package com.liferay.headless.form.internal.jaxrs.message.body.v1_0;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.form.dto.v1_0.Columns;
import com.liferay.headless.form.dto.v1_0.Creator;
import com.liferay.headless.form.dto.v1_0.FieldValues;
import com.liferay.headless.form.dto.v1_0.Fields;
import com.liferay.headless.form.dto.v1_0.Form;
import com.liferay.headless.form.dto.v1_0.FormDocument;
import com.liferay.headless.form.dto.v1_0.FormPages;
import com.liferay.headless.form.dto.v1_0.FormRecord;
import com.liferay.headless.form.dto.v1_0.FormStructure;
import com.liferay.headless.form.dto.v1_0.Grid;
import com.liferay.headless.form.dto.v1_0.Options;
import com.liferay.headless.form.dto.v1_0.Rows;
import com.liferay.headless.form.dto.v1_0.SuccessPage;
import com.liferay.headless.form.dto.v1_0.Validation;
import com.liferay.headless.form.internal.dto.v1_0.ColumnsImpl;
import com.liferay.headless.form.internal.dto.v1_0.CreatorImpl;
import com.liferay.headless.form.internal.dto.v1_0.FieldValuesImpl;
import com.liferay.headless.form.internal.dto.v1_0.FieldsImpl;
import com.liferay.headless.form.internal.dto.v1_0.FormDocumentImpl;
import com.liferay.headless.form.internal.dto.v1_0.FormImpl;
import com.liferay.headless.form.internal.dto.v1_0.FormPagesImpl;
import com.liferay.headless.form.internal.dto.v1_0.FormRecordImpl;
import com.liferay.headless.form.internal.dto.v1_0.FormStructureImpl;
import com.liferay.headless.form.internal.dto.v1_0.GridImpl;
import com.liferay.headless.form.internal.dto.v1_0.OptionsImpl;
import com.liferay.headless.form.internal.dto.v1_0.RowsImpl;
import com.liferay.headless.form.internal.dto.v1_0.SuccessPageImpl;
import com.liferay.headless.form.internal.dto.v1_0.ValidationImpl;

import java.io.IOException;
import java.io.InputStream;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.annotation.Generated;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import org.osgi.service.component.annotations.Component;

/**
 * @author Javier Gamarra
 * @generated
 */
@Component(
	property = {
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.extension.select=(osgi.jaxrs.name=Liferay.Headless.Form)",
		"osgi.jaxrs.name=Liferay.Headless.Form.v1_0.JSONMessageBodyReader"
	},
	service = MessageBodyReader.class
)
@Consumes(MediaType.APPLICATION_JSON)
@Generated("")
@Provider
public class JSONMessageBodyReader implements MessageBodyReader<Object> {

	@Override
	public boolean isReadable(
		Class<?> clazz, Type genericType, Annotation[] annotations,
		MediaType mediaType) {

			if (clazz.equals(Columns.class)) {
				return true;
	}
			if (clazz.equals(Creator.class)) {
				return true;
	}
			if (clazz.equals(FieldValues.class)) {
				return true;
	}
			if (clazz.equals(Fields.class)) {
				return true;
	}
			if (clazz.equals(Form.class)) {
				return true;
	}
			if (clazz.equals(FormDocument.class)) {
				return true;
	}
			if (clazz.equals(FormPages.class)) {
				return true;
	}
			if (clazz.equals(FormRecord.class)) {
				return true;
	}
			if (clazz.equals(FormStructure.class)) {
				return true;
	}
			if (clazz.equals(Grid.class)) {
				return true;
	}
			if (clazz.equals(Options.class)) {
				return true;
	}
			if (clazz.equals(Rows.class)) {
				return true;
	}
			if (clazz.equals(SuccessPage.class)) {
				return true;
	}
			if (clazz.equals(Validation.class)) {
				return true;
	}

		return false;
	}

	@Override
	public Object readFrom(
			Class<Object> clazz, Type genericType, Annotation[] annotations,
			MediaType mediaType, MultivaluedMap<String, String> multivaluedMap,
			InputStream inputStream)
		throws IOException, WebApplicationException {

		return _objectMapper.readValue(inputStream, clazz);
	}

	private static final ObjectMapper _objectMapper = new ObjectMapper() {
		{
			SimpleModule simpleModule =
				new SimpleModule("Liferay.Headless.Form",
					Version.unknownVersion());

			SimpleAbstractTypeResolver simpleAbstractTypeResolver =
				new SimpleAbstractTypeResolver();

			simpleAbstractTypeResolver.addMapping(
				Columns.class, ColumnsImpl.class);
			simpleAbstractTypeResolver.addMapping(
				Creator.class, CreatorImpl.class);
			simpleAbstractTypeResolver.addMapping(
				FieldValues.class, FieldValuesImpl.class);
			simpleAbstractTypeResolver.addMapping(
				Fields.class, FieldsImpl.class);
			simpleAbstractTypeResolver.addMapping(
				Form.class, FormImpl.class);
			simpleAbstractTypeResolver.addMapping(
				FormDocument.class, FormDocumentImpl.class);
			simpleAbstractTypeResolver.addMapping(
				FormPages.class, FormPagesImpl.class);
			simpleAbstractTypeResolver.addMapping(
				FormRecord.class, FormRecordImpl.class);
			simpleAbstractTypeResolver.addMapping(
				FormStructure.class, FormStructureImpl.class);
			simpleAbstractTypeResolver.addMapping(
				Grid.class, GridImpl.class);
			simpleAbstractTypeResolver.addMapping(
				Options.class, OptionsImpl.class);
			simpleAbstractTypeResolver.addMapping(
				Rows.class, RowsImpl.class);
			simpleAbstractTypeResolver.addMapping(
				SuccessPage.class, SuccessPageImpl.class);
			simpleAbstractTypeResolver.addMapping(
				Validation.class, ValidationImpl.class);

			simpleModule.setAbstractTypes(simpleAbstractTypeResolver);

			registerModule(simpleModule);

			setDateFormat(new ISO8601DateFormat());
	}
	};

}