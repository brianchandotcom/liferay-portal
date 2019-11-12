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
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class FragmentCollectionContributorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceRegistration = registry.registerService(
			FragmentCollectionContributor.class,
			new TestFragmentCollectionContributor());
	}

	@After
	public void tearDown() {
		_serviceRegistration.unregister();
	}

	@Test
	public void testRegisterContributedFragmentEntries() {
		Map<String, FragmentEntry> fragmentEntries =
			_fragmentCollectionContributorTracker.getFragmentEntries();

		Assert.assertNotNull(
			fragmentEntries.get(
				TestFragmentCollectionContributor.
					TEST_COMPONENT_FRAGMENT_ENTRY));
		Assert.assertNotNull(
			fragmentEntries.get(
				TestFragmentCollectionContributor.TEST_SECTION_FRAGMENT_ENTRY));
		Assert.assertNull(
			fragmentEntries.get(
				TestFragmentCollectionContributor.
					TEST_UNSUPPORTED_FRAGMENT_ENTRY));
	}

	@Test
	public void testRegisterFragmentCollectionContributor() {
		_fragmentCollectionContributorTracker.getFragmentCollectionContributor(
			TestFragmentCollectionContributor.TEST_FRAGMENT_COLLECTION_KEY);

		Assert.assertNotNull(
			_fragmentCollectionContributorTracker.
				getFragmentCollectionContributor(
					TestFragmentCollectionContributor.
						TEST_FRAGMENT_COLLECTION_KEY));
	}

	@Inject
	private FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;

	private ServiceRegistration<FragmentCollectionContributor>
		_serviceRegistration;

	private static class TestFragmentCollectionContributor
		implements FragmentCollectionContributor {

		public static final String TEST_COMPONENT_FRAGMENT_ENTRY =
			"test-component-fragment-entry";

		public static final String TEST_FRAGMENT_COLLECTION_KEY =
			"test-fragment-collection-contributor";

		public static final String TEST_SECTION_FRAGMENT_ENTRY =
			"test-section-fragment-entry";

		public static final String TEST_UNSUPPORTED_FRAGMENT_ENTRY =
			"test-unsuported-fragment-entry";

		@Override
		public String getFragmentCollectionKey() {
			return TEST_FRAGMENT_COLLECTION_KEY;
		}

		@Override
		public List<FragmentEntry> getFragmentEntries() {
			return Collections.emptyList();
		}

		@Override
		public List<FragmentEntry> getFragmentEntries(int type) {
			List<FragmentEntry> fragmentEntries = new ArrayList<>();

			if (type == FragmentConstants.TYPE_COMPONENT) {
				fragmentEntries.add(
					_getFragmentEntry(TEST_COMPONENT_FRAGMENT_ENTRY, type));
			}
			else if (type == FragmentConstants.TYPE_SECTION) {
				fragmentEntries.add(
					_getFragmentEntry(TEST_SECTION_FRAGMENT_ENTRY, type));
			}
			else {
				fragmentEntries.add(
					_getFragmentEntry(
						TEST_UNSUPPORTED_FRAGMENT_ENTRY,
						RandomTestUtil.randomInt()));
			}

			return fragmentEntries;
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
			return new HashMap<Locale, String>() {
				{
					put(LocaleUtil.getSiteDefault(), getName());
				}
			};
		}

		private FragmentEntry _getFragmentEntry(String key, int type) {
			FragmentEntry fragmentEntry =
				FragmentEntryLocalServiceUtil.createFragmentEntry(0L);

			fragmentEntry.setFragmentEntryKey(key);
			fragmentEntry.setName(RandomTestUtil.randomString());
			fragmentEntry.setCss(null);
			fragmentEntry.setHtml(RandomTestUtil.randomString());
			fragmentEntry.setJs(null);
			fragmentEntry.setConfiguration(null);
			fragmentEntry.setType(type);

			return fragmentEntry;
		}

	}

}