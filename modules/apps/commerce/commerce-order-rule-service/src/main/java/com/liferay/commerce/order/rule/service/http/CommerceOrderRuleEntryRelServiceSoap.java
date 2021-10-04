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

package com.liferay.commerce.order.rule.service.http;

import com.liferay.commerce.order.rule.service.CommerceOrderRuleEntryRelServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>CommerceOrderRuleEntryRelServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRelSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel</code>, that is translated to a
 * <code>com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRelSoap</code>. Methods that SOAP
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
 * @see CommerceOrderRuleEntryRelServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommerceOrderRuleEntryRelServiceSoap {

	public static
		com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRelSoap
				addCommerceOrderRuleEntryRel(
					String className, long classPK,
					long commerceOrderRuleEntryId)
			throws RemoteException {

		try {
			com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel
				returnValue =
					CommerceOrderRuleEntryRelServiceUtil.
						addCommerceOrderRuleEntryRel(
							className, classPK, commerceOrderRuleEntryId);

			return com.liferay.commerce.order.rule.model.
				CommerceOrderRuleEntryRelSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCommerceOrderRuleEntryRel(
			long commerceOrderRuleEntryRelId)
		throws RemoteException {

		try {
			CommerceOrderRuleEntryRelServiceUtil.
				deleteCommerceOrderRuleEntryRel(commerceOrderRuleEntryRelId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void
			deleteCommerceOrderRuleEntryRelsByCommerceOrderRuleEntryId(
				long commerceOrderRuleEntryId)
		throws RemoteException {

		try {
			CommerceOrderRuleEntryRelServiceUtil.
				deleteCommerceOrderRuleEntryRelsByCommerceOrderRuleEntryId(
					commerceOrderRuleEntryId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRelSoap
				fetchCommerceOrderRuleEntryRel(
					String className, long classPK,
					long commerceOrderRuleEntryId)
			throws RemoteException {

		try {
			com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel
				returnValue =
					CommerceOrderRuleEntryRelServiceUtil.
						fetchCommerceOrderRuleEntryRel(
							className, classPK, commerceOrderRuleEntryId);

			return com.liferay.commerce.order.rule.model.
				CommerceOrderRuleEntryRelSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRelSoap[]
				getCommerceOrderRuleEntryAccountEntryRels(
					long commerceOrderRuleEntryId, String keywords, int start,
					int end)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.order.rule.model.
					CommerceOrderRuleEntryRel> returnValue =
						CommerceOrderRuleEntryRelServiceUtil.
							getCommerceOrderRuleEntryAccountEntryRels(
								commerceOrderRuleEntryId, keywords, start, end);

			return com.liferay.commerce.order.rule.model.
				CommerceOrderRuleEntryRelSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommerceOrderRuleEntryAccountEntryRelsCount(
			long commerceOrderRuleEntryId, String keywords)
		throws RemoteException {

		try {
			int returnValue =
				CommerceOrderRuleEntryRelServiceUtil.
					getCommerceOrderRuleEntryAccountEntryRelsCount(
						commerceOrderRuleEntryId, keywords);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRelSoap[]
				getCommerceOrderRuleEntryAccountGroupRels(
					long commerceOrderRuleEntryId, String keywords, int start,
					int end)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.order.rule.model.
					CommerceOrderRuleEntryRel> returnValue =
						CommerceOrderRuleEntryRelServiceUtil.
							getCommerceOrderRuleEntryAccountGroupRels(
								commerceOrderRuleEntryId, keywords, start, end);

			return com.liferay.commerce.order.rule.model.
				CommerceOrderRuleEntryRelSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommerceOrderRuleEntryAccountGroupRelsCount(
			long commerceOrderRuleEntryId, String keywords)
		throws RemoteException {

		try {
			int returnValue =
				CommerceOrderRuleEntryRelServiceUtil.
					getCommerceOrderRuleEntryAccountGroupRelsCount(
						commerceOrderRuleEntryId, keywords);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRelSoap[]
				getCommerceOrderRuleEntryCommerceChannelRels(
					long commerceOrderRuleEntryId, String keywords, int start,
					int end)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.order.rule.model.
					CommerceOrderRuleEntryRel> returnValue =
						CommerceOrderRuleEntryRelServiceUtil.
							getCommerceOrderRuleEntryCommerceChannelRels(
								commerceOrderRuleEntryId, keywords, start, end);

			return com.liferay.commerce.order.rule.model.
				CommerceOrderRuleEntryRelSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommerceOrderRuleEntryCommerceChannelRelsCount(
			long commerceOrderRuleEntryId, String keywords)
		throws RemoteException {

		try {
			int returnValue =
				CommerceOrderRuleEntryRelServiceUtil.
					getCommerceOrderRuleEntryCommerceChannelRelsCount(
						commerceOrderRuleEntryId, keywords);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRelSoap[]
				getCommerceOrderRuleEntryCommerceOrderTypeRels(
					long commerceOrderRuleEntryId, String keywords, int start,
					int end)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.order.rule.model.
					CommerceOrderRuleEntryRel> returnValue =
						CommerceOrderRuleEntryRelServiceUtil.
							getCommerceOrderRuleEntryCommerceOrderTypeRels(
								commerceOrderRuleEntryId, keywords, start, end);

			return com.liferay.commerce.order.rule.model.
				CommerceOrderRuleEntryRelSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommerceOrderRuleEntryCommerceOrderTypeRelsCount(
			long commerceOrderRuleEntryId, String keywords)
		throws RemoteException {

		try {
			int returnValue =
				CommerceOrderRuleEntryRelServiceUtil.
					getCommerceOrderRuleEntryCommerceOrderTypeRelsCount(
						commerceOrderRuleEntryId, keywords);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRelSoap
				getCommerceOrderRuleEntryRel(long commerceOrderRuleEntryRelId)
			throws RemoteException {

		try {
			com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel
				returnValue =
					CommerceOrderRuleEntryRelServiceUtil.
						getCommerceOrderRuleEntryRel(
							commerceOrderRuleEntryRelId);

			return com.liferay.commerce.order.rule.model.
				CommerceOrderRuleEntryRelSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRelSoap[]
				getCommerceOrderRuleEntryRels(long commerceOrderRuleEntryId)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.order.rule.model.
					CommerceOrderRuleEntryRel> returnValue =
						CommerceOrderRuleEntryRelServiceUtil.
							getCommerceOrderRuleEntryRels(
								commerceOrderRuleEntryId);

			return com.liferay.commerce.order.rule.model.
				CommerceOrderRuleEntryRelSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRelSoap[]
				getCommerceOrderRuleEntryRels(
					long commerceOrderRuleEntryId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.order.rule.model.
							CommerceOrderRuleEntryRel> orderByComparator)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.order.rule.model.
					CommerceOrderRuleEntryRel> returnValue =
						CommerceOrderRuleEntryRelServiceUtil.
							getCommerceOrderRuleEntryRels(
								commerceOrderRuleEntryId, start, end,
								orderByComparator);

			return com.liferay.commerce.order.rule.model.
				CommerceOrderRuleEntryRelSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommerceOrderRuleEntryRelsCount(
			long commerceOrderRuleEntryId)
		throws RemoteException {

		try {
			int returnValue =
				CommerceOrderRuleEntryRelServiceUtil.
					getCommerceOrderRuleEntryRelsCount(
						commerceOrderRuleEntryId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceOrderRuleEntryRelServiceSoap.class);

}