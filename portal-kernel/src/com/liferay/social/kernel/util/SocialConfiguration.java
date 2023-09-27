/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.social.kernel.util;

import com.liferay.social.kernel.model.SocialActivityDefinition;

import java.util.List;

/**
 * @author Zsolt Berentey
 */
public interface SocialConfiguration {

	public List<String> getActivityCounterNames();

	public List<String> getActivityCounterNames(boolean transientCounter);

	public List<String> getActivityCounterNames(int ownerType);

	public List<String> getActivityCounterNames(
		int ownerType, boolean transientCounter);

	public SocialActivityDefinition getActivityDefinition(
		String modelName, int activityType);

	public List<SocialActivityDefinition> getActivityDefinitions(
		String modelName);

	public String[] getActivityModelNames();

	public List<Object> read(ClassLoader classLoader, String[] xmls)
		throws Exception;

	public void removeActivityDefinition(
		SocialActivityDefinition activityDefinition);

}