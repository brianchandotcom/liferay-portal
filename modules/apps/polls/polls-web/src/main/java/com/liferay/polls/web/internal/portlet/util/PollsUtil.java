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

package com.liferay.polls.web.internal.portlet.util;

import com.liferay.petra.string.StringPool;
import com.liferay.polls.model.PollsChoice;
import com.liferay.polls.model.PollsQuestion;
import com.liferay.polls.model.PollsVote;
import com.liferay.polls.service.PollsChoiceLocalServiceUtil;
import com.liferay.polls.service.PollsQuestionServiceUtil;
import com.liferay.polls.service.PollsVoteLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * @author Brian Wing Shun Chan
 * @author Shepherd Ching
 * @author Peter Fellwock
 */
public class PollsUtil {

	public static PollsQuestion getQuestionByPortlet(
			PortletPreferences portletPreferences)
		throws Exception {

		long questionId = GetterUtil.getLong(
			portletPreferences.getValue("questionId", StringPool.BLANK));

		if (questionId > 0) {
			return PollsQuestionServiceUtil.getQuestion(questionId);
		}

		return null;
	}

	public static CategoryDataset getVotesDataset(long questionId) {
		DefaultCategoryDataset defaultCategoryDataset =
			new DefaultCategoryDataset();

		String seriesName = StringPool.BLANK;

		for (PollsChoice choice :
				PollsChoiceLocalServiceUtil.getChoices(questionId)) {

			Integer number = choice.getVotesCount();

			defaultCategoryDataset.addValue(
				number, seriesName, choice.getName());
		}

		return defaultCategoryDataset;
	}

	public static boolean hasVoted(
			HttpServletRequest httpServletRequest, long questionId)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (themeDisplay.isSignedIn()) {
			PollsVote vote = PollsVoteLocalServiceUtil.fetchQuestionUserVote(
				questionId, themeDisplay.getUserId());

			if (vote == null) {
				return false;
			}

			return true;
		}

		String cookie = CookieKeys.getCookie(
			httpServletRequest, _getCookieName(questionId));

		return GetterUtil.getBoolean(cookie);
	}

	public static void saveVote(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, long questionId) {

		Cookie cookie = new Cookie(_getCookieName(questionId), StringPool.TRUE);

		cookie.setMaxAge((int)(Time.WEEK / 1000));
		cookie.setPath(StringPool.SLASH);
		cookie.setHttpOnly(true);

		CookieKeys.addCookie(httpServletRequest, httpServletResponse, cookie);
	}

	public static void saveVote(
		PortletRequest portletRequest, PortletResponse portletResponse,
		long questionId) {

		saveVote(
			PortalUtil.getHttpServletRequest(portletRequest),
			PortalUtil.getHttpServletResponse(portletResponse), questionId);
	}

	private static String _getCookieName(long questionId) {
		return PollsQuestion.class.getName() + StringPool.POUND + questionId;
	}

}