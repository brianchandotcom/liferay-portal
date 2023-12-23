/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.util.comparator;

import com.liferay.dynamic.data.mapping.model.DDMStructureLink;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author georgelpop
 */
public class StructureLinkStructureModifiedDateComparator
	extends OrderByComparator<DDMStructureLink> {

	public static final String ORDER_BY_ASC = "DDMStructure.modifiedDate ASC";

	public static final String ORDER_BY_DESC = "DDMStructure.modifiedDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"modifiedDate"};

	public StructureLinkStructureModifiedDateComparator() {
		this(false);
	}

	public StructureLinkStructureModifiedDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		DDMStructureLink ddmStructureLink1,
		DDMStructureLink ddmStructureLink2) {

		int value = 0;

		try {
			value = DateUtil.compareTo(
				ddmStructureLink1.getStructure(
				).getModifiedDate(),
				ddmStructureLink2.getStructure(
				).getModifiedDate());
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}

		if (_ascending) {
			return value;
		}

		return -value;
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}

		return ORDER_BY_DESC;
	}

	@Override
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private final boolean _ascending;

}