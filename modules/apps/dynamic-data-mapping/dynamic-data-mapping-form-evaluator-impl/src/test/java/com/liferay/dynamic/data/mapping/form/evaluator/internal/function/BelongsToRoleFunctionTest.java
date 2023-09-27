/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Leonardo Barros
 */
public class BelongsToRoleFunctionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_belongsToRoleFunction = new BelongsToRoleFunction(
			_roleLocalService, _userGroupRoleLocalService, _userLocalService);
	}

	@Test
	public void testCatchPortalException() throws Exception {
		Mockito.when(
			_roleLocalService.fetchRole(1, "test")
		).thenReturn(
			_role
		);

		Mockito.when(
			_role.getType()
		).thenReturn(
			RoleConstants.TYPE_REGULAR
		);

		Mockito.when(
			_userLocalService.hasRoleUser(1, "test", 1, true)
		).thenThrow(
			new PortalException()
		);

		DefaultDDMExpressionParameterAccessor ddmExpressionParameterAccessor =
			new DefaultDDMExpressionParameterAccessor();

		ddmExpressionParameterAccessor.setGetCompanyIdSupplier(() -> 1L);
		ddmExpressionParameterAccessor.setGetUserIdSupplier(() -> 1L);

		_belongsToRoleFunction.setDDMExpressionParameterAccessor(
			ddmExpressionParameterAccessor);

		Assert.assertFalse(_belongsToRoleFunction.apply(new String[] {"test"}));
	}

	@Test
	public void testGuestRole() {
		Mockito.when(
			_roleLocalService.fetchRole(1, "Guest")
		).thenReturn(
			_role
		);

		DefaultDDMExpressionParameterAccessor ddmExpressionParameterAccessor =
			new DefaultDDMExpressionParameterAccessor();

		ddmExpressionParameterAccessor.setGetCompanyIdSupplier(() -> 1L);

		_belongsToRoleFunction.setDDMExpressionParameterAccessor(
			ddmExpressionParameterAccessor);

		Assert.assertTrue(_belongsToRoleFunction.apply(new String[] {"Guest"}));
	}

	@Test
	public void testNotGuestRole() {
		Mockito.when(
			_roleLocalService.fetchRole(2, "test")
		).thenReturn(
			_role
		);

		DefaultDDMExpressionParameterAccessor ddmExpressionParameterAccessor =
			new DefaultDDMExpressionParameterAccessor();

		ddmExpressionParameterAccessor.setGetCompanyIdSupplier(() -> 2L);

		_belongsToRoleFunction.setDDMExpressionParameterAccessor(
			ddmExpressionParameterAccessor);

		Assert.assertFalse(_belongsToRoleFunction.apply(new String[] {"test"}));
	}

	@Test
	public void testNullObserver() {
		Assert.assertFalse(_belongsToRoleFunction.apply(new String[] {"test"}));
	}

	@Test
	public void testRegularRoleFalse() throws Exception {
		boolean result = _callTestRegularRole(false);

		Assert.assertFalse(result);
	}

	@Test
	public void testRegularRoleTrue() throws Exception {
		boolean result = _callTestRegularRole(true);

		Assert.assertTrue(result);
	}

	@Test
	public void testRoleNotFound() {
		Mockito.when(
			_roleLocalService.fetchRole(1, "test")
		).thenReturn(
			null
		);

		DefaultDDMExpressionParameterAccessor ddmExpressionParameterAccessor =
			new DefaultDDMExpressionParameterAccessor();

		ddmExpressionParameterAccessor.setGetCompanyIdSupplier(() -> 1L);

		_belongsToRoleFunction.setDDMExpressionParameterAccessor(
			ddmExpressionParameterAccessor);

		Assert.assertFalse(_belongsToRoleFunction.apply(new String[] {"test"}));
	}

	@Test
	public void testUserGroupRoleFalse() throws Exception {
		boolean result = _callTestUserGroupRole(false);

		Assert.assertFalse(result);
	}

	@Test
	public void testUserGroupRoleTrue() throws Exception {
		boolean result = _callTestUserGroupRole(true);

		Assert.assertTrue(result);
	}

	private boolean _callTestRegularRole(boolean returnValue) throws Exception {
		Mockito.when(
			_roleLocalService.fetchRole(1, "test")
		).thenReturn(
			_role
		);

		Mockito.when(
			_role.getType()
		).thenReturn(
			RoleConstants.TYPE_REGULAR
		);

		Mockito.when(
			_userLocalService.hasRoleUser(1, "test", 1, true)
		).thenReturn(
			returnValue
		);

		DefaultDDMExpressionParameterAccessor ddmExpressionParameterAccessor =
			new DefaultDDMExpressionParameterAccessor();

		ddmExpressionParameterAccessor.setGetCompanyIdSupplier(() -> 1L);
		ddmExpressionParameterAccessor.setGetUserIdSupplier(() -> 1L);

		_belongsToRoleFunction.setDDMExpressionParameterAccessor(
			ddmExpressionParameterAccessor);

		return _belongsToRoleFunction.apply(new String[] {"test"});
	}

	private boolean _callTestUserGroupRole(boolean returnValue)
		throws Exception {

		Mockito.when(
			_roleLocalService.fetchRole(1, "test")
		).thenReturn(
			_role
		);

		Mockito.when(
			_role.getType()
		).thenReturn(
			RoleConstants.TYPE_ORGANIZATION
		);

		Mockito.when(
			_userGroupRoleLocalService.hasUserGroupRole(1, 1, "test", true)
		).thenReturn(
			returnValue
		);

		DefaultDDMExpressionParameterAccessor ddmExpressionParameterAccessor =
			new DefaultDDMExpressionParameterAccessor();

		ddmExpressionParameterAccessor.setGetCompanyIdSupplier(() -> 1L);
		ddmExpressionParameterAccessor.setGetGroupIdSupplier(() -> 1L);
		ddmExpressionParameterAccessor.setGetUserIdSupplier(() -> 1L);

		_belongsToRoleFunction.setDDMExpressionParameterAccessor(
			ddmExpressionParameterAccessor);

		return _belongsToRoleFunction.apply(new String[] {"test"});
	}

	private BelongsToRoleFunction _belongsToRoleFunction;
	private final Role _role = Mockito.mock(Role.class);
	private final RoleLocalService _roleLocalService = Mockito.mock(
		RoleLocalService.class);
	private final UserGroupRoleLocalService _userGroupRoleLocalService =
		Mockito.mock(UserGroupRoleLocalService.class);
	private final UserLocalService _userLocalService = Mockito.mock(
		UserLocalService.class);

}