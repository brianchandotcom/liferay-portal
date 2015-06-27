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

package com.liferay.dynamic.data.lists.web.configuration;

import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Rafael Praxedes
 */
public class DDLWebConfigurationValues {
	
	public static final boolean DYNAMIC_DATA_LISTS_RECORD_SET_FORCE_AUTOGENERATE_KEY =
			GetterUtil.getBoolean(
				DDLWebConfigurationUtil.get(
					"dynamic.data.lists.record.set.force.autogenerate.key"));

	public static final String DYNAMIC_DATA_LISTS_ERROR_TEMPLATE =
		"dynamic.data.lists.error.template";
	
	public static final String DYNAMIC_DATA_LISTS_STORAGE_TYPE =
			GetterUtil.getString(
					DDLWebConfigurationUtil.get(
							"dynamic.data.lists.storage.type"));


}