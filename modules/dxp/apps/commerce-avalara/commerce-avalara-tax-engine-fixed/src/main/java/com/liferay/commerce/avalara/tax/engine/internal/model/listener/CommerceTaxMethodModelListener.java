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

package com.liferay.commerce.avalara.tax.engine.internal.model.listener;

import com.liferay.commerce.avalara.connector.dispatch.CommerceAvalaraDispatchTrigger;
import com.liferay.commerce.avalara.connector.engine.CommerceAvalaraConnectorEngine;
import com.liferay.commerce.tax.model.CommerceTaxMethod;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Katie Nesterovich
 */
@Component(service = ModelListener.class)
public class CommerceTaxMethodModelListener
	extends BaseModelListener<CommerceTaxMethod> {

	@Override
	public void onAfterCreate(CommerceTaxMethod commerceTaxMethod) {
		String engineKey = commerceTaxMethod.getEngineKey();

		if (engineKey.equals("avalara")) {
			_commerceAvalaraDispatchTriggerHelper.addDispatchTrigger(
				commerceTaxMethod);
		}
	}

	@Override
	public void onAfterUpdate(
		CommerceTaxMethod originalCommerceTaxMethod,
		CommerceTaxMethod commerceTaxMethod) {

		String engineKey = commerceTaxMethod.getEngineKey();

		if (engineKey.equals("avalara")) {
			_commerceAvalaraDispatchTriggerHelper.updateDispatchTrigger(
				commerceTaxMethod);
		}
	}

	@Override
	public void onBeforeRemove(CommerceTaxMethod commerceTaxMethod) {
		String engineKey = commerceTaxMethod.getEngineKey();

		if (engineKey.equals("avalara")) {
			_commerceAvalaraDispatchTriggerHelper.deleteDispatchTrigger(
				commerceTaxMethod);

			_commerceAvalaraConnectorEngine.deleteByAddressEntries(
				commerceTaxMethod);
		}
	}

	@Reference
	private CommerceAvalaraConnectorEngine _commerceAvalaraConnectorEngine;

	@Reference
	private CommerceAvalaraDispatchTrigger
		_commerceAvalaraDispatchTriggerHelper;

}