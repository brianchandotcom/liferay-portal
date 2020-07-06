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

package com.liferay.segments.internal.background.task;

import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.backgroundtask.BaseBackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplay;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.provider.SegmentsEntryProviderRegistry;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.service.SegmentsEntryRelLocalService;

import java.io.Serializable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	immediate = true,
	property = "background.task.executor.class.name=com.liferay.segments.internal.background.task.SegmentsEntryRelIndexerBackgroundTaskExecutor",
	service = BackgroundTaskExecutor.class
)
public class SegmentsEntryRelIndexerBackgroundTaskExecutor
	extends BaseBackgroundTaskExecutor {

	public static String getBackgroundTaskName(long segmentsEntryId) {
		return _BACKGROUND_TASK_NAME_PREFIX + segmentsEntryId;
	}

	@Override
	public BackgroundTaskExecutor clone() {
		return this;
	}

	@Override
	public BackgroundTaskResult execute(BackgroundTask backgroundTask) {
		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		String type = (String)taskContextMap.get("type");

		Indexer<Object> indexer = _indexerRegistry.getIndexer(type);

		if (indexer == null) {
			return BackgroundTaskResult.SUCCESS;
		}

		Number segmentsEntryIdNumber = (Number)taskContextMap.get(
			"segmentsEntryId");

		long segmentsEntryId = segmentsEntryIdNumber.longValue();

		if (segmentsEntryId == 0) {
			return BackgroundTaskResult.SUCCESS;
		}

		try {
			Set<Long> classPKs = SetUtil.symmetricDifference(
				_getOldClassPKs(
					backgroundTask.getCompanyId(), segmentsEntryId, indexer),
				_getNewClassPKs(segmentsEntryId));

			for (long classPK : classPKs) {
				indexer.reindex(type, classPK);
			}
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to index segment members", portalException);
			}
		}

		return BackgroundTaskResult.SUCCESS;
	}

	@Override
	public BackgroundTaskDisplay getBackgroundTaskDisplay(
		BackgroundTask backgroundTask) {

		return null;
	}

	@Override
	public int getIsolationLevel() {
		return BackgroundTaskConstants.ISOLATION_LEVEL_TASK_NAME;
	}

	@Override
	public boolean isSerial() {
		return true;
	}

	private Set<Long> _getNewClassPKs(long segmentsEntryId)
		throws PortalException {

		long[] classPKs =
			_segmentsEntryProviderRegistry.getSegmentsEntryClassPKs(
				segmentsEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		SegmentsEntry segmentsEntry =
			_segmentsEntryLocalService.fetchSegmentsEntry(segmentsEntryId);

		if ((segmentsEntry != null) &&
			(segmentsEntry.getCriteriaObj() != null)) {

			_segmentsEntryRelLocalService.deleteSegmentsEntryRels(
				segmentsEntryId);

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setScopeGroupId(segmentsEntry.getGroupId());
			serviceContext.setUserId(segmentsEntry.getUserId());

			_segmentsEntryRelLocalService.addSegmentsEntryRels(
				segmentsEntryId,
				_portal.getClassNameId(segmentsEntry.getType()), classPKs,
				serviceContext);
		}

		return SetUtil.fromArray(classPKs);
	}

	private Set<Long> _getOldClassPKs(
			long companyId, long segmentsEntryId, Indexer<Object> indexer)
		throws SearchException {

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setAttribute(
			"segmentsEntryIds", new long[] {segmentsEntryId});

		Hits hits = indexer.search(searchContext);

		Set<Long> classPKsSet = new HashSet<>();

		for (Document document : hits.getDocs()) {
			classPKsSet.add(
				GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)));
		}

		Stream<Document> stream = Arrays.stream(hits.getDocs());

		return stream.map(
			doc -> GetterUtil.getLong(doc.getField(Field.ENTRY_CLASS_PK))
		).collect(
			Collectors.toSet()
		);
	}

	private static final String _BACKGROUND_TASK_NAME_PREFIX =
		"SegmentsEntryRelIndexerBackgroundTaskExecutor-";

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsEntryRelIndexerBackgroundTaskExecutor.class);

	@Reference
	private IndexerRegistry _indexerRegistry;

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	@Reference
	private SegmentsEntryProviderRegistry _segmentsEntryProviderRegistry;

	@Reference
	private SegmentsEntryRelLocalService _segmentsEntryRelLocalService;

}