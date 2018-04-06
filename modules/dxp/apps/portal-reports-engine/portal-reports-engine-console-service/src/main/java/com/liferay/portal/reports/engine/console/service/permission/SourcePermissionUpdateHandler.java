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

package com.liferay.portal.reports.engine.console.service.permission;

import com.liferay.portal.kernel.security.permission.PermissionUpdateHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.reports.engine.console.model.Source;
import com.liferay.portal.reports.engine.console.service.SourceLocalService;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Prathima Shreenath
 */
@Component(
	property = "model.class.name=com.liferay.portal.reports.engine.console.model.Source",
	service = PermissionUpdateHandler.class
)
public class SourcePermissionUpdateHandler implements PermissionUpdateHandler {

	@Override
	public void updatedPermission(String primKey) {
		Source source = _sourceLocalService.fetchSource(
			GetterUtil.getLong(primKey));

		if (source == null) {
			return;
		}

		source.setModifiedDate(new Date());

		_sourceLocalService.updateSource(source);
	}

	@Reference
	private SourceLocalService _sourceLocalService;

}