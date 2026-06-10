/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.subscription.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.constants.CommerceSubscriptionEntryConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.model.CommerceSubscriptionEntry;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.service.CommerceSubscriptionEntryLocalService;
import com.liferay.commerce.service.CommerceSubscriptionEntryService;
import com.liferay.commerce.subscription.CommerceSubscriptionEntryHelper;
import com.liferay.commerce.subscription.test.util.CommerceSubscriptionEntryTestUtil;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Stefano Motta
 */
@RunWith(Arquillian.class)
public class CommerceSubscriptionEntryServiceTest {

	@ClassRule
	@Rule
	public static AggregateTestRule aggregateTestRule = new AggregateTestRule(
		new LiferayIntegrationTestRule(),
		PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		Group group = GroupTestUtil.addGroup();

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			TestPropsValues.getCompanyId());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			group.getGroupId(), _commerceCurrency.getCode());

		_commerceSubscriptionEntry = _getCommerceSubscriptionEntry();

		_company = CompanyTestUtil.addCompany(true);
	}

	@Test
	public void testDeleteCommerceSubscriptionEntry() throws Exception {
		UserTestUtil.setUser(UserTestUtil.addCompanyAdminUser(_company));

		Assert.assertThrows(
			PrincipalException.class,
			() ->
				_commerceSubscriptionEntryService.
					deleteCommerceSubscriptionEntry(
						_commerceSubscriptionEntry.
							getCommerceSubscriptionEntryId()));

		UserTestUtil.setUser(TestPropsValues.getUser());

		_commerceSubscriptionEntryService.deleteCommerceSubscriptionEntry(
			_commerceSubscriptionEntry.getCommerceSubscriptionEntryId());

		_commerceSubscriptionEntry = _getCommerceSubscriptionEntry();
	}

	@Test
	public void testFetchCommerceSubscriptionEntry() throws Exception {
		UserTestUtil.setUser(UserTestUtil.addCompanyAdminUser(_company));

		Assert.assertThrows(
			PrincipalException.class,
			() ->
				_commerceSubscriptionEntryService.
					fetchCommerceSubscriptionEntry(
						_commerceSubscriptionEntry.
							getCommerceSubscriptionEntryId()));

		UserTestUtil.setUser(TestPropsValues.getUser());

		Assert.assertNotNull(
			_commerceSubscriptionEntryService.fetchCommerceSubscriptionEntry(
				_commerceSubscriptionEntry.getCommerceSubscriptionEntryId()));
	}

	@Test
	public void testGetCommerceSubscriptionEntries() throws Exception {
		UserTestUtil.setUser(UserTestUtil.addCompanyAdminUser(_company));

		Assert.assertThrows(
			PrincipalException.class,
			() ->
				_commerceSubscriptionEntryService.
					getCommerceSubscriptionEntries(
						TestPropsValues.getCompanyId(),
						_commerceChannel.getGroupId(),
						TestPropsValues.getUserId(), 0, 1, null));

		UserTestUtil.setUser(TestPropsValues.getUser());

		_commerceSubscriptionEntryService.getCommerceSubscriptionEntries(
			TestPropsValues.getCompanyId(), _commerceChannel.getGroupId(),
			TestPropsValues.getUserId(), 0, 1, null);
	}

	@Test
	public void testGetCommerceSubscriptionEntriesCount() throws Exception {
		UserTestUtil.setUser(UserTestUtil.addCompanyAdminUser(_company));

		Assert.assertThrows(
			PrincipalException.class,
			() ->
				_commerceSubscriptionEntryService.
					getCommerceSubscriptionEntriesCount(
						TestPropsValues.getCompanyId(),
						_commerceChannel.getGroupId(),
						TestPropsValues.getUserId()));

		UserTestUtil.setUser(TestPropsValues.getUser());

		_commerceSubscriptionEntryService.getCommerceSubscriptionEntriesCount(
			TestPropsValues.getCompanyId(), _commerceChannel.getGroupId(),
			TestPropsValues.getUserId());
	}

	@Test
	public void testSearchCommerceSubscriptionEntries() throws Exception {
		UserTestUtil.setUser(UserTestUtil.addCompanyAdminUser(_company));

		Assert.assertThrows(
			PrincipalException.class,
			() ->
				_commerceSubscriptionEntryService.
					searchCommerceSubscriptionEntries(
						TestPropsValues.getCompanyId(), null, null,
						StringPool.BLANK, 0, 10, null));

		UserTestUtil.setUser(TestPropsValues.getUser());

		_commerceSubscriptionEntryService.searchCommerceSubscriptionEntries(
			TestPropsValues.getCompanyId(), null, null, StringPool.BLANK, 0, 10,
			null);
	}

	@Test
	public void testUpdateCommerceSubscriptionEntry() throws Exception {
		UserTestUtil.setUser(UserTestUtil.addCompanyAdminUser(_company));

		Assert.assertThrows(
			PrincipalException.class,
			() ->
				_commerceSubscriptionEntryService.
					updateCommerceSubscriptionEntry(
						_commerceSubscriptionEntry.
							getCommerceSubscriptionEntryId(),
						0, null, null, 0,
						CommerceSubscriptionEntryConstants.
							SUBSCRIPTION_STATUS_INACTIVE,
						0, 0, 0, 0, 0, 0, null, null, 0,
						CommerceSubscriptionEntryConstants.
							SUBSCRIPTION_STATUS_INACTIVE,
						0, 0, 0, 0, 0));

		UserTestUtil.setUser(TestPropsValues.getUser());

		_commerceSubscriptionEntryService.updateCommerceSubscriptionEntry(
			_commerceSubscriptionEntry.getCommerceSubscriptionEntryId(), 0,
			null, null, 0,
			CommerceSubscriptionEntryConstants.SUBSCRIPTION_STATUS_INACTIVE, 0,
			0, 0, 0, 0, 0, null, null, 0,
			CommerceSubscriptionEntryConstants.SUBSCRIPTION_STATUS_INACTIVE, 0,
			0, 0, 0, 0);
	}

	private static CommerceSubscriptionEntry _getCommerceSubscriptionEntry()
		throws Exception {

		CommerceSubscriptionEntryTestUtil.setUpCommerceSubscriptionEntry(
			TestPropsValues.getUserId(), _commerceChannel.getGroupId(), 1,
			_commerceCurrency.getCommerceCurrencyId(),
			_commerceSubscriptionEntryHelper);

		List<CommerceSubscriptionEntry> commerceSubscriptionEntries =
			_commerceSubscriptionEntryLocalService.
				getCommerceSubscriptionEntries(
					TestPropsValues.getCompanyId(),
					_commerceChannel.getGroupId(), TestPropsValues.getUserId(),
					0, 1, null);

		return commerceSubscriptionEntries.get(0);
	}

	private static CommerceChannel _commerceChannel;
	private static CommerceCurrency _commerceCurrency;
	private static CommerceSubscriptionEntry _commerceSubscriptionEntry;

	@Inject
	private static CommerceSubscriptionEntryHelper
		_commerceSubscriptionEntryHelper;

	@Inject
	private static CommerceSubscriptionEntryLocalService
		_commerceSubscriptionEntryLocalService;

	private static Company _company;

	@Inject
	private CommerceSubscriptionEntryService _commerceSubscriptionEntryService;

}