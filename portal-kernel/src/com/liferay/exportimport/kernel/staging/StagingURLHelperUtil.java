/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.staging;

import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.util.UnicodeProperties;

/**
 * @author Akos Thurzo
 */
public class StagingURLHelperUtil {

	public static String buildRemoteURL(
		ExportImportConfiguration exportImportConfiguration) {

		StagingURLHelper stagingURLHelper = _stagingURLHelperSnapshot.get();

		return stagingURLHelper.buildRemoteURL(exportImportConfiguration);
	}

	public static String buildRemoteURL(
		String remoteAddress, int remotePort, String remotePathContext,
		boolean secureConnection) {

		StagingURLHelper stagingURLHelper = _stagingURLHelperSnapshot.get();

		return stagingURLHelper.buildRemoteURL(
			remoteAddress, remotePort, remotePathContext, secureConnection);
	}

	public static String buildRemoteURL(
		UnicodeProperties typeSettingsUnicodeProperties) {

		StagingURLHelper stagingURLHelper = _stagingURLHelperSnapshot.get();

		return stagingURLHelper.buildRemoteURL(typeSettingsUnicodeProperties);
	}

	private static final Snapshot<StagingURLHelper> _stagingURLHelperSnapshot =
		new Snapshot<>(StagingURLHelperUtil.class, StagingURLHelper.class);

}