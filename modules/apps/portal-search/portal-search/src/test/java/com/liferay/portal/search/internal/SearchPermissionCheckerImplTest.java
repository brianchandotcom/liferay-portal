/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchPermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.UserBag;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.configuration.SearchPermissionCheckerConfiguration;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author André de Oliveira
 */
public class SearchPermissionCheckerImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		Mockito.doReturn(
			_indexer
		).when(
			_indexerRegistry
		).getIndexer(
			Mockito.nullable(String.class)
		);

		_searchPermissionChecker = _createSearchPermissionChecker();
	}

	@After
	public void tearDown() throws Exception {
		_searchPermissionCheckerImpl.deactivate();
	}

	@Test
	public void testNullInput() {
		Assert.assertNull(
			_searchPermissionChecker.getPermissionBooleanFilter(
				0, null, 0, null, null, null));
	}

	@Test
	public void testPermissionFilterTakesOverNullInputFilter()
		throws Exception {

		long userId = RandomTestUtil.randomLong();

		_whenIndexerIsPermissionAware(true);
		_whenPermissionCheckerGetUser(_user);
		_whenPermissionCheckerGetUserBag(_userBag);
		_whenUserGetUserId(userId);

		Assert.assertNotNull(
			_searchPermissionChecker.getPermissionBooleanFilter(
				0, null, userId, null, null, new SearchContext()));
	}

	private SearchPermissionCheckerImpl _createSearchPermissionChecker() {
		_searchPermissionCheckerImpl = new SearchPermissionCheckerImpl();

		ReflectionTestUtil.setFieldValue(
			_searchPermissionCheckerImpl, "_indexerRegistry", _indexerRegistry);
		ReflectionTestUtil.setFieldValue(
			_searchPermissionCheckerImpl, "_permissionChecker",
			_permissionChecker);
		ReflectionTestUtil.setFieldValue(
			_searchPermissionCheckerImpl, "_resourcePermissionLocalService",
			_resourcePermissionLocalService);
		ReflectionTestUtil.setFieldValue(
			_searchPermissionCheckerImpl, "_roleLocalService",
			_roleLocalService);
		ReflectionTestUtil.setFieldValue(
			_searchPermissionCheckerImpl,
			"_searchPermissionCheckerConfiguration",
			_searchPermissionCheckerConfiguration);
		ReflectionTestUtil.setFieldValue(
			_searchPermissionCheckerImpl, "_userLocalService",
			_userLocalService);

		_searchPermissionCheckerImpl.activate(
			SystemBundleUtil.getBundleContext(), Collections.emptyMap());

		return _searchPermissionCheckerImpl;
	}

	private boolean _whenIndexerIsPermissionAware(boolean permissionAware) {
		return Mockito.doReturn(
			permissionAware
		).when(
			_indexer
		).isPermissionAware();
	}

	private User _whenPermissionCheckerGetUser(User user) {
		return Mockito.doReturn(
			user
		).when(
			_permissionChecker
		).getUser();
	}

	private void _whenPermissionCheckerGetUserBag(UserBag userBag)
		throws Exception {

		Mockito.doReturn(
			userBag
		).when(
			_permissionChecker
		).getUserBag();
	}

	private long _whenUserGetUserId(long userId) {
		return Mockito.doReturn(
			userId
		).when(
			_user
		).getUserId();
	}

	private final Indexer<?> _indexer = Mockito.mock(Indexer.class);
	private final IndexerRegistry _indexerRegistry = Mockito.mock(
		IndexerRegistry.class);
	private final PermissionChecker _permissionChecker = Mockito.mock(
		PermissionChecker.class);
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService = Mockito.mock(
			ResourcePermissionLocalService.class);
	private final RoleLocalService _roleLocalService = Mockito.mock(
		RoleLocalService.class);
	private SearchPermissionChecker _searchPermissionChecker;
	private final SearchPermissionCheckerConfiguration
		_searchPermissionCheckerConfiguration = Mockito.mock(
			SearchPermissionCheckerConfiguration.class);
	private SearchPermissionCheckerImpl _searchPermissionCheckerImpl;
	private final User _user = Mockito.mock(User.class);
	private final UserBag _userBag = Mockito.mock(UserBag.class);
	private final UserLocalService _userLocalService = Mockito.mock(
		UserLocalService.class);

}