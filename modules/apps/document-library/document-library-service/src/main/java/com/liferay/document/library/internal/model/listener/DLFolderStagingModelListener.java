/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.model.listener;

import com.liferay.document.library.internal.helper.DLExportableRepositoryPublisherHelper;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.staging.model.listener.StagingModelListener;

import java.util.Collection;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(service = ModelListener.class)
public class DLFolderStagingModelListener extends BaseModelListener<DLFolder> {

	@Override
	public void onAfterCreate(DLFolder dlFolder) throws ModelListenerException {
		Collection<Long> exportableRepositoryIds =
			_dlExportableRepositoryPublisherHelper.publish(
				dlFolder.getGroupId());

		if (!exportableRepositoryIds.contains(dlFolder.getRepositoryId())) {
			return;
		}

		_stagingModelListener.onAfterCreate(dlFolder);
	}

	@Override
	public void onAfterRemove(DLFolder dlFolder) throws ModelListenerException {
		_stagingModelListener.onAfterRemove(dlFolder);
	}

	@Override
	public void onAfterUpdate(DLFolder originalDLFolder, DLFolder dlFolder)
		throws ModelListenerException {

		Collection<Long> exportableRepositoryIds =
			_dlExportableRepositoryPublisherHelper.publish(
				dlFolder.getGroupId());

		if (!exportableRepositoryIds.contains(dlFolder.getRepositoryId())) {
			return;
		}

		_stagingModelListener.onAfterUpdate(dlFolder);
	}

	@Reference
	private DLExportableRepositoryPublisherHelper
		_dlExportableRepositoryPublisherHelper;

	@Reference
	private StagingModelListener<DLFolder> _stagingModelListener;

}