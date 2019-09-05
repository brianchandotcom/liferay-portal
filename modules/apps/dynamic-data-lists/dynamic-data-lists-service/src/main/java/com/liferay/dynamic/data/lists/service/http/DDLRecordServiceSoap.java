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

package com.liferay.dynamic.data.lists.service.http;

import com.liferay.dynamic.data.lists.service.DDLRecordServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>DDLRecordServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.dynamic.data.lists.model.DDLRecordSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.dynamic.data.lists.model.DDLRecord</code>, that is translated to a
 * <code>com.liferay.dynamic.data.lists.model.DDLRecordSoap</code>. Methods that SOAP
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
 * @see DDLRecordServiceHttp
 * @generated
 */
public class DDLRecordServiceSoap {

	/**
	 * Adds a record referencing the record set.
	 *
	 * @param groupId the primary key of the record's group
	 * @param recordSetId the primary key of the record set
	 * @param displayIndex the index position in which the record is displayed
	 in the spreadsheet view
	 * @param ddmFormValues the record values. See <code>DDMFormValues</code>
	 in the <code>dynamic.data.mapping.api</code> module.
	 * @param serviceContext the service context to be applied. This can set
	 the UUID, guest permissions, and group permissions for the
	 record.
	 * @return the record
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.dynamic.data.lists.model.DDLRecordSoap addRecord(
			long groupId, long recordSetId, int displayIndex,
			com.liferay.dynamic.data.mapping.storage.DDMFormValues
				ddmFormValues,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.dynamic.data.lists.model.DDLRecord returnValue =
				DDLRecordServiceUtil.addRecord(
					groupId, recordSetId, displayIndex, ddmFormValues,
					serviceContext);

			return com.liferay.dynamic.data.lists.model.DDLRecordSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Adds a record referencing the record set.
	 *
	 * @param groupId the primary key of the record's group
	 * @param recordSetId the primary key of the record set
	 * @param displayIndex the index position in which the record is
	 displayed in the spreadsheet view
	 * @param fields the record values. See <code>Fields</code> in the
	 <code>dynamic.data.mapping.api</code> module.
	 * @param serviceContext the service context to be applied. This can
	 set the UUID, guest permissions, and group permissions for
	 the record.
	 * @return the record
	 * @throws PortalException if a portal exception occurred
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 #addRecord(long, long, int, DDMFormValues, ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.dynamic.data.lists.model.DDLRecordSoap addRecord(
			long groupId, long recordSetId, int displayIndex,
			com.liferay.dynamic.data.mapping.storage.Fields fields,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.dynamic.data.lists.model.DDLRecord returnValue =
				DDLRecordServiceUtil.addRecord(
					groupId, recordSetId, displayIndex, fields, serviceContext);

			return com.liferay.dynamic.data.lists.model.DDLRecordSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Deletes the record and its resources.
	 *
	 * @param recordId the primary key of the record to be deleted
	 * @throws PortalException
	 */
	public static void deleteRecord(long recordId) throws RemoteException {
		try {
			DDLRecordServiceUtil.deleteRecord(recordId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Disassociates the locale from the record.
	 *
	 * @param recordId the primary key of the record
	 * @param locale the locale of the record values to be removed
	 * @param serviceContext the service context to be applied. This can
	 set the record modified date.
	 * @return the affected record
	 * @throws PortalException
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 #updateRecord(long, boolean, int, DDMFormValues,
	 ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.dynamic.data.lists.model.DDLRecordSoap
			deleteRecordLocale(
				long recordId, String locale,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.dynamic.data.lists.model.DDLRecord returnValue =
				DDLRecordServiceUtil.deleteRecordLocale(
					recordId, LocaleUtil.fromLanguageId(locale),
					serviceContext);

			return com.liferay.dynamic.data.lists.model.DDLRecordSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns the record with the ID.
	 *
	 * @param recordId the primary key of the record
	 * @return the record with the ID
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.dynamic.data.lists.model.DDLRecordSoap getRecord(
			long recordId)
		throws RemoteException {

		try {
			com.liferay.dynamic.data.lists.model.DDLRecord returnValue =
				DDLRecordServiceUtil.getRecord(recordId);

			return com.liferay.dynamic.data.lists.model.DDLRecordSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns all the records matching the record set ID
	 *
	 * @param recordSetId the record's record set ID
	 * @return the matching records
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.dynamic.data.lists.model.DDLRecordSoap[]
			getRecords(long recordSetId)
		throws RemoteException {

		try {
			java.util.List<com.liferay.dynamic.data.lists.model.DDLRecord>
				returnValue = DDLRecordServiceUtil.getRecords(recordSetId);

			return com.liferay.dynamic.data.lists.model.DDLRecordSoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Reverts the record to a given version.
	 *
	 * @param recordId the primary key of the record
	 * @param version the version to be reverted
	 * @param serviceContext the service context to be applied. This can set
	 the record modified date.
	 * @throws PortalException if a portal exception occurred
	 */
	public static void revertRecord(
			long recordId, String version,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			DDLRecordServiceUtil.revertRecord(
				recordId, version, serviceContext);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 #revertRecord(long, String, ServiceContext)}
	 */
	@Deprecated
	public static void revertRecordVersion(
			long recordId, String version,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			DDLRecordServiceUtil.revertRecordVersion(
				recordId, version, serviceContext);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Updates a record, replacing its display index and values.
	 *
	 * @param recordId the primary key of the record
	 * @param majorVersion whether this update is a major change. A major
	 change increments the record's major version number.
	 * @param displayIndex the index position in which the record is displayed
	 in the spreadsheet view
	 * @param ddmFormValues the record values. See <code>DDMFormValues</code>
	 in the <code>dynamic.data.mapping.api</code> module.
	 * @param serviceContext the service context to be applied. This can set
	 the record modified date.
	 * @return the record
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.dynamic.data.lists.model.DDLRecordSoap
			updateRecord(
				long recordId, boolean majorVersion, int displayIndex,
				com.liferay.dynamic.data.mapping.storage.DDMFormValues
					ddmFormValues,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.dynamic.data.lists.model.DDLRecord returnValue =
				DDLRecordServiceUtil.updateRecord(
					recordId, majorVersion, displayIndex, ddmFormValues,
					serviceContext);

			return com.liferay.dynamic.data.lists.model.DDLRecordSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Updates a record, replacing its display index and values.
	 *
	 * @param recordId the primary key of the record
	 * @param majorVersion whether this update is a major change. Major
	 changes causes the increment of the major version number.
	 * @param displayIndex the index position in which the record is
	 displayed in the spreadsheet view
	 * @param fields the record values. See <code>Fields</code> in the
	 <code>dynamic.data.mapping.api</code> module.
	 * @param mergeFields whether to merge the new fields with the existing
	 ones; otherwise replace the existing fields
	 * @param serviceContext the service context to be applied. This can
	 set the record modified date.
	 * @return the record
	 * @throws PortalException if a portal exception occurred
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 #updateRecord(long, boolean, int, DDMFormValues,
	 ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.dynamic.data.lists.model.DDLRecordSoap
			updateRecord(
				long recordId, boolean majorVersion, int displayIndex,
				com.liferay.dynamic.data.mapping.storage.Fields fields,
				boolean mergeFields,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.dynamic.data.lists.model.DDLRecord returnValue =
				DDLRecordServiceUtil.updateRecord(
					recordId, majorVersion, displayIndex, fields, mergeFields,
					serviceContext);

			return com.liferay.dynamic.data.lists.model.DDLRecordSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(DDLRecordServiceSoap.class);

}