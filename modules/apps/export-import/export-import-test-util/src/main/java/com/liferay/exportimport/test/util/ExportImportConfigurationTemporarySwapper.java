/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.test.util;

import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.lazy.referencing.LazyReferencingThreadLocal;

/**
 * @author Carolina Barbosa
 */
public class ExportImportConfigurationTemporarySwapper
	implements AutoCloseable {

	public ExportImportConfigurationTemporarySwapper(
			long exportImportConfigurationId)
		throws Exception {

		_lazyReferencingSafeCloseable =
			LazyReferencingThreadLocal.setEnabledWithSafeCloseable(true);
		_originalExportImportConfigurationId =
			ExportImportThreadLocal.getExportImportConfigurationId();
		_originalPortletImportInProcess =
			ExportImportThreadLocal.isPortletImportInProcess();

		ExportImportThreadLocal.setExportImportConfigurationId(
			exportImportConfigurationId);
		ExportImportThreadLocal.setPortletImportInProcess(true);
	}

	@Override
	public void close() throws Exception {
		if (_lazyReferencingSafeCloseable != null) {
			_lazyReferencingSafeCloseable.close();
		}

		ExportImportThreadLocal.setExportImportConfigurationId(
			_originalExportImportConfigurationId);
		ExportImportThreadLocal.setPortletImportInProcess(
			_originalPortletImportInProcess);
	}

	private final SafeCloseable _lazyReferencingSafeCloseable;
	private final long _originalExportImportConfigurationId;
	private final boolean _originalPortletImportInProcess;

}