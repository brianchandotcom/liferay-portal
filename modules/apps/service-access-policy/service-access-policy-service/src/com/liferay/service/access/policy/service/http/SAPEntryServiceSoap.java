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

package com.liferay.service.access.policy.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import com.liferay.service.access.policy.service.SAPEntryServiceUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * {@link SAPEntryServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.service.access.policy.model.SAPEntrySoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.service.access.policy.model.SAPEntry}, that is translated to a
 * {@link com.liferay.service.access.policy.model.SAPEntrySoap}. Methods that SOAP cannot
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
 * @see SAPEntryServiceHttp
 * @see com.liferay.service.access.policy.model.SAPEntrySoap
 * @see SAPEntryServiceUtil
 * @generated
 */
@ProviderType
public class SAPEntryServiceSoap {
	public static com.liferay.service.access.policy.model.SAPEntrySoap addSAPEntry(
		java.lang.String allowedServiceSignatures, boolean enabled,
		java.lang.String name, java.lang.String[] titleMapLanguageIds,
		java.lang.String[] titleMapValues,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(titleMapLanguageIds,
					titleMapValues);

			com.liferay.service.access.policy.model.SAPEntry returnValue = SAPEntryServiceUtil.addSAPEntry(allowedServiceSignatures,
					enabled, name, titleMap, serviceContext);

			return com.liferay.service.access.policy.model.SAPEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.service.access.policy.model.SAPEntrySoap deleteSAPEntry(
		long sapEntryId) throws RemoteException {
		try {
			com.liferay.service.access.policy.model.SAPEntry returnValue = SAPEntryServiceUtil.deleteSAPEntry(sapEntryId);

			return com.liferay.service.access.policy.model.SAPEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.service.access.policy.model.SAPEntrySoap deleteSAPEntry(
		com.liferay.service.access.policy.model.SAPEntrySoap sapEntry)
		throws RemoteException {
		try {
			com.liferay.service.access.policy.model.SAPEntry returnValue = SAPEntryServiceUtil.deleteSAPEntry(com.liferay.service.access.policy.model.impl.SAPEntryModelImpl.toModel(
						sapEntry));

			return com.liferay.service.access.policy.model.SAPEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.service.access.policy.model.SAPEntrySoap[] getCompanySAPEntries(
		long companyId, int start, int end) throws RemoteException {
		try {
			java.util.List<com.liferay.service.access.policy.model.SAPEntry> returnValue =
				SAPEntryServiceUtil.getCompanySAPEntries(companyId, start, end);

			return com.liferay.service.access.policy.model.SAPEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.service.access.policy.model.SAPEntrySoap[] getCompanySAPEntries(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.SAPEntry> obc)
		throws RemoteException {
		try {
			java.util.List<com.liferay.service.access.policy.model.SAPEntry> returnValue =
				SAPEntryServiceUtil.getCompanySAPEntries(companyId, start, end,
					obc);

			return com.liferay.service.access.policy.model.SAPEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCompanySAPEntriesCount(long companyId)
		throws RemoteException {
		try {
			int returnValue = SAPEntryServiceUtil.getCompanySAPEntriesCount(companyId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.service.access.policy.model.SAPEntrySoap getSAPEntry(
		long sapEntryId) throws RemoteException {
		try {
			com.liferay.service.access.policy.model.SAPEntry returnValue = SAPEntryServiceUtil.getSAPEntry(sapEntryId);

			return com.liferay.service.access.policy.model.SAPEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.service.access.policy.model.SAPEntrySoap getSAPEntry(
		long companyId, java.lang.String name) throws RemoteException {
		try {
			com.liferay.service.access.policy.model.SAPEntry returnValue = SAPEntryServiceUtil.getSAPEntry(companyId,
					name);

			return com.liferay.service.access.policy.model.SAPEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.service.access.policy.model.SAPEntrySoap updateSAPEntry(
		long sapEntryId, java.lang.String allowedServiceSignatures,
		boolean enabled, java.lang.String name,
		java.lang.String[] titleMapLanguageIds,
		java.lang.String[] titleMapValues,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(titleMapLanguageIds,
					titleMapValues);

			com.liferay.service.access.policy.model.SAPEntry returnValue = SAPEntryServiceUtil.updateSAPEntry(sapEntryId,
					allowedServiceSignatures, enabled, name, titleMap,
					serviceContext);

			return com.liferay.service.access.policy.model.SAPEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(SAPEntryServiceSoap.class);
}