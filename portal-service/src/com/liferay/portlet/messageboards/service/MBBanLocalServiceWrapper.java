/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link MBBanLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBBanLocalService
 * @generated
 */
public class MBBanLocalServiceWrapper implements MBBanLocalService,
	ServiceWrapper<MBBanLocalService> {
	public MBBanLocalServiceWrapper(MBBanLocalService mbBanLocalService) {
		_mbBanLocalService = mbBanLocalService;
	}

	/**
	* Adds the message boards ban to the database. Also notifies the appropriate model listeners.
	*
	* @param mbBan the message boards ban
	* @return the message boards ban that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBBan addMBBan(
		com.liferay.portlet.messageboards.model.MBBan mbBan)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbBanLocalService.addMBBan(mbBan);
	}

	/**
	* Creates a new message boards ban with the primary key. Does not add the message boards ban to the database.
	*
	* @param banId the primary key for the new message boards ban
	* @return the new message boards ban
	*/
	public com.liferay.portlet.messageboards.model.MBBan createMBBan(long banId) {
		return _mbBanLocalService.createMBBan(banId);
	}

	/**
	* Deletes the message boards ban with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param banId the primary key of the message boards ban
	* @throws PortalException if a message boards ban with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteMBBan(long banId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbBanLocalService.deleteMBBan(banId);
	}

	/**
	* Deletes the message boards ban from the database. Also notifies the appropriate model listeners.
	*
	* @param mbBan the message boards ban
	* @throws SystemException if a system exception occurred
	*/
	public void deleteMBBan(com.liferay.portlet.messageboards.model.MBBan mbBan)
		throws com.liferay.portal.kernel.exception.SystemException {
		_mbBanLocalService.deleteMBBan(mbBan);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbBanLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _mbBanLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbBanLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbBanLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the message boards ban with the primary key.
	*
	* @param banId the primary key of the message boards ban
	* @return the message boards ban
	* @throws PortalException if a message boards ban with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBBan getMBBan(long banId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbBanLocalService.getMBBan(banId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbBanLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the message boards bans.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of message boards bans
	* @param end the upper bound of the range of message boards bans (not inclusive)
	* @return the range of message boards bans
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBBan> getMBBans(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbBanLocalService.getMBBans(start, end);
	}

	/**
	* Returns the number of message boards bans.
	*
	* @return the number of message boards bans
	* @throws SystemException if a system exception occurred
	*/
	public int getMBBansCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbBanLocalService.getMBBansCount();
	}

	/**
	* Updates the message boards ban in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param mbBan the message boards ban
	* @return the message boards ban that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBBan updateMBBan(
		com.liferay.portlet.messageboards.model.MBBan mbBan)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbBanLocalService.updateMBBan(mbBan);
	}

	/**
	* Updates the message boards ban in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param mbBan the message boards ban
	* @param merge whether to merge the message boards ban with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the message boards ban that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBBan updateMBBan(
		com.liferay.portlet.messageboards.model.MBBan mbBan, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbBanLocalService.updateMBBan(mbBan, merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _mbBanLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_mbBanLocalService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portlet.messageboards.model.MBBan addBan(long userId,
		long banUserId, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbBanLocalService.addBan(userId, banUserId, serviceContext);
	}

	public void checkBan(long groupId, long banUserId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbBanLocalService.checkBan(groupId, banUserId);
	}

	public void deleteBan(long banId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbBanLocalService.deleteBan(banId);
	}

	public void deleteBan(long banUserId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.SystemException {
		_mbBanLocalService.deleteBan(banUserId, serviceContext);
	}

	public void deleteBan(com.liferay.portlet.messageboards.model.MBBan ban)
		throws com.liferay.portal.kernel.exception.SystemException {
		_mbBanLocalService.deleteBan(ban);
	}

	public void deleteBansByBanUserId(long banUserId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_mbBanLocalService.deleteBansByBanUserId(banUserId);
	}

	public void deleteBansByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_mbBanLocalService.deleteBansByGroupId(groupId);
	}

	public void expireBans()
		throws com.liferay.portal.kernel.exception.SystemException {
		_mbBanLocalService.expireBans();
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBBan> getBans(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbBanLocalService.getBans(groupId, start, end);
	}

	public int getBansCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbBanLocalService.getBansCount(groupId);
	}

	public boolean hasBan(long groupId, long banUserId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbBanLocalService.hasBan(groupId, banUserId);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public MBBanLocalService getWrappedMBBanLocalService() {
		return _mbBanLocalService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedMBBanLocalService(MBBanLocalService mbBanLocalService) {
		_mbBanLocalService = mbBanLocalService;
	}

	public MBBanLocalService getWrappedService() {
		return _mbBanLocalService;
	}

	public void setWrappedService(MBBanLocalService mbBanLocalService) {
		_mbBanLocalService = mbBanLocalService;
	}

	private MBBanLocalService _mbBanLocalService;
}