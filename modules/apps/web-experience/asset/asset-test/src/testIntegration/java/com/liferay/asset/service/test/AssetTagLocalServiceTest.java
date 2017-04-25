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
import com.liferay.asset.kernel.exception.DuplicateTagException;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.model.AssetTagStats;
import com.liferay.asset.kernel.service.AssetTagLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetTagStatsLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.asset.util.AssetUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Michael C. Han
 * @author Manuel de la Peña
 */
@RunWith(Arquillian.class)
@Sync
public class AssetTagLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_organizationIndexer = IndexerRegistryUtil.getIndexer(
			Organization.class);
	}

	@After
	public void tearDown() throws Exception {
		if (_organizationIndexer != null) {
			IndexerRegistryUtil.register(_organizationIndexer);
		}
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
	public void testAddTagWithMultipleWords() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		String tagName = "tag name";

		AssetTag tag = AssetTagLocalServiceUtil.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(), tagName,
			serviceContext);

		Assert.assertEquals(tagName, tag.getName());
	}

	@Test
	public void testAddTagWithPermittedSpecialCharacter()
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		String permittedCharactersString = "-_^()!$";

		AssetTagLocalServiceUtil.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(),
			permittedCharactersString, serviceContext);
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
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		String utf8FormattedString = "標籤名稱";

		AssetTag assetTag = AssetTagLocalServiceUtil.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(),
			utf8FormattedString, serviceContext);

		Assert.assertEquals(utf8FormattedString, assetTag.getName());
	}

	@Test(expected = DuplicateTagException.class)
	public void testCannotAddDuplicateTags() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		String tagName = "tag";

		AssetTagLocalServiceUtil.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(), tagName,
			serviceContext);

		AssetTagLocalServiceUtil.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(), tagName,
			serviceContext);
	}

	@Test(expected = AssetTagException.class)
	public void testCannotAddTagWithEmptyName() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		AssetTagLocalServiceUtil.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(), StringPool.BLANK,
			serviceContext);
	}

	@Test(expected = AssetTagException.class)
	public void testCannotAddTagWithInvalidCharacters() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		String stringWithInvalidCharacters = String.valueOf(
			AssetUtil.INVALID_CHARACTERS);

		AssetTagLocalServiceUtil.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(),
			stringWithInvalidCharacters, serviceContext);
	}

	@Test(expected = AssetTagException.class)
	public void testCannotAddTagWithNullName() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		AssetTagLocalServiceUtil.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(), null,
			serviceContext);
	}

	@Test(expected = AssetTagException.class)
	public void testCannotAddTagWithOnlySpacesInName() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		AssetTagLocalServiceUtil.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(), StringPool.SPACE,
			serviceContext);
	}

	@Test
	public void testCreateTag() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		String tagName = "tag";

		AssetTag assetTag = AssetTagLocalServiceUtil.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(), tagName,
			serviceContext);

		Assert.assertEquals(tagName, assetTag.getName());
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

		if (_organizationIndexer == null) {
			_organizationIndexer = IndexerRegistryUtil.getIndexer(
				Organization.class);
		}

		IndexerRegistryUtil.register(testAssetIndexer);

		AssetTagLocalServiceUtil.deleteTag(assetTag);

		Assert.assertNull(
			AssetTagLocalServiceUtil.fetchAssetTag(assetTag.getTagId()));

		long classNameId = PortalUtil.getClassNameId(Organization.class);

		AssetTagStats assetTagStats = AssetTagStatsLocalServiceUtil.getTagStats(
			assetTag.getTagId(), classNameId);

		Assert.assertEquals(0, assetTagStats.getAssetCount());
	}

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private Organization _organization;

	private Indexer<Organization> _organizationIndexer;

}