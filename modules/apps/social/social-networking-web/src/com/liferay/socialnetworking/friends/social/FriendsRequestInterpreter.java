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

package com.liferay.socialnetworking.friends.social;

import com.liferay.portal.service.UserLocalService;
import com.liferay.portlet.social.service.SocialActivityLocalService;
import com.liferay.portlet.social.service.SocialRelationLocalService;
import com.liferay.socialnetworking.social.BaseSocialNetworkingRequestInterpreter;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
public class FriendsRequestInterpreter
	extends BaseSocialNetworkingRequestInterpreter {

	@Override
	protected SocialActivityLocalService getSocialActivityLocalService() {
		return _socialActivityLocalService;
	}

	@Override
	protected SocialRelationLocalService getSocialRelationLocalService() {
		return _socialRelationLocalService;
	}

	@Override
	protected UserLocalService getUserLocalService() {
		return _userLocalService;
	}

	@Reference
	protected void setSocialActivityLocalService(
		SocialActivityLocalService socialActivityLocalService) {

		_socialActivityLocalService = socialActivityLocalService;
	}

	@Reference
	protected void setSocialRelationLocalService(
		SocialRelationLocalService socialRelationLocalService) {

		_socialRelationLocalService = socialRelationLocalService;
	}

	@Reference
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private SocialActivityLocalService _socialActivityLocalService;
	private SocialRelationLocalService _socialRelationLocalService;
	private UserLocalService _userLocalService;

}