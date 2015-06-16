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

package com.liferay.portal.lar;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusMessage;
import com.liferay.portal.kernel.lar.ManifestSummary;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.StagedModelDataHandler;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.lar.backgroundtask.StagingBackgroundTaskConstants;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.service.PortletLocalServiceUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Andrew Betts
 */
public class PortletDataHandlerBackgroundTaskStatusMessage
	extends BackgroundTaskStatusMessage {

	public PortletDataHandlerBackgroundTaskStatusMessage(
		String messageType, String portletId, ManifestSummary manifestSummary) {

		init(messageType, manifestSummary);

		put("portletId", portletId);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(portletId);

		if (portlet != null) {
			PortletDataHandler portletDataHandler =
				portlet.getPortletDataHandlerInstance();

			long portletModelAdditionCountersTotal =
				portletDataHandler.getExportModelCount(manifestSummary);

			if (portletModelAdditionCountersTotal < 0) {
				portletModelAdditionCountersTotal = 0;
			}

			put(
				StagingBackgroundTaskConstants.
					PORTLET_MODEL_ADDITION_COUNTERS_TOTAL,
				portletModelAdditionCountersTotal);
		}
	}

	public PortletDataHandlerBackgroundTaskStatusMessage(
		String messageType, String[] portletIds,
		ManifestSummary manifestSummary) {

		init(messageType, manifestSummary);

		put("portletIds", portletIds);
	}

	public <T extends StagedModel> PortletDataHandlerBackgroundTaskStatusMessage(
		String messageType, T stagedModel, ManifestSummary manifestSummary) {

		init(messageType, manifestSummary);

		StagedModelDataHandler<T> stagedModelDataHandler =
			(StagedModelDataHandler<T>)
				StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(
					stagedModel.getModelClassName());

		put(
			StagingBackgroundTaskConstants.STAGED_MODEL_NAME,
			stagedModelDataHandler.getDisplayName(stagedModel));

		put(
			StagingBackgroundTaskConstants.STAGED_MODEL_TYPE,
			String.valueOf(stagedModel.getStagedModelType()));

		put("uuid", stagedModel.getUuid());
	}

	protected void init(String messageType, ManifestSummary manifestSummary) {
		put("messageType", messageType);

		Map<String, LongWrapper> modelAdditionCounters =
			manifestSummary.getModelAdditionCounters();

		put(
			StagingBackgroundTaskConstants.MODEL_ADDITION_COUNTERS,
			new HashMap<>(modelAdditionCounters));

		Map<String, LongWrapper> modelDeletionCounters =
			manifestSummary.getModelDeletionCounters();

		put(
			StagingBackgroundTaskConstants.MODEL_DELETION_COUNTERS,
			new HashMap<>(modelDeletionCounters));
	}

}