/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.social.lar;

import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portlet.social.service.SocialActivityAchievementLocalServiceUtil;
import com.liferay.portlet.social.service.SocialActivityCounterLocalServiceUtil;
import com.liferay.portlet.social.service.SocialActivityLimitLocalServiceUtil;
import com.liferay.portlet.social.service.SocialActivityLocalServiceUtil;
import com.liferay.portlet.social.service.SocialActivitySettingLocalServiceUtil;
import com.liferay.portlet.social.service.SocialRelationLocalServiceUtil;
import com.liferay.portlet.social.service.SocialRequestLocalServiceUtil;

import javax.portlet.PortletPreferences;

/**
 * @author Jonathan Potter
 */
public class SocialPortletDataHandlerImpl extends BasePortletDataHandler {

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, long companyId)
		throws Exception {

		SocialActivityLocalServiceUtil.deleteActivitiesByCompany(companyId);
		SocialActivityAchievementLocalServiceUtil
			.deleteActivityAchievementsByCompany(companyId);
		SocialActivityCounterLocalServiceUtil.deleteActivityCountersByCompany(
			companyId);
		SocialActivityLimitLocalServiceUtil.deleteActivityLimitsByCompany(
			companyId);
		SocialActivitySettingLocalServiceUtil.deleteActivitySettingsByCompany(
			companyId);
		SocialRelationLocalServiceUtil.deleteRelationsByCompany(companyId);
		SocialRequestLocalServiceUtil.deleteRequestsByCompany(companyId);

		return null;
	}

}