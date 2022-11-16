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

package com.liferay.fragment.contributor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.contributor.FragmentCollectionContributor;
import com.liferay.fragment.contributor.FragmentCollectionContributorRegistry;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class FragmentCollectionContributorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testRegisterContributedFragmentEntries() {
		String fragmentCollectionContributorKey = RandomTestUtil.randomString();

		TestFragmentCollectionContributor testFragmentCollectionContributor =
			new TestFragmentCollectionContributor(
				fragmentCollectionContributorKey,
				HashMapBuilder.put(
					-1,
					_getFragmentEntry(
						"test-unsupported-fragment-entry",
						RandomTestUtil.randomString(), -1)
				).put(
					FragmentConstants.TYPE_COMPONENT,
					_getFragmentEntry(
						"test-component-fragment-entry",
						RandomTestUtil.randomString(),
						FragmentConstants.TYPE_COMPONENT)
				).put(
					FragmentConstants.TYPE_INPUT,
					_getFragmentEntry(
						"test-input-fragment-entry",
						RandomTestUtil.randomString(),
						FragmentConstants.TYPE_INPUT)
				).put(
					FragmentConstants.TYPE_SECTION,
					_getFragmentEntry(
						"test-section-fragment-entry",
						RandomTestUtil.randomString(),
						FragmentConstants.TYPE_SECTION)
				).build());

		ServiceRegistration<?> serviceRegistration = _getServiceRegistration(
			testFragmentCollectionContributor);

		try {
			Map<String, FragmentEntry> fragmentEntries =
				_fragmentCollectionContributorRegistry.getFragmentEntries();

			Assert.assertNotNull(
				fragmentEntries.get("test-component-fragment-entry"));
			Assert.assertNotNull(
				fragmentEntries.get("test-input-fragment-entry"));
			Assert.assertNotNull(
				fragmentEntries.get("test-section-fragment-entry"));
			Assert.assertNull(
				fragmentEntries.get("test-unsupported-fragment-entry"));
		}
		finally {
			serviceRegistration.unregister();
		}
	}

	@Test
	public void testRegisterFragmentCollectionContributor() {
		String fragmentCollectionContributorKey = RandomTestUtil.randomString();

		ServiceRegistration<?> serviceRegistration = _getServiceRegistration(
			new TestFragmentCollectionContributor(
				fragmentCollectionContributorKey, new HashMap<>()));

		try {
			Assert.assertNotNull(
				_fragmentCollectionContributorRegistry.
					getFragmentCollectionContributor(
						fragmentCollectionContributorKey));
		}
		finally {
			serviceRegistration.unregister();
		}
	}

	private FragmentEntry _getFragmentEntry(String key, String html, int type) {
		FragmentEntry fragmentEntry =
			FragmentEntryLocalServiceUtil.createFragmentEntry(0L);

		fragmentEntry.setFragmentEntryKey(key);
		fragmentEntry.setName(RandomTestUtil.randomString());
		fragmentEntry.setCss(null);
		fragmentEntry.setHtml(html);
		fragmentEntry.setJs(null);
		fragmentEntry.setConfiguration(null);
		fragmentEntry.setType(type);

		return fragmentEntry;
	}

	private ServiceRegistration<?> _getServiceRegistration(
		TestFragmentCollectionContributor testFragmentCollectionContributor) {

		Bundle bundle = FrameworkUtil.getBundle(
			FragmentCollectionContributorTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		return bundleContext.registerService(
			FragmentCollectionContributor.class,
			testFragmentCollectionContributor,
			MapUtil.singletonDictionary(
				"fragment.collection.key",
				testFragmentCollectionContributor.getFragmentCollectionKey()));
	}

	@Inject
	private FragmentCollectionContributorRegistry
		_fragmentCollectionContributorRegistry;

	private static class TestFragmentCollectionContributor
		implements FragmentCollectionContributor {

		public TestFragmentCollectionContributor(
			String fragmentCollectionKey,
			Map<Integer, FragmentEntry> fragmentEntriesMap) {

			_fragmentCollectionKey = fragmentCollectionKey;
			_fragmentEntriesMap = fragmentEntriesMap;
		}

		@Override
		public String getFragmentCollectionKey() {
			return _fragmentCollectionKey;
		}

		@Override
		public List<FragmentEntry> getFragmentEntries() {
			return Collections.emptyList();
		}

		@Override
		public List<FragmentEntry> getFragmentEntries(int type) {
			FragmentEntry fragmentEntry = _fragmentEntriesMap.get(type);

			if (fragmentEntry != null) {
				return Collections.singletonList(fragmentEntry);
			}

			return Collections.emptyList();
		}

		@Override
		public List<FragmentEntry> getFragmentEntries(Locale locale) {
			return Collections.emptyList();
		}

		@Override
		public String getName() {
			return "Test Fragment Collection Contributor";
		}

		@Override
		public Map<Locale, String> getNames() {
			return HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), getName()
			).build();
		}

		private final String _fragmentCollectionKey;
		private final Map<Integer, FragmentEntry> _fragmentEntriesMap;

	}

}