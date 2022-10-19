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

package com.liferay.object.internal.info.collection.provider;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.depot.util.SiteConnectedGroupGroupProviderUtil;
import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.ConfigurableInfoCollectionProvider;
import com.liferay.info.collection.provider.FilteredInfoCollectionProvider;
import com.liferay.info.collection.provider.SingleFormVariationInfoCollectionProvider;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.InfoFieldSetEntry;
import com.liferay.info.field.type.SelectInfoFieldType;
import com.liferay.info.filter.InfoFilter;
import com.liferay.info.filter.KeywordsInfoFilter;
import com.liferay.info.form.InfoForm;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.localized.SingleValueInfoLocalizedValue;
import com.liferay.info.localized.bundle.FunctionInfoLocalizedValue;
import com.liferay.info.localized.bundle.ResourceBundleInfoLocalizedValue;
import com.liferay.info.pagination.InfoPage;
import com.liferay.info.pagination.Pagination;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectLayoutBoxConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectLayout;
import com.liferay.object.model.ObjectLayoutTab;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerTracker;
import com.liferay.object.scope.ObjectScopeProvider;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectLayoutLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.NestedQuery;
import com.liferay.portal.kernel.search.generic.TermQueryImpl;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portlet.asset.util.comparator.AssetTagNameComparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Jorge Ferrer
 * @author Guilherme Camacho
 */
public class ObjectEntrySingleFormVariationInfoCollectionProvider
	implements ConfigurableInfoCollectionProvider<ObjectEntry>,
			   FilteredInfoCollectionProvider<ObjectEntry>,
			   SingleFormVariationInfoCollectionProvider<ObjectEntry> {

	public ObjectEntrySingleFormVariationInfoCollectionProvider(
		AssetCategoryLocalService assetCategoryLocalService,
		AssetTagLocalService assetTagLocalService,
		AssetVocabularyLocalService assetVocabularyLocalService,
		GroupLocalService groupLocalService,
		ListTypeEntryLocalService listTypeEntryLocalService,
		ObjectDefinition objectDefinition,
		ObjectEntryLocalService objectEntryLocalService,
		ObjectEntryManagerTracker objectEntryManagerTracker,
		ObjectFieldLocalService objectFieldLocalService,
		ObjectLayoutLocalService objectLayoutLocalService,
		ObjectScopeProviderRegistry objectScopeProviderRegistry) {

		_assetCategoryLocalService = assetCategoryLocalService;
		_assetTagLocalService = assetTagLocalService;
		_assetVocabularyLocalService = assetVocabularyLocalService;
		_groupLocalService = groupLocalService;
		_listTypeEntryLocalService = listTypeEntryLocalService;
		_objectDefinition = objectDefinition;
		_objectEntryLocalService = objectEntryLocalService;
		_objectEntryManagerTracker = objectEntryManagerTracker;
		_objectFieldLocalService = objectFieldLocalService;
		_objectLayoutLocalService = objectLayoutLocalService;
		_objectScopeProviderRegistry = objectScopeProviderRegistry;
	}

	@Override
	public InfoPage<ObjectEntry> getCollectionInfoPage(
		CollectionQuery collectionQuery) {

		try {
			if (_objectDefinition.isDefaultStorageType()) {
				return _getCollectionInfoPageByDefaultStorageType(
					collectionQuery);
			}

			return _getCollectionInfoPageByObjectEntryManager(collectionQuery);
		}
		catch (Exception exception) {
			throw new RuntimeException(
				"Unable to get object entries for object definition " +
					_objectDefinition.getObjectDefinitionId(),
				exception);
		}
	}

	@Override
	public String getCollectionItemClassName() {
		return _objectDefinition.getClassName();
	}

	@Override
	public InfoForm getConfigurationInfoForm() {
		return InfoForm.builder(
		).infoFieldSetEntries(
			_getInfoFieldSetEntries()
		).infoFieldSetEntry(
			InfoFieldSet.builder(
			).infoFieldSetEntry(
				unsafeConsumer -> {
					InfoField<?> infoField = _getInfoField();

					if (infoField != null) {
						unsafeConsumer.accept(infoField);
					}
				}
			).build()
		).infoFieldSetEntry(
			InfoFieldSet.builder(
			).infoFieldSetEntry(
				unsafeConsumer -> {
					for (ObjectField objectField :
							_objectFieldLocalService.getObjectFields(
								_objectDefinition.getObjectDefinitionId())) {

						if (!(Objects.equals(
								objectField.getDBType(),
								ObjectFieldConstants.DB_TYPE_BOOLEAN) ||
							  (Objects.equals(
								  objectField.getDBType(),
								  ObjectFieldConstants.DB_TYPE_STRING) &&
							   (objectField.getListTypeDefinitionId() != 0))) ||
							!objectField.isIndexed()) {

							continue;
						}

						unsafeConsumer.accept(
							InfoField.builder(
							).infoFieldType(
								SelectInfoFieldType.INSTANCE
							).namespace(
								StringPool.BLANK
							).name(
								objectField.getName()
							).attribute(
								SelectInfoFieldType.OPTIONS,
								_getOptions(objectField)
							).labelInfoLocalizedValue(
								InfoLocalizedValue.<String>builder(
								).values(
									objectField.getLabelMap()
								).build()
							).localizable(
								true
							).build());
					}
				}
			).build()
		).build();
	}

	@Override
	public String getFormVariationKey() {
		return String.valueOf(_objectDefinition.getObjectDefinitionId());
	}

	@Override
	public String getKey() {
		return StringBundler.concat(
			SingleFormVariationInfoCollectionProvider.super.getKey(), "_",
			_objectDefinition.getName());
	}

	@Override
	public String getLabel(Locale locale) {
		return _objectDefinition.getPluralLabel(locale);
	}

	@Override
	public List<InfoFilter> getSupportedInfoFilters() {
		return Arrays.asList(new KeywordsInfoFilter());
	}

	@Override
	public boolean isAvailable() {
		if (_objectDefinition.getCompanyId() !=
				CompanyThreadLocal.getCompanyId()) {

			return false;
		}

		return true;
	}

	private SearchContext _buildSearchContext(CollectionQuery collectionQuery)
		throws Exception {

		SearchContext searchContext = new SearchContext();

		List<String> assetCategoryIds = new ArrayList<>();

		Optional<Map<String, String[]>> configurationOptional =
			collectionQuery.getConfigurationOptional();

		Map<String, String[]> configuration = configurationOptional.orElse(
			Collections.emptyMap());

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		for (AssetVocabulary assetVocabulary :
				_getAssetVocabularies(serviceContext)) {

			String[] assetCategoryIdsArray = configuration.get(
				String.valueOf(assetVocabulary.getVocabularyId()));

			if ((assetCategoryIdsArray != null) &&
				!StringUtil.equals(assetCategoryIdsArray[0], "null")) {

				Collections.addAll(assetCategoryIds, assetCategoryIdsArray);
			}
		}

		searchContext.setAssetCategoryIds(
			ListUtil.toLongArray(assetCategoryIds, Long::parseLong));

		String[] assetTagNames = configuration.get(Field.ASSET_TAG_NAMES);

		if (ArrayUtil.isNotEmpty(assetTagNames) &&
			Validator.isNotNull(assetTagNames[0])) {

			searchContext.setAssetTagNames(assetTagNames);
		}

		searchContext.setAttribute(
			Field.STATUS, WorkflowConstants.STATUS_APPROVED);
		searchContext.setAttribute(
			"objectDefinitionId", _objectDefinition.getObjectDefinitionId());
		searchContext.setBooleanClauses(_getBooleanClauses(collectionQuery));
		searchContext.setCompanyId(serviceContext.getCompanyId());

		Pagination pagination = collectionQuery.getPagination();

		searchContext.setEnd(pagination.getEnd());

		searchContext.setGroupIds(new long[] {_getGroupId()});

		Optional<KeywordsInfoFilter> keywordsInfoFilterOptional =
			collectionQuery.getInfoFilterOptional(KeywordsInfoFilter.class);

		if (keywordsInfoFilterOptional.isPresent()) {
			KeywordsInfoFilter keywordsInfoFilter =
				keywordsInfoFilterOptional.get();

			searchContext.setKeywords(keywordsInfoFilter.getKeywords());
		}

		searchContext.setStart(pagination.getStart());

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		return searchContext;
	}

	private List<AssetVocabulary> _getAssetVocabularies(
		ServiceContext serviceContext) {

		try {
			return ListUtil.filter(
				_assetVocabularyLocalService.getGroupVocabularies(
					SiteConnectedGroupGroupProviderUtil.
						getCurrentAndAncestorSiteAndDepotGroupIds(
							serviceContext.getScopeGroupId())),
				assetVocabulary ->
					assetVocabulary.isAssociatedToClassNameIdAndClassTypePK(
						PortalUtil.getClassNameId(
							_objectDefinition.getClassName()),
						AssetCategoryConstants.ALL_CLASS_TYPE_PK));
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return Collections.emptyList();
		}
	}

	private BooleanClause[] _getBooleanClauses(CollectionQuery collectionQuery)
		throws Exception {

		BooleanQuery booleanQuery = new BooleanQueryImpl();

		List<ObjectField> objectFields =
			_objectFieldLocalService.getObjectFields(
				_objectDefinition.getObjectDefinitionId());

		Optional<Map<String, String[]>> configurationOptional =
			collectionQuery.getConfigurationOptional();

		Map<String, String[]> configuration = configurationOptional.orElse(
			Collections.emptyMap());

		for (Map.Entry<String, String[]> entry : configuration.entrySet()) {
			String[] values = entry.getValue();

			if ((values == null) || (values.length == 0) ||
				values[0].isEmpty()) {

				continue;
			}

			ObjectField objectField = _getObjectField(
				entry.getKey(), objectFields);

			if (objectField == null) {
				continue;
			}

			BooleanQuery nestedBooleanQuery = new BooleanQueryImpl();

			nestedBooleanQuery.add(
				new TermQueryImpl(
					_getFieldName(objectField), entry.getValue()[0]),
				BooleanClauseOccur.MUST);
			nestedBooleanQuery.add(
				new TermQueryImpl("nestedFieldArray.fieldName", entry.getKey()),
				BooleanClauseOccur.MUST);

			booleanQuery.add(
				new NestedQuery("nestedFieldArray", nestedBooleanQuery),
				BooleanClauseOccur.MUST);
		}

		return new BooleanClause[] {
			BooleanClauseFactoryUtil.create(
				booleanQuery, BooleanClauseOccur.MUST.getName())
		};
	}

	private InfoPage<ObjectEntry> _getCollectionInfoPageByDefaultStorageType(
			CollectionQuery collectionQuery)
		throws Exception {

		Indexer<ObjectEntry> indexer = IndexerRegistryUtil.getIndexer(
			_objectDefinition.getClassName());

		Hits hits = indexer.search(_buildSearchContext(collectionQuery));

		return InfoPage.of(
			TransformUtil.transformToList(
				hits.getDocs(),
				document -> {
					long classPK = GetterUtil.getLong(
						document.get(Field.ENTRY_CLASS_PK));

					return _objectEntryLocalService.fetchObjectEntry(classPK);
				}),
			collectionQuery.getPagination(), hits.getLength());
	}

	private InfoPage<ObjectEntry> _getCollectionInfoPageByObjectEntryManager(
			CollectionQuery collectionQuery)
		throws Exception {

		ObjectEntryManager objectEntryManager =
			_objectEntryManagerTracker.getObjectEntryManager(
				_objectDefinition.getStorageType());

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		Group scopeGroup = themeDisplay.getScopeGroup();

		Pagination pagination = collectionQuery.getPagination();

		Page<com.liferay.object.rest.dto.v1_0.ObjectEntry> objectEntriesPage =
			objectEntryManager.getObjectEntries(
				themeDisplay.getCompanyId(), _objectDefinition,
				scopeGroup.getGroupKey(), null,
				new DefaultDTOConverterContext(
					false, null, null, null, null, themeDisplay.getLocale(),
					null, themeDisplay.getUser()),
				(Filter)null,
				com.liferay.portal.vulcan.pagination.Pagination.of(
					1, pagination.getEnd()),
				null, null);

		List<com.liferay.object.rest.dto.v1_0.ObjectEntry> objectEntries =
			new ArrayList<>(objectEntriesPage.getItems());

		return InfoPage.of(
			TransformUtil.transform(
				objectEntries,
				objectEntry -> _toObjectEntry(
					_objectDefinition.getObjectDefinitionId(), objectEntry)),
			collectionQuery.getPagination(), objectEntries.size());
	}

	private String _getFieldName(ObjectField objectField) {
		if (Objects.equals(
				objectField.getDBType(),
				ObjectFieldConstants.DB_TYPE_BOOLEAN)) {

			return "nestedFieldArray.value_boolean";
		}
		else if (Objects.equals(
					objectField.getDBType(),
					ObjectFieldConstants.DB_TYPE_STRING)) {

			return "nestedFieldArray.value_keyword_lowercase";
		}

		return "";
	}

	private long _getGroupId() throws Exception {
		ObjectScopeProvider objectScopeProvider =
			_objectScopeProviderRegistry.getObjectScopeProvider(
				_objectDefinition.getScope());

		if (!objectScopeProvider.isGroupAware()) {
			return 0;
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		return objectScopeProvider.getGroupId(serviceContext.getRequest());
	}

	private InfoField<?> _getInfoField() {
		if (!StringUtil.equals(
				_objectDefinition.getStorageType(),
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT) ||
			!_hasCategorizationObjectLayoutBox()) {

			return null;
		}

		long groupId = 0;

		if (StringUtil.equals(
				_objectDefinition.getScope(),
				ObjectDefinitionConstants.SCOPE_COMPANY)) {

			try {
				Group group = _groupLocalService.getCompanyGroup(
					_objectDefinition.getCompanyId());

				groupId = group.getGroupId();
			}
			catch (PortalException portalException) {
				_log.error(portalException);
			}
		}
		else {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			groupId = serviceContext.getScopeGroupId();
		}

		List<AssetTag> assetTags = new ArrayList<>(
			_assetTagLocalService.getGroupTags(groupId));

		assetTags.sort(new AssetTagNameComparator(true));

		List<SelectInfoFieldType.Option> options = new ArrayList<>();

		for (AssetTag assetTag : assetTags) {
			options.add(
				new SelectInfoFieldType.Option(
					new SingleValueInfoLocalizedValue<>(assetTag.getName()),
					assetTag.getName()));
		}

		InfoField.FinalStep<?> finalStep = InfoField.builder(
		).infoFieldType(
			SelectInfoFieldType.INSTANCE
		).namespace(
			StringPool.BLANK
		).name(
			Field.ASSET_TAG_NAMES
		).attribute(
			SelectInfoFieldType.MULTIPLE, true
		).attribute(
			SelectInfoFieldType.OPTIONS, options
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "tag")
		).localizable(
			true
		);

		return finalStep.build();
	}

	private List<InfoFieldSetEntry> _getInfoFieldSetEntries() {
		if (!StringUtil.equals(
				_objectDefinition.getStorageType(),
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT) ||
			!_hasCategorizationObjectLayoutBox()) {

			return Collections.emptyList();
		}

		List<InfoFieldSetEntry> fieldSetEntries = new ArrayList<>();

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		for (AssetVocabulary assetVocabulary :
				_getAssetVocabularies(serviceContext)) {

			List<SelectInfoFieldType.Option> options = new ArrayList<>();

			for (AssetCategory assetCategory :
					_assetCategoryLocalService.getVocabularyCategories(
						assetVocabulary.getVocabularyId(), QueryUtil.ALL_POS,
						QueryUtil.ALL_POS, null)) {

				options.add(
					new SelectInfoFieldType.Option(
						new SingleValueInfoLocalizedValue<>(
							assetCategory.getName()),
						String.valueOf(assetCategory.getCategoryId())));
			}

			if (!options.isEmpty()) {
				fieldSetEntries.add(
					InfoField.builder(
					).infoFieldType(
						SelectInfoFieldType.INSTANCE
					).namespace(
						StringPool.BLANK
					).name(
						String.valueOf(assetVocabulary.getVocabularyId())
					).attribute(
						SelectInfoFieldType.MULTIPLE, true
					).attribute(
						SelectInfoFieldType.OPTIONS, options
					).labelInfoLocalizedValue(
						InfoLocalizedValue.singleValue(
							assetVocabulary.getTitle(
								serviceContext.getLocale()))
					).localizable(
						true
					).build());
			}
		}

		return fieldSetEntries;
	}

	private ObjectField _getObjectField(
		String name, List<ObjectField> objectFields) {

		for (ObjectField objectField : objectFields) {
			if (Objects.equals(name, objectField.getName())) {
				return objectField;
			}
		}

		return null;
	}

	private List<SelectInfoFieldType.Option> _getOptions(
		ObjectField objectField) {

		List<SelectInfoFieldType.Option> options = new ArrayList<>();

		options.add(
			new SelectInfoFieldType.Option(
				new ResourceBundleInfoLocalizedValue(
					getClass(), "choose-an-option"),
				""));

		if (Objects.equals(
				objectField.getDBType(),
				ObjectFieldConstants.DB_TYPE_BOOLEAN)) {

			options.add(
				new SelectInfoFieldType.Option(
					new ResourceBundleInfoLocalizedValue(getClass(), "true"),
					"true"));
			options.add(
				new SelectInfoFieldType.Option(
					new ResourceBundleInfoLocalizedValue(getClass(), "false"),
					"false"));
		}
		else if (objectField.getListTypeDefinitionId() != 0) {
			options.addAll(
				TransformUtil.transform(
					_listTypeEntryLocalService.getListTypeEntries(
						objectField.getListTypeDefinitionId(),
						QueryUtil.ALL_POS, QueryUtil.ALL_POS),
					listTypeEntry -> new SelectInfoFieldType.Option(
						new FunctionInfoLocalizedValue<>(
							listTypeEntry::getName),
						listTypeEntry.getKey())));
		}

		return options;
	}

	private boolean _hasCategorizationObjectLayoutBox() {
		ObjectLayout objectLayout = null;

		try {
			objectLayout = _objectLayoutLocalService.getDefaultObjectLayout(
				_objectDefinition.getObjectDefinitionId());
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			return false;
		}

		for (ObjectLayoutTab objectLayoutTab :
				objectLayout.getObjectLayoutTabs()) {

			if (ListUtil.exists(
					objectLayoutTab.getObjectLayoutBoxes(),
					objectLayoutBox -> StringUtil.equals(
						objectLayoutBox.getType(),
						ObjectLayoutBoxConstants.TYPE_CATEGORIZATION))) {

				return true;
			}
		}

		return false;
	}

	private ObjectEntry _toObjectEntry(
		long objectDefinitionId,
		com.liferay.object.rest.dto.v1_0.ObjectEntry objectEntry) {

		ObjectEntry serviceBuilderObjectEntry =
			_objectEntryLocalService.createObjectEntry(0L);

		serviceBuilderObjectEntry.setExternalReferenceCode(
			objectEntry.getExternalReferenceCode());
		serviceBuilderObjectEntry.setObjectDefinitionId(objectDefinitionId);

		return serviceBuilderObjectEntry;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntrySingleFormVariationInfoCollectionProvider.class);

	private final AssetCategoryLocalService _assetCategoryLocalService;
	private final AssetTagLocalService _assetTagLocalService;
	private final AssetVocabularyLocalService _assetVocabularyLocalService;
	private final GroupLocalService _groupLocalService;
	private final ListTypeEntryLocalService _listTypeEntryLocalService;
	private final ObjectDefinition _objectDefinition;
	private final ObjectEntryLocalService _objectEntryLocalService;
	private final ObjectEntryManagerTracker _objectEntryManagerTracker;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final ObjectLayoutLocalService _objectLayoutLocalService;
	private final ObjectScopeProviderRegistry _objectScopeProviderRegistry;

}