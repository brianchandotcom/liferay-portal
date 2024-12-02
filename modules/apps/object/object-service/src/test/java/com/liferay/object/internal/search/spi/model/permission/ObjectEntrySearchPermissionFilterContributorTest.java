/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.search.spi.model.permission;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.configuration.SearchPermissionCheckerConfiguration;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Gustavo Lima
 */
public class ObjectEntrySearchPermissionFilterContributorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testContribute() throws PortalException {
		long activeAccountEntryId = RandomTestUtil.randomLong();
		long inactiveAccountEntryId = RandomTestUtil.randomLong();
		long userId = RandomTestUtil.randomLong();

		ObjectEntrySearchPermissionFilterContributor
			objectEntrySearchPermissionFilterContributor =
				_setUpObjectEntrySearchPermissionFilterContributor(
					activeAccountEntryId, inactiveAccountEntryId, userId);

		BooleanFilter booleanFilter = new BooleanFilter();

		objectEntrySearchPermissionFilterContributor.contribute(
			booleanFilter, RandomTestUtil.randomLong(), new long[0], userId,
			_getPermissionChecker(userId), ObjectDefinition.class.getName());

		String booleanFilterString = booleanFilter.toString();

		Assert.assertFalse(
			booleanFilterString,
			booleanFilterString.contains(
				"accountEntryRestrictedObjectFieldValue=[" +
					inactiveAccountEntryId + "]"));
		Assert.assertTrue(
			booleanFilterString,
			booleanFilterString.contains(
				"accountEntryRestrictedObjectFieldValue=[" +
					activeAccountEntryId + "]"));
	}

	private AccountEntryUserRel _createAccountEntryUserRel(
			long accountEntryId, boolean inactive)
		throws PortalException {

		AccountEntryUserRel accountEntryUserRel = Mockito.mock(
			AccountEntryUserRel.class);

		AccountEntry accountEntry = Mockito.mock(AccountEntry.class);

		Mockito.when(
			accountEntry.getAccountEntryId()
		).thenReturn(
			accountEntryId
		);

		Mockito.when(
			accountEntry.isInactive()
		).thenReturn(
			inactive
		);

		Mockito.when(
			accountEntryUserRel.getAccountEntry()
		).thenReturn(
			accountEntry
		);

		return accountEntryUserRel;
	}

	private PermissionChecker _getPermissionChecker(long userId) {
		PermissionChecker permissionChecker = Mockito.mock(
			PermissionChecker.class);

		Mockito.when(
			permissionChecker.getUserId()
		).thenReturn(
			userId
		);

		return permissionChecker;
	}

	private void _setUpAccountEntryUserRelLocalService(
			long activeAccountEntryId, long inactiveAccountEntryId,
			ObjectEntrySearchPermissionFilterContributor
				objectEntrySearchPermissionFilterContributor,
			long userId)
		throws PortalException {

		AccountEntryUserRelLocalService accountEntryUserRelLocalService =
			Mockito.mock(AccountEntryUserRelLocalService.class);

		ReflectionTestUtil.setFieldValue(
			objectEntrySearchPermissionFilterContributor,
			"_accountEntryUserRelLocalService",
			accountEntryUserRelLocalService);

		List<AccountEntryUserRel> accountEntryUserRels = Arrays.asList(
			_createAccountEntryUserRel(activeAccountEntryId, false),
			_createAccountEntryUserRel(inactiveAccountEntryId, true));

		Mockito.when(
			accountEntryUserRelLocalService.
				getAccountEntryUserRelsByAccountUserId(userId)
		).thenReturn(
			accountEntryUserRels
		);
	}

	private ObjectEntrySearchPermissionFilterContributor
			_setUpObjectEntrySearchPermissionFilterContributor(
				long activeAccountEntryId, long inactiveAccountEntryId,
				long userId)
		throws PortalException {

		ObjectEntrySearchPermissionFilterContributor
			objectEntrySearchPermissionFilterContributor =
				new ObjectEntrySearchPermissionFilterContributor();

		_setUpAccountEntryUserRelLocalService(
			activeAccountEntryId, inactiveAccountEntryId,
			objectEntrySearchPermissionFilterContributor, userId);
		_setUpOrganizationLocalService(
			objectEntrySearchPermissionFilterContributor, userId);
		_setUpSearchPermissionCheckerConfiguration(
			objectEntrySearchPermissionFilterContributor);

		return objectEntrySearchPermissionFilterContributor;
	}

	private void _setUpOrganizationLocalService(
		ObjectEntrySearchPermissionFilterContributor
			objectEntrySearchPermissionFilterContributor,
		long userId) {

		OrganizationLocalService organizationLocalService = Mockito.mock(
			OrganizationLocalService.class);

		ReflectionTestUtil.setFieldValue(
			objectEntrySearchPermissionFilterContributor,
			"_organizationLocalService", organizationLocalService);

		Mockito.when(
			organizationLocalService.getUserOrganizations(userId)
		).thenReturn(
			Collections.emptyList()
		);
	}

	private void _setUpSearchPermissionCheckerConfiguration(
		ObjectEntrySearchPermissionFilterContributor
			objectEntrySearchPermissionFilterContributor) {

		SearchPermissionCheckerConfiguration
			searchPermissionCheckerConfiguration = Mockito.mock(
				SearchPermissionCheckerConfiguration.class);

		ReflectionTestUtil.setFieldValue(
			objectEntrySearchPermissionFilterContributor,
			"_searchPermissionCheckerConfiguration",
			searchPermissionCheckerConfiguration);

		Mockito.when(
			searchPermissionCheckerConfiguration.permissionTermsLimit()
		).thenReturn(
			2
		);
	}

}