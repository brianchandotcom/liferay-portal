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

package com.liferay.portal.search.internal.background.task;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatus;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusMessageTranslator;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.background.task.ReindexBackgroundTaskConstants;
import com.liferay.portal.kernel.util.GetterUtil;
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
			setPhaseAttributes(backgroundTaskStatus, message);

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

		for (long companyId : companyIds) {
			long currentCompanyId = GetterUtil.getLong(
				backgroundTaskStatus.getAttribute(
					ReindexBackgroundTaskConstants.COMPANY_ID));

			if (companyId == currentCompanyId) {
				break;
			}

			companyCount++;
		}

		int percentage = 100;

		if (phase.equals(ReindexBackgroundTaskConstants.PORTAL_START)) {
			String lastIndexer = GetterUtil.getString(
				backgroundTaskStatus.getAttribute("lastIndexer"));
			int indexerCount = GetterUtil.getInteger(
				backgroundTaskStatus.getAttribute("indexerCount"));

			if (Validator.isNull(lastIndexer)) {
				backgroundTaskStatus.setAttribute("lastIndexer", className);
			}
			else if (!lastIndexer.equals(className)) {
				backgroundTaskStatus.setAttribute(
					"indexerCount", ++indexerCount);
				backgroundTaskStatus.setAttribute("lastIndexer", className);
			}

			Set<Indexer<?>> indexers = IndexerRegistryUtil.getIndexers();

			percentage = getPercentage(
				companyCount, companyIds.length, indexerCount, indexers.size(),
				count, total);
		}
		else if (phase.equals(ReindexBackgroundTaskConstants.SINGLE_START)) {
			percentage = getPercentage(
				companyCount, companyIds.length, 0, 1, count, total);
		}

		backgroundTaskStatus.setAttribute("percentage", percentage);
	}

	protected int getPercentage(
		int companyCount, int companyTotal, int indexerCount, int indexerTotal,
		long documentCount, long documentTotal) {

		if (companyTotal <= 0) {
			return 100;
		}

		if (indexerTotal <= 0) {
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

	protected void setPhaseAttributes(
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