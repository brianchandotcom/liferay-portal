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

package com.liferay.document.library.kernel.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

/**
 * @author Roberto Díaz
 */
public class DLFileEntryPreviewHandlerUtil {

	public static void addDLFileEntryPreview(
			long fileEntryId, long fileVersionId,
			DLFileEntryPreviewHandler.DLFileEntryPreviewType
				fileEntryPreviewType)
		throws PortalException {

		DLFileEntryPreviewHandler dlFileEntryPreviewHandler =
			getDLFileEntryPreviewHandler();

		if (dlFileEntryPreviewHandler != null) {
			dlFileEntryPreviewHandler.addDLFileEntryPreview(
				fileEntryId, fileVersionId, fileEntryPreviewType);
		}
	}

	public static void addFailDLFileEntryPreview(
			long fileEntryId, long fileVersionId)
		throws PortalException {

		addDLFileEntryPreview(
			fileEntryId, fileVersionId,
			DLFileEntryPreviewHandler.DLFileEntryPreviewType.FAIL);
	}

	public static void addNotGeneratedDLFileEntryPreview(
			long fileEntryId, long fileVersionId)
		throws PortalException {

		addDLFileEntryPreview(
			fileEntryId, fileVersionId,
			DLFileEntryPreviewHandler.DLFileEntryPreviewType.NOT_GENERATED);
	}

	public static void addSuccessDLFileEntryPreview(
			long fileEntryId, long fileVersionId)
		throws PortalException {

		addDLFileEntryPreview(
			fileEntryId, fileVersionId,
			DLFileEntryPreviewHandler.DLFileEntryPreviewType.SUCCESS);
	}

	public static void deleteDLFileEntryPreviews(long fileEntryId)
		throws PortalException {

		DLFileEntryPreviewHandler dlFileEntryPreviewHandler =
			getDLFileEntryPreviewHandler();

		if (dlFileEntryPreviewHandler != null) {
			dlFileEntryPreviewHandler.deleteDLFileEntryPreviews(fileEntryId);
		}
	}

	public static long getDLFileEntryPreviewId(
			long fileEntryId, long fileVersionId)
		throws PortalException {

		DLFileEntryPreviewHandler dlFileEntryPreviewHandler =
			getDLFileEntryPreviewHandler();

		if (dlFileEntryPreviewHandler != null) {
			return dlFileEntryPreviewHandler.getDLFileEntryPreviewId(
				fileEntryId, fileVersionId);
		}

		return 0;
	}

	public static long getDLFileEntryPreviewId(
			long fileEntryId, long fileVersionId,
			DLFileEntryPreviewHandler.DLFileEntryPreviewType
				fileEntryPreviewType)
		throws PortalException {

		DLFileEntryPreviewHandler dlFileEntryPreviewHandler =
			getDLFileEntryPreviewHandler();

		if (dlFileEntryPreviewHandler != null) {
			return dlFileEntryPreviewHandler.getDLFileEntryPreviewId(
				fileEntryId, fileVersionId, fileEntryPreviewType);
		}

		return 0;
	}

	public static int getDLFileEntryPreviewType(
		DLFileEntryPreviewHandler.DLFileEntryPreviewType fileEntryPreviewType) {

		if (fileEntryPreviewType ==
				DLFileEntryPreviewHandler.DLFileEntryPreviewType.FAIL) {

			return -1;
		}
		else if (fileEntryPreviewType ==
					DLFileEntryPreviewHandler.
						DLFileEntryPreviewType.NOT_GENERATED) {

			return 0;
		}
		else if (fileEntryPreviewType ==
					DLFileEntryPreviewHandler.DLFileEntryPreviewType.SUCCESS) {

			return 1;
		}

		return 0;
	}

	public static void updateDLFileEntryPreview(
			long dlFileEntryPreviewId,
			DLFileEntryPreviewHandler.DLFileEntryPreviewType
				fileEntryPreviewType)
		throws PortalException {

		DLFileEntryPreviewHandler dlFileEntryPreviewHandler =
			getDLFileEntryPreviewHandler();

		if (dlFileEntryPreviewHandler != null) {
			dlFileEntryPreviewHandler.updateDLFileEntryPreview(
				dlFileEntryPreviewId, fileEntryPreviewType);
		}
	}

	protected static DLFileEntryPreviewHandler getDLFileEntryPreviewHandler() {
		return _instance._serviceTracker.getService();
	}

	private DLFileEntryPreviewHandlerUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			DLFileEntryPreviewHandler.class);

		_serviceTracker.open();
	}

	private static final DLFileEntryPreviewHandlerUtil _instance =
		new DLFileEntryPreviewHandlerUtil();

	private final ServiceTracker
		<DLFileEntryPreviewHandler, DLFileEntryPreviewHandler> _serviceTracker;

}