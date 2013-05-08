/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.announcements.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AnnouncementsDeliveryLocalService}.
 *
 * @author    Brian Wing Shun Chan
 * @see       AnnouncementsDeliveryLocalService
 * @generated
 */
public class AnnouncementsDeliveryLocalServiceWrapper
	implements AnnouncementsDeliveryLocalService,
		ServiceWrapper<AnnouncementsDeliveryLocalService> {
	public AnnouncementsDeliveryLocalServiceWrapper(
		AnnouncementsDeliveryLocalService announcementsDeliveryLocalService) {
		_announcementsDeliveryLocalService = announcementsDeliveryLocalService;
	}

	/**
	* Adds the announcements delivery to the database. Also notifies the appropriate model listeners.
	*
	* @param announcementsDelivery the announcements delivery
	* @return the announcements delivery that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.announcements.model.AnnouncementsDelivery addAnnouncementsDelivery(
		com.liferay.portlet.announcements.model.AnnouncementsDelivery announcementsDelivery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _announcementsDeliveryLocalService.addAnnouncementsDelivery(announcementsDelivery);
	}

	/**
	* Creates a new announcements delivery with the primary key. Does not add the announcements delivery to the database.
	*
	* @param deliveryId the primary key for the new announcements delivery
	* @return the new announcements delivery
	*/
	public com.liferay.portlet.announcements.model.AnnouncementsDelivery createAnnouncementsDelivery(
		long deliveryId) {
		return _announcementsDeliveryLocalService.createAnnouncementsDelivery(deliveryId);
	}

	/**
	* Deletes the announcements delivery with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param deliveryId the primary key of the announcements delivery
	* @return the announcements delivery that was removed
	* @throws PortalException if a announcements delivery with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.announcements.model.AnnouncementsDelivery deleteAnnouncementsDelivery(
		long deliveryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _announcementsDeliveryLocalService.deleteAnnouncementsDelivery(deliveryId);
	}

	/**
	* Deletes the announcements delivery from the database. Also notifies the appropriate model listeners.
	*
	* @param announcementsDelivery the announcements delivery
	* @return the announcements delivery that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.announcements.model.AnnouncementsDelivery deleteAnnouncementsDelivery(
		com.liferay.portlet.announcements.model.AnnouncementsDelivery announcementsDelivery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _announcementsDeliveryLocalService.deleteAnnouncementsDelivery(announcementsDelivery);
	}

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _announcementsDeliveryLocalService.dynamicQuery();
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
		return _announcementsDeliveryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.announcements.model.impl.AnnouncementsDeliveryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _announcementsDeliveryLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.announcements.model.impl.AnnouncementsDeliveryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _announcementsDeliveryLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
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
		return _announcementsDeliveryLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.announcements.model.AnnouncementsDelivery fetchAnnouncementsDelivery(
		long deliveryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _announcementsDeliveryLocalService.fetchAnnouncementsDelivery(deliveryId);
	}

	/**
	* Returns the announcements delivery with the primary key.
	*
	* @param deliveryId the primary key of the announcements delivery
	* @return the announcements delivery
	* @throws PortalException if a announcements delivery with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.announcements.model.AnnouncementsDelivery getAnnouncementsDelivery(
		long deliveryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _announcementsDeliveryLocalService.getAnnouncementsDelivery(deliveryId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _announcementsDeliveryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the announcements deliveries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.announcements.model.impl.AnnouncementsDeliveryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of announcements deliveries
	* @param end the upper bound of the range of announcements deliveries (not inclusive)
	* @return the range of announcements deliveries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> getAnnouncementsDeliveries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _announcementsDeliveryLocalService.getAnnouncementsDeliveries(start,
			end);
	}

	/**
	* Returns the number of announcements deliveries.
	*
	* @return the number of announcements deliveries
	* @throws SystemException if a system exception occurred
	*/
	public int getAnnouncementsDeliveriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _announcementsDeliveryLocalService.getAnnouncementsDeliveriesCount();
	}

	/**
	* Updates the announcements delivery in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param announcementsDelivery the announcements delivery
	* @return the announcements delivery that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.announcements.model.AnnouncementsDelivery updateAnnouncementsDelivery(
		com.liferay.portlet.announcements.model.AnnouncementsDelivery announcementsDelivery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _announcementsDeliveryLocalService.updateAnnouncementsDelivery(announcementsDelivery);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _announcementsDeliveryLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_announcementsDeliveryLocalService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portlet.announcements.model.AnnouncementsDelivery addUserDelivery(
		long userId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _announcementsDeliveryLocalService.addUserDelivery(userId, type);
	}

	public void deleteDeliveries(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_announcementsDeliveryLocalService.deleteDeliveries(userId);
	}

	public void deleteDelivery(
		com.liferay.portlet.announcements.model.AnnouncementsDelivery delivery)
		throws com.liferay.portal.kernel.exception.SystemException {
		_announcementsDeliveryLocalService.deleteDelivery(delivery);
	}

	public void deleteDelivery(long deliveryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_announcementsDeliveryLocalService.deleteDelivery(deliveryId);
	}

	public void deleteDelivery(long userId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException {
		_announcementsDeliveryLocalService.deleteDelivery(userId, type);
	}

	public com.liferay.portlet.announcements.model.AnnouncementsDelivery getDelivery(
		long deliveryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _announcementsDeliveryLocalService.getDelivery(deliveryId);
	}

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> getUserDeliveries(
		long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _announcementsDeliveryLocalService.getUserDeliveries(userId);
	}

	public com.liferay.portlet.announcements.model.AnnouncementsDelivery getUserDelivery(
		long userId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _announcementsDeliveryLocalService.getUserDelivery(userId, type);
	}

	public com.liferay.portlet.announcements.model.AnnouncementsDelivery updateDelivery(
		long userId, java.lang.String type, boolean email, boolean sms,
		boolean website)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _announcementsDeliveryLocalService.updateDelivery(userId, type,
			email, sms, website);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public AnnouncementsDeliveryLocalService getWrappedAnnouncementsDeliveryLocalService() {
		return _announcementsDeliveryLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedAnnouncementsDeliveryLocalService(
		AnnouncementsDeliveryLocalService announcementsDeliveryLocalService) {
		_announcementsDeliveryLocalService = announcementsDeliveryLocalService;
	}

	public AnnouncementsDeliveryLocalService getWrappedService() {
		return _announcementsDeliveryLocalService;
	}

	public void setWrappedService(
		AnnouncementsDeliveryLocalService announcementsDeliveryLocalService) {
		_announcementsDeliveryLocalService = announcementsDeliveryLocalService;
	}

	private AnnouncementsDeliveryLocalService _announcementsDeliveryLocalService;
}