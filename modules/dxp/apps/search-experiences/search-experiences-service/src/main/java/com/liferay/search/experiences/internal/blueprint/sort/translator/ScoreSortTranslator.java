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

package com.liferay.search.experiences.internal.blueprint.sort.translator;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.search.sort.Sort;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=_score", service = SortTranslator.class
)
public class ScoreSortTranslator implements SortTranslator {

	@Override
	public Optional<Sort> translate(
		String field, JSONObject jsonObject, SortOrder sortOrder) {

		Sort sort = _sorts.score();

		sort.setSortOrder(sortOrder);

		return Optional.of(sort);
	}

	@Reference
	private Sorts _sorts;

}