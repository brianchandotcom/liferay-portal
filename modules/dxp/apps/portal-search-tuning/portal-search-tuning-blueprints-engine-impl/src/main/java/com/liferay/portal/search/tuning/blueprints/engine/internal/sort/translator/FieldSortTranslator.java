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

package com.liferay.portal.search.tuning.blueprints.engine.internal.sort.translator;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.search.sort.Sort;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.search.tuning.blueprints.constants.json.keys.sort.SortConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.engine.spi.sort.SortTranslator;
import com.liferay.portal.search.tuning.blueprints.message.Messages;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "type=field", service = SortTranslator.class
)
public class FieldSortTranslator implements SortTranslator {

	@Override
	public Optional<Sort> translate(
		JSONObject configurationJSONObject, SortOrder sortOrder,
		Messages messages) {

		String field = configurationJSONObject.getString(
			SortConfigurationKeys.FIELD.getJsonKey());

		if (field.equals("_score")) {
			Sort sort = _sorts.score();

			sort.setSortOrder(sortOrder);

			return Optional.of(sort);
		}

		return Optional.of(_sorts.field(field, sortOrder));
	}

	@Reference
	private Sorts _sorts;

}