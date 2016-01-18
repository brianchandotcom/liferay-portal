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

package com.liferay.message.boards.web.portlet.configuration.icon;

import com.liferay.message.boards.web.constants.MBPortletKeys;
import com.liferay.message.boards.web.portlet.action.ActionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIconFactory;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIconFactory;
import com.liferay.portlet.messageboards.model.MBMessageDisplay;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio González
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS_ADMIN,
		"path=/message_boards/view_message"
	},
	service = PortletConfigurationIconFactory.class
)
public class ThreadPermissionsPortletConfigurationIconFactory
	extends BasePortletConfigurationIconFactory {

	@Override
	public PortletConfigurationIcon create(PortletRequest portletRequest) {
		try {
			MBMessageDisplay messageDisplay = ActionUtil.getMessageDisplay(
				portletRequest);

			return new ThreadPermissionsPortletConfigurationIcon(
				portletRequest, messageDisplay);
		}
		catch (PortalException pe) {
		}

		return null;
	}

	@Override
	public double getWeight() {
		return 102;
	}

}