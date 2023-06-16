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

package com.liferay.site.initializer.extender;

import com.liferay.portal.kernel.service.ServiceContext;

import java.util.Map;

import javax.servlet.ServletContext;

import org.osgi.annotation.versioning.ProviderType;
import org.osgi.framework.Bundle;

/**
 * @author Nilton Vieira
 */
@ProviderType
public interface SiteInitializerCommerceExtension {

	public void addAccountGroups(
			ServiceContext serviceContext, ServletContext servletContext)
		throws Exception;

	public void addCPDefinitions(
			Bundle bundle, Map<String, String> documentsStringUtilReplaceValues,
			Map<String, String> objectDefinitionIdsStringUtilReplaceValues,
			ServiceContext serviceContext, ServletContext servletContext)
		throws Exception;

	public void addPortletSettings(
			ClassLoader classLoader, ServiceContext serviceContext,
			ServletContext servletContext)
		throws Exception;

	public long getCommerceChannelGroupId(long siteGroupId);

	public String getCommerceOrderClassName();

}