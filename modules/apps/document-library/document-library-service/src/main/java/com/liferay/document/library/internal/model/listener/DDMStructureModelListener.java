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

package com.liferay.document.library.internal.model.listener;

import com.liferay.data.engine.service.DEDataDefinitionFieldLinkLocalService;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(service = ModelListener.class)
public class DDMStructureModelListener extends BaseModelListener<DDMStructure> {

	@Override
	public void onBeforeRemove(DDMStructure ddmStructure)
		throws ModelListenerException {

		try {
			_onBeforeRemove(ddmStructure);
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	private void _onBeforeRemove(DDMStructure ddmStructure)
		throws PortalException {

		long classNameId = ddmStructure.getClassNameId();

		if (classNameId != _portal.getClassNameId(DLFileEntryMetadata.class)) {
			return;
		}

		_deDataDefinitionFieldLinkLocalService.deleteDEDataDefinitionFieldLinks(
			_portal.getClassNameId(DDMStructure.class),
			ddmStructure.getStructureId());

		List<DDMStructureVersion> ddmStructureVersions =
			_ddmStructureVersionLocalService.getStructureVersions(
				ddmStructure.getStructureId());

		for (DDMStructureVersion ddmStructureVersion : ddmStructureVersions) {
			List<DDMStructureLayout> ddmStructureLayouts =
				_ddmStructureLayoutLocalService.getStructureLayouts(
					ddmStructure.getGroupId(), classNameId,
					ddmStructureVersion.getStructureVersionId());

			for (DDMStructureLayout ddmStructureLayout : ddmStructureLayouts) {
				_deDataDefinitionFieldLinkLocalService.
					deleteDEDataDefinitionFieldLinks(
						_portal.getClassNameId(DDMStructureLayout.class),
						ddmStructureLayout.getStructureLayoutId());
			}
		}

		_ddlRecordSetLocalService.deleteDDMStructureRecordSets(
			ddmStructure.getStructureId());
	}

	@Reference
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	@Reference
	private DDMStructureLayoutLocalService _ddmStructureLayoutLocalService;

	@Reference
	private DDMStructureVersionLocalService _ddmStructureVersionLocalService;

	@Reference
	private DEDataDefinitionFieldLinkLocalService
		_deDataDefinitionFieldLinkLocalService;

	@Reference
	private Portal _portal;

}