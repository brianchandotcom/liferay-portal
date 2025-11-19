/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.internal.messaging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.spi.reindexer.BulkReindexer;
import com.liferay.segments.internal.constants.SegmentsDestinationNames;
import com.liferay.segments.internal.helper.IndexerHelper;
import com.liferay.segments.provider.SegmentsEntryProviderRegistry;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.service.SegmentsEntryRelLocalService;

import java.util.Set;

import org.osgi.service.component.annotations.Activate;
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

	@Activate
	protected void activate() {
		_indexerHelper = new IndexerHelper(
			_indexer, _portal, _segmentsEntryLocalService,
			_segmentsEntryProviderRegistry, _segmentsEntryRelLocalService);
	}

	@Override
	protected void doReceive(Message message) {
		long segmentsEntryId = message.getLong("segmentsEntryId");

		if (segmentsEntryId == 0) {
			return;
		}

		try {
			Set<Long> newClassPKs = _indexerHelper.getNewClassPKs(
				segmentsEntryId);

			_indexerHelper.updateDatabase(segmentsEntryId, newClassPKs);

			_updateIndex(
				message.getLong("companyId"), newClassPKs, segmentsEntryId);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to index segment members", exception);
			}
		}
	}

	private void _updateIndex(
			long companyId, Set<Long> newClassPKs, long segmentsEntryId)
		throws Exception {

		_bulkReindexer.reindex(
			companyId,
			_indexerHelper.getIndexableClassPKs(
				companyId, newClassPKs, segmentsEntryId));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsEntryReindexMessageListener.class);

	@Reference(
		target = "(indexer.class.name=com.liferay.portal.kernel.model.User)"
	)
	private BulkReindexer _bulkReindexer;

	@Reference(
		target = "(indexer.class.name=com.liferay.portal.kernel.model.User)"
	)
	private Indexer<User> _indexer;

	private IndexerHelper _indexerHelper;

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	@Reference
	private SegmentsEntryProviderRegistry _segmentsEntryProviderRegistry;

	@Reference
	private SegmentsEntryRelLocalService _segmentsEntryRelLocalService;

}