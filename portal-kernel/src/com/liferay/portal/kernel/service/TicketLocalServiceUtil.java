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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * Provides the local service utility for Ticket. This utility wraps
 * <code>com.liferay.portal.service.impl.TicketLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see TicketLocalService
 * @generated
 */
public class TicketLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.TicketLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.kernel.model.Ticket addDistinctTicket(
		long companyId, String className, long classPK, int type,
		String extraInfo, java.util.Date expirationDate,
		ServiceContext serviceContext) {

		return getService().addDistinctTicket(
			companyId, className, classPK, type, extraInfo, expirationDate,
			serviceContext);
	}

	public static com.liferay.portal.kernel.model.Ticket addTicket(
		long companyId, String className, long classPK, int type,
		String extraInfo, java.util.Date expirationDate,
		ServiceContext serviceContext) {

		return getService().addTicket(
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
	public static com.liferay.portal.kernel.model.Ticket addTicket(
		com.liferay.portal.kernel.model.Ticket ticket) {

		return getService().addTicket(ticket);
	}

	/**
	 * Creates a new ticket with the primary key. Does not add the ticket to the database.
	 *
	 * @param ticketId the primary key for the new ticket
	 * @return the new ticket
	 */
	public static com.liferay.portal.kernel.model.Ticket createTicket(
		long ticketId) {

		return getService().createTicket(ticketId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
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
	public static com.liferay.portal.kernel.model.Ticket deleteTicket(
			long ticketId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteTicket(ticketId);
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
	public static com.liferay.portal.kernel.model.Ticket deleteTicket(
		com.liferay.portal.kernel.model.Ticket ticket) {

		return getService().deleteTicket(ticket);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQuery(dynamicQuery);
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
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.portal.kernel.model.Ticket fetchTicket(
		long ticketId) {

		return getService().fetchTicket(ticketId);
	}

	public static com.liferay.portal.kernel.model.Ticket fetchTicket(
		String key) {

		return getService().fetchTicket(key);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the ticket with the primary key.
	 *
	 * @param ticketId the primary key of the ticket
	 * @return the ticket
	 * @throws PortalException if a ticket with the primary key could not be found
	 */
	public static com.liferay.portal.kernel.model.Ticket getTicket(
			long ticketId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getTicket(ticketId);
	}

	public static com.liferay.portal.kernel.model.Ticket getTicket(String key)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getTicket(key);
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
	public static java.util.List<com.liferay.portal.kernel.model.Ticket>
		getTickets(int start, int end) {

		return getService().getTickets(start, end);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Ticket>
		getTickets(long companyId, String className, long classPK, int type) {

		return getService().getTickets(companyId, className, classPK, type);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Ticket>
		getTickets(String className, long classPK, int type) {

		return getService().getTickets(className, classPK, type);
	}

	/**
	 * Returns the number of tickets.
	 *
	 * @return the number of tickets
	 */
	public static int getTicketsCount() {
		return getService().getTicketsCount();
	}

	public static com.liferay.portal.kernel.model.Ticket updateTicket(
			long ticketId, String className, long classPK, int type,
			String extraInfo, java.util.Date expirationDate)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateTicket(
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
	public static com.liferay.portal.kernel.model.Ticket updateTicket(
		com.liferay.portal.kernel.model.Ticket ticket) {

		return getService().updateTicket(ticket);
	}

	public static TicketLocalService getService() {
		if (_service == null) {
			_service = (TicketLocalService)PortalBeanLocatorUtil.locate(
				TicketLocalService.class.getName());
		}

		return _service;
	}

	private static TicketLocalService _service;

}