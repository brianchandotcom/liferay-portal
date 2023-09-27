/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.web.internal.bulk.selection;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryService;
import com.liferay.bulk.selection.BaseContainerEntryBulkSelection;
import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionFactory;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.interval.IntervalActionProcessor;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;
import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class GroupBlogsEntryBulkSelection
	extends BaseContainerEntryBulkSelection<BlogsEntry> {

	public GroupBlogsEntryBulkSelection(
		long groupId, Map<String, String[]> parameterMap,
		BlogsEntryService blogsEntryService,
		AssetEntryLocalService assetEntryLocalService) {

		super(groupId, parameterMap);

		_groupId = groupId;
		_blogsEntryService = blogsEntryService;
		_assetEntryLocalService = assetEntryLocalService;
	}

	@Override
	public <E extends PortalException> void forEach(
			UnsafeConsumer<BlogsEntry, E> unsafeConsumer)
		throws PortalException {

		IntervalActionProcessor<BlogsEntry> blogsEntryIntervalActionProcessor =
			new IntervalActionProcessor<>((int)getSize());

		blogsEntryIntervalActionProcessor.setPerformIntervalActionMethod(
			(start, end) -> {
				List<BlogsEntry> blogsEntries =
					_blogsEntryService.getGroupEntries(
						_groupId, WorkflowConstants.STATUS_APPROVED, start,
						end);

				for (BlogsEntry blogsEntry : blogsEntries) {
					unsafeConsumer.accept(blogsEntry);
				}

				return null;
			});

		blogsEntryIntervalActionProcessor.performIntervalActions();
	}

	@Override
	public Class<? extends BulkSelectionFactory>
		getBulkSelectionFactoryClass() {

		return BlogsEntryBulkSelectionFactory.class;
	}

	@Override
	public long getSize() {
		return _blogsEntryService.getGroupEntriesCount(
			_groupId, WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public BulkSelection<AssetEntry> toAssetEntryBulkSelection() {
		return new BlogsEntryAssetEntryBulkSelection(
			this, _assetEntryLocalService);
	}

	private final AssetEntryLocalService _assetEntryLocalService;
	private final BlogsEntryService _blogsEntryService;
	private final long _groupId;

}