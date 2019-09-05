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