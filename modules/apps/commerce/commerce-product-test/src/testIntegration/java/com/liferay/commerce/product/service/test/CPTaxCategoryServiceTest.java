/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.product.constants.CPActionKeys;
import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.commerce.product.service.CPTaxCategoryLocalService;
import com.liferay.commerce.product.service.CPTaxCategoryService;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Tancredi Covioli
 */
@RunWith(Arquillian.class)
public class CPTaxCategoryServiceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);
		_user = UserTestUtil.addUser();

		_userLocalService.addRoleUser(_role.getRoleId(), _user);
	}

	@Test
	public void testAddCPTaxCategory() throws Exception {
		_runTest(
			CPActionKeys.ADD_COMMERCE_PRODUCT_TAX_CATEGORIES,
			"com.liferay.commerce.tax", false,
			() ->
				_cpTaxCategory = _cpTaxCategoryService.addCPTaxCategory(
					RandomTestUtil.randomString(),
					RandomTestUtil.randomLocaleStringMap(),
					RandomTestUtil.randomLocaleStringMap(),
					ServiceContextTestUtil.getServiceContext()));
	}

	@Test
	public void testCountCPTaxCategoriesByCompanyId() throws Exception {
		_runTest(
			CPActionKeys.VIEW_COMMERCE_PRODUCT_TAX_CATEGORIES,
			"com.liferay.commerce.tax", true,
			() -> _cpTaxCategoryService.countCPTaxCategoriesByCompanyId(
				TestPropsValues.getCompanyId(), _cpTaxCategory.getName()));
	}

	@Test
	public void testDeleteCPTaxCategory() throws Exception {
		_runTest(
			ActionKeys.DELETE, CPTaxCategory.class.getName(), true,
			() -> _cpTaxCategoryService.deleteCPTaxCategory(
				_cpTaxCategory.getCPTaxCategoryId()));
	}

	@Test
	public void testFindCPTaxCategoriesByCompanyId() throws Exception {
		_runTest(
			CPActionKeys.VIEW_COMMERCE_PRODUCT_TAX_CATEGORIES,
			"com.liferay.commerce.tax", true,
			() -> _cpTaxCategoryService.findCPTaxCategoriesByCompanyId(
				TestPropsValues.getCompanyId(), _cpTaxCategory.getName(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS));
	}

	@Test
	public void testGetCPTaxCategories() throws Exception {
		_runTest(
			CPActionKeys.VIEW_COMMERCE_PRODUCT_TAX_CATEGORIES,
			"com.liferay.commerce.tax", false,
			() -> _cpTaxCategoryService.getCPTaxCategories(
				TestPropsValues.getCompanyId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null));
	}

	@Test
	public void testGetCPTaxCategoriesCount() throws Exception {
		_runTest(
			CPActionKeys.VIEW_COMMERCE_PRODUCT_TAX_CATEGORIES,
			"com.liferay.commerce.tax", false,
			() -> _cpTaxCategoryService.getCPTaxCategoriesCount(
				TestPropsValues.getCompanyId()));
	}

	@Test
	public void testGetCPTaxCategory() throws Exception {
		_runTest(
			ActionKeys.VIEW, CPTaxCategory.class.getName(), true,
			() -> _cpTaxCategoryService.getCPTaxCategory(
				_cpTaxCategory.getCPTaxCategoryId()));
	}

	@Test
	public void testSearchCPTaxCategories() throws Exception {
		_runTest(
			CPActionKeys.VIEW_COMMERCE_PRODUCT_TAX_CATEGORIES,
			"com.liferay.commerce.tax", true,
			() -> _cpTaxCategoryService.searchCPTaxCategories(
				TestPropsValues.getCompanyId(), _cpTaxCategory.getName(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				SortFactoryUtil.create(
					Field.CREATE_DATE, Sort.STRING_TYPE, false)));
	}

	@Test
	public void testUpdateCPTaxCategory() throws Exception {
		_runTest(
			ActionKeys.UPDATE, CPTaxCategory.class.getName(), true,
			() -> _cpTaxCategoryService.updateCPTaxCategory(
				_cpTaxCategory.getExternalReferenceCode(),
				_cpTaxCategory.getCPTaxCategoryId(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap()));
	}

	private void _assertMessage(String actionKey, String message, long userId) {
		Assert.assertTrue(
			message.contains(
				StringBundler.concat(
					"User ", userId, " must have ", actionKey,
					" permission for")));
	}

	private void _runTest(
			String actionId, String actionName, boolean createTaxCategory,
			UnsafeRunnable<Exception> testRunnable)
		throws Exception {

		if (createTaxCategory) {
			_cpTaxCategory = _cpTaxCategoryLocalService.addCPTaxCategory(
				RandomTestUtil.randomString(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				ServiceContextTestUtil.getServiceContext());
		}

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			testRunnable.run();
			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			_assertMessage(
				actionId, principalException.getMessage(), _user.getUserId());
		}

		_resourcePermissionLocalService.addResourcePermission(
			TestPropsValues.getCompanyId(), actionName,
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()), _role.getRoleId(),
			actionId);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			testRunnable.run();
		}
	}

	@DeleteAfterTestRun
	private CPTaxCategory _cpTaxCategory;

	@Inject
	private CPTaxCategoryLocalService _cpTaxCategoryLocalService;

	@Inject
	private CPTaxCategoryService _cpTaxCategoryService;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	private Role _role;
	private User _user;

	@Inject
	private UserLocalService _userLocalService;

}