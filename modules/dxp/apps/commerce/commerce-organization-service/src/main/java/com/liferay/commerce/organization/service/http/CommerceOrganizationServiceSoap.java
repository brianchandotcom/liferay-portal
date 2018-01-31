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

package com.liferay.commerce.organization.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.organization.service.CommerceOrganizationServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link CommerceOrganizationServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
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
 * @see CommerceOrganizationServiceHttp
 * @see CommerceOrganizationServiceUtil
 * @generated
 */
@ProviderType
public class CommerceOrganizationServiceSoap {
	public static com.liferay.portal.kernel.model.Organization addOrganization(
		long parentOrganizationId, java.lang.String name,
		java.lang.String type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.kernel.model.Organization returnValue = CommerceOrganizationServiceUtil.addOrganization(parentOrganizationId,
					name, type, serviceContext);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void addOrganizationUsers(long organizationId,
		java.lang.String[] emailAddresses,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			CommerceOrganizationServiceUtil.addOrganizationUsers(organizationId,
				emailAddresses, serviceContext);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.Organization getOrganization(
		long organizationId) throws RemoteException {
		try {
			com.liferay.portal.kernel.model.Organization returnValue = CommerceOrganizationServiceUtil.getOrganization(organizationId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.Address getOrganizationPrimaryAddress(
		long organizationId) throws RemoteException {
		try {
			com.liferay.portal.kernel.model.Address returnValue = CommerceOrganizationServiceUtil.getOrganizationPrimaryAddress(organizationId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.EmailAddress getOrganizationPrimaryEmailAddress(
		long organizationId) throws RemoteException {
		try {
			com.liferay.portal.kernel.model.EmailAddress returnValue = CommerceOrganizationServiceUtil.getOrganizationPrimaryEmailAddress(organizationId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void unsetOrganizationUsers(long organizationId,
		long[] userIds) throws RemoteException {
		try {
			CommerceOrganizationServiceUtil.unsetOrganizationUsers(organizationId,
				userIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceOrganizationServiceSoap.class);
}