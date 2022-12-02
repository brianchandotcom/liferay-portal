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

package com.liferay.asset.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.exception.AssetTagException;
import com.liferay.asset.kernel.exception.AssetTagNameException;
import com.liferay.asset.kernel.exception.DuplicateTagException;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetTagLocalServiceUtil;
import com.liferay.asset.test.util.AssetTestUtil;
import com.liferay.asset.util.AssetHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

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

/**
 * @author Michael C. Han
 * @author Manuel de la Peña
 */
@RunWith(Arquillian.class)
public class AssetTagLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test(expected = DuplicateTagException.class)
	public void testAddDuplicateTags() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		AssetTagLocalServiceUtil.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(), "tag",
			serviceContext);

		AssetTagLocalServiceUtil.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(), "tag",
			serviceContext);
	}

	@Test
	public void testAddMultipleTags() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		int originalTagsCount = AssetTagLocalServiceUtil.getAssetTagsCount();

		AssetTagLocalServiceUtil.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(), "tag1",
			serviceContext);

		AssetTagLocalServiceUtil.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(), "tag2",
			serviceContext);

		int actualTagsCount = AssetTagLocalServiceUtil.getAssetTagsCount();

		Assert.assertEquals(originalTagsCount + 2, actualTagsCount);
	}

	@Test
	public void testAddTag() throws PortalException {
		AssetTag assetTag = AssetTagLocalServiceUtil.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(), "tag",
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));

		Assert.assertEquals("tag", assetTag.getName());
	}

	@Test(expected = AssetTagNameException.class)
	public void testAddTagWithEmptyName() throws Exception {
		AssetTagLocalServiceUtil.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(), StringPool.BLANK,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));
	}

	@Test(expected = AssetTagException.class)
	public void testAddTagWithInvalidCharacters() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		String stringWithInvalidCharacters = String.valueOf(
			AssetHelper.INVALID_CHARACTERS);

		AssetTagLocalServiceUtil.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(),
			stringWithInvalidCharacters, serviceContext);
	}

	@Test
	public void testAddTagWithMultipleWords() throws PortalException {
		AssetTag tag = AssetTagLocalServiceUtil.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(), "tag name",
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));

		Assert.assertEquals("tag name", tag.getName());
	}

	@Test(expected = AssetTagNameException.class)
	public void testAddTagWithNullName() throws Exception {
		AssetTagLocalServiceUtil.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(), null,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));
	}

	@Test(expected = AssetTagNameException.class)
	public void testAddTagWithOnlySpacesInName() throws Exception {
		AssetTagLocalServiceUtil.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(), StringPool.SPACE,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));
	}

	@Test
	public void testAddTagWithPermittedSpecialCharacter()
		throws PortalException {

		AssetTagLocalServiceUtil.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(), "-_^()!$",
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));
	}

	@Test
	public void testAddTagWithSingleWord() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		int originalTagsCount = AssetTagLocalServiceUtil.getAssetTagsCount();

		AssetTagLocalServiceUtil.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(), "tag",
			serviceContext);

		int actualTagsCount = AssetTagLocalServiceUtil.getAssetTagsCount();

		Assert.assertEquals(originalTagsCount + 1, actualTagsCount);
	}

	@Test
	public void testAddUTF8FormattedTags() throws PortalException {
		AssetTag assetTag = AssetTagLocalServiceUtil.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(), "標籤名稱",
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));

		Assert.assertEquals("標籤名稱", assetTag.getName());
	}

	@Test
	public void testDeleteTag() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		AssetTag assetTag = AssetTagLocalServiceUtil.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(), "Tag",
			serviceContext);

		serviceContext.setAssetTagNames(new String[] {assetTag.getName()});

		_organization = OrganizationLocalServiceUtil.addOrganization(
			TestPropsValues.getUserId(),
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			RandomTestUtil.randomString(),
			OrganizationConstants.TYPE_ORGANIZATION, 0, 0,
			ListTypeConstants.ORGANIZATION_STATUS_DEFAULT,
			RandomTestUtil.randomString(), true, serviceContext);

		TestAssetIndexer testAssetIndexer = new TestAssetIndexer();

		testAssetIndexer.setExpectedValues(
			Organization.class.getName(), _organization.getOrganizationId());

		Bundle bundle = FrameworkUtil.getBundle(getClass());

		BundleContext bundleContext = bundle.getBundleContext();

		ServiceRegistration<?> serviceRegistration =
			bundleContext.registerService(
				Indexer.class, testAssetIndexer,
				MapUtil.singletonDictionary(
					"service.ranking", Integer.MAX_VALUE));

		try {
			AssetTagLocalServiceUtil.deleteTag(assetTag);

			Assert.assertNull(
				AssetTagLocalServiceUtil.fetchAssetTag(assetTag.getTagId()));
		}
		finally {
			serviceRegistration.unregister();
		}
	}

	@Test
	public void testIncrementAssetCountWhenUpdatingAssetEntry()
		throws PortalException {

		AssetEntry assetEntry = AssetTestUtil.addAssetEntry(
			_group.getGroupId());

		assetEntry = AssetEntryLocalServiceUtil.updateEntry(
			TestPropsValues.getUserId(), assetEntry.getGroupId(),
			assetEntry.getClassName(), assetEntry.getClassPK(), null,
			new String[] {"tag"});

		List<AssetTag> assetTags = assetEntry.getTags();

		AssetTag assetTag = assetTags.get(0);

		Assert.assertEquals(1, assetTag.getAssetCount());
	}

	@Test(expected = AssetTagException.class)
	public void testIncrementAssetCountWithAssetTagNameGreaterThan75()
		throws PortalException {

		AssetTagLocalServiceUtil.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(100),
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));
	}

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private Organization _organization;

}