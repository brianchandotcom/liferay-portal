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

import com.liferay.commerce.service.CommerceOrderNoteServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link CommerceOrderNoteServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.commerce.model.CommerceOrderNoteSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.commerce.model.CommerceOrderNote}, that is translated to a
 * {@link com.liferay.commerce.model.CommerceOrderNoteSoap}. Methods that SOAP cannot
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
 * @see CommerceOrderNoteServiceHttp
 * @see com.liferay.commerce.model.CommerceOrderNoteSoap
 * @see CommerceOrderNoteServiceUtil
 * @generated
 */
@ProviderType
public class CommerceOrderNoteServiceSoap {
	public static com.liferay.commerce.model.CommerceOrderNoteSoap addCommerceOrderNote(
		long commerceOrderId, java.lang.String content, boolean restricted,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.commerce.model.CommerceOrderNote returnValue = CommerceOrderNoteServiceUtil.addCommerceOrderNote(commerceOrderId,
					content, restricted, serviceContext);

			return com.liferay.commerce.model.CommerceOrderNoteSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteCommerceOrderNote(long commerceOrderNoteId)
		throws RemoteException {
		try {
			CommerceOrderNoteServiceUtil.deleteCommerceOrderNote(commerceOrderNoteId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderNoteSoap getCommerceOrderNote(
		long commerceOrderNoteId) throws RemoteException {
		try {
			com.liferay.commerce.model.CommerceOrderNote returnValue = CommerceOrderNoteServiceUtil.getCommerceOrderNote(commerceOrderNoteId);

			return com.liferay.commerce.model.CommerceOrderNoteSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderNoteSoap[] getCommerceOrderNotes(
		long commerceOrderId, int start, int end) throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.model.CommerceOrderNote> returnValue =
				CommerceOrderNoteServiceUtil.getCommerceOrderNotes(commerceOrderId,
					start, end);

			return com.liferay.commerce.model.CommerceOrderNoteSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCommerceOrderNotesCount(long commerceOrderId)
		throws RemoteException {
		try {
			int returnValue = CommerceOrderNoteServiceUtil.getCommerceOrderNotesCount(commerceOrderId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderNoteSoap updateCommerceOrderNote(
		long commerceOrderNoteId, java.lang.String content, boolean restricted)
		throws RemoteException {
		try {
			com.liferay.commerce.model.CommerceOrderNote returnValue = CommerceOrderNoteServiceUtil.updateCommerceOrderNote(commerceOrderNoteId,
					content, restricted);

			return com.liferay.commerce.model.CommerceOrderNoteSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceOrderNoteServiceSoap.class);
}