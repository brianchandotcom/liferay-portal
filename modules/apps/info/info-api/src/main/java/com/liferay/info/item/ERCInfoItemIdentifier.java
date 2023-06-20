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

package com.liferay.info.item;

import com.liferay.info.item.provider.filter.InfoItemServiceFilter;
import com.liferay.petra.string.StringBundler;

import java.util.Objects;

/**
 * @author Eudaldo Alonso
 */
public class ERCInfoItemIdentifier extends BaseInfoItemIdentifier {

	public static final InfoItemServiceFilter INFO_ITEM_SERVICE_FILTER =
		getInfoItemServiceFilter(ERCInfoItemIdentifier.class);

	public ERCInfoItemIdentifier(String externalReferenceCode) {
		_externalReferenceCode = externalReferenceCode;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ERCInfoItemIdentifier)) {
			return false;
		}

		ERCInfoItemIdentifier ercInfoItemIdentifier =
			(ERCInfoItemIdentifier)object;

		return Objects.equals(
			_externalReferenceCode,
			ercInfoItemIdentifier._externalReferenceCode);
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	@Override
	public InfoItemServiceFilter getInfoItemServiceFilter() {
		return INFO_ITEM_SERVICE_FILTER;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_externalReferenceCode);
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{className=", ERCInfoItemIdentifier.class.getName(),
			", externalReferenceCode=", _externalReferenceCode, "}");
	}

	private final String _externalReferenceCode;

}