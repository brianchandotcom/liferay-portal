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

package com.liferay.portal.kernel.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Repository;

/**
 * Provides the remote service utility for Repository. This utility wraps
 * <code>com.liferay.portal.service.impl.RepositoryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see RepositoryService
 * @generated
 */
public class RepositoryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.RepositoryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static Repository addRepository(
			long groupId, long classNameId, long parentFolderId, String name,
			String description, String portletId,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsUnicodeProperties,
			ServiceContext serviceContext)
		throws PortalException {

		return getService().addRepository(
			groupId, classNameId, parentFolderId, name, description, portletId,
			typeSettingsUnicodeProperties, serviceContext);
	}

	public static void checkRepository(long repositoryId)
		throws PortalException {

		getService().checkRepository(repositoryId);
	}

	public static void deleteRepository(long repositoryId)
		throws PortalException {

		getService().deleteRepository(repositoryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static Repository getRepository(long repositoryId)
		throws PortalException {

		return getService().getRepository(repositoryId);
	}

	public static Repository getRepository(long groupId, String portletId)
		throws PortalException {

		return getService().getRepository(groupId, portletId);
	}

	public static com.liferay.portal.kernel.util.UnicodeProperties
			getTypeSettingsProperties(long repositoryId)
		throws PortalException {

		return getService().getTypeSettingsProperties(repositoryId);
	}

	public static void updateRepository(
			long repositoryId, String name, String description)
		throws PortalException {

		getService().updateRepository(repositoryId, name, description);
	}

	public static RepositoryService getService() {
		return _service;
	}

	public static void setService(RepositoryService service) {
		_service = service;
	}

	private static volatile RepositoryService _service;

}