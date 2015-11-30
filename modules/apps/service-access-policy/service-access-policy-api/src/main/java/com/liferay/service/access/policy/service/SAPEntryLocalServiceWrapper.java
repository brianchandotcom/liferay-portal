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

package com.liferay.service.access.policy.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SAPEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see SAPEntryLocalService
 * @generated
 */
@ProviderType
public class SAPEntryLocalServiceWrapper implements SAPEntryLocalService,
	ServiceWrapper<SAPEntryLocalService> {
	public SAPEntryLocalServiceWrapper(
		SAPEntryLocalService sapEntryLocalService) {
		_sapEntryLocalService = sapEntryLocalService;
	}

	/**
	* Adds the s a p entry to the database. Also notifies the appropriate model listeners.
	*
	* @param sapEntry the s a p entry
	* @return the s a p entry that was added
	*/
	@Override
	public com.liferay.service.access.policy.model.SAPEntry addSAPEntry(
		com.liferay.service.access.policy.model.SAPEntry sapEntry) {
		return _sapEntryLocalService.addSAPEntry(sapEntry);
	}

	@Override
	public com.liferay.service.access.policy.model.SAPEntry addSAPEntry(
		long userId, java.lang.String allowedServiceSignatures,
		boolean defaultSAPEntry, boolean enabled, java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sapEntryLocalService.addSAPEntry(userId,
			allowedServiceSignatures, defaultSAPEntry, enabled, name, titleMap,
			serviceContext);
	}

	@Override
	public void checkSystemSAPEntries(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_sapEntryLocalService.checkSystemSAPEntries(companyId);
	}

	/**
	* Creates a new s a p entry with the primary key. Does not add the s a p entry to the database.
	*
	* @param sapEntryId the primary key for the new s a p entry
	* @return the new s a p entry
	*/
	@Override
	public com.liferay.service.access.policy.model.SAPEntry createSAPEntry(
		long sapEntryId) {
		return _sapEntryLocalService.createSAPEntry(sapEntryId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sapEntryLocalService.deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the s a p entry from the database. Also notifies the appropriate model listeners.
	*
	* @param sapEntry the s a p entry
	* @return the s a p entry that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.service.access.policy.model.SAPEntry deleteSAPEntry(
		com.liferay.service.access.policy.model.SAPEntry sapEntry)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sapEntryLocalService.deleteSAPEntry(sapEntry);
	}

	/**
	* Deletes the s a p entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param sapEntryId the primary key of the s a p entry
	* @return the s a p entry that was removed
	* @throws PortalException if a s a p entry with the primary key could not be found
	*/
	@Override
	public com.liferay.service.access.policy.model.SAPEntry deleteSAPEntry(
		long sapEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sapEntryLocalService.deleteSAPEntry(sapEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _sapEntryLocalService.dynamicQuery();
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
		return _sapEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.policy.model.impl.SAPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _sapEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.policy.model.impl.SAPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _sapEntryLocalService.dynamicQuery(dynamicQuery, start, end,
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
		return _sapEntryLocalService.dynamicQueryCount(dynamicQuery);
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
		return _sapEntryLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public com.liferay.service.access.policy.model.SAPEntry fetchSAPEntry(
		long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sapEntryLocalService.fetchSAPEntry(companyId, name);
	}

	@Override
	public com.liferay.service.access.policy.model.SAPEntry fetchSAPEntry(
		long sapEntryId) {
		return _sapEntryLocalService.fetchSAPEntry(sapEntryId);
	}

	/**
	* Returns the s a p entry with the matching UUID and company.
	*
	* @param uuid the s a p entry's UUID
	* @param companyId the primary key of the company
	* @return the matching s a p entry, or <code>null</code> if a matching s a p entry could not be found
	*/
	@Override
	public com.liferay.service.access.policy.model.SAPEntry fetchSAPEntryByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _sapEntryLocalService.fetchSAPEntryByUuidAndCompanyId(uuid,
			companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _sapEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public java.util.List<com.liferay.service.access.policy.model.SAPEntry> getCompanySAPEntries(
		long companyId, int start, int end) {
		return _sapEntryLocalService.getCompanySAPEntries(companyId, start, end);
	}

	@Override
	public java.util.List<com.liferay.service.access.policy.model.SAPEntry> getCompanySAPEntries(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.SAPEntry> obc) {
		return _sapEntryLocalService.getCompanySAPEntries(companyId, start,
			end, obc);
	}

	@Override
	public int getCompanySAPEntriesCount(long companyId) {
		return _sapEntryLocalService.getCompanySAPEntriesCount(companyId);
	}

	@Override
	public java.util.List<com.liferay.service.access.policy.model.SAPEntry> getDefaultSAPEntries(
		long companyId, boolean defaultSAPEntry) {
		return _sapEntryLocalService.getDefaultSAPEntries(companyId,
			defaultSAPEntry);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext) {
		return _sapEntryLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _sapEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _sapEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sapEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the s a p entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.policy.model.impl.SAPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of s a p entries
	* @param end the upper bound of the range of s a p entries (not inclusive)
	* @return the range of s a p entries
	*/
	@Override
	public java.util.List<com.liferay.service.access.policy.model.SAPEntry> getSAPEntries(
		int start, int end) {
		return _sapEntryLocalService.getSAPEntries(start, end);
	}

	/**
	* Returns the number of s a p entries.
	*
	* @return the number of s a p entries
	*/
	@Override
	public int getSAPEntriesCount() {
		return _sapEntryLocalService.getSAPEntriesCount();
	}

	@Override
	public com.liferay.service.access.policy.model.SAPEntry getSAPEntry(
		long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sapEntryLocalService.getSAPEntry(companyId, name);
	}

	/**
	* Returns the s a p entry with the primary key.
	*
	* @param sapEntryId the primary key of the s a p entry
	* @return the s a p entry
	* @throws PortalException if a s a p entry with the primary key could not be found
	*/
	@Override
	public com.liferay.service.access.policy.model.SAPEntry getSAPEntry(
		long sapEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sapEntryLocalService.getSAPEntry(sapEntryId);
	}

	/**
	* Returns the s a p entry with the matching UUID and company.
	*
	* @param uuid the s a p entry's UUID
	* @param companyId the primary key of the company
	* @return the matching s a p entry
	* @throws PortalException if a matching s a p entry could not be found
	*/
	@Override
	public com.liferay.service.access.policy.model.SAPEntry getSAPEntryByUuidAndCompanyId(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sapEntryLocalService.getSAPEntryByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Updates the s a p entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param sapEntry the s a p entry
	* @return the s a p entry that was updated
	*/
	@Override
	public com.liferay.service.access.policy.model.SAPEntry updateSAPEntry(
		com.liferay.service.access.policy.model.SAPEntry sapEntry) {
		return _sapEntryLocalService.updateSAPEntry(sapEntry);
	}

	@Override
	public com.liferay.service.access.policy.model.SAPEntry updateSAPEntry(
		long sapEntryId, java.lang.String allowedServiceSignatures,
		boolean defaultSAPEntry, boolean enabled, java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sapEntryLocalService.updateSAPEntry(sapEntryId,
			allowedServiceSignatures, defaultSAPEntry, enabled, name, titleMap,
			serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public SAPEntryLocalService getWrappedSAPEntryLocalService() {
		return _sapEntryLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedSAPEntryLocalService(
		SAPEntryLocalService sapEntryLocalService) {
		_sapEntryLocalService = sapEntryLocalService;
	}

	@Override
	public SAPEntryLocalService getWrappedService() {
		return _sapEntryLocalService;
	}

	@Override
	public void setWrappedService(SAPEntryLocalService sapEntryLocalService) {
		_sapEntryLocalService = sapEntryLocalService;
	}

	private SAPEntryLocalService _sapEntryLocalService;
}