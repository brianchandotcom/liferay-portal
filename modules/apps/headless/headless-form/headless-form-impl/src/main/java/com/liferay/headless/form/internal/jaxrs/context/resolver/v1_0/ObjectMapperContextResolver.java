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

package com.liferay.headless.form.internal.jaxrs.context.resolver.v1_0;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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

import javax.annotation.Generated;

import javax.ws.rs.ext.ContextResolver;
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
		"osgi.jaxrs.name=Liferay.Headless.Form.v1_0.ObjectMapperContextResolver"
	},
	service = ContextResolver.class
)
@Generated("")
@Provider
public class ObjectMapperContextResolver
	implements ContextResolver<ObjectMapper> {

	public ObjectMapper getContext(Class<?> aClass) {
		return _objectMapper;
	}

	private static final ObjectMapper _objectMapper = new ObjectMapper() {
		{
			registerModule(
				new SimpleModule("Liferay.Headless.Form", Version.unknownVersion()) {
					{
						setAbstractTypes(
							new SimpleAbstractTypeResolver() {
								{
									addMapping(Columns.class, ColumnsImpl.class);
									addMapping(Creator.class, CreatorImpl.class);
									addMapping(FieldValues.class, FieldValuesImpl.class);
									addMapping(Fields.class, FieldsImpl.class);
									addMapping(Form.class, FormImpl.class);
									addMapping(FormDocument.class, FormDocumentImpl.class);
									addMapping(FormPages.class, FormPagesImpl.class);
									addMapping(FormRecord.class, FormRecordImpl.class);
									addMapping(FormStructure.class, FormStructureImpl.class);
									addMapping(Grid.class, GridImpl.class);
									addMapping(Options.class, OptionsImpl.class);
									addMapping(Rows.class, RowsImpl.class);
									addMapping(SuccessPage.class, SuccessPageImpl.class);
									addMapping(Validation.class, ValidationImpl.class);
	}
							});
	}
				});

			configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
			enable(SerializationFeature.INDENT_OUTPUT);
			setDateFormat(new ISO8601DateFormat());
	}
	};

}