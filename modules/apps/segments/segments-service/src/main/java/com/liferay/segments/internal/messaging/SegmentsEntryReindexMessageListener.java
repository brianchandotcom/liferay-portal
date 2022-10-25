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

package com.liferay.segments.internal.messaging;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
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
import com.liferay.segments.internal.constants.SegmentsDestinationNames;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.provider.SegmentsEntryProviderRegistry;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.service.SegmentsEntryRelLocalService;

import java.util.HashSet;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 * @author Preston Crary
 */
@Component(
	immediate = true,
	property = "destination.name=" + SegmentsDestinationNames.SEGMENTS_ENTRY_REINDEX,
	service = MessageListener.class
)
public class SegmentsEntryReindexMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) {
		String type = message.getString("type");

		Indexer<Object> indexer = _indexerRegistry.getIndexer(type);

		if (indexer == null) {
			return;
		}

		long segmentsEntryId = message.getLong("segmentsEntryId");

		if (segmentsEntryId == 0) {
			return;
		}

		long companyId = message.getLong("companyId");

		try {
			Set<Long> classPKs = SetUtil.symmetricDifference(
				_getOldClassPKs(companyId, segmentsEntryId, indexer),
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

		searchContext.setAttribute(
			"segmentsEntryIds", new long[] {segmentsEntryId});
		searchContext.setCompanyId(companyId);

		Hits hits = indexer.search(searchContext);

		Set<Long> classPKsSet = new HashSet<>();

		for (Document document : hits.getDocs()) {
			classPKsSet.add(
				GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)));
		}

		return classPKsSet;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsEntryReindexMessageListener.class);

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