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

package com.liferay.portal.search.tuning.blueprints.facets.internal.response.handler;

import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.journal.model.JournalFolder;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.aggregation.bucket.Bucket;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.filter.ComplexQueryPartBuilderFactory;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.tuning.blueprints.attributes.BlueprintsAttributes;
import com.liferay.portal.search.tuning.blueprints.facets.constants.FacetsJSONResponseKeys;
import com.liferay.portal.search.tuning.blueprints.facets.spi.response.FacetResponseHandler;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=folder",
	service = FacetResponseHandler.class
)
public class FolderFacetResponseHandler
	extends BaseTermsFacetResponseHandler implements FacetResponseHandler {

	@Override
	protected JSONObject createBucketJSONObject(
			Bucket bucket, BlueprintsAttributes blueprintsAttributes,
			ResourceBundle resourceBundle)
		throws Exception {

		Locale locale = blueprintsAttributes.getLocale();

		String value = bucket.getKey();

		long folderId = GetterUtil.getLong(value);

		Document document = _getDocument(folderId, blueprintsAttributes);

		if (document == null) {
			return null;
		}

		long frequency = bucket.getDocCount();

		String name = document.getString(
			"localized_title_" + locale.toString());

		long groupId = document.getLong(Field.GROUP_ID);

		Group group = _groupLocalService.getGroup(groupId);

		return JSONUtil.put(
			FacetsJSONResponseKeys.FREQUENCY, frequency
		).put(
			FacetsJSONResponseKeys.GROUP_NAME, group.getName(locale, true)
		).put(
			FacetsJSONResponseKeys.NAME, name
		).put(
			FacetsJSONResponseKeys.TEXT, getText(name, frequency, null)
		).put(
			FacetsJSONResponseKeys.VALUE, folderId
		);
	}

	private Document _getDocument(
		long folderId, BlueprintsAttributes blueprintsAttributes) {

		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder(
			).addComplexQueryPart(
				_complexQueryPartBuilderFactory.builder(
				).query(
					_queries.term(Field.ENTRY_CLASS_PK, folderId)
				).occur(
					"filter"
				).build()
			).companyId(
				blueprintsAttributes.getCompanyId()
			).modelIndexerClasses(
				BookmarksFolder.class, DLFolder.class, JournalFolder.class
			).emptySearchEnabled(
				true
			).highlightEnabled(
				false
			).locale(
				blueprintsAttributes.getLocale()
			).size(
				1
			).from(
				0
			);

		SearchResponse searchResponse = _searcher.search(
			searchRequestBuilder.build());

		SearchHits searchHits = searchResponse.getSearchHits();

		List<SearchHit> hits = searchHits.getSearchHits();

		if (hits.size() == 1) {
			SearchHit hit = hits.get(0);

			return hit.getDocument();
		}

		return null;
	}

	@Reference
	private ComplexQueryPartBuilderFactory _complexQueryPartBuilderFactory;

	@Reference
	private DLFolderLocalService _dlFolderLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Queries _queries;

	@Reference
	private Searcher _searcher;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

}