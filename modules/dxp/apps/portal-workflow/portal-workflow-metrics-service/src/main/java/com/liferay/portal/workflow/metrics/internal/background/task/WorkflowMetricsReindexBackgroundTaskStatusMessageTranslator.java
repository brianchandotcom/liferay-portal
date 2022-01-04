/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.metrics.internal.background.task;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatus;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusMessageTranslator;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.workflow.metrics.internal.background.task.constants.WorkflowMetricsReindexBackgroundTaskConstants;

import org.apache.commons.lang.ArrayUtils;

/**
 * @author Rafael Praxedes
 */
public class WorkflowMetricsReindexBackgroundTaskStatusMessageTranslator
	implements BackgroundTaskStatusMessageTranslator {

	@Override
	public void translate(
		BackgroundTaskStatus backgroundTaskStatus, Message message) {

		String phase = message.getString(
			WorkflowMetricsReindexBackgroundTaskConstants.PHASE);

		if (Validator.isNotNull(phase)) {
			_setPhaseAttributes(backgroundTaskStatus, message);

			return;
		}

		String indexEntityName = message.getString(
			WorkflowMetricsReindexBackgroundTaskConstants.INDEX_ENTITY_NAME);

		long count = message.getLong(
			WorkflowMetricsReindexBackgroundTaskConstants.COUNT);

		long total = message.getLong(
			WorkflowMetricsReindexBackgroundTaskConstants.TOTAL);

		int percentage = 0;

		if (Validator.isNull(indexEntityName)) {
			percentage = (int)(((count + 1) / (double)total) * 100);
		}
		else {
			String[] indexEntityNames =
				(String[])backgroundTaskStatus.getAttribute(
					WorkflowMetricsReindexBackgroundTaskConstants.
						INDEX_ENTITY_NAMES);

			percentage = _getPercentage(
				count, ArrayUtils.indexOf(indexEntityNames, indexEntityName),
				indexEntityNames.length, total);
		}

		backgroundTaskStatus.setAttribute(
			"percentage",
			Math.max(
				GetterUtil.getInteger(
					backgroundTaskStatus.getAttribute("percentage")),
				percentage));
	}

	private int _getPercentage(
		long count, int indexerCount, int indexerTotal, long total) {

		if ((total <= 0) || (indexerTotal <= 0)) {
			return 100;
		}

		double indexerPercentage = count / (double)total;

		double totalPercentage =
			(indexerCount + indexerPercentage) / indexerTotal;

		return (int)Math.min(Math.ceil(totalPercentage * 100), 100);
	}

	private void _setPhaseAttributes(
		BackgroundTaskStatus backgroundTaskStatus, Message message) {

		String[] indexEntityNames = (String[])message.get(
			WorkflowMetricsReindexBackgroundTaskConstants.INDEX_ENTITY_NAMES);

		backgroundTaskStatus.setAttribute(
			WorkflowMetricsReindexBackgroundTaskConstants.COMPANY_ID,
			message.getLong(
				WorkflowMetricsReindexBackgroundTaskConstants.COMPANY_ID));

		if (ArrayUtil.isNotEmpty(indexEntityNames)) {
			backgroundTaskStatus.setAttribute(
				WorkflowMetricsReindexBackgroundTaskConstants.INDEX_ENTITY_NAME,
				indexEntityNames[0]);
			backgroundTaskStatus.setAttribute(
				WorkflowMetricsReindexBackgroundTaskConstants.
					INDEX_ENTITY_NAMES,
				indexEntityNames);
		}

		backgroundTaskStatus.setAttribute(
			WorkflowMetricsReindexBackgroundTaskConstants.PHASE,
			message.getString(
				WorkflowMetricsReindexBackgroundTaskConstants.PHASE));
	}

}