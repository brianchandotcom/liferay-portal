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

package com.liferay.service.access.control.profile.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SACPEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see SACPEntryLocalService
 * @generated
 */
@ProviderType
public class SACPEntryLocalServiceWrapper implements SACPEntryLocalService,
	ServiceWrapper<SACPEntryLocalService> {
	public SACPEntryLocalServiceWrapper(
		SACPEntryLocalService sacpEntryLocalService) {
		_sacpEntryLocalService = sacpEntryLocalService;
	}

	/**
	* Adds the s a c p entry to the database. Also notifies the appropriate model listeners.
	*
	* @param sacpEntry the s a c p entry
	* @return the s a c p entry that was added
	*/
	@Override
	public com.liferay.service.access.control.profile.model.SACPEntry addSACPEntry(
		com.liferay.service.access.control.profile.model.SACPEntry sacpEntry) {
		return _sacpEntryLocalService.addSACPEntry(sacpEntry);
	}

	@Override
	public com.liferay.service.access.control.profile.model.SACPEntry addSACPEntry(
		long userId, java.lang.String allowedServices, java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sacpEntryLocalService.addSACPEntry(userId, allowedServices,
			name, titleMap, serviceContext);
	}

	/**
	* Creates a new s a c p entry with the primary key. Does not add the s a c p entry to the database.
	*
	* @param sacpEntryId the primary key for the new s a c p entry
	* @return the new s a c p entry
	*/
	@Override
	public com.liferay.service.access.control.profile.model.SACPEntry createSACPEntry(
		long sacpEntryId) {
		return _sacpEntryLocalService.createSACPEntry(sacpEntryId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sacpEntryLocalService.deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the s a c p entry from the database. Also notifies the appropriate model listeners.
	*
	* @param sacpEntry the s a c p entry
	* @return the s a c p entry that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.service.access.control.profile.model.SACPEntry deleteSACPEntry(
		com.liferay.service.access.control.profile.model.SACPEntry sacpEntry)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sacpEntryLocalService.deleteSACPEntry(sacpEntry);
	}

	/**
	* Deletes the s a c p entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param sacpEntryId the primary key of the s a c p entry
	* @return the s a c p entry that was removed
	* @throws PortalException if a s a c p entry with the primary key could not be found
	*/
	@Override
	public com.liferay.service.access.control.profile.model.SACPEntry deleteSACPEntry(
		long sacpEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sacpEntryLocalService.deleteSACPEntry(sacpEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _sacpEntryLocalService.dynamicQuery();
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
		return _sacpEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.control.profile.model.impl.SACPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _sacpEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.control.profile.model.impl.SACPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _sacpEntryLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
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
		return _sacpEntryLocalService.dynamicQueryCount(dynamicQuery);
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
		return _sacpEntryLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public com.liferay.service.access.control.profile.model.SACPEntry fetchSACPEntry(
		long sacpEntryId) {
		return _sacpEntryLocalService.fetchSACPEntry(sacpEntryId);
	}

	/**
	* Returns the s a c p entry with the matching UUID and company.
	*
	* @param uuid the s a c p entry's UUID
	* @param companyId the primary key of the company
	* @return the matching s a c p entry, or <code>null</code> if a matching s a c p entry could not be found
	*/
	@Override
	public com.liferay.service.access.control.profile.model.SACPEntry fetchSACPEntryByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _sacpEntryLocalService.fetchSACPEntryByUuidAndCompanyId(uuid,
			companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _sacpEntryLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _sacpEntryLocalService.getBeanIdentifier();
	}

	@Override
	public java.util.List<com.liferay.service.access.control.profile.model.SACPEntry> getCompanySACPEntries(
		long companyId, int start, int end) {
		return _sacpEntryLocalService.getCompanySACPEntries(companyId, start,
			end);
	}

	@Override
	public java.util.List<com.liferay.service.access.control.profile.model.SACPEntry> getCompanySACPEntries(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.control.profile.model.SACPEntry> obc) {
		return _sacpEntryLocalService.getCompanySACPEntries(companyId, start,
			end, obc);
	}

	@Override
	public int getCompanySACPEntriesCount(long companyId) {
		return _sacpEntryLocalService.getCompanySACPEntriesCount(companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portal.kernel.lar.PortletDataContext portletDataContext) {
		return _sacpEntryLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sacpEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the s a c p entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.control.profile.model.impl.SACPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of s a c p entries
	* @param end the upper bound of the range of s a c p entries (not inclusive)
	* @return the range of s a c p entries
	*/
	@Override
	public java.util.List<com.liferay.service.access.control.profile.model.SACPEntry> getSACPEntries(
		int start, int end) {
		return _sacpEntryLocalService.getSACPEntries(start, end);
	}

	/**
	* Returns the number of s a c p entries.
	*
	* @return the number of s a c p entries
	*/
	@Override
	public int getSACPEntriesCount() {
		return _sacpEntryLocalService.getSACPEntriesCount();
	}

	@Override
	public com.liferay.service.access.control.profile.model.SACPEntry getSACPEntry(
		long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sacpEntryLocalService.getSACPEntry(companyId, name);
	}

	/**
	* Returns the s a c p entry with the primary key.
	*
	* @param sacpEntryId the primary key of the s a c p entry
	* @return the s a c p entry
	* @throws PortalException if a s a c p entry with the primary key could not be found
	*/
	@Override
	public com.liferay.service.access.control.profile.model.SACPEntry getSACPEntry(
		long sacpEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sacpEntryLocalService.getSACPEntry(sacpEntryId);
	}

	/**
	* Returns the s a c p entry with the matching UUID and company.
	*
	* @param uuid the s a c p entry's UUID
	* @param companyId the primary key of the company
	* @return the matching s a c p entry
	* @throws PortalException if a matching s a c p entry could not be found
	*/
	@Override
	public com.liferay.service.access.control.profile.model.SACPEntry getSACPEntryByUuidAndCompanyId(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sacpEntryLocalService.getSACPEntryByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_sacpEntryLocalService.setBeanIdentifier(beanIdentifier);
	}

	/**
	* Updates the s a c p entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param sacpEntry the s a c p entry
	* @return the s a c p entry that was updated
	*/
	@Override
	public com.liferay.service.access.control.profile.model.SACPEntry updateSACPEntry(
		com.liferay.service.access.control.profile.model.SACPEntry sacpEntry) {
		return _sacpEntryLocalService.updateSACPEntry(sacpEntry);
	}

	@Override
	public com.liferay.service.access.control.profile.model.SACPEntry updateSACPEntry(
		long sacpEntryId, java.lang.String allowedServices,
		java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sacpEntryLocalService.updateSACPEntry(sacpEntryId,
			allowedServices, name, titleMap, serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public SACPEntryLocalService getWrappedSACPEntryLocalService() {
		return _sacpEntryLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedSACPEntryLocalService(
		SACPEntryLocalService sacpEntryLocalService) {
		_sacpEntryLocalService = sacpEntryLocalService;
	}

	@Override
	public SACPEntryLocalService getWrappedService() {
		return _sacpEntryLocalService;
	}

	@Override
	public void setWrappedService(SACPEntryLocalService sacpEntryLocalService) {
		_sacpEntryLocalService = sacpEntryLocalService;
	}

	private SACPEntryLocalService _sacpEntryLocalService;
}