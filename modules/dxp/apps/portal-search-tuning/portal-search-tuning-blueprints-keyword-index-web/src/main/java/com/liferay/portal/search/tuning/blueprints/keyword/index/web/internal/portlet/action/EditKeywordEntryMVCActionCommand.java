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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.tuning.blueprints.keyword.index.constants.KeywordEntryStatus;
import com.liferay.portal.search.tuning.blueprints.keyword.index.index.KeywordEntry;
import com.liferay.portal.search.tuning.blueprints.keyword.index.index.name.KeywordIndexName;
import com.liferay.portal.search.tuning.blueprints.keyword.index.index.name.KeywordIndexNameBuilder;
import com.liferay.portal.search.tuning.blueprints.keyword.index.util.KeywordIndexHelper;
import com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.constants.KeywordIndexMVCCommandNames;
import com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.constants.KeywordIndexPortletKeys;
import com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.constants.KeywordIndexWebKeys;
import com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.index.KeywordIndexReader;
import com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.index.KeywordIndexWriter;

import java.util.Date;
import java.util.Optional;

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
		"mvc.command.name=" + KeywordIndexMVCCommandNames.EDIT_KEYWORD_ENTRY
	},
	service = MVCActionCommand.class
)
public class EditKeywordEntryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		editKeywordEntry(actionRequest);

		sendRedirect(actionRequest, actionResponse);
	}

	protected void editKeywordEntry(ActionRequest actionRequest) {
		KeywordIndexName keywordIndexName =
			_keywordIndexNameBuilder.getKeywordIndexName(
				_portal.getCompanyId(actionRequest));

		String[] keywordEntryIds = _getKeywordEntryIds(actionRequest);

		if (keywordEntryIds == null) {
			_keywordIndexHelper.addKeywordEntry(
				_portal.getCompanyId(actionRequest), _getGroupId(actionRequest),
				_getLanguageId(actionRequest), _getContent(actionRequest),
				KeywordEntryStatus.ACTIVE);

			return;
		}

		for (String id : keywordEntryIds) {
			Optional<KeywordEntry> keywordEntryOptional =
				_getKeywordEntryOptional(id, keywordIndexName);

			if (keywordEntryOptional.isPresent()) {
				_updateKeywordEntry(
					actionRequest, keywordIndexName,
					keywordEntryOptional.get());
			}
		}
	}

	private String _getContent(ActionRequest actionRequest) {
		return ParamUtil.getString(actionRequest, KeywordIndexWebKeys.CONTENT);
	}

	private long _getGroupId(ActionRequest actionRequest) {
		return ParamUtil.getLong(actionRequest, KeywordIndexWebKeys.GROUP_ID);
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

	private Optional<KeywordEntry> _getKeywordEntryOptional(
		String id, KeywordIndexName keywordIndexName) {

		return _keywordIndexReader.fetchKeywordEntryOptional(
			keywordIndexName, id);
	}

	private String _getLanguageId(ActionRequest actionRequest) {
		return ParamUtil.getString(
			actionRequest, KeywordIndexWebKeys.LANGUAGE_ID);
	}

	private KeywordEntryStatus _getStatus(ActionRequest actionRequest)
		throws IllegalArgumentException {

		String status = ParamUtil.getString(
			actionRequest, KeywordIndexWebKeys.STATUS);

		if (Validator.isBlank(status)) {
			return null;
		}

		return KeywordEntryStatus.valueOf(status);
	}

	private void _updateKeywordEntry(
		ActionRequest actionRequest, KeywordIndexName keywordIndexName,
		KeywordEntry keywordEntry) {

		try {
			KeywordEntryStatus status = _getStatus(actionRequest);

			long groupId = _getGroupId(actionRequest);

			if ((status == null) && (groupId == 0)) {
				return;
			}

			KeywordEntry.KeywordEntryBuilder keywordEntryBuilder =
				new KeywordEntry.KeywordEntryBuilder();

			keywordEntryBuilder.keywordEntryId(
				keywordEntry.getKeywordEntryId());

			if (status != null) {
				keywordEntryBuilder.status(status);
				keywordEntryBuilder.statusDate(new Date());
			}

			if (groupId > 0) {
				keywordEntryBuilder.groupId(groupId);
			}

			keywordEntryBuilder.modified(new Date());

			_keywordIndexWriter.update(
				keywordIndexName, keywordEntryBuilder.build());
		}
		catch (IllegalArgumentException illegalArgumentException) {
			SessionErrors.add(
				actionRequest, KeywordIndexWebKeys.ERROR,
				illegalArgumentException.getMessage());

			_log.error(
				illegalArgumentException.getMessage(),
				illegalArgumentException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditKeywordEntryMVCActionCommand.class);

	@Reference
	private KeywordIndexHelper _keywordIndexHelper;

	@Reference
	private KeywordIndexNameBuilder _keywordIndexNameBuilder;

	@Reference
	private KeywordIndexReader _keywordIndexReader;

	@Reference
	private KeywordIndexWriter _keywordIndexWriter;

	@Reference
	private Portal _portal;

}