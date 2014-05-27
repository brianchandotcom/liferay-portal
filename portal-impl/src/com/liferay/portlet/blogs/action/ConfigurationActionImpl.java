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

package com.liferay.portlet.blogs.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.SettingsConfigurationAction;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portlet.blogs.BlogsSettings;

import javax.portlet.ActionRequest;

/**
 * @author Jorge Ferrer
 * @author Thiago Moreira
 */
public class ConfigurationActionImpl extends SettingsConfigurationAction {

	@Override
	protected Settings getSettings(ActionRequest actionRequest)
		throws PortalException, SystemException {

		return new BlogsSettings(super.getSettings(actionRequest));
	}

}