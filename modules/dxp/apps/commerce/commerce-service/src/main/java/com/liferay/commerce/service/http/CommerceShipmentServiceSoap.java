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

import com.liferay.commerce.service.CommerceShipmentServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link CommerceShipmentServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.commerce.model.CommerceShipmentSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.commerce.model.CommerceShipment}, that is translated to a
 * {@link com.liferay.commerce.model.CommerceShipmentSoap}. Methods that SOAP cannot
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
 * @see CommerceShipmentServiceHttp
 * @see com.liferay.commerce.model.CommerceShipmentSoap
 * @see CommerceShipmentServiceUtil
 * @generated
 */
@ProviderType
public class CommerceShipmentServiceSoap {
	public static com.liferay.commerce.model.CommerceShipmentSoap addCommerceShipment(
		long commerceOrderId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.commerce.model.CommerceShipment returnValue = CommerceShipmentServiceUtil.addCommerceShipment(commerceOrderId,
					serviceContext);

			return com.liferay.commerce.model.CommerceShipmentSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteCommerceShipment(long commerceShipmentId)
		throws RemoteException {
		try {
			CommerceShipmentServiceUtil.deleteCommerceShipment(commerceShipmentId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceShipmentSoap getCommerceShipment(
		long commerceShipmentId) throws RemoteException {
		try {
			com.liferay.commerce.model.CommerceShipment returnValue = CommerceShipmentServiceUtil.getCommerceShipment(commerceShipmentId);

			return com.liferay.commerce.model.CommerceShipmentSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceShipmentSoap[] getCommerceShipmentsByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceShipment> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.model.CommerceShipment> returnValue =
				CommerceShipmentServiceUtil.getCommerceShipmentsByGroupId(groupId,
					start, end, orderByComparator);

			return com.liferay.commerce.model.CommerceShipmentSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceShipmentSoap[] getCommerceShipmentsBySiteGroupId(
		long siteGroupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceShipment> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.model.CommerceShipment> returnValue =
				CommerceShipmentServiceUtil.getCommerceShipmentsBySiteGroupId(siteGroupId,
					start, end, orderByComparator);

			return com.liferay.commerce.model.CommerceShipmentSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCommerceShipmentsCountByGroupId(long groupId)
		throws RemoteException {
		try {
			int returnValue = CommerceShipmentServiceUtil.getCommerceShipmentsCountByGroupId(groupId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCommerceShipmentsCountBySiteGroupId(long siteGroupId)
		throws RemoteException {
		try {
			int returnValue = CommerceShipmentServiceUtil.getCommerceShipmentsCountBySiteGroupId(siteGroupId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceShipmentSoap updateCommerceShipment(
		long commerceShipmentId, String carrier, String trackingNumber,
		int status, int shippingDateMonth, int shippingDateDay,
		int shippingDateYear, int shippingDateHour, int shippingDateMinute,
		int expectedDateMonth, int expectedDateDay, int expectedDateYear,
		int expectedDateHour, int expectedDateMinute) throws RemoteException {
		try {
			com.liferay.commerce.model.CommerceShipment returnValue = CommerceShipmentServiceUtil.updateCommerceShipment(commerceShipmentId,
					carrier, trackingNumber, status, shippingDateMonth,
					shippingDateDay, shippingDateYear, shippingDateHour,
					shippingDateMinute, expectedDateMonth, expectedDateDay,
					expectedDateYear, expectedDateHour, expectedDateMinute);

			return com.liferay.commerce.model.CommerceShipmentSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceShipmentServiceSoap.class);
}