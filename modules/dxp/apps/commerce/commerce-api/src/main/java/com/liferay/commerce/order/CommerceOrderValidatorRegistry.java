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

package com.liferay.commerce.order;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;
import java.util.Map;

/**
 * @author Alessio Antonio Rendina
 */
@ProviderType
public interface CommerceOrderValidatorRegistry {

	public CommerceOrderValidator getCommerceOrderValidator(String key);

	public Map<Long, List<CommerceOrderValidatorResult>>
			getCommerceOrderValidatorResults(CommerceOrder commerceOrder)
		throws PortalException;

	public List<CommerceOrderValidator> getCommerceOrderValidators();

	public boolean isValid(CommerceOrder commerceOrder) throws PortalException;

	public List<CommerceOrderValidatorResult> validate(
			CommerceOrderItem commerceOrderItem)
		throws PortalException;

	public List<CommerceOrderValidatorResult> validate(
			CPInstance cpInstance, int quantity)
		throws PortalException;

}