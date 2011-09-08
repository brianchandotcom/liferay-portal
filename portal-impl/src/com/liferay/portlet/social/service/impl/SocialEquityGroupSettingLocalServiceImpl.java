/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.social.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.social.model.SocialEquityGroupSetting;
import com.liferay.portlet.social.model.SocialEquitySettingConstants;
import com.liferay.portlet.social.service.base.SocialEquityGroupSettingLocalServiceBaseImpl;

/**
 * The social equity group setting local service.
 *
 * <p>
 * This service is responsible for storing social equity settings related to
 * groups, i.e. whether social equity is turned on or off for a particular
 * group.
 * </p>
 *
 * @author Zsolt Berentey
 */
public class SocialEquityGroupSettingLocalServiceImpl
	extends SocialEquityGroupSettingLocalServiceBaseImpl {

	/**
	 * Returns <code>true</code> if social equity is turned on for the model
	 * (asset type) in the group.
	 *
	 * @param  groupId the primary key of the group
	 * @param  className the class name for the target asset type
	 * @return <code>true</code> if social equity is enabled for the model;
	 *         <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean isEnabled(long groupId, String className)
		throws SystemException {

		if (isEnabled(
				groupId, className,
				SocialEquitySettingConstants.TYPE_INFORMATION) &&
			isEnabled(
				groupId, className,
				SocialEquitySettingConstants.TYPE_PARTICIPATION)) {

			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Returns <code>true</code> if the specified social equity scoring type is
	 * turned on for the model (asset type) in the group.
	 *
	 * @param  groupId the primary key of the group
	 * @param  className the class name for the target asset type
	 * @param  type the social equity score type, acceptable values are {@link
	 *         SocialEquitySettingConstants.TYPE_INFORMATION} and {@link
	 *         SocialEquitySettingConstants.TYPE_PARTICIPATION}
	 * @return <code>true</code> if the given type of social equity scoring is
	 *         enabled for the model; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean isEnabled(long groupId, String className, int type)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		SocialEquityGroupSetting equityGroupSetting =
			socialEquityGroupSettingPersistence.fetchByG_C_T(
				groupId, classNameId, type);

		if (equityGroupSetting != null) {
			return equityGroupSetting.isEnabled();
		}

		if (className.equals(Group.class.getName())) {
			return false;
		}

		return true;
	}

	/**
	 * Updates the group related social equity settings for the group and model
	 * (asset type).
	 *
	 * <p>
	 * This method stores whether the social equity scoring type (information
	 * or participation) is turned on for the model in the group.
	 * </p>
	 *
	 * @param  groupId the primary key of the group
	 * @param  className the class name for the target asset
	 * @param  type the social equity score type, acceptable values are {@link
	 *         SocialEquitySettingConstants.TYPE_INFORMATION}, {@link
	 *         SocialEquitySettingConstants.TYPE_PARTICIPATION}
	 * @param  enabled whether social equity is turned on
	 * @throws PortalException if the group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void updateEquityGroupSetting(
			long groupId, String className, int type, boolean enabled)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		SocialEquityGroupSetting equityGroupSetting =
			socialEquityGroupSettingPersistence.fetchByG_C_T(
				groupId, classNameId, type);

		if (equityGroupSetting == null) {
			Group group = groupLocalService.getGroup(groupId);

			long equityGroupSettingId = counterLocalService.increment();

			equityGroupSetting = socialEquityGroupSettingPersistence.create(
				equityGroupSettingId);

			equityGroupSetting.setGroupId(groupId);
			equityGroupSetting.setCompanyId(group.getCompanyId());
			equityGroupSetting.setClassNameId(classNameId);
			equityGroupSetting.setType(type);
		}

		equityGroupSetting.setEnabled(enabled);

		socialEquityGroupSettingPersistence.update(equityGroupSetting, false);
	}

}