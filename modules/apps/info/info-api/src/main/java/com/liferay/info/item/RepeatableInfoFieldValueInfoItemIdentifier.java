/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.item;

import com.liferay.info.item.provider.filter.InfoItemServiceFilter;
import com.liferay.petra.string.StringBundler;

import java.util.List;
import java.util.Objects;

/**
 * @author Víctor Galán
 */
public class RepeatableInfoFieldValueInfoItemIdentifier
	extends BaseInfoItemIdentifier {

	public static final InfoItemServiceFilter INFO_ITEM_SERVICE_FILTER =
		getInfoItemServiceFilter(
			RepeatableInfoFieldValueInfoItemIdentifier.class);

	public RepeatableInfoFieldValueInfoItemIdentifier(
		List<String> fieldNames, int iterationNumber,
		InfoItemReference objectInfoItemReference) {

		_fieldNames = fieldNames;
		_iterationNumber = iterationNumber;
		_objectInfoItemReference = objectInfoItemReference;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof RepeatableInfoFieldValueInfoItemIdentifier)) {
			return false;
		}

		RepeatableInfoFieldValueInfoItemIdentifier
			repeatableFieldInfoItemIdentifier =
				(RepeatableInfoFieldValueInfoItemIdentifier)object;

		if (Objects.equals(
				_fieldNames, repeatableFieldInfoItemIdentifier._fieldNames) &&
			Objects.equals(
				_objectInfoItemReference,
				repeatableFieldInfoItemIdentifier._objectInfoItemReference) &&
			Objects.equals(
				_iterationNumber,
				repeatableFieldInfoItemIdentifier._iterationNumber)) {

			return true;
		}

		return false;
	}

	public List<String> getFieldNames() {
		return _fieldNames;
	}

	@Override
	public InfoItemServiceFilter getInfoItemServiceFilter() {
		return INFO_ITEM_SERVICE_FILTER;
	}

	public int getIterationNumber() {
		return _iterationNumber;
	}

	public InfoItemReference getObjectInfoItemReference() {
		return _objectInfoItemReference;
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			_fieldNames, _objectInfoItemReference, _iterationNumber);
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{className=",
			RepeatableInfoFieldValueInfoItemIdentifier.class.getName(),
			", _fieldName=", _fieldNames, ", _iterationNumber",
			_iterationNumber, ", _infoItemIdentifier=",
			_objectInfoItemReference, "}");
	}

	private final List<String> _fieldNames;
	private final int _iterationNumber;
	private final InfoItemReference _objectInfoItemReference;

}