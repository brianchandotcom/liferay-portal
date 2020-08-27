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

package com.liferay.commerce.application.internal.model.listener;

import com.liferay.commerce.application.service.CommerceApplicationModelCProductRelLocalService;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = ModelListener.class)
public class CProductModelListener extends BaseModelListener<CProduct> {

	@Override
	public void onBeforeRemove(CProduct cProduct) {
		_commerceApplicationModelCProductRelLocalService.
			deleteCommerceApplicationModelCProductRelsByCProductId(
				cProduct.getCProductId());
	}

	@Reference
	private CommerceApplicationModelCProductRelLocalService
		_commerceApplicationModelCProductRelLocalService;

}