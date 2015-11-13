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

package com.liferay.journal.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceMode;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.service.BaseService;

/**
 * Provides the remote service interface for JournalFeed. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see JournalFeedServiceUtil
 * @see com.liferay.journal.service.base.JournalFeedServiceBaseImpl
 * @see com.liferay.journal.service.impl.JournalFeedServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=journal", "json.web.service.context.path=JournalFeed"}, service = JournalFeedService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface JournalFeedService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link JournalFeedServiceUtil} to access the journal feed remote service. Add custom service methods to {@link com.liferay.journal.service.impl.JournalFeedServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public com.liferay.journal.model.JournalFeed addFeed(long groupId,
		java.lang.String feedId, boolean autoFeedId, java.lang.String name,
		java.lang.String description, java.lang.String ddmStructureKey,
		java.lang.String ddmTemplateKey,
		java.lang.String ddmRendererTemplateKey, int delta,
		java.lang.String orderByCol, java.lang.String orderByType,
		java.lang.String targetLayoutFriendlyUrl,
		java.lang.String targetPortletId, java.lang.String contentField,
		java.lang.String feedType, double feedVersion,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	public void deleteFeed(long feedId) throws PortalException;

	/**
	* @deprecated As of 6.2.0, replaced by {@link #deleteFeed(long, String)}
	*/
	@java.lang.Deprecated
	@JSONWebService(mode = JSONWebServiceMode.IGNORE)
	public void deleteFeed(long groupId, long feedId) throws PortalException;

	public void deleteFeed(long groupId, java.lang.String feedId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.journal.model.JournalFeed getFeed(long feedId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.journal.model.JournalFeed getFeed(long groupId,
		java.lang.String feedId) throws PortalException;

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getFeed(long, String)}
	*/
	@java.lang.Deprecated
	@JSONWebService(mode = JSONWebServiceMode.IGNORE)
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.journal.model.JournalFeed getFeed(long groupId,
		long feedId) throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	public com.liferay.journal.model.JournalFeed updateFeed(long groupId,
		java.lang.String feedId, java.lang.String name,
		java.lang.String description, java.lang.String ddmStructureKey,
		java.lang.String ddmTemplateKey,
		java.lang.String ddmRendererTemplateKey, int delta,
		java.lang.String orderByCol, java.lang.String orderByType,
		java.lang.String targetLayoutFriendlyUrl,
		java.lang.String targetPortletId, java.lang.String contentField,
		java.lang.String feedType, double feedVersion,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;
}