/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.taxonomy.internal.jaxrs.exception.mapper;

import com.liferay.asset.kernel.exception.SystemVocabularyException;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.BaseExceptionMapper;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Headless.Admin.Taxonomy)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=Liferay.Headless.Admin.Taxonomy.SystemVocabularyExceptionMapper"
	},
	service = ExceptionMapper.class
)
public class SystemVocabularyExceptionMapper
	extends BaseExceptionMapper<SystemVocabularyException> {

	@Override
	protected Problem getProblem(
		SystemVocabularyException systemVocabularyException) {

		return new Problem(
			Response.Status.FORBIDDEN, systemVocabularyException.getMessage());
	}

}