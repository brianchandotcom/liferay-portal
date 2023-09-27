/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.batch.exportimport.constants;

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