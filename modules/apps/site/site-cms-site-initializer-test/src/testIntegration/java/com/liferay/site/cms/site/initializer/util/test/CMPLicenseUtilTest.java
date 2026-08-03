/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.license.util.App;
import com.liferay.portal.kernel.license.util.LicenseManager;
import com.liferay.portal.kernel.license.util.LicenseManagerUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionRegistryUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.site.cmp.site.initializer.test.util.CMPTestUtil;
import com.liferay.site.cms.site.initializer.util.CMPLicenseUtil;

import java.util.Objects;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Fábio Alves
 */
@FeatureFlag("LPD-58677")
@RunWith(Arquillian.class)
public class CMPLicenseUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_licenseManager = ReflectionTestUtil.getFieldValue(
			LicenseManagerUtil.class, "_licenseManager");

		_group = CMPTestUtil.getOrAddGroup(CMPLicenseUtilTest.class);
	}

	@After
	public void tearDown() throws Exception {
		ReflectionTestUtil.setFieldValue(
			LicenseManagerUtil.class, "_licenseManager", _licenseManager);

		_checkResources();
	}

	@Test
	public void testCheckResources() throws Exception {
		_testCheckResources(false);
		_testCheckResources(true);
	}

	private void _checkResources() {
		CMPLicenseUtil.checkResources(
			_group.getCompanyId(), _groupLocalService, _layoutLocalService,
			_objectDefinitionLocalService);
	}

	private void _testCheckResources(boolean appEnabled) throws Exception {
		ReflectionTestUtil.setFieldValue(
			LicenseManagerUtil.class, "_licenseManager",
			ProxyUtil.newProxyInstance(
				LicenseManager.class.getClassLoader(),
				new Class<?>[] {LicenseManager.class},
				(proxy, method, arguments) -> {
					if (Objects.equals(method.getName(), "isAppEnabled") &&
						Objects.equals(arguments[0], App.CMP)) {

						return appEnabled;
					}

					return method.invoke(_licenseManager, arguments);
				}));

		_checkResources();

		for (String friendlyURL : _CMP_LAYOUT_FRIENDLY_URLS) {
			Layout layout = _layoutLocalService.getFriendlyURLLayout(
				_group.getGroupId(), false, friendlyURL);

			Assert.assertEquals(!appEnabled, layout.isHidden());
		}

		for (String externalReferenceCode :
				_CMP_OBJECT_DEFINITION_EXTERNAL_REFERENCE_CODES) {

			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.
					getObjectDefinitionByExternalReferenceCode(
						externalReferenceCode, _group.getCompanyId());

			Assert.assertEquals(appEnabled, objectDefinition.isActive());

			ModelResourcePermission<ObjectEntry> modelResourcePermission =
				ModelResourcePermissionRegistryUtil.getModelResourcePermission(
					objectDefinition.getClassName());

			if (appEnabled) {
				Assert.assertNotNull(modelResourcePermission);
			}
			else {
				Assert.assertNull(modelResourcePermission);
			}
		}
	}

	private static final String[] _CMP_LAYOUT_FRIENDLY_URLS = {
		"/planning", "/projects", "/tasks"
	};

	private static final String[]
		_CMP_OBJECT_DEFINITION_EXTERNAL_REFERENCE_CODES = {
			"L_CMP_PROJECT", "L_CMP_PROJECT_LINK", "L_CMP_TASK",
			"L_CMP_TASK_LINK"
		};

	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private LayoutLocalService _layoutLocalService;

	private LicenseManager _licenseManager;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

}