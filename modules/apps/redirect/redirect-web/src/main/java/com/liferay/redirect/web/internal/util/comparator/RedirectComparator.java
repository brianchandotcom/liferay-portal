/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.web.internal.util.comparator;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.function.Function;

/**
 * @author Alejandro Tardín
 */
public class RedirectComparator<T extends BaseModel, V extends Comparable<V>>
	extends OrderByComparator<T> {

	public RedirectComparator(
		String modelName, String fieldName, Function<T, V> fieldValueFunction,
		boolean ascending) {

		_modelName = modelName;
		_fieldName = fieldName;
		_fieldValueFunction = fieldValueFunction;
		_ascending = ascending;
	}

	@Override
	public int compare(T baseModel1, T baseModel2) {
		V fieldValue1 = _fieldValueFunction.apply(baseModel1);
		V fieldValue2 = _fieldValueFunction.apply(baseModel2);

		int value = fieldValue1.compareTo(fieldValue2);

		if (_ascending) {
			return value;
		}

		return -value;
	}

	public V getFieldValue(T baseModel) {
		return _fieldValueFunction.apply(baseModel);
	}

	@Override
	public String getOrderBy() {
		return StringBundler.concat(
			_modelName, StringPool.PERIOD, _fieldName, StringPool.SPACE,
			_ascending ? "DESC" : "ASC");
	}

	@Override
	public String[] getOrderByFields() {
		return new String[] {_fieldName};
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private final boolean _ascending;
	private final String _fieldName;
	private final Function<T, V> _fieldValueFunction;
	private final String _modelName;

}