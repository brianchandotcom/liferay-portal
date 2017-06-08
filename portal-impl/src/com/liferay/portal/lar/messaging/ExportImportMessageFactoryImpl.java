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
 * Implementing class for the export import message factory.
 *
 * @author Daniel Kocsis
 * @author Mate Thurzo
 * @see    com.liferay.portal.kernel.lar.messaging.ExportImportMessage
 * @see    com.liferay.portal.kernel.lar.messaging.ExportImportMessageFactoryUtil
 * @since  6.2
 */
public class ExportImportMessageFactoryImpl
	implements ExportImportMessageFactory {

	public ExportImportMessage getErrorMessage(
		StagedModel stagedModel, Exception exception) {

		return doGetMessage(
			stagedModel, exception, exception.getMessage(),
			ExportImportMessageLevel.ERROR);
	}

	public ExportImportMessage getErrorMessage(
		StagedModel stagedModel, String message) {

		return doGetMessage(
			stagedModel, null, message, ExportImportMessageLevel.ERROR);
	}

	public ExportImportMessage getErrorMessage(
		String portletId, Exception exception) {

		return doGetMessage(
			Portlet.class.getName(), portletId, exception,
			exception.getMessage(), ExportImportMessageLevel.ERROR);
	}

	public ExportImportMessage getErrorMessage(
		String portletId, String message) {

		return doGetMessage(
			Portlet.class.getName(), portletId, null, message,
			ExportImportMessageLevel.ERROR);
	}

	public ExportImportMessage getInfoMessage(
		StagedModel stagedModel, String message) {

		return doGetMessage(
			stagedModel, null, message, ExportImportMessageLevel.INFO);
	}

	public ExportImportMessage getInfoMessage(String message) {
		return doGetMessage(
			null, null, null, message, ExportImportMessageLevel.INFO);
	}

	public ExportImportMessage getInfoMessage(
		String portletId, String message) {

		return doGetMessage(
			Portlet.class.getName(), portletId, null, message,
			ExportImportMessageLevel.INFO);
	}

	public ExportImportMessage getWarningMessage(
			StagedModel stagedModel, String message) {

		return doGetMessage(
			stagedModel, null, message, ExportImportMessageLevel.WARNING);
	}

	public ExportImportMessage getWarningMessage(String message) {
		return doGetMessage(
			null, null, null, message, ExportImportMessageLevel.WARNING);
	}

	public ExportImportMessage getWarningMessage(
		String portletId, String message) {

		return doGetMessage(
			Portlet.class.getName(), portletId, null, message,
			ExportImportMessageLevel.WARNING);
	}

	protected ExportImportMessage doGetMessage(
		StagedModel stagedModel, Exception exception, String message,
		ExportImportMessageLevel messageLevel) {

		ClassedModel classedModel = (ClassedModel)stagedModel;

		String modelClassName = classedModel.getModelClassName();
		String modelClassPk = String.valueOf(classedModel.getPrimaryKeyObj());

		return doGetMessage(
			modelClassName, modelClassPk, exception, message, messageLevel);
	}

	protected ExportImportMessage doGetMessage(
		String className, String classPk, Exception exception, String message,
		ExportImportMessageLevel messageLevel) {

		return new ExportImportMessage(
			className, classPk, exception, message, messageLevel, new Date());
	}

}