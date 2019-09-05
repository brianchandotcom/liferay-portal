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

package com.liferay.dynamic.data.mapping.service.http;

import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>DDMFormInstanceRecordServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord</code>, that is translated to a
 * <code>com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordSoap</code>. Methods that SOAP
 * cannot safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMFormInstanceRecordServiceHttp
 * @generated
 */
public class DDMFormInstanceRecordServiceSoap {

	public static
		com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordSoap
				addFormInstanceRecord(
					long groupId, long ddmFormInstanceId,
					com.liferay.dynamic.data.mapping.storage.DDMFormValues
						ddmFormValues,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws RemoteException {

		try {
			com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord
				returnValue =
					DDMFormInstanceRecordServiceUtil.addFormInstanceRecord(
						groupId, ddmFormInstanceId, ddmFormValues,
						serviceContext);

			return com.liferay.dynamic.data.mapping.model.
				DDMFormInstanceRecordSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteFormInstanceRecord(long ddmFormInstanceRecordId)
		throws RemoteException {

		try {
			DDMFormInstanceRecordServiceUtil.deleteFormInstanceRecord(
				ddmFormInstanceRecordId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static
		com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordSoap
				getFormInstanceRecord(long ddmFormInstanceRecordId)
			throws RemoteException {

		try {
			com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord
				returnValue =
					DDMFormInstanceRecordServiceUtil.getFormInstanceRecord(
						ddmFormInstanceRecordId);

			return com.liferay.dynamic.data.mapping.model.
				DDMFormInstanceRecordSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static
		com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordSoap[]
				getFormInstanceRecords(long ddmFormInstanceId)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord>
					returnValue =
						DDMFormInstanceRecordServiceUtil.getFormInstanceRecords(
							ddmFormInstanceId);

			return com.liferay.dynamic.data.mapping.model.
				DDMFormInstanceRecordSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static
		com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordSoap[]
				getFormInstanceRecords(
					long ddmFormInstanceId, int status, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.dynamic.data.mapping.model.
							DDMFormInstanceRecord> orderByComparator)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord>
					returnValue =
						DDMFormInstanceRecordServiceUtil.getFormInstanceRecords(
							ddmFormInstanceId, status, start, end,
							orderByComparator);

			return com.liferay.dynamic.data.mapping.model.
				DDMFormInstanceRecordSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFormInstanceRecordsCount(long ddmFormInstanceId)
		throws RemoteException {

		try {
			int returnValue =
				DDMFormInstanceRecordServiceUtil.getFormInstanceRecordsCount(
					ddmFormInstanceId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void revertFormInstanceRecord(
			long ddmFormInstanceRecordId, String version,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			DDMFormInstanceRecordServiceUtil.revertFormInstanceRecord(
				ddmFormInstanceRecordId, version, serviceContext);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static
		com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordSoap
				updateFormInstanceRecord(
					long ddmFormInstanceRecordId, boolean majorVersion,
					com.liferay.dynamic.data.mapping.storage.DDMFormValues
						ddmFormValues,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws RemoteException {

		try {
			com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord
				returnValue =
					DDMFormInstanceRecordServiceUtil.updateFormInstanceRecord(
						ddmFormInstanceRecordId, majorVersion, ddmFormValues,
						serviceContext);

			return com.liferay.dynamic.data.mapping.model.
				DDMFormInstanceRecordSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DDMFormInstanceRecordServiceSoap.class);

}