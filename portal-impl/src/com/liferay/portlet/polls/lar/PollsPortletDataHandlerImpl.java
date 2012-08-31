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
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.polls.DuplicateVoteException;
import com.liferay.portlet.polls.model.PollsChoice;
import com.liferay.portlet.polls.model.PollsQuestion;
import com.liferay.portlet.polls.model.PollsVote;
import com.liferay.portlet.polls.service.PollsChoiceLocalServiceUtil;
import com.liferay.portlet.polls.service.PollsQuestionLocalServiceUtil;
import com.liferay.portlet.polls.service.PollsVoteLocalServiceUtil;
import com.liferay.portlet.polls.service.persistence.PollsChoiceFinderUtil;
import com.liferay.portlet.polls.service.persistence.PollsChoiceUtil;
import com.liferay.portlet.polls.service.persistence.PollsQuestionUtil;
import com.liferay.portlet.polls.service.persistence.PollsVoteUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Bruno Farache
 * @author Marcellus Tavares
 */
public class PollsPortletDataHandlerImpl extends BasePortletDataHandler {

	@Override
	public PortletDataHandlerControl[] getExportControls() {
		return new PortletDataHandlerControl[] {_questions, _votes};
	}

	@Override
	public PortletDataHandlerControl[] getImportControls() {
		return new PortletDataHandlerControl[] {_questions, _votes};
	}

	@Override
	public boolean isAlwaysExportable() {
		return _ALWAYS_EXPORTABLE;
	}

	@Override
	public boolean isDataLocalized() {
		return _DATA_LOCALIZED;
	}

	protected static void exportChoice(
			PortletDataContext portletDataContext, Element questionsElement,
			PollsChoice choice)
		throws Exception {

		String path = getChoicePath(portletDataContext, choice);

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		Element choiceElement = questionsElement.addElement("choice");

		portletDataContext.addClassedModel(
			choiceElement, path, choice, _NAMESPACE);
	}

	protected static void exportQuestion(
			PortletDataContext portletDataContext, Element questionsElement,
			Element choicesElement, Element votesElement,
			PollsQuestion question)
		throws Exception {

		if (!portletDataContext.isWithinDateRange(question.getModifiedDate())) {
			return;
		}

		String path = getQuestionPath(portletDataContext, question);

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		Element questionElement = questionsElement.addElement("question");

		List<PollsChoice> choices = PollsChoiceUtil.findByQuestionId(
			question.getQuestionId());

		for (PollsChoice choice : choices) {
			exportChoice(portletDataContext, choicesElement, choice);
		}

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "votes")) {
			List<PollsVote> votes = PollsVoteUtil.findByQuestionId(
				question.getQuestionId());

			for (PollsVote vote : votes) {
				exportVote(portletDataContext, votesElement, vote);
			}
		}

		portletDataContext.addClassedModel(
			questionElement, path, question, _NAMESPACE);
	}

	protected static void exportVote(
			PortletDataContext portletDataContext, Element questionsElement,
			PollsVote vote)
		throws Exception {

		String path = getVotePath(portletDataContext, vote);

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		Element voteElement = questionsElement.addElement("vote");

		portletDataContext.addClassedModel(voteElement, path, vote, _NAMESPACE);
	}

	protected static String getChoicePath(
		PortletDataContext portletDataContext, PollsChoice choice) {

		StringBundler sb = new StringBundler(6);

		sb.append(portletDataContext.getPortletPath(PortletKeys.POLLS));
		sb.append("/questions/");
		sb.append(choice.getQuestionId());
		sb.append("/choices/");
		sb.append(choice.getChoiceId());
		sb.append(".xml");

		return sb.toString();
	}

	protected static String getQuestionPath(
		PortletDataContext portletDataContext, PollsQuestion question) {

		StringBundler sb = new StringBundler(4);

		sb.append(portletDataContext.getPortletPath(PortletKeys.POLLS));
		sb.append("/questions/");
		sb.append(question.getQuestionId());
		sb.append(".xml");

		return sb.toString();
	}

	protected static String getVotePath(
		PortletDataContext portletDataContext, PollsVote vote) {

		StringBundler sb = new StringBundler(6);

		sb.append(portletDataContext.getPortletPath(PortletKeys.POLLS));
		sb.append("/questions/");
		sb.append(vote.getQuestionId());
		sb.append("/votes/");
		sb.append(vote.getVoteId());
		sb.append(".xml");

		return sb.toString();
	}

	protected static void importChoice(
			PortletDataContext portletDataContext, PollsChoice choice)
		throws Exception {

		Map<Long, Long> questionIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				PollsQuestion.class);

		long questionId = MapUtil.getLong(
			questionIds, choice.getQuestionId(), choice.getQuestionId());

		PollsChoice importedChoice = null;

		if (portletDataContext.isDataStrategyMirror()) {
			PollsChoice existingChoice = PollsChoiceFinderUtil.fetchByUUID_G(
				choice.getUuid(), portletDataContext.getScopeGroupId());

			if (existingChoice == null) {
				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setUuid(choice.getUuid());

				importedChoice = PollsChoiceLocalServiceUtil.addChoice(
					questionId, choice.getName(), choice.getDescription(),
					serviceContext);
			}
			else {
				importedChoice = PollsChoiceLocalServiceUtil.updateChoice(
					existingChoice.getChoiceId(), questionId, choice.getName(),
					choice.getDescription());
			}
		}
		else {
			importedChoice = PollsChoiceLocalServiceUtil.addChoice(
				questionId, choice.getName(), choice.getDescription(),
				new ServiceContext());
		}

		portletDataContext.importClassedModel(
			choice, importedChoice, _NAMESPACE);
	}

	protected static void importQuestion(
			PortletDataContext portletDataContext, Element questionElement,
			PollsQuestion question)
		throws Exception {

		long userId = portletDataContext.getUserId(question.getUserUuid());

		Date expirationDate = question.getExpirationDate();

		int expirationMonth = 0;
		int expirationDay = 0;
		int expirationYear = 0;
		int expirationHour = 0;
		int expirationMinute = 0;
		boolean neverExpire = true;

		if (expirationDate != null) {
			Calendar expirationCal = CalendarFactoryUtil.getCalendar();

			expirationCal.setTime(expirationDate);

			expirationMonth = expirationCal.get(Calendar.MONTH);
			expirationDay = expirationCal.get(Calendar.DATE);
			expirationYear = expirationCal.get(Calendar.YEAR);
			expirationHour = expirationCal.get(Calendar.HOUR);
			expirationMinute = expirationCal.get(Calendar.MINUTE);
			neverExpire = false;

			if (expirationCal.get(Calendar.AM_PM) == Calendar.PM) {
				expirationHour += 12;
			}
		}

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			questionElement, question, _NAMESPACE);

		PollsQuestion importedQuestion = null;

		if (portletDataContext.isDataStrategyMirror()) {
			PollsQuestion existingQuestion = PollsQuestionUtil.fetchByUUID_G(
				question.getUuid(), portletDataContext.getScopeGroupId());

			if (existingQuestion == null) {
				serviceContext.setUuid(question.getUuid());

				importedQuestion = PollsQuestionLocalServiceUtil.addQuestion(
					userId, question.getTitleMap(),
					question.getDescriptionMap(), expirationMonth,
					expirationDay, expirationYear, expirationHour,
					expirationMinute, neverExpire, null, serviceContext);
			}
			else {
				importedQuestion = PollsQuestionLocalServiceUtil.updateQuestion(
					userId, existingQuestion.getQuestionId(),
					question.getTitleMap(), question.getDescriptionMap(),
					expirationMonth, expirationDay, expirationYear,
					expirationHour, expirationMinute, neverExpire, null,
					serviceContext);
			}
		}
		else {
			importedQuestion = PollsQuestionLocalServiceUtil.addQuestion(
				userId, question.getTitleMap(), question.getDescriptionMap(),
				expirationMonth, expirationDay, expirationYear, expirationHour,
				expirationMinute, neverExpire, null, serviceContext);
		}

		portletDataContext.importClassedModel(
			question, importedQuestion, _NAMESPACE);
	}

	protected static void importVote(
			PortletDataContext portletDataContext, PollsVote vote)
		throws Exception {

		Map<Long, Long> questionIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				PollsQuestion.class);

		long questionId = MapUtil.getLong(
			questionIds, vote.getQuestionId(), vote.getQuestionId());

		Map<Long, Long> choiceIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				PollsChoice.class);

		long choiceId = MapUtil.getLong(
			choiceIds, vote.getChoiceId(), vote.getChoiceId());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCreateDate(vote.getVoteDate());

		try {
			PollsVoteLocalServiceUtil.addVote(
				vote.getUserId(), questionId, choiceId, serviceContext);
		}
		catch (DuplicateVoteException dve) {
		}
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (!portletDataContext.addPrimaryKey(
				PollsPortletDataHandlerImpl.class, "deleteData")) {

			PollsQuestionLocalServiceUtil.deleteQuestions(
				portletDataContext.getScopeGroupId());
		}

		return null;
	}

	@Override
	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPermissions(
			"com.liferay.portlet.polls", portletDataContext.getScopeGroupId());

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("polls-data");

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		Element questionsElement = rootElement.addElement("questions");
		Element choicesElement = rootElement.addElement("choices");
		Element votesElement = rootElement.addElement("votes");

		List<PollsQuestion> questions = PollsQuestionUtil.findByGroupId(
			portletDataContext.getScopeGroupId());

		for (PollsQuestion question : questions) {
			exportQuestion(
				portletDataContext, questionsElement, choicesElement,
				votesElement, question);
		}

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

			importQuestion(portletDataContext, questionElement, question);
		}

		Element choicesElement = rootElement.element("choices");

		for (Element choiceElement : choicesElement.elements("choice")) {
			String path = choiceElement.attributeValue("path");

			if (!portletDataContext.isPathNotProcessed(path)) {
				continue;
			}

			PollsChoice choice =
				(PollsChoice)portletDataContext.getZipEntryAsObject(path);

			importChoice(portletDataContext, choice);
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

				importVote(portletDataContext, vote);
			}
		}

		return null;
	}

	private static final boolean _ALWAYS_EXPORTABLE = true;

	private static final boolean _DATA_LOCALIZED = true;

	private static final String _NAMESPACE = "polls";

	private static PortletDataHandlerBoolean _questions =
		new PortletDataHandlerBoolean(_NAMESPACE, "questions", true, true);

	private static PortletDataHandlerBoolean _votes =
		new PortletDataHandlerBoolean(_NAMESPACE, "votes");

}