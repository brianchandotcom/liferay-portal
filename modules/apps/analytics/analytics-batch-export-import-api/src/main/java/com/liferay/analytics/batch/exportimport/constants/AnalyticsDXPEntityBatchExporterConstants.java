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

package com.liferay.analytics.batch.exportimport.constants;

import com.liferay.portal.kernel.util.ArrayUtil;

/**
 * @author Marcos Martins
 */
public class AnalyticsDXPEntityBatchExporterConstants {

	public static final String
		DISPATCH_TRIGGER_NAME_ACCOUNT_ENTRY_DXP_ENTITIES =
			"export-account-entry-analytics-dxp-entities";

	public static final String DISPATCH_TRIGGER_NAME_ORDER =
		"analytics-upload-order";

	public static final String DISPATCH_TRIGGER_NAME_PRODUCT =
		"analytics-upload-product";

	public static final String DISPATCH_TRIGGER_NAME_USER_DXP_ENTITIES =
		"export-user-analytics-dxp-entities";

	public static final String[] DISPATCH_TRIGGER_NAMES = ArrayUtil.append(
		AnalyticsDXPEntityBatchExporterConstants.
			DISPATCH_TRIGGER_NAMES_DXP_ENTITIES,
		new String[] {
			DISPATCH_TRIGGER_NAME_ACCOUNT_ENTRY_DXP_ENTITIES,
			DISPATCH_TRIGGER_NAME_ORDER, DISPATCH_TRIGGER_NAME_PRODUCT,
			DISPATCH_TRIGGER_NAME_USER_DXP_ENTITIES
		});

	public static final String[] DISPATCH_TRIGGER_NAMES_DXP_ENTITIES = {
		"export-account-group-analytics-dxp-entities",
		"export-analytics-association-analytics-dxp-entities",
		"export-analytics-delete-message-analytics-dxp-entities",
		"export-expando-column-analytics-dxp-entities",
		"export-group-analytics-dxp-entities",
		"export-organization-analytics-dxp-entities",
		"export-role-analytics-dxp-entities",
		"export-team-analytics-dxp-entities",
		"export-user-group-analytics-dxp-entities"
	};

}