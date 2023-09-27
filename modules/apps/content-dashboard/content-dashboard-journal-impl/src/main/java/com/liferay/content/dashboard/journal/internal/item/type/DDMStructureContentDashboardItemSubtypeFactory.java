/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.journal.internal.item.type;

import com.liferay.content.dashboard.item.type.ContentDashboardItemSubtype;
import com.liferay.content.dashboard.item.type.ContentDashboardItemSubtypeFactory;
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
	public ContentDashboardItemSubtype<DDMStructure> create(
			long classPK, long entryClassPK)
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