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

package com.liferay.change.tracking.service.http;

import com.liferay.change.tracking.service.CTCollectionServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>CTCollectionServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.change.tracking.model.CTCollectionSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.change.tracking.model.CTCollection</code>, that is translated to a
 * <code>com.liferay.change.tracking.model.CTCollectionSoap</code>. Methods that SOAP
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
 * @see CTCollectionServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CTCollectionServiceSoap {

	public static com.liferay.change.tracking.model.CTCollectionSoap
			addCTCollection(
				long companyId, long userId, String name, String description)
		throws RemoteException {

		try {
			com.liferay.change.tracking.model.CTCollection returnValue =
				CTCollectionServiceUtil.addCTCollection(
					companyId, userId, name, description);

			return com.liferay.change.tracking.model.CTCollectionSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCTAutoResolutionInfo(long ctAutoResolutionInfoId)
		throws RemoteException {

		try {
			CTCollectionServiceUtil.deleteCTAutoResolutionInfo(
				ctAutoResolutionInfoId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.change.tracking.model.CTCollectionSoap
			deleteCTCollection(
				com.liferay.change.tracking.model.CTCollectionSoap ctCollection)
		throws RemoteException {

		try {
			com.liferay.change.tracking.model.CTCollection returnValue =
				CTCollectionServiceUtil.deleteCTCollection(
					com.liferay.change.tracking.model.impl.
						CTCollectionModelImpl.toModel(ctCollection));

			return com.liferay.change.tracking.model.CTCollectionSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void discardCTEntries(
			long ctCollectionId, long modelClassNameId, long modelClassPK)
		throws RemoteException {

		try {
			CTCollectionServiceUtil.discardCTEntries(
				ctCollectionId, modelClassNameId, modelClassPK);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void discardCTEntry(
			long ctCollectionId, long modelClassNameId, long modelClassPK)
		throws RemoteException {

		try {
			CTCollectionServiceUtil.discardCTEntry(
				ctCollectionId, modelClassNameId, modelClassPK);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.change.tracking.model.CTCollectionSoap[]
			getCTCollections(
				long companyId, int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.change.tracking.model.CTCollection>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.change.tracking.model.CTCollection>
				returnValue = CTCollectionServiceUtil.getCTCollections(
					companyId, status, start, end, orderByComparator);

			return com.liferay.change.tracking.model.CTCollectionSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.change.tracking.model.CTCollectionSoap[]
			getCTCollections(
				long companyId, int status, String keywords, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.change.tracking.model.CTCollection>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.change.tracking.model.CTCollection>
				returnValue = CTCollectionServiceUtil.getCTCollections(
					companyId, status, keywords, start, end, orderByComparator);

			return com.liferay.change.tracking.model.CTCollectionSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCTCollectionsCount(
			long companyId, int status, String keywords)
		throws RemoteException {

		try {
			int returnValue = CTCollectionServiceUtil.getCTCollectionsCount(
				companyId, status, keywords);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void publishCTCollection(long userId, long ctCollectionId)
		throws RemoteException {

		try {
			CTCollectionServiceUtil.publishCTCollection(userId, ctCollectionId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.change.tracking.model.CTCollectionSoap
			undoCTCollection(
				long ctCollectionId, long userId, String name,
				String description)
		throws RemoteException {

		try {
			com.liferay.change.tracking.model.CTCollection returnValue =
				CTCollectionServiceUtil.undoCTCollection(
					ctCollectionId, userId, name, description);

			return com.liferay.change.tracking.model.CTCollectionSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.change.tracking.model.CTCollectionSoap
			updateCTCollection(
				long userId, long ctCollectionId, String name,
				String description)
		throws RemoteException {

		try {
			com.liferay.change.tracking.model.CTCollection returnValue =
				CTCollectionServiceUtil.updateCTCollection(
					userId, ctCollectionId, name, description);

			return com.liferay.change.tracking.model.CTCollectionSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CTCollectionServiceSoap.class);

}