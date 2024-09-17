/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.OptionCategory;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zoltán Takács
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class OptionCategoryResourceTest
	extends BaseOptionCategoryResourceTestCase {

	@Ignore
	@Override
	@Test
	public void testGraphQLDeleteOptionCategory() throws Exception {
		super.testGraphQLDeleteOptionCategory();
	}

	@Ignore
	@Override
	@Test
	public void testPatchOptionCategory() throws Exception {
		super.testPatchOptionCategory();
	}

	@Ignore
	@Override
	@Test
	public void testPatchOptionCategoryByExternalReferenceCode()
		throws Exception {

		super.testPatchOptionCategoryByExternalReferenceCode();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"externalReferenceCode", "key"};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {"title"};
	}

	@Override
	protected OptionCategory randomOptionCategory() throws Exception {
		return new OptionCategory() {
			{
				description = LanguageUtils.getLanguageIdMap(
					RandomTestUtil.randomLocaleStringMap());
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
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
	protected OptionCategory
			testDeleteOptionCategoryByExternalReferenceCode_addOptionCategory()
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
	protected OptionCategory
			testGetOptionCategoryByExternalReferenceCode_addOptionCategory()
		throws Exception {

		return optionCategoryResource.postOptionCategory(
			randomOptionCategory());
	}

	@Override
	protected OptionCategory testGraphQLDeleteOptionCategory_addOptionCategory()
		throws Exception {

		return optionCategoryResource.postOptionCategory(
			randomOptionCategory());
	}

	@Override
	protected OptionCategory
			testGraphQLGetOptionCategoryByExternalReferenceCode_addOptionCategory()
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
	protected OptionCategory testPostOptionCategory_addOptionCategory(
			OptionCategory optionCategory)
		throws Exception {

		return optionCategoryResource.postOptionCategory(optionCategory);
	}

	@Override
	protected OptionCategory
			testPutOptionCategoryByExternalReferenceCode_addOptionCategory()
		throws Exception {

		return optionCategoryResource.postOptionCategory(
			randomOptionCategory());
	}

}