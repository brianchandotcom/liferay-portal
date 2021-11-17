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

package com.liferay.portal.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.process.ProcessConfig;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PortalClassPathUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Dante Wang
 */
@RunWith(Arquillian.class)
public class PortalClassPathUtilTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetPortalProcessConfig() {
		ProcessConfig processConfig =
			PortalClassPathUtil.getPortalProcessConfig();

		List<String> runtimeClassPathEntries = StringUtil.split(
			processConfig.getRuntimeClassPath(), File.pathSeparatorChar);

		Set<String> runtimeClassPathEntrySet = new HashSet<>();

		List<String> duplicateEntries = new ArrayList<>();

		for (String runtimeClassPathEntry : runtimeClassPathEntries) {
			if (!runtimeClassPathEntrySet.add(runtimeClassPathEntry)) {
				duplicateEntries.add(runtimeClassPathEntry);
			}
		}

		Assert.assertTrue(
			"Portal process config runtime class path should not contain " +
				"duplicate entries: " + duplicateEntries,
			duplicateEntries.isEmpty());
	}

}