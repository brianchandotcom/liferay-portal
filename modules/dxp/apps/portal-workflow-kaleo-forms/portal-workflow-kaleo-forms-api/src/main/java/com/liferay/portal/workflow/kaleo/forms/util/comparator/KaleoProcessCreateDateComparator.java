/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.forms.util.comparator;

import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess;

/**
 * Orders Kaleo processes according to their created date during listing
 * operations. The order can be ascending or descending and is defined by the
 * value specified in the class constructor.
 *
 * @author Inácio Nery
 * @see    com.liferay.portal.workflow.kaleo.forms.service.KaleoProcessLocalService#getKaleoProcesses(
 *         long, int, int, OrderByComparator)
 */
public class KaleoProcessCreateDateComparator
	extends OrderByComparator<KaleoProcess> {

	public static final String ORDER_BY_ASC = "KaleoProcess.createDate ASC";

	public static final String ORDER_BY_DESC = "KaleoProcess.createDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"createDate"};

	public KaleoProcessCreateDateComparator() {
		this(false);
	}

	public KaleoProcessCreateDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(KaleoProcess kaleoProcess1, KaleoProcess kaleoProcess2) {
		int value = DateUtil.compareTo(
			kaleoProcess1.getCreateDate(), kaleoProcess2.getCreateDate());

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