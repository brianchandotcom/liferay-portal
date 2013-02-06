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

	public static ExportImportMessage getErrorExportImportMessage(
		StagedModel stagedModel, Exception exception) {

		return getExportImportMessageFactory().getErrorExportImportMessage(
			stagedModel, exception);
	}

	public static ExportImportMessage getErrorExportImportMessage(
		StagedModel stagedModel, String message) {

		return getExportImportMessageFactory().getErrorExportImportMessage(
			stagedModel, message);
	}

	public static ExportImportMessage getErrorExportImportMessage(
		String portletId, Exception exception) {

		return getExportImportMessageFactory().getErrorExportImportMessage(
			portletId, exception);
	}

	public static ExportImportMessage getErrorExportImportMessage(
		String portletId, String message) {

		return getExportImportMessageFactory().getErrorExportImportMessage(
			portletId, message);
	}

	public static ExportImportMessageFactory getExportImportMessageFactory() {
		PortalRuntimePermission.checkGetBeanProperty(
			ExportImportMessageFactoryUtil.class);

		return _exportImportMessageFactory;
	}

	public static ExportImportMessage getInfoExportImportMessage(
		StagedModel stagedModel, String message) {

		return getExportImportMessageFactory().getInfoExportImportMessage(
			stagedModel, message);
	}

	public static ExportImportMessage getInfoExportImportMessage(
		String message) {

		return getExportImportMessageFactory().getInfoExportImportMessage(
			message);
	}

	public static ExportImportMessage getInfoExportImportMessage(
		String portletId, String message) {

		return getExportImportMessageFactory().getInfoExportImportMessage(
			portletId, message);
	}

	public static ExportImportMessage getWarningExportImportMessage(
		StagedModel stagedModel, String message) {

		return getExportImportMessageFactory().getWarningExportImportMessage(
			stagedModel, message);
	}

	public static ExportImportMessage getWarningExportImportMessage(
		String message) {

		return getExportImportMessageFactory().getWarningExportImportMessage(
			message);
	}

	public static ExportImportMessage getWarningExportImportMessage(
		String portletId, String message) {

		return getExportImportMessageFactory().getWarningExportImportMessage(
			portletId, message);
	}

	public void setExportImportMessageFactory(
		ExportImportMessageFactory exportImportMessageFactory) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_exportImportMessageFactory = exportImportMessageFactory;
	}

	private static ExportImportMessageFactory _exportImportMessageFactory;

}