/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.taxonomy.internal.jaxrs.exception.mapper;

import com.liferay.asset.kernel.exception.VocabularyExternalReferenceCodeException;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.BaseExceptionMapper;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

import jakarta.ws.rs.ext.ExceptionMapper;

import org.osgi.service.component.annotations.Component;

/**
 * Converts any {@code VocabularyExternalReferenceCodeException} to a
 * {@code 400} error.
 *
 * @author Roberto Díaz
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Headless.Admin.Taxonomy)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=Liferay.Headless.Admin.Taxonomy.VocabularyExternalReferenceCodeExceptionMapper"
	},
	service = ExceptionMapper.class
)
public class VocabularyExternalReferenceCodeExceptionMapper
	extends BaseExceptionMapper<VocabularyExternalReferenceCodeException> {

	@Override
	protected Problem getProblem(
		VocabularyExternalReferenceCodeException
			vocabularyExternalReferenceCodeException) {

		return new Problem(vocabularyExternalReferenceCodeException);
	}

}