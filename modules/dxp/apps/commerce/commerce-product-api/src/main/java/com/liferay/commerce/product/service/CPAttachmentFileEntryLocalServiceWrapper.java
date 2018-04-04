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
 * Provides a wrapper for {@link CPAttachmentFileEntryLocalService}.
 *
 * @author Marco Leo
 * @see CPAttachmentFileEntryLocalService
 * @generated
 */
@ProviderType
public class CPAttachmentFileEntryLocalServiceWrapper
	implements CPAttachmentFileEntryLocalService,
		ServiceWrapper<CPAttachmentFileEntryLocalService> {
	public CPAttachmentFileEntryLocalServiceWrapper(
		CPAttachmentFileEntryLocalService cpAttachmentFileEntryLocalService) {
		_cpAttachmentFileEntryLocalService = cpAttachmentFileEntryLocalService;
	}

	/**
	* Adds the cp attachment file entry to the database. Also notifies the appropriate model listeners.
	*
	* @param cpAttachmentFileEntry the cp attachment file entry
	* @return the cp attachment file entry that was added
	*/
	@Override
	public com.liferay.commerce.product.model.CPAttachmentFileEntry addCPAttachmentFileEntry(
		com.liferay.commerce.product.model.CPAttachmentFileEntry cpAttachmentFileEntry) {
		return _cpAttachmentFileEntryLocalService.addCPAttachmentFileEntry(cpAttachmentFileEntry);
	}

	@Override
	public com.liferay.commerce.product.model.CPAttachmentFileEntry addCPAttachmentFileEntry(
		long classNameId, long classPK, long fileEntryId, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, int expirationDateMonth, int expirationDateDay,
		int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.lang.String json, double priority, int type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpAttachmentFileEntryLocalService.addCPAttachmentFileEntry(classNameId,
			classPK, fileEntryId, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire, titleMap,
			json, priority, type, serviceContext);
	}

	@Override
	public void checkCPAttachmentFileEntries()
		throws com.liferay.portal.kernel.exception.PortalException {
		_cpAttachmentFileEntryLocalService.checkCPAttachmentFileEntries();
	}

	@Override
	public void checkCPAttachmentFileEntriesByDisplayDate(long classNameId,
		long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		_cpAttachmentFileEntryLocalService.checkCPAttachmentFileEntriesByDisplayDate(classNameId,
			classPK);
	}

	/**
	* Creates a new cp attachment file entry with the primary key. Does not add the cp attachment file entry to the database.
	*
	* @param CPAttachmentFileEntryId the primary key for the new cp attachment file entry
	* @return the new cp attachment file entry
	*/
	@Override
	public com.liferay.commerce.product.model.CPAttachmentFileEntry createCPAttachmentFileEntry(
		long CPAttachmentFileEntryId) {
		return _cpAttachmentFileEntryLocalService.createCPAttachmentFileEntry(CPAttachmentFileEntryId);
	}

	@Override
	public void deleteCPAttachmentFileEntries(java.lang.String className,
		long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		_cpAttachmentFileEntryLocalService.deleteCPAttachmentFileEntries(className,
			classPK);
	}

	/**
	* Deletes the cp attachment file entry from the database. Also notifies the appropriate model listeners.
	*
	* @param cpAttachmentFileEntry the cp attachment file entry
	* @return the cp attachment file entry that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.commerce.product.model.CPAttachmentFileEntry deleteCPAttachmentFileEntry(
		com.liferay.commerce.product.model.CPAttachmentFileEntry cpAttachmentFileEntry)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpAttachmentFileEntryLocalService.deleteCPAttachmentFileEntry(cpAttachmentFileEntry);
	}

	/**
	* Deletes the cp attachment file entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPAttachmentFileEntryId the primary key of the cp attachment file entry
	* @return the cp attachment file entry that was removed
	* @throws PortalException if a cp attachment file entry with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPAttachmentFileEntry deleteCPAttachmentFileEntry(
		long CPAttachmentFileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpAttachmentFileEntryLocalService.deleteCPAttachmentFileEntry(CPAttachmentFileEntryId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpAttachmentFileEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _cpAttachmentFileEntryLocalService.dynamicQuery();
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
		return _cpAttachmentFileEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPAttachmentFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cpAttachmentFileEntryLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPAttachmentFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cpAttachmentFileEntryLocalService.dynamicQuery(dynamicQuery,
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
		return _cpAttachmentFileEntryLocalService.dynamicQueryCount(dynamicQuery);
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
		return _cpAttachmentFileEntryLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.product.model.CPAttachmentFileEntry fetchCPAttachmentFileEntry(
		long CPAttachmentFileEntryId) {
		return _cpAttachmentFileEntryLocalService.fetchCPAttachmentFileEntry(CPAttachmentFileEntryId);
	}

	/**
	* Returns the cp attachment file entry matching the UUID and group.
	*
	* @param uuid the cp attachment file entry's UUID
	* @param groupId the primary key of the group
	* @return the matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPAttachmentFileEntry fetchCPAttachmentFileEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _cpAttachmentFileEntryLocalService.fetchCPAttachmentFileEntryByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _cpAttachmentFileEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.repository.model.Folder getAttachmentsFolder(
		long userId, long groupId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpAttachmentFileEntryLocalService.getAttachmentsFolder(userId,
			groupId, className, classPK);
	}

	/**
	* Returns a range of all the cp attachment file entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPAttachmentFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp attachment file entries
	* @param end the upper bound of the range of cp attachment file entries (not inclusive)
	* @return the range of cp attachment file entries
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPAttachmentFileEntry> getCPAttachmentFileEntries(
		int start, int end) {
		return _cpAttachmentFileEntryLocalService.getCPAttachmentFileEntries(start,
			end);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPAttachmentFileEntry> getCPAttachmentFileEntries(
		long classNameId, long classPK, int type, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpAttachmentFileEntryLocalService.getCPAttachmentFileEntries(classNameId,
			classPK, type, status, start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPAttachmentFileEntry> getCPAttachmentFileEntries(
		long classNameId, long classPK, int type, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPAttachmentFileEntry> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpAttachmentFileEntryLocalService.getCPAttachmentFileEntries(classNameId,
			classPK, type, status, start, end, orderByComparator);
	}

	/**
	* Returns all the cp attachment file entries matching the UUID and company.
	*
	* @param uuid the UUID of the cp attachment file entries
	* @param companyId the primary key of the company
	* @return the matching cp attachment file entries, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPAttachmentFileEntry> getCPAttachmentFileEntriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _cpAttachmentFileEntryLocalService.getCPAttachmentFileEntriesByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of cp attachment file entries matching the UUID and company.
	*
	* @param uuid the UUID of the cp attachment file entries
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of cp attachment file entries
	* @param end the upper bound of the range of cp attachment file entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching cp attachment file entries, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPAttachmentFileEntry> getCPAttachmentFileEntriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPAttachmentFileEntry> orderByComparator) {
		return _cpAttachmentFileEntryLocalService.getCPAttachmentFileEntriesByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of cp attachment file entries.
	*
	* @return the number of cp attachment file entries
	*/
	@Override
	public int getCPAttachmentFileEntriesCount() {
		return _cpAttachmentFileEntryLocalService.getCPAttachmentFileEntriesCount();
	}

	@Override
	public int getCPAttachmentFileEntriesCount(long classNameId, long classPK,
		int type, int status) {
		return _cpAttachmentFileEntryLocalService.getCPAttachmentFileEntriesCount(classNameId,
			classPK, type, status);
	}

	/**
	* Returns the cp attachment file entry with the primary key.
	*
	* @param CPAttachmentFileEntryId the primary key of the cp attachment file entry
	* @return the cp attachment file entry
	* @throws PortalException if a cp attachment file entry with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPAttachmentFileEntry getCPAttachmentFileEntry(
		long CPAttachmentFileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpAttachmentFileEntryLocalService.getCPAttachmentFileEntry(CPAttachmentFileEntryId);
	}

	/**
	* Returns the cp attachment file entry matching the UUID and group.
	*
	* @param uuid the cp attachment file entry's UUID
	* @param groupId the primary key of the group
	* @return the matching cp attachment file entry
	* @throws PortalException if a matching cp attachment file entry could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPAttachmentFileEntry getCPAttachmentFileEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpAttachmentFileEntryLocalService.getCPAttachmentFileEntryByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _cpAttachmentFileEntryLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _cpAttachmentFileEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _cpAttachmentFileEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpAttachmentFileEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the cp attachment file entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpAttachmentFileEntry the cp attachment file entry
	* @return the cp attachment file entry that was updated
	*/
	@Override
	public com.liferay.commerce.product.model.CPAttachmentFileEntry updateCPAttachmentFileEntry(
		com.liferay.commerce.product.model.CPAttachmentFileEntry cpAttachmentFileEntry) {
		return _cpAttachmentFileEntryLocalService.updateCPAttachmentFileEntry(cpAttachmentFileEntry);
	}

	@Override
	public com.liferay.commerce.product.model.CPAttachmentFileEntry updateCPAttachmentFileEntry(
		long cpAttachmentFileEntryId, long fileEntryId, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, int expirationDateMonth, int expirationDateDay,
		int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.lang.String json, double priority, int type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpAttachmentFileEntryLocalService.updateCPAttachmentFileEntry(cpAttachmentFileEntryId,
			fileEntryId, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, titleMap, json, priority, type,
			serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CPAttachmentFileEntry updateStatus(
		long userId, long cpAttachmentFileEntryId, int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext,
		java.util.Map<java.lang.String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpAttachmentFileEntryLocalService.updateStatus(userId,
			cpAttachmentFileEntryId, status, serviceContext, workflowContext);
	}

	@Override
	public CPAttachmentFileEntryLocalService getWrappedService() {
		return _cpAttachmentFileEntryLocalService;
	}

	@Override
	public void setWrappedService(
		CPAttachmentFileEntryLocalService cpAttachmentFileEntryLocalService) {
		_cpAttachmentFileEntryLocalService = cpAttachmentFileEntryLocalService;
	}

	private CPAttachmentFileEntryLocalService _cpAttachmentFileEntryLocalService;
}