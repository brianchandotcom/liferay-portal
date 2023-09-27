/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.builder.internal.converter.model.action;

import com.liferay.dynamic.data.mapping.form.builder.internal.converter.serializer.AutoFillDDMFormRuleActionSerializer;
import com.liferay.dynamic.data.mapping.spi.converter.model.SPIDDMFormRuleAction;
import com.liferay.dynamic.data.mapping.spi.converter.serializer.SPIDDMFormRuleActionSerializer;
import com.liferay.dynamic.data.mapping.spi.converter.serializer.SPIDDMFormRuleSerializerContext;
import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.kernel.json.JSON;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Rafael Praxedes
 */
public class AutoFillDDMFormRuleAction implements SPIDDMFormRuleAction {

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
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AutoFillDDMFormRuleAction)) {
			return false;
		}

		AutoFillDDMFormRuleAction autoFillDDMFormRuleAction =
			(AutoFillDDMFormRuleAction)object;

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
		SPIDDMFormRuleSerializerContext spiDDMFormRuleSerializerContext) {

		SPIDDMFormRuleActionSerializer spiDDMFormRuleActionSerializer =
			new AutoFillDDMFormRuleActionSerializer(this);

		return spiDDMFormRuleActionSerializer.serialize(
			spiDDMFormRuleSerializerContext);
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