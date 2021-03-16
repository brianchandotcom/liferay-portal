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

package com.liferay.portal.search.tuning.blueprints.engine.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.tuning.blueprints.attributes.BlueprintsAttributes;
import com.liferay.portal.search.tuning.blueprints.engine.exception.BlueprintsEngineException;
import com.liferay.portal.search.tuning.blueprints.message.Messages;

/**
 * @author Petteri Karttunen
 */
public interface BlueprintsSearchRequestContributorHelper {

	public void combine(
			SearchRequestBuilder searchRequestBuilder, long blueprintId,
			BlueprintsAttributes blueprintsAttributes, Messages messages)
		throws BlueprintsEngineException, PortalException;

}