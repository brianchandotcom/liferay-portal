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

package com.liferay.category.apio.internal.resource;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.AssetVocabularyModel;
import com.liferay.asset.kernel.service.AssetVocabularyService;
import com.liferay.category.apio.identifier.VocabularyIdentifier;
import com.liferay.category.apio.internal.form.AssetVocabularyForm;
import com.liferay.person.apio.identifier.PersonIdentifier;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.site.apio.identifier.WebSiteIdentifier;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier Gamarra
 */
@Component(immediate = true)
public class VocabularyNestedCollectionResource
	implements NestedCollectionResource<AssetVocabulary, Long,
		VocabularyIdentifier, Long, WebSiteIdentifier> {

	@Override
	public NestedCollectionRoutes<AssetVocabulary, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<AssetVocabulary, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).addCreator(
			this::_addAssetVocabulary, (credentials, assetVocabularyId) -> true,
			AssetVocabularyForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "vocabularies";
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
			AssetVocabularyForm::buildForm
		).addRemover(
			this::_deleteAssetVocabulary,
			(credentials, assetVocabularyId) -> true
		).build();
	}

	@Override
	public Representor<AssetVocabulary> representor(
		Representor.Builder<AssetVocabulary, Long> builder) {

		return builder.types(
			"Vocabulary"
		).identifier(
			AssetVocabulary::getVocabularyId
		).addBidirectionalModel(
			"interactionService", "vocabularies", WebSiteIdentifier.class,
			AssetVocabularyModel::getGroupId
		).addDate(
			"dateCreated", AssetVocabulary::getCreateDate
		).addDate(
			"dateModified", AssetVocabulary::getModifiedDate
		).addDate(
			"datePublished", AssetVocabulary::getLastPublishDate
		).addLocalizedStringByLocale(
			"description", AssetVocabulary::getDescription
		).addLocalizedStringByLocale(
			"name", AssetVocabulary::getTitle
		).addLinkedModel(
			"creator", PersonIdentifier.class, AssetVocabulary::getUserId
		).addString(
			"name", AssetVocabulary::getName
		).build();
	}

	private AssetVocabulary _addAssetVocabulary(
			Long groupId, AssetVocabularyForm assetVocabularyForm)
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

		List<AssetVocabulary> className =
			_assetVocabularyService.getGroupVocabularies(
				groupId, pagination.getStartPosition(),
				pagination.getEndPosition(), null);

		return new PageItems<>(className, className.size());
	}

	private AssetVocabulary _updateAssetVocabulary(
			Long vocabularyId, AssetVocabularyForm assetVocabularyForm)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		return _assetVocabularyService.updateVocabulary(
			vocabularyId, assetVocabularyForm.getTitle(),
			assetVocabularyForm.getTitleMap(),
			assetVocabularyForm.getDescriptionMap(), null, serviceContext);
	}

	@Reference
	private AssetVocabularyService _assetVocabularyService;

	@Reference
	private HasPermission _hasPermission;

}