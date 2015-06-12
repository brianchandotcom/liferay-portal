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

import java.util.Date;

/**
 * @author Adolfo Pérez
 */
public interface SocialActivityHandler<T extends ClassedModel & GroupedModel> {

	public void addActivity(
			long userId, long groupId, T classedModel, int type,
			String extraData, long receiverUserId)
		throws PortalException;

	public void addUniqueActivity(
			long userId, long groupId, Date createDate, T classedModel,
			int type, String extraData, long receiverUserId)
		throws PortalException;

	public void addUniqueActivity(
			long userId, long groupId, T classedModel, int type,
			String extraData, long receiverUserId)
		throws PortalException;

	public void deleteActivities(T classedModel) throws PortalException;

	public void updateLastSocialActivity(
			long userId, long groupId, T classedModel, int type,
			Date createDate)
		throws PortalException;

}