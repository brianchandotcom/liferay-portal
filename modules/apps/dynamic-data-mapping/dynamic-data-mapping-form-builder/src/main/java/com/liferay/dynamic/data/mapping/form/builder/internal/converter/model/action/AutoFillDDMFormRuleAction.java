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

package com.liferay.dynamic.data.mapping.form.builder.internal.converter.model.action;

import com.liferay.dynamic.data.mapping.form.builder.internal.converter.model.DDMFormRuleAction;
import com.liferay.dynamic.data.mapping.form.builder.internal.converter.serializer.AutoFillDDMFormRuleActionSerializer;
import com.liferay.dynamic.data.mapping.form.builder.internal.converter.serializer.DDMFormRuleActionSerializer;
import com.liferay.dynamic.data.mapping.form.builder.internal.converter.serializer.DDMFormRuleSerializerContext;
import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.kernel.json.JSON;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Rafael Praxedes
 */
public class AutoFillDDMFormRuleAction implements DDMFormRuleAction {

	public AutoFillDDMFormRuleAction() {
	}

	public AutoFillDDMFormRuleAction(
		String ddmDataProviderInstanceUUID, Map<String, String> inputMapper,
		Map<String, String> outputMapper) {

		_ddmDataProviderInstanceUUID = ddmDataProviderInstanceUUID;
		_inputMapper = inputMapper;
		_outputMapper = outputMapper;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AutoFillDDMFormRuleAction)) {
			return false;
		}

		AutoFillDDMFormRuleAction autoFillDDMFormRuleAction =
			(AutoFillDDMFormRuleAction)obj;

		if (Objects.equals(
				_ddmDataProviderInstanceUUID,
				autoFillDDMFormRuleAction._ddmDataProviderInstanceUUID) &&
			Objects.equals(
				_inputMapper, autoFillDDMFormRuleAction._inputMapper) &&
			Objects.equals(
				_outputMapper, autoFillDDMFormRuleAction._outputMapper)) {

			return true;
		}

		return false;
	}

	@Override
	public String getAction() {
		return "auto-fill";
	}

	@JSON(name = "ddmDataProviderInstanceUUID")
	public String getDDMDataProviderInstanceUUID() {
		return _ddmDataProviderInstanceUUID;
	}

	@JSON(name = "inputs")
	public Map<String, String> getInputParametersMapper() {
		return _inputMapper;
	}

	@JSON(name = "outputs")
	public Map<String, String> getOutputParametersMapper() {
		return _outputMapper;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _ddmDataProviderInstanceUUID);

		hash = HashUtil.hash(hash, _inputMapper);

		return HashUtil.hash(hash, _inputMapper);
	}

	@Override
	public String serialize(
		DDMFormRuleSerializerContext ddmFormRuleSerializerContext) {

		DDMFormRuleActionSerializer ddmFormRuleActionSerializer =
			new AutoFillDDMFormRuleActionSerializer(this);

		return ddmFormRuleActionSerializer.serialize(
			ddmFormRuleSerializerContext);
	}

	public void setDDMDataProviderInstanceUUID(
		String ddmDataProviderInstanceUUID) {

		_ddmDataProviderInstanceUUID = ddmDataProviderInstanceUUID;
	}

	public void setInputParametersMapper(Map<String, String> inputMapper) {
		_inputMapper = inputMapper;
	}

	public void setOutputParametersMapper(Map<String, String> outputMapper) {
		_outputMapper = outputMapper;
	}

	private String _ddmDataProviderInstanceUUID;
	private Map<String, String> _inputMapper = new LinkedHashMap<>();
	private Map<String, String> _outputMapper = new LinkedHashMap<>();

}