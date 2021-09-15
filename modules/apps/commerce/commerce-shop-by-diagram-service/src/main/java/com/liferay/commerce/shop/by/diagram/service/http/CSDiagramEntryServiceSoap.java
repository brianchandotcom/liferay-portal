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

import com.liferay.commerce.shop.by.diagram.service.CSDiagramEntryServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>CSDiagramEntryServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.shop.by.diagram.model.CSDiagramEntrySoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry</code>, that is translated to a
 * <code>com.liferay.commerce.shop.by.diagram.model.CSDiagramEntrySoap</code>. Methods that SOAP
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
 * @see CSDiagramEntryServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CSDiagramEntryServiceSoap {

	public static com.liferay.commerce.shop.by.diagram.model.CSDiagramEntrySoap
			addCSDiagramEntry(
				long cpDefinitionId, long cpInstanceId, long cProductId,
				boolean diagram, int quantity, String sequence, String sku,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry
				returnValue = CSDiagramEntryServiceUtil.addCSDiagramEntry(
					cpDefinitionId, cpInstanceId, cProductId, diagram, quantity,
					sequence, sku, serviceContext);

			return com.liferay.commerce.shop.by.diagram.model.
				CSDiagramEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCSDiagramEntries(long cpDefinitionId)
		throws RemoteException {

		try {
			CSDiagramEntryServiceUtil.deleteCSDiagramEntries(cpDefinitionId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCSDiagramEntry(long csDiagramEntryId)
		throws RemoteException {

		try {
			CSDiagramEntryServiceUtil.deleteCSDiagramEntry(csDiagramEntryId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.shop.by.diagram.model.CSDiagramEntrySoap
			fetchCSDiagramEntry(long cpDefinitionId, String sequence)
		throws RemoteException {

		try {
			com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry
				returnValue = CSDiagramEntryServiceUtil.fetchCSDiagramEntry(
					cpDefinitionId, sequence);

			return com.liferay.commerce.shop.by.diagram.model.
				CSDiagramEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.shop.by.diagram.model.CSDiagramEntrySoap[]
				getCSDiagramEntries(long cpDefinitionId, int start, int end)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry>
					returnValue = CSDiagramEntryServiceUtil.getCSDiagramEntries(
						cpDefinitionId, start, end);

			return com.liferay.commerce.shop.by.diagram.model.
				CSDiagramEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCSDiagramEntriesCount(long cpDefinitionId)
		throws RemoteException {

		try {
			int returnValue =
				CSDiagramEntryServiceUtil.getCSDiagramEntriesCount(
					cpDefinitionId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.shop.by.diagram.model.CSDiagramEntrySoap
			getCSDiagramEntry(long csDiagramEntryId)
		throws RemoteException {

		try {
			com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry
				returnValue = CSDiagramEntryServiceUtil.getCSDiagramEntry(
					csDiagramEntryId);

			return com.liferay.commerce.shop.by.diagram.model.
				CSDiagramEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.shop.by.diagram.model.CSDiagramEntrySoap
			getCSDiagramEntry(long cpDefinitionId, String sequence)
		throws RemoteException {

		try {
			com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry
				returnValue = CSDiagramEntryServiceUtil.getCSDiagramEntry(
					cpDefinitionId, sequence);

			return com.liferay.commerce.shop.by.diagram.model.
				CSDiagramEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.shop.by.diagram.model.CSDiagramEntrySoap
			updateCSDiagramEntry(
				long csDiagramEntryId, long cpInstanceId, long cProductId,
				boolean diagram, int quantity, String sequence, String sku,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry
				returnValue = CSDiagramEntryServiceUtil.updateCSDiagramEntry(
					csDiagramEntryId, cpInstanceId, cProductId, diagram,
					quantity, sequence, sku, serviceContext);

			return com.liferay.commerce.shop.by.diagram.model.
				CSDiagramEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CSDiagramEntryServiceSoap.class);

}