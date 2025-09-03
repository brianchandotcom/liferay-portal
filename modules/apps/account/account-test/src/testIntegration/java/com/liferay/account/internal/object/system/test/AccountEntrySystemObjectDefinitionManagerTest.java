/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.object.system.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.system.SystemObjectDefinitionManager;
import com.liferay.object.system.SystemObjectDefinitionManagerRegistry;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lazy.referencing.LazyReferencingThreadLocal;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alberto Sousa
 */
@RunWith(Arquillian.class)
public class AccountEntrySystemObjectDefinitionManagerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_accountEntrySystemObjectDefinitionManager =
			_systemObjectDefinitionManagerRegistry.
				getSystemObjectDefinitionManager("AccountEntry");
	}

	@Test
	@TestInfo("LPD-62555")
	public void testGetOrAddEmptyBaseModel() throws Exception {

		// Lazy referencing disabled

		String originalName = PrincipalThreadLocal.getName();
		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		User user1 = TestPropsValues.getUser();

		_setUser(user1);

		String externalReferenceCode = RandomTestUtil.randomString();

		AssertUtils.assertFailure(
			PortalException.class,
			StringBundler.concat(
				"No AccountEntry exists with the key {",
				"externalReferenceCode=", externalReferenceCode, ", companyId=",
				TestPropsValues.getCompanyId(), "}"),
			() ->
				_accountEntrySystemObjectDefinitionManager.
					getOrAddEmptyBaseModel(externalReferenceCode, user1));

		// Lazy referencing enabled

		try (SafeCloseable safeCloseable =
				LazyReferencingThreadLocal.setEnabledWithSafeCloseable(true)) {

			// With permissions

			AccountEntry accountEntry =
				(AccountEntry)
					_accountEntrySystemObjectDefinitionManager.
						getOrAddEmptyBaseModel(
							RandomTestUtil.randomString(), user1);

			Assert.assertEquals(
				WorkflowConstants.STATUS_EMPTY, accountEntry.getStatus());

			// Without permissions

			User user2 = UserTestUtil.addUser();

			_setUser(user2);

			AssertUtils.assertFailure(
				PortalException.class,
				StringBundler.concat(
					"User ", user2.getUserId(), " must have ",
					PortletKeys.PORTAL, ",", PortletKeys.PORTAL,
					",ADD_ACCOUNT_ENTRY permission for null "),
				() ->
					_accountEntrySystemObjectDefinitionManager.
						getOrAddEmptyBaseModel(
							RandomTestUtil.randomString(), user2));

			// Without permissions, existing account entry

			AssertUtils.assertFailure(
				PortalException.class,
				StringBundler.concat(
					"User ", user2.getUserId(),
					" must have VIEW permission for ",
					AccountEntry.class.getName(), " ",
					accountEntry.getAccountEntryId()),
				() ->
					_accountEntrySystemObjectDefinitionManager.
						getOrAddEmptyBaseModel(
							accountEntry.getExternalReferenceCode(), user2));
		}

		PermissionThreadLocal.setPermissionChecker(originalPermissionChecker);

		PrincipalThreadLocal.setName(originalName);
	}

	private void _setUser(User user) throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		PrincipalThreadLocal.setName(user.getUserId());
	}

	private SystemObjectDefinitionManager
		_accountEntrySystemObjectDefinitionManager;

	@Inject
	private SystemObjectDefinitionManagerRegistry
		_systemObjectDefinitionManagerRegistry;

}