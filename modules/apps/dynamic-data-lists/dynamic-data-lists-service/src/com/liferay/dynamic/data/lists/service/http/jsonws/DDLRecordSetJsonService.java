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

import com.liferay.dynamic.data.lists.service.DDLRecordSetService;

import com.liferay.portal.kernel.jsonwebservice.JSONWebService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the JSONWS remote service utility for DDLRecordSet. This utility wraps
 * {@link com.liferay.dynamic.data.lists.service.impl.DDLRecordSetServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordSetService
 * @generated
 */
@Component(immediate = true, property =  {
	"json.web.service.context.name=ddl", "json.web.service.context.path=DDLRecordSet"}, service = DDLRecordSetJsonService.class)
@JSONWebService
@ProviderType
public class DDLRecordSetJsonService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.dynamic.data.lists.service.impl.DDLRecordSetServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public com.liferay.dynamic.data.lists.model.DDLRecordSet addRecordSet(
		long groupId, long ddmStructureId, java.lang.String recordSetKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		int minDisplayRows, int scope,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.addRecordSet(groupId, ddmStructureId, recordSetKey,
			nameMap, descriptionMap, minDisplayRows, scope, serviceContext);
	}

	public void deleteRecordSet(long recordSetId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.deleteRecordSet(recordSetId);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _service.getBeanIdentifier();
	}

	public com.liferay.dynamic.data.lists.model.DDLRecordSet getRecordSet(
		long recordSetId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getRecordSet(recordSetId);
	}

	public java.util.List<com.liferay.dynamic.data.lists.model.DDLRecordSet> getRecordSets(
		long[] groupIds) {
		return _service.getRecordSets(groupIds);
	}

	public java.util.List<com.liferay.dynamic.data.lists.model.DDLRecordSet> search(
		long companyId, long groupId, java.lang.String keywords, int scope,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.lists.model.DDLRecordSet> orderByComparator) {
		return _service.search(companyId, groupId, keywords, scope, start, end,
			orderByComparator);
	}

	public java.util.List<com.liferay.dynamic.data.lists.model.DDLRecordSet> search(
		long companyId, long groupId, java.lang.String name,
		java.lang.String description, int scope, boolean andOperator,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.lists.model.DDLRecordSet> orderByComparator) {
		return _service.search(companyId, groupId, name, description, scope,
			andOperator, start, end, orderByComparator);
	}

	public int searchCount(long companyId, long groupId,
		java.lang.String keywords, int scope) {
		return _service.searchCount(companyId, groupId, keywords, scope);
	}

	public int searchCount(long companyId, long groupId, java.lang.String name,
		java.lang.String description, int scope, boolean andOperator) {
		return _service.searchCount(companyId, groupId, name, description,
			scope, andOperator);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_service.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.dynamic.data.lists.model.DDLRecordSet updateMinDisplayRows(
		long recordSetId, int minDisplayRows,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.updateMinDisplayRows(recordSetId, minDisplayRows,
			serviceContext);
	}

	public com.liferay.dynamic.data.lists.model.DDLRecordSet updateRecordSet(
		long groupId, long ddmStructureId, java.lang.String recordSetKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		int minDisplayRows,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.updateRecordSet(groupId, ddmStructureId, recordSetKey,
			nameMap, descriptionMap, minDisplayRows, serviceContext);
	}

	public com.liferay.dynamic.data.lists.model.DDLRecordSet updateRecordSet(
		long recordSetId, long ddmStructureId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		int minDisplayRows,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.updateRecordSet(recordSetId, ddmStructureId, nameMap,
			descriptionMap, minDisplayRows, serviceContext);
	}

	@Reference
	protected void setService(DDLRecordSetService service) {
		_service = service;
	}

	private DDLRecordSetService _service;
}