/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.background.task;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatus;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusMessageTranslator;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.background.task.ReindexBackgroundTaskConstants;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Set;

/**
 * @author Andrew Betts
 */
public class ReindexBackgroundTaskStatusMessageTranslator
	implements BackgroundTaskStatusMessageTranslator {

	@Override
	public void translate(
		BackgroundTaskStatus backgroundTaskStatus, Message message) {

		String phase = message.getString(ReindexBackgroundTaskConstants.PHASE);

		if (Validator.isNotNull(phase)) {
			_setPhaseAttributes(backgroundTaskStatus, message);

			return;
		}

		String reindexType = GetterUtil.getString(
			backgroundTaskStatus.getAttribute(_REINDEX_TYPE));

		String className = message.getString(
			ReindexBackgroundTaskConstants.CLASS_NAME);

		backgroundTaskStatus.setAttribute(
			ReindexBackgroundTaskConstants.CLASS_NAME, className);

		long count = message.getLong(ReindexBackgroundTaskConstants.COUNT);

		backgroundTaskStatus.setAttribute(
			ReindexBackgroundTaskConstants.COUNT, count);

		long total = message.getLong(ReindexBackgroundTaskConstants.TOTAL);

		backgroundTaskStatus.setAttribute(
			ReindexBackgroundTaskConstants.TOTAL, total);

		long[] companyIds = GetterUtil.getLongValues(
			backgroundTaskStatus.getAttribute(
				ReindexBackgroundTaskConstants.COMPANY_IDS));

		int completedCompanyCount = GetterUtil.getInteger(
			backgroundTaskStatus.getAttribute(_COMPLETED_COMPANY_COUNT));

		int percentage = 100;

		if (_REINDEX_TYPE_PORTAL.equals(reindexType)) {
			int activeCompanyCount = companyIds.length - completedCompanyCount;

			if (activeCompanyCount > 0) {
				String[] pastIndexers = GetterUtil.getStringValues(
					backgroundTaskStatus.getAttribute(_PAST_INDEXERS));
				int indexerCount = GetterUtil.getInteger(
					backgroundTaskStatus.getAttribute(_INDEXER_COUNT));

				Set<String> pastIndexersSet = SetUtil.fromArray(pastIndexers);

				if (pastIndexersSet.isEmpty()) {
					backgroundTaskStatus.setAttribute(
						_PAST_INDEXERS, new String[] {className});
				}
				else if (pastIndexersSet.add(className)) {
					backgroundTaskStatus.setAttribute(
						_INDEXER_COUNT, ++indexerCount);
					backgroundTaskStatus.setAttribute(
						_PAST_INDEXERS,
						ArrayUtil.toStringArray(pastIndexersSet));
				}

				Set<Indexer<?>> indexers = IndexerRegistryUtil.getIndexers();

				percentage = _getPercentage(
					completedCompanyCount, companyIds.length, indexerCount,
					indexers.size(), count, total);

				if (companyIds.length > 1) {
					percentage = Math.min(percentage, 99);
				}
			}
		}
		else if (_REINDEX_TYPE_SINGLE.equals(reindexType)) {
			int activeCompanyCount = companyIds.length - completedCompanyCount;

			if (activeCompanyCount > 0) {
				percentage = _getPercentage(
					completedCompanyCount, companyIds.length, 0, 1, count,
					total);

				if (companyIds.length > 1) {
					percentage = Math.min(percentage, 99);
				}
			}
		}

		backgroundTaskStatus.setAttribute(
			"percentage", String.valueOf(percentage));
	}

	private int _getPercentage(
		int companyCount, int companyTotal, int indexerCount, int indexerTotal,
		long documentCount, long documentTotal) {

		if ((companyTotal <= 0) || (indexerTotal <= 0)) {
			return 100;
		}

		double indexerPercentage = 1;

		if (documentTotal != 0) {
			indexerPercentage = (double)documentCount / documentTotal;
		}

		double companyPercentage =
			(indexerCount + indexerPercentage) / indexerTotal;

		double totalPercentage =
			(companyCount + companyPercentage) / companyTotal;

		return (int)Math.min(Math.ceil(totalPercentage * 100), 100);
	}

	private void _setPhaseAttributes(
		BackgroundTaskStatus backgroundTaskStatus, Message message) {

		long[] companyIds = GetterUtil.getLongValues(
			message.get(ReindexBackgroundTaskConstants.COMPANY_IDS));
		String phase = message.getString(ReindexBackgroundTaskConstants.PHASE);

		backgroundTaskStatus.setAttribute(
			ReindexBackgroundTaskConstants.COMPANY_ID,
			message.getLong(ReindexBackgroundTaskConstants.COMPANY_ID));
		backgroundTaskStatus.setAttribute(
			ReindexBackgroundTaskConstants.COMPANY_IDS, companyIds);

		if (ReindexBackgroundTaskConstants.PORTAL_START.equals(phase)) {
			backgroundTaskStatus.setAttribute(
				_REINDEX_TYPE, _REINDEX_TYPE_PORTAL);
			backgroundTaskStatus.setAttribute(
				ReindexBackgroundTaskConstants.PHASE, phase);
		}
		else if (ReindexBackgroundTaskConstants.SINGLE_START.equals(phase)) {
			backgroundTaskStatus.setAttribute(
				_REINDEX_TYPE, _REINDEX_TYPE_SINGLE);
			backgroundTaskStatus.setAttribute(
				ReindexBackgroundTaskConstants.PHASE, phase);
		}
		else if (ReindexBackgroundTaskConstants.PORTAL_END.equals(phase) ||
				 ReindexBackgroundTaskConstants.SINGLE_END.equals(phase)) {

			int completedCompanyCount = GetterUtil.getInteger(
				backgroundTaskStatus.getAttribute(_COMPLETED_COMPANY_COUNT));

			completedCompanyCount++;

			backgroundTaskStatus.setAttribute(
				_COMPLETED_COMPANY_COUNT, completedCompanyCount);

			backgroundTaskStatus.setAttribute(_INDEXER_COUNT, 0);
			backgroundTaskStatus.setAttribute(_PAST_INDEXERS, new String[0]);

			int percentage;

			if (completedCompanyCount >= companyIds.length) {
				backgroundTaskStatus.setAttribute(
					ReindexBackgroundTaskConstants.PHASE, phase);

				percentage = 100;
			}
			else {
				percentage = (int)Math.ceil(
					(double)completedCompanyCount / companyIds.length * 100);

				percentage = Math.min(percentage, 99);
			}

			backgroundTaskStatus.setAttribute(
				"percentage", String.valueOf(percentage));
		}
	}

	private static final String _COMPLETED_COMPANY_COUNT =
		"completedCompanyCount";

	private static final String _INDEXER_COUNT = "indexerCount";

	private static final String _PAST_INDEXERS = "pastIndexers";

	private static final String _REINDEX_TYPE = "reindexType";

	private static final String _REINDEX_TYPE_PORTAL = "portal";

	private static final String _REINDEX_TYPE_SINGLE = "single";

}