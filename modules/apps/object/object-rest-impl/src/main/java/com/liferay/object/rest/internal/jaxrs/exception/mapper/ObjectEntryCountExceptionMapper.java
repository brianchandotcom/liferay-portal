/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.jaxrs.exception.mapper;

import com.liferay.object.exception.ObjectEntryCountException;
import com.liferay.object.jaxrs.exception.mapper.util.ObjectExceptionMapperUtil;
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
 * @author Thalles Montenegro
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Object.Admin.REST)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=Liferay.Object.Admin.REST.ObjectEntryCountExceptionMapper"
	},
	service = ExceptionMapper.class
)
public class ObjectEntryCountExceptionMapper
	extends BaseExceptionMapper<ObjectEntryCountException> {

	@Override
	protected Problem getProblem(
		ObjectEntryCountException objectEntryCountException) {

		return new Problem(
			JSONUtil.putAll(
				JSONUtil.put(
					"fieldName", "name"
				).put(
					"message",
					ObjectExceptionMapperUtil.getTitle(
						_acceptLanguage,
						objectEntryCountException.getArguments(), _language,
						objectEntryCountException.getMessage(),
						objectEntryCountException.getMessageKey())
				)
			).toString(),
			Response.Status.BAD_REQUEST, null,
			ObjectEntryCountException.class.getName());
	}

	@Context
	private AcceptLanguage _acceptLanguage;

	@Reference
	private Language _language;

}