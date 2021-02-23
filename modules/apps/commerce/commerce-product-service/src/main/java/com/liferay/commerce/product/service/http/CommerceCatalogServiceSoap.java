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

import com.liferay.commerce.product.service.CommerceCatalogServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>CommerceCatalogServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.product.model.CommerceCatalogSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.product.model.CommerceCatalog</code>, that is translated to a
 * <code>com.liferay.commerce.product.model.CommerceCatalogSoap</code>. Methods that SOAP
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
 * @see CommerceCatalogServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommerceCatalogServiceSoap {

	public static com.liferay.commerce.product.model.CommerceCatalogSoap
			addCommerceCatalog(
				String externalReferenceCode, String name,
				String commerceCurrencyCode, String catalogDefaultLanguageId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CommerceCatalog returnValue =
				CommerceCatalogServiceUtil.addCommerceCatalog(
					externalReferenceCode, name, commerceCurrencyCode,
					catalogDefaultLanguageId, serviceContext);

			return com.liferay.commerce.product.model.CommerceCatalogSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CommerceCatalogSoap
			deleteCommerceCatalog(long commerceCatalogId)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CommerceCatalog returnValue =
				CommerceCatalogServiceUtil.deleteCommerceCatalog(
					commerceCatalogId);

			return com.liferay.commerce.product.model.CommerceCatalogSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CommerceCatalogSoap
			fetchByExternalReferenceCode(
				String externalReferenceCode, long companyId)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CommerceCatalog returnValue =
				CommerceCatalogServiceUtil.fetchByExternalReferenceCode(
					externalReferenceCode, companyId);

			return com.liferay.commerce.product.model.CommerceCatalogSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CommerceCatalogSoap
			fetchCommerceCatalog(long commerceCatalogId)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CommerceCatalog returnValue =
				CommerceCatalogServiceUtil.fetchCommerceCatalog(
					commerceCatalogId);

			return com.liferay.commerce.product.model.CommerceCatalogSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CommerceCatalogSoap
			fetchCommerceCatalogByGroupId(long groupId)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CommerceCatalog returnValue =
				CommerceCatalogServiceUtil.fetchCommerceCatalogByGroupId(
					groupId);

			return com.liferay.commerce.product.model.CommerceCatalogSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CommerceCatalogSoap
			getCommerceCatalog(long commerceCatalogId)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CommerceCatalog returnValue =
				CommerceCatalogServiceUtil.getCommerceCatalog(
					commerceCatalogId);

			return com.liferay.commerce.product.model.CommerceCatalogSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CommerceCatalogSoap[]
			getCommerceCatalogs(long companyId, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.product.model.CommerceCatalog>
				returnValue = CommerceCatalogServiceUtil.getCommerceCatalogs(
					companyId, start, end);

			return com.liferay.commerce.product.model.CommerceCatalogSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CommerceCatalogSoap[]
			searchCommerceCatalogs(
				long companyId, String keywords, int start, int end,
				com.liferay.portal.kernel.search.Sort sort)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.product.model.CommerceCatalog>
				returnValue = CommerceCatalogServiceUtil.searchCommerceCatalogs(
					companyId, keywords, start, end, sort);

			return com.liferay.commerce.product.model.CommerceCatalogSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int searchCommerceCatalogsCount(
			long companyId, String keywords)
		throws RemoteException {

		try {
			int returnValue =
				CommerceCatalogServiceUtil.searchCommerceCatalogsCount(
					companyId, keywords);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CommerceCatalogSoap
			updateCommerceCatalog(
				long commerceCatalogId, String name,
				String commerceCurrencyCode, String catalogDefaultLanguageId)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CommerceCatalog returnValue =
				CommerceCatalogServiceUtil.updateCommerceCatalog(
					commerceCatalogId, name, commerceCurrencyCode,
					catalogDefaultLanguageId);

			return com.liferay.commerce.product.model.CommerceCatalogSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CommerceCatalogSoap
			updateCommerceCatalogExternalReferenceCode(
				String externalReferenceCode, long commerceCatalogId)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CommerceCatalog returnValue =
				CommerceCatalogServiceUtil.
					updateCommerceCatalogExternalReferenceCode(
						externalReferenceCode, commerceCatalogId);

			return com.liferay.commerce.product.model.CommerceCatalogSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceCatalogServiceSoap.class);

}