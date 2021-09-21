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

package com.liferay.object.service.http;

import com.liferay.object.service.ObjectLayoutServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * <code>ObjectLayoutServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.object.model.ObjectLayoutSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.object.model.ObjectLayout</code>, that is translated to a
 * <code>com.liferay.object.model.ObjectLayoutSoap</code>. Methods that SOAP
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
 * @author Marco Leo
 * @see ObjectLayoutServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class ObjectLayoutServiceSoap {

	public static com.liferay.object.model.ObjectLayoutSoap addObjectLayout(
			long objectDefinitionId, boolean defaultObjectLayout,
			String[] nameMapLanguageIds, String[] nameMapValues,
			com.liferay.object.model.ObjectLayoutTabSoap[] objectLayoutTabs)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);

			com.liferay.object.model.ObjectLayout returnValue =
				ObjectLayoutServiceUtil.addObjectLayout(
					objectDefinitionId, defaultObjectLayout, nameMap,
					com.liferay.object.model.impl.ObjectLayoutTabModelImpl.
						toModels(objectLayoutTabs));

			return com.liferay.object.model.ObjectLayoutSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.object.model.ObjectLayoutSoap deleteObjectLayout(
			long objectLayoutId)
		throws RemoteException {

		try {
			com.liferay.object.model.ObjectLayout returnValue =
				ObjectLayoutServiceUtil.deleteObjectLayout(objectLayoutId);

			return com.liferay.object.model.ObjectLayoutSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.object.model.ObjectLayoutSoap getObjectLayout(
			long objectLayoutId)
		throws RemoteException {

		try {
			com.liferay.object.model.ObjectLayout returnValue =
				ObjectLayoutServiceUtil.getObjectLayout(objectLayoutId);

			return com.liferay.object.model.ObjectLayoutSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.object.model.ObjectLayoutSoap updateObjectLayout(
			long objectLayoutId, boolean defaultObjectLayout,
			String[] nameMapLanguageIds, String[] nameMapValues,
			com.liferay.object.model.ObjectLayoutTabSoap[] objectLayoutTabs)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);

			com.liferay.object.model.ObjectLayout returnValue =
				ObjectLayoutServiceUtil.updateObjectLayout(
					objectLayoutId, defaultObjectLayout, nameMap,
					com.liferay.object.model.impl.ObjectLayoutTabModelImpl.
						toModels(objectLayoutTabs));

			return com.liferay.object.model.ObjectLayoutSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		ObjectLayoutServiceSoap.class);

}