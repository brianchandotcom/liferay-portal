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

package com.liferay.commerce.inventory.service.http;

import com.liferay.commerce.inventory.service.CommerceInventoryReplenishmentItemServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>CommerceInventoryReplenishmentItemServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.inventory.model.CommerceInventoryReplenishmentItemSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.inventory.model.CommerceInventoryReplenishmentItem</code>, that is translated to a
 * <code>com.liferay.commerce.inventory.model.CommerceInventoryReplenishmentItemSoap</code>. Methods that SOAP
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
 * @see CommerceInventoryReplenishmentItemServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommerceInventoryReplenishmentItemServiceSoap {

	public static
		com.liferay.commerce.inventory.model.
			CommerceInventoryReplenishmentItemSoap
					addCommerceInventoryReplenishmentItem(
						long commerceInventoryWarehouseId, String sku,
						java.util.Date availabilityDate, int quantity)
				throws RemoteException {

		try {
			com.liferay.commerce.inventory.model.
				CommerceInventoryReplenishmentItem returnValue =
					CommerceInventoryReplenishmentItemServiceUtil.
						addCommerceInventoryReplenishmentItem(
							commerceInventoryWarehouseId, sku, availabilityDate,
							quantity);

			return com.liferay.commerce.inventory.model.
				CommerceInventoryReplenishmentItemSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCommerceInventoryReplenishmentItem(
			long commerceInventoryReplenishmentItemId)
		throws RemoteException {

		try {
			CommerceInventoryReplenishmentItemServiceUtil.
				deleteCommerceInventoryReplenishmentItem(
					commerceInventoryReplenishmentItemId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.inventory.model.
			CommerceInventoryReplenishmentItemSoap
					getCommerceInventoryReplenishmentItem(
						long commerceInventoryReplenishmentItemId)
				throws RemoteException {

		try {
			com.liferay.commerce.inventory.model.
				CommerceInventoryReplenishmentItem returnValue =
					CommerceInventoryReplenishmentItemServiceUtil.
						getCommerceInventoryReplenishmentItem(
							commerceInventoryReplenishmentItemId);

			return com.liferay.commerce.inventory.model.
				CommerceInventoryReplenishmentItemSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.inventory.model.
		CommerceInventoryReplenishmentItemSoap[]
				getCommerceInventoryReplenishmentItemsByCompanyIdAndSku(
					long companyId, String sku, int start, int end)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.inventory.model.
					CommerceInventoryReplenishmentItem> returnValue =
						CommerceInventoryReplenishmentItemServiceUtil.
							getCommerceInventoryReplenishmentItemsByCompanyIdAndSku(
								companyId, sku, start, end);

			return com.liferay.commerce.inventory.model.
				CommerceInventoryReplenishmentItemSoap.toSoapModels(
					returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static long getCommerceInventoryReplenishmentItemsCount(
			long commerceInventoryWarehouseId, String sku)
		throws RemoteException {

		try {
			long returnValue =
				CommerceInventoryReplenishmentItemServiceUtil.
					getCommerceInventoryReplenishmentItemsCount(
						commerceInventoryWarehouseId, sku);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int
			getCommerceInventoryReplenishmentItemsCountByCompanyIdAndSku(
				long companyId, String sku)
		throws RemoteException {

		try {
			int returnValue =
				CommerceInventoryReplenishmentItemServiceUtil.
					getCommerceInventoryReplenishmentItemsCountByCompanyIdAndSku(
						companyId, sku);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.inventory.model.
			CommerceInventoryReplenishmentItemSoap
					updateCommerceInventoryReplenishmentItem(
						long commerceInventoryReplenishmentItemId,
						java.util.Date availabilityDate, int quantity,
						long mvccVersion)
				throws RemoteException {

		try {
			com.liferay.commerce.inventory.model.
				CommerceInventoryReplenishmentItem returnValue =
					CommerceInventoryReplenishmentItemServiceUtil.
						updateCommerceInventoryReplenishmentItem(
							commerceInventoryReplenishmentItemId,
							availabilityDate, quantity, mvccVersion);

			return com.liferay.commerce.inventory.model.
				CommerceInventoryReplenishmentItemSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceInventoryReplenishmentItemServiceSoap.class);

}