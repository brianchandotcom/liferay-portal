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
public class SocialConfigurationUtil {

	public static List<String> getActivityCounterNames() {
		return _socialConfiguration.getActivityCounterNames();
	}

	public static List<String> getActivityCounterNames(
		boolean transientCounter) {

		return _socialConfiguration.getActivityCounterNames(transientCounter);
	}

	public static List<String> getActivityCounterNames(int ownerType) {
		return _socialConfiguration.getActivityCounterNames(ownerType);
	}

	public static List<String> getActivityCounterNames(
		int ownerType, boolean transientCounter) {

		return _socialConfiguration.getActivityCounterNames(
			ownerType, transientCounter);
	}

	public static SocialActivityDefinition getActivityDefinition(
		String modelName, int activityType) {

		return _socialConfiguration.getActivityDefinition(
			modelName, activityType);
	}

	public static List<SocialActivityDefinition> getActivityDefinitions(
		String modelName) {

		return _socialConfiguration.getActivityDefinitions(modelName);
	}

	public static String[] getActivityModelNames() {
		return _socialConfiguration.getActivityModelNames();
	}

	public static SocialConfiguration getSocialConfiguration() {
		return _socialConfiguration;
	}

	public static List<Object> read(ClassLoader classLoader, String[] xmls)
		throws Exception {

		return _socialConfiguration.read(classLoader, xmls);
	}

	public static void removeActivityDefinition(
		SocialActivityDefinition activityDefinition) {

		_socialConfiguration.removeActivityDefinition(activityDefinition);
	}

	public void setSocialConfiguration(
		SocialConfiguration socialConfiguration) {

		_socialConfiguration = socialConfiguration;
	}

	private static SocialConfiguration _socialConfiguration;

}