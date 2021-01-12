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

import com.liferay.commerce.price.list.service.CommercePriceListChannelRelServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>CommercePriceListChannelRelServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.price.list.model.CommercePriceListChannelRelSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.price.list.model.CommercePriceListChannelRel</code>, that is translated to a
 * <code>com.liferay.commerce.price.list.model.CommercePriceListChannelRelSoap</code>. Methods that SOAP
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
 * @see CommercePriceListChannelRelServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommercePriceListChannelRelServiceSoap {

	public static
		com.liferay.commerce.price.list.model.CommercePriceListChannelRelSoap
				addCommercePriceListChannelRel(
					long commercePriceListId, long commerceChannelId, int order,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws RemoteException {

		try {
			com.liferay.commerce.price.list.model.CommercePriceListChannelRel
				returnValue =
					CommercePriceListChannelRelServiceUtil.
						addCommercePriceListChannelRel(
							commercePriceListId, commerceChannelId, order,
							serviceContext);

			return com.liferay.commerce.price.list.model.
				CommercePriceListChannelRelSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCommercePriceListChannelRel(
			long commercePriceListChannelRelId)
		throws RemoteException {

		try {
			CommercePriceListChannelRelServiceUtil.
				deleteCommercePriceListChannelRel(
					commercePriceListChannelRelId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCommercePriceListChannelRelsByCommercePriceListId(
			long commercePriceListId)
		throws RemoteException {

		try {
			CommercePriceListChannelRelServiceUtil.
				deleteCommercePriceListChannelRelsByCommercePriceListId(
					commercePriceListId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.price.list.model.CommercePriceListChannelRelSoap
				fetchCommercePriceListChannelRel(
					long commerceChannelId, long commercePriceListId)
			throws RemoteException {

		try {
			com.liferay.commerce.price.list.model.CommercePriceListChannelRel
				returnValue =
					CommercePriceListChannelRelServiceUtil.
						fetchCommercePriceListChannelRel(
							commerceChannelId, commercePriceListId);

			return com.liferay.commerce.price.list.model.
				CommercePriceListChannelRelSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.price.list.model.CommercePriceListChannelRelSoap
				getCommercePriceListChannelRel(
					long commercePriceListChannelRelId)
			throws RemoteException {

		try {
			com.liferay.commerce.price.list.model.CommercePriceListChannelRel
				returnValue =
					CommercePriceListChannelRelServiceUtil.
						getCommercePriceListChannelRel(
							commercePriceListChannelRelId);

			return com.liferay.commerce.price.list.model.
				CommercePriceListChannelRelSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.price.list.model.CommercePriceListChannelRelSoap[]
				getCommercePriceListChannelRels(long commercePriceListId)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.price.list.model.
					CommercePriceListChannelRel> returnValue =
						CommercePriceListChannelRelServiceUtil.
							getCommercePriceListChannelRels(
								commercePriceListId);

			return com.liferay.commerce.price.list.model.
				CommercePriceListChannelRelSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.price.list.model.CommercePriceListChannelRelSoap[]
				getCommercePriceListChannelRels(
					long commercePriceListId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.price.list.model.
							CommercePriceListChannelRel> orderByComparator)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.price.list.model.
					CommercePriceListChannelRel> returnValue =
						CommercePriceListChannelRelServiceUtil.
							getCommercePriceListChannelRels(
								commercePriceListId, start, end,
								orderByComparator);

			return com.liferay.commerce.price.list.model.
				CommercePriceListChannelRelSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.price.list.model.CommercePriceListChannelRelSoap[]
				getCommercePriceListChannelRels(
					long commercePriceListId, String name, int start, int end)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.price.list.model.
					CommercePriceListChannelRel> returnValue =
						CommercePriceListChannelRelServiceUtil.
							getCommercePriceListChannelRels(
								commercePriceListId, name, start, end);

			return com.liferay.commerce.price.list.model.
				CommercePriceListChannelRelSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommercePriceListChannelRelsCount(
			long commercePriceListId)
		throws RemoteException {

		try {
			int returnValue =
				CommercePriceListChannelRelServiceUtil.
					getCommercePriceListChannelRelsCount(commercePriceListId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommercePriceListChannelRelsCount(
			long commercePriceListId, String name)
		throws RemoteException {

		try {
			int returnValue =
				CommercePriceListChannelRelServiceUtil.
					getCommercePriceListChannelRelsCount(
						commercePriceListId, name);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommercePriceListChannelRelServiceSoap.class);

}