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
 * Provides a utility facade to the factory for producing messages to used by
 * the export and import framework.
 *
 * The factory class can create 3 types of messages:
 *
 * <ul>
 * <li>
 * ERRROR
 * </li>
 * <li>
 * WARNING
 * </li>
 * <li>
 * INFO
 * </li>
 * </ul>
 *
 * These different message types can be found in the {@link
 * ExportImportMessageLevel} enumeration.
 *
 * The differently typed messages can contain several information like if the
 * system experiences an error message, the error message creational methods can
 * use an Exception object as well to provide possibility to process information
 * about the error.
 *
 * Also the messages can be produced based on a portlet ID or a {@link
 * com.liferay.portal.model.StagedModel}
 * object depending on which part of the process is sending the message. If a
 * portlet is sending the message then the methods with the portletId parameters
 * should be used. If a StagedModel export or import is in progress and the
 * message is coming from there the methods with the stagedModel parameters
 * should be used.
 *
 * The factory will extract the className and classPk information from the
 * StagedModel and use that in the future.
 *
 * @author Daniel Kocsis
 * @author Mate Thurzo
 * @see    ExportImportMessage
 * @see    ExportImportMessageLevel
 * @since  6.2
 */
public class ExportImportMessageFactoryUtil {

	/**
	 * Produces an {@link ExportImportMessage} with the level of ERROR, based on
	 * a StagedModel object and an Exception object. This method is useful when
	 * we want to broadcast an error message during a staged model export or
	 * import and we want to process other information from the exception
	 * besides the message. The Exception's message will be the default message
	 * in the produced object.
	 *
	 * @param  stagedModel the {@link com.liferay.portal.model.StagedModel} to
	 *         use to extract the className and classPk from
	 * @param  exception the exception object to pass on to the produced message
	 * @return the produced {@link ExportImportMessage} with an ERROR level and
	 *         an Exception object.
	 */
	public static ExportImportMessage getErrorMessage(
		StagedModel stagedModel, Exception exception) {

		return getExportImportMessageFactory().getErrorMessage(
			stagedModel, exception);
	}

	/**
	 * Produces an {@link ExportImportMessage} with the level of ERROR, based on
	 * a {@link com.liferay.portal.model.StagedModel} object and a custom
	 * message. This method is useful when we want to broadcast an error message
	 * during a staged model export or import and a custom message is being
	 * broadcasted without additional error information like an Exception.
	 *
	 * @param  stagedModel the {@link com.liferay.portal.model.StagedModel} to
	 *         use to extract the className and classPk from
	 * @param  message the custom message to be added to the produced object
	 * @return the produced {@link ExportImportMessage} with an ERROR level and
	 *         a custom message
	 */
	public static ExportImportMessage getErrorMessage(
		StagedModel stagedModel, String message) {

		return getExportImportMessageFactory().getErrorMessage(
			stagedModel, message);
	}

	/**
	 * Produces an {@link ExportImportMessage} with the level of ERROR, based on
	 * a portlet ID and an Exception object. This method is useful when we want
	 * to broadcast an error message during export or importing a portlet's data
	 * and we want to process other information from the exception besides the
	 * message. The Exception's message will be the default message in the
	 * produced object.
	 *
	 * @param  portletId the portlet ID to use for the produced {@link
	 *         ExportImportMessage}
	 * @param  exception the exception object to pass on to the produced {@link
	 *         ExportImportMessage}
	 * @return the produced {@link ExportImportMessage} with an ERROR level and
	 *         an Exception object.
	 */
	public static ExportImportMessage getErrorMessage(
		String portletId, Exception exception) {

		return getExportImportMessageFactory().getErrorMessage(
			portletId, exception);
	}

	/**
	 * Produces an {@link ExportImportMessage} with the level of ERROR, based on
	 * a portlet ID and an Exception object. This method is useful when we want
	 * to broadcast an error message during export or importing a portlet's data
	 * and a custom message is being broadcasted without additional error
	 * information like an Exception.
	 *
	 * @param  portletId the portlet ID to use for the produced {@link
	 *         ExportImportMessage}
	 * @param  message the custom message to be added to the produced object
	 * @return the produced {@link ExportImportMessage} with an ERROR level and
	 *         a custom message
	 */
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

	/**
	 * Produces an {@link ExportImportMessage} with the level of INFO, based on
	 * a {@link com.liferay.portal.model.StagedModel} object and a custom
	 * message. This method is useful when we want to broadcast an success
	 * message or any informational messages during a staged model export or
	 * import.
	 *
	 * @param  stagedModel the {@link com.liferay.portal.model.StagedModel} to
	 *         use to extract the className and classPk from
	 * @param  message the custom message to be added to the produced object
	 * @return the produced {@link ExportImportMessage} with an INFO level and a
	 *         custom message
	 */
	public static ExportImportMessage getInfoMessage(
		StagedModel stagedModel, String message) {

		return getExportImportMessageFactory().getInfoMessage(
			stagedModel, message);
	}

	/**
	 * Produces an {@link ExportImportMessage} with the level of INFO. This
	 * method is useful when a very generic message needs to be broadcasted in
	 * the system, with just a custom message in it.
	 *
	 * @param  message the custom message to be added to the produced object
	 * @return the produced {@link ExportImportMessage} with an INFO level and a
	 *         custom message
	 */
	public static ExportImportMessage getInfoMessage(String message) {
		return getExportImportMessageFactory().getInfoMessage(message);
	}

	/**
	 * Produces an {@link ExportImportMessage} with the level of INFO, based on
	 * a portlet ID and a custom message. This method is useful when we want to
	 * broadcast an success message or any informational messages during a
	 * portlet export or import process.
	 *
	 * @param  portletId the portlet ID to use for the produced {@link
	 *         ExportImportMessage}
	 * @param  message the custom message to be added to the produced object
	 * @return the produced {@link ExportImportMessage} with an INFO level and a
	 *         custom message
	 */
	public static ExportImportMessage getInfoMessage(
		String portletId, String message) {

		return getExportImportMessageFactory().getInfoMessage(
			portletId, message);
	}

	/**
	 * Produces an {@link ExportImportMessage} with the level of WARNING, based
	 * on a {@link com.liferay.portal.model.StagedModel} object and a custom
	 * message. This method is useful when we want to broadcast warning messages
	 * during a staged model export or import, which would inform the user about
	 * a minor problem.
	 *
	 * @param  stagedModel the {@link com.liferay.portal.model.StagedModel} to
	 *         use to extract the className and classPk from
	 * @param  message the custom message to be added to the produced object
	 * @return the produced {@link ExportImportMessage} with an WARNING level
	 *         and a custom message
	 */
	public static ExportImportMessage getWarningMessage(
		StagedModel stagedModel, String message) {

		return getExportImportMessageFactory().getWarningMessage(
			stagedModel, message);
	}

	/**
	 * Produces an {@link ExportImportMessage} with the level of WARNING. This
	 * method is useful when a very generic message needs to be broadcasted in
	 * the system, with just a custom message in it.
	 *
	 * @param  message the custom message to be added to the produced object
	 * @return the produced {@link ExportImportMessage} with an WARNING level
	 *         and a custom message
	 */
	public static ExportImportMessage getWarningMessage(String message) {
		return getExportImportMessageFactory().getWarningMessage(message);
	}

	/**
	 * Produces an {@link ExportImportMessage} with the level of WARNING, based
	 * on a portlet ID and a custom message. This method is useful when we want
	 * to broadcast warning messages during a portlet export or import process,
	 * which would inform the user about a minor problem.
	 *
	 * @param  portletId the portlet ID to use for the produced {@link
	 *         ExportImportMessage}
	 * @param  message the custom message to be added to the produced object
	 * @return the produced {@link ExportImportMessage} with an WARNING level
	 *         and a custom message
	 */
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