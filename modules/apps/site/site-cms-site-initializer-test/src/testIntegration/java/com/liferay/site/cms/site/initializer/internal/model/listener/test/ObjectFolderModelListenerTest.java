/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.model.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectActionKeys;
import com.liferay.object.constants.ObjectFolderConstants;
import com.liferay.object.model.ObjectFolder;
import com.liferay.object.service.ObjectFolderLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PortalInstances;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Víctor Galán
 */
@RunWith(Arquillian.class)
public class ObjectFolderModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testOnAfterCreate() throws Exception {
		Company company = CompanyTestUtil.addCompany();

		PortalInstances.initCompany(company);

		Role role = _roleLocalService.getRole(
			company.getCompanyId(), RoleConstants.CMS_ADMINISTRATOR);

		for (String externalReferenceCode :
				_OBJECT_FOLDER_EXTERNAL_REFERENCE_CODES) {

			ObjectFolder objectFolder =
				_objectFolderLocalService.
					getObjectFolderByExternalReferenceCode(
						externalReferenceCode, company.getCompanyId());

			for (String actionId : _ACTION_IDS) {
				Assert.assertTrue(
					externalReferenceCode + StringPool.SPACE + actionId,
					_resourcePermissionLocalService.hasResourcePermission(
						company.getCompanyId(), ObjectFolder.class.getName(),
						ResourceConstants.SCOPE_INDIVIDUAL,
						String.valueOf(objectFolder.getObjectFolderId()),
						role.getRoleId(), actionId));
			}
		}
	}

	private static final String[] _ACTION_IDS = {
		ObjectActionKeys.ADD_OBJECT_DEFINITION, ActionKeys.DELETE,
		ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.VIEW
	};

	private static final String[] _OBJECT_FOLDER_EXTERNAL_REFERENCE_CODES = {
		ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_CONTENT_STRUCTURES,
		ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_FILE_TYPES,
		ObjectFolderConstants.
			EXTERNAL_REFERENCE_CODE_STRUCTURE_REPEATABLE_GROUPS
	};

	@Inject
	private ObjectFolderLocalService _objectFolderLocalService;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

}