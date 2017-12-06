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

package com.liferay.commerce.price.list.qualification.type.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.price.list.qualification.type.service.CommercePriceListUserRelServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link CommercePriceListUserRelServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRelSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel}, that is translated to a
 * {@link com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRelSoap}. Methods that SOAP cannot
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
 * @see CommercePriceListUserRelServiceHttp
 * @see com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRelSoap
 * @see CommercePriceListUserRelServiceUtil
 * @generated
 */
@ProviderType
public class CommercePriceListUserRelServiceSoap {
	public static com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRelSoap addCommercePriceListUserRel(
		long commercePriceListQualificationTypeRelId,
		java.lang.String className, long classPK,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel returnValue =
				CommercePriceListUserRelServiceUtil.addCommercePriceListUserRel(commercePriceListQualificationTypeRelId,
					className, classPK, serviceContext);

			return com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRelSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteCommercePriceListUserRel(
		long commercePriceListUserRelId) throws RemoteException {
		try {
			CommercePriceListUserRelServiceUtil.deleteCommercePriceListUserRel(commercePriceListUserRelId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteCommercePriceListUserRels(
		long commercePriceListQualificationTypeRelId,
		java.lang.String className, long classPK) throws RemoteException {
		try {
			CommercePriceListUserRelServiceUtil.deleteCommercePriceListUserRels(commercePriceListQualificationTypeRelId,
				className, classPK);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRelSoap getCommercePriceListUserRel(
		long commercePriceListUserRelId) throws RemoteException {
		try {
			com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel returnValue =
				CommercePriceListUserRelServiceUtil.getCommercePriceListUserRel(commercePriceListUserRelId);

			return com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRelSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRelSoap[] getCommercePriceListUserRels(
		long commercePriceListQualificationTypeRelId, java.lang.String className)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel> returnValue =
				CommercePriceListUserRelServiceUtil.getCommercePriceListUserRels(commercePriceListQualificationTypeRelId,
					className);

			return com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRelSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRelSoap[] getCommercePriceListUserRels(
		long commercePriceListQualificationTypeRelId,
		java.lang.String className, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel> returnValue =
				CommercePriceListUserRelServiceUtil.getCommercePriceListUserRels(commercePriceListQualificationTypeRelId,
					className, start, end);

			return com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRelSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRelSoap[] getCommercePriceListUserRels(
		long commercePriceListQualificationTypeRelId,
		java.lang.String className, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel> returnValue =
				CommercePriceListUserRelServiceUtil.getCommercePriceListUserRels(commercePriceListQualificationTypeRelId,
					className, start, end, orderByComparator);

			return com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRelSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCommercePriceListUserRelsCount(
		long commercePriceListQualificationTypeRelId, java.lang.String className)
		throws RemoteException {
		try {
			int returnValue = CommercePriceListUserRelServiceUtil.getCommercePriceListUserRelsCount(commercePriceListQualificationTypeRelId,
					className);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRelSoap updateCommercePriceListUserRel(
		long commercePriceListUserRelId,
		long commercePriceListQualificationTypeRelId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel returnValue =
				CommercePriceListUserRelServiceUtil.updateCommercePriceListUserRel(commercePriceListUserRelId,
					commercePriceListQualificationTypeRelId, serviceContext);

			return com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRelSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommercePriceListUserRelServiceSoap.class);
}