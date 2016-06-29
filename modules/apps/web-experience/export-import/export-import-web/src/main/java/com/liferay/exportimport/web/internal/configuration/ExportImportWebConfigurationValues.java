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

package com.liferay.exportimport.web.internal.configuration;

import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Daniel Kocsis
 */
public class ExportImportWebConfigurationValues {

	public static final int DRAFT_EXPORT_IMPORT_CONFIGURATION_CHECK_INTERVAL =
		GetterUtil.getInteger(
			ExportImportWebConfigurationUtil.get(
				"draft.export.import.configuration.check.interval"));

	public static int DRAFT_EXPORT_IMPORT_CONFIGURATION_CLEAN_UP_COUNT =
		GetterUtil.getInteger(
			ExportImportWebConfigurationUtil.get(
				"draft.export.import.configuration.clean.up.count"));

}