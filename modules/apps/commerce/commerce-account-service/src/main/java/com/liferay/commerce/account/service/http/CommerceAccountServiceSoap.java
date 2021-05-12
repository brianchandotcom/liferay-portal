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

package com.liferay.commerce.account.service.http;

import com.liferay.commerce.account.service.CommerceAccountServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>CommerceAccountServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.account.model.CommerceAccountSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.account.model.CommerceAccount</code>, that is translated to a
 * <code>com.liferay.commerce.account.model.CommerceAccountSoap</code>. Methods that SOAP
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
 * @see CommerceAccountServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommerceAccountServiceSoap {

	public static com.liferay.commerce.account.model.CommerceAccountSoap
			addBusinessCommerceAccount(
				String name, long parentCommerceAccountId, String email,
				String taxId, boolean active, String externalReferenceCode,
				long[] userIds, String[] emailAddresses,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.account.model.CommerceAccount returnValue =
				CommerceAccountServiceUtil.addBusinessCommerceAccount(
					name, parentCommerceAccountId, email, taxId, active,
					externalReferenceCode, userIds, emailAddresses,
					serviceContext);

			return com.liferay.commerce.account.model.CommerceAccountSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.account.model.CommerceAccountSoap
			addCommerceAccount(
				String name, long parentCommerceAccountId, String email,
				String taxId, int type, boolean active,
				String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.account.model.CommerceAccount returnValue =
				CommerceAccountServiceUtil.addCommerceAccount(
					name, parentCommerceAccountId, email, taxId, type, active,
					externalReferenceCode, serviceContext);

			return com.liferay.commerce.account.model.CommerceAccountSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.account.model.CommerceAccountSoap
			addOrUpdateCommerceAccount(
				String name, long parentCommerceAccountId, boolean logo,
				byte[] logoBytes, String email, String taxId, int type,
				boolean active, String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.account.model.CommerceAccount returnValue =
				CommerceAccountServiceUtil.addOrUpdateCommerceAccount(
					name, parentCommerceAccountId, logo, logoBytes, email,
					taxId, type, active, externalReferenceCode, serviceContext);

			return com.liferay.commerce.account.model.CommerceAccountSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCommerceAccount(long commerceAccountId)
		throws RemoteException {

		try {
			CommerceAccountServiceUtil.deleteCommerceAccount(commerceAccountId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.account.model.CommerceAccountSoap
			fetchByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws RemoteException {

		try {
			com.liferay.commerce.account.model.CommerceAccount returnValue =
				CommerceAccountServiceUtil.fetchByExternalReferenceCode(
					companyId, externalReferenceCode);

			return com.liferay.commerce.account.model.CommerceAccountSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.account.model.CommerceAccountSoap
			fetchCommerceAccount(long commerceAccountId)
		throws RemoteException {

		try {
			com.liferay.commerce.account.model.CommerceAccount returnValue =
				CommerceAccountServiceUtil.fetchCommerceAccount(
					commerceAccountId);

			return com.liferay.commerce.account.model.CommerceAccountSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.account.model.CommerceAccountSoap
			getCommerceAccount(long commerceAccountId)
		throws RemoteException {

		try {
			com.liferay.commerce.account.model.CommerceAccount returnValue =
				CommerceAccountServiceUtil.getCommerceAccount(
					commerceAccountId);

			return com.liferay.commerce.account.model.CommerceAccountSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.account.model.CommerceAccountSoap
			getPersonalCommerceAccount(long userId)
		throws RemoteException {

		try {
			com.liferay.commerce.account.model.CommerceAccount returnValue =
				CommerceAccountServiceUtil.getPersonalCommerceAccount(userId);

			return com.liferay.commerce.account.model.CommerceAccountSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.account.model.CommerceAccountSoap[]
			getUserCommerceAccounts(
				long userId, long parentCommerceAccountId, int commerceSiteType,
				String keywords, Boolean active, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.account.model.CommerceAccount>
				returnValue =
					CommerceAccountServiceUtil.getUserCommerceAccounts(
						userId, parentCommerceAccountId, commerceSiteType,
						keywords, active, start, end);

			return com.liferay.commerce.account.model.CommerceAccountSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.account.model.CommerceAccountSoap[]
			getUserCommerceAccounts(
				long userId, long parentCommerceAccountId, int commerceSiteType,
				String keywords, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.account.model.CommerceAccount>
				returnValue =
					CommerceAccountServiceUtil.getUserCommerceAccounts(
						userId, parentCommerceAccountId, commerceSiteType,
						keywords, start, end);

			return com.liferay.commerce.account.model.CommerceAccountSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getUserCommerceAccountsCount(
			long userId, long parentCommerceAccountId, int commerceSiteType,
			String keywords)
		throws RemoteException {

		try {
			int returnValue =
				CommerceAccountServiceUtil.getUserCommerceAccountsCount(
					userId, parentCommerceAccountId, commerceSiteType,
					keywords);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getUserCommerceAccountsCount(
			long userId, long parentCommerceAccountId, int commerceSiteType,
			String keywords, Boolean active)
		throws RemoteException {

		try {
			int returnValue =
				CommerceAccountServiceUtil.getUserCommerceAccountsCount(
					userId, parentCommerceAccountId, commerceSiteType, keywords,
					active);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.account.model.CommerceAccountSoap
			setActive(long commerceAccountId, boolean active)
		throws RemoteException {

		try {
			com.liferay.commerce.account.model.CommerceAccount returnValue =
				CommerceAccountServiceUtil.setActive(commerceAccountId, active);

			return com.liferay.commerce.account.model.CommerceAccountSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.account.model.CommerceAccountSoap
			updateCommerceAccount(
				long commerceAccountId, String name, boolean logo,
				byte[] logoBytes, String email, String taxId, boolean active,
				long defaultBillingAddressId, long defaultShippingAddressId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.account.model.CommerceAccount returnValue =
				CommerceAccountServiceUtil.updateCommerceAccount(
					commerceAccountId, name, logo, logoBytes, email, taxId,
					active, defaultBillingAddressId, defaultShippingAddressId,
					serviceContext);

			return com.liferay.commerce.account.model.CommerceAccountSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.account.model.CommerceAccountSoap
			updateCommerceAccount(
				long commerceAccountId, String name, boolean logo,
				byte[] logoBytes, String email, String taxId, boolean active,
				long defaultBillingAddressId, long defaultShippingAddressId,
				String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.account.model.CommerceAccount returnValue =
				CommerceAccountServiceUtil.updateCommerceAccount(
					commerceAccountId, name, logo, logoBytes, email, taxId,
					active, defaultBillingAddressId, defaultShippingAddressId,
					externalReferenceCode, serviceContext);

			return com.liferay.commerce.account.model.CommerceAccountSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * @deprecated As of Mueller (7.2.x), pass Default Billing/Shipping Ids
	 */
	@Deprecated
	public static com.liferay.commerce.account.model.CommerceAccountSoap
			updateCommerceAccount(
				long commerceAccountId, String name, boolean logo,
				byte[] logoBytes, String email, String taxId, boolean active,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.account.model.CommerceAccount returnValue =
				CommerceAccountServiceUtil.updateCommerceAccount(
					commerceAccountId, name, logo, logoBytes, email, taxId,
					active, serviceContext);

			return com.liferay.commerce.account.model.CommerceAccountSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.account.model.CommerceAccountSoap
			updateDefaultBillingAddress(
				long commerceAccountId, long commerceAddressId)
		throws RemoteException {

		try {
			com.liferay.commerce.account.model.CommerceAccount returnValue =
				CommerceAccountServiceUtil.updateDefaultBillingAddress(
					commerceAccountId, commerceAddressId);

			return com.liferay.commerce.account.model.CommerceAccountSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.account.model.CommerceAccountSoap
			updateDefaultShippingAddress(
				long commerceAccountId, long commerceAddressId)
		throws RemoteException {

		try {
			com.liferay.commerce.account.model.CommerceAccount returnValue =
				CommerceAccountServiceUtil.updateDefaultShippingAddress(
					commerceAccountId, commerceAddressId);

			return com.liferay.commerce.account.model.CommerceAccountSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceAccountServiceSoap.class);

}