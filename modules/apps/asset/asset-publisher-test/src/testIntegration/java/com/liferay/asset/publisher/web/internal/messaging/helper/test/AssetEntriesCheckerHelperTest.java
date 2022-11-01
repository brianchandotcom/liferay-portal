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

package com.liferay.asset.publisher.web.internal.messaging.helper.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.asset.publisher.test.util.AssetPublisherTestUtil;
import com.liferay.asset.publisher.util.AssetPublisherHelper;
import com.liferay.asset.util.AssetHelper;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.lang.reflect.Constructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author István András Dézsi
 */
@RunWith(Arquillian.class)
public class AssetEntriesCheckerHelperTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addTypePortletLayout(_group.getGroupId());

		_setUpAssetEntriesCheckerHelper();
	}

	@Test
	public void testGetAssetEntries() throws Exception {
		String portletId1 = LayoutTestUtil.addPortletToLayout(
			_layout, AssetPublisherPortletKeys.ASSET_PUBLISHER);

		String portletId2 = LayoutTestUtil.addPortletToLayout(
			_layout, AssetPublisherPortletKeys.ASSET_PUBLISHER);

		AssetEntry assetEntry1 = _addAssetEntry(portletId1, false);

		AssetEntry assetEntry2 = _addAssetEntry(portletId2, false);

		AssetEntry assetEntry3 = _addAssetEntry(portletId2, true);

		AssetEntry assetEntry4 = _addAssetEntry(portletId2, true);

		PortletPreferences portletId1PortletPreferences =
			LayoutTestUtil.getPortletPreferences(_layout, portletId1);

		PortletPreferences portletId2PortletPreferences =
			LayoutTestUtil.getPortletPreferences(_layout, portletId2);

		List<AssetEntry> expectedPortletId1AssetEntries = new ArrayList<>(
			Arrays.asList(assetEntry1, assetEntry2, assetEntry3, assetEntry4));

		List<AssetEntry> actualPortletId1AssetEntries =
			ReflectionTestUtil.invoke(
				_assetEntriesCheckerHelper, "_getAssetEntries",
				new Class<?>[] {PortletPreferences.class, Layout.class},
				portletId1PortletPreferences, _layout);

		_assertAssetEntries(
			expectedPortletId1AssetEntries, actualPortletId1AssetEntries);

		List<AssetEntry> expectedPortletId2AssetEntries = new ArrayList<>(
			Arrays.asList(assetEntry3, assetEntry4));

		List<AssetEntry> actualPortletId2AssetEntries =
			ReflectionTestUtil.invoke(
				_assetEntriesCheckerHelper, "_getAssetEntries",
				new Class<?>[] {PortletPreferences.class, Layout.class},
				portletId2PortletPreferences, _layout);

		_assertAssetEntries(
			expectedPortletId2AssetEntries, actualPortletId2AssetEntries);
	}

	private AssetEntry _addAssetEntry(String portletId, boolean manualMode)
		throws Exception {

		BlogsEntry blogsEntry = _blogsEntryLocalService.addEntry(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, RandomTestUtil.randomString(),
			1, 1, 1965, 0, 0, true, true, null, StringPool.BLANK, null, null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		AssetEntry assetEntry = _assetEntryLocalService.getEntry(
			_group.getGroupId(), blogsEntry.getUuid());

		if (manualMode) {
			PortletPreferences portletPreferences =
				LayoutTestUtil.getPortletPreferences(_layout, portletId);

			portletPreferences.setValue("selectionStyle", "manual");

			String[] assetEntryXmls = portletPreferences.getValues(
				"assetEntryXml", new String[0]);

			String assetEntryXml = AssetPublisherTestUtil.getAssetEntryXml(
				assetEntry);

			if (!ArrayUtil.contains(assetEntryXmls, assetEntryXml)) {
				assetEntryXmls = ArrayUtil.append(
					assetEntryXmls, assetEntryXml);

				portletPreferences.setValues("assetEntryXml", assetEntryXmls);

				portletPreferences.store();
			}
		}

		return assetEntry;
	}

	private void _assertAssetEntries(
		List<AssetEntry> expectedAssetEntries,
		List<AssetEntry> actualAssetEntries) {

		Assert.assertEquals(
			actualAssetEntries.toString(), expectedAssetEntries.size(),
			actualAssetEntries.size());

		Iterator<AssetEntry> expectedAssetEntriesIterator =
			expectedAssetEntries.iterator();
		Iterator<AssetEntry> actualAssetEntriesIterator =
			expectedAssetEntries.iterator();

		while (expectedAssetEntriesIterator.hasNext() &&
			   actualAssetEntriesIterator.hasNext()) {

			AssetEntry expectedAssetEntry = expectedAssetEntriesIterator.next();
			AssetEntry actualAssetEntry = actualAssetEntriesIterator.next();

			Assert.assertEquals(
				expectedAssetEntry.getClassName(),
				actualAssetEntry.getClassName());
			Assert.assertEquals(
				expectedAssetEntry.getClassUuid(),
				actualAssetEntry.getClassUuid());
		}
	}

	private void _setUpAssetEntriesCheckerHelper() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			AssetEntriesCheckerHelperTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		Bundle assetPublisherWebBundle = null;

		for (Bundle curBundle : bundleContext.getBundles()) {
			if (Objects.equals(
					curBundle.getSymbolicName(),
					"com.liferay.asset.publisher.web")) {

				assetPublisherWebBundle = curBundle;

				break;
			}
		}

		Assert.assertNotNull(
			"Unable to find asset-publisher-web bundle",
			assetPublisherWebBundle);

		Class<?> clazz = assetPublisherWebBundle.loadClass(
			"com.liferay.asset.publisher.web.internal.messaging.helper." +
				"AssetEntriesCheckerHelper");

		Constructor<?> constructor = clazz.getConstructor();

		_assetEntriesCheckerHelper = constructor.newInstance();

		ReflectionTestUtil.setFieldValue(
			_assetEntriesCheckerHelper, "_assetEntryLocalService",
			_assetEntryLocalService);

		ReflectionTestUtil.setFieldValue(
			_assetEntriesCheckerHelper, "_assetHelper", _assetHelper);

		ReflectionTestUtil.setFieldValue(
			_assetEntriesCheckerHelper, "_assetPublisherHelper",
			_assetPublisherHelper);

		ReflectionTestUtil.setFieldValue(
			_assetEntriesCheckerHelper, "_configurationProvider",
			_configurationProvider);

		ReflectionTestUtil.setFieldValue(
			_assetEntriesCheckerHelper, "_groupLocalService",
			_groupLocalService);
	}

	private Object _assetEntriesCheckerHelper;

	@Inject
	private AssetEntryLocalService _assetEntryLocalService;

	@Inject
	private AssetHelper _assetHelper;

	@Inject
	private AssetPublisherHelper _assetPublisherHelper;

	@Inject
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Inject
	private ConfigurationProvider _configurationProvider;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	private Layout _layout;

}