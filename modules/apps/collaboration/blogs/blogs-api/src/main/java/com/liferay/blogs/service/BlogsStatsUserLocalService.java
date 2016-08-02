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

package com.liferay.blogs.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.blogs.kernel.model.BlogsStatsUser;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Date;
import java.util.List;

/**
 * Provides the local service interface for BlogsStatsUser. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see BlogsStatsUserLocalServiceUtil
 * @see com.liferay.blogs.service.base.BlogsStatsUserLocalServiceBaseImpl
 * @see com.liferay.blogs.service.impl.BlogsStatsUserLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface BlogsStatsUserLocalService extends BaseLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link BlogsStatsUserLocalServiceUtil} to access the blogs stats user local service. Add custom service methods to {@link com.liferay.blogs.service.impl.BlogsStatsUserLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BlogsStatsUser getStatsUser(long groupId, long userId)
		throws PortalException;

	public BlogsStatsUser updateStatsUser(long groupId, long userId,
		int ratingsTotalEntries, double ratingsTotalScore,
		double ratingsAverageScore) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCompanyStatsUsersCount(long companyId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupStatsUsersCount(long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getOrganizationStatsUsersCount(long organizationId);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogsStatsUser> getCompanyStatsUsers(long companyId, int start,
		int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogsStatsUser> getCompanyStatsUsers(long companyId, int start,
		int end, OrderByComparator<BlogsStatsUser> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogsStatsUser> getGroupStatsUsers(long groupId, int start,
		int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogsStatsUser> getGroupStatsUsers(long groupId, int start,
		int end, OrderByComparator<BlogsStatsUser> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogsStatsUser> getGroupsStatsUsers(long companyId,
		long groupId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogsStatsUser> getOrganizationStatsUsers(long organizationId,
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogsStatsUser> getOrganizationStatsUsers(long organizationId,
		int start, int end, OrderByComparator<BlogsStatsUser> obc);

	public void deleteStatsUser(BlogsStatsUser statsUsers);

	public void deleteStatsUser(long statsUserId) throws PortalException;

	public void deleteStatsUserByGroupId(long groupId);

	public void deleteStatsUserByUserId(long userId);

	public void updateStatsUser(long groupId, long userId)
		throws PortalException;

	public void updateStatsUser(long groupId, long userId, Date displayDate)
		throws PortalException;
}