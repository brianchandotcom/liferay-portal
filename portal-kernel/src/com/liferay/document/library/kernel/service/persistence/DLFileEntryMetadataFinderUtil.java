/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DLFileEntryMetadataFinderUtil {

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntryMetadata>
			findByMismatchedCompanyId() {

		return getFinder().findByMismatchedCompanyId();
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntryMetadata>
			findByNoStructures() {

		return getFinder().findByNoStructures();
	}

	public static DLFileEntryMetadataFinder getFinder() {
		if (_finder == null) {
			_finder = (DLFileEntryMetadataFinder)PortalBeanLocatorUtil.locate(
				DLFileEntryMetadataFinder.class.getName());
		}

		return _finder;
	}

	public void setFinder(DLFileEntryMetadataFinder finder) {
		_finder = finder;
	}

	private static DLFileEntryMetadataFinder _finder;

}