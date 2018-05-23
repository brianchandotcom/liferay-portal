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

package com.liferay.category.apio.internal.architect.router;

import com.liferay.apio.architect.router.NestedCollectionRouter;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.category.apio.identifier.architect.CategoryIdentifier;
import com.liferay.category.apio.internal.architect.router.base.BaseCategoryNestedCollectionRouter;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.media.object.apio.architect.identifier.FileEntryIdentifier;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Perez
 */
@Component(immediate = true)
public class MediaObjectCategoryNestedCollectionRouter extends
	BaseCategoryNestedCollectionRouter<FileEntryIdentifier>
	implements NestedCollectionRouter
		<AssetCategory, Long, CategoryIdentifier, Long, FileEntryIdentifier>{

	@Override
	protected AssetCategoryService getAssetCategoryService() {
		return _assetCategoryService;
	}

	@Override
	protected long getClassNameId() {
		return _classNameLocalService.getClassNameId(
			DLFileEntry.class.getName());
	}

	@Reference
	private AssetCategoryService _assetCategoryService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

}