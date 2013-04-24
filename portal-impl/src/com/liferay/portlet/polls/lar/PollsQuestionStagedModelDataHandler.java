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

import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.polls.model.PollsQuestion;
import com.liferay.portlet.polls.service.PollsQuestionLocalServiceUtil;
import com.liferay.portlet.polls.service.persistence.PollsQuestionUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Shinn Lok
 */
public class PollsQuestionStagedModelDataHandler
	extends BaseStagedModelDataHandler<PollsQuestion> {

	public static final String[] CLASS_NAMES = {PollsQuestion.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, PollsQuestion question)
		throws Exception {

		Element questionElement = portletDataContext.getExportDataElement(
			question);

		portletDataContext.addClassedModel(
			questionElement, ExportImportPathUtil.getModelPath(question),
			question, PollsPortletDataHandler.NAMESPACE);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, PollsQuestion question)
		throws Exception {

		long userId = portletDataContext.getUserId(question.getUserUuid());

		int expirationMonth = 0;
		int expirationDay = 0;
		int expirationYear = 0;
		int expirationHour = 0;
		int expirationMinute = 0;
		boolean neverExpire = true;

		Date expirationDate = question.getExpirationDate();

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
			question, PollsPortletDataHandler.NAMESPACE);

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
			question, importedQuestion, PollsPortletDataHandler.NAMESPACE);
	}

}