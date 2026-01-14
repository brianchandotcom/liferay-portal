/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.impl;

import com.liferay.petra.io.StreamUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.SAXReader;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.xml.SAXReaderImpl;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class ResourcePermissionLocalServiceImplTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testQueryHintsIndex() throws Exception {
		SAXReader saxReader = new SAXReaderImpl();

		Document document = saxReader.read(
			ResourcePermissionLocalServiceImpl.class.getResourceAsStream(
				"/com/liferay/portal/service.xml"));

		Assert.assertNotNull(
			document.selectSingleNode(
				"/service-builder/entity[@name='ResourcePermission']/finder" +
					"[@name='C_N_S_P_R_A']"));

		String indexes = StreamUtil.toString(
			ResourcePermissionLocalServiceImpl.class.getResourceAsStream(
				"/com/liferay/portal/tools/sql/dependencies/indexes.sql"));

		Assert.assertTrue(
			indexes.contains("create index IX_954084A2 on ResourcePermission"));
	}

}