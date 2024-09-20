/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.db.partition.test.util.BaseDBPartitionTestCase;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;

import java.util.Collections;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Carolina Barbosa
 */
@RunWith(Arquillian.class)
public class ObjectDefinitionLocalServiceDBPartitionTest
	extends BaseDBPartitionTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		BaseDBPartitionTestCase.setUpClass();

		BaseDBPartitionTestCase.setUpDBPartitions();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		BaseDBPartitionTestCase.tearDownDBPartitions();
	}

	@Test
	public void testPublishObjectDefinition() throws Exception {
		_objectDefinition = null;

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setWithSafeCloseable(
					TestPropsValues.getCompanyId())) {

			_objectDefinition =
				ObjectDefinitionTestUtil.publishObjectDefinition(
					Collections.singletonList(
						new TextObjectFieldBuilder(
						).labelMap(
							RandomTestUtil.randomLocaleStringMap()
						).name(
							"a" + RandomTestUtil.randomString()
						).build()),
					ObjectDefinitionConstants.SCOPE_COMPANY,
					TestPropsValues.getUserId());
		}

		_assertResourceActionsCount(
			TestPropsValues.getCompanyId(), _objectDefinition, 4);
		_assertResourceActionsCount(COMPANY_IDS[0], _objectDefinition, 0);
		_assertResourceActionsCount(COMPANY_IDS[1], _objectDefinition, 0);
	}

	private void _assertResourceActionsCount(
		long companyId, ObjectDefinition objectDefinition,
		int resourceActionsCount) {

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setWithSafeCloseable(companyId)) {

			Assert.assertEquals(
				resourceActionsCount,
				_resourceActionLocalService.getResourceActionsCount(
					objectDefinition.getClassName()));
		}
	}

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition;

	@Inject
	private ResourceActionLocalService _resourceActionLocalService;

}