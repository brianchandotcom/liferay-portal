/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.taxonomy.internal.jaxrs.exception.mapper;

import com.liferay.asset.kernel.exception.DuplicateAssetVocabularyExternalReferenceCodeException;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.BaseExceptionMapper;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

import org.osgi.service.component.annotations.Component;

/**
 * Converts any {@code DuplicateAssetVocabularyExternalReferenceCodeException}
 * to a {@code 409} error.
 *
 * @author Balázs Sáfrány-Kovalik
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Headless.Admin.Taxonomy)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=Liferay.Headless.Admin.Taxonomy.DuplicateAssetVocabularyExternalReferenceCodeExceptionMapper"
	},
	service = ExceptionMapper.class
)
public class DuplicateAssetVocabularyExternalReferenceCodeExceptionMapper
	extends BaseExceptionMapper
		<DuplicateAssetVocabularyExternalReferenceCodeException> {

	@Override
	protected Problem getProblem(
		DuplicateAssetVocabularyExternalReferenceCodeException
			duplicateAssetVocabularyExternalReferenceCodeException) {

		return new Problem(
			Response.Status.CONFLICT,
			"A taxonomy vocabulary with the same external reference code " +
				"already exists");
	}

}