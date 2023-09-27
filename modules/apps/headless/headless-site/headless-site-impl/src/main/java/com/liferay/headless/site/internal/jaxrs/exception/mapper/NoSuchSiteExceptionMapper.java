/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
 * Converts any {@code NoSuchGroupException} to a {@code 404} error.
 *
 * @author Rubén Pulido
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Headless.Site)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=Liferay.Headless.Site.NoSuchSiteExceptionMapper"
	},
	service = ExceptionMapper.class
)
public class NoSuchSiteExceptionMapper
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