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

package com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.index;

import com.liferay.portal.search.tuning.blueprints.keyword.index.index.KeywordEntry;
import com.liferay.portal.search.tuning.blueprints.keyword.index.index.name.KeywordIndexName;

import java.util.Optional;

/**
 * @author Petteri Karttunen
 */
public interface KeywordIndexReader {

	public Optional<String> fetchIdOptional(
		KeywordIndexName keywordIndexName, long companyId, long groupId,
		String keywords);

	public Optional<KeywordEntry> fetchKeywordEntryOptional(
		KeywordIndexName keywordIndexName, String id);

	public boolean isIndexExists(KeywordIndexName keywordIndexName);

}