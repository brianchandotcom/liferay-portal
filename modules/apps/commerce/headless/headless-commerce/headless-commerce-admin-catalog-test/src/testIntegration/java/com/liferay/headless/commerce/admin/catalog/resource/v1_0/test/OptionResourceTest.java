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
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CPOptionLocalService;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.Option;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zoltán Takács
 */
@RunWith(Arquillian.class)
public class OptionResourceTest extends BaseOptionResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser(testCompany);

		_commerceCatalog = _commerceCatalogLocalService.addCommerceCatalog(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), _user.getLanguageId(), false,
			ServiceContextTestUtil.getServiceContext(
				testCompany.getCompanyId(), testGroup.getGroupId(),
				_user.getUserId()));

		_cpOptionLocalService.deleteCPOptions(testCompany.getCompanyId());
	}

	@Ignore
	@Override
	@Test
	public void testDeleteOption() throws Exception {
		super.testDeleteOption();
	}

	@Ignore
	@Override
	@Test
	public void testDeleteOptionByExternalReferenceCode() throws Exception {
		super.testDeleteOptionByExternalReferenceCode();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLDeleteOption() throws Exception {
		super.testGraphQLDeleteOption();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetOptionByExternalReferenceCodeNotFound()
		throws Exception {

		super.testGraphQLGetOptionByExternalReferenceCodeNotFound();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetOptionNotFound() throws Exception {
		super.testGraphQLGetOptionNotFound();
	}

	@Override
	@Test
	public void testPatchOption() throws Exception {
		Option postOption = optionResource.postOption(randomOption());

		Option randomOption = randomOption();

		optionResource.patchOption(postOption.getId(), randomOption);

		Option expectedOption = postOption.clone();

		BeanTestUtil.copyProperties(randomOption, expectedOption);

		Option getOption = optionResource.getOption(postOption.getId());

		assertEquals(expectedOption, getOption);
		assertValid(getOption);
	}

	@Override
	@Test
	public void testPatchOptionByExternalReferenceCode() throws Exception {
		Option postOption = optionResource.postOption(randomOption());

		Option randomOption = randomOption();

		optionResource.patchOptionByExternalReferenceCode(
			postOption.getExternalReferenceCode(), randomOption);

		Option expectedOption = postOption.clone();

		BeanTestUtil.copyProperties(randomOption, expectedOption);

		Option getOption = optionResource.getOption(postOption.getId());

		assertEquals(expectedOption, getOption);
		assertValid(getOption);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"facetable", "required", "skuContributor"};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {"description", "fieldType", "key", "name"};
	}

	@Override
	protected Option randomOption() throws Exception {
		return new Option() {
			{
				catalogId = _commerceCatalog.getCommerceCatalogId();
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				facetable = RandomTestUtil.randomBoolean();
				fieldType = FieldType.TEXT;
				id = RandomTestUtil.randomLong();
				key = StringUtil.toLowerCase(RandomTestUtil.randomString());
				name = LanguageUtils.getLanguageIdMap(
					RandomTestUtil.randomLocaleStringMap());
				priority = RandomTestUtil.randomDouble();
				required = RandomTestUtil.randomBoolean();
				skuContributor = false;
			}
		};
	}

	@Override
	protected Option testDeleteOption_addOption() throws Exception {
		return optionResource.postOption(randomOption());
	}

	@Override
	protected Option testDeleteOptionByExternalReferenceCode_addOption()
		throws Exception {

		return optionResource.postOption(randomOption());
	}

	@Override
	protected Option testGetOption_addOption() throws Exception {
		return optionResource.postOption(randomOption());
	}

	@Override
	protected Option testGetOptionByExternalReferenceCode_addOption()
		throws Exception {

		return optionResource.postOption(randomOption());
	}

	@Override
	protected Option testGetOptionsPage_addOption(Option option)
		throws Exception {

		return optionResource.postOption(option);
	}

	@Override
	protected Option testGraphQLOption_addOption() throws Exception {
		return optionResource.postOption(randomOption());
	}

	@Override
	protected Option testPostOption_addOption(Option option) throws Exception {
		return optionResource.postOption(option);
	}

	@Inject
	private static CommerceCatalogLocalService _commerceCatalogLocalService;

	@Inject
	private static CPOptionLocalService _cpOptionLocalService;

	@DeleteAfterTestRun
	private CommerceCatalog _commerceCatalog;

	@DeleteAfterTestRun
	private User _user;

}