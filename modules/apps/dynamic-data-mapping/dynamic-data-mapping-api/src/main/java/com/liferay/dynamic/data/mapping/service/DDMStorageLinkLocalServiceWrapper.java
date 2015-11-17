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

package com.liferay.dynamic.data.mapping.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DDMStorageLinkLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DDMStorageLinkLocalService
 * @generated
 */
@ProviderType
public class DDMStorageLinkLocalServiceWrapper
	implements DDMStorageLinkLocalService,
		ServiceWrapper<DDMStorageLinkLocalService> {
	public DDMStorageLinkLocalServiceWrapper(
		DDMStorageLinkLocalService ddmStorageLinkLocalService) {
		_ddmStorageLinkLocalService = ddmStorageLinkLocalService;
	}

	/**
	* Adds the d d m storage link to the database. Also notifies the appropriate model listeners.
	*
	* @param ddmStorageLink the d d m storage link
	* @return the d d m storage link that was added
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStorageLink addDDMStorageLink(
		com.liferay.dynamic.data.mapping.model.DDMStorageLink ddmStorageLink) {
		return _ddmStorageLinkLocalService.addDDMStorageLink(ddmStorageLink);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStorageLink addStorageLink(
		long classNameId, long classPK, long structureId,
		com.liferay.portal.service.ServiceContext serviceContext) {
		return _ddmStorageLinkLocalService.addStorageLink(classNameId, classPK,
			structureId, serviceContext);
	}

	/**
	* Creates a new d d m storage link with the primary key. Does not add the d d m storage link to the database.
	*
	* @param storageLinkId the primary key for the new d d m storage link
	* @return the new d d m storage link
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStorageLink createDDMStorageLink(
		long storageLinkId) {
		return _ddmStorageLinkLocalService.createDDMStorageLink(storageLinkId);
	}

	@Override
	public void deleteClassStorageLink(long classPK) {
		_ddmStorageLinkLocalService.deleteClassStorageLink(classPK);
	}

	/**
	* Deletes the d d m storage link from the database. Also notifies the appropriate model listeners.
	*
	* @param ddmStorageLink the d d m storage link
	* @return the d d m storage link that was removed
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStorageLink deleteDDMStorageLink(
		com.liferay.dynamic.data.mapping.model.DDMStorageLink ddmStorageLink) {
		return _ddmStorageLinkLocalService.deleteDDMStorageLink(ddmStorageLink);
	}

	/**
	* Deletes the d d m storage link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param storageLinkId the primary key of the d d m storage link
	* @return the d d m storage link that was removed
	* @throws PortalException if a d d m storage link with the primary key could not be found
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStorageLink deleteDDMStorageLink(
		long storageLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStorageLinkLocalService.deleteDDMStorageLink(storageLinkId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStorageLinkLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public void deleteStorageLink(
		com.liferay.dynamic.data.mapping.model.DDMStorageLink storageLink) {
		_ddmStorageLinkLocalService.deleteStorageLink(storageLink);
	}

	@Override
	public void deleteStorageLink(long storageLinkId) {
		_ddmStorageLinkLocalService.deleteStorageLink(storageLinkId);
	}

	@Override
	public void deleteStructureStorageLinks(long structureId) {
		_ddmStorageLinkLocalService.deleteStructureStorageLinks(structureId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _ddmStorageLinkLocalService.dynamicQuery();
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
		return _ddmStorageLinkLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMStorageLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _ddmStorageLinkLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMStorageLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _ddmStorageLinkLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
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
		return _ddmStorageLinkLocalService.dynamicQueryCount(dynamicQuery);
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
		return _ddmStorageLinkLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStorageLink fetchDDMStorageLink(
		long storageLinkId) {
		return _ddmStorageLinkLocalService.fetchDDMStorageLink(storageLinkId);
	}

	/**
	* Returns the d d m storage link with the matching UUID and company.
	*
	* @param uuid the d d m storage link's UUID
	* @param companyId the primary key of the company
	* @return the matching d d m storage link, or <code>null</code> if a matching d d m storage link could not be found
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStorageLink fetchDDMStorageLinkByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _ddmStorageLinkLocalService.fetchDDMStorageLinkByUuidAndCompanyId(uuid,
			companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _ddmStorageLinkLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStorageLink getClassStorageLink(
		long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStorageLinkLocalService.getClassStorageLink(classPK);
	}

	/**
	* Returns the d d m storage link with the primary key.
	*
	* @param storageLinkId the primary key of the d d m storage link
	* @return the d d m storage link
	* @throws PortalException if a d d m storage link with the primary key could not be found
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStorageLink getDDMStorageLink(
		long storageLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStorageLinkLocalService.getDDMStorageLink(storageLinkId);
	}

	/**
	* Returns the d d m storage link with the matching UUID and company.
	*
	* @param uuid the d d m storage link's UUID
	* @param companyId the primary key of the company
	* @return the matching d d m storage link
	* @throws PortalException if a matching d d m storage link could not be found
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStorageLink getDDMStorageLinkByUuidAndCompanyId(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStorageLinkLocalService.getDDMStorageLinkByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of all the d d m storage links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMStorageLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m storage links
	* @param end the upper bound of the range of d d m storage links (not inclusive)
	* @return the range of d d m storage links
	*/
	@Override
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMStorageLink> getDDMStorageLinks(
		int start, int end) {
		return _ddmStorageLinkLocalService.getDDMStorageLinks(start, end);
	}

	/**
	* Returns the number of d d m storage links.
	*
	* @return the number of d d m storage links
	*/
	@Override
	public int getDDMStorageLinksCount() {
		return _ddmStorageLinkLocalService.getDDMStorageLinksCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _ddmStorageLinkLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _ddmStorageLinkLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStorageLinkLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStorageLink getStorageLink(
		long storageLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStorageLinkLocalService.getStorageLink(storageLinkId);
	}

	@Override
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMStorageLink> getStructureStorageLinks(
		long structureId) {
		return _ddmStorageLinkLocalService.getStructureStorageLinks(structureId);
	}

	@Override
	public int getStructureStorageLinksCount(long structureId) {
		return _ddmStorageLinkLocalService.getStructureStorageLinksCount(structureId);
	}

	/**
	* Updates the d d m storage link in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ddmStorageLink the d d m storage link
	* @return the d d m storage link that was updated
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStorageLink updateDDMStorageLink(
		com.liferay.dynamic.data.mapping.model.DDMStorageLink ddmStorageLink) {
		return _ddmStorageLinkLocalService.updateDDMStorageLink(ddmStorageLink);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStorageLink updateStorageLink(
		long storageLinkId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStorageLinkLocalService.updateStorageLink(storageLinkId,
			classNameId, classPK);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public DDMStorageLinkLocalService getWrappedDDMStorageLinkLocalService() {
		return _ddmStorageLinkLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedDDMStorageLinkLocalService(
		DDMStorageLinkLocalService ddmStorageLinkLocalService) {
		_ddmStorageLinkLocalService = ddmStorageLinkLocalService;
	}

	@Override
	public DDMStorageLinkLocalService getWrappedService() {
		return _ddmStorageLinkLocalService;
	}

	@Override
	public void setWrappedService(
		DDMStorageLinkLocalService ddmStorageLinkLocalService) {
		_ddmStorageLinkLocalService = ddmStorageLinkLocalService;
	}

	private DDMStorageLinkLocalService _ddmStorageLinkLocalService;
}