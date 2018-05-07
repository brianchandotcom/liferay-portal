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

package com.liferay.commerce.product.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CPFriendlyURLEntryLocalService}.
 *
 * @author Marco Leo
 * @see CPFriendlyURLEntryLocalService
 * @generated
 */
@ProviderType
public class CPFriendlyURLEntryLocalServiceWrapper
	implements CPFriendlyURLEntryLocalService,
		ServiceWrapper<CPFriendlyURLEntryLocalService> {
	public CPFriendlyURLEntryLocalServiceWrapper(
		CPFriendlyURLEntryLocalService cpFriendlyURLEntryLocalService) {
		_cpFriendlyURLEntryLocalService = cpFriendlyURLEntryLocalService;
	}

	@Override
	public void addCPFriendlyURLEntries(long groupId, long companyId,
		Class<?> clazz, long classPK,
		java.util.Map<java.util.Locale, String> urlTitleMap)
		throws com.liferay.portal.kernel.exception.PortalException {
		_cpFriendlyURLEntryLocalService.addCPFriendlyURLEntries(groupId,
			companyId, clazz, classPK, urlTitleMap);
	}

	/**
	* Adds the cp friendly url entry to the database. Also notifies the appropriate model listeners.
	*
	* @param cpFriendlyURLEntry the cp friendly url entry
	* @return the cp friendly url entry that was added
	*/
	@Override
	public com.liferay.commerce.product.model.CPFriendlyURLEntry addCPFriendlyURLEntry(
		com.liferay.commerce.product.model.CPFriendlyURLEntry cpFriendlyURLEntry) {
		return _cpFriendlyURLEntryLocalService.addCPFriendlyURLEntry(cpFriendlyURLEntry);
	}

	@Override
	public String buildUrlTitle(long groupId, long classNameId, long classPK,
		String languageId, String title) {
		return _cpFriendlyURLEntryLocalService.buildUrlTitle(groupId,
			classNameId, classPK, languageId, title);
	}

	/**
	* Creates a new cp friendly url entry with the primary key. Does not add the cp friendly url entry to the database.
	*
	* @param CPFriendlyURLEntryId the primary key for the new cp friendly url entry
	* @return the new cp friendly url entry
	*/
	@Override
	public com.liferay.commerce.product.model.CPFriendlyURLEntry createCPFriendlyURLEntry(
		long CPFriendlyURLEntryId) {
		return _cpFriendlyURLEntryLocalService.createCPFriendlyURLEntry(CPFriendlyURLEntryId);
	}

	@Override
	public void deleteCPFriendlyURLEntries(long groupId, Class<?> clazz,
		long classPK) {
		_cpFriendlyURLEntryLocalService.deleteCPFriendlyURLEntries(groupId,
			clazz, classPK);
	}

	/**
	* Deletes the cp friendly url entry from the database. Also notifies the appropriate model listeners.
	*
	* @param cpFriendlyURLEntry the cp friendly url entry
	* @return the cp friendly url entry that was removed
	*/
	@Override
	public com.liferay.commerce.product.model.CPFriendlyURLEntry deleteCPFriendlyURLEntry(
		com.liferay.commerce.product.model.CPFriendlyURLEntry cpFriendlyURLEntry) {
		return _cpFriendlyURLEntryLocalService.deleteCPFriendlyURLEntry(cpFriendlyURLEntry);
	}

	/**
	* Deletes the cp friendly url entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPFriendlyURLEntryId the primary key of the cp friendly url entry
	* @return the cp friendly url entry that was removed
	* @throws PortalException if a cp friendly url entry with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPFriendlyURLEntry deleteCPFriendlyURLEntry(
		long CPFriendlyURLEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpFriendlyURLEntryLocalService.deleteCPFriendlyURLEntry(CPFriendlyURLEntryId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpFriendlyURLEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _cpFriendlyURLEntryLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _cpFriendlyURLEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPFriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _cpFriendlyURLEntryLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPFriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _cpFriendlyURLEntryLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _cpFriendlyURLEntryLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _cpFriendlyURLEntryLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.product.model.CPFriendlyURLEntry fetchCPFriendlyURLEntry(
		long CPFriendlyURLEntryId) {
		return _cpFriendlyURLEntryLocalService.fetchCPFriendlyURLEntry(CPFriendlyURLEntryId);
	}

	@Override
	public com.liferay.commerce.product.model.CPFriendlyURLEntry fetchCPFriendlyURLEntry(
		long groupId, long classNameId, long classPK, String languageId,
		boolean main) {
		return _cpFriendlyURLEntryLocalService.fetchCPFriendlyURLEntry(groupId,
			classNameId, classPK, languageId, main);
	}

	@Override
	public com.liferay.commerce.product.model.CPFriendlyURLEntry fetchCPFriendlyURLEntry(
		long groupId, long classNameId, String languageId, String urlTitle) {
		return _cpFriendlyURLEntryLocalService.fetchCPFriendlyURLEntry(groupId,
			classNameId, languageId, urlTitle);
	}

	/**
	* Returns the cp friendly url entry matching the UUID and group.
	*
	* @param uuid the cp friendly url entry's UUID
	* @param groupId the primary key of the group
	* @return the matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPFriendlyURLEntry fetchCPFriendlyURLEntryByUuidAndGroupId(
		String uuid, long groupId) {
		return _cpFriendlyURLEntryLocalService.fetchCPFriendlyURLEntryByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _cpFriendlyURLEntryLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns a range of all the cp friendly url entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPFriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp friendly url entries
	* @param end the upper bound of the range of cp friendly url entries (not inclusive)
	* @return the range of cp friendly url entries
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPFriendlyURLEntry> getCPFriendlyURLEntries(
		int start, int end) {
		return _cpFriendlyURLEntryLocalService.getCPFriendlyURLEntries(start,
			end);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPFriendlyURLEntry> getCPFriendlyURLEntries(
		long groupId, long classNameId, long classPK) {
		return _cpFriendlyURLEntryLocalService.getCPFriendlyURLEntries(groupId,
			classNameId, classPK);
	}

	/**
	* Returns all the cp friendly url entries matching the UUID and company.
	*
	* @param uuid the UUID of the cp friendly url entries
	* @param companyId the primary key of the company
	* @return the matching cp friendly url entries, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPFriendlyURLEntry> getCPFriendlyURLEntriesByUuidAndCompanyId(
		String uuid, long companyId) {
		return _cpFriendlyURLEntryLocalService.getCPFriendlyURLEntriesByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of cp friendly url entries matching the UUID and company.
	*
	* @param uuid the UUID of the cp friendly url entries
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of cp friendly url entries
	* @param end the upper bound of the range of cp friendly url entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching cp friendly url entries, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPFriendlyURLEntry> getCPFriendlyURLEntriesByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPFriendlyURLEntry> orderByComparator) {
		return _cpFriendlyURLEntryLocalService.getCPFriendlyURLEntriesByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of cp friendly url entries.
	*
	* @return the number of cp friendly url entries
	*/
	@Override
	public int getCPFriendlyURLEntriesCount() {
		return _cpFriendlyURLEntryLocalService.getCPFriendlyURLEntriesCount();
	}

	/**
	* Returns the cp friendly url entry with the primary key.
	*
	* @param CPFriendlyURLEntryId the primary key of the cp friendly url entry
	* @return the cp friendly url entry
	* @throws PortalException if a cp friendly url entry with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPFriendlyURLEntry getCPFriendlyURLEntry(
		long CPFriendlyURLEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpFriendlyURLEntryLocalService.getCPFriendlyURLEntry(CPFriendlyURLEntryId);
	}

	/**
	* Returns the cp friendly url entry matching the UUID and group.
	*
	* @param uuid the cp friendly url entry's UUID
	* @param groupId the primary key of the group
	* @return the matching cp friendly url entry
	* @throws PortalException if a matching cp friendly url entry could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPFriendlyURLEntry getCPFriendlyURLEntryByUuidAndGroupId(
		String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpFriendlyURLEntryLocalService.getCPFriendlyURLEntryByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _cpFriendlyURLEntryLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _cpFriendlyURLEntryLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public java.util.Map<String, String> getLanguageIdToUrlTitleMap(
		long groupId, long classNameId, long classPK) {
		return _cpFriendlyURLEntryLocalService.getLanguageIdToUrlTitleMap(groupId,
			classNameId, classPK);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpFriendlyURLEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpFriendlyURLEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public java.util.Map<java.util.Locale, String> getUrlTitleMap(
		long groupId, long classNameId, long classPK) {
		return _cpFriendlyURLEntryLocalService.getUrlTitleMap(groupId,
			classNameId, classPK);
	}

	@Override
	public String getUrlTitleMapAsXML(long groupId, long classNameId,
		long classPK, String defaultLanguageId) {
		return _cpFriendlyURLEntryLocalService.getUrlTitleMapAsXML(groupId,
			classNameId, classPK, defaultLanguageId);
	}

	/**
	* Updates the cp friendly url entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpFriendlyURLEntry the cp friendly url entry
	* @return the cp friendly url entry that was updated
	*/
	@Override
	public com.liferay.commerce.product.model.CPFriendlyURLEntry updateCPFriendlyURLEntry(
		com.liferay.commerce.product.model.CPFriendlyURLEntry cpFriendlyURLEntry) {
		return _cpFriendlyURLEntryLocalService.updateCPFriendlyURLEntry(cpFriendlyURLEntry);
	}

	@Override
	public CPFriendlyURLEntryLocalService getWrappedService() {
		return _cpFriendlyURLEntryLocalService;
	}

	@Override
	public void setWrappedService(
		CPFriendlyURLEntryLocalService cpFriendlyURLEntryLocalService) {
		_cpFriendlyURLEntryLocalService = cpFriendlyURLEntryLocalService;
	}

	private CPFriendlyURLEntryLocalService _cpFriendlyURLEntryLocalService;
}