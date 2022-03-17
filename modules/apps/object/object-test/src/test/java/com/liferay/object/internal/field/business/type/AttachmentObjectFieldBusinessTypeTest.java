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

package com.liferay.object.internal.field.business.type;

import com.liferay.object.exception.ObjectFieldSettingsValidationException;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Carolina Barbosa
 */
@RunWith(PowerMockRunner.class)
public class AttachmentObjectFieldBusinessTypeTest {

	@Test
	public void testValidateObjectFieldSettings() throws PortalException {
		Map<String, String> objectFieldSettings = HashMapBuilder.put(
			"acceptedFileExtensions", RandomTestUtil.randomString()
		).build();

		_assertObjectFieldSettingsValidationExceptionMessage(
			"The following settings are required for field attachment: " +
				"fileSource, maximumFileSize",
			objectFieldSettings);

		objectFieldSettings.putAll(
			HashMapBuilder.put(
				"fileSource", StringPool.BLANK
			).put(
				"invalidSetting", RandomTestUtil.randomString()
			).put(
				"maximumFileSize", StringPool.BLANK
			).build());

		_assertObjectFieldSettingsValidationExceptionMessage(
			"The following settings are not allowed for field attachment: " +
				"invalidSetting",
			objectFieldSettings);

		objectFieldSettings.remove("invalidSetting");

		_assertObjectFieldSettingsValidationExceptionMessage(
			"The following settings are required for field attachment: " +
				"fileSource, maximumFileSize",
			objectFieldSettings);

		objectFieldSettings.put("fileSource", "Test1");
		objectFieldSettings.put("maximumFileSize", "Test2");

		_assertObjectFieldSettingsValidationExceptionMessage(
			"The value Test1 of setting fileSource is not valid for field" +
				" attachment",
			objectFieldSettings);

		objectFieldSettings.put("fileSource", "userComputer");

		_assertObjectFieldSettingsValidationExceptionMessage(
			"The value Test2 of setting maximumFileSize is not valid for" +
				" field attachment",
			objectFieldSettings);

		objectFieldSettings.put("maximumFileSize", "-1");

		_assertObjectFieldSettingsValidationExceptionMessage(
			"The value -1 of setting maximumFileSize is not valid for field" +
				" attachment",
			objectFieldSettings);

		objectFieldSettings.put("maximumFileSize", "10");

		_attachmentObjectFieldBusinessType.validateObjectFieldSettings(
			"attachment", objectFieldSettings);
	}

	private void _assertObjectFieldSettingsValidationExceptionMessage(
			String message, Map<String, String> objectFieldSettings)
		throws PortalException {

		try {
			_attachmentObjectFieldBusinessType.validateObjectFieldSettings(
				"attachment", objectFieldSettings);

			Assert.fail();
		}
		catch (ObjectFieldSettingsValidationException
					objectFieldSettingsValidationException) {

			Assert.assertEquals(
				message, objectFieldSettingsValidationException.getMessage());
		}
	}

	private final AttachmentObjectFieldBusinessType
		_attachmentObjectFieldBusinessType =
			new AttachmentObjectFieldBusinessType();

}