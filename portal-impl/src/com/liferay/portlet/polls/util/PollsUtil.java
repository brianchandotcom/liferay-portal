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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.CookieKeys;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.polls.NoSuchVoteException;
import com.liferay.portlet.polls.model.PollsChoice;
import com.liferay.portlet.polls.model.PollsQuestion;
import com.liferay.portlet.polls.service.PollsChoiceLocalServiceUtil;
import com.liferay.portlet.polls.service.PollsVoteLocalServiceUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
			String votedCookie = CookieKeys.getCookie(
				request, PollsQuestion.class.getName() + "." + questionId);

			Boolean hasVoted = GetterUtil.getBoolean(votedCookie);

			if (hasVoted) {
				return true;
			}
			else {
				return false;
			}
		}
	}

	public static void saveVote(
		ActionRequest actionRequest, ActionResponse actionResponse,
		long questionId) {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		HttpServletResponse response =PortalUtil.getHttpServletResponse(
			actionResponse);

		saveVote(request, response, questionId);
	}

	public static void saveVote(
		HttpServletRequest request, HttpServletResponse response,
		long questionId) {

		Cookie votedCookie = new Cookie(
			PollsQuestion.class.getName() + "." + questionId, StringPool.TRUE);

		votedCookie.setMaxAge(_maxCookieAge);
		votedCookie.setPath(StringPool.SLASH);

		CookieKeys.addCookie(request, response, votedCookie);
	}

	public static void saveVote(
		RenderRequest renderRequest, RenderResponse renderResponse,
		long questionId) {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			renderRequest);

		HttpServletResponse response = PortalUtil.getHttpServletResponse(
			renderResponse);

		saveVote(request, response, questionId);
	}

	private static final int _maxCookieAge = 604800;
}