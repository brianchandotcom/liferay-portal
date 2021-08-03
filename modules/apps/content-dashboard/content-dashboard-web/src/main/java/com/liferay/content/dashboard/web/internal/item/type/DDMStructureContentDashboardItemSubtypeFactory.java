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

package com.liferay.content.dashboard.web.internal.item.type;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.GroupLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = ContentDashboardItemSubtypeFactory.class)
public class DDMStructureContentDashboardItemSubtypeFactory
	implements ContentDashboardItemSubtypeFactory<DDMStructure> {

	@Override
	public ContentDashboardItemSubtype<DDMStructure> create(long classPK)
		throws PortalException {

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			classPK);

		return new DDMStructureContentDashboardItemSubtype(
			ddmStructure,
			_groupLocalService.fetchGroup(ddmStructure.getGroupId()));
	}

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

}