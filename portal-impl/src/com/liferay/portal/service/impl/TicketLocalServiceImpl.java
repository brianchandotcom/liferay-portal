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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.service.base.TicketLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * @author Mika Koivisto
 */
public class TicketLocalServiceImpl extends TicketLocalServiceBaseImpl {

	@Override
	public Ticket addDistinctTicket(
		long companyId, String className, long classPK, int type,
		String extraInfo, Date expirationDate, ServiceContext serviceContext) {

		ticketPersistence.removeByC_C_C_T(
			companyId, _classNameLocalService.getClassNameId(className),
			classPK, type);

		return addTicket(
			companyId, className, classPK, type, extraInfo, expirationDate,
			serviceContext);
	}

	@Override
	public Ticket addTicket(
		long companyId, String className, long classPK, int type,
		String extraInfo, Date expirationDate, ServiceContext serviceContext) {

		long ticketId = counterLocalService.increment();

		Ticket ticket = ticketPersistence.create(ticketId);

		ticket.setCompanyId(companyId);
		ticket.setCreateDate(new Date());
		ticket.setClassNameId(_classNameLocalService.getClassNameId(className));
		ticket.setClassPK(classPK);
		ticket.setKey(PortalUUIDUtil.generate());
		ticket.setType(type);
		ticket.setExtraInfo(extraInfo);
		ticket.setExpirationDate(expirationDate);

		return ticketPersistence.update(ticket);
	}

	@Override
	public void deleteTickets(long companyId, String className, long classPK) {
		ticketPersistence.removeByC_C_C(
			companyId, _classNameLocalService.getClassNameId(className),
			classPK);
	}

	@Override
	public Ticket fetchTicket(String key) {
		return ticketPersistence.fetchByKey(key);
	}

	@Override
	public Ticket getTicket(String key) throws PortalException {
		return ticketPersistence.findByKey(key);
	}

	@Override
	public List<Ticket> getTickets(
		long companyId, String className, long classPK) {

		return ticketPersistence.findByC_C_C(
			companyId, _classNameLocalService.getClassNameId(className),
			classPK);
	}

	@Override
	public List<Ticket> getTickets(
		long companyId, String className, long classPK, int type) {

		return ticketPersistence.findByC_C_C_T(
			companyId, _classNameLocalService.getClassNameId(className),
			classPK, type);
	}

	@Override
	public List<Ticket> getTickets(String className, long classPK, int type) {
		return ticketPersistence.findByC_C_T(
			_classNameLocalService.getClassNameId(className), classPK, type);
	}

	@Override
	public Ticket updateTicket(
			long ticketId, String className, long classPK, int type,
			String extraInfo, Date expirationDate)
		throws PortalException {

		Ticket ticket = ticketPersistence.findByPrimaryKey(ticketId);

		ticket.setClassNameId(_classNameLocalService.getClassNameId(className));
		ticket.setClassPK(classPK);
		ticket.setType(type);
		ticket.setExtraInfo(extraInfo);
		ticket.setExpirationDate(expirationDate);

		return ticketPersistence.update(ticket);
	}

	@BeanReference(type = ClassNameLocalService.class)
	private ClassNameLocalService _classNameLocalService;

}