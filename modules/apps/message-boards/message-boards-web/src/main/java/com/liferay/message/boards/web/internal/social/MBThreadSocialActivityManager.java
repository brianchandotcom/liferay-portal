/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.web.internal.social;

import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
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
			_addSubscribeSocialActivity(
				userId, thread.getGroupId(), thread, extraData);
		}
		else if (type == SocialActivityConstants.TYPE_VIEW) {
			_addViewSocialActivity(
				userId, thread, type, extraData, receiverUserId);
		}
		else {
			super.addActivity(userId, thread, type, extraData, receiverUserId);
		}
	}

	@Override
	protected SocialActivityLocalService getSocialActivityLocalService() {
		return _socialActivityLocalService;
	}

	private void _addSubscribeSocialActivity(
			long userId, long groupId, MBThread thread, String extraData)
		throws PortalException {

		JSONObject extraDataJSONObject = _jsonFactory.createJSONObject(
			extraData);

		extraDataJSONObject.put("threadId", thread.getThreadId());

		_socialActivityLocalService.addActivity(
			userId, groupId, MBMessage.class.getName(),
			thread.getRootMessageId(), SocialActivityConstants.TYPE_SUBSCRIBE,
			extraDataJSONObject.toString(), 0);
	}

	private void _addViewSocialActivity(
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

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private MBMessageLocalService _mbMessageLocalService;

	@Reference
	private SocialActivityLocalService _socialActivityLocalService;

}