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

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portlet.polls.model.PollsChoice;
import com.liferay.portlet.polls.model.PollsQuestion;
import com.liferay.portlet.polls.model.PollsVote;
import com.liferay.portlet.polls.service.PollsQuestionLocalServiceUtil;
import com.liferay.portlet.polls.service.persistence.PollsChoiceActionableDynamicQuery;
import com.liferay.portlet.polls.service.persistence.PollsQuestionActionableDynamicQuery;
import com.liferay.portlet.polls.service.persistence.PollsVoteActionableDynamicQuery;

import java.util.List;

import javax.portlet.PortletPreferences;

/**
 * @author Bruno Farache
 * @author Marcellus Tavares
 * @author Mate Thurzo
 */
public class PollsPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "polls";

	public PollsPortletDataHandler() {
		setAlwaysExportable(true);
		setDataLocalized(true);
		setExportControls(
			new PortletDataHandlerBoolean(NAMESPACE, "questions", true, true),
			new PortletDataHandlerBoolean(NAMESPACE, "votes"));
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletDataContext.addPrimaryKey(
				PollsPortletDataHandler.class, "deleteData")) {

			return portletPreferences;
		}

		PollsQuestionLocalServiceUtil.deleteQuestions(
			portletDataContext.getScopeGroupId());

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			final PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPermissions(
			"com.liferay.portlet.polls", portletDataContext.getScopeGroupId());

		Element rootElement = addExportDataRootElement(portletDataContext);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		// Questions

		final Element questionsElement = rootElement.addElement("questions");

		ActionableDynamicQuery questionActionableDynamicQuery =
			new PollsQuestionActionableDynamicQuery() {

			@Override
			protected void addCriteria(DynamicQuery dynamicQuery) {
				portletDataContext.addDateRangeCriteria(
					dynamicQuery, "modifiedDate");
			}

			@Override
			protected void performAction(Object object) throws PortalException {
				PollsQuestion pollsQuestion = (PollsQuestion)object;

				StagedModelDataHandlerUtil.exportStagedModel(
					portletDataContext, new Element[] {questionsElement},
					pollsQuestion);
			}

		};

		questionActionableDynamicQuery.setGroupId(
			portletDataContext.getGroupId());

		questionActionableDynamicQuery.performActions();

		// Choices

		final Element choicesElement = rootElement.addElement("choices");

		ActionableDynamicQuery choiceActionableDynamicQuery =
			new PollsChoiceActionableDynamicQuery() {

			@Override
			protected void addCriteria(DynamicQuery dynamicQuery) {
				portletDataContext.addDateRangeCriteria(
					dynamicQuery, "modifiedDate");
			}

			@Override
			protected void performAction(Object object) throws PortalException {
				PollsChoice pollsChoice = (PollsChoice)object;

				StagedModelDataHandlerUtil.exportStagedModel(
					portletDataContext,
					new Element[] {questionsElement, choicesElement},
					pollsChoice);
			}

		};

		choiceActionableDynamicQuery.setGroupId(
			portletDataContext.getGroupId());

		choiceActionableDynamicQuery.performActions();

		// Votes

		final Element votesElement = rootElement.addElement("votes");

		if (portletDataContext.getBooleanParameter(
				PollsPortletDataHandler.NAMESPACE, "votes")) {

			ActionableDynamicQuery voteActionableDynamicQuery =
				new PollsVoteActionableDynamicQuery() {

				@Override
				protected void addCriteria(DynamicQuery dynamicQuery) {
					portletDataContext.addDateRangeCriteria(
						dynamicQuery, "modifiedDate");
				}

				@Override
				protected void performAction(Object object)
					throws PortalException {

					PollsVote pollsVote = (PollsVote)object;

					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext,
						new Element[] {
							questionsElement, choicesElement, votesElement
						},
						pollsVote);
				}

			};

			voteActionableDynamicQuery.setGroupId(
				portletDataContext.getGroupId());

			voteActionableDynamicQuery.performActions();
		}

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPermissions(
			"com.liferay.portlet.polls", portletDataContext.getSourceGroupId(),
			portletDataContext.getScopeGroupId());

		Element rootElement = portletDataContext.getImportDataRootElement();

		Element questionsElement = rootElement.element("questions");

		List<Element> questionElements = questionsElement.elements("question");

		for (Element questionElement : questionElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, questionElement);
		}

		Element choicesElement = rootElement.element("choices");

		List<Element> choiceElements = choicesElement.elements("choice");

		for (Element choiceElement : choiceElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, choiceElement);
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "votes")) {
			Element votesElement = rootElement.element("votes");

			List<Element> voteElements = votesElement.elements("vote");

			for (Element voteElement : voteElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, voteElement);
			}
		}

		return null;
	}

}