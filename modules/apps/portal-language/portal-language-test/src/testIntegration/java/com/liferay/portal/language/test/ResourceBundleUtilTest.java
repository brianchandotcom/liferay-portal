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

package com.liferay.portal.language.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.language.LanguageBuilderUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Tina Tian
 */
@RunWith(Arquillian.class)
public class ResourceBundleUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetString() {
		ResourceBundleLoader portalResourceBundleLoader =
			ResourceBundleLoaderUtil.getPortalResourceBundleLoader();

		ResourceBundle portalResourceBundle =
			portalResourceBundleLoader.loadResourceBundle(_UNSUPPORTED_LOCALE);

		_testGetString(portalResourceBundle, Collections.emptySet());

		Bundle bundle = FrameworkUtil.getBundle(ResourceBundleUtilTest.class);

		try (ServiceTrackerList<ResourceBundleLoader, ResourceBundleLoader>
				serviceTrackerList = ServiceTrackerListFactory.open(
					bundle.getBundleContext(), ResourceBundleLoader.class)) {

			Set<String> portalKeys = portalResourceBundle.keySet();

			for (ResourceBundleLoader resourceBundleLoader :
					serviceTrackerList) {

				_testGetString(
					resourceBundleLoader.loadResourceBundle(
						_UNSUPPORTED_LOCALE),
					portalKeys);
			}
		}
	}

	private void _testGetString(
		ResourceBundle resourceBundle, Set<String> excludedKeys) {

		if (resourceBundle == null) {
			return;
		}

		Set<String> keys = resourceBundle.keySet();

		keys.removeAll(excludedKeys);

		if (keys.isEmpty()) {
			return;
		}

		Iterator<String> iterator = keys.iterator();

		String value = ResourceBundleUtil.getString(
			resourceBundle, iterator.next());

		Assert.assertFalse(
			value + " should not contain " + LanguageBuilderUtil.AUTOMATIC_COPY,
			value.endsWith(LanguageBuilderUtil.AUTOMATIC_COPY));
		Assert.assertFalse(
			value + " should not contain " +
				LanguageBuilderUtil.AUTOMATIC_TRANSLATION,
			value.endsWith(LanguageBuilderUtil.AUTOMATIC_TRANSLATION));
	}

	private static final Locale _UNSUPPORTED_LOCALE = new Locale("en", "GB");

}