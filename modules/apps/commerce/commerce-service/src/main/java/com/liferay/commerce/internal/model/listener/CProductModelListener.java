/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.model.listener;

import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.service.CPDAvailabilityEstimateLocalService;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = ModelListener.class)
public class CProductModelListener extends BaseModelListener<CProduct> {

	@Override
	public void onBeforeRemove(CProduct cProduct) {
		_cpdAvailabilityEstimateLocalService.
			deleteCPDAvailabilityEstimateByCProductId(cProduct.getCProductId());
	}

	@Reference
	private CPDAvailabilityEstimateLocalService
		_cpdAvailabilityEstimateLocalService;

}