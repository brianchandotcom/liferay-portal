/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.bulk.selection.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.bulk.selection.BulkSelectionAction;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectEntryLocalServiceUtil;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.object.test.util.ObjectRelationshipTestUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.io.Serializable;

import java.util.Collections;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Víctor Galán
 */
@RunWith(Arquillian.class)
public class UpdateObjectValuesBulkSelectionActionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testDoExecute() throws Exception {
		String fieldName = "text";

		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.addCustomObjectDefinition(
				Collections.singletonList(
					new TextObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						fieldName
					).build()));

		objectDefinition =
			ObjectDefinitionLocalServiceUtil.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				objectDefinition.getObjectDefinitionId());

		ObjectEntry objectEntry = ObjectEntryLocalServiceUtil.addObjectEntry(
			0L, TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId(), 0, null,
			HashMapBuilder.<String, Serializable>put(
				fieldName, RandomTestUtil.randomString()
			).build(),
			ServiceContextTestUtil.getServiceContext());

		String newValue = RandomTestUtil.randomString();

		ReflectionTestUtil.invoke(
			_updateObjectValuesBulkSelectionAction, "doExecute",
			new Class<?>[] {User.class, Map.class, Object.class},
			TestPropsValues.getUser(),
			HashMapBuilder.put(
				"values",
				(Serializable)
					HashMapBuilder.<String, Map<String, Serializable>>put(
						String.valueOf(objectEntry.getObjectEntryId()),
						HashMapBuilder.<String, Serializable>put(
							fieldName, newValue
						).build()
					).build()
			).build(),
			objectEntry);

		ObjectEntry updatedObjectEntry =
			_objectEntryLocalService.getObjectEntry(
				objectEntry.getObjectEntryId());

		Map<String, Serializable> values = updatedObjectEntry.getValues();

		Assert.assertEquals(newValue, values.get(fieldName));
	}

	@Test
	public void testDoExecuteWithObjectRelationship() throws Exception {
		String fieldName = "text";

		ObjectDefinition mainObjectDefinition =
			ObjectDefinitionTestUtil.addCustomObjectDefinition(
				Collections.singletonList(
					new TextObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						fieldName
					).build()));

		mainObjectDefinition =
			ObjectDefinitionLocalServiceUtil.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				mainObjectDefinition.getObjectDefinitionId());

		String innerFieldName = "innerText";

		ObjectDefinition relatedObjectDefinition =
			ObjectDefinitionTestUtil.addCustomObjectDefinition(
				Collections.singletonList(
					new TextObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						innerFieldName
					).build()));

		relatedObjectDefinition =
			ObjectDefinitionLocalServiceUtil.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				relatedObjectDefinition.getObjectDefinitionId());

		ObjectRelationship objectRelationship =
			ObjectRelationshipTestUtil.addObjectRelationship(
				_objectRelationshipLocalService, mainObjectDefinition,
				relatedObjectDefinition);

		objectRelationship =
			_objectRelationshipLocalService.updateObjectRelationship(
				objectRelationship);

		ObjectEntry objectEntry = ObjectEntryLocalServiceUtil.addObjectEntry(
			0L, TestPropsValues.getUserId(),
			mainObjectDefinition.getObjectDefinitionId(), 0, null,
			HashMapBuilder.<String, Serializable>put(
				fieldName, RandomTestUtil.randomString()
			).build(),
			ServiceContextTestUtil.getServiceContext());

		String externalReferenceCode = PortalUUIDUtil.generate();

		ObjectEntry relatedObjectEntry =
			ObjectEntryLocalServiceUtil.addObjectEntry(
				0L, TestPropsValues.getUserId(),
				relatedObjectDefinition.getObjectDefinitionId(), 0, null,
				HashMapBuilder.<String, Serializable>put(
					innerFieldName, RandomTestUtil.randomString()
				).put(
					"externalReferenceCode", externalReferenceCode
				).build(),
				ServiceContextTestUtil.getServiceContext());

		ObjectRelationshipTestUtil.relateObjectEntries(
			objectEntry.getObjectEntryId(),
			relatedObjectEntry.getObjectEntryId(), objectRelationship,
			TestPropsValues.getUserId());

		String textValue = RandomTestUtil.randomString();
		String innerTextValue = RandomTestUtil.randomString();

		Map<String, Map<String, Serializable>> updatesMap =
			HashMapBuilder.<String, Map<String, Serializable>>put(
				String.valueOf(objectEntry.getObjectEntryId()),
				HashMapBuilder.<String, Serializable>put(
					fieldName, textValue
				).put(
					objectRelationship.getName(),
					(Serializable)Collections.singletonList(
						HashMapBuilder.<String, Serializable>put(
							innerFieldName, innerTextValue
						).put(
							"externalReferenceCode", externalReferenceCode
						).build())
				).build()
			).build();

		ReflectionTestUtil.invoke(
			_updateObjectValuesBulkSelectionAction, "doExecute",
			new Class<?>[] {User.class, Map.class, Object.class},
			TestPropsValues.getUser(),
			HashMapBuilder.put(
				"values", (Serializable)updatesMap
			).build(),
			objectEntry);

		ObjectEntry updatedObjectEntry =
			_objectEntryLocalService.getObjectEntry(
				objectEntry.getObjectEntryId());

		Map<String, Serializable> values = updatedObjectEntry.getValues();

		Assert.assertEquals(textValue, values.get(fieldName));

		ObjectEntry updatedRelatedObjectEntry =
			_objectEntryLocalService.getObjectEntry(
				relatedObjectEntry.getObjectEntryId());

		values = updatedRelatedObjectEntry.getValues();

		Assert.assertEquals(innerTextValue, values.get(innerFieldName));
	}

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Inject(
		filter = "component.name=com.liferay.site.cms.site.initializer.internal.bulk.selection.UpdateObjectValuesBulkSelectionAction"
	)
	private BulkSelectionAction<Object> _updateObjectValuesBulkSelectionAction;

}