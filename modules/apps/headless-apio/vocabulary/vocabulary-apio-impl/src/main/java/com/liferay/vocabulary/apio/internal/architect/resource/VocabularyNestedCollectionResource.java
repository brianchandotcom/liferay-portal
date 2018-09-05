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

package com.liferay.vocabulary.apio.internal.architect.resource;

import static com.liferay.portal.apio.idempotent.Idempotent.idempotent;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyService;
import com.liferay.content.space.apio.architect.identifier.ContentSpaceIdentifier;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.vocabulary.apio.architect.identifier.VocabularyIdentifier;
import com.liferay.vocabulary.apio.internal.architect.form.VocabularyForm;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose {@code Vocabulary} resources
 * through a web API. The resources are mapped from the internal model {@code
 * AssetVocabulary}.
 *
 * @author Javier Gamarra
 * @author Eduardo Pérez
 * @review
 */
@Component(immediate = true)
public class VocabularyNestedCollectionResource
	implements NestedCollectionResource
		<AssetVocabulary, Long, VocabularyIdentifier, Long,
		 ContentSpaceIdentifier> {

	@Override
	public NestedCollectionRoutes<AssetVocabulary, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<AssetVocabulary, Long, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).addCreator(
			this::_addAssetVocabulary,
			_hasPermission.forAddingIn(ContentSpaceIdentifier.class),
			VocabularyForm::buildForm
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
			_assetVocabularyService::getVocabulary
		).addUpdater(
			this::_updateAssetVocabulary, _hasPermission::forUpdating,
			VocabularyForm::buildForm
		).addRemover(
			idempotent(_assetVocabularyService::deleteVocabulary),
			_hasPermission::forDeleting
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
			"contentSpace", "vocabularies", ContentSpaceIdentifier.class,
			AssetVocabulary::getGroupId
		).addDate(
			"dateCreated", AssetVocabulary::getCreateDate
		).addDate(
			"dateModified", AssetVocabulary::getModifiedDate
		).addLinkedModel(
			"creator", PersonIdentifier.class, AssetVocabulary::getUserId
		).addLocalizedStringByLocale(
			"description", AssetVocabulary::getDescription
		).addLocalizedStringByLocale(
			"name", AssetVocabulary::getTitle
		).addStringList(
			"availableLanguages",
			vocabulary -> Arrays.asList(vocabulary.getAvailableLanguageIds())
		).build();
	}

	private AssetVocabulary _addAssetVocabulary(
			long groupId, VocabularyForm vocabularyForm)
		throws PortalException {

		Group group = _groupLocalService.getGroup(groupId);

		Locale locale = LocaleUtil.fromLanguageId(group.getDefaultLanguageId());

		ServiceContext serviceContext = new ServiceContext();

		return _assetVocabularyService.addVocabulary(
			groupId, null, vocabularyForm.getTitles(locale),
			vocabularyForm.getDescriptions(locale), null, serviceContext);
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
			long vocabularyId, VocabularyForm vocabularyForm)
		throws PortalException {

		AssetVocabulary assetVocabulary = _assetVocabularyService.getVocabulary(
			vocabularyId);

		Group group = _groupLocalService.getGroup(assetVocabulary.getGroupId());

		Locale locale = LocaleUtil.fromLanguageId(group.getDefaultLanguageId());

		return _assetVocabularyService.updateVocabulary(
			vocabularyId, null, vocabularyForm.getTitles(locale),
			vocabularyForm.getDescriptions(locale), null, new ServiceContext());
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