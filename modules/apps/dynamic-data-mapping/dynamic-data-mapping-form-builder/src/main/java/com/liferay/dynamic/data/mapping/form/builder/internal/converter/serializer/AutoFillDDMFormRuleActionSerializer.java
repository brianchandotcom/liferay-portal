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

package com.liferay.dynamic.data.mapping.form.builder.internal.converter.serializer;

import com.liferay.dynamic.data.mapping.form.builder.internal.converter.model.action.AutoFillDDMFormRuleAction;
import com.liferay.dynamic.data.mapping.spi.converter.serializer.SPIDDMFormRuleActionSerializer;
import com.liferay.dynamic.data.mapping.spi.converter.serializer.SPIDDMFormRuleSerializerContext;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Map;
import java.util.Objects;

/**
 * @author Leonardo Barros
 */
public class AutoFillDDMFormRuleActionSerializer
	implements SPIDDMFormRuleActionSerializer {

	public AutoFillDDMFormRuleActionSerializer(
		AutoFillDDMFormRuleAction autoFillDDMFormRuleAction) {

		_autoFillDDMFormRuleAction = autoFillDDMFormRuleAction;
	}

	@Override
	public String serialize(
		SPIDDMFormRuleSerializerContext spiDDMFormRuleSerializerContext) {

		Map<String, String> inputParametersMapper =
			_autoFillDDMFormRuleAction.getInputParametersMapper();

		for (Map.Entry<String, String> inputParameterMapper :
				inputParametersMapper.entrySet()) {

			if (Objects.equals(
					inputParameterMapper.getValue(), StringPool.BLANK)) {

				return null;
			}
		}

		Map<String, String> outputParametersMapper =
			_autoFillDDMFormRuleAction.getOutputParametersMapper();

		for (Map.Entry<String, String> outputParameterMapper :
				outputParametersMapper.entrySet()) {

			if (Objects.equals(
					outputParameterMapper.getValue(), StringPool.BLANK)) {

				return null;
			}
		}

		return String.format(
			_FUNCTION_CALL_TERNARY_EXPRESSION_FORMAT, "call",
			StringUtil.quote(
				_autoFillDDMFormRuleAction.getDDMDataProviderInstanceUUID()),
			_convertAutoFillInputParameters(inputParametersMapper),
			_convertAutoFillOutputParameters(outputParametersMapper));
	}

	private String _convertAutoFillInputParameters(
		Map<String, String> inputParametersMapper) {

		if (MapUtil.isEmpty(inputParametersMapper)) {
			return StringUtil.quote(StringPool.BLANK);
		}

		StringBundler sb = new StringBundler(
			(inputParametersMapper.size() * 4) - 1);

		for (Map.Entry<String, String> inputParameterMapper :
				inputParametersMapper.entrySet()) {

			sb.append(inputParameterMapper.getKey());
			sb.append(CharPool.EQUAL);
			sb.append(inputParameterMapper.getValue());
			sb.append(CharPool.SEMICOLON);
		}

		sb.setIndex(sb.index() - 1);

		return StringUtil.quote(sb.toString());
	}

	private String _convertAutoFillOutputParameters(
		Map<String, String> outputParametersMapper) {

		if (MapUtil.isEmpty(outputParametersMapper)) {
			return StringUtil.quote(StringPool.BLANK);
		}

		StringBundler sb = new StringBundler(
			(outputParametersMapper.size() * 4) - 1);

		for (Map.Entry<String, String> outputParameterMapper :
				outputParametersMapper.entrySet()) {

			sb.append(outputParameterMapper.getValue());
			sb.append(CharPool.EQUAL);
			sb.append(outputParameterMapper.getKey());
			sb.append(CharPool.SEMICOLON);
		}

		sb.setIndex(sb.index() - 1);

		return StringUtil.quote(sb.toString());
	}

	private static final String _FUNCTION_CALL_TERNARY_EXPRESSION_FORMAT =
		"%s(%s, %s, %s)";

	private final AutoFillDDMFormRuleAction _autoFillDDMFormRuleAction;

}