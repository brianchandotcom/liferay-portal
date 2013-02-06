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

import com.liferay.portal.model.StagedModel;

/**
 * @author Daniel Kocsis
 * @author Mate Thurzo
 */
public interface ExportImportMessageFactory {

	public ExportImportMessage getErrorExportImportMessage(
		StagedModel stagedModel, Exception exception);

	public ExportImportMessage getErrorExportImportMessage(
		StagedModel stagedModel, String message);

	public ExportImportMessage getErrorExportImportMessage(
		String portletId, Exception exception);

	public ExportImportMessage getErrorExportImportMessage(
		String portletId, String message);

	public ExportImportMessage getInfoExportImportMessage(
		StagedModel stagedModel, String message);

	public ExportImportMessage getInfoExportImportMessage(String message);

	public ExportImportMessage getInfoExportImportMessage(
		String portletId, String message);

	public ExportImportMessage getWarningExportImportMessage(
		StagedModel stagedModel, String message);

	public ExportImportMessage getWarningExportImportMessage(String message);

	public ExportImportMessage getWarningExportImportMessage(
		String portletId, String message);

}