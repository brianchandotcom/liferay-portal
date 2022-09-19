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

package com.liferay.commerce.avalara.connector.helper;

import com.liferay.commerce.tax.model.CommerceTaxMethod;
import com.liferay.dispatch.model.DispatchLog;
import com.liferay.dispatch.model.DispatchTrigger;

/**
 * @author Katie Nesterovich
 */
public interface CommerceAvalaraDispatchTriggerHelper {

	public DispatchTrigger createDispatchTrigger(
		CommerceTaxMethod commerceTaxMethod);

	public void deleteDispatchTrigger(CommerceTaxMethod commerceTaxMethod);

	public DispatchLog getLatestDispatchLog(
		CommerceTaxMethod commerceTaxMethod);

	public boolean jobPreviouslyRun(CommerceTaxMethod commerceTaxMethod);

	public void runJob(CommerceTaxMethod commerceTaxMethod);

	public DispatchTrigger updateDispatchTrigger(
		CommerceTaxMethod commerceTaxMethod);

}