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

	private String _backURL;
	private Map<String, Object> _data;
	private String _formName;
	private String _inputName;
	private String _originalInputName;
	private String _redirect;

}