/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.web.internal.portlet.display.template.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.display.template.PortletDisplayTemplate;
import com.liferay.wiki.model.WikiPage;

import java.util.List;
import java.util.Objects;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mikel Lorza
 */
@RunWith(Arquillian.class)
public class WikiPortletDisplayTemplateHandlerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_enabled = FeatureFlagManagerUtil.isEnabled("LPD-35013");

		PropsTestUtil.setProps(
			"feature.flag.LPD-35013", Boolean.TRUE.toString());
	}

	@After
	public void tearDown() {
		PropsUtil.setProps(_props);
	}

	@Test
	public void testGetPortletDisplayTemplateHandlers() {
		Assert.assertTrue(
			_contains(
				WikiPage.class.getName(),
				_portletDisplayTemplate.getPortletDisplayTemplateHandlers()));

		PropsTestUtil.setProps(
			"feature.flag.LPD-35013", Boolean.FALSE.toString());

		Assert.assertFalse(
			_contains(
				WikiPage.class.getName(),
				_portletDisplayTemplate.getPortletDisplayTemplateHandlers()));
	}

	private boolean _contains(
		String className, List<TemplateHandler> templateHandlers) {

		for (TemplateHandler templateHandler : templateHandlers) {
			if (Objects.equals(templateHandler.getClassName(), className)) {
				return true;
			}
		}

		return false;
	}

	private boolean _enabled;

	@Inject
	private PortletDisplayTemplate _portletDisplayTemplate;

	@Inject
	private Props _props;

}