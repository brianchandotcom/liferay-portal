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

package com.liferay.commerce.order.importer.item;

import com.liferay.commerce.price.CommerceOrderItemPrice;

import java.util.Locale;

/**
 * @author Alessio Antonio Rendina
 */
public interface CommerceOrderImporterItem {

	public CommerceOrderItemPrice getCommerceOrderItemPrice();

	public long getCPDefinitionId();

	public long getCPInstanceId();

	public String[] getErrorMessages();

	public String getJSON();

	public String getName(Locale locale);

	public long getParentCommerceOrderItemCPDefinitionId();

	public int getQuantity();

	public String getReplacingSKU();

	public String getSKU();

	public boolean hasParentCommerceOrderItem();

	public boolean isValid();

}