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

package com.liferay.commerce.shipping.engine.fixed.service.http;

import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionQualifierServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>CommerceShippingFixedOptionQualifierServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionQualifierSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionQualifier</code>, that is translated to a
 * <code>com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionQualifierSoap</code>. Methods that SOAP
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
 * @see CommerceShippingFixedOptionQualifierServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommerceShippingFixedOptionQualifierServiceSoap {

	public static com.liferay.commerce.shipping.engine.fixed.model.
		CommerceShippingFixedOptionQualifierSoap
				addCommerceShippingFixedOptionQualifier(
					String className, long classPK,
					long commerceShippingFixedOptionId)
			throws RemoteException {

		try {
			com.liferay.commerce.shipping.engine.fixed.model.
				CommerceShippingFixedOptionQualifier returnValue =
					CommerceShippingFixedOptionQualifierServiceUtil.
						addCommerceShippingFixedOptionQualifier(
							className, classPK, commerceShippingFixedOptionId);

			return com.liferay.commerce.shipping.engine.fixed.model.
				CommerceShippingFixedOptionQualifierSoap.toSoapModel(
					returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCommerceShippingFixedOptionQualifier(
			long commerceShippingFixedOptionQualifierId)
		throws RemoteException {

		try {
			CommerceShippingFixedOptionQualifierServiceUtil.
				deleteCommerceShippingFixedOptionQualifier(
					commerceShippingFixedOptionQualifierId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCommerceShippingFixedOptionQualifiers(
			long commerceShippingFixedOptionId)
		throws RemoteException {

		try {
			CommerceShippingFixedOptionQualifierServiceUtil.
				deleteCommerceShippingFixedOptionQualifiers(
					commerceShippingFixedOptionId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCommerceShippingFixedOptionQualifiers(
			String className, long commerceShippingFixedOptionId)
		throws RemoteException {

		try {
			CommerceShippingFixedOptionQualifierServiceUtil.
				deleteCommerceShippingFixedOptionQualifiers(
					className, commerceShippingFixedOptionId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.shipping.engine.fixed.model.
		CommerceShippingFixedOptionQualifierSoap
				fetchCommerceShippingFixedOptionQualifier(
					String className, long classPK,
					long commerceShippingFixedOptionId)
			throws RemoteException {

		try {
			com.liferay.commerce.shipping.engine.fixed.model.
				CommerceShippingFixedOptionQualifier returnValue =
					CommerceShippingFixedOptionQualifierServiceUtil.
						fetchCommerceShippingFixedOptionQualifier(
							className, classPK, commerceShippingFixedOptionId);

			return com.liferay.commerce.shipping.engine.fixed.model.
				CommerceShippingFixedOptionQualifierSoap.toSoapModel(
					returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.shipping.engine.fixed.model.
		CommerceShippingFixedOptionQualifierSoap[]
				getCommerceOrderTypeCommerceShippingFixedOptionQualifiers(
					long commerceShippingFixedOptionId, String keywords,
					int start, int end)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.shipping.engine.fixed.model.
					CommerceShippingFixedOptionQualifier> returnValue =
						CommerceShippingFixedOptionQualifierServiceUtil.
							getCommerceOrderTypeCommerceShippingFixedOptionQualifiers(
								commerceShippingFixedOptionId, keywords, start,
								end);

			return com.liferay.commerce.shipping.engine.fixed.model.
				CommerceShippingFixedOptionQualifierSoap.toSoapModels(
					returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int
			getCommerceOrderTypeCommerceShippingFixedOptionQualifiersCount(
				long commerceShippingFixedOptionId, String keywords)
		throws RemoteException {

		try {
			int returnValue =
				CommerceShippingFixedOptionQualifierServiceUtil.
					getCommerceOrderTypeCommerceShippingFixedOptionQualifiersCount(
						commerceShippingFixedOptionId, keywords);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.shipping.engine.fixed.model.
		CommerceShippingFixedOptionQualifierSoap
				getCommerceShippingFixedOptionQualifier(
					long commerceShippingFixedOptionQualifierId)
			throws RemoteException {

		try {
			com.liferay.commerce.shipping.engine.fixed.model.
				CommerceShippingFixedOptionQualifier returnValue =
					CommerceShippingFixedOptionQualifierServiceUtil.
						getCommerceShippingFixedOptionQualifier(
							commerceShippingFixedOptionQualifierId);

			return com.liferay.commerce.shipping.engine.fixed.model.
				CommerceShippingFixedOptionQualifierSoap.toSoapModel(
					returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.shipping.engine.fixed.model.
		CommerceShippingFixedOptionQualifierSoap[]
				getCommerceShippingFixedOptionQualifiers(
					long commerceShippingFixedOptionId)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.shipping.engine.fixed.model.
					CommerceShippingFixedOptionQualifier> returnValue =
						CommerceShippingFixedOptionQualifierServiceUtil.
							getCommerceShippingFixedOptionQualifiers(
								commerceShippingFixedOptionId);

			return com.liferay.commerce.shipping.engine.fixed.model.
				CommerceShippingFixedOptionQualifierSoap.toSoapModels(
					returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.shipping.engine.fixed.model.
		CommerceShippingFixedOptionQualifierSoap[]
				getCommerceShippingFixedOptionQualifiers(
					long commerceShippingFixedOptionId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.shipping.engine.fixed.model.
							CommerceShippingFixedOptionQualifier>
								orderByComparator)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.shipping.engine.fixed.model.
					CommerceShippingFixedOptionQualifier> returnValue =
						CommerceShippingFixedOptionQualifierServiceUtil.
							getCommerceShippingFixedOptionQualifiers(
								commerceShippingFixedOptionId, start, end,
								orderByComparator);

			return com.liferay.commerce.shipping.engine.fixed.model.
				CommerceShippingFixedOptionQualifierSoap.toSoapModels(
					returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommerceShippingFixedOptionQualifiersCount(
			long commerceShippingFixedOptionId)
		throws RemoteException {

		try {
			int returnValue =
				CommerceShippingFixedOptionQualifierServiceUtil.
					getCommerceShippingFixedOptionQualifiersCount(
						commerceShippingFixedOptionId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.shipping.engine.fixed.model.
		CommerceShippingFixedOptionQualifierSoap[]
				getCommerceTermEntryCommerceShippingFixedOptionQualifiers(
					long commerceShippingFixedOptionId, String keywords,
					int start, int end)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.shipping.engine.fixed.model.
					CommerceShippingFixedOptionQualifier> returnValue =
						CommerceShippingFixedOptionQualifierServiceUtil.
							getCommerceTermEntryCommerceShippingFixedOptionQualifiers(
								commerceShippingFixedOptionId, keywords, start,
								end);

			return com.liferay.commerce.shipping.engine.fixed.model.
				CommerceShippingFixedOptionQualifierSoap.toSoapModels(
					returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int
			getCommerceTermEntryCommerceShippingFixedOptionQualifiersCount(
				long commerceShippingFixedOptionId, String keywords)
		throws RemoteException {

		try {
			int returnValue =
				CommerceShippingFixedOptionQualifierServiceUtil.
					getCommerceTermEntryCommerceShippingFixedOptionQualifiersCount(
						commerceShippingFixedOptionId, keywords);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceShippingFixedOptionQualifierServiceSoap.class);

}