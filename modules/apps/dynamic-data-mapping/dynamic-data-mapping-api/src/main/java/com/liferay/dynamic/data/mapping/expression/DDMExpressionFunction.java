/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.expression;

import com.liferay.petra.string.StringPool;

import java.util.Locale;

/**
 * @author Leonardo Barros
 */
public interface DDMExpressionFunction {

	public default String getLabel(Locale locale) {
		return StringPool.BLANK;
	}

	public String getName();

	public default boolean isCustomDDMExpressionFunction() {
		return false;
	}

	public interface Function0<R> extends DDMExpressionFunction {

		public R apply();

	}

	public interface Function1<A, R> extends DDMExpressionFunction {

		public R apply(A a);

	}

	public interface Function2<A, B, R> extends DDMExpressionFunction {

		public R apply(A a, B b);

	}

	public interface Function3<A, B, C, R> extends DDMExpressionFunction {

		public R apply(A a, B b, C c);

	}

	public interface Function4<A, B, C, D, R> extends DDMExpressionFunction {

		public R apply(A a, B b, C c, D d);

	}

}