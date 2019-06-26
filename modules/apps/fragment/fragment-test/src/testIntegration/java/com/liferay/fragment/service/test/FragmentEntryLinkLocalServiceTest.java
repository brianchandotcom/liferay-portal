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

package com.liferay.fragment.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.configuration.FragmentServiceConfiguration;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Dictionary;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Kyle Miho
 */
@RunWith(Arquillian.class)
public class FragmentEntryLinkLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@After
	public void tearDown() throws Exception {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("propagateChanges", false);

		_configurationProvider.saveCompanyConfiguration(
			FragmentServiceConfiguration.class, _group.getCompanyId(),
			properties);
	}

	@Test
	public void testAddFragmentEntryLink() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.addFragmentCollection(
				TestPropsValues.getUserId(), _group.getGroupId(),
				"Fragment Collection", StringPool.BLANK, serviceContext);

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(), null,
				"Fragment Name", StringPool.BLANK, "<div>test</div>",
				StringPool.BLANK, "{fieldSets:[]}", 0,
				FragmentConstants.TYPE_SECTION,
				WorkflowConstants.STATUS_APPROVED, serviceContext);

		long classNameId = PortalUtil.getClassNameId(Layout.class);

		long classPK = RandomTestUtil.randomLong();

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				fragmentEntry.getFragmentEntryId(), classNameId, classPK,
				fragmentEntry.getCss(), fragmentEntry.getHtml(),
				fragmentEntry.getJs(), fragmentEntry.getConfiguration(),
				StringPool.BLANK, StringPool.BLANK, 0, null, serviceContext);

		Assert.assertNotNull(
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				fragmentEntryLink.getFragmentEntryLinkId()));

		Assert.assertEquals(classNameId, fragmentEntryLink.getClassNameId());

		Assert.assertEquals(classPK, fragmentEntryLink.getClassPK());

		Assert.assertEquals(fragmentEntry.getCss(), fragmentEntryLink.getCss());

		Assert.assertEquals(fragmentEntry.getJs(), fragmentEntryLink.getJs());

		Assert.assertEquals(
			fragmentEntry.getConfiguration(),
			fragmentEntryLink.getConfiguration());

		Assert.assertEquals(
			fragmentEntry.getHtml(), fragmentEntryLink.getHtml());
	}

	@Test
	public void testAddMultipleFragmentEntryLinks() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		long classPK = RandomTestUtil.randomLong();

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.addFragmentCollection(
				TestPropsValues.getUserId(), _group.getGroupId(),
				"Fragment Collection", StringPool.BLANK, serviceContext);

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(), null,
				"Fragment Name", StringPool.BLANK, "<div>test</div>",
				StringPool.BLANK, "{fieldSets:[]}", 0,
				FragmentConstants.TYPE_SECTION,
				WorkflowConstants.STATUS_APPROVED, serviceContext);

		List<FragmentEntryLink> originalFragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinks(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		_fragmentEntryLinkLocalService.addFragmentEntryLink(
			TestPropsValues.getUserId(), _group.getGroupId(), 0,
			fragmentEntry.getFragmentEntryId(),
			PortalUtil.getClassNameId(Layout.class), classPK,
			fragmentEntry.getCss(), fragmentEntry.getHtml(),
			fragmentEntry.getJs(), fragmentEntry.getConfiguration(),
			StringPool.BLANK, StringPool.BLANK, 0, null, serviceContext);

		_fragmentEntryLinkLocalService.addFragmentEntryLink(
			TestPropsValues.getUserId(), _group.getGroupId(), 0,
			fragmentEntry.getFragmentEntryId(),
			PortalUtil.getClassNameId(Layout.class), classPK,
			fragmentEntry.getCss(), fragmentEntry.getHtml(),
			fragmentEntry.getJs(), fragmentEntry.getConfiguration(),
			StringPool.BLANK, StringPool.BLANK, 1, null, serviceContext);

		List<FragmentEntryLink> actualFragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinks(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			actualFragmentEntryLinks.toString(),
			originalFragmentEntryLinks.size() + 2,
			actualFragmentEntryLinks.size());
	}

	@Test
	public void testDeleteFragmentEntryLink() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		long classPK = RandomTestUtil.randomLong();

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.addFragmentCollection(
				TestPropsValues.getUserId(), _group.getGroupId(),
				"Fragment Collection", StringPool.BLANK, serviceContext);

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(), null,
				"Fragment Name", StringPool.BLANK, "<div>test</div>",
				StringPool.BLANK, "{fieldSets:[]}", 0,
				FragmentConstants.TYPE_SECTION,
				WorkflowConstants.STATUS_APPROVED, serviceContext);

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				fragmentEntry.getFragmentEntryId(),
				PortalUtil.getClassNameId(Layout.class), classPK,
				fragmentEntry.getCss(), fragmentEntry.getHtml(),
				fragmentEntry.getJs(), fragmentEntry.getConfiguration(),
				StringPool.BLANK, StringPool.BLANK, 0, null, serviceContext);

		_fragmentEntryLinkLocalService.deleteFragmentEntryLink(
			fragmentEntryLink.getFragmentEntryLinkId());

		Assert.assertNull(
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				fragmentEntryLink.getFragmentEntryLinkId()));
	}

	@Test
	public void testDeleteFragmentEntryLinks() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		long classPK = RandomTestUtil.randomLong();

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.addFragmentCollection(
				TestPropsValues.getUserId(), _group.getGroupId(),
				"Fragment Collection", StringPool.BLANK, serviceContext);

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(), null,
				"Fragment Name", StringPool.BLANK, "<div>test</div>",
				StringPool.BLANK, "{fieldSets:[]}", 0,
				FragmentConstants.TYPE_SECTION,
				WorkflowConstants.STATUS_APPROVED, serviceContext);

		FragmentEntryLink fragmentEntryLink1 =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				fragmentEntry.getFragmentEntryId(),
				PortalUtil.getClassNameId(Layout.class), classPK,
				fragmentEntry.getCss(), fragmentEntry.getHtml(),
				fragmentEntry.getJs(), fragmentEntry.getConfiguration(),
				StringPool.BLANK, StringPool.BLANK, 0, null, serviceContext);

		FragmentEntryLink fragmentEntryLink2 =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				fragmentEntry.getFragmentEntryId(),
				PortalUtil.getClassNameId(Layout.class), classPK,
				fragmentEntry.getCss(), fragmentEntry.getHtml(),
				fragmentEntry.getJs(), fragmentEntry.getConfiguration(),
				StringPool.BLANK, StringPool.BLANK, 0, null, serviceContext);

		_fragmentEntryLinkLocalService.deleteFragmentEntryLinks(
			_group.getGroupId());

		Assert.assertNull(
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				fragmentEntryLink1.getFragmentEntryLinkId()));

		Assert.assertNull(
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				fragmentEntryLink2.getFragmentEntryLinkId()));
	}

	@Test
	public void testUpdateFragmentEntryLinkPosition() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.addFragmentCollection(
				TestPropsValues.getUserId(), _group.getGroupId(),
				"Fragment Collection", StringPool.BLANK, serviceContext);

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(), null,
				"Fragment Name", StringPool.BLANK, "<div>test</div>",
				StringPool.BLANK, "{fieldSets:[]}", 0,
				FragmentConstants.TYPE_SECTION,
				WorkflowConstants.STATUS_APPROVED, serviceContext);

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				fragmentEntry.getFragmentEntryId(),
				PortalUtil.getClassNameId(Layout.class),
				RandomTestUtil.randomLong(), fragmentEntry.getCss(),
				fragmentEntry.getHtml(), fragmentEntry.getJs(),
				fragmentEntry.getConfiguration(), StringPool.BLANK,
				StringPool.BLANK, 0, null, serviceContext);

		fragmentEntryLink =
			_fragmentEntryLinkLocalService.updateFragmentEntryLink(
				fragmentEntryLink.getFragmentEntryLinkId(), 1);

		Assert.assertEquals(1, fragmentEntryLink.getPosition());
	}

	@Test
	public void testUpdateFragmentEntryLinkWithoutPropagation()
		throws Exception {

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("propagateChanges", false);

		_configurationProvider.saveCompanyConfiguration(
			FragmentServiceConfiguration.class, _group.getCompanyId(),
			properties);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.addFragmentCollection(
				TestPropsValues.getUserId(), _group.getGroupId(),
				"Fragment Collection", StringPool.BLANK, serviceContext);

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(), null,
				"Fragment Name", RandomTestUtil.randomString(),
				"<div>test</div>", RandomTestUtil.randomString(),
				"{fieldSets:[]}", 0, FragmentConstants.TYPE_SECTION,
				WorkflowConstants.STATUS_APPROVED, serviceContext);

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				fragmentEntry.getFragmentEntryId(),
				PortalUtil.getClassNameId(Layout.class),
				RandomTestUtil.randomLong(), fragmentEntry.getCss(),
				fragmentEntry.getHtml(), fragmentEntry.getJs(),
				fragmentEntry.getConfiguration(), StringPool.BLANK,
				StringPool.BLANK, 0, null, serviceContext);

		_fragmentEntryLocalService.updateFragmentEntry(
			TestPropsValues.getUserId(), fragmentEntry.getFragmentEntryId(),
			fragmentEntry.getName(), StringPool.BLANK,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), fragmentEntry.getPreviewFileEntryId(),
			WorkflowConstants.STATUS_APPROVED);

		fragmentEntryLink = _fragmentEntryLinkLocalService.getFragmentEntryLink(
			fragmentEntryLink.getFragmentEntryLinkId());

		Assert.assertEquals(
			fragmentEntry.getHtml(), fragmentEntryLink.getHtml());
		Assert.assertEquals(fragmentEntry.getCss(), fragmentEntryLink.getCss());
		Assert.assertEquals(fragmentEntry.getJs(), fragmentEntryLink.getJs());
		Assert.assertEquals(
			fragmentEntry.getConfiguration(),
			fragmentEntryLink.getConfiguration());
	}

	@Test
	public void testUpdateFragmentEntryLinkWithPropagation() throws Exception {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("propagateChanges", true);

		_configurationProvider.saveCompanyConfiguration(
			FragmentServiceConfiguration.class, _group.getCompanyId(),
			properties);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.addFragmentCollection(
				TestPropsValues.getUserId(), _group.getGroupId(),
				"Fragment Collection", StringPool.BLANK, serviceContext);

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(), "Fragment Name",
				StringPool.BLANK, StringUtil.randomString(), StringPool.BLANK,
				WorkflowConstants.STATUS_APPROVED, serviceContext);

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				fragmentEntry.getFragmentEntryId(),
				PortalUtil.getClassNameId(Layout.class),
				RandomTestUtil.randomLong(), fragmentEntry.getCss(),
				fragmentEntry.getHtml(), fragmentEntry.getJs(),
				StringPool.BLANK, StringPool.BLANK, 0, null, serviceContext);

		String newCSS = StringUtil.randomString();
		String newHTML = StringUtil.randomString();
		String newJS = StringUtil.randomString();
		String newConfiguration = StringUtil.randomString();

		_fragmentEntryLocalService.updateFragmentEntry(
			TestPropsValues.getUserId(), fragmentEntry.getFragmentEntryId(),
			"Fragment Name", newCSS, newHTML, newJS, newConfiguration,
			fragmentEntry.getPreviewFileEntryId(),
			WorkflowConstants.STATUS_APPROVED);

		fragmentEntryLink = _fragmentEntryLinkLocalService.getFragmentEntryLink(
			fragmentEntryLink.getFragmentEntryLinkId());

		Assert.assertEquals(newCSS, fragmentEntryLink.getCss());
		Assert.assertEquals(newHTML, fragmentEntryLink.getHtml());
		Assert.assertEquals(newJS, fragmentEntryLink.getJs());
		Assert.assertEquals(
			newConfiguration, fragmentEntryLink.getConfiguration());
	}

	@Inject
	private ConfigurationProvider _configurationProvider;

	@Inject
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

}