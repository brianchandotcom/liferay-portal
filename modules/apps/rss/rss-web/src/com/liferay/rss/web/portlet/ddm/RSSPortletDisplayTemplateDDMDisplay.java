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

package com.liferay.rss.web.portlet.ddm;

import com.liferay.portlet.dynamicdatamapping.util.DDMDisplay;
import com.liferay.portlet.portletdisplaytemplate.ddm.PortletDisplayTemplateDDMDisplay;
import com.liferay.rss.web.util.RSSFeed;

import org.osgi.service.component.annotations.Component;

/**
 * @author Preston Crary
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=com_liferay_rss_web_portlet_RSSPortlet"
	},
	service = DDMDisplay.class
)
public class RSSPortletDisplayTemplateDDMDisplay
	extends PortletDisplayTemplateDDMDisplay {

	public static final String[] CLASS_NAMES = {RSSFeed.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

}