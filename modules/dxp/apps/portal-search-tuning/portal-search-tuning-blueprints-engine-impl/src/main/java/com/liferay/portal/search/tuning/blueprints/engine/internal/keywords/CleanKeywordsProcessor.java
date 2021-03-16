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

package com.liferay.portal.search.tuning.blueprints.engine.internal.keywords;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.tuning.blueprints.attributes.BlueprintsAttributes;
import com.liferay.portal.search.tuning.blueprints.engine.spi.keywords.KeywordsProcessor;
import com.liferay.portal.search.tuning.blueprints.message.Messages;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;

import java.util.stream.IntStream;

import org.osgi.service.component.annotations.Component;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=clean", service = KeywordsProcessor.class
)
public class CleanKeywordsProcessor implements KeywordsProcessor {

	@Override
	public String process(
		String keywords, Blueprint blueprint,
		BlueprintsAttributes blueprintsAttributes, Messages messages) {

		return _clean(keywords);
	}

	private String _clean(String keywords) {
		keywords = StringUtil.trim(keywords);

		if (Validator.isBlank(keywords)) {
			return keywords;
		}

		// Remove quotes if there's an uneven count of them.

		IntStream intStream = keywords.chars();

		long quoteCount = intStream.filter(
			ch -> ch == '"'
		).count();

		if ((quoteCount % 2) != 0) {
			keywords = keywords.replaceAll("\"", "");
		}

		// Encode slashes.

		keywords = keywords.replaceAll("/", "&#8725;");
		keywords = keywords.replaceAll("\\\\", "&#92;");

		keywords = keywords.replaceAll("\"", "\\\\\"");

		return keywords;
	}

}