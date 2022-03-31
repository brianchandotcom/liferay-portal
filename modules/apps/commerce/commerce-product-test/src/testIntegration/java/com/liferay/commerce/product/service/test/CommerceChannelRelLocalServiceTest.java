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
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.product.constants.CommerceChannelConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.model.CommerceChannelRel;
import com.liferay.commerce.product.service.CommerceCatalogLocalServiceUtil;
import com.liferay.commerce.product.service.CommerceChannelLocalServiceUtil;
import com.liferay.commerce.product.service.CommerceChannelRelLocalServiceUtil;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.product.type.simple.constants.SimpleCPTypeConstants;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Andrea Sbarra
 */
@RunWith(Arquillian.class)
public class CommerceChannelRelLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_user = UserTestUtil.addUser();

		PrincipalThreadLocal.setName(_user.getUserId());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_user));

		_group = GroupTestUtil.addGroup(
			_user.getCompanyId(), _user.getUserId(),
			GroupConstants.DEFAULT_PARENT_GROUP_ID);

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			_user.getCompanyId());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_user.getCompanyId(), _group.getGroupId(), _user.getUserId());

		_commerceChannel1 = CommerceChannelLocalServiceUtil.addCommerceChannel(
			null, _group.getGroupId(), "Channel",
			CommerceChannelConstants.CHANNEL_TYPE_SITE, null,
			_commerceCurrency.getCode(), _serviceContext);

		_commerceChannel2 = CommerceChannelLocalServiceUtil.addCommerceChannel(
			null, _group.getGroupId(), "Channel Test",
			CommerceChannelConstants.CHANNEL_TYPE_SITE, null,
			_commerceCurrency.getCode(), _serviceContext);

		_commerceCatalog = CommerceCatalogLocalServiceUtil.addCommerceCatalog(
			null, RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			LocaleUtil.US.getDisplayLanguage(),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_cpDefinition = CPTestUtil.addCPDefinitionFromCatalog(
			_commerceCatalog.getGroupId(), SimpleCPTypeConstants.NAME, false,
			false);
	}

	@After
	public void tearDown() throws Exception {
		CommerceChannelRelLocalServiceUtil.deleteCommerceChannelRels(
			_commerceChannel1.getCommerceChannelId());
		CommerceChannelRelLocalServiceUtil.deleteCommerceChannelRels(
			_commerceChannel2.getCommerceChannelId());
	}

	@Test
	public void testAddProductVisibility() throws Exception {
		CommerceChannelRelLocalServiceUtil.addCommerceChannelRel(
			CPDefinition.class.getName(), _cpDefinition.getCPDefinitionId(),
			_commerceChannel1.getCommerceChannelId(), _serviceContext);

		CommerceChannelRelLocalServiceUtil.addCommerceChannelRel(
			CPDefinition.class.getName(), _cpDefinition.getCPDefinitionId(),
			_commerceChannel2.getCommerceChannelId(), _serviceContext);

		List<CommerceChannelRel> commerceChannelRels =
			CommerceChannelRelLocalServiceUtil.getCommerceChannelRels(
				CPDefinition.class.getName(), _cpDefinition.getCPDefinitionId(),
				"Channel Test", QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			commerceChannelRels.toString(), 2, commerceChannelRels.size());
		Assert.assertEquals(
			commerceChannelRels.toString(), 2,
			CommerceChannelRelLocalServiceUtil.getCommerceChannelRelsCount(
				CPDefinition.class.getName(), _cpDefinition.getCPDefinitionId(),
				"Channel Test"));
	}

	private static User _user;

	private CommerceCatalog _commerceCatalog;
	private CommerceChannel _commerceChannel1;
	private CommerceChannel _commerceChannel2;
	private CommerceCurrency _commerceCurrency;
	private CPDefinition _cpDefinition;
	private Group _group;
	private ServiceContext _serviceContext;

}