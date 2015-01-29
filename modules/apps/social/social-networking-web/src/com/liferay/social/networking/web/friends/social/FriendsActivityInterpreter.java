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

package com.liferay.social.networking.web.friends.social;

import com.liferay.portlet.social.model.SocialActivityInterpreter;
import com.liferay.social.networking.constants.SocialNetworkingPortletKeys;
import com.liferay.social.networking.web.social.BaseSocialNetworkingActivityInterpreter;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 * @author Zsolt Berentey
 */
@Component(
	property = {"javax.portlet.name=" + SocialNetworkingPortletKeys.FRIENDS},
	service = SocialActivityInterpreter.class
)
public class FriendsActivityInterpreter
	extends BaseSocialNetworkingActivityInterpreter {
}