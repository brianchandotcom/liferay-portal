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
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.ArrayList;
import java.util.Collections;
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

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.runtime.ServiceComponentRuntime;
import org.osgi.service.component.runtime.dto.ComponentDescriptionDTO;
import org.osgi.util.promise.Promise;

/**
 * @author Michael Bowerman
 */
@DataGuard(scope = DataGuard.Scope.NONE)
@RunWith(Arquillian.class)
public class FragmentCollectionContributorRegistryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			FragmentCollectionContributorRegistryTest.class);

		_bundleContext = bundle.getBundleContext();

		_fragmentEntry = FragmentEntryLocalServiceUtil.createFragmentEntry(0L);

		_fragmentEntry.setFragmentEntryKey("test-component-fragment-entry");
		_fragmentEntry.setName(RandomTestUtil.randomString());
		_fragmentEntry.setCss(RandomTestUtil.randomString());
		_fragmentEntry.setHtml(RandomTestUtil.randomString());
		_fragmentEntry.setJs(RandomTestUtil.randomString());
		_fragmentEntry.setConfiguration(null);
		_fragmentEntry.setType(FragmentConstants.TYPE_COMPONENT);

		_group = GroupTestUtil.addGroup();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());
	}

	@After
	public void tearDown() throws PortalException {
		for (Long fragmentEntryLinkId : _fragmentEntryLinkIds) {
			_fragmentEntryLinkLocalService.deleteFragmentEntryLink(
				fragmentEntryLinkId);
		}

		_fragmentEntryLinkIds.clear();
	}

	@Test
	public void testActivate() throws Exception {
		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		long segmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				layout.getPlid());

		for (int i = 0; i < _FRAGMENT_ENTRY_LINKS_COUNT; i++) {
			_addFragmentEntryLink(
				segmentsExperienceId, layout.getPlid(),
				_fragmentEntry.getHtml());
		}

		FragmentEntryLink modifiedFragmentEntryLink = _addFragmentEntryLink(
			segmentsExperienceId, layout.getPlid(), StringPool.BLANK);

		Class<?> clazz = _fragmentCollectionContributorRegistry.getClass();

		ComponentDescriptionDTO componentDescriptionDTO =
			_serviceComponentRuntime.getComponentDescriptionDTO(
				FrameworkUtil.getBundle(clazz), clazz.getName());

		Promise<Void> promise = _serviceComponentRuntime.disableComponent(
			componentDescriptionDTO);

		promise.getValue();

		promise = _serviceComponentRuntime.enableComponent(
			componentDescriptionDTO);

		promise.getValue();

		FragmentCollectionContributorRegistry
			fragmentCollectionContributorRegistry = _bundleContext.getService(
				_bundleContext.getServiceReference(
					FragmentCollectionContributorRegistry.class));

		fragmentCollectionContributorRegistry.getFragmentEntries();

		TestFragmentCollectionContributor testFragmentCollectionContributor =
			new TestFragmentCollectionContributor(
				"test",
				HashMapBuilder.put(
					FragmentConstants.TYPE_COMPONENT, _fragmentEntry
				).build());

		ServiceRegistration<?> serviceRegistration = null;

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			serviceRegistration = _bundleContext.registerService(
				FragmentCollectionContributor.class,
				testFragmentCollectionContributor,
				MapUtil.singletonDictionary(
					"fragment.collection.key",
					testFragmentCollectionContributor.
						getFragmentCollectionKey()));

			Assert.assertEquals(
				_fragmentEntry,
				fragmentCollectionContributorRegistry.getFragmentEntry(
					_fragmentEntry.getFragmentEntryKey()));

			modifiedFragmentEntryLink =
				_fragmentEntryLinkLocalService.getFragmentEntryLink(
					modifiedFragmentEntryLink.getFragmentEntryLinkId());

			Assert.assertEquals(
				_fragmentEntry.getHtml(), modifiedFragmentEntryLink.getHtml());
		}
		finally {
			if (serviceRegistration != null) {
				serviceRegistration.unregister();
			}
		}
	}

	private FragmentEntryLink _addFragmentEntryLink(
			long segmentsExperienceId, long plid, String html)
		throws Exception {

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(), 0, 0,
				segmentsExperienceId, plid, _fragmentEntry.getCss(), html,
				_fragmentEntry.getJs(), _fragmentEntry.getConfiguration(),
				StringBundler.concat(
					"{\"instanceid\": \"",
					RandomTestUtil.randomString(_FRAGMENT_ENTRY_LINK_SIZE),
					"\"}"),
				StringPool.BLANK, 0, _fragmentEntry.getFragmentEntryKey(),
				FragmentConstants.TYPE_COMPONENT, _serviceContext);

		_fragmentEntryLinkIds.add(fragmentEntryLink.getFragmentEntryLinkId());

		return fragmentEntryLink;
	}

	private static final int _FRAGMENT_ENTRY_LINK_SIZE = 3000;

	private static final int _FRAGMENT_ENTRY_LINKS_COUNT = 10;

	@Inject
	private static ServiceComponentRuntime _serviceComponentRuntime;

	private BundleContext _bundleContext;

	@Inject
	private FragmentCollectionContributorRegistry
		_fragmentCollectionContributorRegistry;

	private FragmentEntry _fragmentEntry;
	private final List<Long> _fragmentEntryLinkIds = new ArrayList<>();

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	private ServiceContext _serviceContext;

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
			return RandomTestUtil.randomString();
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