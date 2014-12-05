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

package com.liferay.portlet.documentlibrary.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class DLFileEntryMetadataFinderUtil {
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata> findByNoStructures() {
		return getFinder().findByNoStructures();
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata> findByMismatchedCompanyId() {
		return getFinder().findByMismatchedCompanyId();
	}

	public static DLFileEntryMetadataFinder getFinder() {
		if (_finder == null) {
			_finder = (DLFileEntryMetadataFinder)PortalBeanLocatorUtil.locate(DLFileEntryMetadataFinder.class.getName());

			ReferenceRegistry.registerReference(DLFileEntryMetadataFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(DLFileEntryMetadataFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(DLFileEntryMetadataFinderUtil.class,
			"_finder");
	}

	private static DLFileEntryMetadataFinder _finder;
}