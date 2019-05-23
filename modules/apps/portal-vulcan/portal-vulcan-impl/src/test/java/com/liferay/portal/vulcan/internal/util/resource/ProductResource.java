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

package com.liferay.portal.vulcan.internal.util.resource;

import com.liferay.portal.vulcan.internal.util.dto.ProductOption;
import com.liferay.portal.vulcan.internal.util.dto.Sku;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Date;
import java.util.List;

/**
 * @author Ivica Cardic
 */
public interface ProductResource {

	public List<ProductOption> getProductOptions(
		Long id, String name, Date createDate, Pagination pagination);

	public Page<Sku> getSkus(String id, Pagination pagination);

}