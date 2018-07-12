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

package com.liferay.apio.architect.impl.jaxrs.json.writer;

import com.liferay.apio.architect.form.Form;
import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.functional.Try.Success;
import com.liferay.apio.architect.impl.jaxrs.json.writer.base.BaseMessageBodyWriter;
import com.liferay.apio.architect.impl.message.json.FormMessageMapper;
import com.liferay.apio.architect.impl.request.RequestInfo;
import com.liferay.apio.architect.impl.wiring.osgi.manager.message.json.FormMessageMapperManager;
import com.liferay.apio.architect.impl.wiring.osgi.util.GenericUtil;
import com.liferay.apio.architect.impl.writer.FormWriter;

import java.lang.reflect.Type;

import java.util.Optional;

import javax.ws.rs.core.Request;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Writes form by using the {@link FormMessageMapper} that corresponds to the
 * media type.
 *
 * @author Alejandro Hernández
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(liferay.apio.architect.application=true)",
		"osgi.jaxrs.extension=true"
	},
	service = MessageBodyWriter.class
)
@Provider
public class FormMessageBodyWriter
	extends BaseMessageBodyWriter<Success<Form>, FormMessageMapper> {

	@Override
	public boolean canWrite(Class<?> clazz, Type genericType) {
		Try<Class<Object>> classTry =
			GenericUtil.getFirstGenericTypeArgumentFromTypeTry(
				genericType, Try.class);

		return classTry.filter(
			Form.class::equals
		).isSuccess();
	}

	@Override
	public Optional<FormMessageMapper> getMessageMapperOptional(
		Request request) {

		return _formMessageMapperManager.getFormMessageMapperOptional(request);
	}

	@Override
	protected String write(
		Success<Form> success, FormMessageMapper formMessageMapper,
		RequestInfo requestInfo) {

		FormWriter formWriter = FormWriter.create(
			builder -> builder.form(
				success.getValue()
			).formMessageMapper(
				formMessageMapper
			).requestInfo(
				requestInfo
			).build());

		return formWriter.write();
	}

	@Reference
	private FormMessageMapperManager _formMessageMapperManager;

}