/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.status;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Locale;

/**
 * @author Alec Sloan
 */
public interface CommerceOrderStatus {

	public CommerceOrder doTransition(
			CommerceOrder commerceOrder, long userId, boolean secure)
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