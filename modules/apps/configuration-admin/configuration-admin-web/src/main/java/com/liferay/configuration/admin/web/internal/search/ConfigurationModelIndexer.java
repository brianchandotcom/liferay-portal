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

package com.liferay.configuration.admin.web.internal.search;

import com.liferay.configuration.admin.category.ConfigurationCategory;
import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.configuration.admin.web.internal.model.ConfigurationModel;
import com.liferay.configuration.admin.web.internal.util.ConfigurationEntryRetriever;
import com.liferay.configuration.admin.web.internal.util.ConfigurationModelRetriever;
import com.liferay.configuration.admin.web.internal.util.ResourceBundleLoaderProvider;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.index.IndexStatusManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.AttributeDefinition;
import org.osgi.service.metatype.ObjectClassDefinition;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true, property = "index.on.startup=false",
	service = {ConfigurationModelIndexer.class, Indexer.class}
)
public class ConfigurationModelIndexer extends BaseIndexer<ConfigurationModel> {

	@Override
	public String getClassName() {
		return ConfigurationModel.class.getName();
	}

	@Override
	public BooleanQuery getFullQuery(SearchContext searchContext)
		throws SearchException {

		try {
			BooleanFilter fullQueryBooleanFilter = new BooleanFilter();

			fullQueryBooleanFilter.addRequiredTerm(
				Field.ENTRY_CLASS_NAME, getClassName());

			BooleanQuery fullQuery = createFullQuery(
				fullQueryBooleanFilter, searchContext);

			fullQuery.setQueryConfig(searchContext.getQueryConfig());

			return fullQuery;
		}
		catch (SearchException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	@Override
	public void reindex(Collection<ConfigurationModel> configurationModels) {
		if (_indexStatusManager.isIndexReadOnly() ||
			_indexStatusManager.isIndexReadOnly(getClassName()) ||
			!isIndexerEnabled() || configurationModels.isEmpty()) {

			return;
		}

		List<Document> documents = new ArrayList<>();

		try {
			for (ConfigurationModel configurationModel : configurationModels) {
				if (configurationModel == null) {
					return;
				}

				documents.add(getDocument(configurationModel));
			}

			_indexWriterHelper.updateDocuments(
				getSearchEngineId(), CompanyConstants.SYSTEM, documents, false);
		}
		catch (SearchException se) {
			_log.error(
				"Unable to index documents for " + configurationModels, se);
		}
	}

	@Override
	public Hits search(SearchContext searchContext) throws SearchException {
		try {
			Hits hits = doSearch(searchContext);

			processHits(searchContext, hits);

			return hits;
		}
		catch (SearchException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		setCommitImmediately(false);
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.DESCRIPTION, Field.ENTRY_CLASS_NAME,
			Field.TITLE, Field.UID,
			FieldNames.CONFIGURATION_MODEL_ATTRIBUTE_DESCRIPTION,
			FieldNames.CONFIGURATION_MODEL_ATTRIBUTE_NAME,
			FieldNames.CONFIGURATION_MODEL_FACTORY_PID,
			FieldNames.CONFIGURATION_MODEL_ID);
		setFilterSearch(false);
		setPermissionAware(false);
		setSelectAllLocales(false);
		setStagingAware(false);
	}

	@Override
	protected BooleanQuery createFullQuery(
			BooleanFilter fullQueryBooleanFilter, SearchContext searchContext)
		throws Exception {

		BooleanQuery searchQuery = new BooleanQueryImpl();

		addSearchLocalizedTerm(
			searchQuery, searchContext, FieldNames.CONFIGURATION_CATEGORY,
			false);
		addSearchLocalizedTerm(
			searchQuery, searchContext, Field.DESCRIPTION, false);
		addSearchLocalizedTerm(searchQuery, searchContext, Field.TITLE, false);
		addSearchLocalizedTerm(
			searchQuery, searchContext,
			FieldNames.CONFIGURATION_MODEL_ATTRIBUTE_DESCRIPTION, false);
		addSearchLocalizedTerm(
			searchQuery, searchContext,
			FieldNames.CONFIGURATION_MODEL_ATTRIBUTE_NAME, false);
		addSearchTerm(
			searchQuery, searchContext,
			FieldNames.CONFIGURATION_MODEL_FACTORY_PID, false);
		addSearchTerm(
			searchQuery, searchContext, FieldNames.CONFIGURATION_MODEL_ID,
			false);

		BooleanQuery fullBooleanQuery = new BooleanQueryImpl();

		if (fullQueryBooleanFilter.hasClauses()) {
			fullBooleanQuery.setPreBooleanFilter(fullQueryBooleanFilter);
		}

		fullBooleanQuery.add(searchQuery, BooleanClauseOccur.MUST);

		return fullBooleanQuery;
	}

	@Override
	protected void doDelete(ConfigurationModel configurationModel)
		throws Exception {

		Document document = newDocument();

		document.addUID(
			ConfigurationAdminPortletKeys.SYSTEM_SETTINGS,
			configurationModel.getFactoryPid());

		_indexWriterHelper.deleteDocument(
			getSearchEngineId(), CompanyConstants.SYSTEM,
			document.get(Field.UID), isCommitImmediately());
	}

	@Override
	protected Document doGetDocument(ConfigurationModel configurationModel)
		throws Exception {

		Document document = newDocument();

		document.addUID(
			ConfigurationAdminPortletKeys.SYSTEM_SETTINGS,
			configurationModel.getFactoryPid());
		document.addKeyword(
			FieldNames.CONFIGURATION_MODEL_FACTORY_PID,
			configurationModel.getFactoryPid());
		document.addKeyword(
			FieldNames.CONFIGURATION_MODEL_ID, configurationModel.getID());
		document.addKeyword(Field.COMPANY_ID, CompanyConstants.SYSTEM);

		document.addKeyword(Field.ENTRY_CLASS_NAME, getClassName());

		AttributeDefinition[] requiredAttributeDefinitions =
			configurationModel.getAttributeDefinitions(
				ObjectClassDefinition.ALL);

		List<String> attributeNames = new ArrayList<>(
			requiredAttributeDefinitions.length);

		List<String> attributeDescriptions = new ArrayList<>(
			requiredAttributeDefinitions.length);

		for (AttributeDefinition attributeDefinition :
				requiredAttributeDefinitions) {

			attributeNames.add(attributeDefinition.getName());
			attributeDescriptions.add(attributeDefinition.getDescription());
		}

		ResourceBundleLoader resourceBundleLoader =
			_resourceBundleLoaderProvider.getResourceBundleLoader(
				configurationModel.getBundleSymbolicName());

		for (Locale locale : LanguageUtil.getAvailableLocales()) {
			String fieldNameSuffix = StringBundler.concat(
				StringPool.UNDERLINE, locale.getLanguage(),
				StringPool.UNDERLINE, locale.getCountry());

			List<String> descriptionValues = _getLocalizedValues(
				attributeDescriptions, resourceBundleLoader, locale);

			document.addText(
				FieldNames.CONFIGURATION_MODEL_ATTRIBUTE_DESCRIPTION +
					fieldNameSuffix,
				descriptionValues.toArray(new String[0]));

			List<String> nameValues = _getLocalizedValues(
				attributeNames, resourceBundleLoader, locale);

			document.addKeyword(
				FieldNames.CONFIGURATION_MODEL_ATTRIBUTE_NAME + fieldNameSuffix,
				nameValues.toArray(new String[0]));
		}

		List<TranslationHelper> translationHelpers = new ArrayList<>(3);

		ConfigurationCategory configurationCategory =
			_configurationEntryRetriever.getConfigurationCategory(
				configurationModel.getCategory());

		if (configurationCategory != null) {
			translationHelpers.add(
				new TranslationHelper(
					"category." + configurationModel.getCategory(),
					FieldNames.CONFIGURATION_CATEGORY));
		}

		translationHelpers.add(
			new TranslationHelper(
				configurationModel.getDescription(), Field.DESCRIPTION));
		translationHelpers.add(
			new TranslationHelper(configurationModel.getName(), Field.TITLE));

		_addLocalizedText(document, resourceBundleLoader, translationHelpers);

		return document;
	}

	@Override
	protected Summary doGetSummary(
			Document document, Locale locale, String snippet,
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception {

		Summary summary = createSummary(
			document, Field.TITLE, Field.DESCRIPTION);

		summary.setMaxContentLength(200);

		return summary;
	}

	@Override
	protected void doReindex(ConfigurationModel configurationModel)
		throws Exception {

		_indexWriterHelper.updateDocument(
			getSearchEngineId(), CompanyConstants.SYSTEM,
			getDocument(configurationModel), isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		Map<String, ConfigurationModel> configurationModels =
			_configurationModelRetriever.getConfigurationModels(
				ExtendedObjectClassDefinition.Scope.SYSTEM, null);

		for (ConfigurationModel configurationModel :
				configurationModels.values()) {

			doReindex(configurationModel);
		}
	}

	private void _addLocalizedText(
		Document document, ResourceBundleLoader resourceBundleLoader,
		List<TranslationHelper> translationHelpers) {

		for (Locale locale : LanguageUtil.getAvailableLocales()) {
			for (TranslationHelper translationHelper : translationHelpers) {
				ResourceBundle resourceBundle = _getResourceBundle(
					locale, resourceBundleLoader);

				if (resourceBundle != null) {
					translationHelper.accept(resourceBundle, locale);
				}
			}
		}

		for (TranslationHelper translationHelper : translationHelpers) {
			document.addLocalizedText(
				translationHelper._name, translationHelper._values);
		}
	}

	private List<String> _getLocalizedValues(
		List<String> attributeDescriptions,
		ResourceBundleLoader resourceBundleLoader, Locale locale) {

		List<String> values = new ArrayList<>(attributeDescriptions.size());

		for (String attributeDescription : attributeDescriptions) {
			if (Validator.isNull(attributeDescription)) {
				continue;
			}

			ResourceBundle resourceBundle = _getResourceBundle(
				locale, resourceBundleLoader);

			if (resourceBundle == null) {
				continue;
			}

			String value = ResourceBundleUtil.getString(
				resourceBundle, attributeDescription);

			if (Validator.isNull(value)) {
				continue;
			}

			values.add(value);
		}

		return values;
	}

	private ResourceBundle _getResourceBundle(
		Locale locale, ResourceBundleLoader resourceBundleLoader) {

		ResourceBundle resourceBundle = resourceBundleLoader.loadResourceBundle(
			locale);

		if (resourceBundle != null) {
			return resourceBundle;
		}

		return resourceBundleLoader.loadResourceBundle(LocaleUtil.getDefault());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ConfigurationModelIndexer.class);

	@Reference
	private ConfigurationEntryRetriever _configurationEntryRetriever;

	@Reference
	private ConfigurationModelRetriever _configurationModelRetriever;

	@Reference
	private IndexStatusManager _indexStatusManager;

	@Reference
	private IndexWriterHelper _indexWriterHelper;

	@Reference
	private ResourceBundleLoaderProvider _resourceBundleLoaderProvider;

	private static class TranslationHelper {

		public void accept(ResourceBundle resourceBundle, Locale locale) {
			String value = ResourceBundleUtil.getString(resourceBundle, _key);

			if (Validator.isNotNull(value)) {
				_values.put(locale, value);
			}
		}

		private TranslationHelper(String key, String name) {
			_key = GetterUtil.getString(key);
			_name = name;
		}

		private final String _key;
		private final String _name;
		private final Map<Locale, String> _values = new HashMap<>();

	}

}