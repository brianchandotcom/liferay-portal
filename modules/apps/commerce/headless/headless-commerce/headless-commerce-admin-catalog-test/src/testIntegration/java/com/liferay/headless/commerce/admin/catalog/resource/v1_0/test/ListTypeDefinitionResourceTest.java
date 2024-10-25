/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.service.CPOptionCategoryLocalService;
import com.liferay.commerce.product.service.CPSpecificationOptionLocalService;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.ListTypeDefinition;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zoltán Takács
 */
@RunWith(Arquillian.class)
public class ListTypeDefinitionResourceTest
	extends BaseListTypeDefinitionResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser(testCompany);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				testCompany.getCompanyId(), testGroup.getGroupId(),
				_user.getUserId());

		CPOptionCategory cpOptionCategory =
			_cpOptionCategoryLocalService.addCPOptionCategory(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomDouble(), RandomTestUtil.randomString(),
				serviceContext);

		_cpSpecificationOption =
			_cpSpecificationOptionLocalService.addCPSpecificationOption(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				cpOptionCategory.getCPOptionCategoryId(), null,
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(), true,
				RandomTestUtil.randomString(), RandomTestUtil.randomDouble(),
				serviceContext);
	}

	@Ignore
	@Override
	@Test
	public void testGetSpecificationIdListTypeDefinitionsPage()
		throws Exception {

		super.testGetSpecificationIdListTypeDefinitionsPage();
	}

	@Override
	@Test
	public void testPostSpecificationIdListTypeDefinition() throws Exception {
		super.testPostSpecificationIdListTypeDefinition();

		CPSpecificationOption cpSpecificationOption =
			_cpSpecificationOptionLocalService.getCPSpecificationOption(
				_cpSpecificationOption.getCPSpecificationOptionId());

		Assert.assertFalse(
			cpSpecificationOption.getListTypeDefinitions(
			).isEmpty());
	}

	@Override
	protected ListTypeDefinition
			testPostSpecificationIdListTypeDefinition_addListTypeDefinition(
				ListTypeDefinition listTypeDefinition)
		throws Exception {

		return listTypeDefinitionResource.postSpecificationIdListTypeDefinition(
			_cpSpecificationOption.getCPSpecificationOptionId(),
			listTypeDefinition);
	}

	@Inject
	private CPOptionCategoryLocalService _cpOptionCategoryLocalService;

	@DeleteAfterTestRun
	private CPSpecificationOption _cpSpecificationOption;

	@Inject
	private CPSpecificationOptionLocalService
		_cpSpecificationOptionLocalService;

	@DeleteAfterTestRun
	private User _user;

}