/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.model.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.product.change.tracking.test.CPDefinitionSpecificationOptionValueTableReferenceDefinitionTest;
import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.service.CPOptionCategoryLocalService;
import com.liferay.commerce.product.service.CPSpecificationOptionLocalService;
import com.liferay.list.type.entry.util.ListTypeEntryUtil;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Collections;

import org.frutilla.FrutillaRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Andrea Sbarra
 */
@RunWith(Arquillian.class)
public class ListTypeDefinitionModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_user = UserTestUtil.addUser();

		_group = GroupTestUtil.addGroup(
			_user.getCompanyId(), _user.getUserId(),
			GroupConstants.DEFAULT_PARENT_GROUP_ID);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		_cpOptionCategory = _cpOptionCategoryLocalService.addCPOptionCategory(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomDouble(),
			CPDefinitionSpecificationOptionValueTableReferenceDefinitionTest.
				class.getSimpleName(),
			_serviceContext);

		_listTypeDefinition = _addListTypeDefinition();
	}

	@Test(expected = ModelListenerException.class)
	public void testDeleteListTypeDefinitionLinkedToCPSpecificationOption()
		throws Exception {

		frutillaRule.scenario(
			"It should be not possible to delete a Picklist once it is " +
				"linked to a Specification Template"
		).given(
			"The specification template is created"
		).and(
			"the picklist is linked to the specification template"
		).when(
			"the picklist is deleted"
		).then(
			"an exception should be thrown"
		);

		_cpSpecificationOptionLocalService.addCPSpecificationOption(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			_cpOptionCategory.getCPOptionCategoryId(),
			new long[] {_listTypeDefinition.getListTypeDefinitionId()},
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), true,
			CPDefinitionSpecificationOptionValueTableReferenceDefinitionTest.
				class.getSimpleName(),
			RandomTestUtil.randomDouble(), _serviceContext);

		_listTypeDefinitionLocalService.deleteListTypeDefinition(
			_listTypeDefinition.getListTypeDefinitionId());
	}

	@Rule
	public FrutillaRule frutillaRule = new FrutillaRule();

	private ListTypeDefinition _addListTypeDefinition() throws Exception {
		return _listTypeDefinitionLocalService.addListTypeDefinition(
			null, TestPropsValues.getUserId(),
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			false,
			Collections.singletonList(
				ListTypeEntryUtil.createListTypeEntry(
					RandomTestUtil.randomString())));
	}

	private static User _user;

	private CPOptionCategory _cpOptionCategory;

	@Inject
	private CPOptionCategoryLocalService _cpOptionCategoryLocalService;

	@Inject
	private CPSpecificationOptionLocalService
		_cpSpecificationOptionLocalService;

	private Group _group;
	private ListTypeDefinition _listTypeDefinition;

	@Inject
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	private ServiceContext _serviceContext;

}