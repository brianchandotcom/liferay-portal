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

package com.liferay.commerce.context;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.portal.kernel.exception.PortalException;

import java.io.Serializable;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public interface CommerceContext extends Serializable {

	public String[] getAccountEntryAllowedTypes() throws PortalException;

	public CommerceAccount getCommerceAccount() throws PortalException;

	public long[] getCommerceAccountGroupIds() throws PortalException;

	public long getCommerceChannelGroupId() throws PortalException;

	public long getCommerceChannelId() throws PortalException;

	public CommerceCurrency getCommerceCurrency() throws PortalException;

	public CommerceOrder getCommerceOrder() throws PortalException;

	public int getCommerceSiteType();

}