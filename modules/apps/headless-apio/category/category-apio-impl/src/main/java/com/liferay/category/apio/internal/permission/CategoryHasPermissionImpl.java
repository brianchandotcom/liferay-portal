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

import com.liferay.apio.architect.credentials.Credentials;
import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.asset.kernel.service.AssetVocabularyService;
import com.liferay.blog.apio.architect.identifier.BlogPostingIdentifier;
import com.liferay.category.apio.identifier.architect.CategoryIdentifier;
import com.liferay.media.object.apio.architect.identifier.FileEntryIdentifier;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portlet.asset.service.permission.AssetCategoryPermission;
import com.liferay.taxonomy.apio.identifier.architect.TaxonomyIdentifier;

import java.util.function.BiFunction;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Perez
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.asset.kernel.model.AssetCategory"
)
public class CategoryHasPermissionImpl implements HasPermission<Long> {

	@Override
	public <S> BiFunction<Credentials, S, Boolean> forAddingIn(
		Class<? extends Identifier<S>> identifierClass) {

		if (identifierClass.equals(TaxonomyIdentifier.class)) {
			return (credentials, vocabularyId) -> Try.fromFallible(
				() -> {
					AssetVocabulary vocabulary =
						_assetVocabularyService.getVocabulary(
							(Long)vocabularyId);

					AssetCategoryPermission.check(
						(PermissionChecker)credentials.get(),
						vocabulary.getGroupId(),
						AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
						ActionKeys.ADD_CATEGORY);

					return true;
				}
			).orElse(
				false
			);
		}

		if (identifierClass.equals(CategoryIdentifier.class)) {
			return (credentials, parentCategoryId) -> Try.fromFallible(
				() -> {
					AssetCategory category = _assetCategoryService.getCategory(
						(Long)parentCategoryId);

					AssetCategoryPermission.check(
						(PermissionChecker)credentials.get(),
						category.getGroupId(), (Long)parentCategoryId,
						ActionKeys.ADD_CATEGORY);

					return true;
				}
			).orElse(
				false
			);
		}

		if (identifierClass.equals(BlogPostingIdentifier.class)) {
			return (credentials, blogsEntryId) -> Try.fromFallible(
				() -> {
					_blogsEntryModelResourcePermission.check(
						(PermissionChecker)credentials.get(),
						(Long)blogsEntryId, ActionKeys.UPDATE);

					return true;
				}
			).orElse(
				false
			);
		}

		if (identifierClass.equals(FileEntryIdentifier.class)) {
			return (credentials, fileEntryId) -> Try.fromFallible(
				() -> {
					_fileEntryModelResourcePermission.check(
						(PermissionChecker)credentials.get(), (Long)fileEntryId,
						ActionKeys.UPDATE);

					return true;
				}
			).orElse(
				false
			);
		}

		return (credentials, s) -> false;
	}

	@Override
	public Boolean forDeleting(Credentials credentials, Long entryId) {
		return Try.fromFallible(
			() -> {
				AssetCategoryPermission.check(
					(PermissionChecker)credentials.get(), entryId,
					ActionKeys.DELETE);

				return true;
			}
		).orElse(
			false
		);
	}

	@Override
	public Boolean forUpdating(Credentials credentials, Long entryId) {
		return Try.fromFallible(
			() -> {
				AssetCategoryPermission.check(
					(PermissionChecker)credentials.get(), entryId,
					ActionKeys.UPDATE);

				return true;
			}
		).orElse(
			false
		);
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

}