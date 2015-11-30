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

package com.liferay.message.boards.web.social;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.social.BaseSocialActivityManager;
import com.liferay.portal.kernel.social.SocialActivityManager;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ClassNameLocalService;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBDiscussionLocalService;
import com.liferay.portlet.messageboards.service.MBMessageLocalService;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.service.SocialActivityLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = "model.class.name=com.liferay.portlet.messageboards.model.MBMessage",
	service = SocialActivityManager.class
)
public class MBMessageSocialActivityManager
	extends BaseSocialActivityManager<MBMessage> {

	@Override
	public void deleteActivities(MBMessage message) throws PortalException {
		deleteDiscussionSocialActivities(BlogsEntry.class.getName(), message);
	}

	protected void deleteDiscussionSocialActivities(
			String className, MBMessage message)
		throws PortalException {

		MBDiscussion discussion = _mbDiscussionLocalService.getThreadDiscussion(
			message.getThreadId());

		long classNameId = _classNameLocalService.getClassNameId(className);
		long classPK = discussion.getClassPK();

		if (discussion.getClassNameId() != classNameId) {
			return;
		}

		List<SocialActivity> socialActivities =
			_socialActivityLocalService.getActivities(
				0, className, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

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

	@Override
	protected SocialActivityLocalService getSocialActivityLocalService() {
		return _socialActivityLocalService;
	}

	@Reference(unbind = "-")
	protected void setClassNameLocalService(
		ClassNameLocalService classNameLocalService) {

		_classNameLocalService = classNameLocalService;
	}

	@Reference(unbind = "-")
	protected void setMBDiscussionLocalService(
		MBDiscussionLocalService mbDiscussionLocalService) {

		_mbDiscussionLocalService = mbDiscussionLocalService;
	}

	@Reference(unbind = "-")
	protected void setMBMessageLocalService(
		MBMessageLocalService mbMessageLocalService) {

		_mbMessageLocalService = mbMessageLocalService;
	}

	@Reference(unbind = "-")
	protected void setSocialActivityLocalService(
		SocialActivityLocalService socialActivityLocalService) {

		_socialActivityLocalService = socialActivityLocalService;
	}

	private volatile ClassNameLocalService _classNameLocalService;
	private volatile MBDiscussionLocalService _mbDiscussionLocalService;
	private volatile MBMessageLocalService _mbMessageLocalService;
	private volatile SocialActivityLocalService _socialActivityLocalService;

}