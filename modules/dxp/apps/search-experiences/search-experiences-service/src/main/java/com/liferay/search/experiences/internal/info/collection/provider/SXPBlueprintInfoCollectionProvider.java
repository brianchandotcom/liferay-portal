/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.internal.info.collection.provider;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.util.AssetHelper;
import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.ConfigurableInfoCollectionProvider;
import com.liferay.info.collection.provider.FilteredInfoCollectionProvider;
import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.filter.CategoriesInfoFilter;
import com.liferay.info.filter.InfoFilter;
import com.liferay.info.filter.KeywordsInfoFilter;
import com.liferay.info.filter.TagsInfoFilter;
import com.liferay.info.form.InfoForm;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.pagination.InfoPage;
import com.liferay.info.pagination.Pagination;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.search.experiences.exception.NoSuchSXPBlueprintException;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.service.SXPBlueprintLocalService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tibor Lipusz
 */
@Component(
	enabled = false,
	property = "item.class.name=com.liferay.asset.kernel.model.AssetEntry",
	service = InfoCollectionProvider.class
)
public class SXPBlueprintInfoCollectionProvider
	implements ConfigurableInfoCollectionProvider<AssetEntry>,
			   FilteredInfoCollectionProvider<AssetEntry> {

	@Override
	public InfoPage<AssetEntry> getCollectionInfoPage(
		CollectionQuery collectionQuery) {

		Map<String, String[]> configuration =
			collectionQuery.getConfiguration();

		if (configuration == null) {
			configuration = Collections.emptyMap();
		}

		String[] sxpBlueprintExternalReferenceCodes = configuration.get(
			"sxpBlueprintExternalReferenceCode");

		if ((sxpBlueprintExternalReferenceCodes == null) ||
			Validator.isNull(sxpBlueprintExternalReferenceCodes[0])) {

			return InfoPage.of(
				Collections.emptyList(), collectionQuery.getPagination(), 0);
		}

		String sxpBlueprintExternalReferenceCode =
			sxpBlueprintExternalReferenceCodes[0];

		try {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			SXPBlueprint sxpBlueprint =
				_sxpBlueprintLocalService.
					getSXPBlueprintByExternalReferenceCode(
						sxpBlueprintExternalReferenceCode,
						serviceContext.getCompanyId());

			SearchRequestBuilder searchRequestBuilder =
				_searchRequestBuilderFactory.builder(
				).companyId(
					serviceContext.getCompanyId()
				).emptySearchEnabled(
					true
				).withSearchContext(
					searchContext -> {
						CategoriesInfoFilter categoriesInfoFilter =
							collectionQuery.getInfoFilter(
								CategoriesInfoFilter.class);

						if ((categoriesInfoFilter != null) &&
							!ArrayUtil.isEmpty(
								categoriesInfoFilter.getCategoryIds())) {

							long[] categoryIds = ArrayUtil.append(
								categoriesInfoFilter.getCategoryIds());

							categoryIds = ArrayUtil.unique(categoryIds);

							searchContext.setAssetCategoryIds(categoryIds);
						}

						TagsInfoFilter tagsInfoFilter =
							collectionQuery.getInfoFilter(TagsInfoFilter.class);

						if ((tagsInfoFilter != null) &&
							!ArrayUtil.isEmpty(tagsInfoFilter.getTagNames())) {

							String[] tagNames = ArrayUtil.append(
								tagsInfoFilter.getTagNames());

							tagNames = ArrayUtil.unique(tagNames);

							searchContext.setAssetTagNames(tagNames);
						}

						searchContext.setAttribute(
							"search.experiences.blueprint.id",
							sxpBlueprint.getSXPBlueprintId());
						searchContext.setLocale(serviceContext.getLocale());

						searchContext.setAttribute(
							"search.experiences.ip.address",
							serviceContext.getRemoteAddr());

						ThemeDisplay themeDisplay =
							serviceContext.getThemeDisplay();

						searchContext.setAttribute(
							"search.experiences.scope.group.id",
							themeDisplay.getScopeGroupId());

						Pagination pagination = collectionQuery.getPagination();

						searchContext.setEnd(pagination.getEnd());

						KeywordsInfoFilter keywordsInfoFilter =
							collectionQuery.getInfoFilter(
								KeywordsInfoFilter.class);

						if (keywordsInfoFilter != null) {
							searchContext.setKeywords(
								keywordsInfoFilter.getKeywords());
						}

						searchContext.setStart(pagination.getStart());
						searchContext.setTimeZone(serviceContext.getTimeZone());
						searchContext.setUserId(serviceContext.getUserId());
					}
				);

			SearchResponse searchResponse = _searcher.search(
				searchRequestBuilder.build());

			return InfoPage.of(
				_assetHelper.getAssetEntries(searchResponse.getSearchHits()));
		}
		catch (NoSuchSXPBlueprintException noSuchSXPBlueprintException) {
			_log.error(noSuchSXPBlueprintException);
		}
		catch (Exception exception) {
			_log.error("Unable to get asset entries", exception);
		}

		return InfoPage.of(
			Collections.emptyList(), collectionQuery.getPagination(), 0);
	}

	// TODO Implement SXPBlueprntsOptionsPortlet Selector screen

	@Override
	public InfoForm getConfigurationInfoForm() {
		return InfoForm.builder(
		).infoFieldSetEntry(
			InfoField.builder(
			).infoFieldType(
				TextInfoFieldType.INSTANCE
			).namespace(
				StringPool.BLANK
			).name(
				"sxpBlueprintExternalReferenceCode"
			).labelInfoLocalizedValue(
				InfoLocalizedValue.localize(getClass(), "blueprint")
			).localizable(
				false
			).build()
		).build();
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "blueprint");
	}

	@Override
	public List<InfoFilter> getSupportedInfoFilters() {
		return Arrays.asList(
			new CategoriesInfoFilter(), new KeywordsInfoFilter(),
			new TagsInfoFilter());
	}

	@Override
	public boolean isAvailable() {
		return FeatureFlagManagerUtil.isEnabled("LPS-129412");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SXPBlueprintInfoCollectionProvider.class);

	@Reference
	private AssetHelper _assetHelper;

	@Reference
	private Language _language;

	@Reference
	private Searcher _searcher;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@Reference
	private SXPBlueprintLocalService _sxpBlueprintLocalService;

}