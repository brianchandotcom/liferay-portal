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

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.notifications.ChannelHubManagerUtil;
import com.liferay.portal.kernel.notifications.DuplicateChannelHubException;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Edward Han
 * @author Brian Wing Shun Chan
 */
public class ChannelHubAppStartupAction extends SimpleAction {

	@Override
	public void run(String[] ids) throws ActionException {
		try {
			long companyId = GetterUtil.getLong(ids[0]);

			if (_log.isDebugEnabled()) {
				_log.debug("Creating channel hub " + companyId);
			}

			ChannelHubManagerUtil.createChannelHub(companyId);
		}
		catch (DuplicateChannelHubException dche) {
			if (_log.isWarnEnabled()) {
				_log.warn(dche.getMessage());
			}
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ChannelHubAppStartupAction.class);

}