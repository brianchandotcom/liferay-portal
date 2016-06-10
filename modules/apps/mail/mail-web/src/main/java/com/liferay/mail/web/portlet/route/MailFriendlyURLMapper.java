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

package com.liferay.mail.web.portlet.route;

import com.liferay.mail.constants.MailPortletKeys;
import com.liferay.portal.kernel.portlet.DefaultFriendlyURLMapper;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio González
 */
@Component(
	property = {
		"com.liferay.portlet.friendly-url-routes=META-INF/friendly-url-routes/routes.xml",
		"javax.portlet.name=" + MailPortletKeys.MAIL
	},
	service = FriendlyURLMapper.class
)
public class MailFriendlyURLMapper extends DefaultFriendlyURLMapper {

	@Override
	public String getMapping() {
		return _MAPPING;
	}

	private static final String _MAPPING = "mail";

}