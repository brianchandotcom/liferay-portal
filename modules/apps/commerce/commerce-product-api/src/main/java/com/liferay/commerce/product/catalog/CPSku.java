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

package com.liferay.commerce.product.catalog;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Alessio Antonio Rendina
 */
public interface CPSku {

	public long getCPInstanceId();

	public String getCPInstanceUuid();

	public String getExternalReferenceCode();

	public String getGtin();

	public String getManufacturerPartNumber();

	public BigDecimal getPrice();

	public BigDecimal getPromoPrice();

	public String getReplacementCPInstanceUuid();

	public long getReplacementCProductId();

	public String getSku();

	public Date getDiscontinuedDate();

	public boolean isDiscontinued();

	public boolean isPublished();

	public boolean isPurchasable();

}