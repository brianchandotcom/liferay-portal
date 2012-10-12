/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portlet.polls.NoSuchQuestionException;
import com.liferay.portlet.polls.model.PollsChoice;
import com.liferay.portlet.polls.model.PollsQuestion;
import com.liferay.portlet.polls.model.PollsVote;
import com.liferay.portlet.polls.service.persistence.PollsQuestionUtil;

import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Marcellus Tavares
 */
public class PollsDisplayPortletDataHandlerImpl extends BasePortletDataHandler {

	@Override
	public String[] getDataPortletPreferences() {
		return new String[] {"questionId"};
	}

	@Override
	public PortletDataHandlerControl[] getExportControls() {
		return new PortletDataHandlerControl[] {_questions, _votes};
	}

	@Override
	public PortletDataHandlerControl[] getImportControls() {
		return new PortletDataHandlerControl[] {_questions, _votes};
	}

	@Override
	public boolean isDataLocalized() {
		return _DATA_LOCALIZED;
	}

	@Override
	public boolean isPublishToLiveByDefault() {
		return _PUBLISH_TO_LIVE_BY_DEFAULT;
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
			"com.liferay.portlet.polls", portletDataContext.getScopeGroupId());

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("polls-display-data");

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		Element questionsElement = rootElement.addElement("questions");
		Element choicesElement = rootElement.addElement("choices");
		Element votesElement = rootElement.addElement("votes");

		PollsPortletDataHandlerImpl.exportQuestion(
			portletDataContext, questionsElement, choicesElement, votesElement,
			question);

		return document.formattedString();
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPermissions(
			"com.liferay.portlet.polls", portletDataContext.getSourceGroupId(),
			portletDataContext.getScopeGroupId());

		if (Validator.isNull(data)) {
			return null;
		}

		Document document = SAXReaderUtil.read(data);

		Element rootElement = document.getRootElement();

		Element questionsElement = rootElement.element("questions");

		for (Element questionElement : questionsElement.elements("question")) {
			String path = questionElement.attributeValue("path");

			if (!portletDataContext.isPathNotProcessed(path)) {
				continue;
			}

			PollsQuestion question =
				(PollsQuestion)portletDataContext.getZipEntryAsObject(path);

			PollsPortletDataHandlerImpl.importQuestion(
				portletDataContext, questionElement, question);
		}

		Element choicesElement = rootElement.element("choices");

		for (Element choiceElement : choicesElement.elements("choice")) {
			String path = choiceElement.attributeValue("path");

			if (!portletDataContext.isPathNotProcessed(path)) {
				continue;
			}

			PollsChoice choice =
				(PollsChoice)portletDataContext.getZipEntryAsObject(path);

			PollsPortletDataHandlerImpl.importChoice(
				portletDataContext, choice);
		}

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "votes")) {
			Element votesElement = rootElement.element("votes");

			for (Element voteElement : votesElement.elements("vote")) {
				String path = voteElement.attributeValue("path");

				if (!portletDataContext.isPathNotProcessed(path)) {
					continue;
				}

				PollsVote vote =
					(PollsVote)portletDataContext.getZipEntryAsObject(path);

				PollsPortletDataHandlerImpl.importVote(
					portletDataContext, vote);
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

	private static final boolean _DATA_LOCALIZED = true;

	private static final String _NAMESPACE = "polls";

	private static final boolean _PUBLISH_TO_LIVE_BY_DEFAULT = true;

	private static Log _log = LogFactoryUtil.getLog(
		PollsDisplayPortletDataHandlerImpl.class);

	private static PortletDataHandlerBoolean _questions =
		new PortletDataHandlerBoolean(_NAMESPACE, "questions", true, true);

	private static PortletDataHandlerBoolean _votes =
		new PortletDataHandlerBoolean(_NAMESPACE, "votes");

}