/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.social.kernel.model;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.social.kernel.service.persistence.SocialRequestUtil;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Amos Fong
 */
public abstract class BaseSocialRequestInterpreter
	implements SocialRequestInterpreter {

	public String getUserName(long userId, ThemeDisplay themeDisplay) {
		try {
			if (userId <= 0) {
				return StringPool.BLANK;
			}

			User user = UserLocalServiceUtil.getUserById(userId);

			if (user.getUserId() == themeDisplay.getUserId()) {
				return HtmlUtil.escape(user.getFirstName());
			}

			String userName = user.getFullName();

			Group group = user.getGroup();

			if (group.getGroupId() == themeDisplay.getScopeGroupId()) {
				return HtmlUtil.escape(userName);
			}

			String userDisplayURL = user.getDisplayURL(themeDisplay);

			return StringBundler.concat(
				"<a href=\"", userDisplayURL, "\">", HtmlUtil.escape(userName),
				"</a>");
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return StringPool.BLANK;
		}
	}

	public String getUserNameLink(long userId, ThemeDisplay themeDisplay) {
		try {
			if (userId <= 0) {
				return StringPool.BLANK;
			}

			User user = UserLocalServiceUtil.getUserById(userId);

			String userName = user.getFullName();

			String userDisplayURL = user.getDisplayURL(themeDisplay);

			return StringBundler.concat(
				"<a href=\"", userDisplayURL, "\">", HtmlUtil.escape(userName),
				"</a>");
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return StringPool.BLANK;
		}
	}

	@Override
	public SocialRequestFeedEntry interpret(
		SocialRequest request, ThemeDisplay themeDisplay) {

		try {
			return doInterpret(request, themeDisplay);
		}
		catch (Exception exception) {
			_log.error("Unable to interpret request", exception);
		}

		return null;
	}

	@Override
	public boolean processConfirmation(
		SocialRequest request, ThemeDisplay themeDisplay) {

		try {
			return doProcessConfirmation(request, themeDisplay);
		}
		catch (Exception exception) {
			_log.error("Unable to process confirmation", exception);
		}

		return false;
	}

	public void processDuplicateRequestsFromUser(
		SocialRequest request, int oldStatus) {

		List<SocialRequest> requests = SocialRequestUtil.findByU_C_C_T_S(
			request.getUserId(), request.getClassNameId(), request.getClassPK(),
			request.getType(), oldStatus);

		int newStatus = request.getStatus();

		for (SocialRequest curRequest : requests) {
			curRequest.setStatus(newStatus);

			SocialRequestUtil.update(curRequest);
		}
	}

	public void processDuplicateRequestsToUser(
		SocialRequest request, int oldStatus) {

		List<SocialRequest> requests = SocialRequestUtil.findByC_C_T_R_S(
			request.getClassNameId(), request.getClassPK(), request.getType(),
			request.getReceiverUserId(), oldStatus);

		int newStatus = request.getStatus();

		for (SocialRequest curRequest : requests) {
			curRequest.setStatus(newStatus);

			SocialRequestUtil.update(curRequest);
		}
	}

	@Override
	public boolean processRejection(
		SocialRequest request, ThemeDisplay themeDisplay) {

		try {
			return doProcessRejection(request, themeDisplay);
		}
		catch (Exception exception) {
			_log.error("Unable to process rejection", exception);
		}

		return false;
	}

	protected abstract SocialRequestFeedEntry doInterpret(
			SocialRequest request, ThemeDisplay themeDisplay)
		throws Exception;

	protected abstract boolean doProcessConfirmation(
			SocialRequest request, ThemeDisplay themeDisplay)
		throws Exception;

	protected boolean doProcessRejection(
			SocialRequest request, ThemeDisplay themeDisplay)
		throws Exception {

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseSocialRequestInterpreter.class);

}