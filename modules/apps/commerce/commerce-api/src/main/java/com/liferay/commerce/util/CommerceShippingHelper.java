/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.util;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.Dimensions;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
@ProviderType
public interface CommerceShippingHelper {

	public Dimensions getDimensions(CommerceOrderItem commerceOrderItem);

	public Dimensions getDimensions(CPInstance cpInstance);

	public Dimensions getDimensions(List<CommerceOrderItem> commerceOrderItems)
		throws PortalException;

	public double getWeight(CommerceOrderItem commerceOrderItem);

	public double getWeight(CPInstance cpInstance);

	public double getWeight(List<CommerceOrderItem> commerceOrderItems)
		throws PortalException;

	public boolean isFreeShipping(CommerceOrder commerceOrder)
		throws PortalException;

	public boolean isShippable(CommerceOrder commerceOrder)
		throws PortalException;

}