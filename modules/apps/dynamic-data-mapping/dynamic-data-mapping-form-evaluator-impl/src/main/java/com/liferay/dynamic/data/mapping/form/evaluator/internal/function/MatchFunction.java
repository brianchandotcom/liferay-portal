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

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Leonardo Barros
 */
public class MatchFunction
	implements DDMExpressionFunction.Function2<String, String, Boolean> {

	public static final String NAME = "match";

	@Override
	public Boolean apply(String value, String regex) {
		try {
			Pattern pattern = Pattern.compile(regex);

			Matcher matcher = pattern.matcher(value);

			return matcher.matches();
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return false;
	}

	@Override
	public String getName() {
		return NAME;
	}

	private static final Log _log = LogFactoryUtil.getLog(MatchFunction.class);

}