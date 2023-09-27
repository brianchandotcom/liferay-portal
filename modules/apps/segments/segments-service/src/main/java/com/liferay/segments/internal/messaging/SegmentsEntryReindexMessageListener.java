/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.internal.messaging;

import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.segments.internal.constants.SegmentsDestinationNames;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsEntryRelTable;
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

		try {
			Set<Long> newClassPKs = _getNewClassPKs(segmentsEntryId);

			_updateDatabase(segmentsEntryId, newClassPKs);

			_updateIndex(
				message.getLong("companyId"), segmentsEntryId, type,
				newClassPKs, indexer);
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

		return SetUtil.fromArray(classPKs);
	}

	private Set<Long> _getOldDatabaseClassPKs(long segmentsEntryId) {
		Iterable<Long> iterable = _segmentsEntryLocalService.dslQuery(
			DSLQueryFactoryUtil.select(
				SegmentsEntryRelTable.INSTANCE.classPK
			).from(
				SegmentsEntryRelTable.INSTANCE
			).where(
				SegmentsEntryRelTable.INSTANCE.segmentsEntryId.eq(
					segmentsEntryId)
			));

		return SetUtil.fromIterator(iterable.iterator());
	}

	private Set<Long> _getOldIndexClassPKs(
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

	private void _updateDatabase(long segmentsEntryId, Set<Long> newClassPKs)
		throws PortalException {

		SegmentsEntry segmentsEntry =
			_segmentsEntryLocalService.fetchSegmentsEntry(segmentsEntryId);

		if ((segmentsEntry == null) ||
			(segmentsEntry.getCriteriaObj() == null)) {

			return;
		}

		Set<Long> oldClassPKs = _getOldDatabaseClassPKs(segmentsEntryId);

		Set<Long> addClassPKs = new HashSet<>(newClassPKs);
		Set<Long> deleteClassPKs = new HashSet<>();

		for (Long oldClassPK : oldClassPKs) {
			if (!addClassPKs.remove(oldClassPK)) {
				deleteClassPKs.add(oldClassPK);
			}
		}

		long classNameId = _portal.getClassNameId(segmentsEntry.getType());

		_segmentsEntryRelLocalService.deleteSegmentsEntryRels(
			segmentsEntryId, classNameId,
			ArrayUtil.toLongArray(deleteClassPKs));

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(segmentsEntry.getGroupId());
		serviceContext.setUserId(segmentsEntry.getUserId());

		_segmentsEntryRelLocalService.addSegmentsEntryRels(
			segmentsEntryId, classNameId, ArrayUtil.toLongArray(addClassPKs),
			serviceContext);
	}

	private void _updateIndex(
			long companyId, long segmentsEntryId, String type,
			Set<Long> newClassPKs, Indexer<Object> indexer)
		throws PortalException {

		Set<Long> classPKs = SetUtil.symmetricDifference(
			_getOldIndexClassPKs(companyId, segmentsEntryId, indexer),
			newClassPKs);

		for (long classPK : classPKs) {
			indexer.reindex(type, classPK);
		}
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