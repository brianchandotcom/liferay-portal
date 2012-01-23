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

package com.liferay.portlet.polls.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.polls.NoSuchVoteException;
import com.liferay.portlet.polls.model.PollsChoice;
import com.liferay.portlet.polls.model.PollsQuestion;
import com.liferay.portlet.polls.service.PollsChoiceLocalServiceUtil;
import com.liferay.portlet.polls.service.PollsVoteLocalServiceUtil;

import javax.portlet.ActionRequest;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * @author Brian Wing Shun Chan
 * @author Shepherd Ching
 */
public class PollsUtil {

	public static CategoryDataset getVotesDataset(long questionId)
		throws SystemException {

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		String seriesName = StringPool.BLANK;

		for (PollsChoice choice :
				PollsChoiceLocalServiceUtil.getChoices(questionId)) {

			Integer number = choice.getVotesCount();

			dataset.addValue(number, seriesName, choice.getName());
		}

		return dataset;
	}

	public static boolean hasVoted(HttpServletRequest request, long questionId)
		throws PortalException, SystemException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (themeDisplay.isSignedIn()) {
			try {
				PollsVoteLocalServiceUtil.getVote(
					questionId, themeDisplay.getUserId());
			}
			catch (NoSuchVoteException nsve) {
				return false;
			}

			return true;
		}
		else {
			HttpSession session = request.getSession();

			Boolean hasVoted = (Boolean)session.getAttribute(
				PollsQuestion.class.getName() + "." + questionId);

			if ((hasVoted != null) && (hasVoted.booleanValue())) {
				return true;
			}
			else {
				return false;
			}
		}
	}

	public static void saveVote(ActionRequest actionRequest, long questionId) {
		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		saveVote(request, questionId);
	}

	public static void saveVote(HttpServletRequest request, long questionId) {
		HttpSession session = request.getSession();

		session.setAttribute(
			PollsQuestion.class.getName() + "." + questionId, Boolean.TRUE);
	}

	public static void saveVote(RenderRequest renderRequest, long questionId) {
		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			renderRequest);

		saveVote(request, questionId);
	}

}