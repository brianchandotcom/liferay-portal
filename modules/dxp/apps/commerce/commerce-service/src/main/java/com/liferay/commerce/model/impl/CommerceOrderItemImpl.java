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

package com.liferay.commerce.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.model.CommerceMoneyFactoryUtil;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceLocalServiceUtil;
import com.liferay.commerce.service.CommerceOrderLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 * @author Andrea Di Giorgi
 */
@ProviderType
public class CommerceOrderItemImpl extends CommerceOrderItemBaseImpl {

	public CommerceOrderItemImpl() {
	}

	@Override
	public CommerceOrder getCommerceOrder() throws PortalException {
		return CommerceOrderLocalServiceUtil.getCommerceOrder(
			getCommerceOrderId());
	}

	@Override
	public CPDefinition getCPDefinition() throws PortalException {
		CPInstance cpInstance = getCPInstance();

		return cpInstance.getCPDefinition();
	}

	@Override
	public long getCPDefinitionId() throws PortalException {
		CPInstance cpInstance = getCPInstance();

		return cpInstance.getCPDefinitionId();
	}

	@Override
	public CPInstance getCPInstance() throws PortalException {
		return CPInstanceLocalServiceUtil.getCPInstance(getCPInstanceId());
	}

	@Override
	public CommerceMoney getPriceMoney() throws PortalException {
		CommerceOrder commerceOrder = getCommerceOrder();

		return CommerceMoneyFactoryUtil.create(
			commerceOrder.getCommerceCurrencyId(), getPrice());
	}

}