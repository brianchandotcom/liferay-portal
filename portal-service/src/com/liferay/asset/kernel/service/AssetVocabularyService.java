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

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.AssetVocabularyDisplay;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the remote service interface for AssetVocabulary. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see AssetVocabularyServiceUtil
 * @see com.liferay.portlet.asset.service.base.AssetVocabularyServiceBaseImpl
 * @see com.liferay.portlet.asset.service.impl.AssetVocabularyServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface AssetVocabularyService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetVocabularyServiceUtil} to access the asset vocabulary remote service. Add custom service methods to {@link com.liferay.portlet.asset.service.impl.AssetVocabularyServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public AssetVocabulary addVocabulary(long groupId, java.lang.String title,
		ServiceContext serviceContext) throws PortalException;

	public AssetVocabulary addVocabulary(long groupId, java.lang.String title,
		Map<Locale, java.lang.String> titleMap,
		Map<Locale, java.lang.String> descriptionMap,
		java.lang.String settings, ServiceContext serviceContext)
		throws PortalException;

	public List<AssetVocabulary> deleteVocabularies(long[] vocabularyIds,
		ServiceContext serviceContext) throws PortalException;

	public void deleteVocabulary(long vocabularyId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AssetVocabulary fetchVocabulary(long vocabularyId)
		throws PortalException;

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetVocabulary> getCompanyVocabularies(long companyId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetVocabulary> getGroupVocabularies(long groupId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetVocabulary> getGroupVocabularies(long groupId,
		boolean createDefaultVocabulary) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetVocabulary> getGroupVocabularies(long groupId,
		boolean createDefaultVocabulary, int start, int end,
		OrderByComparator<AssetVocabulary> obc) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetVocabulary> getGroupVocabularies(long groupId,
		java.lang.String name, int start, int end,
		OrderByComparator<AssetVocabulary> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetVocabulary> getGroupVocabularies(long groupId, int start,
		int end, OrderByComparator<AssetVocabulary> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetVocabulary> getGroupVocabularies(long[] groupIds);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupVocabulariesCount(long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupVocabulariesCount(long groupId, java.lang.String name);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupVocabulariesCount(long[] groupIds);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AssetVocabularyDisplay getGroupVocabulariesDisplay(long groupId,
		java.lang.String name, int start, int end,
		boolean addDefaultVocabulary, OrderByComparator<AssetVocabulary> obc)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AssetVocabularyDisplay getGroupVocabulariesDisplay(long groupId,
		java.lang.String name, int start, int end,
		OrderByComparator<AssetVocabulary> obc) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetVocabulary> getGroupsVocabularies(long[] groupIds);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetVocabulary> getGroupsVocabularies(long[] groupIds,
		java.lang.String className);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetVocabulary> getGroupsVocabularies(long[] groupIds,
		java.lang.String className, long classTypePK);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	AssetUtil#filterVocabularyIds(PermissionChecker, long[])}
	*/
	@java.lang.Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetVocabulary> getVocabularies(long[] vocabularyIds)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AssetVocabulary getVocabulary(long vocabularyId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AssetVocabularyDisplay searchVocabulariesDisplay(long groupId,
		java.lang.String title, boolean addDefaultVocabulary, int start, int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AssetVocabularyDisplay searchVocabulariesDisplay(long groupId,
		java.lang.String title, boolean addDefaultVocabulary, int start,
		int end, Sort sort) throws PortalException;

	public AssetVocabulary updateVocabulary(long vocabularyId,
		java.lang.String title, Map<Locale, java.lang.String> titleMap,
		Map<Locale, java.lang.String> descriptionMap,
		java.lang.String settings, ServiceContext serviceContext)
		throws PortalException;
}