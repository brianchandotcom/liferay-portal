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

package com.liferay.social.networking.web.members.social;

import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;
import com.liferay.portal.service.OrganizationLocalService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portlet.social.model.BaseSocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityInterpreter;
import com.liferay.social.networking.constants.SocialNetworkingPortletKeys;
import com.liferay.social.networking.members.social.MembersActivityKeys;
import com.liferay.social.networking.web.util.SocialNetworkingResourceBundleLoader;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {"javax.portlet.name=" + SocialNetworkingPortletKeys.MEMBERS},
	service = SocialActivityInterpreter.class
)
public class MembersActivityInterpreter extends BaseSocialActivityInterpreter {

	@Override
	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	@Override
	protected String getLink(
			SocialActivity activity, ServiceContext serviceContext)
		throws Exception {

		int activityType = activity.getType();

		if (activityType != MembersActivityKeys.ADD_MEMBER) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(5);

		sb.append(serviceContext.getPortalURL());
		sb.append(serviceContext.getPathFriendlyURLPublic());
		sb.append(StringPool.SLASH);

		User creatorUser = _userLocalService.getUserById(activity.getUserId());

		sb.append(HtmlUtil.escapeURL(creatorUser.getScreenName()));

		sb.append("/profile");

		return sb.toString();
	}

	@Override
	protected ResourceBundleLoader getResourceBundleLoader() {
		return SocialNetworkingResourceBundleLoader.INSTANCE;
	}

	@Override
	protected Object[] getTitleArguments(
			String groupName, SocialActivity activity, String link,
			String title, ServiceContext serviceContext)
		throws Exception {

		int activityType = activity.getType();

		if (activityType != MembersActivityKeys.ADD_MEMBER) {
			return new Object[0];
		}

		String creatorUserName = getUserName(
			activity.getUserId(), serviceContext);

		String creatorUserNameURL = wrapLink(
			getLink(activity, serviceContext), creatorUserName);

		StringBundler sb = new StringBundler(4);

		sb.append(serviceContext.getPortalURL());
		sb.append(serviceContext.getPathFriendlyURLPublic());

		Organization organization = _organizationLocalService.getOrganization(
			activity.getClassPK());

		Group group = organization.getGroup();

		sb.append(group.getFriendlyURL());

		sb.append("/profile");

		String organizationNameURL = wrapLink(
			sb.toString(), HtmlUtil.escape(organization.getName()));

		return new Object[] {creatorUserNameURL, organizationNameURL};
	}

	@Override
	protected String getTitlePattern(
		String groupName, SocialActivity activity) {

		int activityType = activity.getType();

		if (activityType == MembersActivityKeys.ADD_MEMBER) {
			return "activity-social-networking-summary-join-organization";
		}

		return StringPool.BLANK;
	}

	@Override
	protected boolean hasPermissions(
		PermissionChecker permissionChecker, SocialActivity activity,
		String actionId, ServiceContext serviceContext) {

		return true;
	}

	@Reference(unbind = "-")
	protected void setOrganizationLocalService(
		OrganizationLocalService organizationLocalService) {

		_organizationLocalService = organizationLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private static final String[] _CLASS_NAMES = {Organization.class.getName()};

	private OrganizationLocalService _organizationLocalService;
	private UserLocalService _userLocalService;

}