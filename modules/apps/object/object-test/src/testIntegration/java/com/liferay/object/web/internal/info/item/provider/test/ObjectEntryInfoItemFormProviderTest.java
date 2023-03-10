/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.object.web.internal.info.item.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.field.builder.AttachmentObjectFieldBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class ObjectEntryInfoItemFormProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-176083", "true"
			).build());
	}

	@After
	public void tearDown() {
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-176083", "false"
			).build());
	}

	@Test
	public void testObjectEntryInfoItemFormProviderWithAttachment()
		throws Exception {

		ObjectField attachmentObjectField = _getAttachmentObjectField(
			"attachment",
			Arrays.asList(
				_createObjectFieldSetting("acceptedFileExtensions", "txt"),
				_createObjectFieldSetting("fileSource", "documentsAndMedia"),
				_createObjectFieldSetting("maximumFileSize", "100")));

		ObjectDefinition objectDefinition = null;

		try {
			objectDefinition = _addAndPublishObjectDefinition(
				attachmentObjectField);

			InfoItemFormProvider<?> infoItemFormProvider =
				_infoItemServiceRegistry.getFirstInfoItemService(
					InfoItemFormProvider.class,
					objectDefinition.getClassName());

			InfoForm infoForm = infoItemFormProvider.getInfoForm(
				String.valueOf(objectDefinition.getObjectDefinitionId()));

			Assert.assertNotNull(infoForm);

			Assert.assertNotNull(
				infoForm.getInfoField(attachmentObjectField.getName()));

			ObjectField persistedAttachmentObjectField =
				_objectFieldLocalService.getObjectField(
					objectDefinition.getObjectDefinitionId(),
					attachmentObjectField.getName());

			Assert.assertNotNull(
				infoForm.getInfoField(
					persistedAttachmentObjectField.getObjectFieldId() +
						"#downloadURL"));
			Assert.assertNotNull(
				infoForm.getInfoField(
					persistedAttachmentObjectField.getObjectFieldId() +
						"#fileName"));
			Assert.assertNotNull(
				infoForm.getInfoField(
					persistedAttachmentObjectField.getObjectFieldId() +
						"#mimeType"));
			Assert.assertNotNull(
				infoForm.getInfoField(
					persistedAttachmentObjectField.getObjectFieldId() +
						"#size"));
		}
		finally {
			if (objectDefinition != null) {
				_objectDefinitionLocalService.deleteObjectDefinition(
					objectDefinition.getObjectDefinitionId());
			}
		}
	}

	private ObjectDefinition _addAndPublishObjectDefinition(
			ObjectField attachmentObjectField)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(), false,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_SITE,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Arrays.asList(attachmentObjectField));

		return _objectDefinitionLocalService.publishCustomObjectDefinition(
			TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId());
	}

	private ObjectFieldSetting _createObjectFieldSetting(
		String name, String value) {

		ObjectFieldSetting objectFieldSetting =
			_objectFieldSettingLocalService.createObjectFieldSetting(0L);

		objectFieldSetting.setName(name);
		objectFieldSetting.setValue(value);

		return objectFieldSetting;
	}

	private ObjectField _getAttachmentObjectField(
		String name, List<ObjectFieldSetting> objectFieldSettings) {

		return new AttachmentObjectFieldBuilder(
		).labelMap(
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString())
		).name(
			name
		).objectFieldSettings(
			objectFieldSettings
		).build();
	}

	@Inject
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

	@Inject
	private ObjectFieldSettingLocalService _objectFieldSettingLocalService;

}