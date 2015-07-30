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

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for DDMTemplateVersion. This utility wraps
 * {@link com.liferay.dynamic.data.mapping.service.impl.DDMTemplateVersionServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DDMTemplateVersionService
 * @see com.liferay.dynamic.data.mapping.service.base.DDMTemplateVersionServiceBaseImpl
 * @see com.liferay.dynamic.data.mapping.service.impl.DDMTemplateVersionServiceImpl
 * @generated
 */
@ProviderType
public class DDMTemplateVersionServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.dynamic.data.mapping.service.impl.DDMTemplateVersionServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	public static com.liferay.dynamic.data.mapping.model.DDMTemplateVersion getLatestTemplateVersion(
		long templateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getLatestTemplateVersion(templateId);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMTemplateVersion getTemplateVersion(
		long templateVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getTemplateVersion(templateVersionId);
	}

	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplateVersion> getTemplateVersions(
		long templateId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMTemplateVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getTemplateVersions(templateId, start, end,
			orderByComparator);
	}

	public static int getTemplateVersionsCount(long templateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getTemplateVersionsCount(templateId);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static DDMTemplateVersionService getService() {
		return _serviceTracker.getService();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(DDMTemplateVersionService service) {
	}

	private static ServiceTracker<DDMTemplateVersionService, DDMTemplateVersionService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(DDMTemplateVersionServiceUtil.class);

		_serviceTracker = new ServiceTracker<DDMTemplateVersionService, DDMTemplateVersionService>(bundle.getBundleContext(),
				DDMTemplateVersionService.class, null);

		_serviceTracker.open();
	}
}