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

package com.liferay.powwow.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.powwow.service.PowwowMeetingServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>PowwowMeetingServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.powwow.model.PowwowMeetingSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.powwow.model.PowwowMeeting</code>, that is translated to a
 * <code>com.liferay.powwow.model.PowwowMeetingSoap</code>. Methods that SOAP
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
 * @author Shinn Lok
 * @see PowwowMeetingServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class PowwowMeetingServiceSoap {

	public static com.liferay.powwow.model.PowwowMeetingSoap
			deletePowwowMeeting(long powwowMeetingId)
		throws RemoteException {

		try {
			com.liferay.powwow.model.PowwowMeeting returnValue =
				PowwowMeetingServiceUtil.deletePowwowMeeting(powwowMeetingId);

			return com.liferay.powwow.model.PowwowMeetingSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.powwow.model.PowwowMeetingSoap getPowwowMeeting(
			long powwowMeetingId)
		throws RemoteException {

		try {
			com.liferay.powwow.model.PowwowMeeting returnValue =
				PowwowMeetingServiceUtil.getPowwowMeeting(powwowMeetingId);

			return com.liferay.powwow.model.PowwowMeetingSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.powwow.model.PowwowMeetingSoap[]
			getPowwowMeetings(
				long groupId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.powwow.model.PowwowMeeting> orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.powwow.model.PowwowMeeting> returnValue =
				PowwowMeetingServiceUtil.getPowwowMeetings(
					groupId, start, end, orderByComparator);

			return com.liferay.powwow.model.PowwowMeetingSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getPowwowMeetingsCount(long groupId)
		throws RemoteException {

		try {
			int returnValue = PowwowMeetingServiceUtil.getPowwowMeetingsCount(
				groupId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		PowwowMeetingServiceSoap.class);

}