/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_4_x.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.v7_4_x.UpgradeListTypeType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jorge Avalos
 */
@RunWith(Arquillian.class)
public class UpgradeListTypeTypeTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_companyId = CompanyThreadLocal.getCompanyId();

		for (String listTypeName : _listTypeNames) {
			_listTypeLocalService.addListType(
				_companyId, listTypeName, _OLD_LIST_TYPE_TYPE);
		}
	}

	@Test
	public void testUpgradeListTypeExists() throws Exception {
		for (String listTypeName : _listTypeNames) {
			_listTypeLocalService.addListType(
				_companyId, listTypeName, _NEW_LIST_TYPE_TYPE);
		}

		_test();
	}

	@Test
	public void testUpgradeListTypeNotExists() throws Exception {
		try {
			for (String listTypeName : _listTypeNames) {
				ListType listType = _deleteListType(
					listTypeName, _NEW_LIST_TYPE_TYPE);

				if (listType != null) {
					_originalListTypes.add(listType);
				}
			}

			_test();
		}
		finally {
			for (String listTypeName : _listTypeNames) {
				_deleteListType(listTypeName, _NEW_LIST_TYPE_TYPE);
			}

			for (ListType listType : _originalListTypes) {
				_listTypeLocalService.addListType(listType);
			}
		}
	}

	private ListType _deleteListType(String listTypeName, String listTypeType) {
		ListType listType = _listTypeLocalService.getListType(
			_companyId, listTypeName, listTypeType);

		if (listType != null) {
			_listTypeLocalService.deleteListType(listType);
		}

		return listType;
	}

	private void _test() throws UpgradeException {
		UpgradeProcess upgradeProcess = new UpgradeListTypeType();

		upgradeProcess.upgrade();

		FinderCacheUtil.clearCache();

		for (String listTypeName : _listTypeNames) {
			ListType oldListType = _listTypeLocalService.getListType(
				_companyId, listTypeName, _OLD_LIST_TYPE_TYPE);

			ListType newListType = _listTypeLocalService.getListType(
				_companyId, listTypeName, _NEW_LIST_TYPE_TYPE);

			Assert.assertNull(oldListType);

			Assert.assertNotNull(newListType);
		}
	}

	private static final String _NEW_LIST_TYPE_TYPE =
		"com.liferay.portal.kernel.model.Company.website";

	private static final String _OLD_LIST_TYPE_TYPE =
		"com.liferay.account.model.AccountEntry.address";

	private static final List<String> _listTypeNames = Arrays.asList(
		"intranet", "public");

	private Long _companyId;

	@Inject
	private ListTypeLocalService _listTypeLocalService;

	private final List<ListType> _originalListTypes = new ArrayList<>();

}