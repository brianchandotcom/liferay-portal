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

package com.liferay.polls.lar;

import com.liferay.polls.configuration.PollsConfigurationValues;
import com.liferay.polls.exception.NoSuchQuestionException;
import com.liferay.polls.model.PollsChoice;
import com.liferay.polls.model.PollsQuestion;
import com.liferay.polls.model.PollsVote;
import com.liferay.polls.service.permission.PollsPermission;
import com.liferay.polls.service.persistence.PollsQuestionUtil;
import com.liferay.portal.kernel.lar.DataLevel;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Marcellus Tavares
 */
public class PollsDisplayPortletDataHandler extends PollsPortletDataHandler {

	public PollsDisplayPortletDataHandler() {
		setDataLevel(DataLevel.PORTLET_INSTANCE);
		setDataPortletPreferences("questionId");
		setExportControls(new PortletDataHandlerControl[0]);
		setPublishToLiveByDefault(
			PollsConfigurationValues.PUBLISH_TO_LIVE_BY_DEFAULT);
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletPreferences == null) {
			return portletPreferences;
		}

		portletPreferences.setValue("questionId", StringPool.BLANK);

		return portletPreferences;
	}

	@Override
	protected PortletPreferences doProcessExportPortletPreferences(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		long questionId = GetterUtil.getLong(
			portletPreferences.getValue("questionId", StringPool.BLANK));

		if (questionId <= 0) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No question id found in preferences of portlet " +
						portletId);
			}

			return portletPreferences;
		}

		PollsQuestion question = null;

		try {
			question = PollsQuestionUtil.findByPrimaryKey(questionId);
		}
		catch (NoSuchQuestionException nsqe) {
			if (_log.isWarnEnabled()) {
				_log.warn(nsqe, nsqe);
			}

			return portletPreferences;
		}

		portletDataContext.addPortletPermissions(PollsPermission.RESOURCE_NAME);

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, portletId, question);

		for (PollsChoice choice : question.getChoices()) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, portletId, choice);
		}

		if (portletDataContext.getBooleanParameter(
				PollsPortletDataHandler.NAMESPACE, "votes")) {

			for (PollsVote vote : question.getVotes()) {
				StagedModelDataHandlerUtil.exportReferenceStagedModel(
					portletDataContext, portletId, vote);
			}
		}

		return portletPreferences;
	}

	@Override
	protected PortletPreferences doProcessImportPortletPreferences(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.importPortletPermissions(
			PollsPermission.RESOURCE_NAME);

		StagedModelDataHandlerUtil.importReferenceStagedModels(
			portletDataContext, PollsQuestion.class);

		StagedModelDataHandlerUtil.importReferenceStagedModels(
			portletDataContext, PollsChoice.class);

		StagedModelDataHandlerUtil.importReferenceStagedModels(
			portletDataContext, PollsVote.class);

		long questionId = GetterUtil.getLong(
			portletPreferences.getValue("questionId", StringPool.BLANK));

		if (questionId > 0) {
			Map<Long, Long> questionIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					PollsQuestion.class);

			questionId = MapUtil.getLong(questionIds, questionId, questionId);

			portletPreferences.setValue(
				"questionId", String.valueOf(questionId));
		}

		return portletPreferences;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PollsDisplayPortletDataHandler.class);

}