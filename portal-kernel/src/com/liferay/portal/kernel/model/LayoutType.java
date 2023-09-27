/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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