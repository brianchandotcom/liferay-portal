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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DDMDataProviderInstanceLinkLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DDMDataProviderInstanceLinkLocalService
 * @generated
 */
@ProviderType
public class DDMDataProviderInstanceLinkLocalServiceWrapper
	implements DDMDataProviderInstanceLinkLocalService,
		ServiceWrapper<DDMDataProviderInstanceLinkLocalService> {
	public DDMDataProviderInstanceLinkLocalServiceWrapper(
		DDMDataProviderInstanceLinkLocalService ddmDataProviderInstanceLinkLocalService) {
		_ddmDataProviderInstanceLinkLocalService = ddmDataProviderInstanceLinkLocalService;
	}

	/**
	* Adds the ddm data provider instance link to the database. Also notifies the appropriate model listeners.
	*
	* @param ddmDataProviderInstanceLink the ddm data provider instance link
	* @return the ddm data provider instance link that was added
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLink addDDMDataProviderInstanceLink(
		com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLink ddmDataProviderInstanceLink) {
		return _ddmDataProviderInstanceLinkLocalService.addDDMDataProviderInstanceLink(ddmDataProviderInstanceLink);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLink addDataProviderInstanceLink(
		long dataProviderInstanceId, long structureId) {
		return _ddmDataProviderInstanceLinkLocalService.addDataProviderInstanceLink(dataProviderInstanceId,
			structureId);
	}

	/**
	* Creates a new ddm data provider instance link with the primary key. Does not add the ddm data provider instance link to the database.
	*
	* @param dataProviderInstanceLinkId the primary key for the new ddm data provider instance link
	* @return the new ddm data provider instance link
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLink createDDMDataProviderInstanceLink(
		long dataProviderInstanceLinkId) {
		return _ddmDataProviderInstanceLinkLocalService.createDDMDataProviderInstanceLink(dataProviderInstanceLinkId);
	}

	/**
	* Deletes the ddm data provider instance link from the database. Also notifies the appropriate model listeners.
	*
	* @param ddmDataProviderInstanceLink the ddm data provider instance link
	* @return the ddm data provider instance link that was removed
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLink deleteDDMDataProviderInstanceLink(
		com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLink ddmDataProviderInstanceLink) {
		return _ddmDataProviderInstanceLinkLocalService.deleteDDMDataProviderInstanceLink(ddmDataProviderInstanceLink);
	}

	/**
	* Deletes the ddm data provider instance link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param dataProviderInstanceLinkId the primary key of the ddm data provider instance link
	* @return the ddm data provider instance link that was removed
	* @throws PortalException if a ddm data provider instance link with the primary key could not be found
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLink deleteDDMDataProviderInstanceLink(
		long dataProviderInstanceLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmDataProviderInstanceLinkLocalService.deleteDDMDataProviderInstanceLink(dataProviderInstanceLinkId);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLink fetchDDMDataProviderInstanceLink(
		long dataProviderInstanceLinkId) {
		return _ddmDataProviderInstanceLinkLocalService.fetchDDMDataProviderInstanceLink(dataProviderInstanceLinkId);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLink fetchDataProviderInstanceLink(
		long dataProviderInstanceId, long structureId) {
		return _ddmDataProviderInstanceLinkLocalService.fetchDataProviderInstanceLink(dataProviderInstanceId,
			structureId);
	}

	/**
	* Returns the ddm data provider instance link with the primary key.
	*
	* @param dataProviderInstanceLinkId the primary key of the ddm data provider instance link
	* @return the ddm data provider instance link
	* @throws PortalException if a ddm data provider instance link with the primary key could not be found
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLink getDDMDataProviderInstanceLink(
		long dataProviderInstanceLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmDataProviderInstanceLinkLocalService.getDDMDataProviderInstanceLink(dataProviderInstanceLinkId);
	}

	/**
	* Updates the ddm data provider instance link in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ddmDataProviderInstanceLink the ddm data provider instance link
	* @return the ddm data provider instance link that was updated
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLink updateDDMDataProviderInstanceLink(
		com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLink ddmDataProviderInstanceLink) {
		return _ddmDataProviderInstanceLinkLocalService.updateDDMDataProviderInstanceLink(ddmDataProviderInstanceLink);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _ddmDataProviderInstanceLinkLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _ddmDataProviderInstanceLinkLocalService.dynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _ddmDataProviderInstanceLinkLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmDataProviderInstanceLinkLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmDataProviderInstanceLinkLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the number of ddm data provider instance links.
	*
	* @return the number of ddm data provider instance links
	*/
	@Override
	public int getDDMDataProviderInstanceLinksCount() {
		return _ddmDataProviderInstanceLinkLocalService.getDDMDataProviderInstanceLinksCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _ddmDataProviderInstanceLinkLocalService.getOSGiServiceIdentifier();
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
		return _ddmDataProviderInstanceLinkLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMDataProviderInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _ddmDataProviderInstanceLinkLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMDataProviderInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _ddmDataProviderInstanceLinkLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	* Returns a range of all the ddm data provider instance links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMDataProviderInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of ddm data provider instance links
	* @param end the upper bound of the range of ddm data provider instance links (not inclusive)
	* @return the range of ddm data provider instance links
	*/
	@Override
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLink> getDDMDataProviderInstanceLinks(
		int start, int end) {
		return _ddmDataProviderInstanceLinkLocalService.getDDMDataProviderInstanceLinks(start,
			end);
	}

	@Override
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLink> getDataProviderInstanceLinks(
		long structureId) {
		return _ddmDataProviderInstanceLinkLocalService.getDataProviderInstanceLinks(structureId);
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
		return _ddmDataProviderInstanceLinkLocalService.dynamicQueryCount(dynamicQuery);
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
		return _ddmDataProviderInstanceLinkLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public void deleteDataProviderInstanceLink(
		com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLink dataProviderInstanceLink) {
		_ddmDataProviderInstanceLinkLocalService.deleteDataProviderInstanceLink(dataProviderInstanceLink);
	}

	@Override
	public void deleteDataProviderInstanceLink(long dataProviderInstanceId,
		long structureId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_ddmDataProviderInstanceLinkLocalService.deleteDataProviderInstanceLink(dataProviderInstanceId,
			structureId);
	}

	@Override
	public void deleteDataProviderInstanceLink(long dataProviderInstanceLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_ddmDataProviderInstanceLinkLocalService.deleteDataProviderInstanceLink(dataProviderInstanceLinkId);
	}

	@Override
	public void deleteDataProviderInstanceLinks(long structureId) {
		_ddmDataProviderInstanceLinkLocalService.deleteDataProviderInstanceLinks(structureId);
	}

	@Override
	public DDMDataProviderInstanceLinkLocalService getWrappedService() {
		return _ddmDataProviderInstanceLinkLocalService;
	}

	@Override
	public void setWrappedService(
		DDMDataProviderInstanceLinkLocalService ddmDataProviderInstanceLinkLocalService) {
		_ddmDataProviderInstanceLinkLocalService = ddmDataProviderInstanceLinkLocalService;
	}

	private DDMDataProviderInstanceLinkLocalService _ddmDataProviderInstanceLinkLocalService;
}