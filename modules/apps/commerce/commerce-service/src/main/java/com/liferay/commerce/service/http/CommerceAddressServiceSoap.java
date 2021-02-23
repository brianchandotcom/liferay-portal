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

package com.liferay.commerce.service.http;

import com.liferay.commerce.service.CommerceAddressServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>CommerceAddressServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.model.CommerceAddressSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.model.CommerceAddress</code>, that is translated to a
 * <code>com.liferay.commerce.model.CommerceAddressSoap</code>. Methods that SOAP
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
 * @see CommerceAddressServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommerceAddressServiceSoap {

	/**
	 * @deprecated As of Mueller (7.2.x), defaultBilling/Shipping exist on Account Entity. Pass type.
	 */
	@Deprecated
	public static com.liferay.commerce.model.CommerceAddressSoap
			addCommerceAddress(
				String className, long classPK, String name, String description,
				String street1, String street2, String street3, String city,
				String zip, long regionId, long countryId, String phoneNumber,
				boolean defaultBilling, boolean defaultShipping,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceAddress returnValue =
				CommerceAddressServiceUtil.addCommerceAddress(
					className, classPK, name, description, street1, street2,
					street3, city, zip, regionId, countryId, phoneNumber,
					defaultBilling, defaultShipping, serviceContext);

			return com.liferay.commerce.model.CommerceAddressSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceAddressSoap
			addCommerceAddress(
				String className, long classPK, String name, String description,
				String street1, String street2, String street3, String city,
				String zip, long regionId, long countryId, String phoneNumber,
				int type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceAddress returnValue =
				CommerceAddressServiceUtil.addCommerceAddress(
					className, classPK, name, description, street1, street2,
					street3, city, zip, regionId, countryId, phoneNumber, type,
					serviceContext);

			return com.liferay.commerce.model.CommerceAddressSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceAddressSoap
			addCommerceAddress(
				String externalReferenceCode, String className, long classPK,
				String name, String description, String street1, String street2,
				String street3, String city, String zip, long regionId,
				long countryId, String phoneNumber, int type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceAddress returnValue =
				CommerceAddressServiceUtil.addCommerceAddress(
					externalReferenceCode, className, classPK, name,
					description, street1, street2, street3, city, zip, regionId,
					countryId, phoneNumber, type, serviceContext);

			return com.liferay.commerce.model.CommerceAddressSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCommerceAddress(long commerceAddressId)
		throws RemoteException {

		try {
			CommerceAddressServiceUtil.deleteCommerceAddress(commerceAddressId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceAddressSoap
			fetchByExternalReferenceCode(
				String externalReferenceCode, long companyId)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceAddress returnValue =
				CommerceAddressServiceUtil.fetchByExternalReferenceCode(
					externalReferenceCode, companyId);

			return com.liferay.commerce.model.CommerceAddressSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceAddressSoap
			fetchCommerceAddress(long commerceAddressId)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceAddress returnValue =
				CommerceAddressServiceUtil.fetchCommerceAddress(
					commerceAddressId);

			return com.liferay.commerce.model.CommerceAddressSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceAddressSoap[]
			getBillingCommerceAddresses(
				long companyId, String className, long classPK)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.model.CommerceAddress>
				returnValue =
					CommerceAddressServiceUtil.getBillingCommerceAddresses(
						companyId, className, classPK);

			return com.liferay.commerce.model.CommerceAddressSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceAddressSoap[]
			getBillingCommerceAddresses(
				long companyId, String className, long classPK, String keywords,
				int start, int end, com.liferay.portal.kernel.search.Sort sort)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.model.CommerceAddress>
				returnValue =
					CommerceAddressServiceUtil.getBillingCommerceAddresses(
						companyId, className, classPK, keywords, start, end,
						sort);

			return com.liferay.commerce.model.CommerceAddressSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getBillingCommerceAddressesCount(
			long companyId, String className, long classPK, String keywords)
		throws RemoteException {

		try {
			int returnValue =
				CommerceAddressServiceUtil.getBillingCommerceAddressesCount(
					companyId, className, classPK, keywords);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceAddressSoap
			getCommerceAddress(long commerceAddressId)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceAddress returnValue =
				CommerceAddressServiceUtil.getCommerceAddress(
					commerceAddressId);

			return com.liferay.commerce.model.CommerceAddressSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * @deprecated As of Mueller (7.2.x), commerceAddress is scoped to Company use *ByCompanyId
	 */
	@Deprecated
	public static com.liferay.commerce.model.CommerceAddressSoap[]
			getCommerceAddresses(long groupId, String className, long classPK)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.model.CommerceAddress>
				returnValue = CommerceAddressServiceUtil.getCommerceAddresses(
					groupId, className, classPK);

			return com.liferay.commerce.model.CommerceAddressSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * @deprecated As of Mueller (7.2.x), commerceAddress is scoped to Company use *ByCompanyId
	 */
	@Deprecated
	public static com.liferay.commerce.model.CommerceAddressSoap[]
			getCommerceAddresses(
				long groupId, String className, long classPK, int start,
				int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.model.CommerceAddress>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.model.CommerceAddress>
				returnValue = CommerceAddressServiceUtil.getCommerceAddresses(
					groupId, className, classPK, start, end, orderByComparator);

			return com.liferay.commerce.model.CommerceAddressSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceAddressSoap[]
			getCommerceAddresses(
				String className, long classPK, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.model.CommerceAddress>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.model.CommerceAddress>
				returnValue = CommerceAddressServiceUtil.getCommerceAddresses(
					className, classPK, start, end, orderByComparator);

			return com.liferay.commerce.model.CommerceAddressSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceAddressSoap[]
			getCommerceAddressesByCompanyId(
				long companyId, String className, long classPK)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.model.CommerceAddress>
				returnValue =
					CommerceAddressServiceUtil.getCommerceAddressesByCompanyId(
						companyId, className, classPK);

			return com.liferay.commerce.model.CommerceAddressSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceAddressSoap[]
			getCommerceAddressesByCompanyId(
				long companyId, String className, long classPK, int start,
				int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.model.CommerceAddress>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.model.CommerceAddress>
				returnValue =
					CommerceAddressServiceUtil.getCommerceAddressesByCompanyId(
						companyId, className, classPK, start, end,
						orderByComparator);

			return com.liferay.commerce.model.CommerceAddressSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * @deprecated As of Mueller (7.2.x), commerceAddress is scoped to Company use *ByCompanyId
	 */
	@Deprecated
	public static int getCommerceAddressesCount(
			long groupId, String className, long classPK)
		throws RemoteException {

		try {
			int returnValue =
				CommerceAddressServiceUtil.getCommerceAddressesCount(
					groupId, className, classPK);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommerceAddressesCount(String className, long classPK)
		throws RemoteException {

		try {
			int returnValue =
				CommerceAddressServiceUtil.getCommerceAddressesCount(
					className, classPK);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommerceAddressesCountByCompanyId(
			long companyId, String className, long classPK)
		throws RemoteException {

		try {
			int returnValue =
				CommerceAddressServiceUtil.getCommerceAddressesCountByCompanyId(
					companyId, className, classPK);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceAddressSoap[]
			getShippingCommerceAddresses(
				long companyId, String className, long classPK)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.model.CommerceAddress>
				returnValue =
					CommerceAddressServiceUtil.getShippingCommerceAddresses(
						companyId, className, classPK);

			return com.liferay.commerce.model.CommerceAddressSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceAddressSoap[]
			getShippingCommerceAddresses(
				long companyId, String className, long classPK, String keywords,
				int start, int end, com.liferay.portal.kernel.search.Sort sort)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.model.CommerceAddress>
				returnValue =
					CommerceAddressServiceUtil.getShippingCommerceAddresses(
						companyId, className, classPK, keywords, start, end,
						sort);

			return com.liferay.commerce.model.CommerceAddressSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getShippingCommerceAddressesCount(
			long companyId, String className, long classPK, String keywords)
		throws RemoteException {

		try {
			int returnValue =
				CommerceAddressServiceUtil.getShippingCommerceAddressesCount(
					companyId, className, classPK, keywords);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * @deprecated As of Mueller (7.2.x), defaultBilling/Shipping exist on Account Entity. Pass type.
	 */
	@Deprecated
	public static com.liferay.commerce.model.CommerceAddressSoap
			updateCommerceAddress(
				long commerceAddressId, String name, String description,
				String street1, String street2, String street3, String city,
				String zip, long regionId, long countryId, String phoneNumber,
				boolean defaultBilling, boolean defaultShipping,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceAddress returnValue =
				CommerceAddressServiceUtil.updateCommerceAddress(
					commerceAddressId, name, description, street1, street2,
					street3, city, zip, regionId, countryId, phoneNumber,
					defaultBilling, defaultShipping, serviceContext);

			return com.liferay.commerce.model.CommerceAddressSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceAddressSoap
			updateCommerceAddress(
				long commerceAddressId, String name, String description,
				String street1, String street2, String street3, String city,
				String zip, long regionId, long countryId, String phoneNumber,
				int type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceAddress returnValue =
				CommerceAddressServiceUtil.updateCommerceAddress(
					commerceAddressId, name, description, street1, street2,
					street3, city, zip, regionId, countryId, phoneNumber, type,
					serviceContext);

			return com.liferay.commerce.model.CommerceAddressSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceAddressServiceSoap.class);

}