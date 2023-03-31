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

package com.liferay.object.rest.internal.jaxrs.exception.mapper;

import com.liferay.object.exception.ObjectEntryValuesException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.BaseExceptionMapper;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 * @author Javier Gamarra
 */
@Provider
public class ObjectEntryValuesExceptionMapper
	extends BaseExceptionMapper<ObjectEntryValuesException> {

	public ObjectEntryValuesExceptionMapper(Language language) {
		_language = language;
	}

	@Override
	protected Problem getProblem(
		ObjectEntryValuesException objectEntryValuesException) {

		String messageKey = objectEntryValuesException.getMessageKey();

		if (messageKey == null) {
			messageKey = objectEntryValuesException.getMessage();
		}

		return new Problem(
			Response.Status.BAD_REQUEST,
			_language.format(
				_acceptLanguage.getPreferredLocale(), messageKey,
				objectEntryValuesException.getArguments()));
	}

	@Context
	private AcceptLanguage _acceptLanguage;

	private final Language _language;

}