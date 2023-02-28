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

package com.liferay.headless.commerce.admin.catalog.v2.resource.v2_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.service.CPOptionCategoryLocalService;
import com.liferay.commerce.product.service.CPSpecificationOptionLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.headless.commerce.admin.catalog.v2.client.dto.v2_0.ProductSpecification;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Stefano Motta
 */
@RunWith(Arquillian.class)
public class ProductSpecificationResourceTest
	extends BaseProductSpecificationResourceTestCase {

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

		_cpOptionCategory = _cpOptionCategoryLocalService.addCPOptionCategory(
			_user.getUserId(), RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomDouble(), RandomTestUtil.randomString(),
			_serviceContext);

		_cpSpecificationOption =
			_cpSpecificationOptionLocalService.addCPSpecificationOption(
				_user.getUserId(), _cpOptionCategory.getCPOptionCategoryId(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(), true,
				RandomTestUtil.randomString(), _serviceContext);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"optionCategoryId", "priority", "productId", "specificationId",
			"specificationKey"
		};
	}

	@Override
	protected ProductSpecification randomProductSpecification()
		throws Exception {

		return new ProductSpecification() {
			{
				label = LanguageUtils.getLanguageIdMap(
					RandomTestUtil.randomLocaleStringMap());
				optionCategoryId = _cpOptionCategory.getCPOptionCategoryId();
				priority = RandomTestUtil.randomDouble();
				productId = _cProduct.getCProductId();
				specificationId =
					_cpSpecificationOption.getCPSpecificationOptionId();
				specificationKey = _cpSpecificationOption.getKey();
				value = LanguageUtils.getLanguageIdMap(
					RandomTestUtil.randomLocaleStringMap());
			}
		};
	}

	@Override
	protected ProductSpecification
			testDeleteProductSpecification_addProductSpecification()
		throws Exception {

		return productSpecificationResource.postProductIdProductSpecification(
			_cProduct.getCProductId(), randomProductSpecification());
	}

	@Override
	protected ProductSpecification
			testGetProductIdProductSpecificationsPage_addProductSpecification(
				Long id, ProductSpecification productSpecification)
		throws Exception {

		return productSpecificationResource.postProductIdProductSpecification(
			_cProduct.getCProductId(), productSpecification);
	}

	@Override
	protected Long testGetProductIdProductSpecificationsPage_getId()
		throws Exception {

		return _cProduct.getCProductId();
	}

	@Override
	protected ProductSpecification
			testGetProductSpecification_addProductSpecification()
		throws Exception {

		return productSpecificationResource.postProductIdProductSpecification(
			_cProduct.getCProductId(), randomProductSpecification());
	}

	@Override
	protected ProductSpecification
			testGraphQLProductSpecification_addProductSpecification()
		throws Exception {

		return productSpecificationResource.postProductIdProductSpecification(
			_cProduct.getCProductId(), randomProductSpecification());
	}

	@Override
	protected ProductSpecification
			testPatchProductSpecification_addProductSpecification()
		throws Exception {

		return productSpecificationResource.postProductIdProductSpecification(
			_cProduct.getCProductId(), randomProductSpecification());
	}

	@Override
	protected ProductSpecification
			testPostProductIdProductSpecification_addProductSpecification(
				ProductSpecification productSpecification)
		throws Exception {

		return productSpecificationResource.postProductIdProductSpecification(
			_cProduct.getCProductId(), productSpecification);
	}

	@DeleteAfterTestRun
	private CPDefinition _cpDefinition;

	@DeleteAfterTestRun
	private CPInstance _cpInstance;

	@DeleteAfterTestRun
	private CPOptionCategory _cpOptionCategory;

	@Inject
	private CPOptionCategoryLocalService _cpOptionCategoryLocalService;

	@DeleteAfterTestRun
	private CProduct _cProduct;

	@DeleteAfterTestRun
	private CPSpecificationOption _cpSpecificationOption;

	@Inject
	private CPSpecificationOptionLocalService
		_cpSpecificationOptionLocalService;

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

}