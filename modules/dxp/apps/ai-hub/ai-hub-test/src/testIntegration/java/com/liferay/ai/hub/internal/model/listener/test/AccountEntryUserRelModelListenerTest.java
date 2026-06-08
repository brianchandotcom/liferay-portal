/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.model.listener.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.constants.AccountWebKeys;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.initializer.SiteInitializerRegistry;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Stefano Motta
 */
@FeatureFlag("LPD-62272")
@RunWith(Arquillian.class)
public class AccountEntryUserRelModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testOnAfterCreate() throws Exception {
		ServiceContextThreadLocal.pushServiceContext(
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId()));

		AccountEntry accountEntry1 = _addAccountEntry();
		User user = UserTestUtil.addUser();

		_accountEntryUserRelLocalService.addAccountEntryUserRels(
			accountEntry1.getAccountEntryId(), new long[] {user.getUserId()});

		Assert.assertEquals(
			0,
			_getCurrentAccountEntryId(
				TestPropsValues.getGroupId(), user.getUserId()));

		ServiceContextThreadLocal.popServiceContext();

		Group group = GroupTestUtil.addGroup();

		ServiceContextThreadLocal.pushServiceContext(
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId()));

		SiteInitializer siteInitializer =
			_siteInitializerRegistry.getSiteInitializer(
				"com.liferay.ai.hub.site.initializer");

		siteInitializer.initialize(group.getGroupId());

		AccountEntry accountEntry2 = _addAccountEntry();

		_accountEntryUserRelLocalService.addAccountEntryUserRels(
			accountEntry2.getAccountEntryId(), new long[] {user.getUserId()});

		Assert.assertTrue(
			_accountEntryUserRelLocalService.hasAccountEntryUserRel(
				accountEntry2.getAccountEntryId(), user.getUserId()));

		AccountEntry aiHubAccountEntry =
			_accountEntryLocalService.fetchAccountEntryByExternalReferenceCode(
				"L_AI_HUB", TestPropsValues.getCompanyId());

		Assert.assertTrue(
			_accountEntryUserRelLocalService.hasAccountEntryUserRel(
				aiHubAccountEntry.getAccountEntryId(), user.getUserId()));

		Assert.assertEquals(
			0,
			_getCurrentAccountEntryId(
				TestPropsValues.getGroupId(), user.getUserId()));
		Assert.assertEquals(
			accountEntry2.getAccountEntryId(),
			_getCurrentAccountEntryId(group.getGroupId(), user.getUserId()));

		AccountEntry accountEntry3 = _addAccountEntry();

		_accountEntryUserRelLocalService.addAccountEntryUserRels(
			accountEntry3.getAccountEntryId(), new long[] {user.getUserId()});

		Assert.assertEquals(
			accountEntry3.getAccountEntryId(),
			_getCurrentAccountEntryId(group.getGroupId(), user.getUserId()));
	}

	private AccountEntry _addAccountEntry() throws Exception {
		return _accountEntryLocalService.addAccountEntry(
			null, TestPropsValues.getUserId(),
			AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT,
			RandomTestUtil.randomString(), null, null, null, null, null,
			AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext());
	}

	private long _getCurrentAccountEntryId(long groupId, long userId) {
		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(userId, true);

		return GetterUtil.getLong(
			portalPreferences.getValue(
				AccountEntry.class.getName(),
				AccountWebKeys.CURRENT_ACCOUNT_ENTRY_ID + groupId));
	}

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Inject
	private SiteInitializerRegistry _siteInitializerRegistry;

}