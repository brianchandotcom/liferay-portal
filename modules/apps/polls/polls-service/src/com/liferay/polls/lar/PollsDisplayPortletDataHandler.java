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

import com.liferay.polls.configuration.PollsServiceConfigurationValues;
import com.liferay.polls.constants.PollsPortletKeys;
import com.liferay.polls.exception.NoSuchQuestionException;
import com.liferay.polls.model.PollsChoice;
import com.liferay.polls.model.PollsQuestion;
import com.liferay.polls.model.PollsVote;
import com.liferay.polls.service.permission.PollsResourcePermissionChecker;
import com.liferay.polls.service.persistence.PollsQuestionUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.exportimport.lar.DataLevel;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.PortletDataHandler;
import com.liferay.portlet.exportimport.lar.PortletDataHandlerControl;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;

import java.util.Map;

import javax.portlet.PortletPreferences;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + PollsPortletKeys.POLLS_DISPLAY},
	service = PortletDataHandler.class
)
public class PollsDisplayPortletDataHandler extends PollsPortletDataHandler {

	@Activate
	protected void activate() {
		setDataLevel(DataLevel.PORTLET_INSTANCE);
		setDataPortletPreferences("questionId");
		setExportControls(new PortletDataHandlerControl[0]);
		setPublishToLiveByDefault(
			PollsServiceConfigurationValues.PUBLISH_TO_LIVE_BY_DEFAULT);
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

		portletDataContext.addPortletPermissions(
			PollsResourcePermissionChecker.RESOURCE_NAME);

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
			PollsResourcePermissionChecker.RESOURCE_NAME);

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

	@Reference(target = "(original.bean=*)", unbind = "-")
	protected void setServletContext(ServletContext servletContext) {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PollsDisplayPortletDataHandler.class);

}