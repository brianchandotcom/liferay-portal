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

package com.liferay.headless.site.internal.jaxrs.exception.mapper;

import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.BaseExceptionMapper;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.osgi.service.component.annotations.Component;

/**
 * Converts any {@code NoSuchGroupExceptionMapper} to a {@code 404} error.
 *
 * @author Rubén Pulido
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Headless.Site)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=Liferay.Headless.Site.NoSuchGroupExceptionMapper"
	},
	service = ExceptionMapper.class
)
public class NoSuchGroupExceptionMapper
	extends BaseExceptionMapper<NoSuchGroupException> {

	@Override
	protected Problem getProblem(NoSuchGroupException noSuchGroupException) {
		return new Problem(
			Response.Status.NOT_FOUND, _getTitle(noSuchGroupException));
	}

	private String _getTitle(NoSuchGroupException noSuchGroupException) {
		String title = "No site exists";

		if (noSuchGroupException.getMessage() == null) {
			return title;
		}

		Matcher matcher = _pattern.matcher(noSuchGroupException.getMessage());

		if (!matcher.matches()) {
			return title;
		}

		String siteKey = matcher.group(1);

		if (Validator.isNotNull(siteKey)) {
			title = title + " for site key " + siteKey;
		}

		return title;
	}

	private static final Pattern _pattern = Pattern.compile(".+groupKey=(.+)}");

}