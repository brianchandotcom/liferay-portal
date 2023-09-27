/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.social;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.GroupedModel;

import java.util.Date;

/**
 * @author Adolfo Pérez
 */
public interface SocialActivityManager<T extends ClassedModel & GroupedModel> {

	public void addActivity(
			long userId, T model, int type, String extraData,
			long receiverUserId)
		throws PortalException;

	public void addUniqueActivity(
			long userId, Date createDate, T model, int type, String extraData,
			long receiverUserId)
		throws PortalException;

	public void addUniqueActivity(
			long userId, T model, int type, String extraData,
			long receiverUserId)
		throws PortalException;

	public void deleteActivities(T model) throws PortalException;

	public void updateLastSocialActivity(
			long userId, T model, int type, Date createDate)
		throws PortalException;

}