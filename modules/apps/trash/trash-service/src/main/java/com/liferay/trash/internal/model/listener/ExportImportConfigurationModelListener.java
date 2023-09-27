/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.trash.internal.model.listener;

import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.trash.service.TrashEntryLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = ModelListener.class)
public class ExportImportConfigurationModelListener
	extends BaseModelListener<ExportImportConfiguration> {

	@Override
	public void onBeforeRemove(
			ExportImportConfiguration exportImportConfiguration)
		throws ModelListenerException {

		_trashEntryLocalService.deleteEntry(
			ExportImportConfiguration.class.getName(),
			exportImportConfiguration.getExportImportConfigurationId());
	}

	@Reference
	private TrashEntryLocalService _trashEntryLocalService;

}