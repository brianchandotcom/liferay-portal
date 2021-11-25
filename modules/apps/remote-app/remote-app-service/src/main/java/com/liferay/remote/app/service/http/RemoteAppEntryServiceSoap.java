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

package com.liferay.remote.app.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.remote.app.service.RemoteAppEntryServiceUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * <code>RemoteAppEntryServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.remote.app.model.RemoteAppEntrySoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.remote.app.model.RemoteAppEntry</code>, that is translated to a
 * <code>com.liferay.remote.app.model.RemoteAppEntrySoap</code>. Methods that SOAP
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
 * @see RemoteAppEntryServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class RemoteAppEntryServiceSoap {

	public static com.liferay.remote.app.model.RemoteAppEntrySoap
			addCustomElementRemoteAppEntry(
				String customElementCSSURLs,
				String customElementHTMLElementName, String customElementURLs,
				String description, String friendlyURLMapping,
				boolean instanceable, String[] nameMapLanguageIds,
				String[] nameMapValues, String portletCategoryName,
				String properties, String sourceCodeURL)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);

			com.liferay.remote.app.model.RemoteAppEntry returnValue =
				RemoteAppEntryServiceUtil.addCustomElementRemoteAppEntry(
					customElementCSSURLs, customElementHTMLElementName,
					customElementURLs, description, friendlyURLMapping,
					instanceable, nameMap, portletCategoryName, properties,
					sourceCodeURL);

			return com.liferay.remote.app.model.RemoteAppEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.remote.app.model.RemoteAppEntrySoap
			addIFrameRemoteAppEntry(
				String description, String friendlyURLMapping, String iFrameURL,
				boolean instanceable, String[] nameMapLanguageIds,
				String[] nameMapValues, String portletCategoryName,
				String properties, String sourceCodeURL)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);

			com.liferay.remote.app.model.RemoteAppEntry returnValue =
				RemoteAppEntryServiceUtil.addIFrameRemoteAppEntry(
					description, friendlyURLMapping, iFrameURL, instanceable,
					nameMap, portletCategoryName, properties, sourceCodeURL);

			return com.liferay.remote.app.model.RemoteAppEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.remote.app.model.RemoteAppEntrySoap
			deleteRemoteAppEntry(long remoteAppEntryId)
		throws RemoteException {

		try {
			com.liferay.remote.app.model.RemoteAppEntry returnValue =
				RemoteAppEntryServiceUtil.deleteRemoteAppEntry(
					remoteAppEntryId);

			return com.liferay.remote.app.model.RemoteAppEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.remote.app.model.RemoteAppEntrySoap
			getRemoteAppEntry(long remoteAppEntryId)
		throws RemoteException {

		try {
			com.liferay.remote.app.model.RemoteAppEntry returnValue =
				RemoteAppEntryServiceUtil.getRemoteAppEntry(remoteAppEntryId);

			return com.liferay.remote.app.model.RemoteAppEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.remote.app.model.RemoteAppEntrySoap
			updateCustomElementRemoteAppEntry(
				long remoteAppEntryId, String customElementCSSURLs,
				String customElementHTMLElementName, String customElementURLs,
				String description, String friendlyURLMapping,
				String[] nameMapLanguageIds, String[] nameMapValues,
				String portletCategoryName, String properties,
				String sourceCodeURL)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);

			com.liferay.remote.app.model.RemoteAppEntry returnValue =
				RemoteAppEntryServiceUtil.updateCustomElementRemoteAppEntry(
					remoteAppEntryId, customElementCSSURLs,
					customElementHTMLElementName, customElementURLs,
					description, friendlyURLMapping, nameMap,
					portletCategoryName, properties, sourceCodeURL);

			return com.liferay.remote.app.model.RemoteAppEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.remote.app.model.RemoteAppEntrySoap
			updateIFrameRemoteAppEntry(
				long remoteAppEntryId, String description,
				String friendlyURLMapping, String iFrameURL,
				String[] nameMapLanguageIds, String[] nameMapValues,
				String portletCategoryName, String properties,
				String sourceCodeURL)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);

			com.liferay.remote.app.model.RemoteAppEntry returnValue =
				RemoteAppEntryServiceUtil.updateIFrameRemoteAppEntry(
					remoteAppEntryId, description, friendlyURLMapping,
					iFrameURL, nameMap, portletCategoryName, properties,
					sourceCodeURL);

			return com.liferay.remote.app.model.RemoteAppEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		RemoteAppEntryServiceSoap.class);

}