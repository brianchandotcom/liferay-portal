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

package com.liferay.commerce.price.list.service.http;

import com.liferay.commerce.price.list.service.CommercePriceListDiscountRelServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>CommercePriceListDiscountRelServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.price.list.model.CommercePriceListDiscountRelSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.price.list.model.CommercePriceListDiscountRel</code>, that is translated to a
 * <code>com.liferay.commerce.price.list.model.CommercePriceListDiscountRelSoap</code>. Methods that SOAP
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
 * @see CommercePriceListDiscountRelServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommercePriceListDiscountRelServiceSoap {

	public static
		com.liferay.commerce.price.list.model.CommercePriceListDiscountRelSoap
				addCommercePriceListDiscountRel(
					long commercePriceListId, long commerceDiscountId,
					int order,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws RemoteException {

		try {
			com.liferay.commerce.price.list.model.CommercePriceListDiscountRel
				returnValue =
					CommercePriceListDiscountRelServiceUtil.
						addCommercePriceListDiscountRel(
							commercePriceListId, commerceDiscountId, order,
							serviceContext);

			return com.liferay.commerce.price.list.model.
				CommercePriceListDiscountRelSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCommercePriceListDiscountRel(
			long commercePriceListDiscountRelId)
		throws RemoteException {

		try {
			CommercePriceListDiscountRelServiceUtil.
				deleteCommercePriceListDiscountRel(
					commercePriceListDiscountRelId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.price.list.model.CommercePriceListDiscountRelSoap
				fetchCommercePriceListDiscountRel(
					long commercePriceListId, long commerceDiscountId)
			throws RemoteException {

		try {
			com.liferay.commerce.price.list.model.CommercePriceListDiscountRel
				returnValue =
					CommercePriceListDiscountRelServiceUtil.
						fetchCommercePriceListDiscountRel(
							commercePriceListId, commerceDiscountId);

			return com.liferay.commerce.price.list.model.
				CommercePriceListDiscountRelSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.price.list.model.CommercePriceListDiscountRelSoap
				getCommercePriceListDiscountRel(
					long commercePriceListDiscountRelId)
			throws RemoteException {

		try {
			com.liferay.commerce.price.list.model.CommercePriceListDiscountRel
				returnValue =
					CommercePriceListDiscountRelServiceUtil.
						getCommercePriceListDiscountRel(
							commercePriceListDiscountRelId);

			return com.liferay.commerce.price.list.model.
				CommercePriceListDiscountRelSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.price.list.model.CommercePriceListDiscountRelSoap[]
				getCommercePriceListDiscountRels(long commercePriceListId)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.price.list.model.
					CommercePriceListDiscountRel> returnValue =
						CommercePriceListDiscountRelServiceUtil.
							getCommercePriceListDiscountRels(
								commercePriceListId);

			return com.liferay.commerce.price.list.model.
				CommercePriceListDiscountRelSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.price.list.model.CommercePriceListDiscountRelSoap[]
				getCommercePriceListDiscountRels(
					long commercePriceListId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.price.list.model.
							CommercePriceListDiscountRel> orderByComparator)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.price.list.model.
					CommercePriceListDiscountRel> returnValue =
						CommercePriceListDiscountRelServiceUtil.
							getCommercePriceListDiscountRels(
								commercePriceListId, start, end,
								orderByComparator);

			return com.liferay.commerce.price.list.model.
				CommercePriceListDiscountRelSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommercePriceListDiscountRelsCount(
			long commercePriceListId)
		throws RemoteException {

		try {
			int returnValue =
				CommercePriceListDiscountRelServiceUtil.
					getCommercePriceListDiscountRelsCount(commercePriceListId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommercePriceListDiscountRelServiceSoap.class);

}