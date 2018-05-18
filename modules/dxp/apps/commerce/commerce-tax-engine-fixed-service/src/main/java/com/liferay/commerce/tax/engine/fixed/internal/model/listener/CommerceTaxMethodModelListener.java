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

package com.liferay.commerce.tax.engine.fixed.internal.model.listener;

import com.liferay.commerce.model.CommerceTaxMethod;
import com.liferay.commerce.tax.engine.fixed.service.CommerceTaxFixedRateAddressRelLocalService;
import com.liferay.commerce.tax.engine.fixed.service.CommerceTaxFixedRateLocalService;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = ModelListener.class)
public class CommerceTaxMethodModelListener
	extends BaseModelListener<CommerceTaxMethod> {

	@Override
	public void onBeforeRemove(CommerceTaxMethod commerceTaxMethod) {
		_commerceTaxFixedRateAddressRelLocalService.
			deleteCommerceTaxFixedRateAddressRelsByCommerceTaxMethodId(
				commerceTaxMethod.getCommerceTaxMethodId());

		_commerceTaxFixedRateLocalService.
			deleteCommerceTaxFixedRateByCommerceTaxMethodId(
				commerceTaxMethod.getCommerceTaxMethodId());
	}

	@Reference
	private CommerceTaxFixedRateAddressRelLocalService
		_commerceTaxFixedRateAddressRelLocalService;

	@Reference
	private CommerceTaxFixedRateLocalService _commerceTaxFixedRateLocalService;

}