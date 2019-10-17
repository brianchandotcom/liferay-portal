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

package com.liferay.info.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link InfoItemUsageLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see InfoItemUsageLocalService
 * @generated
 */
public class InfoItemUsageLocalServiceWrapper
	implements InfoItemUsageLocalService,
			   ServiceWrapper<InfoItemUsageLocalService> {

	public InfoItemUsageLocalServiceWrapper(
		InfoItemUsageLocalService infoItemUsageLocalService) {

		_infoItemUsageLocalService = infoItemUsageLocalService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link InfoItemUsageLocalServiceUtil} to access the info item usage local service. Add custom service methods to <code>com.liferay.info.service.impl.InfoItemUsageLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.liferay.info.model.InfoItemUsage addDefaultInfoItemUsage(
		long groupId, long classNameId, long classPK,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _infoItemUsageLocalService.addDefaultInfoItemUsage(
			groupId, classNameId, classPK, serviceContext);
	}

	/**
	 * Adds the info item usage to the database. Also notifies the appropriate model listeners.
	 *
	 * @param infoItemUsage the info item usage
	 * @return the info item usage that was added
	 */
	@Override
	public com.liferay.info.model.InfoItemUsage addInfoItemUsage(
		com.liferay.info.model.InfoItemUsage infoItemUsage) {

		return _infoItemUsageLocalService.addInfoItemUsage(infoItemUsage);
	}

	@Override
	public com.liferay.info.model.InfoItemUsage addInfoItemUsage(
		long groupId, long classNameId, long classPK, long containerType,
		String containerKey, long plid, int type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _infoItemUsageLocalService.addInfoItemUsage(
			groupId, classNameId, classPK, containerType, containerKey, plid,
			type, serviceContext);
	}

	/**
	 * Creates a new info item usage with the primary key. Does not add the info item usage to the database.
	 *
	 * @param infoItemUsageId the primary key for the new info item usage
	 * @return the new info item usage
	 */
	@Override
	public com.liferay.info.model.InfoItemUsage createInfoItemUsage(
		long infoItemUsageId) {

		return _infoItemUsageLocalService.createInfoItemUsage(infoItemUsageId);
	}

	/**
	 * Deletes the info item usage from the database. Also notifies the appropriate model listeners.
	 *
	 * @param infoItemUsage the info item usage
	 * @return the info item usage that was removed
	 */
	@Override
	public com.liferay.info.model.InfoItemUsage deleteInfoItemUsage(
		com.liferay.info.model.InfoItemUsage infoItemUsage) {

		return _infoItemUsageLocalService.deleteInfoItemUsage(infoItemUsage);
	}

	/**
	 * Deletes the info item usage with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param infoItemUsageId the primary key of the info item usage
	 * @return the info item usage that was removed
	 * @throws PortalException if a info item usage with the primary key could not be found
	 */
	@Override
	public com.liferay.info.model.InfoItemUsage deleteInfoItemUsage(
			long infoItemUsageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _infoItemUsageLocalService.deleteInfoItemUsage(infoItemUsageId);
	}

	@Override
	public void deleteInfoItemUsages(long classNameId, long classPK) {
		_infoItemUsageLocalService.deleteInfoItemUsages(classNameId, classPK);
	}

	@Override
	public void deleteInfoItemUsages(
		String containerKey, long containerType, long plid) {

		_infoItemUsageLocalService.deleteInfoItemUsages(
			containerKey, containerType, plid);
	}

	@Override
	public void deleteInfoItemUsagesByPlid(long plid) {
		_infoItemUsageLocalService.deleteInfoItemUsagesByPlid(plid);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _infoItemUsageLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _infoItemUsageLocalService.dynamicQuery();
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

		return _infoItemUsageLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.info.model.impl.InfoItemUsageModelImpl</code>.
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

		return _infoItemUsageLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.info.model.impl.InfoItemUsageModelImpl</code>.
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

		return _infoItemUsageLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
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

		return _infoItemUsageLocalService.dynamicQueryCount(dynamicQuery);
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

		return _infoItemUsageLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.info.model.InfoItemUsage fetchInfoItemUsage(
		long infoItemUsageId) {

		return _infoItemUsageLocalService.fetchInfoItemUsage(infoItemUsageId);
	}

	@Override
	public com.liferay.info.model.InfoItemUsage fetchInfoItemUsage(
		long classNameId, long classPK, String containerKey, long containerType,
		long plid) {

		return _infoItemUsageLocalService.fetchInfoItemUsage(
			classNameId, classPK, containerKey, containerType, plid);
	}

	/**
	 * Returns the info item usage matching the UUID and group.
	 *
	 * @param uuid the info item usage's UUID
	 * @param groupId the primary key of the group
	 * @return the matching info item usage, or <code>null</code> if a matching info item usage could not be found
	 */
	@Override
	public com.liferay.info.model.InfoItemUsage
		fetchInfoItemUsageByUuidAndGroupId(String uuid, long groupId) {

		return _infoItemUsageLocalService.fetchInfoItemUsageByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _infoItemUsageLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _infoItemUsageLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the info item usage with the primary key.
	 *
	 * @param infoItemUsageId the primary key of the info item usage
	 * @return the info item usage
	 * @throws PortalException if a info item usage with the primary key could not be found
	 */
	@Override
	public com.liferay.info.model.InfoItemUsage getInfoItemUsage(
			long infoItemUsageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _infoItemUsageLocalService.getInfoItemUsage(infoItemUsageId);
	}

	/**
	 * Returns the info item usage matching the UUID and group.
	 *
	 * @param uuid the info item usage's UUID
	 * @param groupId the primary key of the group
	 * @return the matching info item usage
	 * @throws PortalException if a matching info item usage could not be found
	 */
	@Override
	public com.liferay.info.model.InfoItemUsage
			getInfoItemUsageByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _infoItemUsageLocalService.getInfoItemUsageByUuidAndGroupId(
			uuid, groupId);
	}

	/**
	 * Returns a range of all the info item usages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.info.model.impl.InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @return the range of info item usages
	 */
	@Override
	public java.util.List<com.liferay.info.model.InfoItemUsage>
		getInfoItemUsages(int start, int end) {

		return _infoItemUsageLocalService.getInfoItemUsages(start, end);
	}

	@Override
	public java.util.List<com.liferay.info.model.InfoItemUsage>
		getInfoItemUsages(long classNameId, long classPK) {

		return _infoItemUsageLocalService.getInfoItemUsages(
			classNameId, classPK);
	}

	@Override
	public java.util.List<com.liferay.info.model.InfoItemUsage>
		getInfoItemUsages(
			long classNameId, long classPK, int type, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.info.model.InfoItemUsage> orderByComparator) {

		return _infoItemUsageLocalService.getInfoItemUsages(
			classNameId, classPK, type, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.info.model.InfoItemUsage>
		getInfoItemUsages(
			long classNameId, long classPK, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.info.model.InfoItemUsage> orderByComparator) {

		return _infoItemUsageLocalService.getInfoItemUsages(
			classNameId, classPK, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.info.model.InfoItemUsage>
		getInfoItemUsagesByPlid(long plid) {

		return _infoItemUsageLocalService.getInfoItemUsagesByPlid(plid);
	}

	/**
	 * Returns the number of info item usages.
	 *
	 * @return the number of info item usages
	 */
	@Override
	public int getInfoItemUsagesCount() {
		return _infoItemUsageLocalService.getInfoItemUsagesCount();
	}

	@Override
	public int getInfoItemUsagesCount(long classNameId, long classPK) {
		return _infoItemUsageLocalService.getInfoItemUsagesCount(
			classNameId, classPK);
	}

	@Override
	public int getInfoItemUsagesCount(
		long classNameId, long classPK, int type) {

		return _infoItemUsageLocalService.getInfoItemUsagesCount(
			classNameId, classPK, type);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _infoItemUsageLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _infoItemUsageLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public int getUniqueInfoItemUsagesCount(long classNameId, long classPK) {
		return _infoItemUsageLocalService.getUniqueInfoItemUsagesCount(
			classNameId, classPK);
	}

	@Override
	public boolean hasDefaultInfoItemUsage(long classNameId, long classPK) {
		return _infoItemUsageLocalService.hasDefaultInfoItemUsage(
			classNameId, classPK);
	}

	/**
	 * Updates the info item usage in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param infoItemUsage the info item usage
	 * @return the info item usage that was updated
	 */
	@Override
	public com.liferay.info.model.InfoItemUsage updateInfoItemUsage(
		com.liferay.info.model.InfoItemUsage infoItemUsage) {

		return _infoItemUsageLocalService.updateInfoItemUsage(infoItemUsage);
	}

	@Override
	public InfoItemUsageLocalService getWrappedService() {
		return _infoItemUsageLocalService;
	}

	@Override
	public void setWrappedService(
		InfoItemUsageLocalService infoItemUsageLocalService) {

		_infoItemUsageLocalService = infoItemUsageLocalService;
	}

	private InfoItemUsageLocalService _infoItemUsageLocalService;

}