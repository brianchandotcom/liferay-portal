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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.liferay.powwow.service.PowwowMeetingServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link PowwowMeetingServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.powwow.model.PowwowMeetingSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.powwow.model.PowwowMeeting}, that is translated to a
 * {@link com.liferay.powwow.model.PowwowMeetingSoap}. Methods that SOAP cannot
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
 * @author Shinn Lok
 * @see PowwowMeetingServiceHttp
 * @see com.liferay.powwow.model.PowwowMeetingSoap
 * @see PowwowMeetingServiceUtil
 * @generated
 */
@ProviderType
public class PowwowMeetingServiceSoap {
	public static com.liferay.powwow.model.PowwowMeetingSoap deletePowwowMeeting(
		long powwowMeetingId) throws RemoteException {
		try {
			com.liferay.powwow.model.PowwowMeeting returnValue = PowwowMeetingServiceUtil.deletePowwowMeeting(powwowMeetingId);

			return com.liferay.powwow.model.PowwowMeetingSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.powwow.model.PowwowMeetingSoap getPowwowMeeting(
		long powwowMeetingId) throws RemoteException {
		try {
			com.liferay.powwow.model.PowwowMeeting returnValue = PowwowMeetingServiceUtil.getPowwowMeeting(powwowMeetingId);

			return com.liferay.powwow.model.PowwowMeetingSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.powwow.model.PowwowMeetingSoap[] getPowwowMeetings(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws RemoteException {
		try {
			java.util.List<com.liferay.powwow.model.PowwowMeeting> returnValue = PowwowMeetingServiceUtil.getPowwowMeetings(groupId,
					start, end, obc);

			return com.liferay.powwow.model.PowwowMeetingSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getPowwowMeetingsCount(long groupId)
		throws RemoteException {
		try {
			int returnValue = PowwowMeetingServiceUtil.getPowwowMeetingsCount(groupId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(PowwowMeetingServiceSoap.class);
}