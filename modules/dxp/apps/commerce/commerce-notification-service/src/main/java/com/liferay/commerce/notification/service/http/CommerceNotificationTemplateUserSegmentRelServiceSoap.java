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

package com.liferay.commerce.notification.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.notification.service.CommerceNotificationTemplateUserSegmentRelServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link CommerceNotificationTemplateUserSegmentRelServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRelSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRel}, that is translated to a
 * {@link com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRelSoap}. Methods that SOAP cannot
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
 * @see CommerceNotificationTemplateUserSegmentRelServiceHttp
 * @see com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRelSoap
 * @see CommerceNotificationTemplateUserSegmentRelServiceUtil
 * @generated
 */
@ProviderType
public class CommerceNotificationTemplateUserSegmentRelServiceSoap {
	public static com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRelSoap addCommerceNotificationTemplateUserSegmentRel(
		long commerceNotificationTemplateId, long commerceUserSegmentEntryId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRel returnValue =
				CommerceNotificationTemplateUserSegmentRelServiceUtil.addCommerceNotificationTemplateUserSegmentRel(commerceNotificationTemplateId,
					commerceUserSegmentEntryId, serviceContext);

			return com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRelSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteCommerceNotificationTemplateUserSegmentRel(
		long commerceNotificationTemplateUserSegmentRelId)
		throws RemoteException {
		try {
			CommerceNotificationTemplateUserSegmentRelServiceUtil.deleteCommerceNotificationTemplateUserSegmentRel(commerceNotificationTemplateUserSegmentRelId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRelSoap fetchCommerceNotificationTemplateUserSegmentRel(
		long commerceNotificationTemplateId, long commerceUserSegmentEntryId)
		throws RemoteException {
		try {
			com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRel returnValue =
				CommerceNotificationTemplateUserSegmentRelServiceUtil.fetchCommerceNotificationTemplateUserSegmentRel(commerceNotificationTemplateId,
					commerceUserSegmentEntryId);

			return com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRelSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRelSoap[] getCommerceNotificationTemplateUserSegmentRels(
		long commerceNotificationTemplateId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRel> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRel> returnValue =
				CommerceNotificationTemplateUserSegmentRelServiceUtil.getCommerceNotificationTemplateUserSegmentRels(commerceNotificationTemplateId,
					start, end, orderByComparator);

			return com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRelSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceNotificationTemplateUserSegmentRelServiceSoap.class);
}