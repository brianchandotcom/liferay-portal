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

package com.liferay.message.boards.kernel.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.message.boards.kernel.model.MBCategory;

import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service interface for MBCategory. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see MBCategoryServiceUtil
 * @see com.liferay.portlet.messageboards.service.base.MBCategoryServiceBaseImpl
 * @see com.liferay.portlet.messageboards.service.impl.MBCategoryServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface MBCategoryService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MBCategoryServiceUtil} to access the message boards category remote service. Add custom service methods to {@link com.liferay.portlet.messageboards.service.impl.MBCategoryServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public MBCategory addCategory(long parentCategoryId, java.lang.String name,
		java.lang.String description, java.lang.String displayStyle,
		java.lang.String emailAddress, java.lang.String inProtocol,
		java.lang.String inServerName, int inServerPort, boolean inUseSSL,
		java.lang.String inUserName, java.lang.String inPassword,
		int inReadInterval, java.lang.String outEmailAddress,
		boolean outCustom, java.lang.String outServerName, int outServerPort,
		boolean outUseSSL, java.lang.String outUserName,
		java.lang.String outPassword, boolean mailingListActive,
		boolean allowAnonymousEmail, ServiceContext serviceContext)
		throws PortalException;

	public MBCategory addCategory(long userId, long parentCategoryId,
		java.lang.String name, java.lang.String description,
		ServiceContext serviceContext) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MBCategory getCategory(long categoryId) throws PortalException;

	public MBCategory moveCategory(long categoryId, long parentCategoryId,
		boolean mergeWithParentCategory) throws PortalException;

	public MBCategory moveCategoryFromTrash(long categoryId, long newCategoryId)
		throws PortalException;

	public MBCategory moveCategoryToTrash(long categoryId)
		throws PortalException;

	public MBCategory updateCategory(long categoryId, long parentCategoryId,
		java.lang.String name, java.lang.String description,
		java.lang.String displayStyle, java.lang.String emailAddress,
		java.lang.String inProtocol, java.lang.String inServerName,
		int inServerPort, boolean inUseSSL, java.lang.String inUserName,
		java.lang.String inPassword, int inReadInterval,
		java.lang.String outEmailAddress, boolean outCustom,
		java.lang.String outServerName, int outServerPort, boolean outUseSSL,
		java.lang.String outUserName, java.lang.String outPassword,
		boolean mailingListActive, boolean allowAnonymousEmail,
		boolean mergeWithParentCategory, ServiceContext serviceContext)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCategoriesAndThreadsCount(long groupId, long categoryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCategoriesAndThreadsCount(long groupId, long categoryId,
		QueryDefinition<?> queryDefinition);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCategoriesAndThreadsCount(long groupId, long categoryId,
		int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCategoriesCount(long groupId, long excludedCategoryId,
		long parentCategoryId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCategoriesCount(long groupId, long parentCategoryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCategoriesCount(long groupId, long parentCategoryId,
		int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCategoriesCount(long groupId, long[] excludedCategoryIds,
		long[] parentCategoryIds, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCategoriesCount(long groupId, long[] parentCategoryIds);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCategoriesCount(long groupId, long[] parentCategoryIds,
		int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getSubscribedCategoriesCount(long groupId, long userId);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBCategory> getCategories(long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBCategory> getCategories(long groupId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBCategory> getCategories(long groupId,
		long excludedCategoryId, long parentCategoryId, int status, int start,
		int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBCategory> getCategories(long groupId, long parentCategoryId,
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBCategory> getCategories(long groupId, long parentCategoryId,
		int status, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBCategory> getCategories(long groupId,
		long[] excludedCategoryIds, long[] parentCategoryIds, int status,
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBCategory> getCategories(long groupId,
		long[] parentCategoryIds, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBCategory> getCategories(long groupId,
		long[] parentCategoryIds, int status, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<java.lang.Object> getCategoriesAndThreads(long groupId,
		long categoryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<java.lang.Object> getCategoriesAndThreads(long groupId,
		long categoryId, QueryDefinition<?> queryDefinition);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<java.lang.Object> getCategoriesAndThreads(long groupId,
		long categoryId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<java.lang.Object> getCategoriesAndThreads(long groupId,
		long categoryId, int status, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<java.lang.Object> getCategoriesAndThreads(long groupId,
		long categoryId, int status, int start, int end,
		OrderByComparator<?> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<java.lang.Long> getSubcategoryIds(
		List<java.lang.Long> categoryIds, long groupId, long categoryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBCategory> getSubscribedCategories(long groupId, long userId,
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long[] getCategoryIds(long groupId, long categoryId);

	public void deleteCategory(long categoryId, boolean includeTrashedEntries)
		throws PortalException;

	public void deleteCategory(long groupId, long categoryId)
		throws PortalException;

	public void restoreCategoryFromTrash(long categoryId)
		throws PortalException;

	public void subscribeCategory(long groupId, long categoryId)
		throws PortalException;

	public void unsubscribeCategory(long groupId, long categoryId)
		throws PortalException;
}