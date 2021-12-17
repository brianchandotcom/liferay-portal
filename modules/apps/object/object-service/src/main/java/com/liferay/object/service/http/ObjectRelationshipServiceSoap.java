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

import com.liferay.object.service.ObjectRelationshipServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * <code>ObjectRelationshipServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.object.model.ObjectRelationshipSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.object.model.ObjectRelationship</code>, that is translated to a
 * <code>com.liferay.object.model.ObjectRelationshipSoap</code>. Methods that SOAP
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
 * @see ObjectRelationshipServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class ObjectRelationshipServiceSoap {

	public static com.liferay.object.model.ObjectRelationshipSoap
			addObjectRelationship(
				long objectDefinitionId1, long objectDefinitionId2,
				String deletionType, String[] labelMapLanguageIds,
				String[] labelMapValues, String name, String type)
		throws RemoteException {

		try {
			Map<Locale, String> labelMap = LocalizationUtil.getLocalizationMap(
				labelMapLanguageIds, labelMapValues);

			com.liferay.object.model.ObjectRelationship returnValue =
				ObjectRelationshipServiceUtil.addObjectRelationship(
					objectDefinitionId1, objectDefinitionId2, deletionType,
					labelMap, name, type);

			return com.liferay.object.model.ObjectRelationshipSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.object.model.ObjectRelationshipSoap
			deleteObjectRelationship(long objectRelationshipId)
		throws RemoteException {

		try {
			com.liferay.object.model.ObjectRelationship returnValue =
				ObjectRelationshipServiceUtil.deleteObjectRelationship(
					objectRelationshipId);

			return com.liferay.object.model.ObjectRelationshipSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.object.model.ObjectRelationshipSoap
			getObjectRelationship(long objectRelationshipId)
		throws RemoteException {

		try {
			com.liferay.object.model.ObjectRelationship returnValue =
				ObjectRelationshipServiceUtil.getObjectRelationship(
					objectRelationshipId);

			return com.liferay.object.model.ObjectRelationshipSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.object.model.ObjectRelationshipSoap[]
			getObjectRelationships(long objectDefinitionId1, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.object.model.ObjectRelationship>
				returnValue =
					ObjectRelationshipServiceUtil.getObjectRelationships(
						objectDefinitionId1, start, end);

			return com.liferay.object.model.ObjectRelationshipSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.object.model.ObjectRelationshipSoap
			updateObjectRelationship(
				long objectRelationshipId, String deletionType,
				String[] labelMapLanguageIds, String[] labelMapValues)
		throws RemoteException {

		try {
			Map<Locale, String> labelMap = LocalizationUtil.getLocalizationMap(
				labelMapLanguageIds, labelMapValues);

			com.liferay.object.model.ObjectRelationship returnValue =
				ObjectRelationshipServiceUtil.updateObjectRelationship(
					objectRelationshipId, deletionType, labelMap);

			return com.liferay.object.model.ObjectRelationshipSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		ObjectRelationshipServiceSoap.class);

}