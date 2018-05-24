/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.price.list.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.price.list.service.CommercePriceEntryServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link CommercePriceEntryServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.commerce.price.list.model.CommercePriceEntrySoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.commerce.price.list.model.CommercePriceEntry}, that is translated to a
 * {@link com.liferay.commerce.price.list.model.CommercePriceEntrySoap}. Methods that SOAP cannot
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
 * @see CommercePriceEntryServiceHttp
 * @see com.liferay.commerce.price.list.model.CommercePriceEntrySoap
 * @see CommercePriceEntryServiceUtil
 * @generated
 */
@ProviderType
public class CommercePriceEntryServiceSoap {
	public static com.liferay.commerce.price.list.model.CommercePriceEntrySoap addCommercePriceEntry(
		long cpInstanceId, long commercePriceListId,
		java.math.BigDecimal price, java.math.BigDecimal promoPrice,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.commerce.price.list.model.CommercePriceEntry returnValue =
				CommercePriceEntryServiceUtil.addCommercePriceEntry(cpInstanceId,
					commercePriceListId, price, promoPrice, serviceContext);

			return com.liferay.commerce.price.list.model.CommercePriceEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.price.list.model.CommercePriceEntrySoap addCommercePriceEntry(
		long cpInstanceId, long commercePriceListId,
		String externalReferenceCode, java.math.BigDecimal price,
		java.math.BigDecimal promoPrice,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.commerce.price.list.model.CommercePriceEntry returnValue =
				CommercePriceEntryServiceUtil.addCommercePriceEntry(cpInstanceId,
					commercePriceListId, externalReferenceCode, price,
					promoPrice, serviceContext);

			return com.liferay.commerce.price.list.model.CommercePriceEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteCommercePriceEntry(long commercePriceEntryId)
		throws RemoteException {
		try {
			CommercePriceEntryServiceUtil.deleteCommercePriceEntry(commercePriceEntryId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.price.list.model.CommercePriceEntrySoap fetchByExternalReferenceCode(
		String externalReferenceCode) throws RemoteException {
		try {
			com.liferay.commerce.price.list.model.CommercePriceEntry returnValue =
				CommercePriceEntryServiceUtil.fetchByExternalReferenceCode(externalReferenceCode);

			return com.liferay.commerce.price.list.model.CommercePriceEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.price.list.model.CommercePriceEntrySoap fetchCommercePriceEntry(
		long commercePriceEntryId) throws RemoteException {
		try {
			com.liferay.commerce.price.list.model.CommercePriceEntry returnValue =
				CommercePriceEntryServiceUtil.fetchCommercePriceEntry(commercePriceEntryId);

			return com.liferay.commerce.price.list.model.CommercePriceEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.price.list.model.CommercePriceEntrySoap[] fetchCommercePriceEntries(
		long groupId, int start, int end) throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.price.list.model.CommercePriceEntry> returnValue =
				CommercePriceEntryServiceUtil.fetchCommercePriceEntries(groupId,
					start, end);

			return com.liferay.commerce.price.list.model.CommercePriceEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.price.list.model.CommercePriceEntrySoap[] getCommercePriceEntries(
		long commercePriceListId, int start, int end) throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.price.list.model.CommercePriceEntry> returnValue =
				CommercePriceEntryServiceUtil.getCommercePriceEntries(commercePriceListId,
					start, end);

			return com.liferay.commerce.price.list.model.CommercePriceEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.price.list.model.CommercePriceEntrySoap[] getCommercePriceEntries(
		long commercePriceListId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.price.list.model.CommercePriceEntry> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.price.list.model.CommercePriceEntry> returnValue =
				CommercePriceEntryServiceUtil.getCommercePriceEntries(commercePriceListId,
					start, end, orderByComparator);

			return com.liferay.commerce.price.list.model.CommercePriceEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCommercePriceEntriesCount(long commercePriceListId)
		throws RemoteException {
		try {
			int returnValue = CommercePriceEntryServiceUtil.getCommercePriceEntriesCount(commercePriceListId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.price.list.model.CommercePriceEntrySoap[] getInstanceCommercePriceEntries(
		long cpInstanceId, int start, int end) throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.price.list.model.CommercePriceEntry> returnValue =
				CommercePriceEntryServiceUtil.getInstanceCommercePriceEntries(cpInstanceId,
					start, end);

			return com.liferay.commerce.price.list.model.CommercePriceEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.price.list.model.CommercePriceEntrySoap[] getInstanceCommercePriceEntries(
		long cpInstanceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.price.list.model.CommercePriceEntry> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.price.list.model.CommercePriceEntry> returnValue =
				CommercePriceEntryServiceUtil.getInstanceCommercePriceEntries(cpInstanceId,
					start, end, orderByComparator);

			return com.liferay.commerce.price.list.model.CommercePriceEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getInstanceCommercePriceEntriesCount(long cpInstanceId)
		throws RemoteException {
		try {
			int returnValue = CommercePriceEntryServiceUtil.getInstanceCommercePriceEntriesCount(cpInstanceId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.price.list.model.CommercePriceEntrySoap updateCommercePriceEntry(
		long commercePriceEntryId, java.math.BigDecimal price,
		java.math.BigDecimal promoPrice,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.commerce.price.list.model.CommercePriceEntry returnValue =
				CommercePriceEntryServiceUtil.updateCommercePriceEntry(commercePriceEntryId,
					price, promoPrice, serviceContext);

			return com.liferay.commerce.price.list.model.CommercePriceEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommercePriceEntryServiceSoap.class);
}