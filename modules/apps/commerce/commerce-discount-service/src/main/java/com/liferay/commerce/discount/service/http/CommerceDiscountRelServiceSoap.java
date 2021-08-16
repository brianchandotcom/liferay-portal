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

package com.liferay.commerce.discount.service.http;

import com.liferay.commerce.discount.service.CommerceDiscountRelServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>CommerceDiscountRelServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.discount.model.CommerceDiscountRelSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.discount.model.CommerceDiscountRel</code>, that is translated to a
 * <code>com.liferay.commerce.discount.model.CommerceDiscountRelSoap</code>. Methods that SOAP
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
 * @see CommerceDiscountRelServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommerceDiscountRelServiceSoap {

	public static com.liferay.commerce.discount.model.CommerceDiscountRelSoap
			addCommerceDiscountRel(
				long commerceDiscountId, String className, long classPK,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.discount.model.CommerceDiscountRel
				returnValue =
					CommerceDiscountRelServiceUtil.addCommerceDiscountRel(
						commerceDiscountId, className, classPK, serviceContext);

			return com.liferay.commerce.discount.model.CommerceDiscountRelSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCommerceDiscountRel(long commerceDiscountRelId)
		throws RemoteException {

		try {
			CommerceDiscountRelServiceUtil.deleteCommerceDiscountRel(
				commerceDiscountRelId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.discount.model.CommerceDiscountRelSoap
			fetchCommerceDiscountRel(String className, long classPK)
		throws RemoteException {

		try {
			com.liferay.commerce.discount.model.CommerceDiscountRel
				returnValue =
					CommerceDiscountRelServiceUtil.fetchCommerceDiscountRel(
						className, classPK);

			return com.liferay.commerce.discount.model.CommerceDiscountRelSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.discount.model.CommerceDiscountRelSoap[]
			getCategoriesByCommerceDiscountId(
				long commerceDiscountId, String name, int start, int end)
		throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.discount.model.CommerceDiscountRel>
					returnValue =
						CommerceDiscountRelServiceUtil.
							getCategoriesByCommerceDiscountId(
								commerceDiscountId, name, start, end);

			return com.liferay.commerce.discount.model.CommerceDiscountRelSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCategoriesByCommerceDiscountIdCount(
			long commerceDiscountId, String name)
		throws RemoteException {

		try {
			int returnValue =
				CommerceDiscountRelServiceUtil.
					getCategoriesByCommerceDiscountIdCount(
						commerceDiscountId, name);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static long[] getClassPKs(long commerceDiscountId, String className)
		throws RemoteException {

		try {
			long[] returnValue = CommerceDiscountRelServiceUtil.getClassPKs(
				commerceDiscountId, className);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.discount.model.CommerceDiscountRelSoap
			getCommerceDiscountRel(long commerceDiscountRelId)
		throws RemoteException {

		try {
			com.liferay.commerce.discount.model.CommerceDiscountRel
				returnValue =
					CommerceDiscountRelServiceUtil.getCommerceDiscountRel(
						commerceDiscountRelId);

			return com.liferay.commerce.discount.model.CommerceDiscountRelSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.discount.model.CommerceDiscountRelSoap[]
			getCommerceDiscountRels(long commerceDiscountId, String className)
		throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.discount.model.CommerceDiscountRel>
					returnValue =
						CommerceDiscountRelServiceUtil.getCommerceDiscountRels(
							commerceDiscountId, className);

			return com.liferay.commerce.discount.model.CommerceDiscountRelSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.discount.model.CommerceDiscountRelSoap[]
			getCommerceDiscountRels(
				long commerceDiscountId, String className, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.discount.model.CommerceDiscountRel>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.discount.model.CommerceDiscountRel>
					returnValue =
						CommerceDiscountRelServiceUtil.getCommerceDiscountRels(
							commerceDiscountId, className, start, end,
							orderByComparator);

			return com.liferay.commerce.discount.model.CommerceDiscountRelSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommerceDiscountRelsCount(
			long commerceDiscountId, String className)
		throws RemoteException {

		try {
			int returnValue =
				CommerceDiscountRelServiceUtil.getCommerceDiscountRelsCount(
					commerceDiscountId, className);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.discount.model.CommerceDiscountRelSoap[]
			getCommercePricingClassesByCommerceDiscountId(
				long commerceDiscountId, String title, int start, int end)
		throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.discount.model.CommerceDiscountRel>
					returnValue =
						CommerceDiscountRelServiceUtil.
							getCommercePricingClassesByCommerceDiscountId(
								commerceDiscountId, title, start, end);

			return com.liferay.commerce.discount.model.CommerceDiscountRelSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommercePricingClassesByCommerceDiscountIdCount(
			long commerceDiscountId, String title)
		throws RemoteException {

		try {
			int returnValue =
				CommerceDiscountRelServiceUtil.
					getCommercePricingClassesByCommerceDiscountIdCount(
						commerceDiscountId, title);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.discount.model.CommerceDiscountRelSoap[]
			getCPDefinitionsByCommerceDiscountId(
				long commerceDiscountId, String name, String languageId,
				int start, int end)
		throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.discount.model.CommerceDiscountRel>
					returnValue =
						CommerceDiscountRelServiceUtil.
							getCPDefinitionsByCommerceDiscountId(
								commerceDiscountId, name, languageId, start,
								end);

			return com.liferay.commerce.discount.model.CommerceDiscountRelSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCPDefinitionsByCommerceDiscountIdCount(
			long commerceDiscountId, String name, String languageId)
		throws RemoteException {

		try {
			int returnValue =
				CommerceDiscountRelServiceUtil.
					getCPDefinitionsByCommerceDiscountIdCount(
						commerceDiscountId, name, languageId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.discount.model.CommerceDiscountRelSoap[]
			getCPInstancesByCommerceDiscountId(
				long commerceDiscountId, String sku, int start, int end)
		throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.discount.model.CommerceDiscountRel>
					returnValue =
						CommerceDiscountRelServiceUtil.
							getCPInstancesByCommerceDiscountId(
								commerceDiscountId, sku, start, end);

			return com.liferay.commerce.discount.model.CommerceDiscountRelSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCPInstancesByCommerceDiscountIdCount(
			long commerceDiscountId, String sku)
		throws RemoteException {

		try {
			int returnValue =
				CommerceDiscountRelServiceUtil.
					getCPInstancesByCommerceDiscountIdCount(
						commerceDiscountId, sku);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceDiscountRelServiceSoap.class);

}