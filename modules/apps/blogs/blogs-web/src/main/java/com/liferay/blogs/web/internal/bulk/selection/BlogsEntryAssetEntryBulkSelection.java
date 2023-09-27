/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.web.internal.bulk.selection;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionFactory;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.PortalException;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class BlogsEntryAssetEntryBulkSelection
	implements BulkSelection<AssetEntry> {

	public BlogsEntryAssetEntryBulkSelection(
		BulkSelection<BlogsEntry> blogsEntryBulkSelection,
		AssetEntryLocalService assetEntryLocalService) {

		_blogsEntryBulkSelection = blogsEntryBulkSelection;
		_assetEntryLocalService = assetEntryLocalService;
	}

	@Override
	public <E extends PortalException> void forEach(
			UnsafeConsumer<AssetEntry, E> unsafeConsumer)
		throws PortalException {

		_blogsEntryBulkSelection.forEach(
			blogsEntry -> unsafeConsumer.accept(_toAssetEntry(blogsEntry)));
	}

	@Override
	public Class<? extends BulkSelectionFactory>
		getBulkSelectionFactoryClass() {

		return _blogsEntryBulkSelection.getBulkSelectionFactoryClass();
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return _blogsEntryBulkSelection.getParameterMap();
	}

	@Override
	public long getSize() throws PortalException {
		return _blogsEntryBulkSelection.getSize();
	}

	@Override
	public Serializable serialize() {
		return _blogsEntryBulkSelection.serialize();
	}

	@Override
	public BulkSelection<AssetEntry> toAssetEntryBulkSelection() {
		return this;
	}

	private AssetEntry _toAssetEntry(BlogsEntry blogsEntry) {
		try {
			return _assetEntryLocalService.getEntry(
				BlogsEntry.class.getName(), blogsEntry.getEntryId());
		}
		catch (PortalException portalException) {
			return ReflectionUtil.throwException(portalException);
		}
	}

	private final AssetEntryLocalService _assetEntryLocalService;
	private final BulkSelection<BlogsEntry> _blogsEntryBulkSelection;

}