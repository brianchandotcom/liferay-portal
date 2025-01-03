/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.upgrade.v0_2_0.test;

import com.liferay.headless.builder.test.BaseTestCase;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.apache.commons.lang.time.StopWatch;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Alejandro Tardín
 */
@FeatureFlags("LPS-178642")
public class ModifyAPIBuilderPicklistsUpgradeProcessTest extends BaseTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() {
		_originalStopWatch = ReflectionTestUtil.getAndSetFieldValue(
			DBUpgrader.class, "_stopWatch", null);
	}

	@AfterClass
	public static void tearDownClass() {
		ReflectionTestUtil.setFieldValue(
			DBUpgrader.class, "_stopWatch", _originalStopWatch);
	}

	@Test
	public void testUpgrade() throws Exception {
		_objectDefinitionLocalService.deleteObjectDefinition(
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_API_SORT", TestPropsValues.getCompanyId()));

		_objectDefinitionLocalService.deleteObjectDefinition(
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_API_FILTER", TestPropsValues.getCompanyId()));

		_objectDefinitionLocalService.deleteObjectDefinition(
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_API_PROPERTY", TestPropsValues.getCompanyId()));

		_objectDefinitionLocalService.deleteObjectDefinition(
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_API_SCHEMA", TestPropsValues.getCompanyId()));

		_objectDefinitionLocalService.deleteObjectDefinition(
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_API_ENDPOINT", TestPropsValues.getCompanyId()));

		_objectDefinitionLocalService.deleteObjectDefinition(
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_API_APPLICATION", TestPropsValues.getCompanyId()));

		_listTypeDefinitionLocalService.deleteListTypeDefinition(
			_listTypeDefinitionLocalService.
				fetchListTypeDefinitionByExternalReferenceCode(
					"APPLICATION_STATUS_PICKLIST",
					TestPropsValues.getCompanyId()));

		_listTypeDefinitionLocalService.deleteListTypeDefinition(
			_listTypeDefinitionLocalService.
				fetchListTypeDefinitionByExternalReferenceCode(
					"HTTP_METHOD_PICKLIST", TestPropsValues.getCompanyId()));

		_listTypeDefinitionLocalService.deleteListTypeDefinition(
			_listTypeDefinitionLocalService.
				fetchListTypeDefinitionByExternalReferenceCode(
					"L_API_PROPERTY_TYPES", TestPropsValues.getCompanyId()));

		_listTypeDefinitionLocalService.deleteListTypeDefinition(
			_listTypeDefinitionLocalService.
				fetchListTypeDefinitionByExternalReferenceCode(
					"RETRIEVE_TYPE_PICKLIST", TestPropsValues.getCompanyId()));

		_listTypeDefinitionLocalService.deleteListTypeDefinition(
			_listTypeDefinitionLocalService.
				fetchListTypeDefinitionByExternalReferenceCode(
					"SCOPE_PICKLIST", TestPropsValues.getCompanyId()));
	}

	private static StopWatch _originalStopWatch;

	@Inject(
		filter = "component.name=com.liferay.headless.builder.internal.upgrade.registry.HeadlessBuilderUpgradeStepRegistrator"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

}