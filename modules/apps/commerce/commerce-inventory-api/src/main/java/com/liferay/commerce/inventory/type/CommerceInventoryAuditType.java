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

package com.liferay.commerce.inventory.type;

import java.util.Locale;
import java.util.Map;

/**
 * @author Alessio Antonio Rendina
 * @author Luca Pellizzon
 */
public interface CommerceInventoryAuditType {

	public String formatLog(long userId, String context, Locale locale)
		throws Exception;

	public String formatQuantity(int quantity, Locale locale);

	public String getLog(Map<String, String> context);

	public String getType();

}