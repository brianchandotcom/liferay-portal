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

import com.liferay.fragment.model.FragmentEntry;

import com.liferay.layout.page.template.model.LayoutPageTemplate;

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
import java.util.Map;

/**
 * Provides the remote service interface for LayoutPageTemplate. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateServiceUtil
 * @see com.liferay.layout.page.template.service.base.LayoutPageTemplateServiceBaseImpl
 * @see com.liferay.layout.page.template.service.impl.LayoutPageTemplateServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=layout", "json.web.service.context.path=LayoutPageTemplate"}, service = LayoutPageTemplateService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface LayoutPageTemplateService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutPageTemplateServiceUtil} to access the layout page template remote service. Add custom service methods to {@link com.liferay.layout.page.template.service.impl.LayoutPageTemplateServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public LayoutPageTemplate addLayoutPageTemplate(long groupId,
		long layoutPageTemplateFolderId, java.lang.String name,
		Map<java.lang.Integer, FragmentEntry> layoutPageTemplateFragments,
		ServiceContext serviceContext) throws PortalException;

	public LayoutPageTemplate deleteLayoutPageTemplate(long pageTemplateId)
		throws PortalException;

	public List<LayoutPageTemplate> deletePageTemplates(long[] pageTemplatesIds)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutPageTemplate fetchLayoutPageTemplate(long pageTemplateId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutPageTemplate> fetchPageTemplates(
		long layoutPageTemplateFolderId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getLayoutPageTemplateFoldersCount(long groupId,
		long layoutPageTemplateFolderId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getLayoutPageTemplateFoldersCount(long groupId,
		long layoutPageTemplateFolderId, java.lang.String name);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutPageTemplate> getPageTemplates(long groupId,
		long layoutPageTemplateFolderId, int start, int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutPageTemplate> getPageTemplates(long groupId,
		long layoutPageTemplateFolderId, int start, int end,
		OrderByComparator<LayoutPageTemplate> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutPageTemplate> getPageTemplates(long groupId,
		long layoutPageTemplateFolderId, java.lang.String name, int start,
		int end, OrderByComparator<LayoutPageTemplate> orderByComparator);

	public LayoutPageTemplate updateLayoutPageTemplate(long pageTemplateId,
		java.lang.String name,
		Map<java.lang.Integer, FragmentEntry> layoutPageTemplateFragments,
		ServiceContext serviceContext) throws PortalException;
}