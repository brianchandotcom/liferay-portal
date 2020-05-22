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

package com.liferay.segments.context.vocabulary.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Locale;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class DeleteSegmentsContextVocabularyConfigurationMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final TestRule testRule = new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		_locale = LocaleThreadLocal.getThemeDisplayLocale();

		LocaleThreadLocal.setThemeDisplayLocale(LocaleUtil.US);
	}

	@AfterClass
	public static void tearDownClass() {
		LocaleThreadLocal.setThemeDisplayLocale(_locale);
	}

	@Test
	public void testProcessAction() throws Exception {
		Configuration configuration = _addConfiguration();

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.setParameter(
			"pid", configuration.getPid());

		try {
			_deleteMVCActionCommand.processAction(
				mockLiferayPortletActionRequest, null);

			configuration = _configurationAdmin.getConfiguration(
				configuration.getPid(), StringPool.QUESTION);

			Assert.assertNull(configuration.getProperties());
		}
		finally {
			configuration.delete();
		}
	}

	private Configuration _addConfiguration() throws Exception {
		Configuration configuration =
			_configurationAdmin.createFactoryConfiguration(
				"com.liferay.segments.context.vocabulary.internal." +
					"configuration.SegmentsContextVocabularyConfiguration",
				StringPool.QUESTION);

		Dictionary<String, Object> configuredProperties = new Hashtable<>();

		configuredProperties.put(
			"assetVocabulary", RandomTestUtil.randomString());
		configuredProperties.put("configuration.cleaner.ignore", "true");
		configuredProperties.put("entityField", RandomTestUtil.randomString());

		configuration.update(configuredProperties);

		return configuration;
	}

	private static Locale _locale;

	@Inject
	private ConfigurationAdmin _configurationAdmin;

	@Inject(
		filter = "mvc.command.name=/delete_segments_context_vocabulary_configuration"
	)
	private MVCActionCommand _deleteMVCActionCommand;

}