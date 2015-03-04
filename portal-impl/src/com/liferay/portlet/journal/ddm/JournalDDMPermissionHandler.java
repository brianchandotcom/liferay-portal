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

package com.liferay.portlet.journal.ddm;

import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMPermissionHandler;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.permission.JournalPermission;

/**
 * @author Marcellus Tavares
 */
public class JournalDDMPermissionHandler implements DDMPermissionHandler {

	public static final long[] RESOURCE_CLASS_NAME_IDS = new long[] {
		PortalUtil.getClassNameId(JournalArticle.class)
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
		return JournalPermission.RESOURCE_NAME;
	}

}