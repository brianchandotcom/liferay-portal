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

package com.liferay.portlet.dynamicdatalists.ddm;

import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.service.permission.DDLPermission;
import com.liferay.portlet.dynamicdatamapping.util.DDMPermissionHandler;

/**
 * @author Marcellus Tavares
 */
public class DDLDDMPermissionHandler implements DDMPermissionHandler {

	public static final long[] RESOURCE_CLASS_NAME_IDS = new long[] {
		PortalUtil.getClassNameId(DDLRecordSet.class)
	};

	@Override
	public String getAddStructureActionId() {
		return ActionKeys.ADD_STRUCTURE;
	}

	@Override
	public String getAddTemplateActionId() {
		return ActionKeys.ADD_TEMPLATE;
	}

	@Override
	public long[] getResourceClassNameIds() {
		return RESOURCE_CLASS_NAME_IDS;
	}

	@Override
	public String getResourceName(long classNameId) {
		return DDLPermission.RESOURCE_NAME;
	}

}