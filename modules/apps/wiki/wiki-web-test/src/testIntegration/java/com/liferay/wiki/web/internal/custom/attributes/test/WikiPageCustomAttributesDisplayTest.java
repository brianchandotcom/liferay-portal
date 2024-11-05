/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.web.internal.custom.attributes.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.expando.kernel.model.CustomAttributesDisplay;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.props.test.util.PropsTemporarySwapper;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.wiki.model.WikiPage;

import java.util.List;
import java.util.Objects;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mikel Lorza
 */
@RunWith(Arquillian.class)
public class WikiPageCustomAttributesDisplayTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetCustomAttributesDisplays() {
		Assert.assertTrue(
			_contains(
				WikiPage.class.getName(),
				_portletLocalService.getCustomAttributesDisplays()));

		try (PropsTemporarySwapper propsTemporarySwapper =
				new PropsTemporarySwapper(
					"feature.flag.LPD-35013", Boolean.FALSE.toString())) {

			Assert.assertFalse(
				_contains(
					WikiPage.class.getName(),
					_portletLocalService.getCustomAttributesDisplays()));
		}
	}

	private boolean _contains(
		String className,
		List<CustomAttributesDisplay> customAttributesDisplays) {

		for (CustomAttributesDisplay customAttributesDisplay :
				customAttributesDisplays) {

			if (Objects.equals(
					customAttributesDisplay.getClassName(), className)) {

				return true;
			}
		}

		return false;
	}

	@Inject
	private PortletLocalService _portletLocalService;

}