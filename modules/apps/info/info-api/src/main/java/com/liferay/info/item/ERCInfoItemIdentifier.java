/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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