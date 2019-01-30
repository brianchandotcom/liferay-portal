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

package com.liferay.headless.foundation.internal.resource;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyService;
import com.liferay.headless.foundation.dto.Vocabulary;
import com.liferay.headless.foundation.dto.VocabularyCollection;
import com.liferay.headless.foundation.resource.VocabularyResource;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyService;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.vulcan.context.Pagination;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

/**
 * @author Javier Gamarra
 * @generated
 */
@Component(
	property = {
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_SELECT + "=(osgi.jaxrs.name=headless-foundation-application.rest)",
		JaxrsWhiteboardConstants.JAX_RS_RESOURCE + "=true", "api.version=1.0.0"
	},
	scope = ServiceScope.PROTOTYPE, service = VocabularyResource.class
)
@Generated("")
public class VocabularyResourceImpl implements VocabularyResource {

	@Override
	public VocabularyCollection<Vocabulary> getVocabularyCollection(
			Pagination pagination, String size)
		throws Exception {

		Company company = _companyService.getCompanyByWebId(
			PropsUtil.get(PropsKeys.COMPANY_DEFAULT_WEB_ID));

		Group group = company.getGroup();

		List<AssetVocabulary> assetVocabularies =
			_assetVocabularyService.getGroupVocabularies(
				group.getGroupId(), pagination.getStartPosition(),
				pagination.getEndPosition(), null);

		int count = _assetVocabularyService.getGroupVocabulariesCount(
			group.getGroupId());

		Stream<AssetVocabulary> stream = assetVocabularies.stream();

		List<Vocabulary> vocabularies = stream.map(
			assetVocabulary -> {
				Vocabulary vocabulary = new Vocabulary();

				vocabulary.setId(assetVocabulary.getVocabularyId());

				return vocabulary;
			}
		).collect(
			Collectors.toList()
		);

		return new VocabularyCollection(vocabularies, count);
	}

	@Reference
	private AssetVocabularyService _assetVocabularyService;

	@Reference
	private CompanyService _companyService;

}