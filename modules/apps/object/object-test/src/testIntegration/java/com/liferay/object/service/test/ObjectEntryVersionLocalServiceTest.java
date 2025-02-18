/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectEntryVersion;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectEntryVersionLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Feliphe Marinho
 */
@FeatureFlags("LPD-37104")
@RunWith(Arquillian.class)
public class ObjectEntryVersionLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testAddObjectEntryVersion() throws Exception {
		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				Collections.singletonList(
					new TextObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						"textObjectFieldName"
					).build()));

		ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"externalReferenceCode", "externalReferenceCodeValue"
			).put(
				"textObjectFieldName", "textObjectFieldValue1"
			).build(),
			ServiceContextTestUtil.getServiceContext());

		_assertObjectEntryVersions(
			Arrays.asList(
				_createObjectEntryVersion(
					JSONUtil.put(
						"textObjectFieldName", "textObjectFieldValue1"),
					1)),
			_objectEntryVersionLocalService.getObjectEntryVersions(
				objectEntry.getObjectEntryId()));

		Assert.assertEquals(1, objectEntry.getVersion());

		_objectEntryLocalService.updateObjectEntry(
			TestPropsValues.getUserId(), objectEntry.getObjectEntryId(),
			HashMapBuilder.<String, Serializable>put(
				"textObjectFieldName", "textObjectFieldValue2"
			).build(),
			ServiceContextTestUtil.getServiceContext());

		_assertObjectEntryVersions(
			Arrays.asList(
				_createObjectEntryVersion(
					JSONUtil.put(
						"textObjectFieldName", "textObjectFieldValue1"),
					1),
				_createObjectEntryVersion(
					JSONUtil.put(
						"textObjectFieldName", "textObjectFieldValue2"),
					2)),
			_objectEntryVersionLocalService.getObjectEntryVersions(
				objectEntry.getObjectEntryId()));

		objectEntry = _objectEntryLocalService.getObjectEntry(
			objectEntry.getObjectEntryId());

		Assert.assertEquals(2, objectEntry.getVersion());
	}

	private void _assertObjectEntryVersions(
		List<ObjectEntryVersion> expectedObjectEntryVersions,
		List<ObjectEntryVersion> actualObjectEntryVersions) {

		Assert.assertEquals(
			actualObjectEntryVersions.toString(),
			expectedObjectEntryVersions.size(),
			actualObjectEntryVersions.size());

		for (int i = 0; i < expectedObjectEntryVersions.size(); i++) {
			ObjectEntryVersion expectedObjectEntryVersion =
				expectedObjectEntryVersions.get(i);
			ObjectEntryVersion actualObjectEntryVersion =
				actualObjectEntryVersions.get(i);

			Assert.assertEquals(
				expectedObjectEntryVersion.getContent(),
				actualObjectEntryVersion.getContent());
			Assert.assertEquals(
				expectedObjectEntryVersion.getVersion(),
				actualObjectEntryVersion.getVersion());
			Assert.assertEquals(
				expectedObjectEntryVersion.getStatus(),
				actualObjectEntryVersion.getStatus());
		}
	}

	private ObjectEntryVersion _createObjectEntryVersion(
		JSONObject jsonObject, int version) {

		ObjectEntryVersion objectEntryVersion =
			_objectEntryVersionLocalService.createObjectEntryVersion(
				_counterLocalService.increment());

		objectEntryVersion.setContent(
			JSONUtil.put(
				"externalReferenceCode", "externalReferenceCodeValue"
			).put(
				"keywords", JSONUtil.putAll()
			).put(
				"properties", jsonObject
			).put(
				"taxonomyCategoryBriefs", JSONUtil.putAll()
			).toString());
		objectEntryVersion.setVersion(version);
		objectEntryVersion.setStatus(WorkflowConstants.STATUS_APPROVED);

		return objectEntryVersion;
	}

	@Inject
	private CounterLocalService _counterLocalService;

	@Inject
	private DTOConverterRegistry _dtoConverterRegistry;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private ObjectEntryVersionLocalService _objectEntryVersionLocalService;

}