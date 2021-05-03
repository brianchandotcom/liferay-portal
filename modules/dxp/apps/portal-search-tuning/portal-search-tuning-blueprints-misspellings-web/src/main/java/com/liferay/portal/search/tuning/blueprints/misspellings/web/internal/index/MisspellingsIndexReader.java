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

package com.liferay.portal.search.tuning.blueprints.misspellings.web.internal.index;

import com.liferay.portal.search.tuning.blueprints.misspellings.index.MisspellingSet;
import com.liferay.portal.search.tuning.blueprints.misspellings.index.name.MisspellingsIndexName;

import java.util.Optional;

/**
 * @author Petteri Karttunen
 */
public interface MisspellingsIndexReader {

	public Optional<MisspellingSet> fetchMisspellingSetOptional(
		MisspellingsIndexName misspellingsIndexName, String id);

	public boolean isIndexExists(MisspellingsIndexName misspellingsIndexName);

}