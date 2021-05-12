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

import com.liferay.commerce.price.list.service.CommerceTierPriceEntryServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>CommerceTierPriceEntryServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.price.list.model.CommerceTierPriceEntrySoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.price.list.model.CommerceTierPriceEntry</code>, that is translated to a
 * <code>com.liferay.commerce.price.list.model.CommerceTierPriceEntrySoap</code>. Methods that SOAP
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
 * @see CommerceTierPriceEntryServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommerceTierPriceEntryServiceSoap {

	public static
		com.liferay.commerce.price.list.model.CommerceTierPriceEntrySoap
				addCommerceTierPriceEntry(
					long commercePriceEntryId, java.math.BigDecimal price,
					java.math.BigDecimal promoPrice, int minQuantity,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws RemoteException {

		try {
			com.liferay.commerce.price.list.model.CommerceTierPriceEntry
				returnValue =
					CommerceTierPriceEntryServiceUtil.addCommerceTierPriceEntry(
						commercePriceEntryId, price, promoPrice, minQuantity,
						serviceContext);

			return com.liferay.commerce.price.list.model.
				CommerceTierPriceEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.price.list.model.CommerceTierPriceEntrySoap
				addCommerceTierPriceEntry(
					String externalReferenceCode, long commercePriceEntryId,
					java.math.BigDecimal price, java.math.BigDecimal promoPrice,
					int minQuantity,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws RemoteException {

		try {
			com.liferay.commerce.price.list.model.CommerceTierPriceEntry
				returnValue =
					CommerceTierPriceEntryServiceUtil.addCommerceTierPriceEntry(
						externalReferenceCode, commercePriceEntryId, price,
						promoPrice, minQuantity, serviceContext);

			return com.liferay.commerce.price.list.model.
				CommerceTierPriceEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.price.list.model.CommerceTierPriceEntrySoap
				addCommerceTierPriceEntry(
					String externalReferenceCode, long commercePriceEntryId,
					java.math.BigDecimal price, int minQuantity,
					boolean bulkPricing, boolean discountDiscovery,
					java.math.BigDecimal discountLevel1,
					java.math.BigDecimal discountLevel2,
					java.math.BigDecimal discountLevel3,
					java.math.BigDecimal discountLevel4, int displayDateMonth,
					int displayDateDay, int displayDateYear,
					int displayDateHour, int displayDateMinute,
					int expirationDateMonth, int expirationDateDay,
					int expirationDateYear, int expirationDateHour,
					int expirationDateMinute, boolean neverExpire,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws RemoteException {

		try {
			com.liferay.commerce.price.list.model.CommerceTierPriceEntry
				returnValue =
					CommerceTierPriceEntryServiceUtil.addCommerceTierPriceEntry(
						externalReferenceCode, commercePriceEntryId, price,
						minQuantity, bulkPricing, discountDiscovery,
						discountLevel1, discountLevel2, discountLevel3,
						discountLevel4, displayDateMonth, displayDateDay,
						displayDateYear, displayDateHour, displayDateMinute,
						expirationDateMonth, expirationDateDay,
						expirationDateYear, expirationDateHour,
						expirationDateMinute, neverExpire, serviceContext);

			return com.liferay.commerce.price.list.model.
				CommerceTierPriceEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.price.list.model.CommerceTierPriceEntrySoap
				addOrUpdateCommerceTierPriceEntry(
					String externalReferenceCode, long commerceTierPriceEntryId,
					long commercePriceEntryId, java.math.BigDecimal price,
					java.math.BigDecimal promoPrice, int minQuantity,
					String priceEntryExternalReferenceCode,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws RemoteException {

		try {
			com.liferay.commerce.price.list.model.CommerceTierPriceEntry
				returnValue =
					CommerceTierPriceEntryServiceUtil.
						addOrUpdateCommerceTierPriceEntry(
							externalReferenceCode, commerceTierPriceEntryId,
							commercePriceEntryId, price, promoPrice,
							minQuantity, priceEntryExternalReferenceCode,
							serviceContext);

			return com.liferay.commerce.price.list.model.
				CommerceTierPriceEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.price.list.model.CommerceTierPriceEntrySoap
				addOrUpdateCommerceTierPriceEntry(
					String externalReferenceCode, long commerceTierPriceEntryId,
					long commercePriceEntryId, java.math.BigDecimal price,
					int minQuantity, boolean bulkPricing,
					boolean discountDiscovery,
					java.math.BigDecimal discountLevel1,
					java.math.BigDecimal discountLevel2,
					java.math.BigDecimal discountLevel3,
					java.math.BigDecimal discountLevel4, int displayDateMonth,
					int displayDateDay, int displayDateYear,
					int displayDateHour, int displayDateMinute,
					int expirationDateMonth, int expirationDateDay,
					int expirationDateYear, int expirationDateHour,
					int expirationDateMinute, boolean neverExpire,
					String priceEntryExternalReferenceCode,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws RemoteException {

		try {
			com.liferay.commerce.price.list.model.CommerceTierPriceEntry
				returnValue =
					CommerceTierPriceEntryServiceUtil.
						addOrUpdateCommerceTierPriceEntry(
							externalReferenceCode, commerceTierPriceEntryId,
							commercePriceEntryId, price, minQuantity,
							bulkPricing, discountDiscovery, discountLevel1,
							discountLevel2, discountLevel3, discountLevel4,
							displayDateMonth, displayDateDay, displayDateYear,
							displayDateHour, displayDateMinute,
							expirationDateMonth, expirationDateDay,
							expirationDateYear, expirationDateHour,
							expirationDateMinute, neverExpire,
							priceEntryExternalReferenceCode, serviceContext);

			return com.liferay.commerce.price.list.model.
				CommerceTierPriceEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCommerceTierPriceEntry(
			long commerceTierPriceEntryId)
		throws RemoteException {

		try {
			CommerceTierPriceEntryServiceUtil.deleteCommerceTierPriceEntry(
				commerceTierPriceEntryId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.price.list.model.CommerceTierPriceEntrySoap
				fetchByExternalReferenceCode(
					String externalReferenceCode, long companyId)
			throws RemoteException {

		try {
			com.liferay.commerce.price.list.model.CommerceTierPriceEntry
				returnValue =
					CommerceTierPriceEntryServiceUtil.
						fetchByExternalReferenceCode(
							externalReferenceCode, companyId);

			return com.liferay.commerce.price.list.model.
				CommerceTierPriceEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static
		com.liferay.commerce.price.list.model.CommerceTierPriceEntrySoap[]
				fetchCommerceTierPriceEntries(
					long companyId, int start, int end)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.price.list.model.CommerceTierPriceEntry>
					returnValue =
						CommerceTierPriceEntryServiceUtil.
							fetchCommerceTierPriceEntries(
								companyId, start, end);

			return com.liferay.commerce.price.list.model.
				CommerceTierPriceEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.price.list.model.CommerceTierPriceEntrySoap
				fetchCommerceTierPriceEntry(long commerceTierPriceEntryId)
			throws RemoteException {

		try {
			com.liferay.commerce.price.list.model.CommerceTierPriceEntry
				returnValue =
					CommerceTierPriceEntryServiceUtil.
						fetchCommerceTierPriceEntry(commerceTierPriceEntryId);

			return com.liferay.commerce.price.list.model.
				CommerceTierPriceEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.price.list.model.CommerceTierPriceEntrySoap[]
				getCommerceTierPriceEntries(
					long commercePriceEntryId, int start, int end)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.price.list.model.CommerceTierPriceEntry>
					returnValue =
						CommerceTierPriceEntryServiceUtil.
							getCommerceTierPriceEntries(
								commercePriceEntryId, start, end);

			return com.liferay.commerce.price.list.model.
				CommerceTierPriceEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.price.list.model.CommerceTierPriceEntrySoap[]
				getCommerceTierPriceEntries(
					long commercePriceEntryId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.price.list.model.
							CommerceTierPriceEntry> orderByComparator)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.price.list.model.CommerceTierPriceEntry>
					returnValue =
						CommerceTierPriceEntryServiceUtil.
							getCommerceTierPriceEntries(
								commercePriceEntryId, start, end,
								orderByComparator);

			return com.liferay.commerce.price.list.model.
				CommerceTierPriceEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommerceTierPriceEntriesCount(
			long commercePriceEntryId)
		throws RemoteException {

		try {
			int returnValue =
				CommerceTierPriceEntryServiceUtil.
					getCommerceTierPriceEntriesCount(commercePriceEntryId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static int getCommerceTierPriceEntriesCountByCompanyId(
			long companyId)
		throws RemoteException {

		try {
			int returnValue =
				CommerceTierPriceEntryServiceUtil.
					getCommerceTierPriceEntriesCountByCompanyId(companyId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.price.list.model.CommerceTierPriceEntrySoap
				getCommerceTierPriceEntry(long commerceTierPriceEntryId)
			throws RemoteException {

		try {
			com.liferay.commerce.price.list.model.CommerceTierPriceEntry
				returnValue =
					CommerceTierPriceEntryServiceUtil.getCommerceTierPriceEntry(
						commerceTierPriceEntryId);

			return com.liferay.commerce.price.list.model.
				CommerceTierPriceEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int searchCommerceTierPriceEntriesCount(
			long companyId, long commercePriceEntryId, String keywords)
		throws RemoteException {

		try {
			int returnValue =
				CommerceTierPriceEntryServiceUtil.
					searchCommerceTierPriceEntriesCount(
						companyId, commercePriceEntryId, keywords);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.price.list.model.CommerceTierPriceEntrySoap
				updateCommerceTierPriceEntry(
					long commerceTierPriceEntryId, java.math.BigDecimal price,
					java.math.BigDecimal promoPrice, int minQuantity,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws RemoteException {

		try {
			com.liferay.commerce.price.list.model.CommerceTierPriceEntry
				returnValue =
					CommerceTierPriceEntryServiceUtil.
						updateCommerceTierPriceEntry(
							commerceTierPriceEntryId, price, promoPrice,
							minQuantity, serviceContext);

			return com.liferay.commerce.price.list.model.
				CommerceTierPriceEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.price.list.model.CommerceTierPriceEntrySoap
				updateCommerceTierPriceEntry(
					long commerceTierPriceEntryId, java.math.BigDecimal price,
					int minQuantity, boolean bulkPricing,
					boolean discountDiscovery,
					java.math.BigDecimal discountLevel1,
					java.math.BigDecimal discountLevel2,
					java.math.BigDecimal discountLevel3,
					java.math.BigDecimal discountLevel4, int displayDateMonth,
					int displayDateDay, int displayDateYear,
					int displayDateHour, int displayDateMinute,
					int expirationDateMonth, int expirationDateDay,
					int expirationDateYear, int expirationDateHour,
					int expirationDateMinute, boolean neverExpire,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws RemoteException {

		try {
			com.liferay.commerce.price.list.model.CommerceTierPriceEntry
				returnValue =
					CommerceTierPriceEntryServiceUtil.
						updateCommerceTierPriceEntry(
							commerceTierPriceEntryId, price, minQuantity,
							bulkPricing, discountDiscovery, discountLevel1,
							discountLevel2, discountLevel3, discountLevel4,
							displayDateMonth, displayDateDay, displayDateYear,
							displayDateHour, displayDateMinute,
							expirationDateMonth, expirationDateDay,
							expirationDateYear, expirationDateHour,
							expirationDateMinute, neverExpire, serviceContext);

			return com.liferay.commerce.price.list.model.
				CommerceTierPriceEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.price.list.model.CommerceTierPriceEntrySoap
				updateExternalReferenceCode(
					com.liferay.commerce.price.list.model.
						CommerceTierPriceEntrySoap commerceTierPriceEntry,
					String externalReferenceCode)
			throws RemoteException {

		try {
			com.liferay.commerce.price.list.model.CommerceTierPriceEntry
				returnValue =
					CommerceTierPriceEntryServiceUtil.
						updateExternalReferenceCode(
							com.liferay.commerce.price.list.model.impl.
								CommerceTierPriceEntryModelImpl.toModel(
									commerceTierPriceEntry),
							externalReferenceCode);

			return com.liferay.commerce.price.list.model.
				CommerceTierPriceEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceTierPriceEntryServiceSoap.class);

}