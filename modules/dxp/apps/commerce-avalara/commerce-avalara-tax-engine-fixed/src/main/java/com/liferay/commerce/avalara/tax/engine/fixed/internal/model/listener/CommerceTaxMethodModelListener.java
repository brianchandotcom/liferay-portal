/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.avalara.tax.engine.fixed.internal.model.listener;

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