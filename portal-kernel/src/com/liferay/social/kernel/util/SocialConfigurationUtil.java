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

package com.liferay.social.kernel.util;

import com.liferay.social.kernel.model.SocialActivityDefinition;

import java.util.List;

/**
 * @author Zsolt Berentey
 */
public class SocialConfigurationUtil {

	public static List<String> getActivityCounterNames() {
		return getSocialConfiguration().getActivityCounterNames();
	}

	public static List<String> getActivityCounterNames(
		boolean transientCounter) {

		return getSocialConfiguration().getActivityCounterNames(
			transientCounter);
	}

	public static List<String> getActivityCounterNames(int ownerType) {
		return getSocialConfiguration().getActivityCounterNames(ownerType);
	}

	public static List<String> getActivityCounterNames(
		int ownerType, boolean transientCounter) {

		return getSocialConfiguration().getActivityCounterNames(
			ownerType, transientCounter);
	}

	public static SocialActivityDefinition getActivityDefinition(
		String modelName, int activityType) {

		return getSocialConfiguration().getActivityDefinition(
			modelName, activityType);
	}

	public static List<SocialActivityDefinition> getActivityDefinitions(
		String modelName) {

		return getSocialConfiguration().getActivityDefinitions(modelName);
	}

	public static String[] getActivityModelNames() {
		return getSocialConfiguration().getActivityModelNames();
	}

	public static SocialConfiguration getSocialConfiguration() {
		return _socialConfiguration;
	}

	public static List<Object> read(ClassLoader classLoader, String[] xmls)
		throws Exception {

		return getSocialConfiguration().read(classLoader, xmls);
	}

	public static void removeActivityDefinition(
		SocialActivityDefinition activityDefinition) {

		getSocialConfiguration().removeActivityDefinition(activityDefinition);
	}

	public void setSocialConfiguration(
		SocialConfiguration socialConfiguration) {

		_socialConfiguration = socialConfiguration;
	}

	private static SocialConfiguration _socialConfiguration;

}