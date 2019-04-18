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

package com.liferay.portlet.title.localization.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.portlet.PortletConfigFactoryUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.portlet.PortletConfig;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Preston Crary
 */
@RunWith(Arquillian.class)
public class PortletTitleTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testPortletTitles() throws Exception {
		List<String> portletIdsWithMissingTitles = new ArrayList<>();

		for (Portlet portlet : PortletLocalServiceUtil.getPortlets()) {
			String rootPortletId = portlet.getRootPortletId();

			PortletBag portletBag = PortletBagPool.get(rootPortletId);

			PortletConfig portletConfig = PortletConfigFactoryUtil.create(
				portlet, portletBag.getServletContext());

			ResourceBundle resourceBundle = portletConfig.getResourceBundle(
				LocaleUtil.getDefault());

			if ((resourceBundle != null) &&
				!resourceBundle.containsKey(
					"javax.portlet.title.".concat(rootPortletId))) {

				portletIdsWithMissingTitles.add(rootPortletId);
			}
		}

		Assert.assertTrue(
			StringBundler.concat(
				"Please update the Language.properties files for the ",
				"following portlets: ", portletIdsWithMissingTitles.toString()),
			portletIdsWithMissingTitles.isEmpty());
	}

}