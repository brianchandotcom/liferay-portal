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

import java.util.Map;

/**
 * @author Petteri Karttunen
 */
public class KeywordEntryDisplayContext {

	public Map<String, Object> getData() {
		return _data;
	}

	public String getKeywordEntryId() {
		return _keywordEntryId;
	}

	public String getPageTitle() {
		return _pageTitle;
	}

	public String getRedirect() {
		return _redirect;
	}

	public void setData(Map<String, Object> data) {
		_data = data;
	}

	public void setKeywordEntryId(String keywordEntryId) {
		_keywordEntryId = keywordEntryId;
	}

	public void setPageTitle(String pageTitle) {
		_pageTitle = pageTitle;
	}

	public void setRedirect(String redirect) {
		_redirect = redirect;
	}

	private Map<String, Object> _data;
	private String _keywordEntryId;
	private String _pageTitle;
	private String _redirect;

}