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

import static com.liferay.portal.apio.idempotent.Idempotent.idempotent;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyService;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.site.apio.architect.identifier.WebSiteIdentifier;
import com.liferay.taxonomy.apio.architect.identifier.TaxonomyIdentifier;
import com.liferay.taxonomy.apio.internal.architect.form.TaxonomyForm;

import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose {@code Taxonomy} resources
 * through a web API. The resources are mapped from the internal model {@code
 * AssetVocabulary}.
 *
 * @author Javier Gamarra
 * @author Eduardo Pérez
 * @review
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
			this::_addAssetVocabulary,
			_hasPermission.forAddingIn(WebSiteIdentifier.class),
			TaxonomyForm::buildForm
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
			_assetVocabularyService::getVocabulary
		).addUpdater(
			this::_updateAssetVocabulary, _hasPermission::forUpdating,
			TaxonomyForm::buildForm
		).addRemover(
			idempotent(_assetVocabularyService::deleteVocabulary),
			_hasPermission::forDeleting
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
			long groupId, TaxonomyForm taxonomyForm)
		throws PortalException {

		Group group = _groupLocalService.getGroup(groupId);

		Locale locale = LocaleUtil.fromLanguageId(group.getDefaultLanguageId());

		ServiceContext serviceContext = new ServiceContext();

		return _assetVocabularyService.addVocabulary(
			groupId, null, taxonomyForm.getTitles(locale),
			taxonomyForm.getDescriptions(locale), null, serviceContext);
	}

	private PageItems<AssetVocabulary> _getPageItems(
		Pagination pagination, long groupId) {

		List<AssetVocabulary> assetVocabularies =
			_assetVocabularyService.getGroupVocabularies(
				groupId, pagination.getStartPosition(),
				pagination.getEndPosition(), null);
		int count = _assetVocabularyService.getGroupVocabulariesCount(groupId);

		return new PageItems<>(assetVocabularies, count);
	}

	private AssetVocabulary _updateAssetVocabulary(
			long vocabularyId, TaxonomyForm taxonomyForm)
		throws PortalException {

		AssetVocabulary assetVocabulary = _assetVocabularyService.getVocabulary(
			vocabularyId);

		Group group = _groupLocalService.getGroup(assetVocabulary.getGroupId());

		Locale locale = LocaleUtil.fromLanguageId(group.getDefaultLanguageId());

		return _assetVocabularyService.updateVocabulary(
			vocabularyId, null, taxonomyForm.getTitles(locale),
			taxonomyForm.getDescriptions(locale), null, new ServiceContext());
	}

	@Reference
	private AssetVocabularyService _assetVocabularyService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.asset.kernel.model.AssetVocabulary)"
	)
	private HasPermission<Long> _hasPermission;

}