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

package com.liferay.commerce.pricing.service.http;

import com.liferay.commerce.pricing.service.CommercePricingClassCPDefinitionRelServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>CommercePricingClassCPDefinitionRelServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.pricing.model.CommercePricingClassCPDefinitionRelSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.pricing.model.CommercePricingClassCPDefinitionRel</code>, that is translated to a
 * <code>com.liferay.commerce.pricing.model.CommercePricingClassCPDefinitionRelSoap</code>. Methods that SOAP
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
 * @author Riccardo Alberti
 * @see CommercePricingClassCPDefinitionRelServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommercePricingClassCPDefinitionRelServiceSoap {

	public static
		com.liferay.commerce.pricing.model.
			CommercePricingClassCPDefinitionRelSoap
					addCommercePricingClassCPDefinitionRel(
						long commercePricingClassId, long cpDefinitionId,
						com.liferay.portal.kernel.service.ServiceContext
							serviceContext)
				throws RemoteException {

		try {
			com.liferay.commerce.pricing.model.
				CommercePricingClassCPDefinitionRel returnValue =
					CommercePricingClassCPDefinitionRelServiceUtil.
						addCommercePricingClassCPDefinitionRel(
							commercePricingClassId, cpDefinitionId,
							serviceContext);

			return com.liferay.commerce.pricing.model.
				CommercePricingClassCPDefinitionRelSoap.toSoapModel(
					returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.pricing.model.
			CommercePricingClassCPDefinitionRelSoap
					deleteCommercePricingClassCPDefinitionRel(
						com.liferay.commerce.pricing.model.
							CommercePricingClassCPDefinitionRelSoap
								commercePricingClassCPDefinitionRel)
				throws RemoteException {

		try {
			com.liferay.commerce.pricing.model.
				CommercePricingClassCPDefinitionRel returnValue =
					CommercePricingClassCPDefinitionRelServiceUtil.
						deleteCommercePricingClassCPDefinitionRel(
							com.liferay.commerce.pricing.model.impl.
								CommercePricingClassCPDefinitionRelModelImpl.
									toModel(
										commercePricingClassCPDefinitionRel));

			return com.liferay.commerce.pricing.model.
				CommercePricingClassCPDefinitionRelSoap.toSoapModel(
					returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.pricing.model.
			CommercePricingClassCPDefinitionRelSoap
					deleteCommercePricingClassCPDefinitionRel(
						long commercePricingClassCPDefinitionRelId)
				throws RemoteException {

		try {
			com.liferay.commerce.pricing.model.
				CommercePricingClassCPDefinitionRel returnValue =
					CommercePricingClassCPDefinitionRelServiceUtil.
						deleteCommercePricingClassCPDefinitionRel(
							commercePricingClassCPDefinitionRelId);

			return com.liferay.commerce.pricing.model.
				CommercePricingClassCPDefinitionRelSoap.toSoapModel(
					returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.pricing.model.
			CommercePricingClassCPDefinitionRelSoap
					fetchCommercePricingClassCPDefinitionRel(
						long commercePricingClassId, long cpDefinitionId)
				throws RemoteException {

		try {
			com.liferay.commerce.pricing.model.
				CommercePricingClassCPDefinitionRel returnValue =
					CommercePricingClassCPDefinitionRelServiceUtil.
						fetchCommercePricingClassCPDefinitionRel(
							commercePricingClassId, cpDefinitionId);

			return com.liferay.commerce.pricing.model.
				CommercePricingClassCPDefinitionRelSoap.toSoapModel(
					returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.pricing.model.
			CommercePricingClassCPDefinitionRelSoap
					getCommercePricingClassCPDefinitionRel(
						long commercePricingClassCPDefinitionRelId)
				throws RemoteException {

		try {
			com.liferay.commerce.pricing.model.
				CommercePricingClassCPDefinitionRel returnValue =
					CommercePricingClassCPDefinitionRelServiceUtil.
						getCommercePricingClassCPDefinitionRel(
							commercePricingClassCPDefinitionRelId);

			return com.liferay.commerce.pricing.model.
				CommercePricingClassCPDefinitionRelSoap.toSoapModel(
					returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.pricing.model.
			CommercePricingClassCPDefinitionRelSoap[]
					getCommercePricingClassCPDefinitionRelByClassId(
						long commercePricingClassId)
				throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.pricing.model.
					CommercePricingClassCPDefinitionRel> returnValue =
						CommercePricingClassCPDefinitionRelServiceUtil.
							getCommercePricingClassCPDefinitionRelByClassId(
								commercePricingClassId);

			return com.liferay.commerce.pricing.model.
				CommercePricingClassCPDefinitionRelSoap.toSoapModels(
					returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.pricing.model.
			CommercePricingClassCPDefinitionRelSoap[]
					getCommercePricingClassCPDefinitionRels(
						long commercePricingClassId, int start, int end,
						com.liferay.portal.kernel.util.OrderByComparator
							<com.liferay.commerce.pricing.model.
								CommercePricingClassCPDefinitionRel>
									orderByComparator)
				throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.pricing.model.
					CommercePricingClassCPDefinitionRel> returnValue =
						CommercePricingClassCPDefinitionRelServiceUtil.
							getCommercePricingClassCPDefinitionRels(
								commercePricingClassId, start, end,
								orderByComparator);

			return com.liferay.commerce.pricing.model.
				CommercePricingClassCPDefinitionRelSoap.toSoapModels(
					returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommercePricingClassCPDefinitionRelsCount(
			long commercePricingClassId)
		throws RemoteException {

		try {
			int returnValue =
				CommercePricingClassCPDefinitionRelServiceUtil.
					getCommercePricingClassCPDefinitionRelsCount(
						commercePricingClassId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommercePricingClassCPDefinitionRelsCount(
			long commercePricingClassId, String name, String languageId)
		throws RemoteException {

		try {
			int returnValue =
				CommercePricingClassCPDefinitionRelServiceUtil.
					getCommercePricingClassCPDefinitionRelsCount(
						commercePricingClassId, name, languageId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static long[] getCPDefinitionIds(long commercePricingClassId)
		throws RemoteException {

		try {
			long[] returnValue =
				CommercePricingClassCPDefinitionRelServiceUtil.
					getCPDefinitionIds(commercePricingClassId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.pricing.model.
			CommercePricingClassCPDefinitionRelSoap[]
					searchByCommercePricingClassId(
						long commercePricingClassId, String name,
						String languageId, int start, int end)
				throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.pricing.model.
					CommercePricingClassCPDefinitionRel> returnValue =
						CommercePricingClassCPDefinitionRelServiceUtil.
							searchByCommercePricingClassId(
								commercePricingClassId, name, languageId, start,
								end);

			return com.liferay.commerce.pricing.model.
				CommercePricingClassCPDefinitionRelSoap.toSoapModels(
					returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommercePricingClassCPDefinitionRelServiceSoap.class);

}