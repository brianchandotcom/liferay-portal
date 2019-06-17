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

import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryService;
import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionFactory;
import com.liferay.bulk.selection.EmptyBulkSelection;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.blogs.model.BlogsEntry",
	service = {BlogsEntryBulkSelectionFactory.class, BulkSelectionFactory.class}
)
public class BlogsEntryBulkSelectionFactory
	implements BulkSelectionFactory<BlogsEntry> {

	@Override
	public BulkSelection<BlogsEntry> create(
		Map<String, String[]> parameterMap) {

		boolean selectAll = MapUtil.getBoolean(parameterMap, "selectAll");

		if (selectAll) {
			long groupId = MapUtil.getLong(parameterMap, "groupId");

			return new GroupBlogsEntryBulkSelection(
				groupId, parameterMap, _blogsEntryService,
				_assetEntryLocalService);
		}

		long entryId = MapUtil.getLong(parameterMap, "entryId");

		if (entryId > 0) {
			return new SingleBlogsEntryBulkSelection(
				entryId, parameterMap, _blogsEntryService,
				_assetEntryLocalService);
		}

		long[] entryIds = GetterUtil.getLongValues(
			StringUtil.split(
				MapUtil.getString(parameterMap, "deleteEntryIds")));

		if (ArrayUtil.isNotEmpty(entryIds)) {
			return new MultipleBlogsEntryBulkSelection(
				entryIds, parameterMap, _blogsEntryService,
				_assetEntryLocalService);
		}

		return new EmptyBulkSelection<>();
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private BlogsEntryService _blogsEntryService;

}