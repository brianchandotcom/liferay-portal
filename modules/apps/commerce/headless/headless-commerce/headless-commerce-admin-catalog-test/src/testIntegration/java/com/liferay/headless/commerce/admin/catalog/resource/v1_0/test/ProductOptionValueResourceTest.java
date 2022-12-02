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

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.ProductOptionValue;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.test.rule.Inject;

import java.math.BigDecimal;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Zoltán Takács
 */
@RunWith(Arquillian.class)
public class ProductOptionValueResourceTest
	extends BaseProductOptionValueResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_cpInstance = CPTestUtil.addCPInstanceWithRandomSku(
			testGroup.getGroupId(), BigDecimal.TEN);

		_cpDefinition = _cpInstance.getCPDefinition();

		_cpOption = CPTestUtil.addCPOption(testGroup.getGroupId(), false);

		_user = UserTestUtil.addUser(testCompany);

		_cpDefinitionOptionRel =
			_cpDefinitionOptionRelLocalService.addCPDefinitionOptionRel(
				_cpDefinition.getCPDefinitionId(), _cpOption.getCPOptionId(),
				ServiceContextTestUtil.getServiceContext(
					testCompany.getCompanyId(), testGroup.getGroupId(),
					_user.getUserId()));
	}

	@Override
	protected Collection<EntityField> getEntityFields() throws Exception {
		try {
			return super.getEntityFields();
		}
		catch (NullPointerException nullPointerException) {
			Map<String, EntityField> entityFieldsMap = new HashMap<>();

			return entityFieldsMap.values();
		}
	}

	@Override
	protected ProductOptionValue randomProductOptionValue() throws Exception {
		return new ProductOptionValue() {
			{
				id = RandomTestUtil.randomLong();
				key = StringUtil.toLowerCase(RandomTestUtil.randomString());
				name = LanguageUtils.getLanguageIdMap(
					RandomTestUtil.randomLocaleStringMap());
				priority = RandomTestUtil.randomDouble();
			}
		};
	}

	protected ProductOptionValue
			testGetProductOptionIdProductOptionValuesPage_addProductOptionValue(
				Long id, ProductOptionValue productOptionValue)
		throws Exception {

		return productOptionValueResource.postProductOptionIdProductOptionValue(
			_cpDefinitionOptionRel.getCPDefinitionOptionRelId(),
			productOptionValue);
	}

	@Override
	protected Long testGetProductOptionIdProductOptionValuesPage_getId()
		throws Exception {

		return _cpDefinitionOptionRel.getCPDefinitionOptionRelId();
	}

	@Override
	protected ProductOptionValue
			testPostProductOptionIdProductOptionValue_addProductOptionValue(
				ProductOptionValue productOptionValue)
		throws Exception {

		return productOptionValueResource.postProductOptionIdProductOptionValue(
			_cpDefinitionOptionRel.getCPDefinitionOptionRelId(),
			productOptionValue);
	}

	@Inject
	private static CPDefinitionOptionRelLocalService
		_cpDefinitionOptionRelLocalService;

	@DeleteAfterTestRun
	private CPDefinition _cpDefinition;

	@DeleteAfterTestRun
	private CPDefinitionOptionRel _cpDefinitionOptionRel;

	@DeleteAfterTestRun
	private CPInstance _cpInstance;

	@DeleteAfterTestRun
	private CPOption _cpOption;

	@DeleteAfterTestRun
	private User _user;

}