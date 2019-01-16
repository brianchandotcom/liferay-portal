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

package com.liferay.frontend.taglib.form.navigator.internal.configuration;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Alejandro Tardín
 */
public class RetrieverWhenAConfigurationEntryHasOneLineWithNoKeysTest
	extends BaseFormNavigatorEntryConfigurationRetrieverTestCase {

	@Override
	public void setUp() throws Exception {
		super.setUp();

		createConfiguration("form1", new String[] {"add.general="});
	}

	@Test
	public void testReturnsEmptyList() {
		List<String> formNavigatorEntryKeys =
			formNavigatorEntryConfigurationRetriever.getFormNavigatorEntryKeys(
				"form1", "general", "add"
			).get();

		Assert.assertTrue(
			formNavigatorEntryKeys.toString(),
			formNavigatorEntryKeys.isEmpty());
	}

}