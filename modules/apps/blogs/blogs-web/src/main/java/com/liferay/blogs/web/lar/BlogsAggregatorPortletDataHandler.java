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

package com.liferay.blogs.web.lar;

import com.liferay.blogs.web.constants.BlogsPortletKeys;
import com.liferay.portlet.exportimport.lar.DataLevel;
import com.liferay.portlet.exportimport.lar.DefaultConfigurationPortletDataHandler;
import com.liferay.portlet.exportimport.lar.PortletDataHandler;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Julio Camarero
 */
@Component(
	property = {"javax.portlet.name=" + BlogsPortletKeys.BLOGS_AGGREGATOR},
	service = PortletDataHandler.class
)
public class BlogsAggregatorPortletDataHandler
	extends DefaultConfigurationPortletDataHandler {

	@Activate
	protected void activate() {
		setDataLevel(DataLevel.PORTLET_INSTANCE);
		setPublishToLiveByDefault(true);
	}

}