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

package com.liferay.dynamic.data.lists.service.http.jsonws;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.lists.service.DDLRecordVersionService;

import com.liferay.portal.kernel.jsonwebservice.JSONWebService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the JSONWS remote service utility for DDLRecordVersion. This utility wraps
 * {@link com.liferay.dynamic.data.lists.service.impl.DDLRecordVersionServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordVersionService
 * @generated
 */
@Component(immediate = true, property =  {
	"json.web.service.context.name=ddl", "json.web.service.context.path=DDLRecordVersion"}, service = DDLRecordVersionJsonService.class)
@JSONWebService
@ProviderType
public class DDLRecordVersionJsonService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.dynamic.data.lists.service.impl.DDLRecordVersionServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _service.getBeanIdentifier();
	}

	public com.liferay.dynamic.data.lists.model.DDLRecordVersion getRecordVersion(
		long recordId, java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getRecordVersion(recordId, version);
	}

	public com.liferay.dynamic.data.lists.model.DDLRecordVersion getRecordVersion(
		long recordVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getRecordVersion(recordVersionId);
	}

	public java.util.List<com.liferay.dynamic.data.lists.model.DDLRecordVersion> getRecordVersions(
		long recordId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.lists.model.DDLRecordVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getRecordVersions(recordId, start, end,
			orderByComparator);
	}

	public int getRecordVersionsCount(long recordId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getRecordVersionsCount(recordId);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_service.setBeanIdentifier(beanIdentifier);
	}

	@Reference
	protected void setService(DDLRecordVersionService service) {
		_service = service;
	}

	private DDLRecordVersionService _service;
}