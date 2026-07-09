/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.frontend.data.set.filter;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.frontend.data.set.constants.FDSEntityFieldTypes;
import com.liferay.frontend.data.set.filter.SelectionFDSFilterItem;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Fábio Alves
 */
public class FunnelStageCategorySelectionFDSFilterTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		_funnelStageCategorySelectionFDSFilter =
			new FunnelStageCategorySelectionFDSFilter();

		ReflectionTestUtil.setFieldValue(
			_funnelStageCategorySelectionFDSFilter, "assetCategoryLocalService",
			_assetCategoryLocalService);
		ReflectionTestUtil.setFieldValue(
			_funnelStageCategorySelectionFDSFilter,
			"assetVocabularyLocalService", _assetVocabularyLocalService);
		ReflectionTestUtil.setFieldValue(
			_funnelStageCategorySelectionFDSFilter, "groupLocalService",
			_groupLocalService);
	}

	@Test
	public void testGetProperties() {
		Assert.assertEquals(
			FDSEntityFieldTypes.INTEGER,
			_funnelStageCategorySelectionFDSFilter.getEntityFieldType());
		Assert.assertEquals(
			"cmpFunnelStageCategoryIds",
			_funnelStageCategorySelectionFDSFilter.getId());
		Assert.assertEquals(
			"funnel-stage", _funnelStageCategorySelectionFDSFilter.getLabel());
	}

	@Test
	public void testGetSelectionFDSFilterItems() throws Exception {
		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(_COMPANY_ID)) {

			Mockito.when(
				_group.getGroupId()
			).thenReturn(
				_GROUP_ID
			);

			Mockito.when(
				_groupLocalService.fetchGroup(_COMPANY_ID, GroupConstants.CMS)
			).thenReturn(
				_group
			);

			AssetVocabulary assetVocabulary = Mockito.mock(
				AssetVocabulary.class);

			Mockito.when(
				_assetVocabularyLocalService.
					fetchAssetVocabularyByExternalReferenceCode(
						"L_CMP_FUNNEL_STAGE", _GROUP_ID)
			).thenReturn(
				assetVocabulary
			);

			AssetCategory assetCategory = Mockito.mock(AssetCategory.class);

			long assetCategoryId = RandomTestUtil.randomLong();

			Mockito.when(
				assetCategory.getCategoryId()
			).thenReturn(
				assetCategoryId
			);

			String assetCategoryTitle = RandomTestUtil.randomString();

			Mockito.when(
				assetCategory.getTitle(_locale)
			).thenReturn(
				assetCategoryTitle
			);

			long vocabularyId = RandomTestUtil.randomLong();

			Mockito.when(
				assetVocabulary.getVocabularyId()
			).thenReturn(
				vocabularyId
			);

			Mockito.when(
				_assetCategoryLocalService.getVocabularyCategories(
					Mockito.eq(vocabularyId), Mockito.anyInt(),
					Mockito.anyInt(), Mockito.any())
			).thenReturn(
				List.of(assetCategory)
			);

			List<SelectionFDSFilterItem> selectionFDSFilterItems =
				_funnelStageCategorySelectionFDSFilter.
					getSelectionFDSFilterItems(_locale);

			Assert.assertEquals(
				selectionFDSFilterItems.toString(), 1,
				selectionFDSFilterItems.size());

			SelectionFDSFilterItem selectionFDSFilterItem =
				selectionFDSFilterItems.get(0);

			Assert.assertEquals(
				assetCategoryId, selectionFDSFilterItem.getValue());
			Assert.assertEquals(
				assetCategoryTitle, selectionFDSFilterItem.getLabel());
		}
	}

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private static final long _GROUP_ID = RandomTestUtil.randomLong();

	@Mock
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Mock
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	private FunnelStageCategorySelectionFDSFilter
		_funnelStageCategorySelectionFDSFilter;

	@Mock
	private Group _group;

	@Mock
	private GroupLocalService _groupLocalService;

	private final Locale _locale = LocaleUtil.US;

}