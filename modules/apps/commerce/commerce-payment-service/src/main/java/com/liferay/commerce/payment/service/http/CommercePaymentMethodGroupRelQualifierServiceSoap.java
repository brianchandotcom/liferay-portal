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

package com.liferay.commerce.payment.service.http;

import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelQualifierServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>CommercePaymentMethodGroupRelQualifierServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.payment.model.CommercePaymentMethodGroupRelQualifierSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.payment.model.CommercePaymentMethodGroupRelQualifier</code>, that is translated to a
 * <code>com.liferay.commerce.payment.model.CommercePaymentMethodGroupRelQualifierSoap</code>. Methods that SOAP
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
 * @see CommercePaymentMethodGroupRelQualifierServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommercePaymentMethodGroupRelQualifierServiceSoap {

	public static com.liferay.commerce.payment.model.
		CommercePaymentMethodGroupRelQualifierSoap
				addCommercePaymentMethodGroupRelQualifier(
					String className, long classPK,
					long commercePaymentMethodGroupRelId)
			throws RemoteException {

		try {
			com.liferay.commerce.payment.model.
				CommercePaymentMethodGroupRelQualifier returnValue =
					CommercePaymentMethodGroupRelQualifierServiceUtil.
						addCommercePaymentMethodGroupRelQualifier(
							className, classPK,
							commercePaymentMethodGroupRelId);

			return com.liferay.commerce.payment.model.
				CommercePaymentMethodGroupRelQualifierSoap.toSoapModel(
					returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCommercePaymentMethodGroupRelQualifier(
			long commercePaymentMethodGroupRelQualifierId)
		throws RemoteException {

		try {
			CommercePaymentMethodGroupRelQualifierServiceUtil.
				deleteCommercePaymentMethodGroupRelQualifier(
					commercePaymentMethodGroupRelQualifierId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCommercePaymentMethodGroupRelQualifiers(
			String className, long commercePaymentMethodGroupRelId)
		throws RemoteException {

		try {
			CommercePaymentMethodGroupRelQualifierServiceUtil.
				deleteCommercePaymentMethodGroupRelQualifiers(
					className, commercePaymentMethodGroupRelId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void
			deleteCommercePaymentMethodGroupRelQualifiersByCommercePaymentMethodGroupRelId(
				long commercePaymentMethodGroupRelId)
		throws RemoteException {

		try {
			CommercePaymentMethodGroupRelQualifierServiceUtil.
				deleteCommercePaymentMethodGroupRelQualifiersByCommercePaymentMethodGroupRelId(
					commercePaymentMethodGroupRelId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.payment.model.
		CommercePaymentMethodGroupRelQualifierSoap
				fetchCommercePaymentMethodGroupRelQualifier(
					String className, long classPK,
					long commercePaymentMethodGroupRelId)
			throws RemoteException {

		try {
			com.liferay.commerce.payment.model.
				CommercePaymentMethodGroupRelQualifier returnValue =
					CommercePaymentMethodGroupRelQualifierServiceUtil.
						fetchCommercePaymentMethodGroupRelQualifier(
							className, classPK,
							commercePaymentMethodGroupRelId);

			return com.liferay.commerce.payment.model.
				CommercePaymentMethodGroupRelQualifierSoap.toSoapModel(
					returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.payment.model.
		CommercePaymentMethodGroupRelQualifierSoap[]
				getCommerceOrderTypeCommercePaymentMethodGroupRelQualifiers(
					long commercePaymentMethodGroupRelId, String keywords,
					int start, int end)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.payment.model.
					CommercePaymentMethodGroupRelQualifier> returnValue =
						CommercePaymentMethodGroupRelQualifierServiceUtil.
							getCommerceOrderTypeCommercePaymentMethodGroupRelQualifiers(
								commercePaymentMethodGroupRelId, keywords,
								start, end);

			return com.liferay.commerce.payment.model.
				CommercePaymentMethodGroupRelQualifierSoap.toSoapModels(
					returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int
			getCommerceOrderTypeCommercePaymentMethodGroupRelQualifiersCount(
				long commercePaymentMethodGroupRelId, String keywords)
		throws RemoteException {

		try {
			int returnValue =
				CommercePaymentMethodGroupRelQualifierServiceUtil.
					getCommerceOrderTypeCommercePaymentMethodGroupRelQualifiersCount(
						commercePaymentMethodGroupRelId, keywords);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.payment.model.
		CommercePaymentMethodGroupRelQualifierSoap
				getCommercePaymentMethodGroupRelQualifier(
					long commercePaymentMethodGroupRelQualifierId)
			throws RemoteException {

		try {
			com.liferay.commerce.payment.model.
				CommercePaymentMethodGroupRelQualifier returnValue =
					CommercePaymentMethodGroupRelQualifierServiceUtil.
						getCommercePaymentMethodGroupRelQualifier(
							commercePaymentMethodGroupRelQualifierId);

			return com.liferay.commerce.payment.model.
				CommercePaymentMethodGroupRelQualifierSoap.toSoapModel(
					returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.payment.model.
		CommercePaymentMethodGroupRelQualifierSoap[]
				getCommercePaymentMethodGroupRelQualifiers(
					long commercePaymentMethodGroupRelId)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.payment.model.
					CommercePaymentMethodGroupRelQualifier> returnValue =
						CommercePaymentMethodGroupRelQualifierServiceUtil.
							getCommercePaymentMethodGroupRelQualifiers(
								commercePaymentMethodGroupRelId);

			return com.liferay.commerce.payment.model.
				CommercePaymentMethodGroupRelQualifierSoap.toSoapModels(
					returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.payment.model.
		CommercePaymentMethodGroupRelQualifierSoap[]
				getCommercePaymentMethodGroupRelQualifiers(
					long commercePaymentMethodGroupRelId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.payment.model.
							CommercePaymentMethodGroupRelQualifier>
								orderByComparator)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.payment.model.
					CommercePaymentMethodGroupRelQualifier> returnValue =
						CommercePaymentMethodGroupRelQualifierServiceUtil.
							getCommercePaymentMethodGroupRelQualifiers(
								commercePaymentMethodGroupRelId, start, end,
								orderByComparator);

			return com.liferay.commerce.payment.model.
				CommercePaymentMethodGroupRelQualifierSoap.toSoapModels(
					returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommercePaymentMethodGroupRelQualifiersCount(
			long commercePaymentMethodGroupRelId)
		throws RemoteException {

		try {
			int returnValue =
				CommercePaymentMethodGroupRelQualifierServiceUtil.
					getCommercePaymentMethodGroupRelQualifiersCount(
						commercePaymentMethodGroupRelId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.payment.model.
		CommercePaymentMethodGroupRelQualifierSoap[]
				getCommerceTermEntryCommercePaymentMethodGroupRelQualifiers(
					long commercePaymentMethodGroupRelId, String keywords,
					int start, int end)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.payment.model.
					CommercePaymentMethodGroupRelQualifier> returnValue =
						CommercePaymentMethodGroupRelQualifierServiceUtil.
							getCommerceTermEntryCommercePaymentMethodGroupRelQualifiers(
								commercePaymentMethodGroupRelId, keywords,
								start, end);

			return com.liferay.commerce.payment.model.
				CommercePaymentMethodGroupRelQualifierSoap.toSoapModels(
					returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int
			getCommerceTermEntryCommercePaymentMethodGroupRelQualifiersCount(
				long commercePaymentMethodGroupRelId, String keywords)
		throws RemoteException {

		try {
			int returnValue =
				CommercePaymentMethodGroupRelQualifierServiceUtil.
					getCommerceTermEntryCommercePaymentMethodGroupRelQualifiersCount(
						commercePaymentMethodGroupRelId, keywords);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommercePaymentMethodGroupRelQualifierServiceSoap.class);

}