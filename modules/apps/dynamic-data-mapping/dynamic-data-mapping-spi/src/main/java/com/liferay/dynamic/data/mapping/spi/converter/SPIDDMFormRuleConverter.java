/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.spi.converter;

import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.spi.converter.model.SPIDDMFormRule;
import com.liferay.dynamic.data.mapping.spi.converter.serializer.SPIDDMFormRuleSerializerContext;

import java.util.List;

/**
 * @author Gabriel Albuquerque
 */
public interface SPIDDMFormRuleConverter {

	public List<SPIDDMFormRule> convert(List<DDMFormRule> ddmFormRules);

	public List<DDMFormRule> convert(
		List<SPIDDMFormRule> spiDDMFormRules,
		SPIDDMFormRuleSerializerContext spiDDMFormRuleSerializerContext);

}