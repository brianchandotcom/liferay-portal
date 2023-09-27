/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.captcha;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldTypeSettingsTestCase;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Carolina Barbosa
 */
public class CaptchaDDMFormFieldTemplateContextContributorTest
	extends BaseDDMFormFieldTypeSettingsTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetParameters() throws Exception {
		StringBundler sb = new StringBundler(3);

		sb.append("<div><div class=\"taglib-captcha\"><img alt=\"Text to ");
		sb.append("Identify\" src=\"captcha\"><label>Text Verification");
		sb.append("</label><input type=\"text\"></div></div>");

		CaptchaDDMFormFieldTemplateContextContributor
			captchaDDMFormFieldTemplateContextContributor = _createSpy(
				sb.toString());

		Map<String, Object> parameters =
			captchaDDMFormFieldTemplateContextContributor.getParameters(
				new DDMFormField("field", "captcha"),
				new DDMFormFieldRenderingContext());

		Assert.assertEquals(sb.toString(), parameters.get("html"));
	}

	private CaptchaDDMFormFieldTemplateContextContributor _createSpy(
			String html)
		throws Exception {

		CaptchaDDMFormFieldTemplateContextContributor
			captchaDDMFormFieldTemplateContextContributor = Mockito.spy(
				_captchaDDMFormFieldTemplateContextContributor);

		Mockito.doReturn(
			html
		).when(
			captchaDDMFormFieldTemplateContextContributor
		).renderCaptchaTag(
			Mockito.any(DDMFormField.class),
			Mockito.any(DDMFormFieldRenderingContext.class)
		);

		return captchaDDMFormFieldTemplateContextContributor;
	}

	private final CaptchaDDMFormFieldTemplateContextContributor
		_captchaDDMFormFieldTemplateContextContributor =
			new CaptchaDDMFormFieldTemplateContextContributor();

}