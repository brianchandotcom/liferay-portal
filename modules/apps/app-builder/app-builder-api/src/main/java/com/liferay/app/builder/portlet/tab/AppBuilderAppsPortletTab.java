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

package com.liferay.app.builder.portlet.tab;

import com.liferay.portal.kernel.model.User;

import java.util.Locale;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Inácio Nery
 */
@ProviderType
public interface AppBuilderAppsPortletTab {

	public void deleteApp(long appBuilderAppId, User user) throws Exception;

	public String getEditEntryPoint();

	public String getLabel(Locale locale);

	public String getListEntryPoint();

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getListEntryPoint()}
	 */
	@Deprecated
	public default String getPluginEntryPoint() {
		return getListEntryPoint();
	}

}