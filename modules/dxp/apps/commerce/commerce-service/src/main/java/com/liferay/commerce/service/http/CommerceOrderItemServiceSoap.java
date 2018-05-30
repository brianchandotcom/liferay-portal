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

package com.liferay.commerce.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.service.CommerceOrderItemServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link CommerceOrderItemServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.commerce.model.CommerceOrderItemSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.commerce.model.CommerceOrderItem}, that is translated to a
 * {@link com.liferay.commerce.model.CommerceOrderItemSoap}. Methods that SOAP cannot
 * safely wire are skipped.
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
 * @see CommerceOrderItemServiceHttp
 * @see com.liferay.commerce.model.CommerceOrderItemSoap
 * @see CommerceOrderItemServiceUtil
 * @generated
 */
@ProviderType
public class CommerceOrderItemServiceSoap {
	public static com.liferay.commerce.model.CommerceOrderItemSoap addCommerceOrderItem(
		long commerceOrderId, long cpInstanceId, int quantity,
		int shippedQuantity, String json, java.math.BigDecimal price,
		com.liferay.commerce.context.CommerceContext commerceContext,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.commerce.model.CommerceOrderItem returnValue = CommerceOrderItemServiceUtil.addCommerceOrderItem(commerceOrderId,
					cpInstanceId, quantity, shippedQuantity, json, price,
					commerceContext, serviceContext);

			return com.liferay.commerce.model.CommerceOrderItemSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteCommerceOrderItem(long commerceOrderItemId)
		throws RemoteException {
		try {
			CommerceOrderItemServiceUtil.deleteCommerceOrderItem(commerceOrderItemId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderItemSoap fetchCommerceOrderItem(
		long commerceOrderItemId) throws RemoteException {
		try {
			com.liferay.commerce.model.CommerceOrderItem returnValue = CommerceOrderItemServiceUtil.fetchCommerceOrderItem(commerceOrderItemId);

			return com.liferay.commerce.model.CommerceOrderItemSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderItemSoap getCommerceOrderItem(
		long commerceOrderItemId) throws RemoteException {
		try {
			com.liferay.commerce.model.CommerceOrderItem returnValue = CommerceOrderItemServiceUtil.getCommerceOrderItem(commerceOrderItemId);

			return com.liferay.commerce.model.CommerceOrderItemSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderItemSoap[] getCommerceOrderItems(
		long commerceOrderId, int start, int end) throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.model.CommerceOrderItem> returnValue =
				CommerceOrderItemServiceUtil.getCommerceOrderItems(commerceOrderId,
					start, end);

			return com.liferay.commerce.model.CommerceOrderItemSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCommerceOrderItemsCount(long commerceOrderId)
		throws RemoteException {
		try {
			int returnValue = CommerceOrderItemServiceUtil.getCommerceOrderItemsCount(commerceOrderId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCommerceOrderItemsQuantity(long commerceOrderId)
		throws RemoteException {
		try {
			int returnValue = CommerceOrderItemServiceUtil.getCommerceOrderItemsQuantity(commerceOrderId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderItemSoap updateCommerceOrderItem(
		long commerceOrderItemId, int quantity) throws RemoteException {
		try {
			com.liferay.commerce.model.CommerceOrderItem returnValue = CommerceOrderItemServiceUtil.updateCommerceOrderItem(commerceOrderItemId,
					quantity);

			return com.liferay.commerce.model.CommerceOrderItemSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderItemSoap updateCommerceOrderItem(
		long commerceOrderItemId, int quantity, String json,
		java.math.BigDecimal price) throws RemoteException {
		try {
			com.liferay.commerce.model.CommerceOrderItem returnValue = CommerceOrderItemServiceUtil.updateCommerceOrderItem(commerceOrderItemId,
					quantity, json, price);

			return com.liferay.commerce.model.CommerceOrderItemSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceOrderItemServiceSoap.class);
}