/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.social.kernel.model;

/**
 * @author Zsolt Berentey
 */
public interface SocialAchievement {

	public String getDescriptionKey();

	public String getIcon();

	public String getName();

	public String getNameKey();

	public void initialize(SocialActivityDefinition activityDefinition);

	public void processActivity(SocialActivity activity);

	public void setIcon(String icon);

	public void setName(String name);

	public void setProperty(String name, String value);

}