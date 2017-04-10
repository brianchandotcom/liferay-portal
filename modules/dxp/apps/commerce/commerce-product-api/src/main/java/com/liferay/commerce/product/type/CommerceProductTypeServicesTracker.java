/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.product.type;

import aQute.bnd.annotation.ProviderType;

import java.util.List;
import java.util.Set;

/**
 * @author Marco Leo
 */
@ProviderType
public interface CommerceProductTypeServicesTracker {

	public CommerceProductType getCommerceProductType(String name);

	public Set<String> getCommerceProductTypeNames();

	public List<CommerceProductType> getCommerceProductTypes();

}