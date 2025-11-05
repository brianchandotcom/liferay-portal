/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.internal.model.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.list.constants.AssetListEntryTypeConstants;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.asset.list.service.AssetListEntrySegmentsEntryRelLocalService;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.journal.model.JournalArticle;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.segments.constants.SegmentsEntryConstants;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Joshua Cords
 */
@RunWith(Arquillian.class)
public class ObjectDefinitionModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testChangeAnyAssetTypeWhenOneClassNameIdRemains()
		throws Exception {

		long journalArticleClassNameId = _portal.getClassNameId(
			JournalArticle.class.getName());

		ObjectDefinition objectDefinition = _addObjectDefinition();

		long objectClassNameId = _portal.getClassNameId(
			objectDefinition.getClassName());

		AssetListEntry assetListEntry = _addAssetListEntry(
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()),
			_createUnicodeProperties(
				Boolean.FALSE.toString(),
				new long[] {objectClassNameId, journalArticleClassNameId}));

		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition);

		UnicodeProperties updatedUnicodeProperties =
			_getUpdatedUnicodeProperties(assetListEntry);

		Assert.assertEquals(
			String.valueOf(journalArticleClassNameId),
			updatedUnicodeProperties.getProperty("anyAssetType"));
		Assert.assertEquals(
			StringUtil.merge(
				AssetRendererFactoryRegistryUtil.getClassNameIds(
					TestPropsValues.getCompanyId(), true)),
			updatedUnicodeProperties.getProperty("classNameIds"));
	}

	@Test
	public void testKeepAnyAssetTypeFalseWhenMultipleClassNameIdsRemain()
		throws Exception {

		ObjectDefinition objectDefinition = _addObjectDefinition();

		long blogsEntryClassNameId = _portal.getClassNameId(
			BlogsEntry.class.getName());
		long journalArticleClassNameId = _portal.getClassNameId(
			JournalArticle.class.getName());
		long objectClassNameId = _portal.getClassNameId(
			objectDefinition.getClassName());

		AssetListEntry assetListEntry = _addAssetListEntry(
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()),
			_createUnicodeProperties(
				Boolean.FALSE.toString(),
				new long[] {
					objectClassNameId, journalArticleClassNameId,
					blogsEntryClassNameId
				}));

		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition);

		UnicodeProperties updatedUnicodeProperties =
			_getUpdatedUnicodeProperties(assetListEntry);

		Assert.assertEquals(
			Boolean.FALSE.toString(),
			updatedUnicodeProperties.getProperty("anyAssetType"));

		Assert.assertArrayEquals(
			new long[] {journalArticleClassNameId, blogsEntryClassNameId},
			GetterUtil.getLongValues(
				StringUtil.split(
					updatedUnicodeProperties.getProperty("classNameIds"))));
	}

	@Test
	public void testNoRemainingClassNameIds() throws Exception {
		ObjectDefinition objectDefinition1 = _addObjectDefinition();
		ObjectDefinition objectDefinition2 = _addObjectDefinition();

		long objectClassNameId1 = _portal.getClassNameId(
			objectDefinition1.getClassName());
		long objectClassNameId2 = _portal.getClassNameId(
			objectDefinition2.getClassName());

		AssetListEntry assetListEntry = _addAssetListEntry(
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()),
			_createUnicodeProperties(
				Boolean.FALSE.toString(),
				new long[] {objectClassNameId1, objectClassNameId2}));

		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition1);
		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition2);

		UnicodeProperties updatedUnicodeProperties =
			_getUpdatedUnicodeProperties(assetListEntry);

		Assert.assertTrue(
			GetterUtil.getBoolean(
				updatedUnicodeProperties.getProperty("anyAssetType")));
		Assert.assertEquals(
			StringUtil.merge(
				AssetRendererFactoryRegistryUtil.getClassNameIds(
					TestPropsValues.getCompanyId(), true)),
			updatedUnicodeProperties.getProperty("classNameIds"));
	}

	@Test
	public void testRemovalFromAnyAssetType() throws Exception {
		ObjectDefinition objectDefinition = _addObjectDefinition();

		long[] classNameIds = AssetRendererFactoryRegistryUtil.getClassNameIds(
			TestPropsValues.getCompanyId(), true);

		long objectClassNameId = _portal.getClassNameId(
			objectDefinition.getClassName());

		AssetListEntry assetListEntry = _addAssetListEntry(
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()),
			_createUnicodeProperties(
				String.valueOf(objectClassNameId), classNameIds));

		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition);

		UnicodeProperties updatedUnicodeProperties =
			_getUpdatedUnicodeProperties(assetListEntry);

		Assert.assertTrue(
			GetterUtil.getBoolean(
				updatedUnicodeProperties.getProperty("anyAssetType")));
		Assert.assertArrayEquals(
			ArrayUtil.remove(classNameIds, objectClassNameId),
			GetterUtil.getLongValues(
				StringUtil.split(
					updatedUnicodeProperties.getProperty("classNameIds"))));
	}

	@Test
	public void testRemovalFromClassNameIds() throws Exception {
		ObjectDefinition objectDefinition = _addObjectDefinition();

		long[] classNameIds = AssetRendererFactoryRegistryUtil.getClassNameIds(
			TestPropsValues.getCompanyId(), true);

		AssetListEntry assetListEntry = _addAssetListEntry(
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()),
			_createUnicodeProperties(Boolean.TRUE.toString(), classNameIds));

		long objectClassNameId = _portal.getClassNameId(
			objectDefinition.getClassName());

		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition);

		UnicodeProperties updatedUnicodeProperties =
			_getUpdatedUnicodeProperties(assetListEntry);

		Assert.assertTrue(
			GetterUtil.getBoolean(
				updatedUnicodeProperties.getProperty("anyAssetType")));
		Assert.assertArrayEquals(
			ArrayUtil.remove(classNameIds, objectClassNameId),
			GetterUtil.getLongValues(
				StringUtil.split(
					updatedUnicodeProperties.getProperty("classNameIds"))));
	}

	private AssetListEntry _addAssetListEntry(
			ServiceContext serviceContext, UnicodeProperties unicodeProperties)
		throws Exception {

		return _assetListEntryLocalService.addAssetListEntry(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			_group.getGroupId(), RandomTestUtil.randomString(),
			AssetListEntryTypeConstants.TYPE_DYNAMIC,
			unicodeProperties.toString(), serviceContext);
	}

	private ObjectDefinition _addObjectDefinition() throws Exception {
		return ObjectDefinitionTestUtil.publishObjectDefinition(
			false, false, false,
			ListUtil.fromArray(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING,
					RandomTestUtil.randomString(), "text")),
			ObjectDefinitionConstants.SCOPE_SITE);
	}

	private UnicodeProperties _createUnicodeProperties(
		String anyAssetType, long[] classNameIds) {

		return UnicodePropertiesBuilder.create(
			true
		).put(
			"anyAssetType", anyAssetType
		).put(
			"classNameIds", () -> StringUtil.merge(classNameIds)
		).put(
			"groupIds", String.valueOf(_group.getGroupId())
		).build();
	}

	private UnicodeProperties _getUpdatedUnicodeProperties(
			AssetListEntry assetListEntry)
		throws Exception {

		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel =
			_assetListEntrySegmentsEntryRelLocalService.
				getAssetListEntrySegmentsEntryRel(
					assetListEntry.getAssetListEntryId(),
					SegmentsEntryConstants.ID_DEFAULT);

		return UnicodePropertiesBuilder.load(
			assetListEntrySegmentsEntryRel.getTypeSettings()
		).build();
	}

	@Inject
	private AssetListEntryLocalService _assetListEntryLocalService;

	@Inject
	private AssetListEntrySegmentsEntryRelLocalService
		_assetListEntrySegmentsEntryRelLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private Portal _portal;

}