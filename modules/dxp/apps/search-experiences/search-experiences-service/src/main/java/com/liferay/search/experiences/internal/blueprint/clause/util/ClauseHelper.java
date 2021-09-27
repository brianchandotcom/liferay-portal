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

package com.liferay.search.experiences.internal.blueprint.clause.util;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.search.query.Query;
import com.liferay.search.experiences.internal.blueprint.clause.ClauseTranslator;
import com.liferay.search.experiences.internal.blueprint.clause.ClauseTranslatorFactory;
import com.liferay.search.experiences.internal.blueprint.parameter.SXPParameterData;
import com.liferay.search.experiences.internal.blueprint.parameter.SXPParameterParser;

import java.util.Iterator;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = ClauseHelper.class)
public class ClauseHelper {

	public Optional<Query> getQueryOptional(
		JSONObject jsonObject, SXPParameterData sxpParameterData) {

		if (jsonObject == null) {
			return Optional.empty();
		}

		try {
			ClauseTranslator clauseTranslator =
				_clauseTranslatorFactory.getTranslator(_getType(jsonObject));

			JSONObject parsedJSONObject = SXPParameterParser.parse(
				jsonObject, sxpParameterData);

			if (parsedJSONObject == null) {
				return Optional.empty();
			}

			return clauseTranslator.translate(
				parsedJSONObject, sxpParameterData);
		}
		catch (IllegalArgumentException illegalArgumentException) {
			_log.error(illegalArgumentException);
		}

		return Optional.empty();
	}

	private String _getType(JSONObject jsonObject) {
		Iterator<String> iterator = jsonObject.keys();

		if (!iterator.hasNext()) {
			return null;
		}

		return iterator.next();
	}

	private static final Log _log = LogFactoryUtil.getLog(ClauseHelper.class);

	@Reference
	private ClauseTranslatorFactory _clauseTranslatorFactory;

}