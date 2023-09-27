/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		catch (NoSuchEntryException noSuchEntryException) {
			if (_log.isWarnEnabled()) {
				_log.warn(noSuchEntryException);
			}

			return null;
		}
		catch (PortalException portalException) {
			return ReflectionUtil.throwException(portalException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MultipleBlogsEntryBulkSelection.class);

	private final AssetEntryLocalService _assetEntryLocalService;
	private final BlogsEntryService _blogsEntryService;

}