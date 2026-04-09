/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.internal.manager.v1_0;

import com.liferay.account.model.AccountEntry;
import com.liferay.ai.hub.rest.dto.v1_0.ContentRetriever;
import com.liferay.ai.hub.rest.manager.v1_0.ContentRetrieverManager;
import com.liferay.ai.hub.util.AccountEntryUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.DefaultObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.index.CreateIndexRequest;
import com.liferay.portal.search.engine.adapter.index.DeleteIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexResponse;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.text.DateFormat;

import java.time.Instant;

import java.util.Date;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author João Victor Alves
 */
@Component(service = ContentRetrieverManager.class)
public class ContentRetrieverManagerImpl implements ContentRetrieverManager {

	@Override
	public void deleteContentRetriever(
			long companyId, DTOConverterContext dtoConverterContext,
			String externalReferenceCode)
		throws Exception {

		ObjectEntry objectEntry = _objectEntryManager.getObjectEntry(
			companyId, dtoConverterContext, externalReferenceCode,
			_getObjectDefinition(companyId), null);

		_deleteIndex(
			GetterUtil.getString(objectEntry.getPropertyValue("indexName")));

		_objectEntryManager.deleteObjectEntry(
			companyId, dtoConverterContext, externalReferenceCode,
			_getObjectDefinition(companyId), null);
	}

	@Override
	public ContentRetriever putContentRetriever(
			long companyId, ContentRetriever contentRetriever,
			DTOConverterContext dtoConverterContext,
			String externalReferenceCode)
		throws Exception {

		AccountEntry accountEntry = AccountEntryUtil.getUserAccountEntry(
			dtoConverterContext.getUserId());

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				companyId, "AIHubContentRetriever");

		DefaultObjectEntryManager defaultObjectEntryManager =
			(DefaultObjectEntryManager)_objectEntryManager;

		ObjectEntry objectEntry = defaultObjectEntryManager.fetchObjectEntry(
			dtoConverterContext, externalReferenceCode, objectDefinition, null);

		if (objectEntry != null) {
			return _toContentRetriever(
				_updateObjectEntry(
					accountEntry.getAccountEntryId(), companyId,
					contentRetriever,
					String.valueOf(objectEntry.getPropertyValue("crawlDate")),
					dtoConverterContext, externalReferenceCode,
					String.valueOf(objectEntry.getPropertyValue("indexName")),
					objectDefinition));
		}

		String indexName = StringBundler.concat(
			_indexNameBuilder.getIndexName(companyId), "-ai-hub-",
			accountEntry.getAccountEntryId(), "-crawl-results-",
			PortalUUIDUtil.generate());

		_createIndex(indexName);

		return _toContentRetriever(
			_updateObjectEntry(
				accountEntry.getAccountEntryId(), companyId, contentRetriever,
				null, dtoConverterContext, externalReferenceCode, indexName,
				objectDefinition));
	}

	private void _createIndex(String indexName) throws Exception {
		if (_hasIndex(indexName)) {
			return;
		}

		CreateIndexRequest createIndexRequest = new CreateIndexRequest(
			indexName);

		createIndexRequest.setMappings(
			_readJSON("ContentRetrieverType-mappings.json"));
		createIndexRequest.setSettings(_readJSON("settings.json"));

		_searchEngineAdapter.execute(createIndexRequest);
	}

	private void _deleteIndex(String indexName) {
		if (!_hasIndex(indexName)) {
			return;
		}

		_searchEngineAdapter.execute(new DeleteIndexRequest(indexName));
	}

	private ObjectDefinition _getObjectDefinition(long companyId)
		throws Exception {

		return _objectDefinitionLocalService.getObjectDefinition(
			companyId, "AIHubContentRetriever");
	}

	private boolean _hasIndex(String indexName) {
		IndicesExistsIndexRequest indicesExistsIndexRequest =
			new IndicesExistsIndexRequest(indexName);

		IndicesExistsIndexResponse indicesExistsIndexResponse =
			_searchEngineAdapter.execute(indicesExistsIndexRequest);

		return indicesExistsIndexResponse.isExists();
	}

	private String _readJSON(String fileName) throws Exception {
		JSONObject jsonObject = _jsonFactory.createJSONObject(
			StringUtil.read(getClass(), "/META-INF/search/" + fileName));

		return jsonObject.toString();
	}

	private ContentRetriever _toContentRetriever(ObjectEntry objectEntry) {
		return new ContentRetriever() {
			{
				setCrawlDate(
					() -> Date.from(
						Instant.parse(
							GetterUtil.getString(
								objectEntry.getPropertyValue("crawlDate")))));
				setDescription(
					() -> GetterUtil.getString(
						objectEntry.getPropertyValue("description")));
				setDescription_i18n(
					() -> (Map<String, String>)objectEntry.getPropertyValue(
						"description_i18n"));
				setExternalReferenceCode(objectEntry::getExternalReferenceCode);
				setIndexName(
					() -> GetterUtil.getString(
						objectEntry.getPropertyValue("indexName")));
				setTitle(
					() -> GetterUtil.getString(
						objectEntry.getPropertyValue("title")));
				setTitle_i18n(
					() -> (Map<String, String>)objectEntry.getPropertyValue(
						"title_i18n"));
				setType(
					() -> GetterUtil.getString(
						objectEntry.getPropertyValue("type")));
				setUrl(
					() -> GetterUtil.getString(
						objectEntry.getPropertyValue("url")));
			}
		};
	}

	private ObjectEntry _updateObjectEntry(
			long accountEntryId, long companyId,
			ContentRetriever contentRetriever, String crawlDate,
			DTOConverterContext dtoConverterContext,
			String externalReferenceCode, String indexName,
			ObjectDefinition objectDefinition)
		throws Exception {

		return _objectEntryManager.updateObjectEntry(
			companyId, dtoConverterContext, externalReferenceCode,
			objectDefinition,
			new ObjectEntry() {
				{
					setExternalReferenceCode(
						contentRetriever::getExternalReferenceCode);
					setProperties(
						() -> HashMapBuilder.<String, Object>put(
							"crawlDate",
							GetterUtil.getDate(
								crawlDate, DateFormat.getDateInstance())
						).put(
							"description_i18n",
							contentRetriever.getDescription_i18n()
						).put(
							"indexName", indexName
						).put(
							"r_accountToAIHubContentRetrievers_accountEntryId",
							String.valueOf(accountEntryId)
						).put(
							"title_i18n", contentRetriever.getTitle_i18n()
						).put(
							"type", contentRetriever.getType()
						).put(
							"url", contentRetriever.getUrl()
						).build());
				}
			},
			null);
	}

	@Reference
	private IndexNameBuilder _indexNameBuilder;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference(target = "(object.entry.manager.storage.type=default)")
	private ObjectEntryManager _objectEntryManager;

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

}