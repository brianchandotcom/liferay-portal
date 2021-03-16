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

package com.liferay.portal.search.tuning.blueprints.engine.internal.suggester.translator;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.suggest.Suggester;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ParameterData;
import com.liferay.portal.search.tuning.blueprints.engine.spi.suggester.SuggesterTranslator;
import com.liferay.portal.search.tuning.blueprints.message.Message;
import com.liferay.portal.search.tuning.blueprints.message.Messages;
import com.liferay.portal.search.tuning.blueprints.message.Severity;

import java.util.Optional;

/**
 * @author Petteri Karttunen
 */
public abstract class BaseSuggesterTranslator implements SuggesterTranslator {

	@Override
	public abstract Optional<Suggester> translate(
		String suggesterName, JSONObject configurationJSONObject,
		ParameterData parameterData, Messages messages);

	protected Suggester.SuggestMode getSuggestMode(String s)
		throws IllegalArgumentException {

		return Suggester.SuggestMode.valueOf(StringUtil.toUpperCase(s));
	}

	protected boolean validateSuggesterConfiguration(
		Messages messages, JSONObject configurationJSONObject,
		String suggesterName) {

		if (_validateField(messages, configurationJSONObject)) {
			return true;
		}

		return false;
	}

	private boolean _validateField(
		Messages messages, JSONObject configurationJSONObject) {

		if (configurationJSONObject.isNull("field")) {
			messages.addMessage(
				new Message.Builder().className(
					getClass().getName()
				).localizationKey(
					"core.error.undefined-suggester-field"
				).msg(
					"Suggester field is not defined"
				).rootObject(
					configurationJSONObject
				).rootProperty(
					"field"
				).severity(
					Severity.ERROR
				).build());

			if (_log.isWarnEnabled()) {
				StringBundler sb = new StringBundler(3);

				sb.append("Suggester field is not defined [ ");
				sb.append(configurationJSONObject);
				sb.append(" ]");

				_log.warn(sb.toString());
			}

			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseSuggesterTranslator.class);

}