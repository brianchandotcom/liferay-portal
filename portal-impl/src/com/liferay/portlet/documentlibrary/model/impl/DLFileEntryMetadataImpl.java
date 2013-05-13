/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;

/**
 * @author Alexander Chow
 */
public class DLFileEntryMetadataImpl extends DLFileEntryMetadataBaseImpl {

	public DLFileEntryMetadataImpl() {
	}

	@Override
	public DDMStructure getDDMStructure()
		throws PortalException, SystemException {

		return DDMStructureLocalServiceUtil.getStructure(getDDMStructureId());
	}

	@Override
	public DLFileEntryType getFileEntryType()
		throws PortalException, SystemException {

		return DLFileEntryTypeLocalServiceUtil.getFileEntryType(
			getFileEntryTypeId());
	}

	@Override
	public DLFileVersion getFileVersion()
		throws PortalException, SystemException {

		return DLFileVersionLocalServiceUtil.getFileVersion(getFileVersionId());
	}

}