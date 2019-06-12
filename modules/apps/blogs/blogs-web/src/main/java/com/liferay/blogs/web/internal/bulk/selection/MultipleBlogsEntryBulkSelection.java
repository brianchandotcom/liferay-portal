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

package com.liferay.blogs.web.internal.bulk.selection;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.blogs.exception.NoSuchEntryException;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryService;
import com.liferay.bulk.selection.BaseMultipleEntryBulkSelection;
import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionFactory;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class MultipleBlogsEntryBulkSelection
	extends BaseMultipleEntryBulkSelection<BlogsEntry> {

	public MultipleBlogsEntryBulkSelection(
		long[] entryIds, Map<String, String[]> parameterMap,
		BlogsEntryService blogsEntryService,
		AssetEntryLocalService assetEntryLocalService) {

		super(entryIds, parameterMap);

		_blogsEntryService = blogsEntryService;
		_assetEntryLocalService = assetEntryLocalService;
	}

	@Override
	public Class<? extends BulkSelectionFactory>
		getBulkSelectionFactoryClass() {

		return BlogsEntryBulkSelectionFactory.class;
	}

	@Override
	public BulkSelection<AssetEntry> toAssetEntryBulkSelection() {
		return new BlogsEntryAssetEntryBulkSelection(
			this, _assetEntryLocalService);
	}

	@Override
	protected BlogsEntry fetchEntry(long entryId) {
		try {
			return _blogsEntryService.getEntry(entryId);
		}
		catch (NoSuchEntryException nsee) {
			if (_log.isWarnEnabled()) {
				_log.warn(nsee, nsee);
			}

			return null;
		}
		catch (PortalException pe) {
			return ReflectionUtil.throwException(pe);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MultipleBlogsEntryBulkSelection.class);

	private final AssetEntryLocalService _assetEntryLocalService;
	private final BlogsEntryService _blogsEntryService;

}