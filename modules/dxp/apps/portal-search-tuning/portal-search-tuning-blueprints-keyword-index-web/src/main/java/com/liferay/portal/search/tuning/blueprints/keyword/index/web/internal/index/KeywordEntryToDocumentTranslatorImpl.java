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

import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.document.DocumentBuilderFactory;
import com.liferay.portal.search.tuning.blueprints.keyword.index.constants.KeywordEntryStatus;
import com.liferay.portal.search.tuning.blueprints.keyword.index.index.KeywordEntry;
import com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.util.KeywordIndexUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(service = KeywordEntryToDocumentTranslator.class)
public class KeywordEntryToDocumentTranslatorImpl
	implements KeywordEntryToDocumentTranslator {

	@Override
	public Document translate(KeywordEntry keywordEntry) {
		DocumentBuilder documentBuilder = _documentBuilderFactory.builder();

		if (keywordEntry.getContent() != null) {
			documentBuilder.setString(
				KeywordEntryFields.CONTENT, keywordEntry.getContent());
		}

		if (keywordEntry.getCreated() != null) {
			documentBuilder.setDate(
				KeywordEntryFields.CREATED,
				KeywordIndexUtil.toIndexDateString(keywordEntry.getCreated()));
		}

		if (keywordEntry.getGroupId() != null) {
			documentBuilder.setLong(
				KeywordEntryFields.GROUP_ID, keywordEntry.getGroupId());
		}

		if (keywordEntry.getHitCount() != null) {
			documentBuilder.setLong(
				KeywordEntryFields.HIT_COUNT, keywordEntry.getHitCount());
		}

		if (keywordEntry.getKeywordEntryId() != null) {
			documentBuilder.setValue(
				KeywordEntryFields.ID, keywordEntry.getKeywordEntryId());
		}

		if (keywordEntry.getLanguageId() != null) {
			documentBuilder.setString(
				KeywordEntryFields.LANGUAGE_ID, keywordEntry.getLanguageId());
		}

		if (keywordEntry.getLastAccessed() != null) {
			documentBuilder.setDate(
				KeywordEntryFields.LAST_ACCESSED,
				KeywordIndexUtil.toIndexDateString(
					keywordEntry.getLastAccessed()));
		}

		if (keywordEntry.getLastReported() != null) {
			documentBuilder.setDate(
				KeywordEntryFields.LAST_REPORTED,
				KeywordIndexUtil.toIndexDateString(
					keywordEntry.getLastReported()));
		}

		if (keywordEntry.getModified() != null) {
			documentBuilder.setDate(
				KeywordEntryFields.MODIFIED,
				KeywordIndexUtil.toIndexDateString(keywordEntry.getModified()));
		}

		if (keywordEntry.getReportCount() != null) {
			documentBuilder.setLong(
				KeywordEntryFields.REPORT_COUNT, keywordEntry.getReportCount());
		}

		if (keywordEntry.getStatus() != null) {
			KeywordEntryStatus status = keywordEntry.getStatus();

			documentBuilder.setString(KeywordEntryFields.STATUS, status.name());
		}

		if (keywordEntry.getStatusDate() != null) {
			documentBuilder.setDate(
				KeywordEntryFields.STATUS_DATE,
				KeywordIndexUtil.toIndexDateString(
					keywordEntry.getStatusDate()));
		}

		if (KeywordIndexUtil.isAnalyzedLanguage(keywordEntry.getLanguageId())) {
			documentBuilder.setString(
				KeywordIndexUtil.getLocalizedFieldName(
					KeywordEntryFields.CONTENT, keywordEntry.getLanguageId()),
				keywordEntry.getContent());
		}

		return documentBuilder.build();
	}

	@Reference
	private DocumentBuilderFactory _documentBuilderFactory;

}