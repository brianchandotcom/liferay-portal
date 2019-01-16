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
public class RetrieverWhenAConfigurationEntryHasDuplicatesTest
	extends BaseFormNavigatorEntryConfigurationRetrieverTestCase {

	@Override
	public void setUp() throws Exception {
		super.setUp();

		StringBundler sb = new StringBundler(6);

		sb.append("add.general");
		sb.append(StringPool.EQUAL);
		sb.append("formNavigatorEntryKey4,formNavigatorEntryKey3,");
		sb.append("formNavigatorEntryKey2,formNavigatorEntryKey1,");
		sb.append("formNavigatorEntryKey1,formNavigatorEntryKey2,");
		sb.append("formNavigatorEntryKey3,formNavigatorEntryKey4");

		createConfiguration("form1", new String[] {sb.toString()});
	}

	@Test
	public void testOnlyTheFirstOcurrenceIsRetrieved() {
		List<String> formNavigatorEntryKeys =
			formNavigatorEntryConfigurationRetriever.getFormNavigatorEntryKeys(
				"form1", "general", "add"
			).get();

		Assert.assertEquals(
			formNavigatorEntryKeys.toString(), 4,
			formNavigatorEntryKeys.size());

		Iterator<String> iterator = formNavigatorEntryKeys.iterator();

		Assert.assertEquals("formNavigatorEntryKey4", iterator.next());
		Assert.assertEquals("formNavigatorEntryKey3", iterator.next());
		Assert.assertEquals("formNavigatorEntryKey2", iterator.next());
		Assert.assertEquals("formNavigatorEntryKey1", iterator.next());
	}

}