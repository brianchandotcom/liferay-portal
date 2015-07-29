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

package com.liferay.document.library.ddm;

import com.liferay.portal.kernel.metadata.RawMetadataProcessor;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.service.permission.DLPermission;
import com.liferay.portlet.dynamicdatamapping.util.DDMPermissionHandler;

/**
 * @author Marcellus Tavares
 */
public class DLDDMPermissionHandler implements DDMPermissionHandler {

	public static final long[] RESOURCE_CLASS_NAME_IDS = new long[] {
		PortalUtil.getClassNameId(DLFileEntryMetadata.class),
		PortalUtil.getClassNameId(RawMetadataProcessor.class)
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
		return DLPermission.RESOURCE_NAME;
	}

}