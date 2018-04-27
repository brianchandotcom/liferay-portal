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

package com.liferay.commerce.product.type.virtual.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting;

import com.liferay.exportimport.kernel.lar.PortletDataContext;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the local service interface for CPDefinitionVirtualSetting. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Marco Leo
 * @see CPDefinitionVirtualSettingLocalServiceUtil
 * @see com.liferay.commerce.product.type.virtual.service.base.CPDefinitionVirtualSettingLocalServiceBaseImpl
 * @see com.liferay.commerce.product.type.virtual.service.impl.CPDefinitionVirtualSettingLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CPDefinitionVirtualSettingLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPDefinitionVirtualSettingLocalServiceUtil} to access the cp definition virtual setting local service. Add custom service methods to {@link com.liferay.commerce.product.type.virtual.service.impl.CPDefinitionVirtualSettingLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the cp definition virtual setting to the database. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionVirtualSetting the cp definition virtual setting
	* @return the cp definition virtual setting that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CPDefinitionVirtualSetting addCPDefinitionVirtualSetting(
		CPDefinitionVirtualSetting cpDefinitionVirtualSetting);

	public CPDefinitionVirtualSetting addCPDefinitionVirtualSetting(
		long cpDefinitionId, long fileEntryId, String url,
		String activationStatus, long duration, int maxUsages,
		boolean useSample, long sampleFileEntryId, String sampleUrl,
		boolean termsOfUseRequired, Map<Locale, String> termsOfUseContentMap,
		long termsOfUseJournalArticleResourcePrimKey,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Creates a new cp definition virtual setting with the primary key. Does not add the cp definition virtual setting to the database.
	*
	* @param CPDefinitionVirtualSettingId the primary key for the new cp definition virtual setting
	* @return the new cp definition virtual setting
	*/
	@Transactional(enabled = false)
	public CPDefinitionVirtualSetting createCPDefinitionVirtualSetting(
		long CPDefinitionVirtualSettingId);

	/**
	* Deletes the cp definition virtual setting from the database. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionVirtualSetting the cp definition virtual setting
	* @return the cp definition virtual setting that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public CPDefinitionVirtualSetting deleteCPDefinitionVirtualSetting(
		CPDefinitionVirtualSetting cpDefinitionVirtualSetting);

	/**
	* Deletes the cp definition virtual setting with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPDefinitionVirtualSettingId the primary key of the cp definition virtual setting
	* @return the cp definition virtual setting that was removed
	* @throws PortalException if a cp definition virtual setting with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public CPDefinitionVirtualSetting deleteCPDefinitionVirtualSetting(
		long CPDefinitionVirtualSettingId) throws PortalException;

	public CPDefinitionVirtualSetting deleteCPDefinitionVirtualSettingByCPDefinitionId(
		long cpDefinitionId);

	/**
	* @throws PortalException
	*/
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	public DynamicQuery dynamicQuery();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.type.virtual.model.impl.CPDefinitionVirtualSettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end);

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.type.virtual.model.impl.CPDefinitionVirtualSettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDefinitionVirtualSetting fetchCPDefinitionVirtualSetting(
		long CPDefinitionVirtualSettingId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDefinitionVirtualSetting fetchCPDefinitionVirtualSettingByCPDefinitionId(
		long cpDefinitionId);

	/**
	* Returns the cp definition virtual setting matching the UUID and group.
	*
	* @param uuid the cp definition virtual setting's UUID
	* @param groupId the primary key of the group
	* @return the matching cp definition virtual setting, or <code>null</code> if a matching cp definition virtual setting could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDefinitionVirtualSetting fetchCPDefinitionVirtualSettingByUuidAndGroupId(
		String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* Returns the cp definition virtual setting with the primary key.
	*
	* @param CPDefinitionVirtualSettingId the primary key of the cp definition virtual setting
	* @return the cp definition virtual setting
	* @throws PortalException if a cp definition virtual setting with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDefinitionVirtualSetting getCPDefinitionVirtualSetting(
		long CPDefinitionVirtualSettingId) throws PortalException;

	/**
	* Returns the cp definition virtual setting matching the UUID and group.
	*
	* @param uuid the cp definition virtual setting's UUID
	* @param groupId the primary key of the group
	* @return the matching cp definition virtual setting
	* @throws PortalException if a matching cp definition virtual setting could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDefinitionVirtualSetting getCPDefinitionVirtualSettingByUuidAndGroupId(
		String uuid, long groupId) throws PortalException;

	/**
	* Returns a range of all the cp definition virtual settings.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.type.virtual.model.impl.CPDefinitionVirtualSettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition virtual settings
	* @param end the upper bound of the range of cp definition virtual settings (not inclusive)
	* @return the range of cp definition virtual settings
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDefinitionVirtualSetting> getCPDefinitionVirtualSettings(
		int start, int end);

	/**
	* Returns all the cp definition virtual settings matching the UUID and company.
	*
	* @param uuid the UUID of the cp definition virtual settings
	* @param companyId the primary key of the company
	* @return the matching cp definition virtual settings, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDefinitionVirtualSetting> getCPDefinitionVirtualSettingsByUuidAndCompanyId(
		String uuid, long companyId);

	/**
	* Returns a range of cp definition virtual settings matching the UUID and company.
	*
	* @param uuid the UUID of the cp definition virtual settings
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of cp definition virtual settings
	* @param end the upper bound of the range of cp definition virtual settings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching cp definition virtual settings, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDefinitionVirtualSetting> getCPDefinitionVirtualSettingsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPDefinitionVirtualSetting> orderByComparator);

	/**
	* Returns the number of cp definition virtual settings.
	*
	* @return the number of cp definition virtual settings
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCPDefinitionVirtualSettingsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	* Updates the cp definition virtual setting in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionVirtualSetting the cp definition virtual setting
	* @return the cp definition virtual setting that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CPDefinitionVirtualSetting updateCPDefinitionVirtualSetting(
		CPDefinitionVirtualSetting cpDefinitionVirtualSetting);

	public CPDefinitionVirtualSetting updateCPDefinitionVirtualSetting(
		long cpDefinitionVirtualSettingId, long fileEntryId, String url,
		String activationStatus, long duration, int maxUsages,
		boolean useSample, long sampleFileEntryId, String sampleUrl,
		boolean termsOfUseRequired, Map<Locale, String> termsOfUseContentMap,
		long termsOfUseJournalArticleResourcePrimKey,
		ServiceContext serviceContext) throws PortalException;
}