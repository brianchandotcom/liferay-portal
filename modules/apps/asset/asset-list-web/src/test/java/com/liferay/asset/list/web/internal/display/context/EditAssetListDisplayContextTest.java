/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.web.internal.display.context;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.AssetVocabularyGroupRel;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.asset.kernel.service.AssetVocabularyGroupRelLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyService;
import com.liferay.asset.kernel.service.AssetVocabularyServiceUtil;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.asset.list.service.AssetListEntryLocalServiceUtil;
import com.liferay.asset.test.util.asset.renderer.factory.TestAssetRendererFactory;
import com.liferay.asset.util.AssetRendererFactoryClassProvider;
import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.depot.service.DepotEntryService;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.info.search.InfoSearchClassMapperRegistry;
import com.liferay.item.selector.ItemSelector;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.segments.configuration.provider.SegmentsConfigurationProvider;

import jakarta.portlet.PortletRequest;
import jakarta.portlet.PortletResponse;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Lourdes Fernández Besada
 */
public class EditAssetListDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@AfterClass
	public static void tearDownClass() {
		_assetRendererFactoryRegistryUtilMockedStatic.close();
	}

	@Before
	public void setUp() {
		_setUpHttpServletRequest();
		_setUpLanguageUtil();
		_setUpPortalUtil();
	}

	@Test
	public void testGetActionDropdownItems() throws Exception {
		String className = RandomTestUtil.randomString();
		long classNameId = RandomTestUtil.randomLong();
		String expectedLabel = RandomTestUtil.randomString();

		_setUpAssetRendererFactoryRegistryUtil(
			className, classNameId, expectedLabel);

		UnicodeProperties unicodeProperties = UnicodePropertiesBuilder.put(
			"classNameIds", classNameId
		).put(
			"selectionStyle", "manual"
		).build();

		_setUpAssetListEntryLocalServiceUtil(
			StringPool.BLANK, className, unicodeProperties.toString());

		EditAssetListDisplayContext editAssetListDisplayContext =
			_getEditAssetListDisplayContext(unicodeProperties);

		List<DropdownItem> dropdownItems =
			editAssetListDisplayContext.getActionDropdownItems();

		Assert.assertEquals(dropdownItems.toString(), 1, dropdownItems.size());

		DropdownItem dropdownItem = dropdownItems.get(0);

		Assert.assertEquals(expectedLabel, dropdownItem.get("label"));
	}

	@Test
	public void testGetActionDropdownItemsEmptyClassTypeIdsProperty()
		throws Exception {

		String className = RandomTestUtil.randomString();
		long classNameId = RandomTestUtil.randomLong();
		String expectedLabel = RandomTestUtil.randomString();

		_setUpAssetRendererFactoryRegistryUtil(
			className, classNameId,
			_getClassTypeReader(
				ListUtil.fromArray(
					_getClassType(), _getClassType(), _getClassType())),
			true, expectedLabel);

		UnicodeProperties unicodeProperties = UnicodePropertiesBuilder.put(
			"selectionStyle", "manual"
		).build();

		_setUpAssetListEntryLocalServiceUtil(
			StringPool.BLANK, className, unicodeProperties.toString());

		EditAssetListDisplayContext editAssetListDisplayContext =
			_getEditAssetListDisplayContext(unicodeProperties);

		List<DropdownItem> dropdownItems =
			editAssetListDisplayContext.getActionDropdownItems();

		Assert.assertEquals(dropdownItems.toString(), 3, dropdownItems.size());
	}

	@Test
	public void testGetActionDropdownItemsNoAvailableClassNameId()
		throws Exception {

		long classNameId = RandomTestUtil.randomLong();

		_setUpAssetRendererFactoryRegistryUtil(null, classNameId, null);

		UnicodeProperties unicodeProperties = UnicodePropertiesBuilder.put(
			"classNameIds", classNameId
		).put(
			"selectionStyle", "manual"
		).build();

		_setUpAssetListEntryLocalServiceUtil(
			StringPool.BLANK, RandomTestUtil.randomString(),
			unicodeProperties.toString());

		EditAssetListDisplayContext editAssetListDisplayContext =
			_getEditAssetListDisplayContext(unicodeProperties);

		List<DropdownItem> dropdownItems =
			editAssetListDisplayContext.getActionDropdownItems();

		Assert.assertEquals(dropdownItems.toString(), 0, dropdownItems.size());
	}

	@Test
	public void testGetActionDropdownItemsWithNoAvailableSelectedSubtype()
		throws Exception {

		String className = RandomTestUtil.randomString();
		long classNameId = RandomTestUtil.randomLong();
		ClassTypeReader classTypeReader = _getClassTypeReader(
			ListUtil.fromArray(
				_getClassType(), _getClassType(), _getClassType()));
		String expectedLabel = RandomTestUtil.randomString();

		_setUpAssetRendererFactoryRegistryUtil(
			className, classNameId, classTypeReader, true, expectedLabel);

		long classTypeId = RandomTestUtil.randomLong();

		UnicodeProperties unicodeProperties = UnicodePropertiesBuilder.put(
			"classNameIds", classNameId
		).put(
			"classTypeIds" + TestAssetRendererFactory.class.getSimpleName(),
			classTypeId
		).put(
			"selectionStyle", "manual"
		).build();

		_setUpAssetListEntryLocalServiceUtil(
			StringPool.BLANK, className, unicodeProperties.toString());

		EditAssetListDisplayContext editAssetListDisplayContext =
			_getEditAssetListDisplayContext(unicodeProperties);

		List<DropdownItem> dropdownItems =
			editAssetListDisplayContext.getActionDropdownItems();

		Assert.assertEquals(dropdownItems.toString(), 0, dropdownItems.size());

		Mockito.verify(
			classTypeReader
		).getClassType(
			classTypeId, LocaleUtil.US
		);
	}

	@Test
	public void testGetActionDropdownItemsWithSelectedSubtype()
		throws Exception {

		String className = RandomTestUtil.randomString();
		long classNameId = RandomTestUtil.randomLong();

		long classTypeId = RandomTestUtil.randomLong();
		String expectedLabel = RandomTestUtil.randomString();

		ClassTypeReader classTypeReader = _getClassTypeReader(
			ListUtil.fromArray(
				_getClassType(), _getClassType(classTypeId, expectedLabel),
				_getClassType()));

		_setUpAssetRendererFactoryRegistryUtil(
			className, classNameId, classTypeReader, true, expectedLabel);

		UnicodeProperties unicodeProperties = UnicodePropertiesBuilder.put(
			"classNameIds", classNameId
		).put(
			"classTypeIds" + TestAssetRendererFactory.class.getSimpleName(),
			classTypeId
		).put(
			"selectionStyle", "manual"
		).build();

		_setUpAssetListEntryLocalServiceUtil(
			StringPool.BLANK, className, unicodeProperties.toString());

		EditAssetListDisplayContext editAssetListDisplayContext =
			_getEditAssetListDisplayContext(unicodeProperties);

		List<DropdownItem> dropdownItems =
			editAssetListDisplayContext.getActionDropdownItems();

		Assert.assertEquals(dropdownItems.toString(), 1, dropdownItems.size());

		DropdownItem dropdownItem = dropdownItems.get(0);

		Assert.assertEquals(expectedLabel, dropdownItem.get("label"));

		Mockito.verify(
			classTypeReader
		).getClassType(
			classTypeId, LocaleUtil.US
		);
	}

	@Test
	public void testGetClassNameIdsDynamicSelection() {
		long classNameId = RandomTestUtil.randomLong();

		UnicodeProperties unicodeProperties = UnicodePropertiesBuilder.put(
			"anyAssetType", classNameId
		).put(
			"selectionStyle", "dynamic"
		).build();

		EditAssetListDisplayContext editAssetListDisplayContext =
			_getEditAssetListDisplayContext(unicodeProperties);

		Assert.assertArrayEquals(
			new long[] {classNameId},
			editAssetListDisplayContext.getClassNameIds(
				unicodeProperties,
				new long[] {
					RandomTestUtil.randomLong(), classNameId,
					RandomTestUtil.randomLong()
				}));
	}

	@Test
	public void testGetClassNameIdsDynamicSelectionNoAvailableClassNameId() {
		UnicodeProperties unicodeProperties = UnicodePropertiesBuilder.put(
			"anyAssetType", RandomTestUtil.randomLong()
		).put(
			"selectionStyle", "dynamic"
		).build();

		EditAssetListDisplayContext editAssetListDisplayContext =
			_getEditAssetListDisplayContext(unicodeProperties);

		Assert.assertArrayEquals(
			new long[0],
			editAssetListDisplayContext.getClassNameIds(
				unicodeProperties,
				new long[] {
					RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
					RandomTestUtil.randomLong()
				}));
	}

	@Test
	public void testGetReferencedModelsGroupIdsWithConnectedDepots()
		throws Exception {

		long connectedDepotGroupId = RandomTestUtil.randomLong();

		DepotEntry depotEntry = Mockito.mock(DepotEntry.class);

		Mockito.doReturn(
			connectedDepotGroupId
		).when(
			depotEntry
		).getGroupId();

		Mockito.doReturn(
			Collections.singletonList(depotEntry)
		).when(
			_depotEntryService
		).getCurrentAndGroupConnectedDepotEntries(
			Mockito.anyLong(), Mockito.anyInt(), Mockito.anyInt(),
			Mockito.anyInt()
		);

		long scopeGroupId = RandomTestUtil.randomLong();

		Group scopeGroup = _setUpGroup(scopeGroupId);

		Mockito.doReturn(
			scopeGroup
		).when(
			_themeDisplay
		).getScopeGroup();

		Mockito.doReturn(
			new long[] {scopeGroupId}
		).when(
			_portal
		).getCurrentAndAncestorSiteGroupIds(
			Mockito.any(long[].class), Mockito.anyBoolean()
		);

		EditAssetListDisplayContext displayContext =
			_getEditAssetListDisplayContext(new UnicodeProperties());

		long[] resultGroupIds = displayContext.getReferencedModelsGroupIds();

		Assert.assertTrue(ArrayUtil.contains(resultGroupIds, scopeGroupId));
		Assert.assertTrue(
			ArrayUtil.contains(resultGroupIds, connectedDepotGroupId));
	}

	@Test
	public void testGetReferencedModelsGroupIdsWithNoConnectedDepots()
		throws Exception {

		long scopeGroupId = RandomTestUtil.randomLong();

		Group scopeGroup = _setUpGroup(scopeGroupId);

		Mockito.doReturn(
			scopeGroup
		).when(
			_themeDisplay
		).getScopeGroup();

		Mockito.doReturn(
			Collections.emptyList()
		).when(
			_depotEntryService
		).getCurrentAndGroupConnectedDepotEntries(
			Mockito.anyLong(), Mockito.anyInt(), Mockito.anyInt(),
			Mockito.anyInt()
		);

		Mockito.doReturn(
			new long[] {scopeGroupId}
		).when(
			_portal
		).getCurrentAndAncestorSiteGroupIds(
			Mockito.any(long[].class), Mockito.anyBoolean()
		);

		EditAssetListDisplayContext displayContext =
			_getEditAssetListDisplayContext(new UnicodeProperties());

		Assert.assertArrayEquals(
			new long[] {scopeGroupId},
			displayContext.getReferencedModelsGroupIds());
	}

	@Test
	public void testGetResolvedReferencedModelsGroupIds() throws Exception {
		long siteGroupId = RandomTestUtil.randomLong();

		Group siteGroup = _setUpGroup(siteGroupId);

		Mockito.doReturn(
			siteGroup
		).when(
			_groupService
		).getGroup(
			siteGroupId
		);

		EditAssetListDisplayContext displayContext = Mockito.spy(
			_getEditAssetListDisplayContext(new UnicodeProperties()));

		Mockito.doReturn(
			new long[] {siteGroupId}
		).when(
			displayContext
		).getReferencedModelsGroupIds();

		Mockito.doThrow(
			new NoSuchGroupException()
		).when(
			_groupService
		).getGroup(
			Mockito.anyLong(), Mockito.eq(GroupConstants.CMS)
		);

		Assert.assertTrue(
			ArrayUtil.contains(
				ArrayUtil.toLongArray(
					displayContext.getResolvedReferencedModelsGroupIds()),
				siteGroupId));
	}

	@FeatureFlag("LPD-17564")
	@Test
	public void testGetResolvedReferencedModelsGroupIdsWithSpaces()
		throws Exception {

		long cmsGroupId = RandomTestUtil.randomLong();

		Group cmsGroup = _setUpGroup(cmsGroupId);

		Mockito.doReturn(
			cmsGroup
		).when(
			_groupLocalService
		).fetchGroup(
			Mockito.anyLong(), Mockito.eq(GroupConstants.CMS)
		);

		long siteGroupId = RandomTestUtil.randomLong();

		Group siteGroup = _setUpGroup(siteGroupId);

		Mockito.doReturn(
			siteGroup
		).when(
			_groupService
		).getGroup(
			siteGroupId
		);

		long spaceGroupId = RandomTestUtil.randomLong();

		Group spaceGroup = _setUpGroup(spaceGroupId, true);

		Mockito.doReturn(
			spaceGroup
		).when(
			_groupService
		).getGroup(
			spaceGroupId
		);

		DepotEntry depotEntry = Mockito.mock(DepotEntry.class);

		Mockito.doReturn(
			DepotConstants.TYPE_SPACE
		).when(
			depotEntry
		).getType();

		Mockito.doReturn(
			depotEntry
		).when(
			_depotEntryLocalService
		).fetchGroupDepotEntry(
			spaceGroupId
		);

		EditAssetListDisplayContext displayContext = Mockito.spy(
			_getEditAssetListDisplayContext(new UnicodeProperties()));

		Mockito.doReturn(
			new long[] {siteGroupId, spaceGroupId}
		).when(
			displayContext
		).getReferencedModelsGroupIds();

		long[] resolvedGroupIds = ArrayUtil.toLongArray(
			displayContext.getResolvedReferencedModelsGroupIds());

		Assert.assertTrue(ArrayUtil.contains(resolvedGroupIds, siteGroupId));
		Assert.assertTrue(ArrayUtil.contains(resolvedGroupIds, cmsGroupId));
		Assert.assertFalse(ArrayUtil.contains(resolvedGroupIds, spaceGroupId));
		Assert.assertEquals(
			Arrays.toString(resolvedGroupIds), 2, resolvedGroupIds.length);
	}

	@Test
	public void testGetVocabularyIdsWithMissingClassName() throws Exception {
		AssetVocabulary assetVocabulary = Mockito.mock(AssetVocabulary.class);

		long missingClassNameId = RandomTestUtil.randomLong();

		Mockito.doReturn(
			new long[] {missingClassNameId}
		).when(
			assetVocabulary
		).getSelectedClassNameIds();

		Mockito.doThrow(
			new RuntimeException()
		).when(
			_portal
		).getClassName(
			missingClassNameId
		);

		long groupId = RandomTestUtil.randomLong();

		long[] groupIds = {groupId};

		Mockito.doReturn(
			groupIds
		).when(
			_portal
		).getCurrentAndAncestorSiteGroupIds(
			Mockito.any(long[].class)
		);

		Mockito.doReturn(
			groupIds
		).when(
			_portal
		).getCurrentAndAncestorSiteGroupIds(
			Mockito.any(long[].class), Mockito.eq(true)
		);

		Group group = Mockito.mock(Group.class);

		Mockito.doReturn(
			groupId
		).when(
			group
		).getGroupId();

		Mockito.doReturn(
			group
		).when(
			_themeDisplay
		).getScopeGroup();

		Mockito.doReturn(
			group
		).when(
			_groupService
		).getGroup(
			groupId
		);

		try (MockedStatic<AssetVocabularyServiceUtil>
				assetVocabularyServiceUtilMockedStatic = Mockito.mockStatic(
					AssetVocabularyServiceUtil.class)) {

			assetVocabularyServiceUtilMockedStatic.when(
				() -> AssetVocabularyServiceUtil.getGroupsVocabularies(
					Mockito.any(long[].class))
			).thenReturn(
				ListUtil.fromArray(assetVocabulary)
			);

			Assert.assertEquals(
				Collections.emptyList(),
				_getEditAssetListDisplayContext(
					new UnicodeProperties()
				).getVocabularyIds());
		}
	}

	@Test
	public void testGetVocabularyIdsWithMissingVocabulary() throws Exception {
		long spaceGroupId = RandomTestUtil.randomLong();

		Group spaceGroup = _setUpGroup(spaceGroupId);

		Mockito.doReturn(
			spaceGroup
		).when(
			_themeDisplay
		).getScopeGroup();

		try (MockedStatic<GroupLocalServiceUtil>
				groupLocalServiceUtilMockedStatic = Mockito.mockStatic(
					GroupLocalServiceUtil.class)) {

			groupLocalServiceUtilMockedStatic.when(
				() -> GroupLocalServiceUtil.getGroups(Mockito.any(long[].class))
			).thenReturn(
				Collections.singletonList(spaceGroup)
			);

			Mockito.doReturn(
				new long[] {spaceGroupId}
			).when(
				_portal
			).getCurrentAndAncestorSiteGroupIds(
				Mockito.any(long[].class)
			);

			Mockito.doReturn(
				new long[] {spaceGroupId}
			).when(
				_portal
			).getCurrentAndAncestorSiteGroupIds(
				Mockito.any(long[].class), Mockito.anyBoolean()
			);

			AssetVocabularyGroupRel assetVocabularyGroupRel = Mockito.mock(
				AssetVocabularyGroupRel.class);

			long vocabularyId = RandomTestUtil.randomLong();

			Mockito.doReturn(
				vocabularyId
			).when(
				assetVocabularyGroupRel
			).getVocabularyId();

			Mockito.doReturn(
				Collections.singletonList(assetVocabularyGroupRel)
			).when(
				_assetVocabularyGroupRelLocalService
			).getAssetVocabularyGroupRelsByGroupId(
				Mockito.anyLong()
			);

			Mockito.doReturn(
				null
			).when(
				_assetVocabularyService
			).fetchVocabulary(
				vocabularyId
			);

			Mockito.doReturn(
				spaceGroup
			).when(
				_groupService
			).getGroup(
				spaceGroupId
			);

			EditAssetListDisplayContext displayContext =
				_getEditAssetListDisplayContext(new UnicodeProperties());

			List<Long> vocabularyIds = displayContext.getVocabularyIds();

			Assert.assertTrue(vocabularyIds.isEmpty());
		}
	}

	@Test
	public void testGetVocabularyIdsWithSpaceCMS() throws Exception {
		long spaceGroupId = RandomTestUtil.randomLong();

		Group spaceGroup = _setUpGroup(spaceGroupId);

		Mockito.doReturn(
			spaceGroup
		).when(
			_themeDisplay
		).getScopeGroup();

		try (MockedStatic<GroupLocalServiceUtil>
				groupLocalServiceUtilMockedStatic = Mockito.mockStatic(
					GroupLocalServiceUtil.class)) {

			groupLocalServiceUtilMockedStatic.when(
				() -> GroupLocalServiceUtil.getGroups(Mockito.any(long[].class))
			).thenReturn(
				Collections.singletonList(spaceGroup)
			);

			Mockito.doReturn(
				new long[] {spaceGroupId}
			).when(
				_portal
			).getCurrentAndAncestorSiteGroupIds(
				Mockito.any(long[].class)
			);

			Mockito.doReturn(
				new long[] {spaceGroupId}
			).when(
				_portal
			).getCurrentAndAncestorSiteGroupIds(
				Mockito.any(long[].class), Mockito.anyBoolean()
			);

			AssetVocabularyGroupRel assetVocabularyGroupRel = Mockito.mock(
				AssetVocabularyGroupRel.class);

			AssetVocabulary vocabulary = Mockito.mock(AssetVocabulary.class);

			long vocabularyId = RandomTestUtil.randomLong();

			Mockito.doReturn(
				vocabularyId
			).when(
				assetVocabularyGroupRel
			).getVocabularyId();

			Mockito.doReturn(
				Collections.singletonList(assetVocabularyGroupRel)
			).when(
				_assetVocabularyGroupRelLocalService
			).getAssetVocabularyGroupRelsByGroupId(
				Mockito.anyLong()
			);

			Mockito.doReturn(
				vocabularyId
			).when(
				vocabulary
			).getVocabularyId();

			Mockito.doReturn(
				new long[] {0}
			).when(
				vocabulary
			).getSelectedClassNameIds();

			Mockito.doReturn(
				vocabulary
			).when(
				_assetVocabularyService
			).fetchVocabulary(
				vocabularyId
			);

			Mockito.doReturn(
				spaceGroup
			).when(
				_groupService
			).getGroup(
				spaceGroupId
			);

			EditAssetListDisplayContext displayContext =
				_getEditAssetListDisplayContext(new UnicodeProperties());

			List<Long> vocabularyIds = displayContext.getVocabularyIds();

			Assert.assertTrue(vocabularyIds.contains(vocabularyId));
		}
	}

	private ClassType _getClassType() {
		return _getClassType(
			RandomTestUtil.randomLong(), RandomTestUtil.randomString());
	}

	private ClassType _getClassType(long classTypeId, String classTypeName) {
		ClassType classType = Mockito.mock(ClassType.class);

		Mockito.doReturn(
			classTypeId
		).when(
			classType
		).getClassTypeId();

		Mockito.doReturn(
			classTypeName
		).when(
			classType
		).getName();

		return classType;
	}

	private ClassTypeReader _getClassTypeReader(List<ClassType> classTypes)
		throws Exception {

		ClassTypeReader classTypeReader = Mockito.mock(ClassTypeReader.class);

		Mockito.doAnswer(
			invocationOnMock -> {
				Long curClassTypeId = invocationOnMock.getArgument(
					0, Long.class);

				for (ClassType classType : classTypes) {
					if (classType.getClassTypeId() == curClassTypeId) {
						return classType;
					}
				}

				throw new PortalException();
			}
		).when(
			classTypeReader
		).getClassType(
			Mockito.anyLong(), Mockito.eq(LocaleUtil.US)
		);

		Mockito.doReturn(
			classTypes
		).when(
			classTypeReader
		).getAvailableClassTypes(
			null, LocaleUtil.US
		);

		return classTypeReader;
	}

	private EditAssetListDisplayContext _getEditAssetListDisplayContext(
		UnicodeProperties unicodeProperties) {

		Mockito.doAnswer(
			invocationOnMock -> TestAssetRendererFactory.class
		).when(
			_assetRendererFactoryClassProvider
		).getClass(
			Mockito.any(AssetRendererFactory.class)
		);

		return new EditAssetListDisplayContext(
			_assetRendererFactoryClassProvider,
			_assetVocabularyGroupRelLocalService, _assetVocabularyService,
			_depotEntryLocalService, _depotEntryService, _groupLocalService,
			_groupService, _infoSearchClassMapperRegistry, _itemSelector,
			_objectDefinitionLocalService, _portletRequest, _portletResponse,
			_segmentsConfigurationProvider, unicodeProperties);
	}

	private void _setUpAssetListEntryLocalServiceUtil(
		String assetEntrySubtype, String assetEntryType, String typeSettings) {

		AssetListEntry assetListEntry = Mockito.mock(AssetListEntry.class);

		Mockito.doReturn(
			assetEntrySubtype
		).when(
			assetListEntry
		).getAssetEntrySubtype();

		Mockito.doReturn(
			assetEntryType
		).when(
			assetListEntry
		).getAssetEntryType();

		Mockito.doReturn(
			typeSettings
		).when(
			assetListEntry
		).getTypeSettings(
			Mockito.anyLong()
		);

		AssetListEntryLocalService assetListEntryLocalService = Mockito.mock(
			AssetListEntryLocalService.class);

		Mockito.doReturn(
			assetListEntry
		).when(
			assetListEntryLocalService
		).fetchAssetListEntry(
			Mockito.anyLong()
		);

		ReflectionTestUtil.setFieldValue(
			AssetListEntryLocalServiceUtil.class, "_serviceSnapshot",
			new Snapshot<AssetListEntryLocalService>(
				AssetListEntryLocalServiceUtil.class,
				AssetListEntryLocalService.class) {

				@Override
				public AssetListEntryLocalService get() {
					return assetListEntryLocalService;
				}

			});
	}

	private void _setUpAssetRendererFactoryRegistryUtil(
		String className, long classNameId, ClassTypeReader classTypeReader,
		boolean supportsClassTypes, String typeName) {

		AssetRendererFactory<?> assetRendererFactory = null;

		if (className != null) {
			assetRendererFactory = Mockito.mock(AssetRendererFactory.class);

			Mockito.doReturn(
				true
			).when(
				assetRendererFactory
			).isActive(
				Mockito.anyLong()
			);

			Mockito.doReturn(
				className
			).when(
				assetRendererFactory
			).getClassName();

			Mockito.doReturn(
				classNameId
			).when(
				assetRendererFactory
			).getClassNameId();

			Mockito.doReturn(
				classTypeReader
			).when(
				assetRendererFactory
			).getClassTypeReader();

			Mockito.doReturn(
				typeName
			).when(
				assetRendererFactory
			).getTypeName(
				LocaleUtil.US
			);

			Mockito.doReturn(
				true
			).when(
				assetRendererFactory
			).isSelectable();

			Mockito.doReturn(
				supportsClassTypes
			).when(
				assetRendererFactory
			).isSupportsClassTypes();
		}

		_assetRendererFactoryRegistryUtilMockedStatic.when(
			() ->
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassNameId(classNameId)
		).thenReturn(
			assetRendererFactory
		);

		ArrayList<AssetRendererFactory<?>> assetRendererFactories =
			new ArrayList<>();

		assetRendererFactories.add(assetRendererFactory);

		_assetRendererFactoryRegistryUtilMockedStatic.when(
			() -> AssetRendererFactoryRegistryUtil.getAssetRendererFactories(
				Mockito.anyLong(), Mockito.eq(true))
		).thenReturn(
			assetRendererFactories
		);
	}

	private void _setUpAssetRendererFactoryRegistryUtil(
		String className, long classNameId, String typeName) {

		_setUpAssetRendererFactoryRegistryUtil(
			className, classNameId, null, false, typeName);
	}

	private Group _setUpGroup(long scopeGroupId) {
		return _setUpGroup(scopeGroupId, false);
	}

	private Group _setUpGroup(long scopeGroupId, boolean depot) {
		Group scopeGroup = Mockito.mock(Group.class);

		Mockito.doReturn(
			scopeGroupId
		).when(
			scopeGroup
		).getGroupId();

		Mockito.doReturn(
			depot
		).when(
			scopeGroup
		).isDepot();

		return scopeGroup;
	}

	private void _setUpHttpServletRequest() {
		Mockito.doReturn(
			LocaleUtil.US
		).when(
			_themeDisplay
		).getLocale();

		Mockito.doReturn(
			_themeDisplay
		).when(
			_httpServletRequest
		).getAttribute(
			WebKeys.THEME_DISPLAY
		);
	}

	private void _setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(Mockito.mock(Language.class));
	}

	private void _setUpPortalUtil() {
		PortalUtil portalUtil = new PortalUtil();

		Mockito.doReturn(
			_httpServletRequest
		).when(
			_portal
		).getHttpServletRequest(
			_portletRequest
		);

		portalUtil.setPortal(_portal);
	}

	private static final MockedStatic<AssetRendererFactoryRegistryUtil>
		_assetRendererFactoryRegistryUtilMockedStatic = Mockito.mockStatic(
			AssetRendererFactoryRegistryUtil.class);

	private final AssetRendererFactoryClassProvider
		_assetRendererFactoryClassProvider = Mockito.mock(
			AssetRendererFactoryClassProvider.class);
	private final AssetVocabularyGroupRelLocalService
		_assetVocabularyGroupRelLocalService = Mockito.mock(
			AssetVocabularyGroupRelLocalService.class);
	private final AssetVocabularyService _assetVocabularyService = Mockito.mock(
		AssetVocabularyService.class);
	private final DepotEntryLocalService _depotEntryLocalService = Mockito.mock(
		DepotEntryLocalService.class);
	private final DepotEntryService _depotEntryService = Mockito.mock(
		DepotEntryService.class);
	private final GroupLocalService _groupLocalService = Mockito.mock(
		GroupLocalService.class);
	private final GroupService _groupService = Mockito.mock(GroupService.class);
	private final HttpServletRequest _httpServletRequest = Mockito.mock(
		HttpServletRequest.class);
	private final InfoSearchClassMapperRegistry _infoSearchClassMapperRegistry =
		Mockito.mock(InfoSearchClassMapperRegistry.class);
	private final ItemSelector _itemSelector = Mockito.mock(ItemSelector.class);
	private final ObjectDefinitionLocalService _objectDefinitionLocalService =
		Mockito.mock(ObjectDefinitionLocalService.class);
	private final Portal _portal = Mockito.mock(Portal.class);
	private final PortletRequest _portletRequest = Mockito.mock(
		PortletRequest.class);
	private final PortletResponse _portletResponse = Mockito.mock(
		PortletResponse.class);
	private final SegmentsConfigurationProvider _segmentsConfigurationProvider =
		Mockito.mock(SegmentsConfigurationProvider.class);
	private final ThemeDisplay _themeDisplay = Mockito.mock(ThemeDisplay.class);

}