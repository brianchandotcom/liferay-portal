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
import com.liferay.commerce.product.service.CPOptionCategoryLocalService;
import com.liferay.headless.commerce.admin.catalog.v2.client.dto.v2_0.OptionCategory;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Stefano Motta
 */
@RunWith(Arquillian.class)
public class OptionCategoryResourceTest
	extends BaseOptionCategoryResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_cpOptionCategoryLocalService.deleteCPOptionCategories(
			testCompany.getCompanyId());
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"description", "key", "priority", "title"};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {"description", "key", "title"};
	}

	@Override
	protected OptionCategory randomOptionCategory() throws Exception {
		return new OptionCategory() {
			{
				description = LanguageUtils.getLanguageIdMap(
					RandomTestUtil.randomLocaleStringMap());
				id = RandomTestUtil.randomLong();
				key = StringUtil.toLowerCase(RandomTestUtil.randomString());
				priority = RandomTestUtil.randomDouble();
				title = LanguageUtils.getLanguageIdMap(
					RandomTestUtil.randomLocaleStringMap());
			}
		};
	}

	@Override
	protected OptionCategory testDeleteOptionCategory_addOptionCategory()
		throws Exception {

		return optionCategoryResource.postOptionCategory(
			randomOptionCategory());
	}

	@Override
	protected OptionCategory testGetOptionCategoriesPage_addOptionCategory(
			OptionCategory optionCategory)
		throws Exception {

		return optionCategoryResource.postOptionCategory(optionCategory);
	}

	@Override
	protected OptionCategory testGetOptionCategory_addOptionCategory()
		throws Exception {

		return optionCategoryResource.postOptionCategory(
			randomOptionCategory());
	}

	@Override
	protected OptionCategory testGraphQLOptionCategory_addOptionCategory()
		throws Exception {

		return optionCategoryResource.postOptionCategory(
			randomOptionCategory());
	}

	@Override
	protected OptionCategory testPatchOptionCategory_addOptionCategory()
		throws Exception {

		return optionCategoryResource.postOptionCategory(
			randomOptionCategory());
	}

	@Override
	protected OptionCategory testPostOptionCategory_addOptionCategory(
			OptionCategory optionCategory)
		throws Exception {

		return optionCategoryResource.postOptionCategory(optionCategory);
	}

	@Inject
	private CPOptionCategoryLocalService _cpOptionCategoryLocalService;

}