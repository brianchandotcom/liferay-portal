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
import com.liferay.info.collection.provider.SingleFormVariationInfoCollectionProvider;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.type.SelectInfoFieldType;
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
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author Jorge Ferrer
 */
public class ObjectEntrySingleFormVariationInfoCollectionProvider
	implements ConfigurableInfoCollectionProvider<ObjectEntry>,
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
		return _objectDefinition.getName();
	}

	private SearchContext _buildSearchContext(CollectionQuery collectionQuery)
		throws PortalException {

		SearchContext searchContext = new SearchContext();

		searchContext.setAndSearch(true);
		searchContext.setAttribute(
			Field.STATUS, WorkflowConstants.STATUS_APPROVED);
		searchContext.setAttribute(
			"objectDefinitionId", _objectDefinition.getObjectDefinitionId());

		Optional<Map<String, String[]>> configurationOptional =
			collectionQuery.getConfigurationOptional();

		Map<String, String[]> configuration = configurationOptional.orElse(
			Collections.emptyMap());

		for (Map.Entry<String, String[]> entry : configuration.entrySet()) {
			searchContext.setAttribute(entry.getKey(), entry.getValue());
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		searchContext.setCompanyId(serviceContext.getCompanyId());

		Pagination pagination = collectionQuery.getPagination();

		searchContext.setEnd(pagination.getEnd());

		searchContext.setGroupIds(new long[] {_getGroupId()});
		searchContext.setStart(pagination.getStart());

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		return searchContext;
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

	private List<SelectInfoFieldType.Option> _getOptions(
		ObjectField objectField) {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (Objects.equals(objectField.getType(), "Boolean")) {
			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				serviceContext.getLocale(), getClass());

			return ListUtil.fromArray(
				new SelectInfoFieldType.Option(
					LanguageUtil.get(resourceBundle, "true"), "true"),
				new SelectInfoFieldType.Option(
					LanguageUtil.get(resourceBundle, "false"), "false"));
		}
		else if (objectField.getListTypeDefinitionId() != 0) {
			return TransformUtil.transform(
				_listTypeEntryLocalService.getListTypeEntries(
					objectField.getListTypeDefinitionId(), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS),
				listTypeEntry -> new SelectInfoFieldType.Option(
					listTypeEntry.getName(serviceContext.getLocale()),
					listTypeEntry.getKey()));
		}

		return null;
	}

	private final ListTypeEntryLocalService _listTypeEntryLocalService;
	private final ObjectDefinition _objectDefinition;
	private final ObjectEntryLocalService _objectEntryLocalService;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final ObjectScopeProviderRegistry _objectScopeProviderRegistry;

}