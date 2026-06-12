/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.data.masking.internal.model.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.engine.unit.BatchEngineUnitThreadLocal;
import com.liferay.headless.data.masking.test.util.DataMaskTestUtil;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.Serializable;

import java.util.Map;

import org.hamcrest.CoreMatchers;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jose Luis Navarro
 */
@RunWith(Arquillian.class)
public class DataMaskObjectEntryModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@FeatureFlags(
		featureFlags = {@FeatureFlag("LPD-63311"), @FeatureFlag("LPD-90204")}
	)
	@Test
	public void testCustomMaskCanBeUpdatedAndDeleted() throws Exception {
		ObjectEntry customMaskObjectEntry = DataMaskTestUtil.addCustomMask(
			RandomTestUtil.randomString(), "\\d{4}", "[REDACTED]");

		ObjectEntry updatedObjectEntry =
			_objectEntryLocalService.updateObjectEntry(
				TestPropsValues.getUserId(),
				customMaskObjectEntry.getObjectEntryId(), 0,
				HashMapBuilder.<String, Serializable>putAll(
					customMaskObjectEntry.getValues()
				).put(
					"replacementValue", "[CUSTOM]"
				).build(),
				ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(
			"[CUSTOM]",
			updatedObjectEntry.getValues(
			).get(
				"replacementValue"
			));

		_objectEntryLocalService.deleteObjectEntry(
			customMaskObjectEntry.getObjectEntryId());

		Assert.assertNull(
			_objectEntryLocalService.fetchObjectEntry(
				customMaskObjectEntry.getObjectEntryId()));
	}

	@FeatureFlags(
		featureFlags = {@FeatureFlag("LPD-63311"), @FeatureFlag("LPD-90204")}
	)
	@Test
	public void testSystemMaskCanBeCreatedByDataMaskingSeed() throws Exception {
		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_DATA_MASK", TestPropsValues.getCompanyId());

		BatchEngineUnitThreadLocal.setFileName(_DATA_MASKING_BATCH_FILE_NAME);

		ObjectEntry systemMaskObjectEntry = null;

		try {
			systemMaskObjectEntry = _objectEntryLocalService.addObjectEntry(
				0, TestPropsValues.getUserId(),
				objectDefinition.getObjectDefinitionId(),
				ObjectEntryFolderConstants.
					PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
				null,
				HashMapBuilder.<String, Serializable>put(
					"detectionRegex", "\\d{4}"
				).put(
					"maskType", "system"
				).put(
					"name", RandomTestUtil.randomString()
				).put(
					"replacementValue", "[REDACTED]"
				).build(),
				ServiceContextTestUtil.getServiceContext());

			Assert.assertNotNull(
				_objectEntryLocalService.fetchObjectEntry(
					systemMaskObjectEntry.getObjectEntryId()));
		}
		finally {
			if (systemMaskObjectEntry != null) {
				_objectEntryLocalService.deleteObjectEntry(
					systemMaskObjectEntry.getObjectEntryId());
			}

			BatchEngineUnitThreadLocal.setFileName(StringPool.BLANK);
		}
	}

	@FeatureFlags(
		featureFlags = {@FeatureFlag("LPD-63311"), @FeatureFlag("LPD-90204")}
	)
	@Test
	public void testSystemMaskCanBeUpdatedByDataMaskingSeed() throws Exception {
		ObjectEntry emailMaskObjectEntry = _findSystemMask("Email Address");

		BatchEngineUnitThreadLocal.setFileName(_DATA_MASKING_BATCH_FILE_NAME);

		try {
			_objectEntryLocalService.updateObjectEntry(
				TestPropsValues.getUserId(),
				emailMaskObjectEntry.getObjectEntryId(), 0,
				HashMapBuilder.<String, Serializable>putAll(
					emailMaskObjectEntry.getValues()
				).put(
					"replacementValue", "[EMAIL]"
				).build(),
				ServiceContextTestUtil.getServiceContext());
		}
		finally {
			BatchEngineUnitThreadLocal.setFileName(StringPool.BLANK);
		}

		ObjectEntry refreshedObjectEntry =
			_objectEntryLocalService.getObjectEntry(
				emailMaskObjectEntry.getObjectEntryId());

		Assert.assertEquals(
			"[EMAIL]",
			refreshedObjectEntry.getValues(
			).get(
				"replacementValue"
			));

		BatchEngineUnitThreadLocal.setFileName(_DATA_MASKING_BATCH_FILE_NAME);

		try {
			_objectEntryLocalService.updateObjectEntry(
				TestPropsValues.getUserId(),
				emailMaskObjectEntry.getObjectEntryId(), 0,
				HashMapBuilder.<String, Serializable>putAll(
					refreshedObjectEntry.getValues()
				).put(
					"replacementValue", "[EMAIL_ADDRESS]"
				).build(),
				ServiceContextTestUtil.getServiceContext());
		}
		finally {
			BatchEngineUnitThreadLocal.setFileName(StringPool.BLANK);
		}
	}

	@FeatureFlags(
		featureFlags = {@FeatureFlag("LPD-63311"), @FeatureFlag("LPD-90204")}
	)
	@Test
	public void testSystemMaskCannotBeCreated() throws Exception {
		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_DATA_MASK", TestPropsValues.getCompanyId());

		try {
			_objectEntryLocalService.addObjectEntry(
				0, TestPropsValues.getUserId(),
				objectDefinition.getObjectDefinitionId(),
				ObjectEntryFolderConstants.
					PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
				null,
				HashMapBuilder.<String, Serializable>put(
					"detectionRegex", "\\d{4}"
				).put(
					"maskType", "system"
				).put(
					"name", RandomTestUtil.randomString()
				).put(
					"replacementValue", "[REDACTED]"
				).build(),
				ServiceContextTestUtil.getServiceContext());

			Assert.fail("Creating a system data mask should have thrown");
		}
		catch (Exception exception) {
			Assert.assertThat(
				exception.getMessage(),
				CoreMatchers.containsString("system data masks"));
		}
	}

	@FeatureFlags(
		featureFlags = {@FeatureFlag("LPD-63311"), @FeatureFlag("LPD-90204")}
	)
	@Test
	public void testSystemMaskCannotBeDeleted() throws Exception {
		ObjectEntry emailMaskObjectEntry = _findSystemMask("Email Address");

		try {
			_objectEntryLocalService.deleteObjectEntry(
				emailMaskObjectEntry.getObjectEntryId());

			Assert.fail("Deleting a system data mask should have thrown");
		}
		catch (Exception exception) {
			Assert.assertThat(
				exception.getMessage(),
				CoreMatchers.containsString("system data masks"));
		}

		Assert.assertNotNull(
			_objectEntryLocalService.fetchObjectEntry(
				emailMaskObjectEntry.getObjectEntryId()));
	}

	@FeatureFlags(
		featureFlags = {@FeatureFlag("LPD-63311"), @FeatureFlag("LPD-90204")}
	)
	@Test
	public void testSystemMaskCannotBeUpdated() throws Exception {
		ObjectEntry emailMaskObjectEntry = _findSystemMask("Email Address");

		try {
			_objectEntryLocalService.updateObjectEntry(
				TestPropsValues.getUserId(),
				emailMaskObjectEntry.getObjectEntryId(), 0,
				HashMapBuilder.<String, Serializable>putAll(
					emailMaskObjectEntry.getValues()
				).put(
					"replacementValue", "[CHANGED]"
				).build(),
				ServiceContextTestUtil.getServiceContext());

			Assert.fail("Updating a system data mask should have thrown");
		}
		catch (Exception exception) {
			Assert.assertThat(
				exception.getMessage(),
				CoreMatchers.containsString("system data masks"));
		}

		ObjectEntry refreshedObjectEntry =
			_objectEntryLocalService.getObjectEntry(
				emailMaskObjectEntry.getObjectEntryId());

		Assert.assertEquals(
			"[EMAIL_ADDRESS]",
			refreshedObjectEntry.getValues(
			).get(
				"replacementValue"
			));
	}

	@FeatureFlags(
		featureFlags = {@FeatureFlag("LPD-63311"), @FeatureFlag("LPD-90204")}
	)
	@Test
	public void testSystemMaskCannotBeUpdatedByForeignBatchImport()
		throws Exception {

		ObjectEntry emailMaskObjectEntry = _findSystemMask("Email Address");

		BatchEngineUnitThreadLocal.setFileName(_FOREIGN_BATCH_FILE_NAME);

		try {
			_objectEntryLocalService.updateObjectEntry(
				TestPropsValues.getUserId(),
				emailMaskObjectEntry.getObjectEntryId(), 0,
				HashMapBuilder.<String, Serializable>putAll(
					emailMaskObjectEntry.getValues()
				).put(
					"replacementValue", "[CHANGED]"
				).build(),
				ServiceContextTestUtil.getServiceContext());

			Assert.fail(
				"A foreign batch import should not update a system data mask");
		}
		catch (Exception exception) {
			Assert.assertThat(
				exception.getMessage(),
				CoreMatchers.containsString("system data masks"));
		}
		finally {
			BatchEngineUnitThreadLocal.setFileName(StringPool.BLANK);
		}

		ObjectEntry refreshedObjectEntry =
			_objectEntryLocalService.getObjectEntry(
				emailMaskObjectEntry.getObjectEntryId());

		Assert.assertEquals(
			"[EMAIL_ADDRESS]",
			refreshedObjectEntry.getValues(
			).get(
				"replacementValue"
			));
	}

	private ObjectEntry _findSystemMask(String name) throws Exception {
		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_DATA_MASK", TestPropsValues.getCompanyId());

		if (objectDefinition == null) {
			return null;
		}

		for (ObjectEntry objectEntry :
				_objectEntryLocalService.getObjectEntries(
					0, objectDefinition.getObjectDefinitionId(),
					QueryUtil.ALL_POS, QueryUtil.ALL_POS)) {

			Map<String, Serializable> values = objectEntry.getValues();

			if (name.equals(values.get("name"))) {
				return objectEntry;
			}
		}

		return null;
	}

	private static final String _DATA_MASKING_BATCH_FILE_NAME =
		"com.liferay.headless.data.masking.impl_1.0.0 [1]";

	private static final String _FOREIGN_BATCH_FILE_NAME =
		"com.liferay.example.impl_1.0.0 [2]";

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

}