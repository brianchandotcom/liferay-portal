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

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.shipping.engine.fixed.service.CShippingFixedOptionRelServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link CShippingFixedOptionRelServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRelSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel}, that is translated to a
 * {@link com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRelSoap}. Methods that SOAP cannot
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
 * @see CShippingFixedOptionRelServiceHttp
 * @see com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRelSoap
 * @see CShippingFixedOptionRelServiceUtil
 * @generated
 */
@ProviderType
public class CShippingFixedOptionRelServiceSoap {
	public static com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRelSoap addCShippingFixedOptionRel(
		long commerceShippingMethodId, long commerceShippingFixedOptionId,
		long commerceWarehouseId, long commerceCountryId,
		long commerceRegionId, java.lang.String zip, double weightFrom,
		double weightTo, double fixedPrice, double rateUnitWeightPrice,
		double ratePercentage,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel returnValue =
				CShippingFixedOptionRelServiceUtil.addCShippingFixedOptionRel(commerceShippingMethodId,
					commerceShippingFixedOptionId, commerceWarehouseId,
					commerceCountryId, commerceRegionId, zip, weightFrom,
					weightTo, fixedPrice, rateUnitWeightPrice, ratePercentage,
					serviceContext);

			return com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRelSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteCShippingFixedOptionRel(
		long cShippingFixedOptionRelId) throws RemoteException {
		try {
			CShippingFixedOptionRelServiceUtil.deleteCShippingFixedOptionRel(cShippingFixedOptionRelId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRelSoap fetchCShippingFixedOptionRel(
		long cShippingFixedOptionRelId) throws RemoteException {
		try {
			com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel returnValue =
				CShippingFixedOptionRelServiceUtil.fetchCShippingFixedOptionRel(cShippingFixedOptionRelId);

			return com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRelSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRelSoap[] getCommerceShippingMethodFixedOptionRels(
		long commerceShippingMethodId, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel> returnValue =
				CShippingFixedOptionRelServiceUtil.getCommerceShippingMethodFixedOptionRels(commerceShippingMethodId,
					start, end);

			return com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRelSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRelSoap[] getCommerceShippingMethodFixedOptionRels(
		long commerceShippingMethodId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel> returnValue =
				CShippingFixedOptionRelServiceUtil.getCommerceShippingMethodFixedOptionRels(commerceShippingMethodId,
					start, end, orderByComparator);

			return com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRelSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCommerceShippingMethodFixedOptionRelsCount(
		long commerceShippingMethodId) throws RemoteException {
		try {
			int returnValue = CShippingFixedOptionRelServiceUtil.getCommerceShippingMethodFixedOptionRelsCount(commerceShippingMethodId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRelSoap[] getCShippingFixedOptionRels(
		long commerceShippingFixedOptionId, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel> returnValue =
				CShippingFixedOptionRelServiceUtil.getCShippingFixedOptionRels(commerceShippingFixedOptionId,
					start, end);

			return com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRelSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRelSoap[] getCShippingFixedOptionRels(
		long commerceShippingFixedOptionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel> returnValue =
				CShippingFixedOptionRelServiceUtil.getCShippingFixedOptionRels(commerceShippingFixedOptionId,
					start, end, orderByComparator);

			return com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRelSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRelSoap fetchCShippingFixedOptionRel(
		long commerceShippingFixedOptionId, long commerceCountryId,
		long commerceRegionId, java.lang.String zip, double weight)
		throws RemoteException {
		try {
			com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel returnValue =
				CShippingFixedOptionRelServiceUtil.fetchCShippingFixedOptionRel(commerceShippingFixedOptionId,
					commerceCountryId, commerceRegionId, zip, weight);

			return com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRelSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCShippingFixedOptionRelsCount(
		long commerceShippingFixedOptionId) throws RemoteException {
		try {
			int returnValue = CShippingFixedOptionRelServiceUtil.getCShippingFixedOptionRelsCount(commerceShippingFixedOptionId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRelSoap updateCShippingFixedOptionRel(
		long cShippingFixedOptionRelId, long commerceWarehouseId,
		long commerceCountryId, long commerceRegionId, java.lang.String zip,
		double weightFrom, double weightTo, double fixedPrice,
		double rateUnitWeightPrice, double ratePercentage)
		throws RemoteException {
		try {
			com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel returnValue =
				CShippingFixedOptionRelServiceUtil.updateCShippingFixedOptionRel(cShippingFixedOptionRelId,
					commerceWarehouseId, commerceCountryId, commerceRegionId,
					zip, weightFrom, weightTo, fixedPrice, rateUnitWeightPrice,
					ratePercentage);

			return com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRelSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CShippingFixedOptionRelServiceSoap.class);
}