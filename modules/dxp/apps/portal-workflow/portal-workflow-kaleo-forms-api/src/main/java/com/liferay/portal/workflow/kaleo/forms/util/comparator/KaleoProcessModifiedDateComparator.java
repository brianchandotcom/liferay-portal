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

import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess;

/**
 * Orders Kaleo processes according to their modified dates during listing
 * operations. The order can be ascending or descending and is defined by the
 * value specified in the class constructor.
 *
 * @author Inácio Nery
 * @see    com.liferay.portal.workflow.kaleo.forms.service.KaleoProcessLocalService#getKaleoProcesses(
 *         long, int, int, OrderByComparator)
 */
public class KaleoProcessModifiedDateComparator
	extends StagedModelModifiedDateComparator<KaleoProcess> {

	public KaleoProcessModifiedDateComparator() {
		this(false);
	}

	public KaleoProcessModifiedDateComparator(boolean ascending) {
		super(ascending);
	}

	@Override
	public String getOrderBy() {
		return "KaleoProcess." + super.getOrderBy();
	}

}