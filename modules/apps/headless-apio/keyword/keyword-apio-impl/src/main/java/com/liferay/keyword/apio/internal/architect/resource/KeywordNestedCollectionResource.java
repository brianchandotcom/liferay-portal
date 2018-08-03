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

package com.liferay.keyword.apio.internal.architect.resource;

import static com.liferay.portal.apio.idempotent.Idempotent.idempotent;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagService;
import com.liferay.content.space.apio.architect.identifier.ContentSpaceIdentifier;
import com.liferay.keyword.apio.architect.identifier.KeywordIdentifier;
import com.liferay.keyword.apio.internal.architect.form.KeywordForm;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portlet.asset.util.comparator.AssetTagNameComparator;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose {@code Keyword} resources
 * through a web API. The resources are mapped from the internal model {@code
 * AssetTag}.
 *
 * @author Alejandro Hernández
 * @author Ibai Ruiz
 * @author Eduardo Perez
 */
@Component(immediate = true)
public class KeywordNestedCollectionResource
	implements NestedCollectionResource
		<AssetTag, Long, KeywordIdentifier, Long, ContentSpaceIdentifier> {

	@Override
	public NestedCollectionRoutes<AssetTag, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<AssetTag, Long, Long> builder) {

		return builder.addCreator(
			this::_addTag,
			_hasPermission.forAddingIn(ContentSpaceIdentifier.class),
			KeywordForm::buildForm
		).addGetter(
			this::_getPageItems
		).build();
	}

	@Override
	public String getName() {
		return "keywords";
	}

	@Override
	public ItemRoutes<AssetTag, Long> itemRoutes(
		ItemRoutes.Builder<AssetTag, Long> builder) {

		return builder.addGetter(
			_assetTagService::getTag
		).addRemover(
			idempotent(_assetTagService::deleteTag), _hasPermission::forDeleting
		).addUpdater(
			this::_updateTag, _hasPermission::forUpdating,
			KeywordForm::buildForm
		).build();
	}

	@Override
	public Representor<AssetTag> representor(
		Representor.Builder<AssetTag, Long> builder) {

		return builder.types(
			"Keyword"
		).identifier(
			AssetTag::getTagId
		).addBidirectionalModel(
			"contentSpace", "keywords", ContentSpaceIdentifier.class,
			AssetTag::getGroupId
		).addDate(
			"dateCreated", AssetTag::getCreateDate
		).addDate(
			"dateModified", AssetTag::getModifiedDate
		).addLinkedModel(
			"creator", PersonIdentifier.class, AssetTag::getUserId
		).addNumber(
			"keywordUsageCount", AssetTag::getAssetCount
		).addString(
			"name", AssetTag::getName
		).build();
	}

	private AssetTag _addTag(long groupId, KeywordForm keywordForm)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(groupId);

		return _assetTagService.addTag(
			groupId, keywordForm.getName(), serviceContext);
	}

	private PageItems<AssetTag> _getPageItems(
		Pagination pagination, Long groupId) {

		List<AssetTag> assetTags = _assetTagService.getGroupTags(
			groupId, pagination.getStartPosition(), pagination.getEndPosition(),
			new AssetTagNameComparator());
		int count = _assetTagService.getGroupTagsCount(groupId);

		return new PageItems<>(assetTags, count);
	}

	private AssetTag _updateTag(Long assetTagId, KeywordForm keywordForm)
		throws PortalException {

		return _assetTagService.updateTag(
			assetTagId, keywordForm.getName(), null);
	}

	@Reference
	private AssetTagService _assetTagService;

	@Reference(
		target = "(model.class.name=com.liferay.asset.kernel.model.AssetTag)"
	)
	private HasPermission<Long> _hasPermission;

}