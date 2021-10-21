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

package com.liferay.commerce.product.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.product.exception.NoSuchCPSpecificationOptionException;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.service.CPSpecificationOptionLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.frutilla.FrutillaRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Igor Beslic
 */
@RunWith(Arquillian.class)
public class CPSpecificationOptionLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addUser();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), _user.getUserId());
	}

	@After
	public void tearDown() throws Exception {
		_cpSpecificationOptionLocalService.deleteCPSpecificationOptions(
			_serviceContext.getCompanyId());
	}

	@Test
	public void testAddSpecficationOption() throws Exception {
		frutillaRule.scenario(
			"Add Specification option"
		).given(
			"There is no any specifications"
		).when(
			"Specification option is added"
		).then(
			"Specification options should be created"
		);

		CPSpecificationOption cpSpecificationOption1 =
			_addCPSpecificationOptions(_serviceContext);

		CPSpecificationOption cpSpecificationOption2 =
			_cpSpecificationOptionLocalService.getCPSpecificationOption(
				_serviceContext.getCompanyId(),
				cpSpecificationOption1.getKey());

		Assert.assertEquals(
			cpSpecificationOption1.getKey(), cpSpecificationOption2.getKey());
	}

	@Test(expected = NoSuchCPSpecificationOptionException.class)
	public void testGetSpecficationOption() throws Exception {
		frutillaRule.scenario(
			"Get Specification option"
		).given(
			"There is no any specifications"
		).when(
			"Specification option is searched"
		).then(
			"NoSuchCPSpecificationOptionException is thrown"
		);

		_cpSpecificationOptionLocalService.getCPSpecificationOption(
			_serviceContext.getCompanyId(), RandomTestUtil.randomString());
	}

	@Rule
	public final FrutillaRule frutillaRule = new FrutillaRule();

	private CPSpecificationOption _addCPSpecificationOptions(
			ServiceContext serviceContext)
		throws Exception {

		return _cpSpecificationOptionLocalService.addCPSpecificationOption(
			serviceContext.getUserId(), 0L,
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomBoolean(), RandomTestUtil.randomString(),
			serviceContext);
	}

	private static User _user;

	@Inject
	private CPSpecificationOptionLocalService
		_cpSpecificationOptionLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private ServiceContext _serviceContext;

}