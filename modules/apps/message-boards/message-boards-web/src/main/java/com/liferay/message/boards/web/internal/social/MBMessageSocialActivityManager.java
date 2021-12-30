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

import com.liferay.message.boards.model.MBDiscussion;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBDiscussionLocalService;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.social.BaseSocialActivityManager;
import com.liferay.portal.kernel.social.SocialActivityManager;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.social.kernel.model.SocialActivity;
import com.liferay.social.kernel.service.SocialActivityLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = "model.class.name=com.liferay.message.boards.model.MBMessage",
	service = SocialActivityManager.class
)
public class MBMessageSocialActivityManager
	extends BaseSocialActivityManager<MBMessage> {

	@Override
	public void deleteActivities(MBMessage message) throws PortalException {
		_deleteDiscussionSocialActivities(message.getClassName(), message);
	}

	@Override
	protected SocialActivityLocalService getSocialActivityLocalService() {
		return _socialActivityLocalService;
	}

	private void _deleteDiscussionSocialActivities(
			String className, MBMessage message)
		throws PortalException {

		MBDiscussion discussion = _mbDiscussionLocalService.getThreadDiscussion(
			message.getThreadId());

		long classNameId = _classNameLocalService.getClassNameId(className);

		if (discussion.getClassNameId() != classNameId) {
			return;
		}

		List<SocialActivity> socialActivities =
			_socialActivityLocalService.getActivities(
				0, className, discussion.getClassPK(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		for (SocialActivity socialActivity : socialActivities) {
			if (Validator.isNull(socialActivity.getExtraData())) {
				continue;
			}

			JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject(
				socialActivity.getExtraData());

			long extraDataMessageId = extraDataJSONObject.getLong("messageId");

			if (message.getMessageId() == extraDataMessageId) {
				_socialActivityLocalService.deleteActivity(
					socialActivity.getActivityId());
			}
		}
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private MBDiscussionLocalService _mbDiscussionLocalService;

	@Reference
	private MBMessageLocalService _mbMessageLocalService;

	@Reference
	private SocialActivityLocalService _socialActivityLocalService;

}