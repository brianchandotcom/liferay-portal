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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Alejandro Tardín
 */
public class RetrieverWhenNullCategoryIsRequestedTest
	extends BaseFormNavigatorEntryConfigurationRetrieverTestCase {

	@Override
	public void setUp() throws Exception {
		super.setUp();

		StringBundler sb = new StringBundler(3);

		sb.append("add");
		sb.append(StringPool.EQUAL);
		sb.append("formNavigatorEntryKey1,formNavigatorEntryKey2");

		createConfiguration("form1", new String[] {sb.toString()});
	}

	@Test
	public void testReturnsTheKeysInThatLineWhenAskedForANullCategory() {
		List<String> formNavigatorEntryKeys =
			formNavigatorEntryConfigurationRetriever.getFormNavigatorEntryKeys(
				"form1", null, "add"
			).get();

		Assert.assertEquals(
			formNavigatorEntryKeys.toString(), 2,
			formNavigatorEntryKeys.size());

		Iterator<String> iterator = formNavigatorEntryKeys.iterator();

		Assert.assertEquals("formNavigatorEntryKey1", iterator.next());
		Assert.assertEquals("formNavigatorEntryKey2", iterator.next());
	}

}