/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.social;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

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

	private static volatile SocialActivityManager<?> _socialActivityManager =
		ServiceProxyFactory.newServiceTrackedInstance(
			SocialActivityManager.class, SocialActivityManagerUtil.class,
			"_socialActivityManager", "(!(model.class.name=*))", false, false);

}