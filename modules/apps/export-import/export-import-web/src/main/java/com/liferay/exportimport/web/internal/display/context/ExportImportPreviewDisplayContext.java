/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.web.internal.display.context;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Group;

/**
 * @author Daniel Raposo
 */
public class ExportImportPreviewDisplayContext {

	public ExportImportPreviewDisplayContext(Group group) {
		_group = group;
	}

	public String getImportPreviewURL() {
		if (_group.isDepot()) {
			return StringBundler.concat(
				"/o/export-import/v1.0/asset-libraries/",
				_group.getExternalReferenceCode(), "/import-preview");
		}

		if (_group.isSite()) {
			return StringBundler.concat(
				"/o/export-import/v1.0/sites/",
				_group.getExternalReferenceCode(), "/import-preview");
		}

		return "/o/export-import/v1.0/import-preview";
	}

	private final Group _group;

}