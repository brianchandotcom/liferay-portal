/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.util.comparator;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLink;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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

	public static StructureLinkStructureModifiedDateComparator getInstance(
		boolean ascending) {

		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(
		DDMStructureLink ddmStructureLink1,
		DDMStructureLink ddmStructureLink2) {

		try {
			DDMStructure ddmStructure1 = ddmStructureLink1.getStructure();
			DDMStructure ddmStructure2 = ddmStructureLink2.getStructure();

			int value = DateUtil.compareTo(
				ddmStructure1.getModifiedDate(),
				ddmStructure2.getModifiedDate());

			if (_ascending) {
				return value;
			}

			return -value;
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return 0;
		}
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

	private StructureLinkStructureModifiedDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final StructureLinkStructureModifiedDateComparator
		_INSTANCE_ASCENDING = new StructureLinkStructureModifiedDateComparator(
			true);

	private static final StructureLinkStructureModifiedDateComparator
		_INSTANCE_DESCENDING = new StructureLinkStructureModifiedDateComparator(
			false);

	private static final Log _log = LogFactoryUtil.getLog(
		StructureLinkStructureModifiedDateComparator.class);

	private final boolean _ascending;

}