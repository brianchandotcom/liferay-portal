/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.content.search.web.internal.portlet.display.template.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.display.template.PortletDisplayTemplate;

import java.util.List;
import java.util.Objects;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Fabio Monaco
 */
@RunWith(Arquillian.class)
public class CPSearchResultsPortletDisplayTemplateHandlerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetName() {
		TemplateHandler templateHandler = _getTemplateHandler(
			CPPortletKeys.CP_SEARCH_RESULTS);

		Assert.assertEquals(
			"Commerce Search Results Template",
			templateHandler.getName(LocaleUtil.US));
	}

	@Test
	public void testGetPortletTitle() {
		Assert.assertEquals(
			"Commerce Search Results",
			_portal.getPortletTitle(
				CPPortletKeys.CP_SEARCH_RESULTS, LocaleUtil.US));
	}

	private TemplateHandler _getTemplateHandler(String resourceName) {
		List<TemplateHandler> templateHandlers =
			_portletDisplayTemplate.getPortletDisplayTemplateHandlers();

		for (TemplateHandler templateHandler : templateHandlers) {
			if (Objects.equals(
					templateHandler.getResourceName(), resourceName)) {

				return templateHandler;
			}
		}

		return null;
	}

	@Inject
	private Portal _portal;

	@Inject
	private PortletDisplayTemplate _portletDisplayTemplate;

}