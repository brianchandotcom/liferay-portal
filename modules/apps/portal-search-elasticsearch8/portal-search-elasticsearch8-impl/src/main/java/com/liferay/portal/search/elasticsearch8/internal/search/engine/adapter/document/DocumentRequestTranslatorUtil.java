/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.document;

import co.elastic.clients.json.JsonData;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.elasticsearch8.internal.document.ElasticsearchDocumentFactoryUtil;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;

/**
 * @author Petteri Karttunen
 */
public class DocumentRequestTranslatorUtil {

	protected static JsonData getDocument(
		Document document,
		com.liferay.portal.kernel.search.Document document71) {

		if (document != null) {
			return ElasticsearchDocumentFactoryUtil.getElasticsearchDocument(
				document);
		}

		return ElasticsearchDocumentFactoryUtil.getElasticsearchDocument(
			document71);
	}

	protected static String getUid(IndexDocumentRequest indexDocumentRequest) {
		String uid = indexDocumentRequest.getUid();

		if (!Validator.isBlank(uid)) {
			return uid;
		}

		if (indexDocumentRequest.getDocument() != null) {
			Document document = indexDocumentRequest.getDocument();

			return document.getString(Field.UID);
		}

		@SuppressWarnings("deprecation")
		com.liferay.portal.kernel.search.Document document =
			indexDocumentRequest.getDocument71();

		Field field = document.getField(Field.UID);

		if (field != null) {
			return field.getValue();
		}

		return uid;
	}

	protected static String getUid(
		UpdateDocumentRequest updateDocumentRequest) {

		String uid = updateDocumentRequest.getUid();

		if (!Validator.isBlank(uid)) {
			return uid;
		}

		if (updateDocumentRequest.getDocument() != null) {
			Document document = updateDocumentRequest.getDocument();

			return document.getString(Field.UID);
		}

		@SuppressWarnings("deprecation")
		com.liferay.portal.kernel.search.Document document =
			updateDocumentRequest.getDocument71();

		Field field = document.getField(Field.UID);

		if (field != null) {
			uid = field.getValue();
		}

		return uid;
	}

}