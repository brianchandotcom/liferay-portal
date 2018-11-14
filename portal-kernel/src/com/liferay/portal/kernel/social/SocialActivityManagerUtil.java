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

package com.liferay.portal.kernel.social;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.GroupedModel;

import java.util.Date;

/**
 * @author Adolfo Pérez
 */
public class SocialActivityManagerUtil {

	public static <T extends ClassedModel & GroupedModel> void addActivity(
			long userId, T classedModel, int type, String extraData,
			long receiverUserId)
		throws PortalException {

		getSocialActivityManager().addActivity(
			userId, classedModel, type, extraData, receiverUserId);
	}

	public static <T extends ClassedModel & GroupedModel> void
			addUniqueActivity(
				long userId, Date createDate, T classedModel, int type,
				String extraData, long receiverUserId)
		throws PortalException {

		getSocialActivityManager().addUniqueActivity(
			userId, createDate, classedModel, type, extraData, receiverUserId);
	}

	public static <T extends ClassedModel & GroupedModel> void
			addUniqueActivity(
				long userId, T classedModel, int type, String extraData,
				long receiverUserId)
		throws PortalException {

		getSocialActivityManager().addUniqueActivity(
			userId, classedModel, type, extraData, receiverUserId);
	}

	public static <T extends ClassedModel & GroupedModel> void deleteActivities(
			T classedModel)
		throws PortalException {

		getSocialActivityManager().deleteActivities(classedModel);
	}

	public static <T extends ClassedModel & GroupedModel>
		SocialActivityManager<T> getSocialActivityManager() {

		return (SocialActivityManager<T>)_socialActivityManager;
	}

	public static <T extends ClassedModel & GroupedModel> void
			updateLastSocialActivity(
				long userId, T classedModel, int type, Date createDate)
		throws PortalException {

		getSocialActivityManager().updateLastSocialActivity(
			userId, classedModel, type, createDate);
	}

	public <T extends ClassedModel & GroupedModel> void
		setSocialActivityManager(
			SocialActivityManager<T> socialActivityManager) {

		_socialActivityManager = socialActivityManager;
	}

	private static SocialActivityManager<?> _socialActivityManager;

}