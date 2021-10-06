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

import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.ConfigurableInfoCollectionProvider;
import com.liferay.info.collection.provider.FilteredInfoCollectionProvider;
import com.liferay.info.collection.provider.SingleFormVariationInfoCollectionProvider;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.type.SelectInfoFieldType;
import com.liferay.info.filter.InfoFilter;
import com.liferay.info.filter.KeywordsInfoFilter;
import com.liferay.info.form.InfoForm;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.pagination.InfoPage;
import com.liferay.info.pagination.Pagination;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.scope.ObjectScopeProvider;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.NestedQuery;
import com.liferay.portal.kernel.search.generic.TermQueryImpl;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.util.TransformUtil;

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
		ListTypeEntryLocalService listTypeEntryLocalService,
		ObjectDefinition objectDefinition,
		ObjectEntryLocalService objectEntryLocalService,
		ObjectFieldLocalService objectFieldLocalService,
		ObjectScopeProviderRegistry objectScopeProviderRegistry) {

		_listTypeEntryLocalService = listTypeEntryLocalService;
		_objectDefinition = objectDefinition;
		_objectEntryLocalService = objectEntryLocalService;
		_objectFieldLocalService = objectFieldLocalService;
		_objectScopeProviderRegistry = objectScopeProviderRegistry;
	}

	@Override
	public InfoPage<ObjectEntry> getCollectionInfoPage(
		CollectionQuery collectionQuery) {

		try {
			Indexer<ObjectEntry> indexer = IndexerRegistryUtil.getIndexer(
				_objectDefinition.getClassName());

			Hits hits = indexer.search(_buildSearchContext(collectionQuery));

			return InfoPage.of(
				TransformUtil.transformToList(
					hits.getDocs(),
					document -> {
						long classPK = GetterUtil.getLong(
							document.get(Field.ENTRY_CLASS_PK));

						return _objectEntryLocalService.fetchObjectEntry(
							classPK);
					}),
				collectionQuery.getPagination(), hits.getLength());
		}
		catch (PortalException portalException) {
			throw new RuntimeException(
				"Unable to get object entries for object definition " +
					_objectDefinition.getObjectDefinitionId(),
				portalException);
		}
	}

	@Override
	public String getCollectionItemClassName() {
		return _objectDefinition.getClassName();
	}

	@Override
	public InfoForm getConfigurationInfoForm() {
		return InfoForm.builder(
		).infoFieldSetEntry(
			InfoFieldSet.builder(
			).infoFieldSetEntry(
				unsafeConsumer -> {
					for (ObjectField objectField :
							_objectFieldLocalService.getObjectFields(
								_objectDefinition.getObjectDefinitionId())) {

						if (!(Objects.equals(
								objectField.getType(), "Boolean") ||
							  (Objects.equals(
								  objectField.getType(), "String") &&
							   (objectField.getListTypeDefinitionId() != 0))) ||
							!objectField.isIndexed()) {

							continue;
						}

						unsafeConsumer.accept(
							InfoField.builder(
							).infoFieldType(
								SelectInfoFieldType.INSTANCE
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
		throws PortalException {

		SearchContext searchContext = new SearchContext();

		searchContext.setAttribute(
			Field.STATUS, WorkflowConstants.STATUS_APPROVED);
		searchContext.setAttribute(
			"objectDefinitionId", _objectDefinition.getObjectDefinitionId());
		searchContext.setBooleanClauses(_getBooleanClauses(collectionQuery));

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

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

	private BooleanClause[] _getBooleanClauses(CollectionQuery collectionQuery)
		throws ParseException {

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
				new TermQueryImpl(_getField(objectField), entry.getValue()[0]),
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

	private String _getField(ObjectField objectField) {
		if (Objects.equals(objectField.getType(), "Boolean")) {
			return "nestedFieldArray.value_boolean";
		}
		else if (Objects.equals(objectField.getType(), "String")) {
			return "nestedFieldArray.value_text";
		}

		return "";
	}

	private long _getGroupId() throws PortalException {
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

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		options.add(
			new SelectInfoFieldType.Option(
				LanguageUtil.get(
					serviceContext.getLocale(), "choose-an-option"),
				""));

		if (Objects.equals(objectField.getType(), "Boolean")) {
			options.add(
				new SelectInfoFieldType.Option(
					LanguageUtil.get(serviceContext.getLocale(), "true"),
					"true"));
			options.add(
				new SelectInfoFieldType.Option(
					LanguageUtil.get(serviceContext.getLocale(), "false"),
					"false"));
		}
		else if (objectField.getListTypeDefinitionId() != 0) {
			options.addAll(
				TransformUtil.transform(
					_listTypeEntryLocalService.getListTypeEntries(
						objectField.getListTypeDefinitionId(),
						QueryUtil.ALL_POS, QueryUtil.ALL_POS),
					listTypeEntry -> new SelectInfoFieldType.Option(
						listTypeEntry.getName(serviceContext.getLocale()),
						listTypeEntry.getKey())));
		}

		return options;
	}

	private final ListTypeEntryLocalService _listTypeEntryLocalService;
	private final ObjectDefinition _objectDefinition;
	private final ObjectEntryLocalService _objectEntryLocalService;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final ObjectScopeProviderRegistry _objectScopeProviderRegistry;

}