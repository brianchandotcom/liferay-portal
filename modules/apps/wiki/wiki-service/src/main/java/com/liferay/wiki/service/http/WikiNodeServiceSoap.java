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

package com.liferay.wiki.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.wiki.service.WikiNodeServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>WikiNodeServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.wiki.model.WikiNodeSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.wiki.model.WikiNode</code>, that is translated to a
 * <code>com.liferay.wiki.model.WikiNodeSoap</code>. Methods that SOAP
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
 * @see WikiNodeServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class WikiNodeServiceSoap {

	public static com.liferay.wiki.model.WikiNodeSoap addNode(
			String name, String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.wiki.model.WikiNode returnValue =
				WikiNodeServiceUtil.addNode(name, description, serviceContext);

			return com.liferay.wiki.model.WikiNodeSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteNode(long nodeId) throws RemoteException {
		try {
			WikiNodeServiceUtil.deleteNode(nodeId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.wiki.model.WikiNodeSoap getNode(long nodeId)
		throws RemoteException {

		try {
			com.liferay.wiki.model.WikiNode returnValue =
				WikiNodeServiceUtil.getNode(nodeId);

			return com.liferay.wiki.model.WikiNodeSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.wiki.model.WikiNodeSoap getNode(
			long groupId, String name)
		throws RemoteException {

		try {
			com.liferay.wiki.model.WikiNode returnValue =
				WikiNodeServiceUtil.getNode(groupId, name);

			return com.liferay.wiki.model.WikiNodeSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.wiki.model.WikiNodeSoap[] getNodes(long groupId)
		throws RemoteException {

		try {
			java.util.List<com.liferay.wiki.model.WikiNode> returnValue =
				WikiNodeServiceUtil.getNodes(groupId);

			return com.liferay.wiki.model.WikiNodeSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.wiki.model.WikiNodeSoap[] getNodes(
			long groupId, int status)
		throws RemoteException {

		try {
			java.util.List<com.liferay.wiki.model.WikiNode> returnValue =
				WikiNodeServiceUtil.getNodes(groupId, status);

			return com.liferay.wiki.model.WikiNodeSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.wiki.model.WikiNodeSoap[] getNodes(
			long groupId, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.wiki.model.WikiNode> returnValue =
				WikiNodeServiceUtil.getNodes(groupId, start, end);

			return com.liferay.wiki.model.WikiNodeSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.wiki.model.WikiNodeSoap[] getNodes(
			long groupId, int status, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.wiki.model.WikiNode> returnValue =
				WikiNodeServiceUtil.getNodes(groupId, status, start, end);

			return com.liferay.wiki.model.WikiNodeSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.wiki.model.WikiNodeSoap[] getNodes(
			long groupId, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.wiki.model.WikiNode> orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.wiki.model.WikiNode> returnValue =
				WikiNodeServiceUtil.getNodes(
					groupId, status, start, end, orderByComparator);

			return com.liferay.wiki.model.WikiNodeSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getNodesCount(long groupId) throws RemoteException {
		try {
			int returnValue = WikiNodeServiceUtil.getNodesCount(groupId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getNodesCount(long groupId, int status)
		throws RemoteException {

		try {
			int returnValue = WikiNodeServiceUtil.getNodesCount(
				groupId, status);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.wiki.model.WikiNodeSoap moveNodeToTrash(
			long nodeId)
		throws RemoteException {

		try {
			com.liferay.wiki.model.WikiNode returnValue =
				WikiNodeServiceUtil.moveNodeToTrash(nodeId);

			return com.liferay.wiki.model.WikiNodeSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void restoreNodeFromTrash(long nodeId)
		throws RemoteException {

		try {
			WikiNodeServiceUtil.restoreNodeFromTrash(nodeId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void subscribeNode(long nodeId) throws RemoteException {
		try {
			WikiNodeServiceUtil.subscribeNode(nodeId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void unsubscribeNode(long nodeId) throws RemoteException {
		try {
			WikiNodeServiceUtil.unsubscribeNode(nodeId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.wiki.model.WikiNodeSoap updateNode(
			long nodeId, String name, String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.wiki.model.WikiNode returnValue =
				WikiNodeServiceUtil.updateNode(
					nodeId, name, description, serviceContext);

			return com.liferay.wiki.model.WikiNodeSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(WikiNodeServiceSoap.class);

}