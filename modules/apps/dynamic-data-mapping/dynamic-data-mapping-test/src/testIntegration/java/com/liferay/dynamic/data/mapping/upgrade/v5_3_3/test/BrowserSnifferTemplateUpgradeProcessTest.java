/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.upgrade.v5_3_3.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.service.test.BaseTemplateUpgradeProcessTestCase;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Albert Gomes Cabral
 */
@RunWith(Arquillian.class)
public class BrowserSnifferTemplateUpgradeProcessTest
	extends BaseTemplateUpgradeProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testUpgradeProcessTemplateRemoveBrowserSniffer()
		throws Exception {

		addDDMTemplate(".v5_3_3/ddm-template-browser-sniffer-content.ftl");

		addFragmentEntry(".v5_3_3/fragment-entry-browser-sniffer-content.html");

		runTemplateUpgrade();

		Assert.assertEquals(
			read(".v5_3_3/expected-ddm-template-browser-sniffer-content.ftl"),
			getDDMTemplate().getScript());

		Assert.assertEquals(
			read(
				".v5_3_3/expected-fragment-entry-browser-sniffer-content.html"),
			getFragmentEntry().getHtml());
	}

	@Override
	protected String getUpgradeStepClassName() throws Exception {
		return "com.liferay.dynamic.data.mapping.internal.upgrade.v5_3_3." +
			"BrowserSnifferTemplateUpgradeProcess";
	}

}