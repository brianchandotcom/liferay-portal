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

package com.liferay.asset.categories.internal.layout.display.page;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.AssetVocabularyConstants;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.HierarchicalInfoItemReference;
import com.liferay.info.item.InfoItemReference;
import com.liferay.layout.display.page.LayoutDisplayPageMultiSelectionProvider;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.asset.util.comparator.AssetVocabularyGroupLocalizedTitleComparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	immediate = true, service = LayoutDisplayPageMultiSelectionProvider.class
)
public class AssetCategoryLayoutDisplayPageMultiSelectionProvider
	implements LayoutDisplayPageMultiSelectionProvider<AssetCategory> {

	@Override
	public String getClassName() {
		return AssetCategory.class.getName();
	}

	@Override
	public String getPluralLabel(Locale locale) {
		return LanguageUtil.get(locale, "categories");
	}

	@Override
	public List<InfoItemReference> process(
		List<InfoItemReference> infoItemReferences) {

		Stream<InfoItemReference> stream = infoItemReferences.stream();

		Map<Long, Map<Long, InfoItemReference>>
			vocabularyIdInfoItemReferencesMap = stream.filter(
				infoItemReference ->
					Objects.equals(
						getClassName(), infoItemReference.getClassName()) &&
					(_getClassPK(infoItemReference) > 0)
			).collect(
				Collectors.groupingBy(
					infoItemReference -> {
						AssetCategory assetCategory =
							_assetCategoryLocalService.fetchAssetCategory(
								_getClassPK(infoItemReference));

						return assetCategory.getVocabularyId();
					},
					Collectors.toMap(
						infoItemReference -> _getClassPK(infoItemReference),
						Function.identity()))
			);

		List<InfoItemReference> hierarchicalInfoItemReferences =
			new ArrayList<>();

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		for (long vocabularyId : _getOrderedVocabularyIds(themeDisplay)) {
			Map<Long, InfoItemReference> categoryIdInfoItemReferences =
				vocabularyIdInfoItemReferencesMap.get(vocabularyId);

			if (MapUtil.isEmpty(categoryIdInfoItemReferences)) {
				continue;
			}

			Set<Long> categoryIds = categoryIdInfoItemReferences.keySet();

			Map<Long, List<InfoItemReference>>
				parentCategoryIdInfoItemReferencesMap = new HashMap<>();

			for (InfoItemReference infoItemReference :
					categoryIdInfoItemReferences.values()) {

				AssetCategory assetCategory =
					_assetCategoryLocalService.fetchAssetCategory(
						_getClassPK(infoItemReference));

				long parentCategoryId = _getNearestAncestorCategoryId(
					assetCategory, categoryIds);

				List<InfoItemReference> parentCategoryIdInfoItemReferences =
					parentCategoryIdInfoItemReferencesMap.get(parentCategoryId);

				if (parentCategoryIdInfoItemReferences == null) {
					parentCategoryIdInfoItemReferences = new ArrayList<>();

					parentCategoryIdInfoItemReferencesMap.put(
						parentCategoryId, parentCategoryIdInfoItemReferences);
				}

				parentCategoryIdInfoItemReferences.add(infoItemReference);
			}

			hierarchicalInfoItemReferences.addAll(
				_getHierarchicalInfoItemReferences(
					parentCategoryIdInfoItemReferencesMap, 0L));
		}

		return hierarchicalInfoItemReferences;
	}

	private long _getClassPK(InfoItemReference infoItemReference) {
		if (infoItemReference.getInfoItemIdentifier() instanceof
				ClassPKInfoItemIdentifier) {

			ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
				(ClassPKInfoItemIdentifier)
					infoItemReference.getInfoItemIdentifier();

			return classPKInfoItemIdentifier.getClassPK();
		}

		return 0;
	}

	private List<HierarchicalInfoItemReference>
		_getHierarchicalInfoItemReferences(
			Map<Long, List<InfoItemReference>>
				parentCategoryIdInfoItemReferencesMap,
			long parentCategoryId) {

		if (!parentCategoryIdInfoItemReferencesMap.containsKey(
				parentCategoryId)) {

			return Collections.emptyList();
		}

		List<HierarchicalInfoItemReference> hierarchicalInfoItemReferences =
			new ArrayList<>();

		List<InfoItemReference> infoItemReferences = ListUtil.sort(
			parentCategoryIdInfoItemReferencesMap.get(parentCategoryId),
			Comparator.comparing(
				infoItemReference -> {
					AssetCategory assetCategory =
						_assetCategoryLocalService.fetchAssetCategory(
							_getClassPK(infoItemReference));

					return assetCategory.getName();
				}));

		for (InfoItemReference infoItemReference : infoItemReferences) {
			HierarchicalInfoItemReference hierarchicalInfoItemReference =
				new HierarchicalInfoItemReference(
					infoItemReference.getClassName(),
					infoItemReference.getInfoItemIdentifier());

			hierarchicalInfoItemReference.
				setChildrenHierarchicalInfoItemReferences(
					_getHierarchicalInfoItemReferences(
						parentCategoryIdInfoItemReferencesMap,
						_getClassPK(infoItemReference)));

			hierarchicalInfoItemReferences.add(hierarchicalInfoItemReference);
		}

		return hierarchicalInfoItemReferences;
	}

	private long _getNearestAncestorCategoryId(
		AssetCategory assetCategory, Set<Long> availableCategoryIds) {

		String treePath = assetCategory.getTreePath();

		Stream<String> stream = Arrays.stream(treePath.split("/"));

		return stream.filter(
			s -> Validator.isNotNull(s)
		).mapToLong(
			Long::valueOf
		).filter(
			categoryId -> !Objects.equals(
				categoryId, assetCategory.getCategoryId())
		).boxed(
		).sorted(
			Collections.reverseOrder()
		).filter(
			parentCategoryId -> availableCategoryIds.contains(parentCategoryId)
		).findFirst(
		).orElse(
			0L
		);
	}

	private List<Long> _getOrderedVocabularyIds(ThemeDisplay themeDisplay) {
		List<AssetVocabulary> assetVocabularies =
			_assetVocabularyLocalService.getGroupVocabularies(
				new long[] {
					themeDisplay.getCompanyGroupId(),
					themeDisplay.getScopeGroupId()
				},
				new int[] {AssetVocabularyConstants.VISIBILITY_TYPE_PUBLIC});

		if (assetVocabularies.isEmpty()) {
			return Collections.emptyList();
		}

		ListUtil.sort(
			assetVocabularies,
			new AssetVocabularyGroupLocalizedTitleComparator(
				themeDisplay.getScopeGroupId(), themeDisplay.getLocale(),
				true));

		return ListUtil.toList(
			assetVocabularies, AssetVocabulary.VOCABULARY_ID_ACCESSOR);
	}

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

}