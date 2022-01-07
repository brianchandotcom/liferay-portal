/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.users.admin.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.ExpandoValue;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.expando.kernel.service.ExpandoValueLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.CSVUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.expando.util.test.ExpandoTestUtil;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pei-Jung Lan
 */
@RunWith(Arquillian.class)
public class ExportUsersMVCResourceCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetUserCSVWithExpando() throws Exception {
		boolean permissionsCustomAttributeReadCheckByDefault =
			PropsValues.PERMISSIONS_CUSTOM_ATTRIBUTE_READ_CHECK_BY_DEFAULT;
		String[] usersExportCSVfields = PropsValues.USERS_EXPORT_CSV_FIELDS;

		try {
			ReflectionTestUtil.setFieldValue(
				PropsValues.class,
				"PERMISSIONS_CUSTOM_ATTRIBUTE_READ_CHECK_BY_DEFAULT", false);

			Company company1 = CompanyTestUtil.addCompany();

			User user1 = UserTestUtil.addUser(company1);

			ExpandoTable expandoTable =
				_expandoTableLocalService.addDefaultTable(
					company1.getCompanyId(), User.class.getName());

			ExpandoColumn expandoColumn = ExpandoTestUtil.addColumn(
				expandoTable, RandomTestUtil.randomString(),
				ExpandoColumnConstants.STRING);

			ExpandoValue expandoValue = _expandoValueLocalService.addValue(
				company1.getCompanyId(), User.class.getName(),
				expandoTable.getName(), expandoColumn.getName(),
				user1.getUserId(), RandomTestUtil.randomString());

			ReflectionTestUtil.setFieldValue(
				PropsValues.class, "USERS_EXPORT_CSV_FIELDS",
				new String[] {
					"fullName", "expando:" + expandoColumn.getName()
				});

			Assert.assertEquals(
				StringBundler.concat(
					CSVUtil.encode(user1.getFullName()), StringPool.COMMA,
					CSVUtil.encode(expandoValue.getString()),
					StringPool.NEW_LINE),
				_getUserCSV(user1));

			Company company2 = CompanyTestUtil.addCompany();

			User user2 = UserTestUtil.addUser(company2);

			Assert.assertEquals(
				StringBundler.concat(
					CSVUtil.encode(user2.getFullName()), StringPool.COMMA,
					StringPool.BLANK, StringPool.NEW_LINE),
				_getUserCSV(user2));
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				PropsValues.class,
				"PERMISSIONS_CUSTOM_ATTRIBUTE_READ_CHECK_BY_DEFAULT",
				permissionsCustomAttributeReadCheckByDefault);
			ReflectionTestUtil.setFieldValue(
				PropsValues.class, "USERS_EXPORT_CSV_FIELDS",
				usersExportCSVfields);
		}
	}

	private String _getUserCSV(User user) {
		return ReflectionTestUtil.invoke(
			_mvcResourceCommand, "_getUserCSV", new Class<?>[] {User.class},
			user);
	}

	@Inject
	private ExpandoTableLocalService _expandoTableLocalService;

	@Inject
	private ExpandoValueLocalService _expandoValueLocalService;

	@Inject(filter = "mvc.command.name=/users_admin/export_users")
	private MVCResourceCommand _mvcResourceCommand;

}