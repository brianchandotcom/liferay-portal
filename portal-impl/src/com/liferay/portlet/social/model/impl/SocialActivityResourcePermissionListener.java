/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.social.model.impl;

import com.liferay.portal.ModelListenerException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.BaseModelListener;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.social.model.SocialActivitySet;
import com.liferay.portlet.social.service.SocialActivitySetLocalServiceUtil;

/**
 * @author Zsolt Berentey
 */
public class SocialActivityResourcePermissionListener
	extends BaseModelListener<ResourcePermission> {

	@Override
	public void onBeforeRemove(ResourcePermission resourcePermission)
		throws ModelListenerException {

		if ((resourcePermission.getScope() !=
				ResourceConstants.SCOPE_INDIVIDUAL) ||
			!resourcePermission.hasActionId(ActionKeys.VIEW)) {

			return;
		}

		try {
			long classNameId = PortalUtil.getClassNameId(
				resourcePermission.getName());
			long classPK = GetterUtil.getLong(resourcePermission.getPrimKey());

			SocialActivitySet activitySet =
				SocialActivitySetLocalServiceUtil.fetchAssetActivitySet(
					classNameId, classPK);

			if (activitySet != null) {
				if (ResourcePermissionLocalServiceUtil.hasResourcePermission(
						resourcePermission.getCompanyId(),
						SocialActivitySet.class.getName(),
						resourcePermission.getScope(),
						String.valueOf(activitySet.getActivitySetId()),
						resourcePermission.getRoleId(), ActionKeys.VIEW)) {

					return;
				}

				ResourcePermissionLocalServiceUtil.setOwnerResourcePermissions(
					resourcePermission.getCompanyId(),
					SocialActivitySet.class.getName(),
					resourcePermission.getScope(),
					String.valueOf(activitySet.getActivitySetId()),
					resourcePermission.getRoleId(),
					resourcePermission.getOwnerId(),
					new String[] {ActionKeys.VIEW});
			}
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

}