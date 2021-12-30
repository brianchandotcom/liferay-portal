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

package com.liferay.dynamic.data.mapping.form.field.type.internal.captcha;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldTypeSettingsTestCase;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.petra.string.StringBundler;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.mockito.expectation.PowerMockitoStubber;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Carolina Barbosa
 */
@RunWith(PowerMockRunner.class)
public class CaptchaDDMFormFieldTemplateContextContributorTest
	extends BaseDDMFormFieldTypeSettingsTestCase {

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
			captchaDDMFormFieldTemplateContextContributor = PowerMockito.spy(
				_captchaDDMFormFieldTemplateContextContributor);

		PowerMockitoStubber powerMockitoStubber = PowerMockito.doReturn(html);

		powerMockitoStubber.when(
			captchaDDMFormFieldTemplateContextContributor
		).renderCaptchaTag(
			Matchers.any(DDMFormField.class),
			Matchers.any(DDMFormFieldRenderingContext.class)
		);

		return captchaDDMFormFieldTemplateContextContributor;
	}

	private final CaptchaDDMFormFieldTemplateContextContributor
		_captchaDDMFormFieldTemplateContextContributor =
			new CaptchaDDMFormFieldTemplateContextContributor();

}