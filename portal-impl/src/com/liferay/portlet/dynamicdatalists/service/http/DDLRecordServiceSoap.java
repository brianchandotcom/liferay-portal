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

package com.liferay.portlet.dynamicdatalists.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import com.liferay.portlet.dynamicdatalists.service.DDLRecordServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link com.liferay.portlet.dynamicdatalists.service.DDLRecordServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portlet.dynamicdatalists.model.DDLRecordSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portlet.dynamicdatalists.model.DDLRecord}, that is translated to a
 * {@link com.liferay.portlet.dynamicdatalists.model.DDLRecordSoap}. Methods that SOAP cannot
 * safely wire are skipped.
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
 * @see com.liferay.portlet.dynamicdatalists.model.DDLRecordSoap
 * @see com.liferay.portlet.dynamicdatalists.service.DDLRecordServiceUtil
 * @generated
 */
@ProviderType
public class DDLRecordServiceSoap {
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecordSoap addRecord(
		long groupId, long recordSetId, int displayIndex,
		com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues ddmFormValues,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.dynamicdatalists.model.DDLRecord returnValue = DDLRecordServiceUtil.addRecord(groupId,
					recordSetId, displayIndex, ddmFormValues, serviceContext);

			return com.liferay.portlet.dynamicdatalists.model.DDLRecordSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteRecord(long recordId) throws RemoteException {
		try {
			DDLRecordServiceUtil.deleteRecord(recordId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.dynamicdatalists.model.DDLRecordSoap deleteRecordLocale(
		long recordId, String locale,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.dynamicdatalists.model.DDLRecord returnValue = DDLRecordServiceUtil.deleteRecordLocale(recordId,
					LocaleUtil.fromLanguageId(locale), serviceContext);

			return com.liferay.portlet.dynamicdatalists.model.DDLRecordSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.dynamicdatalists.model.DDLRecordSoap getRecord(
		long recordId) throws RemoteException {
		try {
			com.liferay.portlet.dynamicdatalists.model.DDLRecord returnValue = DDLRecordServiceUtil.getRecord(recordId);

			return com.liferay.portlet.dynamicdatalists.model.DDLRecordSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void revertRecord(long recordId, java.lang.String version,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			DDLRecordServiceUtil.revertRecord(recordId, version, serviceContext);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #revertRecord(long, long,
	String, ServiceContext)}
	*/
	@Deprecated
	public static void revertRecordVersion(long recordId,
		java.lang.String version,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			DDLRecordServiceUtil.revertRecordVersion(recordId, version,
				serviceContext);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.dynamicdatalists.model.DDLRecordSoap updateRecord(
		long recordId, boolean majorVersion, int displayIndex,
		com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues ddmFormValues,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.dynamicdatalists.model.DDLRecord returnValue = DDLRecordServiceUtil.updateRecord(recordId,
					majorVersion, displayIndex, ddmFormValues, serviceContext);

			return com.liferay.portlet.dynamicdatalists.model.DDLRecordSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(DDLRecordServiceSoap.class);
}