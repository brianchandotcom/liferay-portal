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

import com.liferay.commerce.price.list.service.CommercePriceListCommerceAccountGroupRelServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>CommercePriceListCommerceAccountGroupRelServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.price.list.model.CommercePriceListCommerceAccountGroupRelSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.price.list.model.CommercePriceListCommerceAccountGroupRel</code>, that is translated to a
 * <code>com.liferay.commerce.price.list.model.CommercePriceListCommerceAccountGroupRelSoap</code>. Methods that SOAP
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
 * @see CommercePriceListCommerceAccountGroupRelServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommercePriceListCommerceAccountGroupRelServiceSoap {

	public static com.liferay.commerce.price.list.model.
		CommercePriceListCommerceAccountGroupRelSoap
				addCommercePriceListCommerceAccountGroupRel(
					long commercePriceListId, long commerceAccountGroupId,
					int order,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws RemoteException {

		try {
			com.liferay.commerce.price.list.model.
				CommercePriceListCommerceAccountGroupRel returnValue =
					CommercePriceListCommerceAccountGroupRelServiceUtil.
						addCommercePriceListCommerceAccountGroupRel(
							commercePriceListId, commerceAccountGroupId, order,
							serviceContext);

			return com.liferay.commerce.price.list.model.
				CommercePriceListCommerceAccountGroupRelSoap.toSoapModel(
					returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void
			deleteCommercePriceListAccountGroupRelsByCommercePriceListId(
				long commercePriceListId)
		throws RemoteException {

		try {
			CommercePriceListCommerceAccountGroupRelServiceUtil.
				deleteCommercePriceListAccountGroupRelsByCommercePriceListId(
					commercePriceListId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCommercePriceListCommerceAccountGroupRel(
			long commercePriceListCommerceAccountGroupRelId)
		throws RemoteException {

		try {
			CommercePriceListCommerceAccountGroupRelServiceUtil.
				deleteCommercePriceListCommerceAccountGroupRel(
					commercePriceListCommerceAccountGroupRelId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.price.list.model.
		CommercePriceListCommerceAccountGroupRelSoap
				fetchCommercePriceListCommerceAccountGroupRel(
					long commercePriceListId, long commerceAccountGroupId)
			throws RemoteException {

		try {
			com.liferay.commerce.price.list.model.
				CommercePriceListCommerceAccountGroupRel returnValue =
					CommercePriceListCommerceAccountGroupRelServiceUtil.
						fetchCommercePriceListCommerceAccountGroupRel(
							commercePriceListId, commerceAccountGroupId);

			return com.liferay.commerce.price.list.model.
				CommercePriceListCommerceAccountGroupRelSoap.toSoapModel(
					returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.price.list.model.
		CommercePriceListCommerceAccountGroupRelSoap
				getCommercePriceListCommerceAccountGroupRel(
					long commercePriceListCommerceAccoungGroupRelId)
			throws RemoteException {

		try {
			com.liferay.commerce.price.list.model.
				CommercePriceListCommerceAccountGroupRel returnValue =
					CommercePriceListCommerceAccountGroupRelServiceUtil.
						getCommercePriceListCommerceAccountGroupRel(
							commercePriceListCommerceAccoungGroupRelId);

			return com.liferay.commerce.price.list.model.
				CommercePriceListCommerceAccountGroupRelSoap.toSoapModel(
					returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.price.list.model.
		CommercePriceListCommerceAccountGroupRelSoap[]
				getCommercePriceListCommerceAccountGroupRels(
					long commercePriceListId)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.price.list.model.
					CommercePriceListCommerceAccountGroupRel> returnValue =
						CommercePriceListCommerceAccountGroupRelServiceUtil.
							getCommercePriceListCommerceAccountGroupRels(
								commercePriceListId);

			return com.liferay.commerce.price.list.model.
				CommercePriceListCommerceAccountGroupRelSoap.toSoapModels(
					returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.price.list.model.
		CommercePriceListCommerceAccountGroupRelSoap[]
				getCommercePriceListCommerceAccountGroupRels(
					long commercePriceListId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.price.list.model.
							CommercePriceListCommerceAccountGroupRel>
								orderByComparator)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.price.list.model.
					CommercePriceListCommerceAccountGroupRel> returnValue =
						CommercePriceListCommerceAccountGroupRelServiceUtil.
							getCommercePriceListCommerceAccountGroupRels(
								commercePriceListId, start, end,
								orderByComparator);

			return com.liferay.commerce.price.list.model.
				CommercePriceListCommerceAccountGroupRelSoap.toSoapModels(
					returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.price.list.model.
		CommercePriceListCommerceAccountGroupRelSoap[]
				getCommercePriceListCommerceAccountGroupRels(
					long commercePriceListId, String name, int start, int end)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.price.list.model.
					CommercePriceListCommerceAccountGroupRel> returnValue =
						CommercePriceListCommerceAccountGroupRelServiceUtil.
							getCommercePriceListCommerceAccountGroupRels(
								commercePriceListId, name, start, end);

			return com.liferay.commerce.price.list.model.
				CommercePriceListCommerceAccountGroupRelSoap.toSoapModels(
					returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommercePriceListCommerceAccountGroupRelsCount(
			long commercePriceListId)
		throws RemoteException {

		try {
			int returnValue =
				CommercePriceListCommerceAccountGroupRelServiceUtil.
					getCommercePriceListCommerceAccountGroupRelsCount(
						commercePriceListId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommercePriceListCommerceAccountGroupRelsCount(
			long commercePriceListId, String name)
		throws RemoteException {

		try {
			int returnValue =
				CommercePriceListCommerceAccountGroupRelServiceUtil.
					getCommercePriceListCommerceAccountGroupRelsCount(
						commercePriceListId, name);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.price.list.model.
		CommercePriceListCommerceAccountGroupRelSoap
				updateCommercePriceListCommerceAccountGroupRel(
					long commercePriceListCommerceAccountGroupRelId, int order,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws RemoteException {

		try {
			com.liferay.commerce.price.list.model.
				CommercePriceListCommerceAccountGroupRel returnValue =
					CommercePriceListCommerceAccountGroupRelServiceUtil.
						updateCommercePriceListCommerceAccountGroupRel(
							commercePriceListCommerceAccountGroupRelId, order,
							serviceContext);

			return com.liferay.commerce.price.list.model.
				CommercePriceListCommerceAccountGroupRelSoap.toSoapModel(
					returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommercePriceListCommerceAccountGroupRelServiceSoap.class);

}