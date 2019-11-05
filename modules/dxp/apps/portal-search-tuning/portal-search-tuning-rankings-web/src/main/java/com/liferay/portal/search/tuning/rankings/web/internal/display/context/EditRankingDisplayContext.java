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