/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
		else {
			return -value;
		}
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}
		else {
			return ORDER_BY_DESC;
		}
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