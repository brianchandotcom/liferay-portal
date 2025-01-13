/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.upgrade.v0_2_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectStateFlowLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alberto Javier Moreno Lage
 */
@FeatureFlags("LPS-178642")
@RunWith(Arquillian.class)
public class UpdateListTypeDefinitionsUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testUpgrade() throws Exception {

		// Deletion order is relevant to avoid constraint errors

		for (String externalReferenceCode :
				Arrays.asList(
					"L_API_SORT", "L_API_FILTER", "L_API_PROPERTY",
					"L_API_SCHEMA", "L_API_ENDPOINT", "L_API_APPLICATION")) {

			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.
					fetchObjectDefinitionByExternalReferenceCode(
						externalReferenceCode, TestPropsValues.getCompanyId());

			if (objectDefinition != null) {
				_objectDefinitionLocalService.deleteObjectDefinition(
					objectDefinition);
			}
		}

		for (String externalReferenceCode :
				Arrays.asList(
					"APPLICATION_STATUS_PICKLIST", "HTTP_METHOD_PICKLIST",
					"L_API_PROPERTY_TYPES", "RETRIEVE_TYPE_PICKLIST",
					"SCOPE_PICKLIST")) {

			ListTypeDefinition listTypeDefinition =
				_listTypeDefinitionLocalService.
					fetchListTypeDefinitionByExternalReferenceCode(
						externalReferenceCode, TestPropsValues.getCompanyId());

			if (listTypeDefinition != null) {
				_listTypeDefinitionLocalService.deleteListTypeDefinition(
					listTypeDefinition);
			}
		}

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			new String(
				FileUtil.getBytes(
					getClass(),
					"dependencies/00-old-list-type-definition.json")),
			"headless-batch-engine/v1.0/import-task/com.liferay.headless." +
				"admin.list.type.dto.v1_0.ListTypeDefinition",
			Http.Method.POST);

		_waitForImportCompletion(jsonObject);

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			new String(
				FileUtil.getBytes(
					getClass(), "dependencies/01-old-object-definition.json")),
			"headless-batch-engine/v1.0/import-task/com.liferay.object.admin." +
				"rest.dto.v1_0.ObjectDefinition",
			Http.Method.POST);

		_waitForImportCompletion(jsonObject);

		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator,
			"com.liferay.headless.builder.internal.upgrade.v0_2_0." +
				"UpdateListTypeDefinitionsUpgradeProcess");

		upgradeProcess.upgrade();

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_API_APPLICATION", TestPropsValues.getCompanyId());

		_assertExpectedChanges(
			"Application Status", "PUBLISHED", "UNPUBLISHED", "published",
			"unpublished", "APPLICATION_STATUS_PICKLIST", objectDefinition,
			"APPLICATION_STATUS");

		objectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_API_ENDPOINT", TestPropsValues.getCompanyId());

		_assertExpectedChanges(
			"HTTP Method", "GET", "POST", "get", "post", "HTTP_METHOD_PICKLIST",
			objectDefinition, "HTTP_METHOD");

		_assertExpectedChanges(
			"Retrieve Type", "COLLECTION", "SINGLE_ELEMENT", "collection",
			"singleElement", "RETRIEVE_TYPE_PICKLIST", objectDefinition,
			"RETRIEVE_TYPE");

		_assertExpectedChanges(
			"Scope", "COMPANY", "SITE", "company", "site", "SCOPE_PICKLIST",
			objectDefinition, "SCOPE");
	}

	private void _assertExpectedChanges(
			String expectedListTypeDefinitionName,
			String expectedListTypeEntry1ExternalReferenceCode,
			String expectedListTypeEntry2ExternalReferenceCode,
			String listTypeEntry1Key, String listTypeEntry2Key,
			String listTypeExternalReferenceCode,
			ObjectDefinition objectDefinition,
			String objectFieldExternalReferenceCode)
		throws Exception {

		ObjectField objectField = _objectFieldLocalService.getObjectField(
			objectFieldExternalReferenceCode,
			objectDefinition.getObjectDefinitionId());

		Assert.assertNull(
			_objectStateFlowLocalService.fetchObjectFieldObjectStateFlow(
				objectField.getObjectFieldId()));

		Assert.assertFalse(objectField.isState());

		ListTypeDefinition listTypeDefinition =
			_listTypeDefinitionLocalService.
				getListTypeDefinitionByExternalReferenceCode(
					listTypeExternalReferenceCode,
					TestPropsValues.getCompanyId());

		Assert.assertEquals(
			expectedListTypeDefinitionName, listTypeDefinition.getName());

		ListTypeEntry listTypeEntry =
			_listTypeEntryLocalService.getListTypeEntry(
				listTypeDefinition.getListTypeDefinitionId(),
				listTypeEntry1Key);

		Assert.assertEquals(
			expectedListTypeEntry1ExternalReferenceCode,
			listTypeEntry.getExternalReferenceCode());

		listTypeEntry = _listTypeEntryLocalService.getListTypeEntry(
			listTypeDefinition.getListTypeDefinitionId(), listTypeEntry2Key);

		Assert.assertEquals(
			expectedListTypeEntry2ExternalReferenceCode,
			listTypeEntry.getExternalReferenceCode());
	}

	private void _waitForImportCompletion(JSONObject jsonObject)
		throws Exception {

		while (true) {
			jsonObject = HTTPTestUtil.invokeToJSONObject(
				null,
				StringBundler.concat(
					"headless-batch-engine/v1.0/import-task",
					"/by-external-reference-code/",
					jsonObject.getString("externalReferenceCode")),
				Http.Method.GET);

			String actualExecuteStatus = jsonObject.getString("executeStatus");

			if (StringUtil.equals(actualExecuteStatus, "COMPLETED") ||
				StringUtil.equals(actualExecuteStatus, "FAILED")) {

				break;
			}
		}
	}

	@Inject(
		filter = "component.name=com.liferay.headless.builder.internal.upgrade.registry.HeadlessBuilderUpgradeStepRegistrator"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	@Inject
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

	@Inject
	private ObjectStateFlowLocalService _objectStateFlowLocalService;

}