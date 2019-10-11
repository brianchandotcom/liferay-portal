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

package com.liferay.message.boards.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link MBThreadFlagLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see MBThreadFlagLocalService
 * @generated
 */
public class MBThreadFlagLocalServiceWrapper
	implements MBThreadFlagLocalService,
			   ServiceWrapper<MBThreadFlagLocalService> {

	public MBThreadFlagLocalServiceWrapper(
		MBThreadFlagLocalService mbThreadFlagLocalService) {

		_mbThreadFlagLocalService = mbThreadFlagLocalService;
	}

	/**
	 * Adds the message boards thread flag to the database. Also notifies the appropriate model listeners.
	 *
	 * @param mbThreadFlag the message boards thread flag
	 * @return the message boards thread flag that was added
	 */
	@Override
	public com.liferay.message.boards.model.MBThreadFlag addMBThreadFlag(
		com.liferay.message.boards.model.MBThreadFlag mbThreadFlag) {

		return _mbThreadFlagLocalService.addMBThreadFlag(mbThreadFlag);
	}

	@Override
	public com.liferay.message.boards.model.MBThreadFlag addThreadFlag(
			long userId, com.liferay.message.boards.model.MBThread thread,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbThreadFlagLocalService.addThreadFlag(
			userId, thread, serviceContext);
	}

	/**
	 * Creates a new message boards thread flag with the primary key. Does not add the message boards thread flag to the database.
	 *
	 * @param threadFlagId the primary key for the new message boards thread flag
	 * @return the new message boards thread flag
	 */
	@Override
	public com.liferay.message.boards.model.MBThreadFlag createMBThreadFlag(
		long threadFlagId) {

		return _mbThreadFlagLocalService.createMBThreadFlag(threadFlagId);
	}

	/**
	 * Deletes the message boards thread flag with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param threadFlagId the primary key of the message boards thread flag
	 * @return the message boards thread flag that was removed
	 * @throws PortalException if a message boards thread flag with the primary key could not be found
	 */
	@Override
	public com.liferay.message.boards.model.MBThreadFlag deleteMBThreadFlag(
			long threadFlagId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbThreadFlagLocalService.deleteMBThreadFlag(threadFlagId);
	}

	/**
	 * Deletes the message boards thread flag from the database. Also notifies the appropriate model listeners.
	 *
	 * @param mbThreadFlag the message boards thread flag
	 * @return the message boards thread flag that was removed
	 */
	@Override
	public com.liferay.message.boards.model.MBThreadFlag deleteMBThreadFlag(
		com.liferay.message.boards.model.MBThreadFlag mbThreadFlag) {

		return _mbThreadFlagLocalService.deleteMBThreadFlag(mbThreadFlag);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbThreadFlagLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public void deleteThreadFlag(long threadFlagId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_mbThreadFlagLocalService.deleteThreadFlag(threadFlagId);
	}

	@Override
	public void deleteThreadFlag(
		com.liferay.message.boards.model.MBThreadFlag threadFlag) {

		_mbThreadFlagLocalService.deleteThreadFlag(threadFlag);
	}

	@Override
	public void deleteThreadFlagsByThreadId(long threadId) {
		_mbThreadFlagLocalService.deleteThreadFlagsByThreadId(threadId);
	}

	@Override
	public void deleteThreadFlagsByUserId(long userId) {
		_mbThreadFlagLocalService.deleteThreadFlagsByUserId(userId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _mbThreadFlagLocalService.dynamicQuery();
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

		return _mbThreadFlagLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.message.boards.model.impl.MBThreadFlagModelImpl</code>.
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

		return _mbThreadFlagLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.message.boards.model.impl.MBThreadFlagModelImpl</code>.
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

		return _mbThreadFlagLocalService.dynamicQuery(
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

		return _mbThreadFlagLocalService.dynamicQueryCount(dynamicQuery);
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

		return _mbThreadFlagLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.message.boards.model.MBThreadFlag fetchMBThreadFlag(
		long threadFlagId) {

		return _mbThreadFlagLocalService.fetchMBThreadFlag(threadFlagId);
	}

	/**
	 * Returns the message boards thread flag matching the UUID and group.
	 *
	 * @param uuid the message boards thread flag's UUID
	 * @param groupId the primary key of the group
	 * @return the matching message boards thread flag, or <code>null</code> if a matching message boards thread flag could not be found
	 */
	@Override
	public com.liferay.message.boards.model.MBThreadFlag
		fetchMBThreadFlagByUuidAndGroupId(String uuid, long groupId) {

		return _mbThreadFlagLocalService.fetchMBThreadFlagByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _mbThreadFlagLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _mbThreadFlagLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _mbThreadFlagLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the message boards thread flag with the primary key.
	 *
	 * @param threadFlagId the primary key of the message boards thread flag
	 * @return the message boards thread flag
	 * @throws PortalException if a message boards thread flag with the primary key could not be found
	 */
	@Override
	public com.liferay.message.boards.model.MBThreadFlag getMBThreadFlag(
			long threadFlagId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbThreadFlagLocalService.getMBThreadFlag(threadFlagId);
	}

	/**
	 * Returns the message boards thread flag matching the UUID and group.
	 *
	 * @param uuid the message boards thread flag's UUID
	 * @param groupId the primary key of the group
	 * @return the matching message boards thread flag
	 * @throws PortalException if a matching message boards thread flag could not be found
	 */
	@Override
	public com.liferay.message.boards.model.MBThreadFlag
			getMBThreadFlagByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbThreadFlagLocalService.getMBThreadFlagByUuidAndGroupId(
			uuid, groupId);
	}

	/**
	 * Returns a range of all the message boards thread flags.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.message.boards.model.impl.MBThreadFlagModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards thread flags
	 * @param end the upper bound of the range of message boards thread flags (not inclusive)
	 * @return the range of message boards thread flags
	 */
	@Override
	public java.util.List<com.liferay.message.boards.model.MBThreadFlag>
		getMBThreadFlags(int start, int end) {

		return _mbThreadFlagLocalService.getMBThreadFlags(start, end);
	}

	/**
	 * Returns all the message boards thread flags matching the UUID and company.
	 *
	 * @param uuid the UUID of the message boards thread flags
	 * @param companyId the primary key of the company
	 * @return the matching message boards thread flags, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.liferay.message.boards.model.MBThreadFlag>
		getMBThreadFlagsByUuidAndCompanyId(String uuid, long companyId) {

		return _mbThreadFlagLocalService.getMBThreadFlagsByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of message boards thread flags matching the UUID and company.
	 *
	 * @param uuid the UUID of the message boards thread flags
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of message boards thread flags
	 * @param end the upper bound of the range of message boards thread flags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching message boards thread flags, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.liferay.message.boards.model.MBThreadFlag>
		getMBThreadFlagsByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.message.boards.model.MBThreadFlag>
					orderByComparator) {

		return _mbThreadFlagLocalService.getMBThreadFlagsByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of message boards thread flags.
	 *
	 * @return the number of message boards thread flags
	 */
	@Override
	public int getMBThreadFlagsCount() {
		return _mbThreadFlagLocalService.getMBThreadFlagsCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _mbThreadFlagLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbThreadFlagLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.message.boards.model.MBThreadFlag getThreadFlag(
			long userId, com.liferay.message.boards.model.MBThread thread)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbThreadFlagLocalService.getThreadFlag(userId, thread);
	}

	@Override
	public boolean hasThreadFlag(
			long userId, com.liferay.message.boards.model.MBThread thread)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbThreadFlagLocalService.hasThreadFlag(userId, thread);
	}

	/**
	 * Updates the message boards thread flag in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param mbThreadFlag the message boards thread flag
	 * @return the message boards thread flag that was updated
	 */
	@Override
	public com.liferay.message.boards.model.MBThreadFlag updateMBThreadFlag(
		com.liferay.message.boards.model.MBThreadFlag mbThreadFlag) {

		return _mbThreadFlagLocalService.updateMBThreadFlag(mbThreadFlag);
	}

	@Override
	public MBThreadFlagLocalService getWrappedService() {
		return _mbThreadFlagLocalService;
	}

	@Override
	public void setWrappedService(
		MBThreadFlagLocalService mbThreadFlagLocalService) {

		_mbThreadFlagLocalService = mbThreadFlagLocalService;
	}

	private MBThreadFlagLocalService _mbThreadFlagLocalService;

}