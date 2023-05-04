/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.order.status;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Locale;

/**
 * @author Alec Sloan
 */
public interface CommerceOrderStatus {

	public CommerceOrder doTransition(CommerceOrder commerceOrder, long userId)
		throws PortalException;

	public int getKey();

	public String getLabel(Locale locale);

	public int getPriority();

	public default boolean isComplete(CommerceOrder commerceOrder)
		throws PortalException {

		return true;
	}

	public default boolean isEnabled(CommerceOrder commerceOrder)
		throws PortalException {

		return true;
	}

	public default boolean isTransitionCriteriaMet(CommerceOrder commerceOrder)
		throws PortalException {

		return true;
	}

	public default boolean isValidForOrder(CommerceOrder commerceOrder)
		throws PortalException {

		return true;
	}

	public default boolean isWorkflowEnabled(CommerceOrder commerceOrder)
		throws PortalException {

		return false;
	}

}