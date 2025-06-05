/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.vulcan.batch.engine;

import com.liferay.portal.vulcan.batch.engine.VulcanBatchEngineTaskItemDelegate;

/**
 * @author Alejandro Tardín
 */
public interface ExportImportVulcanBatchEngineTaskItemDelegate<T>
	extends VulcanBatchEngineTaskItemDelegate<T> {

	public String getPortletId();

	public Scope getScope();

	public enum Scope {

		COMPANY, SITE

	}

}