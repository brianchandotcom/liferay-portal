/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.model.listener;

import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.ratings.kernel.model.RatingsStats;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Felipe Veloso
 */
@Component(service = ModelListener.class)
public class RatingsStatsModelListener extends BaseModelListener<RatingsStats> {

	@Override
	public void onAfterCreate(RatingsStats ratingsStats)
		throws ModelListenerException {

		MBMessage mbMessage = _mbMessageLocalService.fetchMBMessage(
			ratingsStats.getClassPK());

		if (mbMessage == null) {
			return;
		}

		Indexer<MBMessage> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			MBMessage.class);

		try {
			indexer.reindex(mbMessage);
		}
		catch (SearchException searchException) {
			throw new ModelListenerException(searchException);
		}
	}

	@Override
	public void onAfterUpdate(
			RatingsStats originalRatingsStats, RatingsStats ratingsStats)
		throws ModelListenerException {

		MBMessage mbMessage = _mbMessageLocalService.fetchMBMessage(
			ratingsStats.getClassPK());

		if (mbMessage == null) {
			return;
		}

		Indexer<MBMessage> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			MBMessage.class);

		try {
			indexer.reindex(mbMessage);
		}
		catch (SearchException searchException) {
			throw new ModelListenerException(searchException);
		}
	}

	@Reference
	private MBMessageLocalService _mbMessageLocalService;

	@Reference
	private MBThreadLocalService _mbThreadLocalService;

}