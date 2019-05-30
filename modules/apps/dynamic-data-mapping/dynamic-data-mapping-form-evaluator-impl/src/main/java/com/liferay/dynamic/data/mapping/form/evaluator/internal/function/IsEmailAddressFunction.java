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

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.dynamic.data.mapping.constants.DDMConstants;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 */
@Component(
	factory = DDMConstants.EXPRESSION_FUNCTION_FACTORY_NAME,
	service = DDMExpressionFunction.Function1.class
)
public class IsEmailAddressFunction
	implements DDMExpressionFunction.Function1<String, Boolean> {

	@Override
	public Boolean apply(String parameter) {
		return Stream.of(
			StringUtil.split(parameter, CharPool.COMMA)
		).map(
			String::trim
		).allMatch(
			IsEmailAddressFunction::_isEmailAddress
		);
	}

	@Override
	public String getName() {
		return "isEmailAddress";
	}

	private static boolean _isEmailAddress(String emailAddress) {
		if (emailAddress == null) {
			return false;
		}

		Matcher matcher = _emailAddressPattern.matcher(emailAddress);

		return matcher.matches();
	}

	private static final Pattern _emailAddressPattern = Pattern.compile(
		"^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)" +
			"*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");

}