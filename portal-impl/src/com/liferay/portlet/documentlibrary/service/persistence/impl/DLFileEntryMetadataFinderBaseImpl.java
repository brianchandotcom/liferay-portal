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

package com.liferay.portlet.documentlibrary.service.persistence.impl;

import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.service.persistence.DLFileEntryMetadataPersistence;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DLFileEntryMetadataFinderBaseImpl
	extends BasePersistenceImpl<DLFileEntryMetadata> {

	public DLFileEntryMetadataFinderBaseImpl() {
		setModelClass(DLFileEntryMetadata.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);
	}

	@Override
	public Set<String> getBadColumnNames() {
		return getDLFileEntryMetadataPersistence().getBadColumnNames();
	}

	/**
	 * Returns the document library file entry metadata persistence.
	 *
	 * @return the document library file entry metadata persistence
	 */
	public DLFileEntryMetadataPersistence getDLFileEntryMetadataPersistence() {
		return dlFileEntryMetadataPersistence;
	}

	/**
	 * Sets the document library file entry metadata persistence.
	 *
	 * @param dlFileEntryMetadataPersistence the document library file entry metadata persistence
	 */
	public void setDLFileEntryMetadataPersistence(
		DLFileEntryMetadataPersistence dlFileEntryMetadataPersistence) {

		this.dlFileEntryMetadataPersistence = dlFileEntryMetadataPersistence;
	}

	@BeanReference(type = DLFileEntryMetadataPersistence.class)
	protected DLFileEntryMetadataPersistence dlFileEntryMetadataPersistence;

	private static final Log _log = LogFactoryUtil.getLog(
		DLFileEntryMetadataFinderBaseImpl.class);

}