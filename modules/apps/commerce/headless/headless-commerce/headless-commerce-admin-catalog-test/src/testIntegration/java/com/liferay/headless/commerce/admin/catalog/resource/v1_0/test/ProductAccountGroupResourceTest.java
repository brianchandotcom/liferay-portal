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

package com.liferay.headless.commerce.admin.catalog.resource.v1_0.test;

import com.liferay.account.model.AccountGroup;
import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.model.CommerceAccountGroup;
import com.liferay.commerce.account.model.CommerceAccountGroupRel;
import com.liferay.commerce.account.service.CommerceAccountGroupRelLocalService;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.ProductAccountGroup;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Zoltán Takács
 */
@RunWith(Arquillian.class)
public class ProductAccountGroupResourceTest
	extends BaseProductAccountGroupResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_cpInstance = CPTestUtil.addCPInstanceWithRandomSku(
			testGroup.getGroupId(), BigDecimal.TEN);

		_cpDefinition = _cpInstance.getCPDefinition();

		_cProduct = _cpDefinition.getCProduct();

		_user = UserTestUtil.addUser(testCompany);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testCompany.getCompanyId(), testGroup.getGroupId(),
			_user.getUserId());
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"name"};
	}

	protected ProductAccountGroup randomProductAccountGroup() throws Exception {
		AccountGroup accountGroup = _accountGroupLocalService.addAccountGroup(
			_user.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		_accountGroups.add(accountGroup);

		return new ProductAccountGroup() {
			{
				accountGroupId = accountGroup.getAccountGroupId();
				externalReferenceCode = accountGroup.getExternalReferenceCode();
				id = RandomTestUtil.randomLong();
				name = accountGroup.getName();
			}
		};
	}

	@Override
	protected ProductAccountGroup
			testDeleteProductAccountGroup_addProductAccountGroup()
		throws Exception {

		return _addCommerceAccountGroupRel(randomProductAccountGroup());
	}

	@Override
	protected ProductAccountGroup
			testGetProductAccountGroup_addProductAccountGroup()
		throws Exception {

		return _addCommerceAccountGroupRel(randomProductAccountGroup());
	}

	@Override
	protected ProductAccountGroup
			testGetProductByExternalReferenceCodeProductAccountGroupsPage_addProductAccountGroup(
				String externalReferenceCode,
				ProductAccountGroup productAccountGroup)
		throws Exception {

		return _addCommerceAccountGroupRel(productAccountGroup);
	}

	@Override
	protected String
			testGetProductByExternalReferenceCodeProductAccountGroupsPage_getExternalReferenceCode()
		throws Exception {

		return _cProduct.getExternalReferenceCode();
	}

	@Override
	protected ProductAccountGroup
			testGetProductIdProductAccountGroupsPage_addProductAccountGroup(
				Long id, ProductAccountGroup productAccountGroup)
		throws Exception {

		return _addCommerceAccountGroupRel(productAccountGroup);
	}

	@Override
	protected Long testGetProductIdProductAccountGroupsPage_getId()
		throws Exception {

		return _cProduct.getCProductId();
	}

	@Override
	protected ProductAccountGroup
			testGraphQLProductAccountGroup_addProductAccountGroup()
		throws Exception {

		return _addCommerceAccountGroupRel(randomProductAccountGroup());
	}

	private ProductAccountGroup _addCommerceAccountGroupRel(
			ProductAccountGroup productAccountGroup)
		throws Exception {

		CommerceAccountGroupRel commerceAccountGroupRel =
			_commerceAccountGroupRelLocalService.addCommerceAccountGroupRel(
				CPDefinition.class.getName(), _cpDefinition.getCPDefinitionId(),
				productAccountGroup.getAccountGroupId(), _serviceContext);

		CommerceAccountGroup commerceAccountGroup =
			commerceAccountGroupRel.getCommerceAccountGroup();

		return new ProductAccountGroup() {
			{
				accountGroupId =
					commerceAccountGroupRel.getCommerceAccountGroupId();
				externalReferenceCode =
					commerceAccountGroup.getExternalReferenceCode();
				id = commerceAccountGroupRel.getCommerceAccountGroupRelId();
				name = commerceAccountGroup.getName();
			}
		};
	}

	@Inject
	private static AccountGroupLocalService _accountGroupLocalService;

	@Inject
	private static CommerceAccountGroupRelLocalService
		_commerceAccountGroupRelLocalService;

	@DeleteAfterTestRun
	private final List<AccountGroup> _accountGroups = new ArrayList<>();

	@DeleteAfterTestRun
	private CPDefinition _cpDefinition;

	@DeleteAfterTestRun
	private CPInstance _cpInstance;

	@DeleteAfterTestRun
	private CProduct _cProduct;

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

}