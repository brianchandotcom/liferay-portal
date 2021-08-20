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

import com.liferay.commerce.discount.service.CommerceDiscountServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>CommerceDiscountServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.discount.model.CommerceDiscountSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.discount.model.CommerceDiscount</code>, that is translated to a
 * <code>com.liferay.commerce.discount.model.CommerceDiscountSoap</code>. Methods that SOAP
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
 * @see CommerceDiscountServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommerceDiscountServiceSoap {

	public static com.liferay.commerce.discount.model.CommerceDiscountSoap
			addCommerceDiscount(
				String title, String target, boolean useCouponCode,
				String couponCode, boolean usePercentage,
				java.math.BigDecimal maximumDiscountAmount,
				java.math.BigDecimal level1, java.math.BigDecimal level2,
				java.math.BigDecimal level3, java.math.BigDecimal level4,
				String limitationType, int limitationTimes, boolean active,
				int displayDateMonth, int displayDateDay, int displayDateYear,
				int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.discount.model.CommerceDiscount returnValue =
				CommerceDiscountServiceUtil.addCommerceDiscount(
					title, target, useCouponCode, couponCode, usePercentage,
					maximumDiscountAmount, level1, level2, level3, level4,
					limitationType, limitationTimes, active, displayDateMonth,
					displayDateDay, displayDateYear, displayDateHour,
					displayDateMinute, expirationDateMonth, expirationDateDay,
					expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, serviceContext);

			return com.liferay.commerce.discount.model.CommerceDiscountSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.discount.model.CommerceDiscountSoap
			addCommerceDiscount(
				String title, String target, boolean useCouponCode,
				String couponCode, boolean usePercentage,
				java.math.BigDecimal maximumDiscountAmount, String level,
				java.math.BigDecimal level1, java.math.BigDecimal level2,
				java.math.BigDecimal level3, java.math.BigDecimal level4,
				String limitationType, int limitationTimes,
				boolean rulesConjunction, boolean active, int displayDateMonth,
				int displayDateDay, int displayDateYear, int displayDateHour,
				int displayDateMinute, int expirationDateMonth,
				int expirationDateDay, int expirationDateYear,
				int expirationDateHour, int expirationDateMinute,
				boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.discount.model.CommerceDiscount returnValue =
				CommerceDiscountServiceUtil.addCommerceDiscount(
					title, target, useCouponCode, couponCode, usePercentage,
					maximumDiscountAmount, level, level1, level2, level3,
					level4, limitationType, limitationTimes, rulesConjunction,
					active, displayDateMonth, displayDateDay, displayDateYear,
					displayDateHour, displayDateMinute, expirationDateMonth,
					expirationDateDay, expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, serviceContext);

			return com.liferay.commerce.discount.model.CommerceDiscountSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.discount.model.CommerceDiscountSoap
			addCommerceDiscount(
				String externalReferenceCode, String title, String target,
				boolean useCouponCode, String couponCode, boolean usePercentage,
				java.math.BigDecimal maximumDiscountAmount, String level,
				java.math.BigDecimal level1, java.math.BigDecimal level2,
				java.math.BigDecimal level3, java.math.BigDecimal level4,
				String limitationType, int limitationTimes,
				int limitationTimesPerAccount, boolean rulesConjunction,
				boolean active, int displayDateMonth, int displayDateDay,
				int displayDateYear, int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.discount.model.CommerceDiscount returnValue =
				CommerceDiscountServiceUtil.addCommerceDiscount(
					externalReferenceCode, title, target, useCouponCode,
					couponCode, usePercentage, maximumDiscountAmount, level,
					level1, level2, level3, level4, limitationType,
					limitationTimes, limitationTimesPerAccount,
					rulesConjunction, active, displayDateMonth, displayDateDay,
					displayDateYear, displayDateHour, displayDateMinute,
					expirationDateMonth, expirationDateDay, expirationDateYear,
					expirationDateHour, expirationDateMinute, neverExpire,
					serviceContext);

			return com.liferay.commerce.discount.model.CommerceDiscountSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.discount.model.CommerceDiscountSoap
			addOrUpdateCommerceDiscount(
				String externalReferenceCode, long commerceDiscountId,
				String title, String target, boolean useCouponCode,
				String couponCode, boolean usePercentage,
				java.math.BigDecimal maximumDiscountAmount,
				java.math.BigDecimal level1, java.math.BigDecimal level2,
				java.math.BigDecimal level3, java.math.BigDecimal level4,
				String limitationType, int limitationTimes, boolean active,
				int displayDateMonth, int displayDateDay, int displayDateYear,
				int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.discount.model.CommerceDiscount returnValue =
				CommerceDiscountServiceUtil.addOrUpdateCommerceDiscount(
					externalReferenceCode, commerceDiscountId, title, target,
					useCouponCode, couponCode, usePercentage,
					maximumDiscountAmount, level1, level2, level3, level4,
					limitationType, limitationTimes, active, displayDateMonth,
					displayDateDay, displayDateYear, displayDateHour,
					displayDateMinute, expirationDateMonth, expirationDateDay,
					expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, serviceContext);

			return com.liferay.commerce.discount.model.CommerceDiscountSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.discount.model.CommerceDiscountSoap
			addOrUpdateCommerceDiscount(
				String externalReferenceCode, long commerceDiscountId,
				String title, String target, boolean useCouponCode,
				String couponCode, boolean usePercentage,
				java.math.BigDecimal maximumDiscountAmount, String level,
				java.math.BigDecimal level1, java.math.BigDecimal level2,
				java.math.BigDecimal level3, java.math.BigDecimal level4,
				String limitationType, int limitationTimes,
				boolean rulesConjunction, boolean active, int displayDateMonth,
				int displayDateDay, int displayDateYear, int displayDateHour,
				int displayDateMinute, int expirationDateMonth,
				int expirationDateDay, int expirationDateYear,
				int expirationDateHour, int expirationDateMinute,
				boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.discount.model.CommerceDiscount returnValue =
				CommerceDiscountServiceUtil.addOrUpdateCommerceDiscount(
					externalReferenceCode, commerceDiscountId, title, target,
					useCouponCode, couponCode, usePercentage,
					maximumDiscountAmount, level, level1, level2, level3,
					level4, limitationType, limitationTimes, rulesConjunction,
					active, displayDateMonth, displayDateDay, displayDateYear,
					displayDateHour, displayDateMinute, expirationDateMonth,
					expirationDateDay, expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, serviceContext);

			return com.liferay.commerce.discount.model.CommerceDiscountSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.discount.model.CommerceDiscountSoap
			addOrUpdateCommerceDiscount(
				String externalReferenceCode, long commerceDiscountId,
				String title, String target, boolean useCouponCode,
				String couponCode, boolean usePercentage,
				java.math.BigDecimal maximumDiscountAmount, String level,
				java.math.BigDecimal level1, java.math.BigDecimal level2,
				java.math.BigDecimal level3, java.math.BigDecimal level4,
				String limitationType, int limitationTimes,
				int limitationTimesPerAccount, boolean rulesConjunction,
				boolean active, int displayDateMonth, int displayDateDay,
				int displayDateYear, int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.discount.model.CommerceDiscount returnValue =
				CommerceDiscountServiceUtil.addOrUpdateCommerceDiscount(
					externalReferenceCode, commerceDiscountId, title, target,
					useCouponCode, couponCode, usePercentage,
					maximumDiscountAmount, level, level1, level2, level3,
					level4, limitationType, limitationTimes,
					limitationTimesPerAccount, rulesConjunction, active,
					displayDateMonth, displayDateDay, displayDateYear,
					displayDateHour, displayDateMinute, expirationDateMonth,
					expirationDateDay, expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, serviceContext);

			return com.liferay.commerce.discount.model.CommerceDiscountSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCommerceDiscount(long commerceDiscountId)
		throws RemoteException {

		try {
			CommerceDiscountServiceUtil.deleteCommerceDiscount(
				commerceDiscountId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #fetchByExternalReferenceCode(String, long)}
	 */
	@Deprecated
	public static com.liferay.commerce.discount.model.CommerceDiscountSoap
			fetchByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws RemoteException {

		try {
			com.liferay.commerce.discount.model.CommerceDiscount returnValue =
				CommerceDiscountServiceUtil.fetchByExternalReferenceCode(
					companyId, externalReferenceCode);

			return com.liferay.commerce.discount.model.CommerceDiscountSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.discount.model.CommerceDiscountSoap
			fetchByExternalReferenceCode(
				String externalReferenceCode, long companyId)
		throws RemoteException {

		try {
			com.liferay.commerce.discount.model.CommerceDiscount returnValue =
				CommerceDiscountServiceUtil.fetchByExternalReferenceCode(
					externalReferenceCode, companyId);

			return com.liferay.commerce.discount.model.CommerceDiscountSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.discount.model.CommerceDiscountSoap
			fetchCommerceDiscount(long commerceDiscountId)
		throws RemoteException {

		try {
			com.liferay.commerce.discount.model.CommerceDiscount returnValue =
				CommerceDiscountServiceUtil.fetchCommerceDiscount(
					commerceDiscountId);

			return com.liferay.commerce.discount.model.CommerceDiscountSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.discount.model.CommerceDiscountSoap
			getCommerceDiscount(long commerceDiscountId)
		throws RemoteException {

		try {
			com.liferay.commerce.discount.model.CommerceDiscount returnValue =
				CommerceDiscountServiceUtil.getCommerceDiscount(
					commerceDiscountId);

			return com.liferay.commerce.discount.model.CommerceDiscountSoap.
				toSoapModel(returnValue);
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
	public static com.liferay.commerce.discount.model.CommerceDiscountSoap[]
			getCommerceDiscounts(long companyId, String couponCode)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.discount.model.CommerceDiscount>
				returnValue = CommerceDiscountServiceUtil.getCommerceDiscounts(
					companyId, couponCode);

			return com.liferay.commerce.discount.model.CommerceDiscountSoap.
				toSoapModels(returnValue);
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
	public static int getCommerceDiscountsCount(
			long companyId, String couponCode)
		throws RemoteException {

		try {
			int returnValue =
				CommerceDiscountServiceUtil.getCommerceDiscountsCount(
					companyId, couponCode);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommerceDiscountsCountByPricingClassId(
			long commercePricingClassId, String title)
		throws RemoteException {

		try {
			int returnValue =
				CommerceDiscountServiceUtil.
					getCommerceDiscountsCountByPricingClassId(
						commercePricingClassId, title);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.discount.model.CommerceDiscountSoap[]
			searchByCommercePricingClassId(
				long commercePricingClassId, String title, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.discount.model.CommerceDiscount>
				returnValue =
					CommerceDiscountServiceUtil.searchByCommercePricingClassId(
						commercePricingClassId, title, start, end);

			return com.liferay.commerce.discount.model.CommerceDiscountSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.discount.model.CommerceDiscountSoap
			updateCommerceDiscount(
				long commerceDiscountId, String title, String target,
				boolean useCouponCode, String couponCode, boolean usePercentage,
				java.math.BigDecimal maximumDiscountAmount,
				java.math.BigDecimal level1, java.math.BigDecimal level2,
				java.math.BigDecimal level3, java.math.BigDecimal level4,
				String limitationType, int limitationTimes, boolean active,
				int displayDateMonth, int displayDateDay, int displayDateYear,
				int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.discount.model.CommerceDiscount returnValue =
				CommerceDiscountServiceUtil.updateCommerceDiscount(
					commerceDiscountId, title, target, useCouponCode,
					couponCode, usePercentage, maximumDiscountAmount, level1,
					level2, level3, level4, limitationType, limitationTimes,
					active, displayDateMonth, displayDateDay, displayDateYear,
					displayDateHour, displayDateMinute, expirationDateMonth,
					expirationDateDay, expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, serviceContext);

			return com.liferay.commerce.discount.model.CommerceDiscountSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.discount.model.CommerceDiscountSoap
			updateCommerceDiscount(
				long commerceDiscountId, String title, String target,
				boolean useCouponCode, String couponCode, boolean usePercentage,
				java.math.BigDecimal maximumDiscountAmount, String level,
				java.math.BigDecimal level1, java.math.BigDecimal level2,
				java.math.BigDecimal level3, java.math.BigDecimal level4,
				String limitationType, int limitationTimes,
				boolean rulesConjunction, boolean active, int displayDateMonth,
				int displayDateDay, int displayDateYear, int displayDateHour,
				int displayDateMinute, int expirationDateMonth,
				int expirationDateDay, int expirationDateYear,
				int expirationDateHour, int expirationDateMinute,
				boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.discount.model.CommerceDiscount returnValue =
				CommerceDiscountServiceUtil.updateCommerceDiscount(
					commerceDiscountId, title, target, useCouponCode,
					couponCode, usePercentage, maximumDiscountAmount, level,
					level1, level2, level3, level4, limitationType,
					limitationTimes, rulesConjunction, active, displayDateMonth,
					displayDateDay, displayDateYear, displayDateHour,
					displayDateMinute, expirationDateMonth, expirationDateDay,
					expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, serviceContext);

			return com.liferay.commerce.discount.model.CommerceDiscountSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.discount.model.CommerceDiscountSoap
			updateCommerceDiscount(
				long commerceDiscountId, String title, String target,
				boolean useCouponCode, String couponCode, boolean usePercentage,
				java.math.BigDecimal maximumDiscountAmount, String level,
				java.math.BigDecimal level1, java.math.BigDecimal level2,
				java.math.BigDecimal level3, java.math.BigDecimal level4,
				String limitationType, int limitationTimes,
				int limitationTimesPerAccount, boolean rulesConjunction,
				boolean active, int displayDateMonth, int displayDateDay,
				int displayDateYear, int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.discount.model.CommerceDiscount returnValue =
				CommerceDiscountServiceUtil.updateCommerceDiscount(
					commerceDiscountId, title, target, useCouponCode,
					couponCode, usePercentage, maximumDiscountAmount, level,
					level1, level2, level3, level4, limitationType,
					limitationTimes, limitationTimesPerAccount,
					rulesConjunction, active, displayDateMonth, displayDateDay,
					displayDateYear, displayDateHour, displayDateMinute,
					expirationDateMonth, expirationDateDay, expirationDateYear,
					expirationDateHour, expirationDateMinute, neverExpire,
					serviceContext);

			return com.liferay.commerce.discount.model.CommerceDiscountSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #updateCommerceDiscountExternalReferenceCode(String, long)}
	 */
	@Deprecated
	public static com.liferay.commerce.discount.model.CommerceDiscountSoap
			updateCommerceDiscountExternalReferenceCode(
				long commerceDiscountId, String externalReferenceCode)
		throws RemoteException {

		try {
			com.liferay.commerce.discount.model.CommerceDiscount returnValue =
				CommerceDiscountServiceUtil.
					updateCommerceDiscountExternalReferenceCode(
						commerceDiscountId, externalReferenceCode);

			return com.liferay.commerce.discount.model.CommerceDiscountSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.discount.model.CommerceDiscountSoap
			updateCommerceDiscountExternalReferenceCode(
				String externalReferenceCode, long commerceDiscountId)
		throws RemoteException {

		try {
			com.liferay.commerce.discount.model.CommerceDiscount returnValue =
				CommerceDiscountServiceUtil.
					updateCommerceDiscountExternalReferenceCode(
						externalReferenceCode, commerceDiscountId);

			return com.liferay.commerce.discount.model.CommerceDiscountSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceDiscountServiceSoap.class);

}