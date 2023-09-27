/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.display.context;

import java.util.Map;

/**
 * @author Kevin Tan
 */
public class EditSynonymSetsDisplayContext {

	public String getBackURL() {
		return _backURL;
	}

	public Map<String, Object> getData() {
		return _data;
	}

	public String getFormName() {
		return _formName;
	}

	public String getInputName() {
		return _inputName;
	}

	public String getOriginalInputName() {
		return _originalInputName;
	}

	public String getRedirect() {
		return _redirect;
	}

	public String getSynonymSetId() {
		return _synonymSetId;
	}

	public void setBackURL(String backURL) {
		_backURL = backURL;
	}

	public void setData(Map<String, Object> data) {
		_data = data;
	}

	public void setFormName(String formName) {
		_formName = formName;
	}

	public void setInputName(String inputName) {
		_inputName = inputName;
	}

	public void setOriginalInputName(String originalInputName) {
		_originalInputName = originalInputName;
	}

	public void setRedirect(String redirect) {
		_redirect = redirect;
	}

	public void setSynonymSetId(String synonymSetId) {
		_synonymSetId = synonymSetId;
	}

	private String _backURL;
	private Map<String, Object> _data;
	private String _formName;
	private String _inputName;
	private String _originalInputName;
	private String _redirect;
	private String _synonymSetId;

}