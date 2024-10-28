/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.manager.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
public class FragmentCollectionManagerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	@TestInfo("LPS-162848")
	public void testGetLayoutElementMapsListMapWithoutApprovedObjectDefinition() {
		Map<String, List<Map<String, Object>>> layoutElementMapsListMap =
			ReflectionTestUtil.invoke(
				_fragmentCollectionManager, "getLayoutElementMapsListMap",
				new Class<?>[] {PermissionChecker.class},
				PermissionThreadLocal.getPermissionChecker());

		Assert.assertFalse(layoutElementMapsListMap.containsKey("INPUTS"));
	}

	@Test
	@TestInfo("LPS-162848")
	public void testGetLayoutElementMapsListMapWithoutPermissions()
		throws Exception {

		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				Collections.singletonList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, "First Name",
						"firstName")));

		User user = UserTestUtil.addUser();

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user));

			Map<String, List<Map<String, Object>>> layoutElementMapsListMap =
				ReflectionTestUtil.invoke(
					_fragmentCollectionManager, "getLayoutElementMapsListMap",
					new Class<?>[] {PermissionChecker.class},
					PermissionThreadLocal.getPermissionChecker());

			Assert.assertFalse(layoutElementMapsListMap.containsKey("INPUTS"));
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);

			_objectDefinitionLocalService.deleteObjectDefinition(
				objectDefinition);
		}
	}

	@Test
	@TestInfo("LPS-162848")
	public void testGetLayoutElementMapsListMapWithPermissions()
		throws Exception {

		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				Collections.singletonList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, "First Name",
						"firstName")));

		try {
			Map<String, List<Map<String, Object>>> layoutElementMapsListMap =
				ReflectionTestUtil.invoke(
					_fragmentCollectionManager, "getLayoutElementMapsListMap",
					new Class<?>[] {PermissionChecker.class},
					PermissionThreadLocal.getPermissionChecker());

			Assert.assertTrue(layoutElementMapsListMap.containsKey("INPUTS"));
		}
		finally {
			_objectDefinitionLocalService.deleteObjectDefinition(
				objectDefinition);
		}
	}

	@Inject(
		filter = "component.name=com.liferay.layout.content.page.editor.web.internal.manager.FragmentCollectionManager",
		type = Inject.NoType.class
	)
	private Object _fragmentCollectionManager;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

}