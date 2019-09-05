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

package com.liferay.dynamic.data.lists.model.impl;

import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalServiceUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Leonardo Barros
 */
public class DDLRecordSetVersionImpl extends DDLRecordSetVersionBaseImpl {

	@Override
	public DDMStructureVersion getDDMStructureVersion() throws PortalException {
		return DDMStructureVersionLocalServiceUtil.getDDMStructureVersion(
			getDDMStructureVersionId());
	}

	@Override
	public DDLRecordSet getRecordSet() throws PortalException {
		return DDLRecordSetLocalServiceUtil.getRecordSet(getRecordSetId());
	}

}