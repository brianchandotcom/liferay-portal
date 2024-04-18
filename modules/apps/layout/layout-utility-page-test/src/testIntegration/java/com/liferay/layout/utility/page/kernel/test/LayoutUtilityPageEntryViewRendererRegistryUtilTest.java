/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.utility.page.kernel.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.utility.page.kernel.LayoutUtilityPageEntryViewRendererRegistryUtil;
import com.liferay.layout.utility.page.kernel.constants.LayoutUtilityPageEntryConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Istvan Sajtos
 */
@FeatureFlags("LPD-6378")
@RunWith(Arquillian.class)
public class LayoutUtilityPageEntryViewRendererRegistryUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetLayoutUtilityPageEntryViewRenderer() {
		Assert.assertNotNull(
			LayoutUtilityPageEntryViewRendererRegistryUtil.
				getLayoutUtilityPageEntryViewRenderer(
					LayoutUtilityPageEntryConstants.TYPE_CREATE_ACCOUNT));
		Assert.assertNotNull(
			LayoutUtilityPageEntryViewRendererRegistryUtil.
				getLayoutUtilityPageEntryViewRenderer(
					LayoutUtilityPageEntryConstants.TYPE_LOGIN));
		Assert.assertNotNull(
			LayoutUtilityPageEntryViewRendererRegistryUtil.
				getLayoutUtilityPageEntryViewRenderer(
					LayoutUtilityPageEntryConstants.TYPE_FORGOT_PASSWORD));
	}

}