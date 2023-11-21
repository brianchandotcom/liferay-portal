/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.exception.AssetTagException;
import com.liferay.asset.kernel.exception.AssetTagNameException;
import com.liferay.asset.kernel.exception.DuplicateTagException;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.asset.test.util.AssetTestUtil;
import com.liferay.asset.util.AssetHelper;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
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

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), TestPropsValues.getUserId());
	}

	@Test(expected = DuplicateTagException.class)
	public void testAddDuplicateTags() throws Exception {
		_assetTagLocalService.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(), "tag",
			_serviceContext);

		_assetTagLocalService.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(), "tag",
			_serviceContext);
	}

	@Test
	public void testAddMultipleTags() throws PortalException {
		_testAddMultipleTags(Arrays.asList("tag1", "tag2"));
	}

	@FeatureFlags("LPS-194362")
	@Test
	public void testAddMultipleTagsWithCaseSensitive() throws PortalException {
		_testAddMultipleTags(Arrays.asList("tag1", "Tag1", "tAg1", "TAG1"));
	}

	@Test
	public void testAddTag() throws PortalException {
		AssetTag assetTag = _assetTagLocalService.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(), "tag",
			_serviceContext);

		Assert.assertEquals("tag", assetTag.getName());
	}

	@Test(expected = AssetTagNameException.class)
	public void testAddTagWithEmptyName() throws Exception {
		_assetTagLocalService.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(), StringPool.BLANK,
			_serviceContext);
	}

	@Test(expected = AssetTagException.class)
	public void testAddTagWithInvalidCharacters() throws Exception {
		String stringWithInvalidCharacters = String.valueOf(
			AssetHelper.INVALID_CHARACTERS);

		_assetTagLocalService.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(),
			stringWithInvalidCharacters, _serviceContext);
	}

	@Test
	public void testAddTagWithMultipleWords() throws PortalException {
		AssetTag tag = _assetTagLocalService.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(), "tag name",
			_serviceContext);

		Assert.assertEquals("tag name", tag.getName());
	}

	@Test(expected = AssetTagNameException.class)
	public void testAddTagWithNullName() throws Exception {
		_assetTagLocalService.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(), null,
			_serviceContext);
	}

	@Test(expected = AssetTagNameException.class)
	public void testAddTagWithOnlySpacesInName() throws Exception {
		_assetTagLocalService.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(), StringPool.SPACE,
			_serviceContext);
	}

	@Test
	public void testAddTagWithPermittedSpecialCharacter()
		throws PortalException {

		_assetTagLocalService.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(), "-_^()!$",
			_serviceContext);
	}

	@Test
	public void testAddTagWithSingleWord() throws PortalException {
		int originalTagsCount = _assetTagLocalService.getAssetTagsCount();

		_assetTagLocalService.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(), "tag",
			_serviceContext);

		int actualTagsCount = _assetTagLocalService.getAssetTagsCount();

		Assert.assertEquals(originalTagsCount + 1, actualTagsCount);
	}

	@Test
	public void testAddUTF8FormattedTags() throws PortalException {
		AssetTag assetTag = _assetTagLocalService.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(), "標籤名稱",
			_serviceContext);

		Assert.assertEquals("標籤名稱", assetTag.getName());
	}

	@Test
	public void testDeleteTag() throws Exception {
		AssetTag assetTag = _assetTagLocalService.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(), "Tag",
			_serviceContext);

		_serviceContext.setAssetTagNames(new String[] {assetTag.getName()});

		_organization = OrganizationLocalServiceUtil.addOrganization(
			null, TestPropsValues.getUserId(),
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			RandomTestUtil.randomString(),
			OrganizationConstants.TYPE_ORGANIZATION, 0, 0,
			_listTypeLocalService.getListTypeId(
				assetTag.getCompanyId(),
				ListTypeConstants.ORGANIZATION_STATUS_DEFAULT,
				ListTypeConstants.ORGANIZATION_STATUS),
			RandomTestUtil.randomString(), true, _serviceContext);

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
			_assetTagLocalService.deleteTag(assetTag);

			Assert.assertNull(
				_assetTagLocalService.fetchAssetTag(assetTag.getTagId()));
		}
		finally {
			serviceRegistration.unregister();
		}
	}

	@FeatureFlags("LPS-194362")
	@Test
	public void testFetchTagWithCaseSensitive() throws PortalException {
		AssetTag expectedAssetTag1 = _assetTagLocalService.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(), "tag",
			_serviceContext);

		AssetTag expectedAssetTag2 = _assetTagLocalService.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(), "TAG",
			_serviceContext);

		AssetTag actualAssetTag1 = _assetTagLocalService.fetchTag(
			_group.getGroupId(), "tag");

		Assert.assertNotNull(actualAssetTag1);
		Assert.assertEquals(
			expectedAssetTag1.getTagId(), actualAssetTag1.getTagId());

		AssetTag actualAssetTag2 = _assetTagLocalService.fetchTag(
			_group.getGroupId(), "TAG");

		Assert.assertNotNull(actualAssetTag2);
		Assert.assertEquals(
			expectedAssetTag2.getTagId(), actualAssetTag2.getTagId());

		Assert.assertNull(
			_assetTagLocalService.fetchTag(_group.getGroupId(), "Tag"));
	}

	@FeatureFlags("LPS-194362")
	@Test
	public void testGetTagIdsFilterByGroupIdWithCaseSensitive()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		try {
			AssetTag expectedAssetTag1 = _assetTagLocalService.addTag(
				TestPropsValues.getUserId(), _group.getGroupId(), "tAg1",
				_serviceContext);

			_assetTagLocalService.addTag(
				TestPropsValues.getUserId(), group.getGroupId(), "tAg1",
				_serviceContext);

			AssetTag expectedAssetTag2 = _assetTagLocalService.addTag(
				TestPropsValues.getUserId(), _group.getGroupId(), "TAG1",
				_serviceContext);

			Assert.assertArrayEquals(
				new long[] {expectedAssetTag1.getTagId()},
				_assetTagLocalService.getTagIds(
					new long[] {_group.getGroupId()}, "tAg1"));
			Assert.assertArrayEquals(
				new long[] {expectedAssetTag2.getTagId()},
				_assetTagLocalService.getTagIds(
					new long[] {_group.getGroupId()}, "TAG1"));
			Assert.assertArrayEquals(
				new long[0],
				_assetTagLocalService.getTagIds(
					new long[] {group.getGroupId()}, "TAG1"));
		}
		finally {
			GroupTestUtil.deleteGroup(group);
		}
	}

	@FeatureFlags("LPS-194362")
	@Test
	public void testGetTagIdsWithCaseSensitive() throws Exception {
		Group group = GroupTestUtil.addGroup();

		try {
			AssetTag expectedAssetTag1 = _assetTagLocalService.addTag(
				TestPropsValues.getUserId(), _group.getGroupId(), "tAg1",
				_serviceContext);
			AssetTag expectedAssetTag2 = _assetTagLocalService.addTag(
				TestPropsValues.getUserId(), group.getGroupId(), "tAg1",
				_serviceContext);
			AssetTag expectedAssetTag3 = _assetTagLocalService.addTag(
				TestPropsValues.getUserId(), _group.getGroupId(), "TAG1",
				_serviceContext);

			Assert.assertArrayEquals(
				new long[] {
					expectedAssetTag1.getTagId(), expectedAssetTag2.getTagId()
				},
				_assetTagLocalService.getTagIds("tAg1"));
			Assert.assertArrayEquals(
				new long[] {expectedAssetTag3.getTagId()},
				_assetTagLocalService.getTagIds("TAG1"));
			Assert.assertArrayEquals(
				new long[0], _assetTagLocalService.getTagIds("Tag1"));
		}
		finally {
			GroupTestUtil.deleteGroup(group);
		}
	}

	@FeatureFlags("LPS-194362")
	@Test
	public void testGetTagsWithCaseInsensitive() throws Exception {
		_assetTagLocalService.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(), "tag1",
			_serviceContext);
		_assetTagLocalService.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(), "Tag1",
			_serviceContext);
		_assetTagLocalService.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(), "TAG1",
			_serviceContext);

		String[] tagNames = {"tag1", "Tag1"};

		Arrays.sort(tagNames);

		_serviceContext.setAssetTagNames(tagNames);

		JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, _serviceContext);

		_getTags("tag1", tagNames);
		_getTags("TAG1", tagNames);
		_getTags("TAG1", Arrays.copyOfRange(tagNames, 0, 1), 0, 1);
		_getTags("Tag1", Arrays.copyOfRange(tagNames, 0, 1), 0, 1);
	}

	@Test
	public void testIncrementAssetCountWhenUpdatingAssetEntry()
		throws PortalException {

		_testIncrementAssetCountWhenUpdatingAssetEntry(new String[] {"tag1"});
	}

	@FeatureFlags("LPS-194362")
	@Test
	public void testIncrementAssetCountWhenUpdatingAssetEntryWithCaseSensitive()
		throws PortalException {

		_testIncrementAssetCountWhenUpdatingAssetEntry(
			new String[] {"tag1", "Tag1", "TAG1"});
	}

	@Test(expected = AssetTagException.class)
	public void testIncrementAssetCountWithAssetTagNameGreaterThan75()
		throws PortalException {

		_assetTagLocalService.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(100), _serviceContext);
	}

	@FeatureFlags("LPS-194362")
	@Test
	public void testSearchWithCaseInsensitive() throws PortalException {
		_assetTagLocalService.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(), "tag1",
			_serviceContext);

		_assetTagLocalService.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(), "Tag1",
			_serviceContext);

		_assetTagLocalService.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(), "TAG1",
			_serviceContext);

		String[] tagNames = {"tag1", "Tag1", "TAG1"};

		for (String tagName : tagNames) {
			List<AssetTag> assetTags = _assetTagLocalService.search(
				new long[] {_group.getGroupId()}, tagName, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

			Assert.assertEquals(assetTags.toString(), 3, assetTags.size());

			Assert.assertTrue(
				ArrayUtil.containsAll(
					TransformUtil.transformToArray(
						assetTags, AssetTag::getName, String.class),
					tagNames));
		}
	}

	@FeatureFlags("LPS-194362")
	@Test
	public void testUpdateTagWithCaseSensitive() throws PortalException {
		AssetTag assetTag = _assetTagLocalService.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(), "tag1",
			_serviceContext);

		String tagName = "updated TAG1";

		AssetTag actualAssetTag = _assetTagLocalService.updateTag(
			TestPropsValues.getUserId(), assetTag.getTagId(), tagName,
			_serviceContext);

		Assert.assertEquals(tagName, actualAssetTag.getName());
	}

	private void _getTags(String name, String[] expectedTagNames) {
		_getTags(name, expectedTagNames, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	private void _getTags(
		String name, String[] expectedTagNames, int start, int end) {

		List<AssetTag> assetTags = _assetTagLocalService.getTags(
			_group.getGroupId(),
			_classNameLocalService.getClassNameId(
				JournalArticle.class.getName()),
			name, start, end);

		Assert.assertEquals(
			assetTags.toString(), expectedTagNames.length, assetTags.size());
		Assert.assertTrue(
			ArrayUtil.containsAll(
				TransformUtil.transformToArray(
					assetTags, AssetTag::getName, String.class),
				expectedTagNames));
	}

	private void _testAddMultipleTags(List<String> tagNames)
		throws PortalException {

		int originalTagsCount = _assetTagLocalService.getAssetTagsCount();

		for (String tagName : tagNames) {
			AssetTag assetTag = _assetTagLocalService.addTag(
				TestPropsValues.getUserId(), _group.getGroupId(), tagName,
				_serviceContext);

			Assert.assertEquals(tagName, assetTag.getName());
		}

		int actualTagsCount = _assetTagLocalService.getAssetTagsCount();

		Assert.assertEquals(
			originalTagsCount + tagNames.size(), actualTagsCount);
	}

	private void _testIncrementAssetCountWhenUpdatingAssetEntry(
			String[] tagNames)
		throws PortalException {

		AssetEntry assetEntry = AssetTestUtil.addAssetEntry(
			_group.getGroupId());

		assetEntry = _assetEntryLocalService.updateEntry(
			TestPropsValues.getUserId(), assetEntry.getGroupId(),
			assetEntry.getClassName(), assetEntry.getClassPK(), null, tagNames);

		List<AssetTag> assetTags = assetEntry.getTags();

		Assert.assertEquals(
			TransformUtil.transform(
				assetTags, AssetTag::getName
			).toString(),
			tagNames.length, assetTags.size());

		for (AssetTag assetTag : assetEntry.getTags()) {
			Assert.assertEquals(1, assetTag.getAssetCount());
		}
	}

	@Inject
	private AssetEntryLocalService _assetEntryLocalService;

	@Inject
	private AssetTagLocalService _assetTagLocalService;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private ListTypeLocalService _listTypeLocalService;

	@DeleteAfterTestRun
	private Organization _organization;

	private ServiceContext _serviceContext;

}