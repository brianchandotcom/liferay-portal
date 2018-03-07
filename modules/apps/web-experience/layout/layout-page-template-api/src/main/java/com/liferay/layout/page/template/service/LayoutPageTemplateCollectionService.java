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

package com.liferay.layout.page.template.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service interface for LayoutPageTemplateCollection. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateCollectionServiceUtil
 * @see com.liferay.layout.page.template.service.base.LayoutPageTemplateCollectionServiceBaseImpl
 * @see com.liferay.layout.page.template.service.impl.LayoutPageTemplateCollectionServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=layout", "json.web.service.context.path=LayoutPageTemplateCollection"}, service = LayoutPageTemplateCollectionService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface LayoutPageTemplateCollectionService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutPageTemplateCollectionServiceUtil} to access the layout page template collection remote service. Add custom service methods to {@link com.liferay.layout.page.template.service.impl.LayoutPageTemplateCollectionServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public LayoutPageTemplateCollection addLayoutPageTemplateCollection(
		long groupId, java.lang.String name, java.lang.String description,
		ServiceContext serviceContext) throws PortalException;

	public LayoutPageTemplateCollection deleteLayoutPageTemplateCollection(
		long layoutPageTemplateCollectionId) throws PortalException;

	public void deleteLayoutPageTemplateCollections(
		long[] layoutPageTemplateCollectionIds) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutPageTemplateCollection fetchLayoutPageTemplateCollection(
		long layoutPageTemplateCollectionId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutPageTemplateCollection> getBasicLayoutPageTemplateCollections(
		long groupId, int start, int end,
		OrderByComparator<LayoutPageTemplateCollection> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutPageTemplateCollection> getLayoutPageTemplateCollections(
		long groupId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutPageTemplateCollection> getLayoutPageTemplateCollections(
		long groupId, int type) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutPageTemplateCollection> getLayoutPageTemplateCollections(
		long groupId, int start, int end) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutPageTemplateCollection> getLayoutPageTemplateCollections(
		long groupId, int start, int end,
		OrderByComparator<LayoutPageTemplateCollection> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutPageTemplateCollection> getLayoutPageTemplateCollections(
		long groupId, java.lang.String name, int start, int end,
		OrderByComparator<LayoutPageTemplateCollection> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getLayoutPageTemplateCollectionsCount(long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getLayoutPageTemplateCollectionsCount(long groupId,
		java.lang.String name);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	public LayoutPageTemplateCollection updateLayoutPageTemplateCollection(
		long layoutPageTemplateCollectionId, java.lang.String name,
		java.lang.String description) throws PortalException;
}