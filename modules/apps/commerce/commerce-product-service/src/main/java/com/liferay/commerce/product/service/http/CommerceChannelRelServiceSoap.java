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

import com.liferay.commerce.product.service.CommerceChannelRelServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>CommerceChannelRelServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.product.model.CommerceChannelRelSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.product.model.CommerceChannelRel</code>, that is translated to a
 * <code>com.liferay.commerce.product.model.CommerceChannelRelSoap</code>. Methods that SOAP
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
 * @see CommerceChannelRelServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommerceChannelRelServiceSoap {

	public static com.liferay.commerce.product.model.CommerceChannelRelSoap
			addCommerceChannelRel(
				String className, long classPK, long commerceChannelId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CommerceChannelRel returnValue =
				CommerceChannelRelServiceUtil.addCommerceChannelRel(
					className, classPK, commerceChannelId, serviceContext);

			return com.liferay.commerce.product.model.CommerceChannelRelSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCommerceChannelRel(long commerceChannelRelId)
		throws RemoteException {

		try {
			CommerceChannelRelServiceUtil.deleteCommerceChannelRel(
				commerceChannelRelId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCommerceChannelRels(String className, long classPK)
		throws RemoteException {

		try {
			CommerceChannelRelServiceUtil.deleteCommerceChannelRels(
				className, classPK);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CommerceChannelRelSoap
			fetchCommerceChannelRel(
				String className, long classPK, long commerceChannelId)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CommerceChannelRel returnValue =
				CommerceChannelRelServiceUtil.fetchCommerceChannelRel(
					className, classPK, commerceChannelId);

			return com.liferay.commerce.product.model.CommerceChannelRelSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CommerceChannelRelSoap
			getCommerceChannelRel(long commerceChannelRelId)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CommerceChannelRel returnValue =
				CommerceChannelRelServiceUtil.getCommerceChannelRel(
					commerceChannelRelId);

			return com.liferay.commerce.product.model.CommerceChannelRelSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CommerceChannelRelSoap[]
			getCommerceChannelRels(
				long commerceChannelId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.product.model.CommerceChannelRel>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.product.model.CommerceChannelRel>
					returnValue =
						CommerceChannelRelServiceUtil.getCommerceChannelRels(
							commerceChannelId, start, end, orderByComparator);

			return com.liferay.commerce.product.model.CommerceChannelRelSoap.
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
	public static com.liferay.commerce.product.model.CommerceChannelRelSoap[]
			getCommerceChannelRels(
				String className, long classPK, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.product.model.CommerceChannelRel>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.product.model.CommerceChannelRel>
					returnValue =
						CommerceChannelRelServiceUtil.getCommerceChannelRels(
							className, classPK, start, end, orderByComparator);

			return com.liferay.commerce.product.model.CommerceChannelRelSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CommerceChannelRelSoap[]
			getCommerceChannelRels(
				String className, long classPK, String name, int start, int end)
		throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.product.model.CommerceChannelRel>
					returnValue =
						CommerceChannelRelServiceUtil.getCommerceChannelRels(
							className, classPK, name, start, end);

			return com.liferay.commerce.product.model.CommerceChannelRelSoap.
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
	public static com.liferay.commerce.product.model.CommerceChannelRelSoap[]
			getCommerceChannelRels(
				String className, long classPK, String classPKField,
				String name, int start, int end)
		throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.product.model.CommerceChannelRel>
					returnValue =
						CommerceChannelRelServiceUtil.getCommerceChannelRels(
							className, classPK, classPKField, name, start, end);

			return com.liferay.commerce.product.model.CommerceChannelRelSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommerceChannelRelsCount(long commerceChannelId)
		throws RemoteException {

		try {
			int returnValue =
				CommerceChannelRelServiceUtil.getCommerceChannelRelsCount(
					commerceChannelId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommerceChannelRelsCount(
			String className, long classPK)
		throws RemoteException {

		try {
			int returnValue =
				CommerceChannelRelServiceUtil.getCommerceChannelRelsCount(
					className, classPK);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommerceChannelRelsCount(
			String className, long classPK, String name)
		throws RemoteException {

		try {
			int returnValue =
				CommerceChannelRelServiceUtil.getCommerceChannelRelsCount(
					className, classPK, name);

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
	public static int getCommerceChannelRelsCount(
			String className, long classPK, String classPKField, String name)
		throws RemoteException {

		try {
			int returnValue =
				CommerceChannelRelServiceUtil.getCommerceChannelRelsCount(
					className, classPK, classPKField, name);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceChannelRelServiceSoap.class);

}