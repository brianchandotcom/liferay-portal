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

package com.liferay.dynamic.data.mapping.service;

import com.liferay.dynamic.data.mapping.model.DDMTemplateVersion;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for DDMTemplateVersion. This utility wraps
 * <code>com.liferay.dynamic.data.mapping.service.impl.DDMTemplateVersionServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DDMTemplateVersionService
 * @generated
 */
public class DDMTemplateVersionServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.dynamic.data.mapping.service.impl.DDMTemplateVersionServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static DDMTemplateVersion getLatestTemplateVersion(long templateId)
		throws PortalException {

		return getService().getLatestTemplateVersion(templateId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static DDMTemplateVersion getTemplateVersion(long templateVersionId)
		throws PortalException {

		return getService().getTemplateVersion(templateVersionId);
	}

	public static List<DDMTemplateVersion> getTemplateVersions(
			long templateId, int start, int end,
			OrderByComparator<DDMTemplateVersion> orderByComparator)
		throws PortalException {

		return getService().getTemplateVersions(
			templateId, start, end, orderByComparator);
	}

	public static int getTemplateVersionsCount(long templateId)
		throws PortalException {

		return getService().getTemplateVersionsCount(templateId);
	}

	public static DDMTemplateVersionService getService() {
		return _service;
	}

	public static void setService(DDMTemplateVersionService service) {
		_service = service;
	}

	private static volatile DDMTemplateVersionService _service;

}