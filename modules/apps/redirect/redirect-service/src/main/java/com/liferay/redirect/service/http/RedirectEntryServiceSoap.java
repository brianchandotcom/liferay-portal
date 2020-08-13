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

package com.liferay.redirect.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.redirect.service.RedirectEntryServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>RedirectEntryServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.redirect.model.RedirectEntrySoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.redirect.model.RedirectEntry</code>, that is translated to a
 * <code>com.liferay.redirect.model.RedirectEntrySoap</code>. Methods that SOAP
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
 * @see RedirectEntryServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class RedirectEntryServiceSoap {

	public static com.liferay.redirect.model.RedirectEntrySoap addRedirectEntry(
			long groupId, String destinationURL, java.util.Date expirationDate,
			boolean permanent, String sourceURL,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.redirect.model.RedirectEntry returnValue =
				RedirectEntryServiceUtil.addRedirectEntry(
					groupId, destinationURL, expirationDate, permanent,
					sourceURL, serviceContext);

			return com.liferay.redirect.model.RedirectEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.redirect.model.RedirectEntrySoap addRedirectEntry(
			long groupId, String destinationURL, java.util.Date expirationDate,
			String groupBaseURL, boolean permanent, String sourceURL,
			boolean updateChainedRedirectEntries,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.redirect.model.RedirectEntry returnValue =
				RedirectEntryServiceUtil.addRedirectEntry(
					groupId, destinationURL, expirationDate, groupBaseURL,
					permanent, sourceURL, updateChainedRedirectEntries,
					serviceContext);

			return com.liferay.redirect.model.RedirectEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.redirect.model.RedirectEntrySoap
			deleteRedirectEntry(long redirectEntryId)
		throws RemoteException {

		try {
			com.liferay.redirect.model.RedirectEntry returnValue =
				RedirectEntryServiceUtil.deleteRedirectEntry(redirectEntryId);

			return com.liferay.redirect.model.RedirectEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.redirect.model.RedirectEntrySoap
			fetchRedirectEntry(long redirectEntryId)
		throws RemoteException {

		try {
			com.liferay.redirect.model.RedirectEntry returnValue =
				RedirectEntryServiceUtil.fetchRedirectEntry(redirectEntryId);

			return com.liferay.redirect.model.RedirectEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.redirect.model.RedirectEntrySoap[]
			getRedirectEntries(
				long groupId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.redirect.model.RedirectEntry>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.redirect.model.RedirectEntry>
				returnValue = RedirectEntryServiceUtil.getRedirectEntries(
					groupId, start, end, orderByComparator);

			return com.liferay.redirect.model.RedirectEntrySoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getRedirectEntriesCount(long groupId)
		throws RemoteException {

		try {
			int returnValue = RedirectEntryServiceUtil.getRedirectEntriesCount(
				groupId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.redirect.model.RedirectEntrySoap
			updateRedirectEntry(
				long redirectEntryId, String destinationURL,
				java.util.Date expirationDate, boolean permanent,
				String sourceURL)
		throws RemoteException {

		try {
			com.liferay.redirect.model.RedirectEntry returnValue =
				RedirectEntryServiceUtil.updateRedirectEntry(
					redirectEntryId, destinationURL, expirationDate, permanent,
					sourceURL);

			return com.liferay.redirect.model.RedirectEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.redirect.model.RedirectEntrySoap
			updateRedirectEntry(
				long redirectEntryId, String destinationURL,
				java.util.Date expirationDate, String groupBaseURL,
				boolean permanent, String sourceURL,
				boolean updateChainedRedirectEntries)
		throws RemoteException {

		try {
			com.liferay.redirect.model.RedirectEntry returnValue =
				RedirectEntryServiceUtil.updateRedirectEntry(
					redirectEntryId, destinationURL, expirationDate,
					groupBaseURL, permanent, sourceURL,
					updateChainedRedirectEntries);

			return com.liferay.redirect.model.RedirectEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		RedirectEntryServiceSoap.class);

}