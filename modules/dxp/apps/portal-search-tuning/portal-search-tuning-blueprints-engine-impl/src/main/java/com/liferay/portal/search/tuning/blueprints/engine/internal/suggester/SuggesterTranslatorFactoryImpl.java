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

package com.liferay.portal.search.tuning.blueprints.engine.internal.suggester;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.tuning.blueprints.engine.spi.suggester.SuggesterTranslator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = SuggesterTranslatorFactory.class)
public class SuggesterTranslatorFactoryImpl
	implements SuggesterTranslatorFactory {

	@Override
	public SuggesterTranslator getTranslator(String type)
		throws IllegalArgumentException {

		SuggesterTranslator suggesterTranslator = _suggesterTranslators.get(
			type);

		if (suggesterTranslator == null) {
			throw new IllegalArgumentException(
				"Unable to find suggester translator for " + type);
		}

		return suggesterTranslator;
	}

	@Override
	public String[] getTranslatorTypes() {
		Set<String> set = _suggesterTranslators.keySet();

		return set.toArray(new String[0]);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void registerSuggesterBuilder(
		SuggesterTranslator suggesterTranslator,
		Map<String, Object> properties) {

		String type = (String)properties.get("type");

		if (Validator.isBlank(type)) {
			Class<?> clazz = suggesterTranslator.getClass();

			StringBundler sb = new StringBundler(3);

			sb.append("Unable to register suggester translator ");
			sb.append(clazz.getName());
			sb.append(". Type property empty.");

			_log.error(sb.toString());

			return;
		}

		_suggesterTranslators.put(type, suggesterTranslator);
	}

	protected void unregisterSuggesterBuilder(
		SuggesterTranslator suggesterTranslator,
		Map<String, Object> properties) {

		String type = (String)properties.get("type");

		if (Validator.isBlank(type)) {
			return;
		}

		_suggesterTranslators.remove(type);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SuggesterTranslatorFactoryImpl.class);

	private volatile Map<String, SuggesterTranslator> _suggesterTranslators =
		new HashMap<>();

}