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

package com.liferay.portlet.social.handler;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.model.ClassedModel;

import java.util.Date;

/**
 * @author Adolfo Pérez
 */
public class SocialActivityHandlerUtil {

	public static <T extends ClassedModel> void addActivity(
			long userId, long groupId, T classedModel, int type,
			String extraData, long receiverUserId)
		throws PortalException {

		getSocialActivityHandler().addActivity(
			userId, groupId, classedModel, type, extraData, receiverUserId);
	}

	public static <T extends ClassedModel> void addUniqueActivity(
			long userId, long groupId, Date createDate, T classedModel,
			int type, String extraData, long receiverUserId)
		throws PortalException {

		getSocialActivityHandler().addUniqueActivity(
			userId, groupId, createDate, classedModel, type, extraData,
			receiverUserId);
	}

	public static <T extends ClassedModel> void addUniqueActivity(
			long userId, long groupId, T classedModel, int type,
			String extraData, long receiverUserId)
		throws PortalException {

		getSocialActivityHandler().addUniqueActivity(
			userId, groupId, classedModel, type, extraData, receiverUserId);
	}

	public static <T extends ClassedModel> void deleteActivities(T classedModel)
		throws PortalException {

		getSocialActivityHandler().deleteActivities(classedModel);
	}

	public static SocialActivityHandler<ClassedModel>
		getSocialActivityHandler() {

		PortalRuntimePermission.checkGetBeanProperty(
			SocialActivityHandlerUtil.class);

		return _socialActivityHandler;
	}

	public static <T extends ClassedModel> void updateLastSocialActivity(
			long userId, long groupId, T classedModel, int type,
			Date createDate)
		throws PortalException {

		getSocialActivityHandler().updateLastSocialActivity(
			userId, groupId, classedModel, type, createDate);
	}

	public void setSocialActivityHandler(
		SocialActivityHandler<ClassedModel> socialActivityHandler) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_socialActivityHandler = socialActivityHandler;
	}

	private static SocialActivityHandler<ClassedModel> _socialActivityHandler;

}