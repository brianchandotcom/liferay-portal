/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.template.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.template.engine.TemplateContextHelper;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Iván Zaera Avellón
 */
@RunWith(Arquillian.class)
public class TemplateContextHelperTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testHelperRestrictedUtilitiesHaveBlankNonceAttribute()
		throws Exception {

		TemplateContextHelper templateContextHelper =
			new TemplateContextHelper();

		Map<String, Object> helperUtilities =
			templateContextHelper.getHelperUtilities(true);

		Assert.assertTrue(helperUtilities.containsKey("nonceAttribute"));
	}

	@Test
	public void testPrepareOverridesBlankNonceAttribute() {
		TemplateContextHelper templateContextHelper =
			new TemplateContextHelper();

		Map<String, Object> contextObjects = new HashMap<>();

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			"com.liferay.portal.security.content.security.policy.internal." +
				"ContentSecurityPolicyNonceManager#NONCE",
			"TEST_NONCE");

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, new ThemeDisplay());

		templateContextHelper.prepare(contextObjects, mockHttpServletRequest);

		Assert.assertEquals(
			" nonce=\"TEST_NONCE\"", contextObjects.get("nonceAttribute"));
	}

	@Test
	public void testUnrestrictedHelperUtilitiesHaveBlankNonceAttribute() {
		TemplateContextHelper templateContextHelper =
			new TemplateContextHelper();

		Map<String, Object> helperUtilities =
			templateContextHelper.getHelperUtilities(false);

		Assert.assertTrue(helperUtilities.containsKey("nonceAttribute"));
	}

}