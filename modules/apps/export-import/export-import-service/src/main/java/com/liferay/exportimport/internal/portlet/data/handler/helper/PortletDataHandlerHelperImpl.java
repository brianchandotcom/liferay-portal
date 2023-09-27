/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.portlet.data.handler.helper;

import com.liferay.exportimport.portlet.data.handler.helper.PortletDataHandlerHelper;
import com.liferay.portal.kernel.plugin.Version;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Máté Thurzó
 */
@Component(service = PortletDataHandlerHelper.class)
public class PortletDataHandlerHelperImpl implements PortletDataHandlerHelper {

	@Override
	public boolean validateSchemaVersion(
		String schemaVersion, String portletSchemaVersion) {

		// Major version has to be identical

		Version currentVersion = Version.getInstance(portletSchemaVersion);
		Version importedVersion = Version.getInstance(schemaVersion);

		if (!Objects.equals(
				currentVersion.getMajor(), importedVersion.getMajor())) {

			return false;
		}

		// Imported minor version should be less than or equal to the current
		// minor version

		int currentMinorVersion = GetterUtil.getInteger(
			currentVersion.getMinor(), -1);
		int importedMinorVersion = GetterUtil.getInteger(
			importedVersion.getMinor(), -1);

		if (((currentMinorVersion == -1) && (importedMinorVersion == -1)) ||
			(currentMinorVersion < importedMinorVersion)) {

			return false;
		}

		// Import should be compatible with all minor versions if previous
		// validations pass

		return true;
	}

}