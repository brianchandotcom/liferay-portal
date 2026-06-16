/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.util;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.service.AccountEntryUserRelLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Joao Victor
 */
public class AccountEntryUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetUserAccountEntry() throws Exception {
		AccountEntryUserRel accountEntryUserRel = _mockAccountEntryUserRel(
			RandomTestUtil.randomString());

		try (MockedStatic<AccountEntryUserRelLocalServiceUtil> mockedStatic =
				Mockito.mockStatic(AccountEntryUserRelLocalServiceUtil.class)) {

			_mockGetAccountEntryUserRels(
				mockedStatic,
				List.of(
					_mockAccountEntryUserRel("L_AI_HUB"), accountEntryUserRel));

			Assert.assertSame(
				accountEntryUserRel.getAccountEntry(),
				AccountEntryUtil.getUserAccountEntry(_USER_ID));
		}
	}

	@Test
	public void testGetUserAccountEntryWhenAccountCountExceedsInvariant()
		throws Exception {

		try (MockedStatic<AccountEntryUserRelLocalServiceUtil> mockedStatic =
				Mockito.mockStatic(AccountEntryUserRelLocalServiceUtil.class)) {

			_mockGetAccountEntryUserRels(
				mockedStatic,
				List.of(
					_mockAccountEntryUserRel("L_AI_HUB"),
					_mockAccountEntryUserRel("L_SEO_STUDIO"),
					_mockAccountEntryUserRel(RandomTestUtil.randomString()),
					_mockAccountEntryUserRel(RandomTestUtil.randomString())));

			Assert.assertNull(AccountEntryUtil.getUserAccountEntry(_USER_ID));
		}
	}

	@Test
	public void testGetUserAccountEntryWhenAccountCountIsInvalid()
		throws Exception {

		try (MockedStatic<AccountEntryUserRelLocalServiceUtil> mockedStatic =
				Mockito.mockStatic(AccountEntryUserRelLocalServiceUtil.class)) {

			_mockGetAccountEntryUserRels(
				mockedStatic, List.of(_mockAccountEntryUserRel("L_AI_HUB")));

			Assert.assertNull(AccountEntryUtil.getUserAccountEntry(_USER_ID));
		}
	}

	@Test
	public void testGetUserAccountEntryWhenAIHubAccountIsMissing()
		throws Exception {

		try (MockedStatic<AccountEntryUserRelLocalServiceUtil> mockedStatic =
				Mockito.mockStatic(AccountEntryUserRelLocalServiceUtil.class)) {

			_mockGetAccountEntryUserRels(
				mockedStatic,
				List.of(
					_mockAccountEntryUserRel(RandomTestUtil.randomString()),
					_mockAccountEntryUserRel("L_SEO_STUDIO")));

			Assert.assertNull(AccountEntryUtil.getUserAccountEntry(_USER_ID));
		}
	}

	@Test
	public void testGetUserAccountEntryWhenSEOStudioAccountIsMissing()
		throws Exception {

		try (MockedStatic<AccountEntryUserRelLocalServiceUtil> mockedStatic =
				Mockito.mockStatic(AccountEntryUserRelLocalServiceUtil.class)) {

			_mockGetAccountEntryUserRels(
				mockedStatic,
				List.of(
					_mockAccountEntryUserRel("L_AI_HUB"),
					_mockAccountEntryUserRel(RandomTestUtil.randomString()),
					_mockAccountEntryUserRel(RandomTestUtil.randomString())));

			Assert.assertNull(AccountEntryUtil.getUserAccountEntry(_USER_ID));
		}
	}

	@Test
	public void testGetUserAccountEntryWhenSEOStudioAccountIsPresent()
		throws Exception {

		AccountEntryUserRel accountEntryUserRel = _mockAccountEntryUserRel(
			RandomTestUtil.randomString());

		try (MockedStatic<AccountEntryUserRelLocalServiceUtil> mockedStatic =
				Mockito.mockStatic(AccountEntryUserRelLocalServiceUtil.class)) {

			_mockGetAccountEntryUserRels(
				mockedStatic,
				List.of(
					_mockAccountEntryUserRel("L_AI_HUB"), accountEntryUserRel,
					_mockAccountEntryUserRel("L_SEO_STUDIO")));

			Assert.assertSame(
				accountEntryUserRel.getAccountEntry(),
				AccountEntryUtil.getUserAccountEntry(_USER_ID));
		}
	}

	private AccountEntryUserRel _mockAccountEntryUserRel(
			String externalReferenceCode)
		throws PortalException {

		AccountEntry accountEntry = Mockito.mock(AccountEntry.class);

		Mockito.when(
			accountEntry.getExternalReferenceCode()
		).thenReturn(
			externalReferenceCode
		);

		AccountEntryUserRel accountEntryUserRel = Mockito.mock(
			AccountEntryUserRel.class);

		Mockito.when(
			accountEntryUserRel.getAccountEntry()
		).thenReturn(
			accountEntry
		);

		return accountEntryUserRel;
	}

	private void _mockGetAccountEntryUserRels(
		MockedStatic<AccountEntryUserRelLocalServiceUtil> mockedStatic,
		List<AccountEntryUserRel> accountEntryUserRels) {

		mockedStatic.when(
			() ->
				AccountEntryUserRelLocalServiceUtil.
					getAccountEntryUserRelsByAccountUserId(_USER_ID)
		).thenReturn(
			accountEntryUserRels
		);
	}

	private static final long _USER_ID = 1;

}