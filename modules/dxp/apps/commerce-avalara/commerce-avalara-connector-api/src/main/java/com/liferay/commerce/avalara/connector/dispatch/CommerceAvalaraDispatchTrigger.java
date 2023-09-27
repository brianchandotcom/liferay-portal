/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.avalara.connector.dispatch;

import com.liferay.commerce.tax.model.CommerceTaxMethod;
import com.liferay.dispatch.model.DispatchLog;
import com.liferay.dispatch.model.DispatchTrigger;

/**
 * @author Katie Nesterovich
 */
public interface CommerceAvalaraDispatchTrigger {

	public DispatchTrigger addDispatchTrigger(
		CommerceTaxMethod commerceTaxMethod);

	public void deleteDispatchTrigger(CommerceTaxMethod commerceTaxMethod);

	public DispatchLog getLatestDispatchLog(
		CommerceTaxMethod commerceTaxMethod);

	public boolean isJobPreviouslyRun(CommerceTaxMethod commerceTaxMethod);

	public void runJob(CommerceTaxMethod commerceTaxMethod);

	public DispatchTrigger updateDispatchTrigger(
		CommerceTaxMethod commerceTaxMethod);

}