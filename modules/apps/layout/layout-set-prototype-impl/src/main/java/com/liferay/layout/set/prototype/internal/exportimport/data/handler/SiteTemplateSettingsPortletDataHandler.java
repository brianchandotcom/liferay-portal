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

package com.liferay.layout.set.prototype.internal.exportimport.data.handler;

import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.layout.set.prototype.constants.LayoutSetPrototypePortletKeys;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Daniela Zapata Riesco
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + LayoutSetPrototypePortletKeys.SITE_TEMPLATE_SETTINGS,
	service = PortletDataHandler.class
)
public class SiteTemplateSettingsPortletDataHandler
	extends LayoutSetPrototypePortletDataHandler {

	@Activate
	@Override
	protected void activate() {
		super.activate();
	}

}