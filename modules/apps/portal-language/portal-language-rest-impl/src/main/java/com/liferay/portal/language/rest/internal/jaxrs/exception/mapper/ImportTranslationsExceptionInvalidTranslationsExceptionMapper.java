/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.language.rest.internal.jaxrs.exception.mapper;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.language.override.exception.ImportTranslationsException;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.BaseExceptionMapper;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Thiago Buarque
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Portal.Language.REST)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=Liferay.Portal.Language.REST.ImportTranslationsExceptionInvalidTranslationsExceptionMapper"
	},
	service = ExceptionMapper.class
)
public class ImportTranslationsExceptionInvalidTranslationsExceptionMapper
	extends BaseExceptionMapper
		<ImportTranslationsException.InvalidTranslations> {

	@Override
	protected Problem getProblem(
		ImportTranslationsException.InvalidTranslations invalidTranslations) {

		return new Problem(
			String.valueOf(_toJSONArray(invalidTranslations.getExceptions())),
			Response.Status.BAD_REQUEST, invalidTranslations.getMessage(),
			ImportTranslationsException.InvalidTranslations.class.getName());
	}

	private JSONArray _toJSONArray(Map<Class<?>, Exception> exceptions) {
		JSONArray jsonArray = _jsonFactory.createJSONArray();

		for (Map.Entry<Class<?>, Exception> entry : exceptions.entrySet()) {
			Exception exception = entry.getValue();

			jsonArray.put(exception.getMessage());
		}

		return jsonArray;
	}

	@Reference
	private JSONFactory _jsonFactory;

}