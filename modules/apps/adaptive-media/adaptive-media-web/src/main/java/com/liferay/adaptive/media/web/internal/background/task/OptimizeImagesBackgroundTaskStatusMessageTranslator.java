/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.web.internal.background.task;

import com.liferay.adaptive.media.constants.AMOptimizeImagesBackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatus;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusMessageTranslator;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.search.background.task.ReindexBackgroundTaskConstants;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Sergio González
 */
public class OptimizeImagesBackgroundTaskStatusMessageTranslator
	implements BackgroundTaskStatusMessageTranslator {

	@Override
	public void translate(
		BackgroundTaskStatus backgroundTaskStatus, Message message) {

		String phase = message.getString(
			AMOptimizeImagesBackgroundTaskConstants.PHASE);

		if (Validator.isNotNull(phase)) {
			_setPhaseAttributes(backgroundTaskStatus, message);

			return;
		}

		String className = message.getString(
			AMOptimizeImagesBackgroundTaskConstants.CLASS_NAME);

		backgroundTaskStatus.setAttribute(
			AMOptimizeImagesBackgroundTaskConstants.CLASS_NAME, className);

		long count = message.getLong(
			AMOptimizeImagesBackgroundTaskConstants.COUNT);

		backgroundTaskStatus.setAttribute(
			AMOptimizeImagesBackgroundTaskConstants.COUNT, count);

		long errors = message.getLong(
			AMOptimizeImagesBackgroundTaskConstants.ERRORS);

		backgroundTaskStatus.setAttribute(
			AMOptimizeImagesBackgroundTaskConstants.ERRORS, errors);

		long total = message.getLong(
			AMOptimizeImagesBackgroundTaskConstants.TOTAL);

		backgroundTaskStatus.setAttribute(
			AMOptimizeImagesBackgroundTaskConstants.TOTAL, total);

		int percentage = 100;

		if (((count + errors) != 0) && (total != 0)) {
			percentage = (int)(count + (errors / total));
		}

		backgroundTaskStatus.setAttribute("percentage", percentage);
	}

	private void _setPhaseAttributes(
		BackgroundTaskStatus backgroundTaskStatus, Message message) {

		backgroundTaskStatus.setAttribute(
			ReindexBackgroundTaskConstants.COMPANY_ID,
			message.getLong(ReindexBackgroundTaskConstants.COMPANY_ID));
		backgroundTaskStatus.setAttribute(
			ReindexBackgroundTaskConstants.PHASE,
			message.getString(ReindexBackgroundTaskConstants.PHASE));
	}

}