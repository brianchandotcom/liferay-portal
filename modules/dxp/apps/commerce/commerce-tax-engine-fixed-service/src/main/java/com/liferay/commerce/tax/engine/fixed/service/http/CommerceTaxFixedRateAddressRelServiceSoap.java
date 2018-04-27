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

package com.liferay.commerce.tax.engine.fixed.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.tax.engine.fixed.service.CommerceTaxFixedRateAddressRelServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link CommerceTaxFixedRateAddressRelServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRelSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel}, that is translated to a
 * {@link com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRelSoap}. Methods that SOAP cannot
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
 * @see CommerceTaxFixedRateAddressRelServiceHttp
 * @see com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRelSoap
 * @see CommerceTaxFixedRateAddressRelServiceUtil
 * @generated
 */
@ProviderType
public class CommerceTaxFixedRateAddressRelServiceSoap {
	public static com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRelSoap addCommerceTaxFixedRateAddressRel(
		long commerceTaxMethodId, long cpTaxCategoryId, long commerceCountryId,
		long commerceRegionId, String zip, double rate,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel returnValue =
				CommerceTaxFixedRateAddressRelServiceUtil.addCommerceTaxFixedRateAddressRel(commerceTaxMethodId,
					cpTaxCategoryId, commerceCountryId, commerceRegionId, zip,
					rate, serviceContext);

			return com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRelSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteCommerceTaxFixedRateAddressRel(
		long commerceTaxFixedRateAddressRelId) throws RemoteException {
		try {
			CommerceTaxFixedRateAddressRelServiceUtil.deleteCommerceTaxFixedRateAddressRel(commerceTaxFixedRateAddressRelId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRelSoap fetchCommerceTaxFixedRateAddressRel(
		long commerceTaxFixedRateAddressRelId) throws RemoteException {
		try {
			com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel returnValue =
				CommerceTaxFixedRateAddressRelServiceUtil.fetchCommerceTaxFixedRateAddressRel(commerceTaxFixedRateAddressRelId);

			return com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRelSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRelSoap[] getCommerceTaxMethodFixedRateAddressRels(
		long groupId, long commerceTaxMethodId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel> returnValue =
				CommerceTaxFixedRateAddressRelServiceUtil.getCommerceTaxMethodFixedRateAddressRels(groupId,
					commerceTaxMethodId, start, end, orderByComparator);

			return com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRelSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCommerceTaxMethodFixedRateAddressRelsCount(
		long groupId, long commerceTaxMethodId) throws RemoteException {
		try {
			int returnValue = CommerceTaxFixedRateAddressRelServiceUtil.getCommerceTaxMethodFixedRateAddressRelsCount(groupId,
					commerceTaxMethodId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRelSoap updateCommerceTaxFixedRateAddressRel(
		long commerceTaxFixedRateAddressRelId, long commerceCountryId,
		long commerceRegionId, String zip, double rate)
		throws RemoteException {
		try {
			com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel returnValue =
				CommerceTaxFixedRateAddressRelServiceUtil.updateCommerceTaxFixedRateAddressRel(commerceTaxFixedRateAddressRelId,
					commerceCountryId, commerceRegionId, zip, rate);

			return com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRelSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceTaxFixedRateAddressRelServiceSoap.class);
}