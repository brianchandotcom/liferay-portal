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

package com.liferay.portal.search.tuning.blueprints.keyword.index.util;

import com.liferay.portal.search.tuning.blueprints.content.analysis.constants.ModerationReason;
import com.liferay.portal.search.tuning.blueprints.keyword.index.constants.KeywordEntryStatus;
import com.liferay.portal.search.tuning.blueprints.keyword.index.index.KeywordEntry;

import java.util.List;

/**
 * @author Petteri Karttunen
 */
public interface KeywordIndexHelper {

	public void addActiveKeywordEntry(
		long companyId, long groupId, String languageId, String keywords);

	public void addReportedKeywordEntry(
		long companyId, long groupId, String languageId, String keywords,
		KeywordEntryStatus keywordEntryStatus,
		ModerationReason moderationReason, String reporter);

	public void deleteCompanyKeywordEntries(long companyId);

	public List<KeywordEntry> getCompanyKeywordEntries(long companyId);

	public int getCompanyKeywordEntriesCount(long companyId);

	public void indexKeywords(
		long companyId, long groupId, String languageId, String keyword);

	public boolean isAnalyzedLanguage(String languageId);

	public void reportKeywordEntry(
		long companyId, long groupId, String keywords,
		ModerationReason moderationReason, String reporter);

	public void reportKeywordEntry(
		long companyId, String keywordEntryId,
		ModerationReason moderationReason, String reporter);

}