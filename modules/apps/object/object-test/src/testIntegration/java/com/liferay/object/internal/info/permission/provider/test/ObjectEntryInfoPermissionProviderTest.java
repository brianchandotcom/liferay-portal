/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.info.permission.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.permission.provider.InfoPermissionProvider;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Collections;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
public class ObjectEntryInfoPermissionProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE);

	@Test
	public void testHasViewPermission() throws Exception {
		_testHasViewPermissionForCustomObjectDefinition();
		_testHasViewPermissionForModifiableSystemObjectDefinition(false);
		_testHasViewPermissionForUnmodifiableSystemObjectDefinition();
	}

	@FeatureFlags("LPD-17564")
	@Test
	public void testHasViewPermissionWithFeatureFlagEnabled() throws Exception {
		_testHasViewPermissionForCustomObjectDefinition();
		_testHasViewPermissionForModifiableSystemObjectDefinition(true);
		_testHasViewPermissionForUnmodifiableSystemObjectDefinition();
	}

	private void _testHasViewPermissionForCustomObjectDefinition()
		throws Exception {

		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.addCustomObjectDefinition(
				Collections.singletonList(
					new TextObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						"a" + RandomTestUtil.randomString()
					).build()));

		objectDefinition =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				objectDefinition.getObjectDefinitionId());

		try {
			InfoPermissionProvider<ObjectEntry> infoPermissionProvider =
				_infoItemServiceRegistry.getFirstInfoItemService(
					InfoPermissionProvider.class,
					objectDefinition.getClassName());

			Assert.assertNotNull(infoPermissionProvider);

			Assert.assertTrue(
				infoPermissionProvider.hasViewPermission(
					PermissionThreadLocal.getPermissionChecker()));
		}
		finally {
			_objectDefinitionLocalService.deleteObjectDefinition(
				objectDefinition);
		}
	}

	private void _testHasViewPermissionForModifiableSystemObjectDefinition(
			boolean expectedResult)
		throws Exception {

		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.addModifiableSystemObjectDefinition(
				TestPropsValues.getUserId(), null, true,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"Test", null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_SITE, null, 1,
				Collections.singletonList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING,
						RandomTestUtil.randomString(), StringUtil.randomId())));

		objectDefinition =
			_objectDefinitionLocalService.publishSystemObjectDefinition(
				TestPropsValues.getUserId(),
				objectDefinition.getObjectDefinitionId());

		try {
			InfoPermissionProvider<ObjectEntry> infoPermissionProvider =
				_infoItemServiceRegistry.getFirstInfoItemService(
					InfoPermissionProvider.class,
					objectDefinition.getClassName());

			Assert.assertNotNull(infoPermissionProvider);

			Assert.assertEquals(
				expectedResult,
				infoPermissionProvider.hasViewPermission(
					PermissionThreadLocal.getPermissionChecker()));
		}
		finally {
			_objectDefinitionLocalService.deleteObjectDefinition(
				objectDefinition);
		}
	}

	private void _testHasViewPermissionForUnmodifiableSystemObjectDefinition()
		throws Exception {

		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.addUnmodifiableSystemObjectDefinition(
				null, TestPropsValues.getUserId(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"Test", null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_SITE, null, 1,
				Collections.singletonList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING,
						RandomTestUtil.randomString(),
						"x" + RandomTestUtil.randomString())));

		try {
			Assert.assertNull(
				_infoItemServiceRegistry.getFirstInfoItemService(
					InfoPermissionProvider.class,
					objectDefinition.getClassName()));
		}
		finally {
			_objectDefinitionLocalService.deleteObjectDefinition(
				objectDefinition);
		}
	}

	@Inject
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

}