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

package com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.display.context;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.tuning.blueprints.keyword.index.index.name.KeywordIndexNameBuilder;
import com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.constants.KeywordIndexMVCCommandNames;
import com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.index.KeywordIndexReader;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Petteri Karttunen
 */
public class EditKeywordEntryDisplayBuilder extends KeywordEntryDisplayBuilder {

	public EditKeywordEntryDisplayBuilder(
		HttpServletRequest httpServletRequest, Language language, Portal portal,
		RenderRequest renderRequest, RenderResponse renderResponse,
		KeywordIndexNameBuilder keywordIndexNameBuilder,
		KeywordIndexReader keywordIndexReader) {

		super(
			httpServletRequest, language, portal, renderRequest, renderResponse,
			keywordIndexNameBuilder, keywordIndexReader);
	}

	public KeywordEntryDisplayContext build() {
		KeywordEntryDisplayContext keywordEntryDisplayContext =
			new KeywordEntryDisplayContext();

		setData(keywordEntryDisplayContext);
		setKeywordEntryId(keywordEntryDisplayContext);
		_setPageTitle(keywordEntryDisplayContext);
		setRedirect(keywordEntryDisplayContext);

		return keywordEntryDisplayContext;
	}

	@Override
	protected Map<String, Object> getProps() {
		Map<String, Object> props = super.getProps();

		props.put("submitFormURL", _getSubmitFormURL());

		return props;
	}

	private String _getSubmitFormURL() {
		ActionURL actionURL = renderResponse.createActionURL();

		actionURL.setParameter(
			ActionRequest.ACTION_NAME,
			KeywordIndexMVCCommandNames.EDIT_KEYWORD_ENTRY);
		actionURL.setParameter(
			Constants.CMD,
			keywordEntryOptional.isPresent() ? Constants.EDIT : Constants.ADD);
		actionURL.setParameter("redirect", getRedirect());

		return actionURL.toString();
	}

	private void _setPageTitle(
		KeywordEntryDisplayContext keywordEntryDisplayContext) {

		StringBundler sb = new StringBundler(2);

		sb.append(keywordEntryOptional.isPresent() ? "edit-" : "add-");

		sb.append("keyword-entry");

		keywordEntryDisplayContext.setPageTitle(
			language.get(httpServletRequest, sb.toString()));
	}

}