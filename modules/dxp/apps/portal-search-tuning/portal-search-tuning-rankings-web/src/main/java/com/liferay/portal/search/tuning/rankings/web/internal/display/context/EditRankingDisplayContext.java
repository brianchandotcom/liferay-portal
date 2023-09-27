/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.display.context;

import java.util.Map;

/**
 * @author Kevin Tan
 */
public class EditRankingDisplayContext {

	public String getBackURL() {
		return _backURL;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public Map<String, Object> getData() {
		return _data;
	}

	public String getFormName() {
		return _formName;
	}

	public boolean getInactive() {
		return _inactive;
	}

	public String getKeywords() {
		return _keywords;
	}

	public String getRedirect() {
		return _redirect;
	}

	public String getResultsRankingUid() {
		return _resultsRankingUid;
	}

	public void setBackURL(String backURL) {
		_backURL = backURL;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setData(Map<String, Object> data) {
		_data = data;
	}

	public void setFormName(String formName) {
		_formName = formName;
	}

	public void setInactive(boolean inactive) {
		_inactive = inactive;
	}

	public void setKeywords(String keywords) {
		_keywords = keywords;
	}

	public void setRedirect(String redirect) {
		_redirect = redirect;
	}

	public void setResultsRankingUid(String resultsRankingUid) {
		_resultsRankingUid = resultsRankingUid;
	}

	private String _backURL;
	private long _companyId;
	private Map<String, Object> _data;
	private String _formName;
	private boolean _inactive;
	private String _keywords;
	private String _redirect;
	private String _resultsRankingUid;

}