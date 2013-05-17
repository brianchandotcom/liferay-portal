/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.polls.lar;

import com.liferay.portal.kernel.lar.DataLevel;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portlet.polls.NoSuchQuestionException;
import com.liferay.portlet.polls.model.PollsChoice;
import com.liferay.portlet.polls.model.PollsQuestion;
import com.liferay.portlet.polls.model.PollsVote;
import com.liferay.portlet.polls.service.permission.PollsPermission;
import com.liferay.portlet.polls.service.persistence.PollsQuestionUtil;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Marcellus Tavares
 */
public class PollsDisplayPortletDataHandler extends PollsPortletDataHandler {

	public PollsDisplayPortletDataHandler() {
		setDataLevel(DataLevel.PORTLET_INSTANCE);
		setDataPortletPreferences("questionId");
		setPublishToLiveByDefault(true);
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
	protected String doExportData(
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

			return StringPool.BLANK;
		}

		PollsQuestion question = null;

		try {
			question = PollsQuestionUtil.findByPrimaryKey(questionId);
		}
		catch (NoSuchQuestionException nsqe) {
			if (_log.isWarnEnabled()) {
				_log.warn(nsqe, nsqe);
			}

			return StringPool.BLANK;
		}

		portletDataContext.addPermissions(
			PollsPermission.RESOURCE_NAME,
			portletDataContext.getScopeGroupId());

		Element rootElement = addExportDataRootElement(portletDataContext);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, question);

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPermissions(
			PollsPermission.RESOURCE_NAME,
			portletDataContext.getSourceGroupId(),
			portletDataContext.getScopeGroupId());

		Element questionsElement = portletDataContext.getImportDataGroupElement(
			PollsQuestion.class);

		List<Element> questionElements = questionsElement.elements();

		for (Element questionElement : questionElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, questionElement);
		}

		Element choicesElement = portletDataContext.getImportDataGroupElement(
			PollsChoice.class);

		List<Element> choiceElements = choicesElement.elements();

		for (Element choiceElement : choiceElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, choiceElement);
		}

		if (portletDataContext.getBooleanParameter(
				PollsPortletDataHandler.NAMESPACE, "votes")) {

			Element votesElement = portletDataContext.getImportDataGroupElement(
				PollsVote.class);

			List<Element> voteElements = votesElement.elements();

			for (Element voteElement : voteElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, voteElement);
			}
		}

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

	private static Log _log = LogFactoryUtil.getLog(
		PollsDisplayPortletDataHandler.class);

}