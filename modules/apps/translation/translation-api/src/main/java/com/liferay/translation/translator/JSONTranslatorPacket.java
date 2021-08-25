/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.translation.translator;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class JSONTranslatorPacket implements TranslatorPacket {

	public JSONTranslatorPacket(JSONObject jsonObject) {
		_companyId = CompanyThreadLocal.getCompanyId();

		_sourceLanguageId = jsonObject.getString("sourceLanguageId");
		_targetLanguageId = jsonObject.getString("targetLanguageId");

		JSONObject fieldsJSONObject = jsonObject.getJSONObject("fields");

		for (String key : fieldsJSONObject.keySet()) {
			_fieldsMap.put(key, fieldsJSONObject.getString(key));
		}
	}

	public JSONTranslatorPacket(long companyId, JSONObject jsonObject) {
		_companyId = companyId;

		_sourceLanguageId = jsonObject.getString("sourceLanguageId");
		_targetLanguageId = jsonObject.getString("targetLanguageId");

		JSONObject fieldsJSONObject = jsonObject.getJSONObject("fields");

		for (String key : fieldsJSONObject.keySet()) {
			_fieldsMap.put(key, fieldsJSONObject.getString(key));
		}
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public Map<String, String> getFieldsMap() {
		return _fieldsMap;
	}

	@Override
	public String getSourceLanguageId() {
		return _sourceLanguageId;
	}

	@Override
	public String getTargetLanguageId() {
		return _targetLanguageId;
	}

	private final long _companyId;
	private final Map<String, String> _fieldsMap = new LinkedHashMap<>();
	private final String _sourceLanguageId;
	private final String _targetLanguageId;

}