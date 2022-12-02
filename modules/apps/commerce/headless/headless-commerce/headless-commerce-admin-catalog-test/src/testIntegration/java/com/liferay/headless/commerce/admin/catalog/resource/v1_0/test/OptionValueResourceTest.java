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
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.service.CPOptionLocalService;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.OptionValue;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.test.rule.Inject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zoltán Takács
 */
@RunWith(Arquillian.class)
public class OptionValueResourceTest extends BaseOptionValueResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser(testCompany);

		_cpOption = _cpOptionLocalService.addCPOption(
			RandomTestUtil.randomString(), _user.getUserId(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), "select",
			RandomTestUtil.randomBoolean(), RandomTestUtil.randomBoolean(),
			RandomTestUtil.randomBoolean(), RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				testCompany.getCompanyId(), testGroup.getGroupId(),
				_user.getUserId()));
	}

	@Ignore
	@Override
	@Test
	public void testDeleteOptionValue() throws Exception {
		super.testDeleteOptionValue();
	}

	@Ignore
	@Override
	@Test
	public void testDeleteOptionValueByExternalReferenceCode()
		throws Exception {

		super.testDeleteOptionValueByExternalReferenceCode();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLDeleteOptionValue() throws Exception {
		super.testGraphQLDeleteOptionValue();
	}

	@Override
	@Test
	public void testPatchOptionValue() throws Exception {
		OptionValue postOptionValue =
			optionValueResource.postOptionIdOptionValue(
				_cpOption.getCPOptionId(), randomOptionValue());

		OptionValue randomPatchOptionValue = randomPatchOptionValue();

		randomPatchOptionValue.setExternalReferenceCode(
			postOptionValue.getExternalReferenceCode());

		optionValueResource.patchOptionValue(
			postOptionValue.getId(), randomPatchOptionValue);

		OptionValue expectedOptionValue = postOptionValue.clone();

		BeanTestUtil.copyProperties(
			randomPatchOptionValue, expectedOptionValue);

		OptionValue getOptionValue = optionValueResource.getOptionValue(
			postOptionValue.getId());

		assertEquals(expectedOptionValue, getOptionValue);
		assertValid(getOptionValue);
	}

	@Override
	@Test
	public void testPatchOptionValueByExternalReferenceCode() throws Exception {
		OptionValue postOptionValue =
			optionValueResource.postOptionByExternalReferenceCodeOptionValue(
				_cpOption.getExternalReferenceCode(), randomOptionValue());

		OptionValue randomPatchOptionValue = randomPatchOptionValue();

		randomPatchOptionValue.setExternalReferenceCode(
			postOptionValue.getExternalReferenceCode());

		optionValueResource.patchOptionValueByExternalReferenceCode(
			postOptionValue.getExternalReferenceCode(), randomPatchOptionValue);

		OptionValue expectedOptionValue = postOptionValue.clone();

		BeanTestUtil.copyProperties(
			randomPatchOptionValue, expectedOptionValue);

		OptionValue getOptionValue =
			optionValueResource.getOptionValueByExternalReferenceCode(
				postOptionValue.getExternalReferenceCode());

		assertEquals(expectedOptionValue, getOptionValue);
		assertValid(getOptionValue);
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"externalReferenceCode", "key", "name", "priority"
		};
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
	protected OptionValue randomOptionValue() throws Exception {
		return new OptionValue() {
			{
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				key = StringUtil.toLowerCase(RandomTestUtil.randomString());
				name = LanguageUtils.getLanguageIdMap(
					RandomTestUtil.randomLocaleStringMap());
				priority = RandomTestUtil.randomDouble();
			}
		};
	}

	@Override
	protected OptionValue randomPatchOptionValue() throws Exception {
		return new OptionValue() {
			{
				key = StringUtil.toLowerCase(RandomTestUtil.randomString());
				name = LanguageUtils.getLanguageIdMap(
					RandomTestUtil.randomLocaleStringMap());
				priority = RandomTestUtil.randomDouble();
			}
		};
	}

	@Override
	protected OptionValue testDeleteOptionValue_addOptionValue()
		throws Exception {

		return optionValueResource.postOptionIdOptionValue(
			_cpOption.getCPOptionId(), randomOptionValue());
	}

	@Override
	protected OptionValue
			testDeleteOptionValueByExternalReferenceCode_addOptionValue()
		throws Exception {

		return optionValueResource.postOptionByExternalReferenceCodeOptionValue(
			_cpOption.getExternalReferenceCode(), randomOptionValue());
	}

	@Override
	protected OptionValue
			testGetOptionByExternalReferenceCodeOptionValuesPage_addOptionValue(
				String externalReferenceCode, OptionValue optionValue)
		throws Exception {

		return optionValueResource.postOptionByExternalReferenceCodeOptionValue(
			externalReferenceCode, optionValue);
	}

	@Override
	protected String
			testGetOptionByExternalReferenceCodeOptionValuesPage_getExternalReferenceCode()
		throws Exception {

		return _cpOption.getExternalReferenceCode();
	}

	@Override
	protected OptionValue testGetOptionIdOptionValuesPage_addOptionValue(
			Long id, OptionValue optionValue)
		throws Exception {

		return optionValueResource.postOptionIdOptionValue(id, optionValue);
	}

	@Override
	protected Long testGetOptionIdOptionValuesPage_getId() throws Exception {
		return _cpOption.getCPOptionId();
	}

	@Override
	protected OptionValue testGetOptionValue_addOptionValue() throws Exception {
		return optionValueResource.postOptionIdOptionValue(
			_cpOption.getCPOptionId(), randomOptionValue());
	}

	@Override
	protected OptionValue
			testGetOptionValueByExternalReferenceCode_addOptionValue()
		throws Exception {

		return optionValueResource.postOptionByExternalReferenceCodeOptionValue(
			_cpOption.getExternalReferenceCode(), randomOptionValue());
	}

	@Override
	protected OptionValue testGraphQLOptionValue_addOptionValue()
		throws Exception {

		return optionValueResource.postOptionIdOptionValue(
			_cpOption.getCPOptionId(), randomOptionValue());
	}

	@Override
	protected OptionValue
			testPostOptionByExternalReferenceCodeOptionValue_addOptionValue(
				OptionValue optionValue)
		throws Exception {

		return optionValueResource.postOptionByExternalReferenceCodeOptionValue(
			_cpOption.getExternalReferenceCode(), optionValue);
	}

	@Override
	protected OptionValue testPostOptionIdOptionValue_addOptionValue(
			OptionValue optionValue)
		throws Exception {

		return optionValueResource.postOptionIdOptionValue(
			_cpOption.getCPOptionId(), optionValue);
	}

	@Inject
	private static CPOptionLocalService _cpOptionLocalService;

	@DeleteAfterTestRun
	private CPOption _cpOption;

	@DeleteAfterTestRun
	private User _user;

}