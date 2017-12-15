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

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordVersionServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link DDMFormInstanceRecordVersionServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersionSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion}, that is translated to a
 * {@link com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersionSoap}. Methods that SOAP cannot
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
 * @see DDMFormInstanceRecordVersionServiceHttp
 * @see com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersionSoap
 * @see DDMFormInstanceRecordVersionServiceUtil
 * @generated
 */
@ProviderType
public class DDMFormInstanceRecordVersionServiceSoap {
	public static com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersionSoap getFormInstanceRecordVersion(
		long ddmFormInstanceRecordVersionId) throws RemoteException {
		try {
			com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion returnValue =
				DDMFormInstanceRecordVersionServiceUtil.getFormInstanceRecordVersion(ddmFormInstanceRecordVersionId);

			return com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersionSoap getFormInstanceRecordVersion(
		long ddmFormInstanceRecordId, java.lang.String version)
		throws RemoteException {
		try {
			com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion returnValue =
				DDMFormInstanceRecordVersionServiceUtil.getFormInstanceRecordVersion(ddmFormInstanceRecordId,
					version);

			return com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersionSoap[] getFormInstanceRecordVersions(
		long ddmFormInstanceRecordId) throws RemoteException {
		try {
			java.util.List<com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion> returnValue =
				DDMFormInstanceRecordVersionServiceUtil.getFormInstanceRecordVersions(ddmFormInstanceRecordId);

			return com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersionSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersionSoap[] getFormInstanceRecordVersions(
		long ddmFormInstanceRecordId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion> returnValue =
				DDMFormInstanceRecordVersionServiceUtil.getFormInstanceRecordVersions(ddmFormInstanceRecordId,
					start, end, orderByComparator);

			return com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersionSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFormInstanceRecordVersionsCount(
		long ddmFormInstanceRecordId) throws RemoteException {
		try {
			int returnValue = DDMFormInstanceRecordVersionServiceUtil.getFormInstanceRecordVersionsCount(ddmFormInstanceRecordId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(DDMFormInstanceRecordVersionServiceSoap.class);
}