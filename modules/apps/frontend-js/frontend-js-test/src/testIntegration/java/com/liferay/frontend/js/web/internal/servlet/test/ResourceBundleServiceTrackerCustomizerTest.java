/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.servlet.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.frontend.js.JSLanguage;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ResourceBundle;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Iván Zaera Avellón
 */
@RunWith(Arquillian.class)
public class ResourceBundleServiceTrackerCustomizerTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testHashChangesWhenResourceBundleIsDeployed() {
		String hash = _jsLanguage.getHash();

		Bundle bundle = FrameworkUtil.getBundle(
			ResourceBundleServiceTrackerCustomizerTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		Package pkg =
			ResourceBundleServiceTrackerCustomizerTest.class.getPackage();

		ServiceRegistration<ResourceBundle> serviceRegistration =
			bundleContext.registerService(
				ResourceBundle.class,
				ResourceBundle.getBundle(
					pkg.getName() + ".dependencies.Language"),
				HashMapDictionaryBuilder.<String, Object>put(
					"language.id", "en"
				).put(
					"service.ranking", Integer.MAX_VALUE
				).build());

		try {
			Assert.assertNotEquals(
				"Hash did not change after deployment of ResourceBundle", hash,
				_jsLanguage.getHash());
		}
		finally {
			serviceRegistration.unregister();
		}

		Assert.assertEquals(
			"Hash change was not reverted after undeployment of ResourceBundle",
			hash, _jsLanguage.getHash());
	}

	@Inject
	private JSLanguage _jsLanguage;

}