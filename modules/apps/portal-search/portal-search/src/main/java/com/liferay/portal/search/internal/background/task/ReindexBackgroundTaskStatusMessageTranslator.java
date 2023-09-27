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

		phase = GetterUtil.getString(
			backgroundTaskStatus.getAttribute(
				ReindexBackgroundTaskConstants.PHASE));

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

		int companyCount = 0;

		long[] companyIds = GetterUtil.getLongValues(
			backgroundTaskStatus.getAttribute(
				ReindexBackgroundTaskConstants.COMPANY_IDS));

		long currentCompanyId = GetterUtil.getLong(
			backgroundTaskStatus.getAttribute(
				ReindexBackgroundTaskConstants.COMPANY_ID));

		for (long companyId : companyIds) {
			if (companyId == currentCompanyId) {
				break;
			}

			companyCount++;
		}

		int percentage = 100;

		if (phase.equals(ReindexBackgroundTaskConstants.PORTAL_START)) {
			String[] pastIndexers = GetterUtil.getStringValues(
				backgroundTaskStatus.getAttribute(
					"pastIndexers" + currentCompanyId));
			int indexerCount = GetterUtil.getInteger(
				backgroundTaskStatus.getAttribute(
					"indexerCount" + currentCompanyId));

			Set<String> pastIndexersSet = SetUtil.fromArray(pastIndexers);

			if (pastIndexersSet.isEmpty()) {
				backgroundTaskStatus.setAttribute(
					"pastIndexers" + currentCompanyId,
					new String[] {className});
			}
			else if (pastIndexersSet.add(className)) {
				backgroundTaskStatus.setAttribute(
					"indexerCount" + currentCompanyId, ++indexerCount);
				backgroundTaskStatus.setAttribute(
					"pastIndexers" + currentCompanyId,
					ArrayUtil.toStringArray(pastIndexersSet));
			}

			Set<Indexer<?>> indexers = IndexerRegistryUtil.getIndexers();

			percentage = _getPercentage(
				companyCount, companyIds.length, indexerCount, indexers.size(),
				count, total);
		}
		else if (phase.equals(ReindexBackgroundTaskConstants.SINGLE_START)) {
			percentage = _getPercentage(
				companyCount, companyIds.length, 0, 1, count, total);
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

		backgroundTaskStatus.setAttribute(
			ReindexBackgroundTaskConstants.COMPANY_ID,
			message.getLong(ReindexBackgroundTaskConstants.COMPANY_ID));
		backgroundTaskStatus.setAttribute(
			ReindexBackgroundTaskConstants.COMPANY_IDS,
			GetterUtil.getLongValues(
				message.get(ReindexBackgroundTaskConstants.COMPANY_IDS)));
		backgroundTaskStatus.setAttribute(
			ReindexBackgroundTaskConstants.PHASE,
			message.getString(ReindexBackgroundTaskConstants.PHASE));
	}

}