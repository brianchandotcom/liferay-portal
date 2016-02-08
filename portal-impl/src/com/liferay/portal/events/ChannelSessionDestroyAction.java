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

package com.liferay.portal.events;

import com.liferay.portal.kernel.events.SessionAction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.notifications.ChannelException;
import com.liferay.portal.kernel.notifications.ChannelHubManagerUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpSession;

/**
 * @author Edward Han
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class ChannelSessionDestroyAction extends SessionAction {

	@Override
	public void run(HttpSession session) {
		User user = null;

		try {
			user = (User)session.getAttribute(WebKeys.USER);
		}
		catch (IllegalStateException ise) {
			return;
		}

		try {
			if (user == null) {
				Long userId = (Long)session.getAttribute(WebKeys.USER_ID);

				if (userId != null) {
					user = UserLocalServiceUtil.getUser(userId);
				}
			}

			if ((user == null) || user.isDefaultUser()) {
				return;
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Destroying channel " + user.getUserId());
			}

			try {
				ChannelHubManagerUtil.destroyChannel(
					user.getCompanyId(), user.getUserId());
			}
			catch (ChannelException ce) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"User channel " + user.getUserId() +
							" is already unregistered",
						ce);
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ChannelSessionDestroyAction.class);

}