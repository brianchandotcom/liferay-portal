/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.lar.messaging;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.model.StagedModel;

/**
 * @author Daniel Kocsis
 * @author Mate Thurzo
 */
public class ExportImportMessageFactoryUtil {

	public static ExportImportMessage getErrorMessage(
		StagedModel stagedModel, Exception exception) {

		return getExportImportMessageFactory().getErrorMessage(
			stagedModel, exception);
	}

	public static ExportImportMessage getErrorMessage(
		StagedModel stagedModel, String message) {

		return getExportImportMessageFactory().getErrorMessage(
			stagedModel, message);
	}

	public static ExportImportMessage getErrorMessage(
		String portletId, Exception exception) {

		return getExportImportMessageFactory().getErrorMessage(
			portletId, exception);
	}

	public static ExportImportMessage getErrorMessage(
		String portletId, String message) {

		return getExportImportMessageFactory().getErrorMessage(
			portletId, message);
	}

	public static ExportImportMessageFactory getExportImportMessageFactory() {
		PortalRuntimePermission.checkGetBeanProperty(
			ExportImportMessageFactoryUtil.class);

		return _exportImportMessageFactory;
	}

	public static ExportImportMessage getInfoMessage(
		StagedModel stagedModel, String message) {

		return getExportImportMessageFactory().getInfoMessage(
			stagedModel, message);
	}

	public static ExportImportMessage getInfoMessage(String message) {
		return getExportImportMessageFactory().getInfoMessage(message);
	}

	public static ExportImportMessage getInfoMessage(
		String portletId, String message) {

		return getExportImportMessageFactory().getInfoMessage(
			portletId, message);
	}

	public static ExportImportMessage getWarningMessage(
		StagedModel stagedModel, String message) {

		return getExportImportMessageFactory().getWarningMessage(
			stagedModel, message);
	}

	public static ExportImportMessage getWarningMessage(String message) {
		return getExportImportMessageFactory().getWarningMessage(message);
	}

	public static ExportImportMessage getWarningMessage(
		String portletId, String message) {

		return getExportImportMessageFactory().getWarningMessage(
			portletId, message);
	}

	public void setExportImportMessageFactory(
		ExportImportMessageFactory exportImportMessageFactory) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_exportImportMessageFactory = exportImportMessageFactory;
	}

	private static ExportImportMessageFactory _exportImportMessageFactory;

}