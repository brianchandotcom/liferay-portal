/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.web.internal.bulk.selection;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryService;
import com.liferay.bulk.selection.BaseSingleEntryBulkSelection;
import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionFactory;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class SingleBlogsEntryBulkSelection
	extends BaseSingleEntryBulkSelection<BlogsEntry> {

	public SingleBlogsEntryBulkSelection(
		long entryId, Map<String, String[]> parameterMap,
		BlogsEntryService blogsEntryService,
		AssetEntryLocalService assetEntryLocalService) {

		super(entryId, parameterMap);

		_entryId = entryId;
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
	protected BlogsEntry getEntry() throws PortalException {
		return _blogsEntryService.getEntry(_entryId);
	}

	@Override
	protected String getEntryName() throws PortalException {
		BlogsEntry blogsEntry = getEntry();

		return blogsEntry.getTitle();
	}

	private final AssetEntryLocalService _assetEntryLocalService;
	private final BlogsEntryService _blogsEntryService;
	private final long _entryId;

}