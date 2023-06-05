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

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileVersionLocalService;
import com.liferay.document.library.kernel.store.DLStoreUtil;
import com.liferay.document.library.kernel.util.DLProcessor;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.util.Objects;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = ModelListener.class)
public class DLFileVersionModelListener
	extends BaseModelListener<DLFileVersion> {

	@Override
	public void onAfterRemove(DLFileVersion dlFileVersion)
		throws ModelListenerException {

		try {
			DLFileEntry dlFileEntry = _dlFileEntryLocalService.fetchDLFileEntry(
				dlFileVersion.getFileEntryId());

			if (dlFileEntry != null) {
				DLStoreUtil.deleteFile(
					dlFileEntry.getCompanyId(),
					dlFileEntry.getDataRepositoryId(), dlFileEntry.getName(),
					dlFileVersion.getStoreFileName());
			}

		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}
	}

	@Override
	public void onBeforeRemove(DLFileVersion dlFileVersion)
		throws ModelListenerException {

		try {
			if (Objects.equals(
					DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION,
					dlFileVersion.getVersion())) {

				DLFileVersion latestFileVersion =
					_dlFileVersionLocalService.getLatestFileVersion(
						dlFileVersion.getFileEntryId(), true);

				FileVersion fileVersion = _dlAppLocalService.getFileVersion(
					latestFileVersion.getFileVersionId());

				for (DLProcessor dlProcessor :
						_dlProcessorServiceTrackerMap.values()) {

					if (dlProcessor.isSupported(fileVersion)) {
						dlProcessor.cleanUp(fileVersion);
					}
				}
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_dlProcessorServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, DLProcessor.class, "type");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLFileVersionModelListener.class);

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private DLFileVersionLocalService _dlFileVersionLocalService;

	private ServiceTrackerMap<String, DLProcessor>
		_dlProcessorServiceTrackerMap;

}