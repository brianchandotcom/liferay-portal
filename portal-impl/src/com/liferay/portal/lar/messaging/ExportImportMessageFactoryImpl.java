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

package com.liferay.portal.lar.messaging;

import com.liferay.portal.kernel.lar.messaging.ExportImportMessage;
import com.liferay.portal.kernel.lar.messaging.ExportImportMessageFactory;
import com.liferay.portal.kernel.lar.messaging.ExportImportMessageLevel;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.StagedModel;

import java.util.Date;

/**
 * @author Daniel Kocsis
 * @author Mate Thurzo
 */
public class ExportImportMessageFactoryImpl
	implements ExportImportMessageFactory {

	public ExportImportMessage getErrorExportImportMessage(
		StagedModel stagedModel, Exception exception) {

		return doGetExportImportMessage(
			stagedModel, exception, exception.getMessage(),
			ExportImportMessageLevel.ERROR);
	}

	public ExportImportMessage getErrorExportImportMessage(
		StagedModel stagedModel, String message) {

		return doGetExportImportMessage(
			stagedModel, null, message, ExportImportMessageLevel.ERROR);
	}

	public ExportImportMessage getErrorExportImportMessage(
		String portletId, Exception exception) {

		return doGetExportImportMessage(
			Portlet.class.getName(), portletId, exception,
			exception.getMessage(), ExportImportMessageLevel.ERROR);
	}

	public ExportImportMessage getErrorExportImportMessage(
		String portletId, String message) {

		return doGetExportImportMessage(
			Portlet.class.getName(), portletId, null, message,
			ExportImportMessageLevel.ERROR);
	}

	public ExportImportMessage getInfoExportImportMessage(
		StagedModel stagedModel, String message) {

		return doGetExportImportMessage(
			stagedModel, null, message, ExportImportMessageLevel.INFO);
	}

	public ExportImportMessage getInfoExportImportMessage(String message) {
		return doGetExportImportMessage(
			null, null, null, message, ExportImportMessageLevel.INFO);
	}

	public ExportImportMessage getInfoExportImportMessage(
		String portletId, String message) {

		return doGetExportImportMessage(
			Portlet.class.getName(), portletId, null, message,
			ExportImportMessageLevel.INFO);
	}

	public ExportImportMessage getWarningExportImportMessage(
		StagedModel stagedModel, String message) {

		return doGetExportImportMessage(
			stagedModel, null, message, ExportImportMessageLevel.WARNING);
	}

	public ExportImportMessage getWarningExportImportMessage(String message) {
		return doGetExportImportMessage(
			null, null, null, message, ExportImportMessageLevel.WARNING);
	}

	public ExportImportMessage getWarningExportImportMessage(
		String portletId, String message) {

		return doGetExportImportMessage(
			Portlet.class.getName(), portletId, null, message,
			ExportImportMessageLevel.WARNING);
	}

	protected ExportImportMessage doGetExportImportMessage(
		StagedModel stagedModel, Exception exception, String message,
		ExportImportMessageLevel messageLevel) {

		ClassedModel classedModel = (ClassedModel)stagedModel;

		String modelClassName = classedModel.getModelClassName();
		String modelClassPk = String.valueOf(classedModel.getPrimaryKeyObj());

		return doGetExportImportMessage(
			modelClassName, modelClassPk, exception, message, messageLevel);
	}

	protected ExportImportMessage doGetExportImportMessage(
		String className, String classPk, Exception exception, String message,
		ExportImportMessageLevel messageLevel) {

		return new ExportImportMessage(
			className, classPk, exception, message, messageLevel, new Date());
	}

}