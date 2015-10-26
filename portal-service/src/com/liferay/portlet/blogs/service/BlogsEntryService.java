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

package com.liferay.portlet.blogs.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.service.BaseService;

/**
 * Provides the remote service interface for BlogsEntry. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see BlogsEntryServiceUtil
 * @see com.liferay.portlet.blogs.service.base.BlogsEntryServiceBaseImpl
 * @see com.liferay.portlet.blogs.service.impl.BlogsEntryServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface BlogsEntryService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link BlogsEntryServiceUtil} to access the blogs entry remote service. Add custom service methods to {@link com.liferay.portlet.blogs.service.impl.BlogsEntryServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* @deprecated As of 7.0.0, replaced by {@link #addEntry(String, String,
	String, String, int, int, int, int, int, boolean, boolean,
	String[], String, ImageSelector, ImageSelector,
	ServiceContext)}
	*/
	@java.lang.Deprecated
	public com.liferay.portlet.blogs.model.BlogsEntry addEntry(
		java.lang.String title, java.lang.String description,
		java.lang.String content, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		boolean allowPingbacks, boolean allowTrackbacks,
		java.lang.String[] trackbacks, boolean smallImage,
		java.lang.String smallImageURL, java.lang.String smallImageFileName,
		java.io.InputStream smallImageInputStream,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	public com.liferay.portlet.blogs.model.BlogsEntry addEntry(
		java.lang.String title, java.lang.String subtitle,
		java.lang.String description, java.lang.String content,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, boolean allowPingbacks,
		boolean allowTrackbacks, java.lang.String[] trackbacks,
		java.lang.String coverImageCaption,
		com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector coverImageImageSelector,
		com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector smallImageImageSelector,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	public void deleteEntry(long entryId) throws PortalException;

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getCompanyEntries(
		long companyId, java.util.Date displayDate, int status, int max)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String getCompanyEntriesRSS(long companyId,
		java.util.Date displayDate, int status, int max, java.lang.String type,
		double version, java.lang.String displayStyle,
		java.lang.String feedURL, java.lang.String entryURL,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.blogs.model.BlogsEntry getEntry(long entryId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.blogs.model.BlogsEntry getEntry(long groupId,
		java.lang.String urlTitle) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupEntries(
		long groupId, java.util.Date displayDate, int status, int max);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupEntries(
		long groupId, java.util.Date displayDate, int status, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupEntries(
		long groupId, int status, int max);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupEntries(
		long groupId, int status, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupEntries(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.blogs.model.BlogsEntry> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupEntriesCount(long groupId, java.util.Date displayDate,
		int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupEntriesCount(long groupId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String getGroupEntriesRSS(long groupId,
		java.util.Date displayDate, int status, int max, java.lang.String type,
		double version, java.lang.String displayStyle,
		java.lang.String feedURL, java.lang.String entryURL,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupUserEntries(
		long groupId, long userId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.blogs.model.BlogsEntry> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupUserEntriesCount(long groupId, long userId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupsEntries(
		long companyId, long groupId, java.util.Date displayDate, int status,
		int max) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getOrganizationEntries(
		long organizationId, java.util.Date displayDate, int status, int max)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String getOrganizationEntriesRSS(long organizationId,
		java.util.Date displayDate, int status, int max, java.lang.String type,
		double version, java.lang.String displayStyle,
		java.lang.String feedURL, java.lang.String entryURL,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws PortalException;

	public com.liferay.portlet.blogs.model.BlogsEntry moveEntryToTrash(
		long entryId) throws PortalException;

	public void restoreEntryFromTrash(long entryId) throws PortalException;

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier);

	public void subscribe(long groupId) throws PortalException;

	public void unsubscribe(long groupId) throws PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link #updateEntry(long, String,
	String, String, String, int, int, int, int, int, boolean,
	boolean, String[], String, ImageSelector, ImageSelector,
	ServiceContext)}
	*/
	@java.lang.Deprecated
	public com.liferay.portlet.blogs.model.BlogsEntry updateEntry(
		long entryId, java.lang.String title, java.lang.String description,
		java.lang.String content, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		boolean allowPingbacks, boolean allowTrackbacks,
		java.lang.String[] trackbacks, boolean smallImage,
		java.lang.String smallImageURL, java.lang.String smallImageFileName,
		java.io.InputStream smallImageInputStream,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	public com.liferay.portlet.blogs.model.BlogsEntry updateEntry(
		long entryId, java.lang.String title, java.lang.String subtitle,
		java.lang.String description, java.lang.String content,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, boolean allowPingbacks,
		boolean allowTrackbacks, java.lang.String[] trackbacks,
		java.lang.String coverImageCaption,
		com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector coverImageImageSelector,
		com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector smallImageImageSelector,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;
}