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

package com.liferay.commerce.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.service.CommercePriceListQualificationTypeRelServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link CommercePriceListQualificationTypeRelServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.commerce.model.CommercePriceListQualificationTypeRelSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.commerce.model.CommercePriceListQualificationTypeRel}, that is translated to a
 * {@link com.liferay.commerce.model.CommercePriceListQualificationTypeRelSoap}. Methods that SOAP cannot
 * safely wire are skipped.
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
 * @see CommercePriceListQualificationTypeRelServiceHttp
 * @see com.liferay.commerce.model.CommercePriceListQualificationTypeRelSoap
 * @see CommercePriceListQualificationTypeRelServiceUtil
 * @generated
 */
@ProviderType
public class CommercePriceListQualificationTypeRelServiceSoap {
	public static com.liferay.commerce.model.CommercePriceListQualificationTypeRelSoap addCommercePriceListQualificationTypeRel(
		long commercePriceListId,
		java.lang.String commercePriceListQualificationType, int order,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.commerce.model.CommercePriceListQualificationTypeRel returnValue =
				CommercePriceListQualificationTypeRelServiceUtil.addCommercePriceListQualificationTypeRel(commercePriceListId,
					commercePriceListQualificationType, order, serviceContext);

			return com.liferay.commerce.model.CommercePriceListQualificationTypeRelSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteCommercePriceListQualificationTypeRel(
		long commercePriceListQualificationTypeRelId) throws RemoteException {
		try {
			CommercePriceListQualificationTypeRelServiceUtil.deleteCommercePriceListQualificationTypeRel(commercePriceListQualificationTypeRelId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommercePriceListQualificationTypeRelSoap fetchCommercePriceListQualificationTypeRel(
		java.lang.String commercePriceListQualificationType,
		long commercePriceListId) throws RemoteException {
		try {
			com.liferay.commerce.model.CommercePriceListQualificationTypeRel returnValue =
				CommercePriceListQualificationTypeRelServiceUtil.fetchCommercePriceListQualificationTypeRel(commercePriceListQualificationType,
					commercePriceListId);

			return com.liferay.commerce.model.CommercePriceListQualificationTypeRelSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommercePriceListQualificationTypeRelSoap[] getCommercePriceListQualificationTypeRels(
		long commercePriceListId) throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.model.CommercePriceListQualificationTypeRel> returnValue =
				CommercePriceListQualificationTypeRelServiceUtil.getCommercePriceListQualificationTypeRels(commercePriceListId);

			return com.liferay.commerce.model.CommercePriceListQualificationTypeRelSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommercePriceListQualificationTypeRelSoap[] getCommercePriceListQualificationTypeRels(
		long commercePriceListId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommercePriceListQualificationTypeRel> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.model.CommercePriceListQualificationTypeRel> returnValue =
				CommercePriceListQualificationTypeRelServiceUtil.getCommercePriceListQualificationTypeRels(commercePriceListId,
					start, end, orderByComparator);

			return com.liferay.commerce.model.CommercePriceListQualificationTypeRelSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCommercePriceListQualificationTypeRelsCount(
		long commercePriceListId) throws RemoteException {
		try {
			int returnValue = CommercePriceListQualificationTypeRelServiceUtil.getCommercePriceListQualificationTypeRelsCount(commercePriceListId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommercePriceListQualificationTypeRelSoap updateCommercePriceListQualificationTypeRel(
		long commercePriceListQualificationTypeRelId, int order,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.commerce.model.CommercePriceListQualificationTypeRel returnValue =
				CommercePriceListQualificationTypeRelServiceUtil.updateCommercePriceListQualificationTypeRel(commercePriceListQualificationTypeRelId,
					order, serviceContext);

			return com.liferay.commerce.model.CommercePriceListQualificationTypeRelSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommercePriceListQualificationTypeRelServiceSoap.class);
}