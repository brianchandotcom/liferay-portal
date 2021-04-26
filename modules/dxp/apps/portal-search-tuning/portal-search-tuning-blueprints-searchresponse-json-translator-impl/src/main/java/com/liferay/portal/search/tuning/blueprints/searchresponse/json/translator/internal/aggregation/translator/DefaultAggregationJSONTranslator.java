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

package com.liferay.portal.search.tuning.blueprints.searchresponse.json.translator.internal.aggregation.translator;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.tuning.blueprints.searchresponse.json.translator.spi.aggregation.AggregationJSONTranslator;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=default",
	service = AggregationJSONTranslator.class
)
public class DefaultAggregationJSONTranslator
	implements AggregationJSONTranslator {

	@Override
	public Optional<JSONObject> translate(AggregationResult aggregationResult) {
		try {
			String json = _jsonFactory.looseSerializeDeep(aggregationResult);

			return Optional.of(_jsonFactory.createJSONObject(json));
		}
		catch (JSONException jsonException) {
			_log.error(jsonException.getMessage(), jsonException);
		}

		return Optional.empty();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultAggregationJSONTranslator.class);

	@Reference
	private JSONFactory _jsonFactory;

}