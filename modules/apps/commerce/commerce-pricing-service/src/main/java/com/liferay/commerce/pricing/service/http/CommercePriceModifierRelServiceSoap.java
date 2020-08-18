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

import com.liferay.commerce.pricing.service.CommercePriceModifierRelServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>CommercePriceModifierRelServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.pricing.model.CommercePriceModifierRelSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.pricing.model.CommercePriceModifierRel</code>, that is translated to a
 * <code>com.liferay.commerce.pricing.model.CommercePriceModifierRelSoap</code>. Methods that SOAP
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
 * @see CommercePriceModifierRelServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommercePriceModifierRelServiceSoap {

	public static
		com.liferay.commerce.pricing.model.CommercePriceModifierRelSoap
				addCommercePriceModifierRel(
					long commercePriceModifierId, String className,
					long classPK,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws RemoteException {

		try {
			com.liferay.commerce.pricing.model.CommercePriceModifierRel
				returnValue =
					CommercePriceModifierRelServiceUtil.
						addCommercePriceModifierRel(
							commercePriceModifierId, className, classPK,
							serviceContext);

			return com.liferay.commerce.pricing.model.
				CommercePriceModifierRelSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCommercePriceModifierRel(
			long commercePriceModifierRelId)
		throws RemoteException {

		try {
			CommercePriceModifierRelServiceUtil.deleteCommercePriceModifierRel(
				commercePriceModifierRelId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.pricing.model.CommercePriceModifierRelSoap
				fetchCommercePriceModifierRel(
					long commercePriceModifierId, String className,
					long classPK)
			throws RemoteException {

		try {
			com.liferay.commerce.pricing.model.CommercePriceModifierRel
				returnValue =
					CommercePriceModifierRelServiceUtil.
						fetchCommercePriceModifierRel(
							commercePriceModifierId, className, classPK);

			return com.liferay.commerce.pricing.model.
				CommercePriceModifierRelSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.pricing.model.CommercePriceModifierRelSoap[]
				getCategoriesCommercePriceModifierRels(
					long commercePriceModifierId, String name, int start,
					int end)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.pricing.model.CommercePriceModifierRel>
					returnValue =
						CommercePriceModifierRelServiceUtil.
							getCategoriesCommercePriceModifierRels(
								commercePriceModifierId, name, start, end);

			return com.liferay.commerce.pricing.model.
				CommercePriceModifierRelSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCategoriesCommercePriceModifierRelsCount(
			long commercePriceModifierId, String name)
		throws RemoteException {

		try {
			int returnValue =
				CommercePriceModifierRelServiceUtil.
					getCategoriesCommercePriceModifierRelsCount(
						commercePriceModifierId, name);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static long[] getClassPKs(
			long commercePriceModifierRelId, String className)
		throws RemoteException {

		try {
			long[] returnValue =
				CommercePriceModifierRelServiceUtil.getClassPKs(
					commercePriceModifierRelId, className);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.pricing.model.CommercePriceModifierRelSoap
				getCommercePriceModifierRel(long commercePriceModifierRelId)
			throws RemoteException {

		try {
			com.liferay.commerce.pricing.model.CommercePriceModifierRel
				returnValue =
					CommercePriceModifierRelServiceUtil.
						getCommercePriceModifierRel(commercePriceModifierRelId);

			return com.liferay.commerce.pricing.model.
				CommercePriceModifierRelSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.pricing.model.CommercePriceModifierRelSoap[]
				getCommercePriceModifierRels(
					long commercePriceModifierRelId, String className)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.pricing.model.CommercePriceModifierRel>
					returnValue =
						CommercePriceModifierRelServiceUtil.
							getCommercePriceModifierRels(
								commercePriceModifierRelId, className);

			return com.liferay.commerce.pricing.model.
				CommercePriceModifierRelSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.pricing.model.CommercePriceModifierRelSoap[]
				getCommercePriceModifierRels(
					long commercePriceModifierRelId, String className,
					int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.pricing.model.
							CommercePriceModifierRel> orderByComparator)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.pricing.model.CommercePriceModifierRel>
					returnValue =
						CommercePriceModifierRelServiceUtil.
							getCommercePriceModifierRels(
								commercePriceModifierRelId, className, start,
								end, orderByComparator);

			return com.liferay.commerce.pricing.model.
				CommercePriceModifierRelSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommercePriceModifierRelsCount(
			long commercePriceModifierRelId, String className)
		throws RemoteException {

		try {
			int returnValue =
				CommercePriceModifierRelServiceUtil.
					getCommercePriceModifierRelsCount(
						commercePriceModifierRelId, className);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.pricing.model.CommercePriceModifierRelSoap[]
				getCommercePriceModifiersRels(String className, long classPK)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.pricing.model.CommercePriceModifierRel>
					returnValue =
						CommercePriceModifierRelServiceUtil.
							getCommercePriceModifiersRels(className, classPK);

			return com.liferay.commerce.pricing.model.
				CommercePriceModifierRelSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.pricing.model.CommercePriceModifierRelSoap[]
				getCommercePricingClassesCommercePriceModifierRels(
					long commercePriceModifierId, String title, int start,
					int end)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.pricing.model.CommercePriceModifierRel>
					returnValue =
						CommercePriceModifierRelServiceUtil.
							getCommercePricingClassesCommercePriceModifierRels(
								commercePriceModifierId, title, start, end);

			return com.liferay.commerce.pricing.model.
				CommercePriceModifierRelSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommercePricingClassesCommercePriceModifierRelsCount(
			long commercePriceModifierId, String title)
		throws RemoteException {

		try {
			int returnValue =
				CommercePriceModifierRelServiceUtil.
					getCommercePricingClassesCommercePriceModifierRelsCount(
						commercePriceModifierId, title);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.pricing.model.CommercePriceModifierRelSoap[]
				getCPDefinitionsCommercePriceModifierRels(
					long commercePriceModifierId, String name,
					String languageId, int start, int end)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.pricing.model.CommercePriceModifierRel>
					returnValue =
						CommercePriceModifierRelServiceUtil.
							getCPDefinitionsCommercePriceModifierRels(
								commercePriceModifierId, name, languageId,
								start, end);

			return com.liferay.commerce.pricing.model.
				CommercePriceModifierRelSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCPDefinitionsCommercePriceModifierRelsCount(
			long commercePriceModifierId, String name, String languageId)
		throws RemoteException {

		try {
			int returnValue =
				CommercePriceModifierRelServiceUtil.
					getCPDefinitionsCommercePriceModifierRelsCount(
						commercePriceModifierId, name, languageId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommercePriceModifierRelServiceSoap.class);

}