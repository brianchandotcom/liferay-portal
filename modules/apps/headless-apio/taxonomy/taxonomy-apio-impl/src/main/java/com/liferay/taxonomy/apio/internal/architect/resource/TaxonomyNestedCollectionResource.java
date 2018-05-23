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

package com.liferay.taxonomy.apio.internal.architect.resource;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyService;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.site.apio.architect.identifier.WebSiteIdentifier;
import com.liferay.taxonomy.apio.identifier.architect.TaxonomyIdentifier;
import com.liferay.taxonomy.apio.internal.architect.form.AssetTaxonomyForm;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier Gamarra
 */
@Component(immediate = true)
public class TaxonomyNestedCollectionResource
	implements NestedCollectionResource<AssetVocabulary, Long,
		TaxonomyIdentifier, Long, WebSiteIdentifier> {

	@Override
	public NestedCollectionRoutes<AssetVocabulary, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<AssetVocabulary, Long, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).addCreator(
			this::_addAssetVocabulary, (credentials, assetVocabularyId) -> true,
			AssetTaxonomyForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "taxonomies";
	}

	@Override
	public ItemRoutes<AssetVocabulary, Long> itemRoutes(
		ItemRoutes.Builder<AssetVocabulary, Long> builder) {

		return builder.addGetter(
			assetVocabularyId -> _assetVocabularyService.getVocabulary(
				assetVocabularyId)
		).addUpdater(
			this::_updateAssetVocabulary,
			(credentials, assetVocabularyId) -> true,
			AssetTaxonomyForm::buildForm
		).addRemover(
			this::_deleteAssetVocabulary,
			(credentials, assetVocabularyId) -> true
		).build();
	}

	@Override
	public Representor<AssetVocabulary> representor(
		Representor.Builder<AssetVocabulary, Long> builder) {

		return builder.types(
			"Taxonomy"
		).identifier(
			AssetVocabulary::getVocabularyId
		).addBidirectionalModel(
			"interactionService", "taxonomies", WebSiteIdentifier.class,
			AssetVocabulary::getGroupId
		).addDate(
			"dateCreated", AssetVocabulary::getCreateDate
		).addDate(
			"dateModified", AssetVocabulary::getModifiedDate
		).addDate(
			"datePublished", AssetVocabulary::getLastPublishDate
		).addLinkedModel(
			"author", PersonIdentifier.class, AssetVocabulary::getUserId
		).addLinkedModel(
			"creator", PersonIdentifier.class, AssetVocabulary::getUserId
		).addLocalizedStringByLocale(
			"description", AssetVocabulary::getDescription
		).addLocalizedStringByLocale(
			"title", AssetVocabulary::getTitle
		).addString(
			"name", AssetVocabulary::getName
		).build();
	}

	private AssetVocabulary _addAssetVocabulary(
			Long groupId, AssetTaxonomyForm assetVocabularyForm)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		return _assetVocabularyService.addVocabulary(
			groupId, assetVocabularyForm.getTitle(),
			assetVocabularyForm.getTitleMap(),
			assetVocabularyForm.getDescriptionMap(), null, serviceContext);
	}

	private void _deleteAssetVocabulary(Long assetVocabularyId)
		throws PortalException {

		_assetVocabularyService.deleteVocabulary(assetVocabularyId);
	}

	private PageItems<AssetVocabulary> _getPageItems(
		Pagination pagination, Long groupId) {

		List<AssetVocabulary> assetVocabularies =
			_assetVocabularyService.getGroupVocabularies(
				groupId, pagination.getStartPosition(),
				pagination.getEndPosition(), null);
		int count = _assetVocabularyService.getGroupVocabulariesCount(groupId);

		return new PageItems<>(assetVocabularies, count);
	}

	private AssetVocabulary _updateAssetVocabulary(
			Long vocabularyId, AssetTaxonomyForm assetVocabularyForm)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		return _assetVocabularyService.updateVocabulary(
			vocabularyId, assetVocabularyForm.getTitle(),
			assetVocabularyForm.getTitleMap(),
			assetVocabularyForm.getDescriptionMap(), null, serviceContext);
	}

	@Reference
	private AssetVocabularyService _assetVocabularyService;

}