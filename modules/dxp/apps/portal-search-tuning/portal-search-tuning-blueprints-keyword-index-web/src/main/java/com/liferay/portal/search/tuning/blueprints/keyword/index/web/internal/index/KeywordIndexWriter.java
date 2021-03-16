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

import com.liferay.portal.search.tuning.blueprints.keyword.index.constants.Reason;
import com.liferay.portal.search.tuning.blueprints.keyword.index.index.KeywordEntry;
import com.liferay.portal.search.tuning.blueprints.keyword.index.index.name.KeywordIndexName;

/**
 * @author Petteri Karttunen
 */
public interface KeywordIndexWriter {

	public void addHit(KeywordIndexName keywordIndexName, String id);

	public void addReport(
		KeywordIndexName keywordIndexName, String id, Reason reason);

	public String create(
		KeywordIndexName keywordIndexName, KeywordEntry keywordEntry);

	public void remove(KeywordIndexName keywordIndexName, String id);

	public void update(
		KeywordIndexName keywordIndexName, KeywordEntry keywordEntry);

}