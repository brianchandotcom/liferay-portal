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

package com.liferay.commerce.order.rule.type;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.rule.model.COREntry;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Locale;

/**
 * @author Luca Pellizzon
 */
public interface COREntryType {

	public boolean evaluate(COREntry corEntry, CommerceOrder commerceOrder)
		throws PortalException;

	public String getErrorMessage(
			COREntry corEntry, CommerceOrder commerceOrder, Locale locale)
		throws PortalException;

	public String getKey();

	public String getLabel(Locale locale);

}