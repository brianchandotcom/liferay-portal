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

package com.liferay.asset.kernel.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Provides the remote service interface for AssetEntry. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryServiceUtil
 * @see com.liferay.portlet.asset.service.base.AssetEntryServiceBaseImpl
 * @see com.liferay.portlet.asset.service.impl.AssetEntryServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface AssetEntryService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetEntryServiceUtil} to access the asset entry remote service. Add custom service methods to {@link com.liferay.portlet.asset.service.impl.AssetEntryServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AssetEntry fetchEntry(long entryId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AssetEntry getEntry(long entryId) throws PortalException;

	public AssetEntry incrementViewCounter(java.lang.String className,
		long classPK) throws PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link #updateEntry(long, Date,
	Date, String, long, String, long, long[], String[], boolean,
	boolean, Date, Date, Date, Date, String, String, String,
	String, String, String, int, int, Double)}
	*/
	@java.lang.Deprecated
	public AssetEntry updateEntry(long groupId, Date createDate,
		Date modifiedDate, java.lang.String className, long classPK,
		java.lang.String classUuid, long classTypeId, long[] categoryIds,
		java.lang.String[] tagNames, boolean listable, boolean visible,
		Date startDate, Date endDate, Date expirationDate,
		java.lang.String mimeType, java.lang.String title,
		java.lang.String description, java.lang.String summary,
		java.lang.String url, java.lang.String layoutUuid, int height,
		int width, java.lang.Double priority) throws PortalException;

	public AssetEntry updateEntry(long groupId, Date createDate,
		Date modifiedDate, java.lang.String className, long classPK,
		java.lang.String classUuid, long classTypeId, long[] categoryIds,
		java.lang.String[] tagNames, boolean listable, boolean visible,
		Date startDate, Date endDate, Date publishDate, Date expirationDate,
		java.lang.String mimeType, java.lang.String title,
		java.lang.String description, java.lang.String summary,
		java.lang.String url, java.lang.String layoutUuid, int height,
		int width, java.lang.Double priority) throws PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link #updateEntry(long, Date,
	Date, String, long, String, long, long[], String[], boolean,
	boolean, Date, Date, Date, Date, String, String, String,
	String, String, String, int, int, Double)}
	*/
	@java.lang.Deprecated
	public AssetEntry updateEntry(long groupId, Date createDate,
		Date modifiedDate, java.lang.String className, long classPK,
		java.lang.String classUuid, long classTypeId, long[] categoryIds,
		java.lang.String[] tagNames, boolean visible, Date startDate,
		Date endDate, Date expirationDate, java.lang.String mimeType,
		java.lang.String title, java.lang.String description,
		java.lang.String summary, java.lang.String url,
		java.lang.String layoutUuid, int height, int width,
		java.lang.Integer priority, boolean sync) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCompanyEntriesCount(long companyId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getEntriesCount(AssetEntryQuery entryQuery)
		throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetEntry> getCompanyEntries(long companyId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetEntry> getEntries(AssetEntryQuery entryQuery)
		throws PortalException;

	public void incrementViewCounter(AssetEntry assetEntry)
		throws PortalException;
}