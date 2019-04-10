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

package com.liferay.headless.foundation.internal.resource.v1_0;

import com.liferay.headless.foundation.dto.v1_0.TaxonomyVocabulary;
import com.liferay.headless.foundation.resource.v1_0.TaxonomyVocabularyResource;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 * @author Shuyang Zhou
 */
public class TaxonomyVocabularyResourceWrapper
	implements EntityModelResource, TaxonomyVocabularyResource {

	public TaxonomyVocabularyResourceWrapper() {
		this(new TaxonomyVocabularyResourceImpl());
	}

	public TaxonomyVocabularyResourceWrapper(
		TaxonomyVocabularyResource taxonomyVocabularyResource) {

		_taxonomyVocabularyResource = taxonomyVocabularyResource;
		_entityModelResource = (EntityModelResource)taxonomyVocabularyResource;
	}

	@Override
	public void deleteTaxonomyVocabulary(Long taxonomyVocabularyId)
		throws Exception {

		_taxonomyVocabularyResource.deleteTaxonomyVocabulary(
			taxonomyVocabularyId);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return _entityModelResource.getEntityModel(multivaluedMap);
	}

	@Override
	public Page<TaxonomyVocabulary> getSiteTaxonomyVocabulariesPage(
			Long siteId, String search, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		return _taxonomyVocabularyResource.getSiteTaxonomyVocabulariesPage(
			siteId, search, filter, pagination, sorts);
	}

	@Override
	public TaxonomyVocabulary getTaxonomyVocabulary(Long taxonomyVocabularyId)
		throws Exception {

		return _taxonomyVocabularyResource.getTaxonomyVocabulary(
			taxonomyVocabularyId);
	}

	@Override
	public TaxonomyVocabulary patchTaxonomyVocabulary(
			Long taxonomyVocabularyId, TaxonomyVocabulary taxonomyVocabulary)
		throws Exception {

		return _taxonomyVocabularyResource.patchTaxonomyVocabulary(
			taxonomyVocabularyId, taxonomyVocabulary);
	}

	@Override
	public TaxonomyVocabulary postSiteTaxonomyVocabulary(
			Long siteId, TaxonomyVocabulary taxonomyVocabulary)
		throws Exception {

		return _taxonomyVocabularyResource.postSiteTaxonomyVocabulary(
			siteId, taxonomyVocabulary);
	}

	@Override
	public TaxonomyVocabulary putTaxonomyVocabulary(
			Long taxonomyVocabularyId, TaxonomyVocabulary taxonomyVocabulary)
		throws Exception {

		return _taxonomyVocabularyResource.putTaxonomyVocabulary(
			taxonomyVocabularyId, taxonomyVocabulary);
	}

	@Override
	public void setContextAcceptLanguage(AcceptLanguage contextAcceptLanguage) {
		_taxonomyVocabularyResource.setContextAcceptLanguage(
			contextAcceptLanguage);
	}

	@Override
	public void setContextCompany(Company contextCompany) {
		_taxonomyVocabularyResource.setContextCompany(contextCompany);
	}

	@Override
	public void setContextUriInfo(UriInfo contextUriInfo) {
		_taxonomyVocabularyResource.setContextUriInfo(contextUriInfo);
	}

	private final EntityModelResource _entityModelResource;
	private final TaxonomyVocabularyResource _taxonomyVocabularyResource;

}