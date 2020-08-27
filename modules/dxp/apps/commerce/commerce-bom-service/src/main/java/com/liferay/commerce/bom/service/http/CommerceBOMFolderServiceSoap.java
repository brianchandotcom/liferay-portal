/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.bom.service.http;

import com.liferay.commerce.bom.service.CommerceBOMFolderServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>CommerceBOMFolderServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.bom.model.CommerceBOMFolderSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.bom.model.CommerceBOMFolder</code>, that is translated to a
 * <code>com.liferay.commerce.bom.model.CommerceBOMFolderSoap</code>. Methods that SOAP
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
 * @author Luca Pellizzon
 * @see CommerceBOMFolderServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommerceBOMFolderServiceSoap {

	public static com.liferay.commerce.bom.model.CommerceBOMFolderSoap
			addCommerceBOMFolder(
				long userId, long parentCommerceBOMFolderId, String name,
				boolean logo, byte[] logoBytes)
		throws RemoteException {

		try {
			com.liferay.commerce.bom.model.CommerceBOMFolder returnValue =
				CommerceBOMFolderServiceUtil.addCommerceBOMFolder(
					userId, parentCommerceBOMFolderId, name, logo, logoBytes);

			return com.liferay.commerce.bom.model.CommerceBOMFolderSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCommerceBOMFolder(long commerceBOMFolderId)
		throws RemoteException {

		try {
			CommerceBOMFolderServiceUtil.deleteCommerceBOMFolder(
				commerceBOMFolderId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.bom.model.CommerceBOMFolderSoap
			getCommerceBOMFolder(long commerceBOMFolderId)
		throws RemoteException {

		try {
			com.liferay.commerce.bom.model.CommerceBOMFolder returnValue =
				CommerceBOMFolderServiceUtil.getCommerceBOMFolder(
					commerceBOMFolderId);

			return com.liferay.commerce.bom.model.CommerceBOMFolderSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.bom.model.CommerceBOMFolderSoap[]
			getCommerceBOMFolders(long companyId, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.bom.model.CommerceBOMFolder>
				returnValue =
					CommerceBOMFolderServiceUtil.getCommerceBOMFolders(
						companyId, start, end);

			return com.liferay.commerce.bom.model.CommerceBOMFolderSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.bom.model.CommerceBOMFolderSoap[]
			getCommerceBOMFolders(
				long companyId, long parentCommerceBOMFolderId, int start,
				int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.bom.model.CommerceBOMFolder>
				returnValue =
					CommerceBOMFolderServiceUtil.getCommerceBOMFolders(
						companyId, parentCommerceBOMFolderId, start, end);

			return com.liferay.commerce.bom.model.CommerceBOMFolderSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommerceBOMFoldersCount(long companyId)
		throws RemoteException {

		try {
			int returnValue =
				CommerceBOMFolderServiceUtil.getCommerceBOMFoldersCount(
					companyId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommerceBOMFoldersCount(
			long companyId, long parentCommerceBOMFolderId)
		throws RemoteException {

		try {
			int returnValue =
				CommerceBOMFolderServiceUtil.getCommerceBOMFoldersCount(
					companyId, parentCommerceBOMFolderId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.bom.model.CommerceBOMFolderSoap
			updateCommerceBOMFolder(
				long commerceBOMFolderId, String name, boolean logo,
				byte[] logoBytes)
		throws RemoteException {

		try {
			com.liferay.commerce.bom.model.CommerceBOMFolder returnValue =
				CommerceBOMFolderServiceUtil.updateCommerceBOMFolder(
					commerceBOMFolderId, name, logo, logoBytes);

			return com.liferay.commerce.bom.model.CommerceBOMFolderSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceBOMFolderServiceSoap.class);

}