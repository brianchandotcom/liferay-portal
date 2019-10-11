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
 * Provides a wrapper for {@link MBBanLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see MBBanLocalService
 * @generated
 */
public class MBBanLocalServiceWrapper
	implements MBBanLocalService, ServiceWrapper<MBBanLocalService> {

	public MBBanLocalServiceWrapper(MBBanLocalService mbBanLocalService) {
		_mbBanLocalService = mbBanLocalService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MBBanLocalServiceUtil} to access the message boards ban local service. Add custom service methods to <code>com.liferay.message.boards.service.impl.MBBanLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.liferay.message.boards.model.MBBan addBan(
			long userId, long banUserId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbBanLocalService.addBan(userId, banUserId, serviceContext);
	}

	/**
	 * Adds the message boards ban to the database. Also notifies the appropriate model listeners.
	 *
	 * @param mbBan the message boards ban
	 * @return the message boards ban that was added
	 */
	@Override
	public com.liferay.message.boards.model.MBBan addMBBan(
		com.liferay.message.boards.model.MBBan mbBan) {

		return _mbBanLocalService.addMBBan(mbBan);
	}

	@Override
	public void checkBan(long groupId, long banUserId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_mbBanLocalService.checkBan(groupId, banUserId);
	}

	/**
	 * Creates a new message boards ban with the primary key. Does not add the message boards ban to the database.
	 *
	 * @param banId the primary key for the new message boards ban
	 * @return the new message boards ban
	 */
	@Override
	public com.liferay.message.boards.model.MBBan createMBBan(long banId) {
		return _mbBanLocalService.createMBBan(banId);
	}

	@Override
	public void deleteBan(long banId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_mbBanLocalService.deleteBan(banId);
	}

	@Override
	public void deleteBan(
		long banUserId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		_mbBanLocalService.deleteBan(banUserId, serviceContext);
	}

	@Override
	public void deleteBan(com.liferay.message.boards.model.MBBan ban) {
		_mbBanLocalService.deleteBan(ban);
	}

	@Override
	public void deleteBansByBanUserId(long banUserId) {
		_mbBanLocalService.deleteBansByBanUserId(banUserId);
	}

	@Override
	public void deleteBansByGroupId(long groupId) {
		_mbBanLocalService.deleteBansByGroupId(groupId);
	}

	/**
	 * Deletes the message boards ban with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param banId the primary key of the message boards ban
	 * @return the message boards ban that was removed
	 * @throws PortalException if a message boards ban with the primary key could not be found
	 */
	@Override
	public com.liferay.message.boards.model.MBBan deleteMBBan(long banId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbBanLocalService.deleteMBBan(banId);
	}

	/**
	 * Deletes the message boards ban from the database. Also notifies the appropriate model listeners.
	 *
	 * @param mbBan the message boards ban
	 * @return the message boards ban that was removed
	 */
	@Override
	public com.liferay.message.boards.model.MBBan deleteMBBan(
		com.liferay.message.boards.model.MBBan mbBan) {

		return _mbBanLocalService.deleteMBBan(mbBan);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbBanLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _mbBanLocalService.dynamicQuery();
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

		return _mbBanLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.message.boards.model.impl.MBBanModelImpl</code>.
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

		return _mbBanLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.message.boards.model.impl.MBBanModelImpl</code>.
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

		return _mbBanLocalService.dynamicQuery(
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

		return _mbBanLocalService.dynamicQueryCount(dynamicQuery);
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

		return _mbBanLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public void expireBans() {
		_mbBanLocalService.expireBans();
	}

	@Override
	public com.liferay.message.boards.model.MBBan fetchMBBan(long banId) {
		return _mbBanLocalService.fetchMBBan(banId);
	}

	/**
	 * Returns the message boards ban matching the UUID and group.
	 *
	 * @param uuid the message boards ban's UUID
	 * @param groupId the primary key of the group
	 * @return the matching message boards ban, or <code>null</code> if a matching message boards ban could not be found
	 */
	@Override
	public com.liferay.message.boards.model.MBBan fetchMBBanByUuidAndGroupId(
		String uuid, long groupId) {

		return _mbBanLocalService.fetchMBBanByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _mbBanLocalService.getActionableDynamicQuery();
	}

	@Override
	public java.util.List<com.liferay.message.boards.model.MBBan> getBans(
		long groupId, int start, int end) {

		return _mbBanLocalService.getBans(groupId, start, end);
	}

	@Override
	public int getBansCount(long groupId) {
		return _mbBanLocalService.getBansCount(groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _mbBanLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _mbBanLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the message boards ban with the primary key.
	 *
	 * @param banId the primary key of the message boards ban
	 * @return the message boards ban
	 * @throws PortalException if a message boards ban with the primary key could not be found
	 */
	@Override
	public com.liferay.message.boards.model.MBBan getMBBan(long banId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbBanLocalService.getMBBan(banId);
	}

	/**
	 * Returns the message boards ban matching the UUID and group.
	 *
	 * @param uuid the message boards ban's UUID
	 * @param groupId the primary key of the group
	 * @return the matching message boards ban
	 * @throws PortalException if a matching message boards ban could not be found
	 */
	@Override
	public com.liferay.message.boards.model.MBBan getMBBanByUuidAndGroupId(
			String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbBanLocalService.getMBBanByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the message boards bans.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.message.boards.model.impl.MBBanModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards bans
	 * @param end the upper bound of the range of message boards bans (not inclusive)
	 * @return the range of message boards bans
	 */
	@Override
	public java.util.List<com.liferay.message.boards.model.MBBan> getMBBans(
		int start, int end) {

		return _mbBanLocalService.getMBBans(start, end);
	}

	/**
	 * Returns all the message boards bans matching the UUID and company.
	 *
	 * @param uuid the UUID of the message boards bans
	 * @param companyId the primary key of the company
	 * @return the matching message boards bans, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.liferay.message.boards.model.MBBan>
		getMBBansByUuidAndCompanyId(String uuid, long companyId) {

		return _mbBanLocalService.getMBBansByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of message boards bans matching the UUID and company.
	 *
	 * @param uuid the UUID of the message boards bans
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of message boards bans
	 * @param end the upper bound of the range of message boards bans (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching message boards bans, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.liferay.message.boards.model.MBBan>
		getMBBansByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.message.boards.model.MBBan> orderByComparator) {

		return _mbBanLocalService.getMBBansByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of message boards bans.
	 *
	 * @return the number of message boards bans
	 */
	@Override
	public int getMBBansCount() {
		return _mbBanLocalService.getMBBansCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _mbBanLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbBanLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public boolean hasBan(long groupId, long banUserId) {
		return _mbBanLocalService.hasBan(groupId, banUserId);
	}

	/**
	 * Updates the message boards ban in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param mbBan the message boards ban
	 * @return the message boards ban that was updated
	 */
	@Override
	public com.liferay.message.boards.model.MBBan updateMBBan(
		com.liferay.message.boards.model.MBBan mbBan) {

		return _mbBanLocalService.updateMBBan(mbBan);
	}

	@Override
	public MBBanLocalService getWrappedService() {
		return _mbBanLocalService;
	}

	@Override
	public void setWrappedService(MBBanLocalService mbBanLocalService) {
		_mbBanLocalService = mbBanLocalService;
	}

	private MBBanLocalService _mbBanLocalService;

}