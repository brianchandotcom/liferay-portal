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

package com.liferay.object.admin.rest.internal.jaxrs.exception.mapper;

import com.liferay.object.exception.ObjectActionNameException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.BaseExceptionMapper;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier Gamarra
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Object.Admin.REST)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=Liferay.Object.Admin.REST.ObjectActionNameExceptionMapper"
	},
	service = ExceptionMapper.class
)
public class ObjectActionNameExceptionMapper
	extends BaseExceptionMapper<ObjectActionNameException> {

	@Override
	protected Problem getProblem(
		ObjectActionNameException objectActionNameException) {

		return new Problem(
			JSONUtil.putAll(
				JSONUtil.put(
					"fieldName", "name"
				).put(
					"message",
					_language.get(
						_acceptLanguage.getPreferredLocale(),
						objectActionNameException.getMessageKey())
				)
			).toString(),
			Response.Status.BAD_REQUEST, null, "ObjectActionNameException");
	}

	@Override
	protected boolean isSanitize() {
		return false;
	}

	@Context
	private AcceptLanguage _acceptLanguage;

	@Reference
	private Language _language;

}