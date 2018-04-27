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

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.service.CPAttachmentFileEntryServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * {@link CPAttachmentFileEntryServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.commerce.product.model.CPAttachmentFileEntrySoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.commerce.product.model.CPAttachmentFileEntry}, that is translated to a
 * {@link com.liferay.commerce.product.model.CPAttachmentFileEntrySoap}. Methods that SOAP cannot
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
 * @author Marco Leo
 * @see CPAttachmentFileEntryServiceHttp
 * @see com.liferay.commerce.product.model.CPAttachmentFileEntrySoap
 * @see CPAttachmentFileEntryServiceUtil
 * @generated
 */
@ProviderType
public class CPAttachmentFileEntryServiceSoap {
	public static com.liferay.commerce.product.model.CPAttachmentFileEntrySoap addCPAttachmentFileEntry(
		long classNameId, long classPK, long fileEntryId, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, int expirationDateMonth, int expirationDateDay,
		int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		String[] titleMapLanguageIds, String[] titleMapValues, String json,
		double priority, int type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(titleMapLanguageIds,
					titleMapValues);

			com.liferay.commerce.product.model.CPAttachmentFileEntry returnValue =
				CPAttachmentFileEntryServiceUtil.addCPAttachmentFileEntry(classNameId,
					classPK, fileEntryId, displayDateMonth, displayDateDay,
					displayDateYear, displayDateHour, displayDateMinute,
					expirationDateMonth, expirationDateDay, expirationDateYear,
					expirationDateHour, expirationDateMinute, neverExpire,
					titleMap, json, priority, type, serviceContext);

			return com.liferay.commerce.product.model.CPAttachmentFileEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPAttachmentFileEntrySoap deleteCPAttachmentFileEntry(
		com.liferay.commerce.product.model.CPAttachmentFileEntrySoap cpAttachmentFileEntry)
		throws RemoteException {
		try {
			com.liferay.commerce.product.model.CPAttachmentFileEntry returnValue =
				CPAttachmentFileEntryServiceUtil.deleteCPAttachmentFileEntry(com.liferay.commerce.product.model.impl.CPAttachmentFileEntryModelImpl.toModel(
						cpAttachmentFileEntry));

			return com.liferay.commerce.product.model.CPAttachmentFileEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPAttachmentFileEntrySoap deleteCPAttachmentFileEntry(
		long cpAttachmentFileEntryId) throws RemoteException {
		try {
			com.liferay.commerce.product.model.CPAttachmentFileEntry returnValue =
				CPAttachmentFileEntryServiceUtil.deleteCPAttachmentFileEntry(cpAttachmentFileEntryId);

			return com.liferay.commerce.product.model.CPAttachmentFileEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPAttachmentFileEntrySoap fetchCPAttachmentFileEntry(
		long cpAttachmentFileEntryId) throws RemoteException {
		try {
			com.liferay.commerce.product.model.CPAttachmentFileEntry returnValue =
				CPAttachmentFileEntryServiceUtil.fetchCPAttachmentFileEntry(cpAttachmentFileEntryId);

			return com.liferay.commerce.product.model.CPAttachmentFileEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPAttachmentFileEntrySoap[] getCPAttachmentFileEntries(
		long classNameId, long classPK, int type, int status, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.product.model.CPAttachmentFileEntry> returnValue =
				CPAttachmentFileEntryServiceUtil.getCPAttachmentFileEntries(classNameId,
					classPK, type, status, start, end);

			return com.liferay.commerce.product.model.CPAttachmentFileEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPAttachmentFileEntrySoap[] getCPAttachmentFileEntries(
		long classNameId, long classPK, int type, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPAttachmentFileEntry> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.product.model.CPAttachmentFileEntry> returnValue =
				CPAttachmentFileEntryServiceUtil.getCPAttachmentFileEntries(classNameId,
					classPK, type, status, start, end, orderByComparator);

			return com.liferay.commerce.product.model.CPAttachmentFileEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCPAttachmentFileEntriesCount(long classNameId,
		long classPK, int type, int status) throws RemoteException {
		try {
			int returnValue = CPAttachmentFileEntryServiceUtil.getCPAttachmentFileEntriesCount(classNameId,
					classPK, type, status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPAttachmentFileEntrySoap getCPAttachmentFileEntry(
		long cpAttachmentFileEntryId) throws RemoteException {
		try {
			com.liferay.commerce.product.model.CPAttachmentFileEntry returnValue =
				CPAttachmentFileEntryServiceUtil.getCPAttachmentFileEntry(cpAttachmentFileEntryId);

			return com.liferay.commerce.product.model.CPAttachmentFileEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPAttachmentFileEntrySoap updateCPAttachmentFileEntry(
		long cpAttachmentFileEntryId, long fileEntryId, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, int expirationDateMonth, int expirationDateDay,
		int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		String[] titleMapLanguageIds, String[] titleMapValues, String json,
		double priority, int type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(titleMapLanguageIds,
					titleMapValues);

			com.liferay.commerce.product.model.CPAttachmentFileEntry returnValue =
				CPAttachmentFileEntryServiceUtil.updateCPAttachmentFileEntry(cpAttachmentFileEntryId,
					fileEntryId, displayDateMonth, displayDateDay,
					displayDateYear, displayDateHour, displayDateMinute,
					expirationDateMonth, expirationDateDay, expirationDateYear,
					expirationDateHour, expirationDateMinute, neverExpire,
					titleMap, json, priority, type, serviceContext);

			return com.liferay.commerce.product.model.CPAttachmentFileEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CPAttachmentFileEntryServiceSoap.class);
}