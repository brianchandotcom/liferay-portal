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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.UpdateByQueryDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.script.Script;
import com.liferay.portal.search.script.ScriptType;
import com.liferay.portal.search.script.Scripts;
import com.liferay.portal.search.tuning.blueprints.keyword.index.constants.KeywordEntryStatus;
import com.liferay.portal.search.tuning.blueprints.keyword.index.constants.Reason;
import com.liferay.portal.search.tuning.blueprints.keyword.index.index.KeywordEntry;
import com.liferay.portal.search.tuning.blueprints.keyword.index.index.name.KeywordIndexName;
import com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.util.KeywordIndexUtil;

import java.util.Date;
import java.util.HashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(service = KeywordIndexWriter.class)
public class KeywordIndexWriterImpl implements KeywordIndexWriter {

	@Override
	public void addHit(KeywordIndexName keywordIndexName, String id) {
		UpdateByQueryDocumentRequest updateByQueryDocumentRequest =
			new UpdateByQueryDocumentRequest(
				_queries.term("_id", id), _getAddHitScript(),
				keywordIndexName.getIndexName());

		updateByQueryDocumentRequest.setRefresh(true);

		_searchEngineAdapter.execute(updateByQueryDocumentRequest);
	}

	@Override
	public void addReport(
		KeywordIndexName keywordIndexName, String id, Reason reason) {

		UpdateByQueryDocumentRequest updateByQueryDocumentRequest =
			new UpdateByQueryDocumentRequest(
				_queries.term("_id", id), _getAddReportScript(reason),
				keywordIndexName.getIndexName());

		updateByQueryDocumentRequest.setRefresh(true);

		_searchEngineAdapter.execute(updateByQueryDocumentRequest);
	}

	@Override
	public String create(
		KeywordIndexName keywordIndexName, KeywordEntry keywordEntry) {

		IndexDocumentRequest documentRequest = new IndexDocumentRequest(
			keywordIndexName.getIndexName(),
			_keywordEntryToDocumentTranslator.translate(keywordEntry));

		documentRequest.setRefresh(true);

		IndexDocumentResponse indexDocumentResponse =
			_searchEngineAdapter.execute(documentRequest);

		return indexDocumentResponse.getUid();
	}

	@Override
	public void remove(KeywordIndexName keywordIndexName, String id) {
		DeleteDocumentRequest deleteDocumentRequest = new DeleteDocumentRequest(
			keywordIndexName.getIndexName(), id);

		deleteDocumentRequest.setRefresh(true);

		_searchEngineAdapter.execute(deleteDocumentRequest);
	}

	@Override
	public void update(
		KeywordIndexName keywordIndexName, KeywordEntry keywordEntry) {

		UpdateDocumentRequest updateDocumentRequest = new UpdateDocumentRequest(
			keywordIndexName.getIndexName(), keywordEntry.getKeywordEntryId(),
			_keywordEntryToDocumentTranslator.translate(keywordEntry));

		updateDocumentRequest.setRefresh(true);

		_searchEngineAdapter.execute(updateDocumentRequest);
	}

	private Script _getAddHitScript() {
		String now = KeywordIndexUtil.toIndexDateString(new Date());

		StringBundler sb = new StringBundler(5);

		sb.append("ctx._source.");
		sb.append(KeywordEntryFields.LAST_ACCESSED);
		sb.append("=");
		sb.append(now);
		sb.append("L;ctx._source.hitCount++");

		return _scripts.builder(
		).idOrCode(
			sb.toString()
		).language(
			"painless"
		).parameters(
			new HashMap<String, Object>()
		).scriptType(
			ScriptType.INLINE
		).build();
	}

	private Script _getAddReportScript(Reason reason) {
		Date now = new Date();

		StringBundler sb = new StringBundler(14);

		sb.append("ctx._source.");
		sb.append(KeywordEntryFields.STATUS);
		sb.append("=");
		sb.append(KeywordEntryStatus.REPORTED.name());
		sb.append(";ctx._source.");
		sb.append(KeywordEntryFields.LAST_REPORTED);
		sb.append("=");
		sb.append(KeywordIndexUtil.toIndexDateString(now));
		sb.append("L;ctx._source.reportCount++;ctx._source.");
		sb.append(KeywordEntryFields.REPORTS);
		sb.append("=");
		sb.append(now);
		sb.append("-");
		sb.append(reason.name());

		return _scripts.builder(
		).idOrCode(
			sb.toString()
		).language(
			"painless"
		).parameters(
			new HashMap<String, Object>()
		).scriptType(
			ScriptType.INLINE
		).build();
	}

	@Reference
	private KeywordEntryToDocumentTranslator _keywordEntryToDocumentTranslator;

	@Reference
	private Queries _queries;

	@Reference
	private Scripts _scripts;

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

}