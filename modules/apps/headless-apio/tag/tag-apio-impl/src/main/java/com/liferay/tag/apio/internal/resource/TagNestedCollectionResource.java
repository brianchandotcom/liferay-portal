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

package com.liferay.tag.apio.internal.resource;

import static com.liferay.portal.apio.idempotent.Idempotent.idempotent;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetTagService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.site.apio.architect.identifier.WebSiteIdentifier;
import com.liferay.tag.apio.identifier.TagIdentifier;
import com.liferay.tag.apio.internal.form.TagForm;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose {@link AssetTag}
 * resources through a web API.
 *
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class TagNestedCollectionResource
	implements NestedCollectionResource
		<AssetTag, Long, TagIdentifier, Long, WebSiteIdentifier> {

	@Override
	public NestedCollectionRoutes<AssetTag, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<AssetTag, Long, Long> builder) {

		return builder.addCreator(
			this::_addTag,
			(credentials, groupId) -> {
				//TODO Permission for creating tags

				return true;
			},
			TagForm::buildForm
		).addGetter(
			this::_getPageItems
		).build();
	}

	@Override
	public String getName() {
		return "tags";
	}

	@Override
	public ItemRoutes<AssetTag, Long> itemRoutes(
		ItemRoutes.Builder<AssetTag, Long> builder) {

		return builder.addGetter(
			_assetTagService::getTag
		).addRemover(
			idempotent(_assetTagService::deleteTag),
			(credentials, assetTagId) -> {
				//TODO Permissions for removing tags

				return true;
			}
		).addUpdater(
			this::_updateTag,
			(credentials, assetTagId) -> {
				//TODO Permissions for updating tags

				return true;
			},
			TagForm::buildForm
		).build();
	}

	@Override
	public Representor<AssetTag> representor(
		Representor.Builder<AssetTag, Long> builder) {

		return builder.types(
			"Tag"
		).identifier(
			AssetTag::getTagId
		).addBidirectionalModel(
			"interactionService", "tag", WebSiteIdentifier.class,
			AssetTag::getGroupId
		).addString(
			"name", AssetTag::getName
		).addNumber(
			"usages",
			assetTag -> {
				return _assetEntryLocalService.getAssetTagAssetEntriesCount(
					assetTag.getTagId());
			}
		).build();
	}

	private AssetTag _addTag(Long groupId, TagForm tagForm)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(groupId);

		return _assetTagService.addTag(
			groupId, tagForm.getName(), serviceContext);
	}

	private PageItems<AssetTag> _getPageItems(
		Pagination pagination, Long groupId) {

		List<AssetTag> tags = _assetTagService.getGroupTags(
			groupId, pagination.getStartPosition(), pagination.getEndPosition(),
			null);

		int totalCount = _assetTagService.getGroupTagsCount(groupId);

		return new PageItems<>(tags, totalCount);
	}

	private AssetTag _updateTag(Long assetTagId, TagForm tagForm)
		throws PortalException {

		return _assetTagService.updateTag(assetTagId, tagForm.getName(), null);
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private AssetTagService _assetTagService;

}