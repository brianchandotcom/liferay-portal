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

package com.liferay.service.access.control.profile.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import com.liferay.service.access.control.profile.service.SACPEntryServiceUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * {@link SACPEntryServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.service.access.control.profile.model.SACPEntrySoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.service.access.control.profile.model.SACPEntry}, that is translated to a
 * {@link com.liferay.service.access.control.profile.model.SACPEntrySoap}. Methods that SOAP cannot
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
 * @see SACPEntryServiceHttp
 * @see com.liferay.service.access.control.profile.model.SACPEntrySoap
 * @see SACPEntryServiceUtil
 * @generated
 */
@ProviderType
public class SACPEntryServiceSoap {
	public static com.liferay.service.access.control.profile.model.SACPEntrySoap addSACPEntry(
		java.lang.String allowedServiceSignatures, java.lang.String name,
		java.lang.String[] titleMapLanguageIds,
		java.lang.String[] titleMapValues,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(titleMapLanguageIds,
					titleMapValues);

			com.liferay.service.access.control.profile.model.SACPEntry returnValue =
				SACPEntryServiceUtil.addSACPEntry(allowedServiceSignatures,
					name, titleMap, serviceContext);

			return com.liferay.service.access.control.profile.model.SACPEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.service.access.control.profile.model.SACPEntrySoap deleteSACPEntry(
		long sacpEntryId) throws RemoteException {
		try {
			com.liferay.service.access.control.profile.model.SACPEntry returnValue =
				SACPEntryServiceUtil.deleteSACPEntry(sacpEntryId);

			return com.liferay.service.access.control.profile.model.SACPEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.service.access.control.profile.model.SACPEntrySoap deleteSACPEntry(
		com.liferay.service.access.control.profile.model.SACPEntrySoap sacpEntry)
		throws RemoteException {
		try {
			com.liferay.service.access.control.profile.model.SACPEntry returnValue =
				SACPEntryServiceUtil.deleteSACPEntry(com.liferay.service.access.control.profile.model.impl.SACPEntryModelImpl.toModel(
						sacpEntry));

			return com.liferay.service.access.control.profile.model.SACPEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.service.access.control.profile.model.SACPEntrySoap[] getCompanySACPEntries(
		long companyId, int start, int end) throws RemoteException {
		try {
			java.util.List<com.liferay.service.access.control.profile.model.SACPEntry> returnValue =
				SACPEntryServiceUtil.getCompanySACPEntries(companyId, start, end);

			return com.liferay.service.access.control.profile.model.SACPEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.service.access.control.profile.model.SACPEntrySoap[] getCompanySACPEntries(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.control.profile.model.SACPEntry> obc)
		throws RemoteException {
		try {
			java.util.List<com.liferay.service.access.control.profile.model.SACPEntry> returnValue =
				SACPEntryServiceUtil.getCompanySACPEntries(companyId, start,
					end, obc);

			return com.liferay.service.access.control.profile.model.SACPEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCompanySACPEntriesCount(long companyId)
		throws RemoteException {
		try {
			int returnValue = SACPEntryServiceUtil.getCompanySACPEntriesCount(companyId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.service.access.control.profile.model.SACPEntrySoap getSACPEntry(
		long sacpEntryId) throws RemoteException {
		try {
			com.liferay.service.access.control.profile.model.SACPEntry returnValue =
				SACPEntryServiceUtil.getSACPEntry(sacpEntryId);

			return com.liferay.service.access.control.profile.model.SACPEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.service.access.control.profile.model.SACPEntrySoap getSACPEntry(
		long companyId, java.lang.String name) throws RemoteException {
		try {
			com.liferay.service.access.control.profile.model.SACPEntry returnValue =
				SACPEntryServiceUtil.getSACPEntry(companyId, name);

			return com.liferay.service.access.control.profile.model.SACPEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.service.access.control.profile.model.SACPEntrySoap updateSACPEntry(
		long sacpEntryId, java.lang.String allowedServiceSignatures,
		java.lang.String name, java.lang.String[] titleMapLanguageIds,
		java.lang.String[] titleMapValues,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(titleMapLanguageIds,
					titleMapValues);

			com.liferay.service.access.control.profile.model.SACPEntry returnValue =
				SACPEntryServiceUtil.updateSACPEntry(sacpEntryId,
					allowedServiceSignatures, name, titleMap, serviceContext);

			return com.liferay.service.access.control.profile.model.SACPEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(SACPEntryServiceSoap.class);
}