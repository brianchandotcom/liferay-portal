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

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.tuning.blueprints.keyword.index.index.name.KeywordIndexNameBuilder;
import com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.index.KeywordIndexReader;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Petteri Karttunen
 */
public class ViewKeywordEntryDisplayBuilder extends KeywordEntryDisplayBuilder {

	public ViewKeywordEntryDisplayBuilder(
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

	private void _setPageTitle(
		KeywordEntryDisplayContext queryStringDisplayContext) {

		queryStringDisplayContext.setPageTitle(
			language.get(httpServletRequest, "view-keyword-entry"));
	}

}