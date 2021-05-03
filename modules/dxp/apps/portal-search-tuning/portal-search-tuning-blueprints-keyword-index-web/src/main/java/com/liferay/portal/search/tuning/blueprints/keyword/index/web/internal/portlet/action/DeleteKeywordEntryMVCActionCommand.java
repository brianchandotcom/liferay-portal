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

package com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.tuning.blueprints.keyword.index.index.KeywordEntry;
import com.liferay.portal.search.tuning.blueprints.keyword.index.index.name.KeywordIndexName;
import com.liferay.portal.search.tuning.blueprints.keyword.index.index.name.KeywordIndexNameBuilder;
import com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.constants.KeywordIndexMVCCommandNames;
import com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.constants.KeywordIndexPortletKeys;
import com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.constants.KeywordIndexWebKeys;
import com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.index.KeywordIndexReader;
import com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.index.KeywordIndexWriter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + KeywordIndexPortletKeys.KEYWORD_INDEX_ADMIN,
		"mvc.command.name=" + KeywordIndexMVCCommandNames.DELETE_KEYWORD_ENTRY
	},
	service = MVCActionCommand.class
)
public class DeleteKeywordEntryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		KeywordIndexName keywordIndexName =
			_keywordIndexNameBuilder.getKeywordIndexName(
				_portal.getCompanyId(actionRequest));

		_deleteKeywordEntries(
			keywordIndexName,
			_getKeywordEntries(actionRequest, keywordIndexName));

		sendRedirect(actionRequest, actionResponse);
	}

	private void _deleteKeywordEntries(
		KeywordIndexName keywordIndexName, List<KeywordEntry> keywordEntries) {

		for (KeywordEntry keywordEntry : keywordEntries) {
			_keywordIndexWriter.remove(
				keywordIndexName, keywordEntry.getKeywordEntryId());
		}
	}

	private List<KeywordEntry> _getKeywordEntries(
		ActionRequest actionRequest, KeywordIndexName keywordIndexName) {

		return Stream.of(
			_getKeywordEntryIds(actionRequest)
		).map(
			keywordEntryId -> _keywordIndexReader.fetchKeywordEntryOptional(
				keywordIndexName, keywordEntryId)
		).filter(
			Optional::isPresent
		).map(
			Optional::get
		).collect(
			Collectors.toList()
		);
	}

	private String[] _getKeywordEntryIds(ActionRequest actionRequest) {
		String[] keywordEntryIds = null;

		String keywordEntryId = ParamUtil.getString(
			actionRequest, KeywordIndexWebKeys.KEYWORD_ENTRY_ID);

		if (!Validator.isBlank(keywordEntryId)) {
			keywordEntryIds = new String[] {keywordEntryId};
		}
		else {
			keywordEntryIds = ParamUtil.getStringValues(
				actionRequest, KeywordIndexWebKeys.ROW_IDS);
		}

		return keywordEntryIds;
	}

	@Reference
	private IndexNameBuilder _indexNameBuilder;

	@Reference
	private KeywordIndexNameBuilder _keywordIndexNameBuilder;

	@Reference
	private KeywordIndexReader _keywordIndexReader;

	@Reference
	private KeywordIndexWriter _keywordIndexWriter;

	@Reference
	private Portal _portal;

}