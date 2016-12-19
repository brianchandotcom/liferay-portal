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

package com.liferay.knowledge.base.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.knowledge.base.model.KBComment;

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
 * Provides the remote service interface for KBComment. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see KBCommentServiceUtil
 * @see com.liferay.knowledge.base.service.base.KBCommentServiceBaseImpl
 * @see com.liferay.knowledge.base.service.impl.KBCommentServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=kb", "json.web.service.context.path=KBComment"}, service = KBCommentService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface KBCommentService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link KBCommentServiceUtil} to access the kb comment remote service. Add custom service methods to {@link com.liferay.knowledge.base.service.impl.KBCommentServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public KBComment deleteKBComment(KBComment kbComment)
		throws PortalException;

	public KBComment deleteKBComment(long kbCommentId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public KBComment getKBComment(long kbCommentId) throws PortalException;

	public KBComment updateKBComment(long kbCommentId, long classNameId,
		long classPK, java.lang.String content, ServiceContext serviceContext)
		throws PortalException;

	public KBComment updateKBComment(long kbCommentId, long classNameId,
		long classPK, java.lang.String content, int status,
		ServiceContext serviceContext) throws PortalException;

	public KBComment updateStatus(long kbCommentId, int status,
		ServiceContext serviceContext) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getKBCommentsCount(long groupId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getKBCommentsCount(long groupId, int status)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getKBCommentsCount(long groupId, java.lang.String className,
		long classPK) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getKBCommentsCount(long groupId, java.lang.String className,
		long classPK, int status) throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<KBComment> getKBComments(long groupId, int start, int end,
		OrderByComparator<KBComment> obc) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<KBComment> getKBComments(long groupId, int status, int start,
		int end) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<KBComment> getKBComments(long groupId, int status, int start,
		int end, OrderByComparator<KBComment> obc) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<KBComment> getKBComments(long groupId,
		java.lang.String className, long classPK, int start, int end,
		OrderByComparator<KBComment> obc) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<KBComment> getKBComments(long groupId,
		java.lang.String className, long classPK, int status, int start, int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<KBComment> getKBComments(long groupId,
		java.lang.String className, long classPK, int status, int start,
		int end, OrderByComparator<KBComment> obc) throws PortalException;
}