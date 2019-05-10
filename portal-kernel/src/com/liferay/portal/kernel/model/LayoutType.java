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

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.Serializable;

import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
@ProviderType
public interface LayoutType extends Serializable {

	public String[] getConfigurationActionDelete();

	public String[] getConfigurationActionUpdate();

	public Layout getLayout();

	public LayoutTypeAccessPolicy getLayoutTypeAccessPolicy();

	public LayoutTypeController getLayoutTypeController();

	public UnicodeProperties getTypeSettingsProperties();

	public String getTypeSettingsProperty(String key);

	public String getTypeSettingsProperty(String key, String defaultValue);

	public String getURL(Map<String, String> variables);

	public boolean isBrowsable();

	public boolean isFirstPageable();

	public boolean isParentable();

	public boolean isSitemapable();

	public boolean isURLFriendliable();

	public void setTypeSettingsProperty(String key, String value);

}