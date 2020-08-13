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

package com.liferay.style.book.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.style.book.service.StyleBookEntryServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>StyleBookEntryServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.style.book.model.StyleBookEntrySoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.style.book.model.StyleBookEntry</code>, that is translated to a
 * <code>com.liferay.style.book.model.StyleBookEntrySoap</code>. Methods that SOAP
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
 * @see StyleBookEntryServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class StyleBookEntryServiceSoap {

	public static com.liferay.style.book.model.StyleBookEntrySoap
			addStyleBookEntry(
				long groupId, String name, String styleBookEntryKey,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.style.book.model.StyleBookEntry returnValue =
				StyleBookEntryServiceUtil.addStyleBookEntry(
					groupId, name, styleBookEntryKey, serviceContext);

			return com.liferay.style.book.model.StyleBookEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.style.book.model.StyleBookEntrySoap
			addStyleBookEntry(
				long groupId, String frontendTokensValues, String name,
				String styleBookEntryKey,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.style.book.model.StyleBookEntry returnValue =
				StyleBookEntryServiceUtil.addStyleBookEntry(
					groupId, frontendTokensValues, name, styleBookEntryKey,
					serviceContext);

			return com.liferay.style.book.model.StyleBookEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.style.book.model.StyleBookEntrySoap
			copyStyleBookEntry(
				long groupId, long styleBookEntryId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.style.book.model.StyleBookEntry returnValue =
				StyleBookEntryServiceUtil.copyStyleBookEntry(
					groupId, styleBookEntryId, serviceContext);

			return com.liferay.style.book.model.StyleBookEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.style.book.model.StyleBookEntrySoap
			deleteStyleBookEntry(long styleBookEntryId)
		throws RemoteException {

		try {
			com.liferay.style.book.model.StyleBookEntry returnValue =
				StyleBookEntryServiceUtil.deleteStyleBookEntry(
					styleBookEntryId);

			return com.liferay.style.book.model.StyleBookEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.style.book.model.StyleBookEntrySoap
			deleteStyleBookEntry(
				com.liferay.style.book.model.StyleBookEntrySoap styleBookEntry)
		throws RemoteException {

		try {
			com.liferay.style.book.model.StyleBookEntry returnValue =
				StyleBookEntryServiceUtil.deleteStyleBookEntry(
					com.liferay.style.book.model.impl.StyleBookEntryModelImpl.
						toModel(styleBookEntry));

			return com.liferay.style.book.model.StyleBookEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.style.book.model.StyleBookEntrySoap
			discardDraftStyleBookEntry(long styleBookEntryId)
		throws RemoteException {

		try {
			com.liferay.style.book.model.StyleBookEntry returnValue =
				StyleBookEntryServiceUtil.discardDraftStyleBookEntry(
					styleBookEntryId);

			return com.liferay.style.book.model.StyleBookEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.style.book.model.StyleBookEntrySoap publishDraft(
			long styleBookEntryId)
		throws RemoteException {

		try {
			com.liferay.style.book.model.StyleBookEntry returnValue =
				StyleBookEntryServiceUtil.publishDraft(styleBookEntryId);

			return com.liferay.style.book.model.StyleBookEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.style.book.model.StyleBookEntrySoap
			updateDefaultStyleBookEntry(
				long styleBookEntryId, boolean defaultStyleBookEntry)
		throws RemoteException {

		try {
			com.liferay.style.book.model.StyleBookEntry returnValue =
				StyleBookEntryServiceUtil.updateDefaultStyleBookEntry(
					styleBookEntryId, defaultStyleBookEntry);

			return com.liferay.style.book.model.StyleBookEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.style.book.model.StyleBookEntrySoap
			updateFrontendTokensValues(
				long styleBookEntryId, String frontendTokensValues)
		throws RemoteException {

		try {
			com.liferay.style.book.model.StyleBookEntry returnValue =
				StyleBookEntryServiceUtil.updateFrontendTokensValues(
					styleBookEntryId, frontendTokensValues);

			return com.liferay.style.book.model.StyleBookEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.style.book.model.StyleBookEntrySoap updateName(
			long styleBookEntryId, String name)
		throws RemoteException {

		try {
			com.liferay.style.book.model.StyleBookEntry returnValue =
				StyleBookEntryServiceUtil.updateName(styleBookEntryId, name);

			return com.liferay.style.book.model.StyleBookEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.style.book.model.StyleBookEntrySoap
			updatePreviewFileEntryId(
				long styleBookEntryId, long previewFileEntryId)
		throws RemoteException {

		try {
			com.liferay.style.book.model.StyleBookEntry returnValue =
				StyleBookEntryServiceUtil.updatePreviewFileEntryId(
					styleBookEntryId, previewFileEntryId);

			return com.liferay.style.book.model.StyleBookEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.style.book.model.StyleBookEntrySoap
			updateStyleBookEntry(
				long styleBookEntryId, String frontendTokensValues, String name)
		throws RemoteException {

		try {
			com.liferay.style.book.model.StyleBookEntry returnValue =
				StyleBookEntryServiceUtil.updateStyleBookEntry(
					styleBookEntryId, frontendTokensValues, name);

			return com.liferay.style.book.model.StyleBookEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		StyleBookEntryServiceSoap.class);

}