/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.search.spi.model.index.contributor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.field.builder.AssigneeObjectFieldBuilder;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.model.ObjectField;
import com.liferay.object.rest.test.util.ObjectEntryTestUtil;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryFolderLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * @author Jhosseph Gonzalez
 */
@RunWith(Arquillian.class)
public class ObjectEntryModelDocumentContributorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@FeatureFlag("LPD-17564")
	@Test
	public void testContribute() throws Exception {
		String objectFieldName = "a" + RandomTestUtil.randomString();

		ObjectField objectField = ObjectFieldUtil.createObjectField(
			0, ObjectFieldConstants.BUSINESS_TYPE_TEXT, null,
			ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
			RandomTestUtil.randomString(), objectFieldName, false, true);

		objectField.setLocalized(true);

		ObjectDefinition modifiableSystemObjectDefinition =
			ObjectDefinitionTestUtil.addModifiableSystemObjectDefinition(
				TestPropsValues.getUserId(), null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"Test" + ObjectDefinitionTestUtil.getRandomName(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_SITE, null, 1,
				Arrays.asList(objectField));

		modifiableSystemObjectDefinition =
			_objectDefinitionLocalService.publishSystemObjectDefinition(
				TestPropsValues.getUserId(),
				modifiableSystemObjectDefinition.getObjectDefinitionId());

		Date displayDate = new Date();
		String objectFieldValue = RandomTestUtil.randomString();

		ObjectEntry objectEntry = ObjectEntryTestUtil.addObjectEntry(
			TestPropsValues.getGroupId(), modifiableSystemObjectDefinition,
			HashMapBuilder.<String, Serializable>put(
				Field.DISPLAY_DATE, displayDate
			).put(
				objectFieldName, objectFieldValue
			).put(
				objectFieldName + "_i18n",
				HashMapBuilder.<String, Serializable>put(
					"en_US", objectFieldValue
				).put(
					"pt_BR", objectFieldValue + "pt_BR"
				).build()
			).build());

		Document document = _getDocument(
			modifiableSystemObjectDefinition, objectEntry);

		Field field = document.getField(Field.DISPLAY_DATE);

		Assert.assertEquals(
			DateUtil.getDate(displayDate, "yyyyMMddHHmmss", LocaleUtil.US),
			field.getValue());

		field = document.getField("objectEntryContent");

		String value = field.getValue();

		Assert.assertTrue(
			value,
			value.contains(
				StringBundler.concat(objectFieldName, ": ", objectFieldValue)));
		Assert.assertTrue(
			value,
			value.contains(
				StringBundler.concat(
					objectFieldName, ": ", objectFieldValue, "pt_BR")));

		Assert.assertEquals("false", document.get("rootDescendantNode"));

		objectEntry.setRootObjectEntryId(objectEntry.getObjectEntryId());

		document = _getDocument(modifiableSystemObjectDefinition, objectEntry);

		Assert.assertEquals("false", document.get("rootDescendantNode"));

		objectEntry.setRootObjectEntryId(objectEntry.getObjectEntryId() + 1);

		document = _getDocument(modifiableSystemObjectDefinition, objectEntry);

		Assert.assertEquals("true", document.get("rootDescendantNode"));
	}

	@FeatureFlag("LPD-17564")
	@Test
	public void testContributeWithAssigneeObjectField() throws Exception {
		String objectFieldName = "a" + RandomTestUtil.randomString();

		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.publishObjectDefinition();

		ObjectFieldUtil.addCustomObjectField(
			new AssigneeObjectFieldBuilder(
			).indexed(
				true
			).labelMap(
				RandomTestUtil.randomLocaleStringMap()
			).name(
				objectFieldName
			).objectDefinitionId(
				objectDefinition.getObjectDefinitionId()
			).userId(
				TestPropsValues.getUserId()
			).build());

		objectDefinition = _objectDefinitionLocalService.getObjectDefinition(
			objectDefinition.getObjectDefinitionId());

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		long roleClassNameId = _classNameLocalService.getClassNameId(
			Role.class.getName());
		long roleClassPK = role.getRoleId();

		ObjectEntry roleObjectEntry = ObjectEntryTestUtil.addObjectEntry(
			objectDefinition,
			HashMapBuilder.<String, Serializable>put(
				objectFieldName,
				HashMapBuilder.put(
					"classNameId", roleClassNameId
				).put(
					"classPK", roleClassPK
				).build()
			).build());

		Document roleDocument = _getDocument(objectDefinition, roleObjectEntry);

		Field roleField = roleDocument.getField("objectEntryContent");

		Assert.assertNotNull(roleField);

		String roleValue = roleField.getValue();

		Assert.assertTrue(
			roleValue,
			roleValue.contains(
				StringBundler.concat(
					objectFieldName, ": ", roleClassNameId, "_", roleClassPK)));
		Assert.assertTrue(
			roleValue,
			roleValue.contains(
				StringBundler.concat(objectFieldName, ": ", role.getName())));

		User user = UserTestUtil.addUser();

		long userClassNameId = _classNameLocalService.getClassNameId(
			User.class.getName());
		long userClassPK = user.getUserId();

		ObjectEntry userObjectEntry = ObjectEntryTestUtil.addObjectEntry(
			objectDefinition,
			HashMapBuilder.<String, Serializable>put(
				objectFieldName,
				HashMapBuilder.put(
					"classNameId", userClassNameId
				).put(
					"classPK", userClassPK
				).build()
			).build());

		Document userDocument = _getDocument(objectDefinition, userObjectEntry);

		Field userField = userDocument.getField("objectEntryContent");

		Assert.assertNotNull(userField);

		String value = userField.getValue();

		Assert.assertTrue(
			value,
			value.contains(
				StringBundler.concat(
					objectFieldName, ": ", userClassNameId, "_", userClassPK)));
		Assert.assertTrue(
			value,
			value.contains(
				StringBundler.concat(
					objectFieldName, ": ", user.getFullName())));
	}

	@FeatureFlag("LPD-17564")
	@Test
	public void testContributeWithCMSAttributes() throws Exception {
		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.publishObjectDefinition();

		ObjectEntry objectEntry = ObjectEntryTestUtil.addObjectEntry(
			objectDefinition, Collections.emptyMap());

		ObjectEntryFolder objectEntryFolder =
			_objectEntryFolderLocalService.addObjectEntryFolder(
				ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_CONTENTS,
				TestPropsValues.getGroupId(), TestPropsValues.getUserId(),
				ObjectEntryFolderConstants.
					PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
				RandomTestUtil.randomString(),
				HashMapBuilder.put(
					LocaleUtil.ENGLISH, RandomTestUtil.randomString()
				).build(),
				RandomTestUtil.randomString(), new ServiceContext());

		objectEntry.setObjectEntryFolderId(
			objectEntryFolder.getObjectEntryFolderId());

		Document document = _getDocument(objectDefinition, objectEntry);

		Assert.assertEquals("object", document.get("cms_kind"));
		Assert.assertEquals("true", document.get("cms_root"));
		Assert.assertEquals("contents", document.get("cms_section"));
	}

	private Document _getDocument(
			ObjectDefinition objectDefinition, ObjectEntry objectEntry)
		throws Exception {

		Document document = new DocumentImpl();

		ModelDocumentContributor<ObjectEntry>
			objectEntryModelDocumentContributor =
				_getObjectEntryModelDocumentContributor(objectDefinition);

		objectEntryModelDocumentContributor.contribute(document, objectEntry);

		return document;
	}

	private ModelDocumentContributor<ObjectEntry>
			_getObjectEntryModelDocumentContributor(
				ObjectDefinition objectDefinition)
		throws Exception {

		Bundle bundle = FrameworkUtil.getBundle(
			ObjectEntryModelDocumentContributorTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		List<ServiceReference<ModelDocumentContributor<ObjectEntry>>>
			serviceReferences = new ArrayList<>(
				bundleContext.getServiceReferences(
					(Class<ModelDocumentContributor<ObjectEntry>>)
						(Class<?>)ModelDocumentContributor.class,
					"(indexer.class.name=" + objectDefinition.getClassName() +
						")"));

		return bundleContext.getService(serviceReferences.get(0));
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryFolderLocalService _objectEntryFolderLocalService;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

}