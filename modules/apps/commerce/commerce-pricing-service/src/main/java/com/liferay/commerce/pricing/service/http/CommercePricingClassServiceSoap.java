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

package com.liferay.commerce.pricing.service.http;

import com.liferay.commerce.pricing.service.CommercePricingClassServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>CommercePricingClassServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.pricing.model.CommercePricingClassSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.pricing.model.CommercePricingClass</code>, that is translated to a
 * <code>com.liferay.commerce.pricing.model.CommercePricingClassSoap</code>. Methods that SOAP
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
 * @author Riccardo Alberti
 * @see CommercePricingClassServiceHttp
 * @generated
 */
public class CommercePricingClassServiceSoap {

	public static com.liferay.commerce.pricing.model.CommercePricingClassSoap
			addCommercePricingClass(
				long userId, long groupId, String title, String description,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.pricing.model.CommercePricingClass
				returnValue =
					CommercePricingClassServiceUtil.addCommercePricingClass(
						userId, groupId, title, description, serviceContext);

			return com.liferay.commerce.pricing.model.CommercePricingClassSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.pricing.model.CommercePricingClassSoap
			deleteCommercePricingClass(long commercePricingClassId)
		throws RemoteException {

		try {
			com.liferay.commerce.pricing.model.CommercePricingClass
				returnValue =
					CommercePricingClassServiceUtil.deleteCommercePricingClass(
						commercePricingClassId);

			return com.liferay.commerce.pricing.model.CommercePricingClassSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.pricing.model.CommercePricingClassSoap
			fetchByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws RemoteException {

		try {
			com.liferay.commerce.pricing.model.CommercePricingClass
				returnValue =
					CommercePricingClassServiceUtil.
						fetchByExternalReferenceCode(
							companyId, externalReferenceCode);

			return com.liferay.commerce.pricing.model.CommercePricingClassSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.pricing.model.CommercePricingClassSoap
			fetchCommercePricingClass(long commercePricingClassId)
		throws RemoteException {

		try {
			com.liferay.commerce.pricing.model.CommercePricingClass
				returnValue =
					CommercePricingClassServiceUtil.fetchCommercePricingClass(
						commercePricingClassId);

			return com.liferay.commerce.pricing.model.CommercePricingClassSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.pricing.model.CommercePricingClassSoap
			getCommercePricingClass(long commercePricingClassId)
		throws RemoteException {

		try {
			com.liferay.commerce.pricing.model.CommercePricingClass
				returnValue =
					CommercePricingClassServiceUtil.getCommercePricingClass(
						commercePricingClassId);

			return com.liferay.commerce.pricing.model.CommercePricingClassSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.pricing.model.CommercePricingClassSoap[]
			getCommercePricingClasses(
				long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.pricing.model.CommercePricingClass>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.pricing.model.CommercePricingClass>
					returnValue =
						CommercePricingClassServiceUtil.
							getCommercePricingClasses(
								companyId, start, end, orderByComparator);

			return com.liferay.commerce.pricing.model.CommercePricingClassSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommercePricingClassesCount(long companyId)
		throws RemoteException {

		try {
			int returnValue =
				CommercePricingClassServiceUtil.getCommercePricingClassesCount(
					companyId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.pricing.model.CommercePricingClassSoap
			updateCommercePricingClass(
				long commercePricingClassId, long userId, long groupId,
				String title, String description,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.pricing.model.CommercePricingClass
				returnValue =
					CommercePricingClassServiceUtil.updateCommercePricingClass(
						commercePricingClassId, userId, groupId, title,
						description, serviceContext);

			return com.liferay.commerce.pricing.model.CommercePricingClassSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.pricing.model.CommercePricingClassSoap
			upsertCommercePricingClass(
				long commercePricingClassId, long userId, long groupId,
				String title, String description, String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.pricing.model.CommercePricingClass
				returnValue =
					CommercePricingClassServiceUtil.upsertCommercePricingClass(
						commercePricingClassId, userId, groupId, title,
						description, externalReferenceCode, serviceContext);

			return com.liferay.commerce.pricing.model.CommercePricingClassSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommercePricingClassServiceSoap.class);

}