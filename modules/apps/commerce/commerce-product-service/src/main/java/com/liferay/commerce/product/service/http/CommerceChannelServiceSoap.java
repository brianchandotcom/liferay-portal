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

import com.liferay.commerce.product.service.CommerceChannelServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>CommerceChannelServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.product.model.CommerceChannelSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.product.model.CommerceChannel</code>, that is translated to a
 * <code>com.liferay.commerce.product.model.CommerceChannelSoap</code>. Methods that SOAP
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
 * @see CommerceChannelServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommerceChannelServiceSoap {

	public static com.liferay.commerce.product.model.CommerceChannelSoap
			deleteCommerceChannel(long commerceChannelId)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CommerceChannel returnValue =
				CommerceChannelServiceUtil.deleteCommerceChannel(
					commerceChannelId);

			return com.liferay.commerce.product.model.CommerceChannelSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CommerceChannelSoap
			fetchByExternalReferenceCode(
				String externalReferenceCode, long companyId)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CommerceChannel returnValue =
				CommerceChannelServiceUtil.fetchByExternalReferenceCode(
					externalReferenceCode, companyId);

			return com.liferay.commerce.product.model.CommerceChannelSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CommerceChannelSoap
			fetchCommerceChannel(long commerceChannelId)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CommerceChannel returnValue =
				CommerceChannelServiceUtil.fetchCommerceChannel(
					commerceChannelId);

			return com.liferay.commerce.product.model.CommerceChannelSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CommerceChannelSoap
			getCommerceChannel(long commerceChannelId)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CommerceChannel returnValue =
				CommerceChannelServiceUtil.getCommerceChannel(
					commerceChannelId);

			return com.liferay.commerce.product.model.CommerceChannelSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CommerceChannelSoap
			getCommerceChannelByOrderGroupId(long groupId)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CommerceChannel returnValue =
				CommerceChannelServiceUtil.getCommerceChannelByOrderGroupId(
					groupId);

			return com.liferay.commerce.product.model.CommerceChannelSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CommerceChannelSoap[]
			getCommerceChannels(int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.product.model.CommerceChannel>
				returnValue = CommerceChannelServiceUtil.getCommerceChannels(
					start, end);

			return com.liferay.commerce.product.model.CommerceChannelSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CommerceChannelSoap[]
			getCommerceChannels(long companyId)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.product.model.CommerceChannel>
				returnValue = CommerceChannelServiceUtil.getCommerceChannels(
					companyId);

			return com.liferay.commerce.product.model.CommerceChannelSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CommerceChannelSoap[]
			searchCommerceChannels(long companyId)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.product.model.CommerceChannel>
				returnValue = CommerceChannelServiceUtil.searchCommerceChannels(
					companyId);

			return com.liferay.commerce.product.model.CommerceChannelSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CommerceChannelSoap[]
			searchCommerceChannels(
				long companyId, String keywords, int start, int end,
				com.liferay.portal.kernel.search.Sort sort)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.product.model.CommerceChannel>
				returnValue = CommerceChannelServiceUtil.searchCommerceChannels(
					companyId, keywords, start, end, sort);

			return com.liferay.commerce.product.model.CommerceChannelSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int searchCommerceChannelsCount(
			long companyId, String keywords)
		throws RemoteException {

		try {
			int returnValue =
				CommerceChannelServiceUtil.searchCommerceChannelsCount(
					companyId, keywords);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CommerceChannelSoap
			updateCommerceChannelExternalReferenceCode(
				String externalReferenceCode, long commerceChannelId)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CommerceChannel returnValue =
				CommerceChannelServiceUtil.
					updateCommerceChannelExternalReferenceCode(
						externalReferenceCode, commerceChannelId);

			return com.liferay.commerce.product.model.CommerceChannelSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceChannelServiceSoap.class);

}