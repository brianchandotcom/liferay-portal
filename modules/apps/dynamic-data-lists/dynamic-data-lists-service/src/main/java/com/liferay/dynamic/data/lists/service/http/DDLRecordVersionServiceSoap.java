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

import com.liferay.dynamic.data.lists.service.DDLRecordVersionServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>DDLRecordVersionServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.dynamic.data.lists.model.DDLRecordVersionSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.dynamic.data.lists.model.DDLRecordVersion</code>, that is translated to a
 * <code>com.liferay.dynamic.data.lists.model.DDLRecordVersionSoap</code>. Methods that SOAP
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
 * @see DDLRecordVersionServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class DDLRecordVersionServiceSoap {

	/**
	 * Returns the record version matching the ID.
	 *
	 * @param recordVersionId the primary key of the record version
	 * @return the record version with the ID
	 * @throws PortalException if the matching record set could not be found or
	 if the user did not have the required permission to access the
	 record set
	 */
	public static com.liferay.dynamic.data.lists.model.DDLRecordVersionSoap
			getRecordVersion(long recordVersionId)
		throws RemoteException {

		try {
			com.liferay.dynamic.data.lists.model.DDLRecordVersion returnValue =
				DDLRecordVersionServiceUtil.getRecordVersion(recordVersionId);

			return com.liferay.dynamic.data.lists.model.DDLRecordVersionSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns a record version matching the record and version.
	 *
	 * @param recordId the primary key of the record
	 * @param version the version of the record to return
	 * @return the record version macthing the record primary key and version
	 * @throws PortalException if the matching record set is not found or if the
	 user do not have the required permission to access the record set
	 */
	public static com.liferay.dynamic.data.lists.model.DDLRecordVersionSoap
			getRecordVersion(long recordId, String version)
		throws RemoteException {

		try {
			com.liferay.dynamic.data.lists.model.DDLRecordVersion returnValue =
				DDLRecordVersionServiceUtil.getRecordVersion(recordId, version);

			return com.liferay.dynamic.data.lists.model.DDLRecordVersionSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns all the record versions matching the record.
	 *
	 * @param recordId the primary key of the record
	 * @return the matching record versions
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.dynamic.data.lists.model.DDLRecordVersionSoap[]
			getRecordVersions(long recordId)
		throws RemoteException {

		try {
			java.util.List
				<com.liferay.dynamic.data.lists.model.DDLRecordVersion>
					returnValue = DDLRecordVersionServiceUtil.getRecordVersions(
						recordId);

			return com.liferay.dynamic.data.lists.model.DDLRecordVersionSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns an ordered range of record versions matching the record.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to <code>QueryUtil.ALL_POS</code> will return the
	 * full result set.
	 * </p>
	 *
	 * @param recordId the primary key of the record
	 * @param start the lower bound of the range of record versions to return
	 * @param end the upper bound of the range of record versions to return
	 (not inclusive)
	 * @param orderByComparator the comparator used to order the record
	 versions
	 * @return the range of matching record versions ordered by the comparator
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.dynamic.data.lists.model.DDLRecordVersionSoap[]
			getRecordVersions(
				long recordId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.dynamic.data.lists.model.DDLRecordVersion>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List
				<com.liferay.dynamic.data.lists.model.DDLRecordVersion>
					returnValue = DDLRecordVersionServiceUtil.getRecordVersions(
						recordId, start, end, orderByComparator);

			return com.liferay.dynamic.data.lists.model.DDLRecordVersionSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the number of record versions matching the record.
	 *
	 * @param recordId the primary key of the record
	 * @return the number of matching record versions
	 * @throws PortalException if a portal exception occurred
	 */
	public static int getRecordVersionsCount(long recordId)
		throws RemoteException {

		try {
			int returnValue =
				DDLRecordVersionServiceUtil.getRecordVersionsCount(recordId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DDLRecordVersionServiceSoap.class);

}