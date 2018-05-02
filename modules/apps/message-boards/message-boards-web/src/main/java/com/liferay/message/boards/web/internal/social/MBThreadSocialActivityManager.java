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

package com.liferay.message.boards.web.internal.social;

import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.social.BaseSocialActivityManager;
import com.liferay.portal.kernel.social.SocialActivityManager;
import com.liferay.social.kernel.model.SocialActivityConstants;
import com.liferay.social.kernel.service.SocialActivityLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = "model.class.name=com.liferay.message.boards.model.MBThread",
	service = SocialActivityManager.class
)
public class MBThreadSocialActivityManager
	extends BaseSocialActivityManager<MBThread> {

	@Override
	public void addActivity(
			long userId, MBThread thread, int type, String extraData,
			long receiverUserId)
		throws PortalException {

		if (type == SocialActivityConstants.TYPE_SUBSCRIBE) {
			addSubscribeSocialActivity(
				userId, thread.getGroupId(), thread, extraData);
		}
		else if (type == SocialActivityConstants.TYPE_VIEW) {
			addViewSocialActivity(
				userId, thread, type, extraData, receiverUserId);
		}
		else {
			super.addActivity(userId, thread, type, extraData, receiverUserId);
		}
	}

	protected void addSubscribeSocialActivity(
			long userId, long groupId, MBThread thread, String extraData)
		throws PortalException {

		JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject(
			extraData);

		extraDataJSONObject.put("threadId", thread.getThreadId());

		_socialActivityLocalService.addActivity(
			userId, groupId, MBMessage.class.getName(),
			thread.getRootMessageId(), SocialActivityConstants.TYPE_SUBSCRIBE,
			extraDataJSONObject.toString(), 0);
	}

	protected void addViewSocialActivity(
			long userId, MBThread thread, int type, String extraData,
			long receiverUserId)
		throws PortalException {

		if (thread.getRootMessageUserId() == userId) {
			return;
		}

		MBMessage rootMessage = _mbMessageLocalService.getMessage(
			thread.getRootMessageId());

		_socialActivityLocalService.addActivity(
			userId, rootMessage.getGroupId(), MBMessage.class.getName(),
			rootMessage.getMessageId(), type, extraData, receiverUserId);
	}

	@Override
	protected SocialActivityLocalService getSocialActivityLocalService() {
		return _socialActivityLocalService;
	}

	@Reference
	private MBMessageLocalService _mbMessageLocalService;

	@Reference
	private SocialActivityLocalService _socialActivityLocalService;

}