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
																 String feedId, boolean autoFeedId, String name,
																 String description, String ddmStructureKey,
																 String ddmTemplateKey,
																 String ddmRendererTemplateKey, int delta,
																 String orderByCol, String orderByType,
																 String targetLayoutFriendlyUrl,
																 String targetPortletId, String contentField,
																 String feedType, double feedVersion,
																 com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	public void deleteFeed(long feedId) throws PortalException;

	/**
	* @deprecated As of 6.2.0, replaced by {@link #deleteFeed(long, String)}
	*/
	@Deprecated
	@JSONWebService(mode = JSONWebServiceMode.IGNORE)
	public void deleteFeed(long groupId, long feedId) throws PortalException;

	public void deleteFeed(long groupId, String feedId)
		throws PortalException;

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public String getBeanIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.journal.model.JournalFeed getFeed(long feedId)
		throws PortalException;

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getFeed(long, String)}
	*/
	@Deprecated
	@JSONWebService(mode = JSONWebServiceMode.IGNORE)
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.journal.model.JournalFeed getFeed(long groupId,
																 long feedId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.journal.model.JournalFeed getFeed(long groupId,
																 String feedId) throws PortalException;

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(String beanIdentifier);

	public com.liferay.journal.model.JournalFeed updateFeed(
		long groupId, String feedId, String name,
		String description, String ddmStructureKey,
		String ddmTemplateKey,
		String ddmRendererTemplateKey, int delta,
		String orderByCol, String orderByType,
		String targetLayoutFriendlyUrl,
		String targetPortletId, String contentField,
		String feedType, double feedVersion,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;
}