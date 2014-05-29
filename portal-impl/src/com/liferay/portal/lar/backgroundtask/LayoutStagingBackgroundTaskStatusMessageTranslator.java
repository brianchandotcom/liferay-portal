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

package com.liferay.portal.lar.backgroundtask;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatus;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;

/**
 * @author Daniel Kocsis
 */
public class LayoutStagingBackgroundTaskStatusMessageTranslator
	extends DefaultExportImportBackgroundTaskStatusMessageTranslator {

	@Override
	protected synchronized void translateLayoutMessage(
		BackgroundTaskStatus backgroundTaskStatus, Message message) {

		String phase = GetterUtil.getString(
			backgroundTaskStatus.getAttribute("phase"));

		if (Validator.isNull(phase)) {
			clearBackgroundTaskStatus(backgroundTaskStatus);

			phase = Constants.EXPORT;
		}
		else {
			phase = Constants.IMPORT;
		}

		backgroundTaskStatus.setAttribute("phase", phase);

		super.translateLayoutMessage(backgroundTaskStatus, message);

		if (phase.equals(Constants.IMPORT)) {
			backgroundTaskStatus.setAttribute(
				"modelAdditionsByPortletsCurrent",
				new HashMap<String, LongWrapper>());
			backgroundTaskStatus.setAttribute(
				"modelAdditionsByPortletsTotal",
				new HashMap<String, LongWrapper>());

			long allModelAdditionCountersCurrent = GetterUtil.getLong(
				backgroundTaskStatus.getAttribute(
					"allModelAdditionCountersCurrent"));
			long allModelAdditionCountersTotal = GetterUtil.getLong(
				backgroundTaskStatus.getAttribute(
					"allModelAdditionCountersTotal"));

			backgroundTaskStatus.setAttribute(
				"allModelAdditionCountersTotal",
				allModelAdditionCountersCurrent +
					allModelAdditionCountersTotal);

			long portletsNumberCurrent = GetterUtil.getLong(
				backgroundTaskStatus.getAttribute("portletsNumberCurrent"));
			long portletsNumberTotal = GetterUtil.getLong(
				backgroundTaskStatus.getAttribute("portletsNumberTotal"));

			backgroundTaskStatus.setAttribute(
				"portletsNumberTotal",
				portletsNumberCurrent + portletsNumberTotal);
		}
	}

}