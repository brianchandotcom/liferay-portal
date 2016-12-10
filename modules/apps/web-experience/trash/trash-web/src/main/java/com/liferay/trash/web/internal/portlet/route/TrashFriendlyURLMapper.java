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

package com.liferay.trash.web.internal.portlet.route;

import com.liferay.portal.kernel.portlet.DefaultFriendlyURLMapper;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.trash.web.internal.constants.TrashPortletKeys;

import org.osgi.service.component.annotations.Component;

/**
 * Provides an implementation of <code>FriendlyURLMapper</code> (in
 * <code>com.liferay.portal.kernel</code>) to use with Recycle Bin friendly URL
 * routes. To add a friendly URL mapping to the Recycle Bin portlet, add a new
 * route to the <code>META-INF/friendly-url-routes/routes.xml</code> file.
 *
 * @author Juergen Kappler
 */
@Component(
	property = {
		"com.liferay.portlet.friendly-url-routes=META-INF/friendly-url-routes/routes.xml",
		"javax.portlet.name=" + TrashPortletKeys.TRASH
	},
	service = FriendlyURLMapper.class
)
public class TrashFriendlyURLMapper extends DefaultFriendlyURLMapper {

	@Override
	public String getMapping() {
		return _MAPPING;
	}

	private static final String _MAPPING = "recycle_bin";

}