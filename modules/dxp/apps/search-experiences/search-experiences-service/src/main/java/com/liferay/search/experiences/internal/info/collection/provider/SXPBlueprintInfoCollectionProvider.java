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
import com.liferay.info.collection.provider.SingleFormVariationInfoCollectionProvider;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.type.SelectInfoFieldType;
import com.liferay.info.filter.CategoriesInfoFilter;
import com.liferay.info.filter.InfoFilter;
import com.liferay.info.filter.KeywordsInfoFilter;
import com.liferay.info.filter.TagsInfoFilter;
import com.liferay.info.form.InfoForm;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.localized.bundle.ResourceBundleInfoLocalizedValue;
import com.liferay.info.pagination.InfoPage;
import com.liferay.info.pagination.Pagination;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.search.experiences.model.SXPBlueprint;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Tibor Lipusz
 * @author Gustavo Lima
 */
public class SXPBlueprintInfoCollectionProvider
	implements ConfigurableInfoCollectionProvider<AssetEntry>,
			   FilteredInfoCollectionProvider<AssetEntry>,
			   SingleFormVariationInfoCollectionProvider<AssetEntry> {

	public SXPBlueprintInfoCollectionProvider(
		AssetHelper assetHelper, GroupLocalService groupLocalService,
		Searcher searcher,
		SearchRequestBuilderFactory searchRequestBuilderFactory,
		SXPBlueprint sxpBlueprint) {

		_assetHelper = assetHelper;
		_groupLocalService = groupLocalService;
		_searcher = searcher;
		_searchRequestBuilderFactory = searchRequestBuilderFactory;
		_sxpBlueprint = sxpBlueprint;
	}

	@Override
	public InfoPage<AssetEntry> getCollectionInfoPage(
		CollectionQuery collectionQuery) {

		try {
			Pagination pagination = collectionQuery.getPagination();

			SearchRequestBuilder searchRequestBuilder =
				_getSearchRequestBuilder(collectionQuery, pagination);

			SearchResponse searchResponse = _searcher.search(
				searchRequestBuilder.build());

			return InfoPage.of(
				_assetHelper.getAssetEntries(searchResponse.getSearchHits()));
		}
		catch (Exception exception) {
			_log.error("Unable to get asset entries", exception);
		}

		return InfoPage.of(
			Collections.emptyList(), collectionQuery.getPagination(), 0);
	}

	@Override
	public InfoForm getConfigurationInfoForm() {
		return InfoForm.builder(
		).infoFieldSetEntry(
			InfoField.builder(
			).infoFieldType(
				SelectInfoFieldType.INSTANCE
			).namespace(
				StringPool.BLANK
			).name(
				"scope"
			).attribute(
				SelectInfoFieldType.OPTIONS, _getOptions()
			).attribute(
				SelectInfoFieldType.MULTIPLE, true
			).labelInfoLocalizedValue(
				InfoLocalizedValue.localize(getClass(), "Scope")
			).localizable(
				false
			).build()
		).name(
			"search-experiences"
		).build();
	}

	@Override
	public String getFormVariationKey() {
		return _sxpBlueprint.getExternalReferenceCode();
	}

	@Override
	public String getKey() {
		return StringBundler.concat(
			SingleFormVariationInfoCollectionProvider.super.getKey(), "_",
			_sxpBlueprint.getCompanyId(), "_",
			_sxpBlueprint.getExternalReferenceCode());
	}

	@Override
	public String getLabel(Locale locale) {
		return _sxpBlueprint.getTitle(locale);
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

	private List<SelectInfoFieldType.Option> _getOptions() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		List<SelectInfoFieldType.Option> options = ListUtil.fromArray(
			new SelectInfoFieldType.Option(
				true,
				new ResourceBundleInfoLocalizedValue(getClass(), "This Site"),
				String.valueOf(serviceContext.getScopeGroupId())));

		List<Group> groups = _groupLocalService.getActiveGroups(
			themeDisplay.getCompanyId(), true);

		for (Group group : groups) {
			if ((group != null) && group.isSite() && !group.isGuest()) {
				User user = themeDisplay.getUser();

				try {
					List<Group> userSiteGroups = user.getSiteGroups();

					if (!ArrayUtil.contains(
							userSiteGroups.toArray(new Group[0]), group)) {

						continue;
					}
				}
				catch (PortalException portalException) {
					_log.error(portalException);
				}

				options.add(
					new SelectInfoFieldType.Option(
						new ResourceBundleInfoLocalizedValue(
							getClass(), group.getNameCurrentValue()),
						String.valueOf(group.getGroupId())));
			}
		}

		return options;
	}

	private SearchRequestBuilder _getSearchRequestBuilder(
		CollectionQuery collectionQuery, Pagination pagination) {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Map<String, String[]> configuration =
			collectionQuery.getConfiguration();

		if (configuration == null) {
			configuration = Collections.emptyMap();
		}

		String[] scopes = configuration.get("scope");

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		if (scopes == null) {
			scopes = new String[] {
				String.valueOf(themeDisplay.getScopeGroupId())
			};
		}

		return _searchRequestBuilderFactory.builder(
		).companyId(
			serviceContext.getCompanyId()
		).groupIds(
			ListUtil.toLongArray(Arrays.asList(scopes), GetterUtil::getLong)
		).from(
			pagination.getStart()
		).emptySearchEnabled(
			true
		).size(
			pagination.getEnd() - pagination.getStart()
		).withSearchContext(
			searchContext -> {
				CategoriesInfoFilter categoriesInfoFilter =
					collectionQuery.getInfoFilter(CategoriesInfoFilter.class);

				if ((categoriesInfoFilter != null) &&
					!ArrayUtil.isEmpty(categoriesInfoFilter.getCategoryIds())) {

					long[] categoryIds = ArrayUtil.append(
						categoriesInfoFilter.getCategoryIds());

					categoryIds = ArrayUtil.unique(categoryIds);

					searchContext.setAssetCategoryIds(categoryIds);
				}

				TagsInfoFilter tagsInfoFilter = collectionQuery.getInfoFilter(
					TagsInfoFilter.class);

				if ((tagsInfoFilter != null) &&
					!ArrayUtil.isEmpty(tagsInfoFilter.getTagNames())) {

					String[] tagNames = ArrayUtil.append(
						tagsInfoFilter.getTagNames());

					tagNames = ArrayUtil.unique(tagNames);

					searchContext.setAssetTagNames(tagNames);
				}

				searchContext.setAttribute(
					"search.experiences.blueprint.id",
					_sxpBlueprint.getSXPBlueprintId());
				searchContext.setAttribute(
					"search.experiences.ip.address",
					serviceContext.getRemoteAddr());

				searchContext.setAttribute(
					"search.experiences.scope.group.id",
					themeDisplay.getScopeGroupId());

				KeywordsInfoFilter keywordsInfoFilter =
					collectionQuery.getInfoFilter(KeywordsInfoFilter.class);

				if (keywordsInfoFilter != null) {
					searchContext.setKeywords(keywordsInfoFilter.getKeywords());
				}

				searchContext.setLocale(serviceContext.getLocale());
				searchContext.setTimeZone(serviceContext.getTimeZone());
				searchContext.setUserId(serviceContext.getUserId());
			}
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SXPBlueprintInfoCollectionProvider.class);

	private final AssetHelper _assetHelper;
	private final GroupLocalService _groupLocalService;
	private final Searcher _searcher;
	private final SearchRequestBuilderFactory _searchRequestBuilderFactory;
	private final SXPBlueprint _sxpBlueprint;

}