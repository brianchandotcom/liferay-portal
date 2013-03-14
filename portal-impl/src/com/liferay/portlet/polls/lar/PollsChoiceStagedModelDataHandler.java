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
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.polls.model.PollsChoice;
import com.liferay.portlet.polls.model.PollsQuestion;
import com.liferay.portlet.polls.service.PollsChoiceLocalServiceUtil;
import com.liferay.portlet.polls.service.persistence.PollsChoiceFinderUtil;

import java.util.Map;

/**
 * @author Shinn Lok
 */
public class PollsChoiceStagedModelDataHandler
		extends BaseStagedModelDataHandler<PollsChoice> {

	@Override
	public String getClassName() {
		return PollsChoice.class.getName();
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

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, Element[] elements,
			PollsChoice choice)
		throws Exception {

		String path = getChoicePath(portletDataContext, choice);

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		Element choiceElement = questionsElement.addElement("choice");

		portletDataContext.addClassedModel(
			choiceElement, path, choice, NAMESPACE);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, Element element, String path,
			PollsChoice choice)
		throws Exception {

		long userId = portletDataContext.getUserId(choice.getUserUuid());

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
					userId, questionId, choice.getName(),
					choice.getDescription(), serviceContext);
			}
			else {
				importedChoice = PollsChoiceLocalServiceUtil.updateChoice(
					existingChoice.getChoiceId(), questionId, choice.getName(),
					choice.getDescription(), new ServiceContext());
			}
		}
		else {
			importedChoice = PollsChoiceLocalServiceUtil.addChoice(
				userId, questionId, choice.getName(), choice.getDescription(),
				new ServiceContext());
		}

		portletDataContext.importClassedModel(
			choice, importedChoice, NAMESPACE);
	}

}