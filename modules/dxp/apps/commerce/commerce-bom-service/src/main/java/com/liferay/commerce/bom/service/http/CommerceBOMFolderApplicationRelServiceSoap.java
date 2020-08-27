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

import com.liferay.commerce.bom.service.CommerceBOMFolderApplicationRelServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>CommerceBOMFolderApplicationRelServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.bom.model.CommerceBOMFolderApplicationRelSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.bom.model.CommerceBOMFolderApplicationRel</code>, that is translated to a
 * <code>com.liferay.commerce.bom.model.CommerceBOMFolderApplicationRelSoap</code>. Methods that SOAP
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
 * @see CommerceBOMFolderApplicationRelServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommerceBOMFolderApplicationRelServiceSoap {

	public static
		com.liferay.commerce.bom.model.CommerceBOMFolderApplicationRelSoap
				addCommerceBOMFolderApplicationRel(
					long userId, long commerceBOMFolderId,
					long commerceApplicationModelId)
			throws RemoteException {

		try {
			com.liferay.commerce.bom.model.CommerceBOMFolderApplicationRel
				returnValue =
					CommerceBOMFolderApplicationRelServiceUtil.
						addCommerceBOMFolderApplicationRel(
							userId, commerceBOMFolderId,
							commerceApplicationModelId);

			return com.liferay.commerce.bom.model.
				CommerceBOMFolderApplicationRelSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCommerceBOMFolderApplicationRel(
			long commerceBOMFolderApplicationRelId)
		throws RemoteException {

		try {
			CommerceBOMFolderApplicationRelServiceUtil.
				deleteCommerceBOMFolderApplicationRel(
					commerceBOMFolderApplicationRelId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.bom.model.CommerceBOMFolderApplicationRelSoap[]
				getCommerceBOMFolderApplicationRelsByCAMId(
					long commerceApplicationModelId, int start, int end)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.bom.model.CommerceBOMFolderApplicationRel>
					returnValue =
						CommerceBOMFolderApplicationRelServiceUtil.
							getCommerceBOMFolderApplicationRelsByCAMId(
								commerceApplicationModelId, start, end);

			return com.liferay.commerce.bom.model.
				CommerceBOMFolderApplicationRelSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.bom.model.CommerceBOMFolderApplicationRelSoap[]
				getCommerceBOMFolderApplicationRelsByCommerceBOMFolderId(
					long commerceBOMFolderId, int start, int end)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.bom.model.CommerceBOMFolderApplicationRel>
					returnValue =
						CommerceBOMFolderApplicationRelServiceUtil.
							getCommerceBOMFolderApplicationRelsByCommerceBOMFolderId(
								commerceBOMFolderId, start, end);

			return com.liferay.commerce.bom.model.
				CommerceBOMFolderApplicationRelSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommerceBOMFolderApplicationRelsCountByCAMId(
			long commerceApplicationModelId)
		throws RemoteException {

		try {
			int returnValue =
				CommerceBOMFolderApplicationRelServiceUtil.
					getCommerceBOMFolderApplicationRelsCountByCAMId(
						commerceApplicationModelId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int
			getCommerceBOMFolderApplicationRelsCountByCommerceBOMFolderId(
				long commerceBOMFolderId)
		throws RemoteException {

		try {
			int returnValue =
				CommerceBOMFolderApplicationRelServiceUtil.
					getCommerceBOMFolderApplicationRelsCountByCommerceBOMFolderId(
						commerceBOMFolderId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceBOMFolderApplicationRelServiceSoap.class);

}