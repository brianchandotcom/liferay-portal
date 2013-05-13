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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.Ticket;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.TicketLocalServiceBaseImpl;
import com.liferay.portal.util.PortalUtil;

import java.util.Date;

/**
 * @author Mika Koivisto
 */
public class TicketLocalServiceImpl extends TicketLocalServiceBaseImpl {

	@Override
	public Ticket addTicket(
			long companyId, String className, long classPK, int type,
			String extraInfo, Date expirationDate,
			ServiceContext serviceContext)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);
		Date now = new Date();

		long ticketId = counterLocalService.increment();

		Ticket ticket = ticketPersistence.create(ticketId);

		ticket.setCompanyId(companyId);
		ticket.setCreateDate(now);
		ticket.setClassNameId(classNameId);
		ticket.setClassPK(classPK);
		ticket.setKey(PortalUUIDUtil.generate());
		ticket.setType(type);
		ticket.setExtraInfo(extraInfo);
		ticket.setExpirationDate(expirationDate);

		ticketPersistence.update(ticket);

		return ticket;
	}

	@Override
	public Ticket fetchTicket(String key) throws SystemException {
		return ticketPersistence.fetchByKey(key);
	}

	@Override
	public Ticket getTicket(String key)
		throws PortalException, SystemException {

		return ticketPersistence.findByKey(key);
	}

}