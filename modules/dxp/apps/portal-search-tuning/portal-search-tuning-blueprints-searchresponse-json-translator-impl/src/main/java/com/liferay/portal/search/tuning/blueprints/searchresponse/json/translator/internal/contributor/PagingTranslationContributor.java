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

package com.liferay.portal.search.tuning.blueprints.searchresponse.json.translator.internal.contributor;

import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.tuning.blueprints.attributes.BlueprintsAttributes;
import com.liferay.portal.search.tuning.blueprints.message.Messages;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;
import com.liferay.portal.search.tuning.blueprints.searchresponse.json.translator.constants.JSONKeys;
import com.liferay.portal.search.tuning.blueprints.searchresponse.json.translator.spi.contributor.JSONTranslationContributor;
import com.liferay.portal.search.tuning.blueprints.util.BlueprintHelper;

import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=paging",
	service = JSONTranslationContributor.class
)
public class PagingTranslationContributor
	implements JSONTranslationContributor {

	@Override
	public void contribute(
		JSONObject responseJSONObject, SearchResponse searchResponse,
		Blueprint blueprint, BlueprintsAttributes blueprintsAttributes,
		ResourceBundle resourceBundle, Messages messages) {

		responseJSONObject.put(
			JSONKeys.PAGINATION, _getPagingJSONObject(searchResponse));
	}

	private JSONObject _getPagingJSONObject(SearchResponse searchResponse) {
		JSONObject jsonObject = _jsonFactory.createJSONObject();

		SearchHits searchHits = searchResponse.getSearchHits();

		try {
			int totalHits = Math.toIntExact(searchHits.getTotalHits());

			if (totalHits == 0) {
				return jsonObject;
			}

			SearchRequest searchRequest = searchResponse.getRequest();

			int pageSize = searchRequest.getSize();

			int start = _getStart(totalHits, pageSize, searchRequest.getFrom());

			jsonObject.put(
				JSONKeys.ACTIVE_PAGE,
				(int)Math.floor((start + 1) / pageSize) + 1
			).put(
				JSONKeys.PAGE_SIZE, pageSize
			).put(
				JSONKeys.TOTAL_PAGES, (int)Math.ceil(totalHits * 1.0 / pageSize)
			);
		}
		catch (ArithmeticException arithmeticException) {
			_log.error(arithmeticException.getMessage(), arithmeticException);
		}

		return jsonObject;
	}

	private int _getStart(int totalHits, int pageSize, int start)
		throws ArithmeticException {

		if (totalHits < start) {
			int pageCount = (int)Math.ceil(totalHits * 1.0 / pageSize);

			start = (pageCount - 1) * pageSize;

			if (start < 0) {
				start = 0;
			}
		}

		return start;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PagingTranslationContributor.class);

	@Reference
	private BlueprintHelper _blueprintHelper;

	@Reference
	private JSONFactory _jsonFactory;

}