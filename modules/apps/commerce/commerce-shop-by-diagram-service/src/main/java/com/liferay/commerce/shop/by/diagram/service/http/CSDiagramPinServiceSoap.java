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

package com.liferay.commerce.shop.by.diagram.service.http;

import com.liferay.commerce.shop.by.diagram.service.CSDiagramPinServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>CSDiagramPinServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.shop.by.diagram.model.CSDiagramPinSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.shop.by.diagram.model.CSDiagramPin</code>, that is translated to a
 * <code>com.liferay.commerce.shop.by.diagram.model.CSDiagramPinSoap</code>. Methods that SOAP
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
 * @author Alessio Antonio Rendina
 * @see CSDiagramPinServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CSDiagramPinServiceSoap {

	public static com.liferay.commerce.shop.by.diagram.model.CSDiagramPinSoap
			addCSDiagramPin(
				long cpDefinitionId, double positionX, double positionY,
				String sequence)
		throws RemoteException {

		try {
			com.liferay.commerce.shop.by.diagram.model.CSDiagramPin
				returnValue = CSDiagramPinServiceUtil.addCSDiagramPin(
					cpDefinitionId, positionX, positionY, sequence);

			return com.liferay.commerce.shop.by.diagram.model.CSDiagramPinSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCSDiagramPin(long csDiagramPinId)
		throws RemoteException {

		try {
			CSDiagramPinServiceUtil.deleteCSDiagramPin(csDiagramPinId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCSDiagramPins(long cpDefinitionId)
		throws RemoteException {

		try {
			CSDiagramPinServiceUtil.deleteCSDiagramPins(cpDefinitionId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.shop.by.diagram.model.CSDiagramPinSoap
			fetchCSDiagramPin(long csDiagramPinId)
		throws RemoteException {

		try {
			com.liferay.commerce.shop.by.diagram.model.CSDiagramPin
				returnValue = CSDiagramPinServiceUtil.fetchCSDiagramPin(
					csDiagramPinId);

			return com.liferay.commerce.shop.by.diagram.model.CSDiagramPinSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.shop.by.diagram.model.CSDiagramPinSoap
			getCSDiagramPin(long csDiagramPinId)
		throws RemoteException {

		try {
			com.liferay.commerce.shop.by.diagram.model.CSDiagramPin
				returnValue = CSDiagramPinServiceUtil.getCSDiagramPin(
					csDiagramPinId);

			return com.liferay.commerce.shop.by.diagram.model.CSDiagramPinSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.shop.by.diagram.model.CSDiagramPinSoap[]
			getCSDiagramPins(long cpDefinitionId, int start, int end)
		throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.shop.by.diagram.model.CSDiagramPin>
					returnValue = CSDiagramPinServiceUtil.getCSDiagramPins(
						cpDefinitionId, start, end);

			return com.liferay.commerce.shop.by.diagram.model.CSDiagramPinSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCSDiagramPinsCount(long cpDefinitionId)
		throws RemoteException {

		try {
			int returnValue = CSDiagramPinServiceUtil.getCSDiagramPinsCount(
				cpDefinitionId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.shop.by.diagram.model.CSDiagramPinSoap
			updateCSDiagramPin(
				long csDiagramPinId, double positionX, double positionY,
				String sequence)
		throws RemoteException {

		try {
			com.liferay.commerce.shop.by.diagram.model.CSDiagramPin
				returnValue = CSDiagramPinServiceUtil.updateCSDiagramPin(
					csDiagramPinId, positionX, positionY, sequence);

			return com.liferay.commerce.shop.by.diagram.model.CSDiagramPinSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CSDiagramPinServiceSoap.class);

}