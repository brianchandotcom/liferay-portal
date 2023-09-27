/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.social;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.social.kernel.model.SocialActivity;
import com.liferay.social.kernel.model.SocialActivityConstants;
import com.liferay.social.kernel.service.SocialActivityLocalService;

import java.util.Date;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseSocialActivityManager
	<T extends ClassedModel & GroupedModel>
		implements SocialActivityManager<T> {

	@Override
	public void addActivity(
			long userId, T model, int type, String extraData,
			long receiverUserId)
		throws PortalException {

		String className = getClassName(model);
		long primaryKey = getPrimaryKey(model);

		if (type == SocialActivityConstants.TYPE_SUBSCRIBE) {
			if (primaryKey != model.getGroupId()) {
				getSocialActivityLocalService().addActivity(
					userId, model.getGroupId(), className, primaryKey,
					SocialActivityConstants.TYPE_SUBSCRIBE, extraData, 0);
			}
		}
		else {
			getSocialActivityLocalService().addActivity(
				userId, model.getGroupId(), className, primaryKey, type,
				extraData, receiverUserId);
		}
	}

	@Override
	public void addUniqueActivity(
			long userId, Date createDate, T model, int type, String extraData,
			long receiverUserId)
		throws PortalException {

		getSocialActivityLocalService().addUniqueActivity(
			userId, model.getGroupId(), createDate, getClassName(model),
			getPrimaryKey(model), type, extraData, receiverUserId);
	}

	@Override
	public void addUniqueActivity(
			long userId, T model, int type, String extraData,
			long receiverUserId)
		throws PortalException {

		getSocialActivityLocalService().addUniqueActivity(
			userId, model.getGroupId(), getClassName(model),
			getPrimaryKey(model), type, extraData, receiverUserId);
	}

	@Override
	public void deleteActivities(T model) throws PortalException {
		getSocialActivityLocalService().deleteActivities(
			getClassName(model), getPrimaryKey(model));
	}

	@Override
	public void updateLastSocialActivity(
		long userId, T model, int type, Date createDate) {

		SocialActivity lastSocialActivity =
			getSocialActivityLocalService().fetchFirstActivity(
				getClassName(model), getPrimaryKey(model), type);

		if (lastSocialActivity != null) {
			lastSocialActivity.setUserId(userId);
			lastSocialActivity.setCreateDate(createDate.getTime());

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
				"Only models with a primary key of type Long can make use of " +
					"SocialActivityManagers");
		}

		return (Long)classedModel.getPrimaryKeyObj();
	}

	protected abstract SocialActivityLocalService
		getSocialActivityLocalService();

}