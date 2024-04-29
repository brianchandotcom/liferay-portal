/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type.internal.factory;

import com.liferay.client.extension.exception.ClientExtensionEntryTypeSettingsException;
import com.liferay.client.extension.type.ThemeCSSCET;
import com.liferay.client.extension.type.internal.ThemeCSSCETImpl;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Thiago Buarque
 */
public class ThemeCSSCETImplFactoryImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testValidate() throws PortalException {
		ThemeCSSCETImplFactoryImpl themeCSSCETImplFactoryImpl =
			new ThemeCSSCETImplFactoryImpl(new JSONFactoryImpl());

		themeCSSCETImplFactoryImpl.validate(
			_createThemeCSSCET("{\"frontendTokenCategories\": []}"), null);

		try {
			themeCSSCETImplFactoryImpl.validate(
				_createThemeCSSCET("{\"frontendTokenCategories\": [}"), null);

			Assert.fail();
		}
		catch (ClientExtensionEntryTypeSettingsException
					clientExtensionEntryTypeSettingsException) {

			Assert.assertEquals(
				"the-format-is-not-valid-please-upload-a-valid-frontend-" +
					"token-definition-json-file",
				clientExtensionEntryTypeSettingsException.getMessageKey());
		}
	}

	private ThemeCSSCET _createThemeCSSCET(String frontendTokenDefinitionJSON) {
		return new ThemeCSSCETImpl(
			StringPool.BLANK, 0, null, StringPool.BLANK, StringPool.BLANK, null,
			StringPool.BLANK, null, false, StringPool.BLANK, 0,
			UnicodePropertiesBuilder.put(
				"frontendTokenDefinitionJSON", frontendTokenDefinitionJSON
			).build());
	}

}