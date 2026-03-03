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
		int completedCompaniesCount = GetterUtil.getInteger(
			backgroundTaskStatus.getAttribute("completedCompaniesCount"));
		int percentage = 100;

		String reindexType = GetterUtil.getString(
			backgroundTaskStatus.getAttribute("reindexType"));

		if (reindexType.equals("portal")) {
			int activeCompaniesCount =
				companyIds.length - completedCompaniesCount;

			if (activeCompaniesCount > 0) {
				int indexerCount = GetterUtil.getInteger(
					backgroundTaskStatus.getAttribute("indexerCount"));

				String[] pastIndexers = GetterUtil.getStringValues(
					backgroundTaskStatus.getAttribute("pastIndexers"));

				Set<String> pastIndexersSet = SetUtil.fromArray(pastIndexers);

				if (pastIndexersSet.isEmpty()) {
					backgroundTaskStatus.setAttribute(
						"pastIndexers", new String[] {className});
				}
				else if (pastIndexersSet.add(className)) {
					backgroundTaskStatus.setAttribute(
						"indexerCount", ++indexerCount);
					backgroundTaskStatus.setAttribute(
						"pastIndexers",
						ArrayUtil.toStringArray(pastIndexersSet));
				}

				Set<Indexer<?>> indexers = IndexerRegistryUtil.getIndexers();

				percentage = _getPercentage(
					completedCompaniesCount, companyIds.length, indexerCount,
					indexers.size(), count, total);

				if (companyIds.length > 1) {
					percentage = Math.min(percentage, 99);
				}
			}
		}
		else if (reindexType.equals("single")) {
			int activeCompaniesCount =
				companyIds.length - completedCompaniesCount;

			if (activeCompaniesCount > 0) {
				percentage = _getPercentage(
					completedCompaniesCount, companyIds.length, 0, 1, count,
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
		int companiesCount, int companyTotal, int indexerCount,
		int indexerTotal, long documentCount, long documentTotal) {

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
			(companiesCount + companyPercentage) / companyTotal;

		return (int)Math.min(Math.ceil(totalPercentage * 100), 100);
	}

	private void _setPhaseAttributes(
		BackgroundTaskStatus backgroundTaskStatus, Message message) {

		long[] companyIds = GetterUtil.getLongValues(
			message.get(ReindexBackgroundTaskConstants.COMPANY_IDS));
		String phase = GetterUtil.getString(
			message.getString(ReindexBackgroundTaskConstants.PHASE));

		backgroundTaskStatus.setAttribute(
			ReindexBackgroundTaskConstants.COMPANY_ID,
			message.getLong(ReindexBackgroundTaskConstants.COMPANY_ID));
		backgroundTaskStatus.setAttribute(
			ReindexBackgroundTaskConstants.COMPANY_IDS, companyIds);

		if (phase.equals(ReindexBackgroundTaskConstants.PORTAL_START)) {
			backgroundTaskStatus.setAttribute(
				ReindexBackgroundTaskConstants.PHASE, phase);
			backgroundTaskStatus.setAttribute("reindexType", "portal");
		}
		else if (phase.equals(ReindexBackgroundTaskConstants.SINGLE_START)) {
			backgroundTaskStatus.setAttribute(
				ReindexBackgroundTaskConstants.PHASE, phase);
			backgroundTaskStatus.setAttribute("reindexType", "single");
		}
		else if (phase.equals(ReindexBackgroundTaskConstants.PORTAL_END) ||
				 phase.equals(ReindexBackgroundTaskConstants.SINGLE_END)) {

			int completedCompaniesCount = GetterUtil.getInteger(
				backgroundTaskStatus.getAttribute("completedCompaniesCount"));

			completedCompaniesCount++;

			backgroundTaskStatus.setAttribute(
				"completedCompaniesCount", completedCompaniesCount);

			backgroundTaskStatus.setAttribute("indexerCount", 0);
			backgroundTaskStatus.setAttribute("pastIndexers", new String[0]);

			int percentage = 0;

			if (completedCompaniesCount >= companyIds.length) {
				backgroundTaskStatus.setAttribute(
					ReindexBackgroundTaskConstants.PHASE, phase);

				percentage = 100;
			}
			else {
				percentage = (int)Math.ceil(
					(double)completedCompaniesCount / companyIds.length * 100);

				percentage = Math.min(percentage, 99);
			}

			backgroundTaskStatus.setAttribute(
				"percentage", String.valueOf(percentage));
		}
	}

}