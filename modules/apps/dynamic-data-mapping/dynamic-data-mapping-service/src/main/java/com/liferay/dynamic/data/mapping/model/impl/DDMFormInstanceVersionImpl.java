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

package com.liferay.dynamic.data.mapping.model.impl;

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class DDMFormInstanceVersionImpl extends DDMFormInstanceVersionBaseImpl {

	@Override
	public DDMFormInstance getFormInstance() throws PortalException {
		return DDMFormInstanceLocalServiceUtil.getFormInstance(
			getFormInstanceId());
	}

	@Override
	public DDMStructureVersion getStructureVersion() throws PortalException {
		return DDMStructureVersionLocalServiceUtil.getDDMStructureVersion(
			getStructureVersionId());
	}

}