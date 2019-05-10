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

package com.liferay.ratings.transformer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.ratings.kernel.transformer.RatingsDataTransformer;
import com.liferay.ratings.kernel.transformer.RatingsDataTransformerUtil;

import java.util.Objects;

import javax.portlet.PortletPreferences;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Leon Chi
 */
@RunWith(Arquillian.class)
public class RatingsDataTransformerUtilTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		Bundle bundle = FrameworkUtil.getBundle(
			RatingsDataTransformerUtilTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceRegistration = bundleContext.registerService(
			RatingsDataTransformer.class,
			(RatingsDataTransformer)ProxyUtil.newProxyInstance(
				RatingsDataTransformer.class.getClassLoader(),
				new Class<?>[] {RatingsDataTransformer.class},
				(proxy, method, args) -> {
					if (Objects.equals(
							method.getName(), "transformRatingsData")) {

						_calledTransformRatingsData = true;
					}

					return null;
				}),
			MapUtil.singletonDictionary("service.ranking", Integer.MAX_VALUE));
	}

	@AfterClass
	public static void tearDownClass() {
		_serviceRegistration.unregister();
	}

	@Test
	public void testTransformCompanyRatingsData() throws Exception {
		_calledTransformRatingsData = false;

		PortletPreferences oldPortletPreferences = new PortletPreferencesImpl();

		oldPortletPreferences.setValue(
			"com.liferay.blogs.model.BlogsEntry_RatingsType", "like");
		oldPortletPreferences.setValue(
			"com.liferay.bookmarks.model.BookmarksEntry_RatingsType", "like");
		oldPortletPreferences.setValue(
			"com.liferay.document.library.kernel.model.DLFileEntry_RatingsType",
			"like");
		oldPortletPreferences.setValue(
			"com.liferay.journal.model.JournalArticle_RatingsType", "like");
		oldPortletPreferences.setValue(
			"com.liferay.knowledge.base.model.KBArticle_RatingsType", "like");
		oldPortletPreferences.setValue(
			"com.liferay.message.boards.model.MBDiscussion_RatingsType",
			"like");
		oldPortletPreferences.setValue(
			"com.liferay.message.boards.model.MBMessage_RatingsType", "like");
		oldPortletPreferences.setValue(
			"com.liferay.wiki.model.WikiPage_RatingsType", "like");

		RatingsDataTransformerUtil.transformCompanyRatingsData(
			1, oldPortletPreferences, _createUnicodeProperties("stars"));

		Assert.assertTrue(_calledTransformRatingsData);
	}

	@Test
	public void testTransformGroupRatingsData() throws Exception {
		_calledTransformRatingsData = false;

		RatingsDataTransformerUtil.transformGroupRatingsData(
			1, _createUnicodeProperties("like"),
			_createUnicodeProperties("stars"));

		Assert.assertTrue(_calledTransformRatingsData);
	}

	private UnicodeProperties _createUnicodeProperties(String value) {
		UnicodeProperties unicodeProperties = new UnicodeProperties();

		unicodeProperties.setProperty(
			"com.liferay.blogs.model.BlogsEntry_RatingsType", value);
		unicodeProperties.setProperty(
			"com.liferay.bookmarks.model.BookmarksEntry_RatingsType", value);
		unicodeProperties.setProperty(
			"com.liferay.document.library.kernel.model.DLFileEntry_RatingsType",
			value);
		unicodeProperties.setProperty(
			"com.liferay.journal.model.JournalArticle_RatingsType", value);
		unicodeProperties.setProperty(
			"com.liferay.knowledge.base.model.KBArticle_RatingsType", value);
		unicodeProperties.setProperty(
			"com.liferay.message.boards.model.MBDiscussion_RatingsType", value);
		unicodeProperties.setProperty(
			"com.liferay.message.boards.model.MBMessage_RatingsType", value);
		unicodeProperties.setProperty(
			"com.liferay.wiki.model.WikiPage_RatingsType", value);

		return unicodeProperties;
	}

	private static boolean _calledTransformRatingsData;
	private static ServiceRegistration<RatingsDataTransformer>
		_serviceRegistration;

}