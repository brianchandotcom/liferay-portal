/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_4_x.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ExternalReferenceCodeModel;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.BaseExternalReferenceCodeUpgradeProcessTestCase;
import com.liferay.portal.upgrade.v7_4_x.RoleExternalReferenceCodeUpgradeProcess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.Assert;
import org.junit.runner.RunWith;

/**
 * @author Stefano Motta
 */
@RunWith(Arquillian.class)
public class RoleExternalReferenceCodeUpgradeProcessTest
	extends BaseExternalReferenceCodeUpgradeProcessTestCase {

	@Override
	protected ExternalReferenceCodeModel[] addExternalReferenceCodeModels(
			String tableName)
		throws PortalException {

		Role role = _roleLocalService.addRole(
			null, TestPropsValues.getUserId(), null, 0,
			StringUtil.randomString(), null, null, RoleConstants.TYPE_SITE,
			null, ServiceContextTestUtil.getServiceContext(group.getGroupId()));

		return new ExternalReferenceCodeModel[] {role};
	}

	@Override
	protected void assertExternalReferenceCode(
			String[] externalReferenceCodes, String tableName)
		throws Exception {

		try (Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select 1 from ", tableName,
					" where externalReferenceCode in ('",
					StringUtil.merge(externalReferenceCodes, "', '"), "')"))) {

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				Assert.assertFalse(resultSet.next());
			}
		}
	}

	@Override
	protected ExternalReferenceCodeModel fetchExternalReferenceCodeModel(
		ExternalReferenceCodeModel externalReferenceCodeModel,
		String tableName) {

		Role role = (Role)externalReferenceCodeModel;

		return _roleLocalService.fetchRole(role.getRoleId());
	}

	@Override
	protected String[] getTableNames() {
		return new String[] {"Role_"};
	}

	@Override
	protected UpgradeProcess getUpgradeProcess() {
		return new RoleExternalReferenceCodeUpgradeProcess();
	}

	@Override
	protected UpgradeStepRegistrator getUpgradeStepRegistrator() {
		return null;
	}

	@Override
	protected Version getVersion() {
		return null;
	}

	@Override
	protected void updateExternalReferenceCode(
			String[] externalReferenceCodes, String tableName)
		throws Exception {

		db.runSQL(
			StringBundler.concat(
				"update ", tableName,
				" set externalReferenceCode = null where ",
				"externalReferenceCode in ('",
				StringUtil.merge(externalReferenceCodes, "', '"), "')"));

		entityCache.clearCache();
		multiVMPool.clear();
	}

	@Inject
	private RoleLocalService _roleLocalService;

}