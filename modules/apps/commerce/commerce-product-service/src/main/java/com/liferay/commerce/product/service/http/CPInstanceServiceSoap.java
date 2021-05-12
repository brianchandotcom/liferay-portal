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

package com.liferay.commerce.product.service.http;

import com.liferay.commerce.product.service.CPInstanceServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>CPInstanceServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.product.model.CPInstanceSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.product.model.CPInstance</code>, that is translated to a
 * <code>com.liferay.commerce.product.model.CPInstanceSoap</code>. Methods that SOAP
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
 * @see CPInstanceServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CPInstanceServiceSoap {

	/**
	 * @param cpDefinitionId
	 * @param groupId
	 * @param sku
	 * @param gtin
	 * @param manufacturerPartNumber
	 * @param purchasable
	 * @param json
	 * @param published
	 * @param displayDateMonth
	 * @param displayDateDay
	 * @param displayDateYear
	 * @param displayDateHour
	 * @param displayDateMinute
	 * @param expirationDateMonth
	 * @param expirationDateDay
	 * @param expirationDateYear
	 * @param expirationDateHour
	 * @param expirationDateMinute
	 * @param neverExpire
	 * @param serviceContext
	 * @return
	 * @throws PortalException
	 * @deprecated As of Athanasius (7.3.x), use {@link #addCPInstance(long,
	 long, String, String, String, boolean, Map, boolean, int,
	 int, int, int, int, int, int, int, int, int, boolean,
	 ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.commerce.product.model.CPInstanceSoap
			addCPInstance(
				long cpDefinitionId, long groupId, String sku, String gtin,
				String manufacturerPartNumber, boolean purchasable, String json,
				boolean published, int displayDateMonth, int displayDateDay,
				int displayDateYear, int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CPInstance returnValue =
				CPInstanceServiceUtil.addCPInstance(
					cpDefinitionId, groupId, sku, gtin, manufacturerPartNumber,
					purchasable, json, published, displayDateMonth,
					displayDateDay, displayDateYear, displayDateHour,
					displayDateMinute, expirationDateMonth, expirationDateDay,
					expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, serviceContext);

			return com.liferay.commerce.product.model.CPInstanceSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * @param cpDefinitionId
	 * @param groupId
	 * @param sku
	 * @param gtin
	 * @param manufacturerPartNumber
	 * @param purchasable
	 * @param json
	 * @param published
	 * @param displayDateMonth
	 * @param displayDateDay
	 * @param displayDateYear
	 * @param displayDateHour
	 * @param displayDateMinute
	 * @param expirationDateMonth
	 * @param expirationDateDay
	 * @param expirationDateYear
	 * @param expirationDateHour
	 * @param expirationDateMinute
	 * @param neverExpire
	 * @param unspsc
	 * @param serviceContext
	 * @return
	 * @throws PortalException
	 * @deprecated As of Athanasius (7.3.x), use {@link #addCPInstance(long,
	 long, String, String, String, boolean, Map, boolean, int,
	 int, int, int, int, int, int, int, int, int, boolean,
	 String, ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.commerce.product.model.CPInstanceSoap
			addCPInstance(
				long cpDefinitionId, long groupId, String sku, String gtin,
				String manufacturerPartNumber, boolean purchasable, String json,
				boolean published, int displayDateMonth, int displayDateDay,
				int displayDateYear, int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire, String unspsc,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CPInstance returnValue =
				CPInstanceServiceUtil.addCPInstance(
					cpDefinitionId, groupId, sku, gtin, manufacturerPartNumber,
					purchasable, json, published, displayDateMonth,
					displayDateDay, displayDateYear, displayDateHour,
					displayDateMinute, expirationDateMonth, expirationDateDay,
					expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, unspsc, serviceContext);

			return com.liferay.commerce.product.model.CPInstanceSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPInstanceSoap
			addOrUpdateCPInstance(
				String externalReferenceCode, long cpDefinitionId, long groupId,
				String sku, String gtin, String manufacturerPartNumber,
				boolean purchasable, String json, double width, double height,
				double depth, double weight, java.math.BigDecimal price,
				java.math.BigDecimal promoPrice, java.math.BigDecimal cost,
				boolean published, int displayDateMonth, int displayDateDay,
				int displayDateYear, int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CPInstance returnValue =
				CPInstanceServiceUtil.addOrUpdateCPInstance(
					externalReferenceCode, cpDefinitionId, groupId, sku, gtin,
					manufacturerPartNumber, purchasable, json, width, height,
					depth, weight, price, promoPrice, cost, published,
					displayDateMonth, displayDateDay, displayDateYear,
					displayDateHour, displayDateMinute, expirationDateMonth,
					expirationDateDay, expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, serviceContext);

			return com.liferay.commerce.product.model.CPInstanceSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPInstanceSoap
			addOrUpdateCPInstance(
				String externalReferenceCode, long cpDefinitionId, long groupId,
				String sku, String gtin, String manufacturerPartNumber,
				boolean purchasable, String json, double width, double height,
				double depth, double weight, java.math.BigDecimal price,
				java.math.BigDecimal promoPrice, java.math.BigDecimal cost,
				boolean published, int displayDateMonth, int displayDateDay,
				int displayDateYear, int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire, String unspsc,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CPInstance returnValue =
				CPInstanceServiceUtil.addOrUpdateCPInstance(
					externalReferenceCode, cpDefinitionId, groupId, sku, gtin,
					manufacturerPartNumber, purchasable, json, width, height,
					depth, weight, price, promoPrice, cost, published,
					displayDateMonth, displayDateDay, displayDateYear,
					displayDateHour, displayDateMinute, expirationDateMonth,
					expirationDateDay, expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, unspsc, serviceContext);

			return com.liferay.commerce.product.model.CPInstanceSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void buildCPInstances(
			long cpDefinitionId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			CPInstanceServiceUtil.buildCPInstances(
				cpDefinitionId, serviceContext);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCPInstance(long cpInstanceId)
		throws RemoteException {

		try {
			CPInstanceServiceUtil.deleteCPInstance(cpInstanceId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPInstanceSoap
			fetchByExternalReferenceCode(
				String externalReferenceCode, long companyId)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CPInstance returnValue =
				CPInstanceServiceUtil.fetchByExternalReferenceCode(
					externalReferenceCode, companyId);

			return com.liferay.commerce.product.model.CPInstanceSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPInstanceSoap
			fetchCPInstance(long cpInstanceId)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CPInstance returnValue =
				CPInstanceServiceUtil.fetchCPInstance(cpInstanceId);

			return com.liferay.commerce.product.model.CPInstanceSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPInstanceSoap
			fetchCProductInstance(long cProductId, String cpInstanceUuid)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CPInstance returnValue =
				CPInstanceServiceUtil.fetchCProductInstance(
					cProductId, cpInstanceUuid);

			return com.liferay.commerce.product.model.CPInstanceSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPInstanceSoap[]
			getCPDefinitionInstances(
				long cpDefinitionId, int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.product.model.CPInstance>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.product.model.CPInstance>
				returnValue = CPInstanceServiceUtil.getCPDefinitionInstances(
					cpDefinitionId, status, start, end, orderByComparator);

			return com.liferay.commerce.product.model.CPInstanceSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCPDefinitionInstancesCount(
			long cpDefinitionId, int status)
		throws RemoteException {

		try {
			int returnValue =
				CPInstanceServiceUtil.getCPDefinitionInstancesCount(
					cpDefinitionId, status);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPInstanceSoap
			getCPInstance(long cpInstanceId)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CPInstance returnValue =
				CPInstanceServiceUtil.getCPInstance(cpInstanceId);

			return com.liferay.commerce.product.model.CPInstanceSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPInstanceSoap[]
			getCPInstances(
				long groupId, int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.product.model.CPInstance>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.product.model.CPInstance>
				returnValue = CPInstanceServiceUtil.getCPInstances(
					groupId, status, start, end, orderByComparator);

			return com.liferay.commerce.product.model.CPInstanceSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCPInstancesCount(long groupId, int status)
		throws RemoteException {

		try {
			int returnValue = CPInstanceServiceUtil.getCPInstancesCount(
				groupId, status);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPInstanceSoap
			updateCPInstance(
				long cpInstanceId, String sku, String gtin,
				String manufacturerPartNumber, boolean purchasable,
				boolean published, int displayDateMonth, int displayDateDay,
				int displayDateYear, int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CPInstance returnValue =
				CPInstanceServiceUtil.updateCPInstance(
					cpInstanceId, sku, gtin, manufacturerPartNumber,
					purchasable, published, displayDateMonth, displayDateDay,
					displayDateYear, displayDateHour, displayDateMinute,
					expirationDateMonth, expirationDateDay, expirationDateYear,
					expirationDateHour, expirationDateMinute, neverExpire,
					serviceContext);

			return com.liferay.commerce.product.model.CPInstanceSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPInstanceSoap
			updateCPInstance(
				long cpInstanceId, String sku, String gtin,
				String manufacturerPartNumber, boolean purchasable,
				boolean published, int displayDateMonth, int displayDateDay,
				int displayDateYear, int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire, String unspsc,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CPInstance returnValue =
				CPInstanceServiceUtil.updateCPInstance(
					cpInstanceId, sku, gtin, manufacturerPartNumber,
					purchasable, published, displayDateMonth, displayDateDay,
					displayDateYear, displayDateHour, displayDateMinute,
					expirationDateMonth, expirationDateDay, expirationDateYear,
					expirationDateHour, expirationDateMinute, neverExpire,
					unspsc, serviceContext);

			return com.liferay.commerce.product.model.CPInstanceSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPInstanceSoap
			updatePricingInfo(
				long cpInstanceId, java.math.BigDecimal price,
				java.math.BigDecimal promoPrice, java.math.BigDecimal cost,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CPInstance returnValue =
				CPInstanceServiceUtil.updatePricingInfo(
					cpInstanceId, price, promoPrice, cost, serviceContext);

			return com.liferay.commerce.product.model.CPInstanceSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPInstanceSoap
			updateShippingInfo(
				long cpInstanceId, double width, double height, double depth,
				double weight,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CPInstance returnValue =
				CPInstanceServiceUtil.updateShippingInfo(
					cpInstanceId, width, height, depth, weight, serviceContext);

			return com.liferay.commerce.product.model.CPInstanceSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CPInstanceServiceSoap.class);

}