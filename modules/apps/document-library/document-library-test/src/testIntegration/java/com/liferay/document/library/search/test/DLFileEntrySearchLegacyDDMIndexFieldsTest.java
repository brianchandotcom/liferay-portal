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

package com.liferay.document.library.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.configuration.DDMIndexerConfiguration;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

/**
 * @author Jorge Díaz
 */
@RunWith(Arquillian.class)
public class DLFileEntrySearchLegacyDDMIndexFieldsTest
	extends DLFileEntrySearchTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		ConfigurationTestUtil.saveConfiguration(
			DDMIndexerConfiguration.class.getName(),
			new HashMapDictionary() {
				{
					put("enableLegacyDDMIndexFields", true);
				}
			});
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		ConfigurationTestUtil.deleteConfiguration(
			DDMIndexerConfiguration.class.getName());
	}

}