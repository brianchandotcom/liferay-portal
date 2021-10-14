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

import com.liferay.commerce.order.rule.service.COREntryRelServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>COREntryRelServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.order.rule.model.COREntryRelSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.order.rule.model.COREntryRel</code>, that is translated to a
 * <code>com.liferay.commerce.order.rule.model.COREntryRelSoap</code>. Methods that SOAP
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
 * @see COREntryRelServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class COREntryRelServiceSoap {

	public static com.liferay.commerce.order.rule.model.COREntryRelSoap
			addCOREntryRel(String className, long classPK, long corEntryId)
		throws RemoteException {

		try {
			com.liferay.commerce.order.rule.model.COREntryRel returnValue =
				COREntryRelServiceUtil.addCOREntryRel(
					className, classPK, corEntryId);

			return com.liferay.commerce.order.rule.model.COREntryRelSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCOREntryRel(long corEntryRelId)
		throws RemoteException {

		try {
			COREntryRelServiceUtil.deleteCOREntryRel(corEntryRelId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCOREntryRels(String className, long corEntryId)
		throws RemoteException {

		try {
			COREntryRelServiceUtil.deleteCOREntryRels(className, corEntryId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCOREntryRelsByCOREntryId(long corEntryId)
		throws RemoteException {

		try {
			COREntryRelServiceUtil.deleteCOREntryRelsByCOREntryId(corEntryId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.order.rule.model.COREntryRelSoap
			fetchCOREntryRel(String className, long classPK, long corEntryId)
		throws RemoteException {

		try {
			com.liferay.commerce.order.rule.model.COREntryRel returnValue =
				COREntryRelServiceUtil.fetchCOREntryRel(
					className, classPK, corEntryId);

			return com.liferay.commerce.order.rule.model.COREntryRelSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.order.rule.model.COREntryRelSoap[]
			getAccountEntryCOREntryRels(
				long corEntryId, String keywords, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.order.rule.model.COREntryRel>
				returnValue =
					COREntryRelServiceUtil.getAccountEntryCOREntryRels(
						corEntryId, keywords, start, end);

			return com.liferay.commerce.order.rule.model.COREntryRelSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getAccountEntryCOREntryRelsCount(
			long corEntryId, String keywords)
		throws RemoteException {

		try {
			int returnValue =
				COREntryRelServiceUtil.getAccountEntryCOREntryRelsCount(
					corEntryId, keywords);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.order.rule.model.COREntryRelSoap[]
			getAccountGroupCOREntryRels(
				long corEntryId, String keywords, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.order.rule.model.COREntryRel>
				returnValue =
					COREntryRelServiceUtil.getAccountGroupCOREntryRels(
						corEntryId, keywords, start, end);

			return com.liferay.commerce.order.rule.model.COREntryRelSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getAccountGroupCOREntryRelsCount(
			long corEntryId, String keywords)
		throws RemoteException {

		try {
			int returnValue =
				COREntryRelServiceUtil.getAccountGroupCOREntryRelsCount(
					corEntryId, keywords);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.order.rule.model.COREntryRelSoap[]
			getCommerceChannelCOREntryRels(
				long corEntryId, String keywords, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.order.rule.model.COREntryRel>
				returnValue =
					COREntryRelServiceUtil.getCommerceChannelCOREntryRels(
						corEntryId, keywords, start, end);

			return com.liferay.commerce.order.rule.model.COREntryRelSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommerceChannelCOREntryRelsCount(
			long corEntryId, String keywords)
		throws RemoteException {

		try {
			int returnValue =
				COREntryRelServiceUtil.getCommerceChannelCOREntryRelsCount(
					corEntryId, keywords);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.order.rule.model.COREntryRelSoap[]
			getCommerceOrderTypeCOREntryRels(
				long corEntryId, String keywords, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.order.rule.model.COREntryRel>
				returnValue =
					COREntryRelServiceUtil.getCommerceOrderTypeCOREntryRels(
						corEntryId, keywords, start, end);

			return com.liferay.commerce.order.rule.model.COREntryRelSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommerceOrderTypeCOREntryRelsCount(
			long corEntryId, String keywords)
		throws RemoteException {

		try {
			int returnValue =
				COREntryRelServiceUtil.getCommerceOrderTypeCOREntryRelsCount(
					corEntryId, keywords);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.order.rule.model.COREntryRelSoap
			getCOREntryRel(long corEntryRelId)
		throws RemoteException {

		try {
			com.liferay.commerce.order.rule.model.COREntryRel returnValue =
				COREntryRelServiceUtil.getCOREntryRel(corEntryRelId);

			return com.liferay.commerce.order.rule.model.COREntryRelSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.order.rule.model.COREntryRelSoap[]
			getCOREntryRels(long corEntryId)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.order.rule.model.COREntryRel>
				returnValue = COREntryRelServiceUtil.getCOREntryRels(
					corEntryId);

			return com.liferay.commerce.order.rule.model.COREntryRelSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.order.rule.model.COREntryRelSoap[]
			getCOREntryRels(
				long corEntryId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.order.rule.model.COREntryRel>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.order.rule.model.COREntryRel>
				returnValue = COREntryRelServiceUtil.getCOREntryRels(
					corEntryId, start, end, orderByComparator);

			return com.liferay.commerce.order.rule.model.COREntryRelSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCOREntryRelsCount(long corEntryId)
		throws RemoteException {

		try {
			int returnValue = COREntryRelServiceUtil.getCOREntryRelsCount(
				corEntryId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		COREntryRelServiceSoap.class);

}