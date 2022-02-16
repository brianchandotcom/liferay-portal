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

package com.liferay.commerce.site.initializer.extender.internal;

import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.site.initializer.extender.spi.CommerceSiteInitializerExtender;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rafael Praxedes
 */
@Component(
	enabled = false, immediate = true,
	service = CommerceSiteInitializerExtender.class
)
public class CommerceSiteInitializerExtenderImpl
	implements CommerceSiteInitializerExtender {

	@Override
	public void addCPDefinitions(
			Map<String, String> documentsStringUtilReplaceValues,
			Map<String, String> objectDefinitionIdsStringUtilReplaceValues,
			ServiceContext serviceContext)
		throws Exception {
	}

}