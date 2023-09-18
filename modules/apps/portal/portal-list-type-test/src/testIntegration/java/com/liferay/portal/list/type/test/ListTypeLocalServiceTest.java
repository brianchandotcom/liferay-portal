/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.list.type.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luis Ortiz
 */
@RunWith(Arquillian.class)
public class ListTypeLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_company = CompanyTestUtil.addCompany();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		UserTestUtil.setUser(
			UserTestUtil.getAdminUser(PortalUtil.getDefaultCompanyId()));

		CompanyLocalServiceUtil.deleteCompany(_company.getCompanyId());
	}

	@Test
	public void testDefaultListTypes() throws Exception {
		JSONArray listTypesJSONArray = _jsonFactory.createJSONArray(
			StringUtil.read(
				getClass().getClassLoader(),
				"com/liferay/portal/list/type/impl/dependencies" +
					"/portal-list-type.json",
				false));

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setWithSafeCloseable(
					_company.getCompanyId())) {

			for (int i = 0; i < listTypesJSONArray.length(); i++) {
				JSONObject listTypeJSONObject =
					listTypesJSONArray.getJSONObject(i);

				ListType listType = _listTypeLocalService.getListType(
					listTypeJSONObject.getString("name"),
					listTypeJSONObject.getString("type"));

				Assert.assertNotNull(listType);
				Assert.assertEquals(
					_company.getCompanyId(), listType.getCompanyId());
			}
		}
	}

	@Test
	public void testGetListType() {
		ListType listType = null;

		try (SafeCloseable safeCloseable1 =
				CompanyThreadLocal.setWithSafeCloseable(
					_company.getCompanyId())) {

			listType = _listTypeLocalService.addListType(
				_LIST_TYPE_NAME, _LIST_TYPE_TYPE);

			Assert.assertNotNull(
				_listTypeLocalService.getListType(
					_LIST_TYPE_NAME, _LIST_TYPE_TYPE));

			try (SafeCloseable safeCloseable2 =
					CompanyThreadLocal.setWithSafeCloseable(
						PortalUtil.getDefaultCompanyId())) {

				Assert.assertNull(
					_listTypeLocalService.getListType(
						_LIST_TYPE_NAME, _LIST_TYPE_TYPE));
			}
		}
		finally {
			if (listType != null) {
				try (SafeCloseable safeCloseable =
						CompanyThreadLocal.setWithSafeCloseable(
							_company.getCompanyId())) {

					_listTypeLocalService.deleteListType(listType);
				}
			}
		}
	}

	@Test
	public void testGetListTypes() {
		ListType listType = null;

		try (SafeCloseable safeCloseable1 =
				CompanyThreadLocal.setWithSafeCloseable(
					_company.getCompanyId())) {

			listType = _listTypeLocalService.addListType(
				_LIST_TYPE_NAME, _LIST_TYPE_TYPE);

			List<ListType> listTypes = _listTypeLocalService.getListTypes(
				_LIST_TYPE_TYPE);

			Assert.assertEquals(
				"listTypes should only have 1 element", 1, listTypes.size());

			try (SafeCloseable safeCloseable2 =
					CompanyThreadLocal.setWithSafeCloseable(
						PortalUtil.getDefaultCompanyId())) {

				listTypes = _listTypeLocalService.getListTypes(_LIST_TYPE_TYPE);

				Assert.assertEquals(
					"listTypes should be empty", 0, listTypes.size());
			}
		}
		finally {
			if (listType != null) {
				try (SafeCloseable safeCloseable =
						CompanyThreadLocal.setWithSafeCloseable(
							_company.getCompanyId())) {

					_listTypeLocalService.deleteListType(listType);
				}
			}
		}
	}

	private static final String _LIST_TYPE_NAME = "test";

	private static final String _LIST_TYPE_TYPE =
		ListTypeLocalServiceTest.class.getName();

	private static Company _company;

	@Inject
	private static CompanyLocalService _companyLocalService;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private ListTypeLocalService _listTypeLocalService;

}