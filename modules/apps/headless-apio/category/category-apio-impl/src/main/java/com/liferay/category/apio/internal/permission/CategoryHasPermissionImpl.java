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

package com.liferay.category.apio.internal.permission;

import com.liferay.apio.architect.alias.routes.permission.HasNestedAddingPermissionFunction;
import com.liferay.apio.architect.credentials.Credentials;
import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.asset.kernel.service.AssetVocabularyService;
import com.liferay.blog.apio.architect.identifier.BlogPostingIdentifier;
import com.liferay.category.apio.architect.identifier.CategoryIdentifier;
import com.liferay.media.object.apio.architect.identifier.MediaObjectIdentifier;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portlet.asset.service.permission.AssetCategoryPermission;
import com.liferay.structured.content.apio.architect.identifier.StructuredContentIdentifier;
import com.liferay.vocabulary.apio.architect.identifier.VocabularyIdentifier;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Pérez
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.asset.kernel.model.AssetCategory",
	service = HasPermission.class
)
public class CategoryHasPermissionImpl implements HasPermission<Long> {

	@Override
	public <S> HasNestedAddingPermissionFunction<S> forAddingIn(
		Class<? extends Identifier<S>> identifierClass) {

		if (identifierClass.equals(VocabularyIdentifier.class)) {
			return (credentials, vocabularyId) -> {
				AssetVocabulary assetVocabulary =
					_assetVocabularyService.getVocabulary((Long)vocabularyId);

				return AssetCategoryPermission.contains(
					(PermissionChecker)credentials.get(),
					assetVocabulary.getGroupId(),
					AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
					ActionKeys.ADD_CATEGORY);
			};
		}

		if (identifierClass.equals(CategoryIdentifier.class)) {
			return (credentials, parentCategoryId) -> {
				AssetCategory category = _assetCategoryService.getCategory(
					(Long)parentCategoryId);

				return AssetCategoryPermission.contains(
					(PermissionChecker)credentials.get(), category.getGroupId(),
					(Long)parentCategoryId, ActionKeys.ADD_CATEGORY);
			};
		}

		if (identifierClass.equals(BlogPostingIdentifier.class)) {
			return (credentials, blogsEntryId) ->
				_blogsEntryModelResourcePermission.contains(
					(PermissionChecker)credentials.get(), (Long)blogsEntryId,
					ActionKeys.UPDATE);
		}

		if (identifierClass.equals(MediaObjectIdentifier.class)) {
			return (credentials, fileEntryId) ->
				_fileEntryModelResourcePermission.contains(
					(PermissionChecker)credentials.get(), (Long)fileEntryId,
					ActionKeys.UPDATE);
		}

		if (identifierClass.equals(StructuredContentIdentifier.class)) {
			return (credentials, journalArticleId) ->
				_journalArticleModelResourcePermission.contains(
					(PermissionChecker)credentials.get(),
					(Long)journalArticleId, ActionKeys.UPDATE);
		}

		return (credentials, s) -> false;
	}

	@Override
	public Boolean forDeleting(Credentials credentials, Long assetCategoryId)
		throws PortalException {

		return AssetCategoryPermission.contains(
			(PermissionChecker)credentials.get(), assetCategoryId,
			ActionKeys.DELETE);
	}

	@Override
	public Boolean forUpdating(Credentials credentials, Long assetCategoryId)
		throws PortalException {

		return AssetCategoryPermission.contains(
			(PermissionChecker)credentials.get(), assetCategoryId,
			ActionKeys.UPDATE);
	}

	@Reference
	private AssetCategoryService _assetCategoryService;

	@Reference
	private AssetVocabularyService _assetVocabularyService;

	@Reference(target = "(model.class.name=com.liferay.blogs.model.BlogsEntry)")
	private ModelResourcePermission _blogsEntryModelResourcePermission;

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.repository.model.FileEntry)"
	)
	private ModelResourcePermission _fileEntryModelResourcePermission;

	@Reference(
		target = "(model.class.name=com.liferay.journal.model.JournalArticle)"
	)
	private ModelResourcePermission _journalArticleModelResourcePermission;

}