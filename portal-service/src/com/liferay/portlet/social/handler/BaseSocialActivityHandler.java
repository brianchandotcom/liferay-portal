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
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.GroupedModel;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityConstants;
import com.liferay.portlet.social.service.SocialActivityLocalService;

import java.util.Date;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseSocialActivityHandler
		<T extends ClassedModel & GroupedModel>
	implements SocialActivityHandler<T> {

	@Override
	public void addActivity(
			long userId, T classedModel, int type, String extraData,
			long receiverUserId)
		throws PortalException {

		String className = getClassName(classedModel);
		long primaryKey = getPrimaryKey(classedModel);

		if (type == SocialActivityConstants.TYPE_SUBSCRIBE) {
			if (primaryKey != classedModel.getGroupId()) {
				getSocialActivityLocalService().addActivity(
					userId, classedModel.getGroupId(), className, primaryKey,
					SocialActivityConstants.TYPE_SUBSCRIBE, extraData, 0);
			}
		}
		else {
			getSocialActivityLocalService().addActivity(
				userId, classedModel.getGroupId(), className, primaryKey, type,
				extraData, receiverUserId);
		}
	}

	@Override
	public void addUniqueActivity(
			long userId, long groupId, Date createDate, T classedModel,
			int type, String extraData, long receiverUserId)
		throws PortalException {

		String className = getClassName(classedModel);
		long primaryKey = getPrimaryKey(classedModel);

		getSocialActivityLocalService().addUniqueActivity(
			userId, groupId, createDate, className, primaryKey, type, extraData,
			receiverUserId);
	}

	@Override
	public void addUniqueActivity(
			long userId, long groupId, T classedModel, int type,
			String extraData, long receiverUserId)
		throws PortalException {

		String className = getClassName(classedModel);
		long primaryKey = getPrimaryKey(classedModel);

		getSocialActivityLocalService().addUniqueActivity(
			userId, groupId, className, primaryKey, type, extraData,
			receiverUserId);
	}

	@Override
	public void deleteActivities(T classedModel) throws PortalException {
		String className = getClassName(classedModel);
		long primaryKey = getPrimaryKey(classedModel);

		getSocialActivityLocalService().deleteActivities(className, primaryKey);
	}

	@Override
	public void updateLastSocialActivity(
			long userId, long groupId, T classedModel, int type,
			Date createDate)
		throws PortalException {

		String className = getClassName(classedModel);
		long primaryKey = getPrimaryKey(classedModel);

		SocialActivity lastSocialActivity =
			getSocialActivityLocalService().fetchFirstActivity(
				className, primaryKey, type);

		if (lastSocialActivity != null) {
			lastSocialActivity.setCreateDate(createDate.getTime());
			lastSocialActivity.setUserId(userId);

			getSocialActivityLocalService().updateSocialActivity(
				lastSocialActivity);
		}
	}

	protected String getClassName(T classedModel) {
		return classedModel.getModelClassName();
	}

	protected long getPrimaryKey(T classedModel) {
		if (!(classedModel.getPrimaryKeyObj() instanceof Long)) {
			throw new IllegalArgumentException(
				"Only models with a primary key of type Long can make use " +
					"of SocialActivityHandlers");
		}

		return (Long)classedModel.getPrimaryKeyObj();
	}

	protected abstract SocialActivityLocalService
		getSocialActivityLocalService();

}