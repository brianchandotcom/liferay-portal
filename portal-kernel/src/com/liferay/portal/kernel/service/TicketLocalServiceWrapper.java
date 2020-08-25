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

package com.liferay.portal.kernel.service;

/**
 * Provides a wrapper for {@link TicketLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see TicketLocalService
 * @generated
 */
public class TicketLocalServiceWrapper
	implements ServiceWrapper<TicketLocalService>, TicketLocalService {

	public TicketLocalServiceWrapper(TicketLocalService ticketLocalService) {
		_ticketLocalService = ticketLocalService;
	}

	@Override
	public com.liferay.portal.kernel.model.Ticket addDistinctTicket(
		long companyId, java.lang.String className, long classPK, int type,
		java.lang.String extraInfo, java.util.Date expirationDate,
		ServiceContext serviceContext) {

		return _ticketLocalService.addDistinctTicket(
			companyId, className, classPK, type, extraInfo, expirationDate,
			serviceContext);
	}

	@Override
	public com.liferay.portal.kernel.model.Ticket addTicket(
		long companyId, java.lang.String className, long classPK, int type,
		java.lang.String extraInfo, java.util.Date expirationDate,
		ServiceContext serviceContext) {

		return _ticketLocalService.addTicket(
			companyId, className, classPK, type, extraInfo, expirationDate,
			serviceContext);
	}

	/**
	 * Adds the ticket to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect TicketLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ticket the ticket
	 * @return the ticket that was added
	 */
	@Override
	public com.liferay.portal.kernel.model.Ticket addTicket(
		com.liferay.portal.kernel.model.Ticket ticket) {

		return _ticketLocalService.addTicket(ticket);
	}

	/**
	 * Creates a new ticket with the primary key. Does not add the ticket to the database.
	 *
	 * @param ticketId the primary key for the new ticket
	 * @return the new ticket
	 */
	@Override
	public com.liferay.portal.kernel.model.Ticket createTicket(long ticketId) {
		return _ticketLocalService.createTicket(ticketId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ticketLocalService.deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the ticket with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect TicketLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ticketId the primary key of the ticket
	 * @return the ticket that was removed
	 * @throws PortalException if a ticket with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.kernel.model.Ticket deleteTicket(long ticketId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ticketLocalService.deleteTicket(ticketId);
	}

	/**
	 * Deletes the ticket from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect TicketLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ticket the ticket
	 * @return the ticket that was removed
	 */
	@Override
	public com.liferay.portal.kernel.model.Ticket deleteTicket(
		com.liferay.portal.kernel.model.Ticket ticket) {

		return _ticketLocalService.deleteTicket(ticket);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _ticketLocalService.dynamicQuery();
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

		return _ticketLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.TicketModelImpl</code>.
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

		return _ticketLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.TicketModelImpl</code>.
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

		return _ticketLocalService.dynamicQuery(
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

		return _ticketLocalService.dynamicQueryCount(dynamicQuery);
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

		return _ticketLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.kernel.model.Ticket fetchTicket(long ticketId) {
		return _ticketLocalService.fetchTicket(ticketId);
	}

	@Override
	public com.liferay.portal.kernel.model.Ticket fetchTicket(
		java.lang.String key) {

		return _ticketLocalService.fetchTicket(key);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _ticketLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _ticketLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _ticketLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ticketLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the ticket with the primary key.
	 *
	 * @param ticketId the primary key of the ticket
	 * @return the ticket
	 * @throws PortalException if a ticket with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.kernel.model.Ticket getTicket(long ticketId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ticketLocalService.getTicket(ticketId);
	}

	@Override
	public com.liferay.portal.kernel.model.Ticket getTicket(
			java.lang.String key)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ticketLocalService.getTicket(key);
	}

	/**
	 * Returns a range of all the tickets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.TicketModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of tickets
	 * @param end the upper bound of the range of tickets (not inclusive)
	 * @return the range of tickets
	 */
	@Override
	public java.util.List<com.liferay.portal.kernel.model.Ticket> getTickets(
		int start, int end) {

		return _ticketLocalService.getTickets(start, end);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Ticket> getTickets(
		long companyId, java.lang.String className, long classPK, int type) {

		return _ticketLocalService.getTickets(
			companyId, className, classPK, type);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Ticket> getTickets(
		java.lang.String className, long classPK, int type) {

		return _ticketLocalService.getTickets(className, classPK, type);
	}

	/**
	 * Returns the number of tickets.
	 *
	 * @return the number of tickets
	 */
	@Override
	public int getTicketsCount() {
		return _ticketLocalService.getTicketsCount();
	}

	@Override
	public com.liferay.portal.kernel.model.Ticket updateTicket(
			long ticketId, java.lang.String className, long classPK, int type,
			java.lang.String extraInfo, java.util.Date expirationDate)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ticketLocalService.updateTicket(
			ticketId, className, classPK, type, extraInfo, expirationDate);
	}

	/**
	 * Updates the ticket in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect TicketLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ticket the ticket
	 * @return the ticket that was updated
	 */
	@Override
	public com.liferay.portal.kernel.model.Ticket updateTicket(
		com.liferay.portal.kernel.model.Ticket ticket) {

		return _ticketLocalService.updateTicket(ticket);
	}

	@Override
	public TicketLocalService getWrappedService() {
		return _ticketLocalService;
	}

	@Override
	public void setWrappedService(TicketLocalService ticketLocalService) {
		_ticketLocalService = ticketLocalService;
	}

	private TicketLocalService _ticketLocalService;

}