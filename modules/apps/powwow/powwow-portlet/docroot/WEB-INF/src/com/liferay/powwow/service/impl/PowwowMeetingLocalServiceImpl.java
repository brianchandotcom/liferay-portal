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

package com.liferay.powwow.service.impl;

import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.service.CalendarBookingLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Junction;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.powwow.model.PowwowMeeting;
import com.liferay.powwow.model.PowwowMeetingConstants;
import com.liferay.powwow.model.PowwowParticipant;
import com.liferay.powwow.model.PowwowParticipantConstants;
import com.liferay.powwow.model.PowwowServer;
import com.liferay.powwow.provider.PowwowServiceProviderUtil;
import com.liferay.powwow.service.base.PowwowMeetingLocalServiceBaseImpl;
import com.liferay.powwow.util.PowwowUtil;

import java.io.Serializable;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Shinn Lok
 * @author Marco Calderon
 */
public class PowwowMeetingLocalServiceImpl
	extends PowwowMeetingLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public PowwowMeeting addPowwowMeeting(
			long userId, long groupId, long powwowServerId, String name,
			String description, String providerType,
			Map<String, Serializable> providerTypeMetadataMap,
			String languageId, long calendarBookingId, int status,
			List<PowwowParticipant> powwowParticipants,
			ServiceContext serviceContext)
		throws PortalException {

		// Powwow meeting

		User user = userLocalService.getUser(userId);
		Date now = new Date();

		long powwowMeetingId = counterLocalService.increment();

		PowwowMeeting powwowMeeting = powwowMeetingPersistence.create(
			powwowMeetingId);

		powwowMeeting.setGroupId(groupId);
		powwowMeeting.setCompanyId(user.getCompanyId());
		powwowMeeting.setUserId(user.getUserId());
		powwowMeeting.setUserName(user.getFullName());
		powwowMeeting.setCreateDate(serviceContext.getCreateDate(now));
		powwowMeeting.setModifiedDate(serviceContext.getModifiedDate(now));
		powwowMeeting.setPowwowServerId(powwowServerId);
		powwowMeeting.setName(name);
		powwowMeeting.setDescription(description);
		powwowMeeting.setProviderType(providerType);
		powwowMeeting.setProviderTypeMetadata(
			JSONFactoryUtil.serialize(providerTypeMetadataMap));
		powwowMeeting.setLanguageId(languageId);
		powwowMeeting.setCalendarBookingId(calendarBookingId);
		powwowMeeting.setStatus(status);

		powwowMeetingPersistence.update(powwowMeeting);

		// Resources

		resourceLocalService.addModelResources(powwowMeeting, serviceContext);

		// Powwow participants

		updatePowwowParticipants(
			userId, groupId, powwowMeetingId, powwowParticipants,
			serviceContext);

		return powwowMeeting;
	}

	@Override
	public void checkPowwowMeetings() throws PortalException {
		List<PowwowMeeting> powwowMeetings = getPowwowMeetings(
			PowwowMeetingConstants.STATUS_IN_PROGRESS);

		for (PowwowMeeting powwowMeeting : powwowMeetings) {
			PowwowServer powwowServer =
				powwowServerLocalService.getPowwowServer(
					powwowMeeting.getPowwowServerId());

			if (!PowwowServiceProviderUtil.isServerActive(powwowServer)) {
				continue;
			}

			if (!PowwowServiceProviderUtil.isPowwowMeetingRunning(
					powwowMeeting.getPowwowMeetingId())) {

				updateStatus(
					powwowMeeting.getPowwowMeetingId(),
					PowwowMeetingConstants.STATUS_COMPLETED);
			}
		}
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	public PowwowMeeting deletePowwowMeeting(long powwowMeetingId)
		throws PortalException {

		PowwowMeeting powwowMeeting = powwowMeetingPersistence.findByPrimaryKey(
			powwowMeetingId);

		return deletePowwowMeeting(powwowMeeting);
	}

	@Override
	public PowwowMeeting deletePowwowMeeting(PowwowMeeting powwowMeeting)
		throws PortalException {

		// Powwow meeting

		powwowMeetingPersistence.remove(powwowMeeting);

		// Resources

		resourceLocalService.deleteResource(
			powwowMeeting, ResourceConstants.SCOPE_INDIVIDUAL);

		// Powwow participants

		List<PowwowParticipant> powwowParticipants =
			powwowParticipantLocalService.getPowwowParticipants(
				powwowMeeting.getPowwowMeetingId());

		for (PowwowParticipant powwowParticipant : powwowParticipants) {
			powwowParticipantLocalService.deletePowwowParticipant(
				powwowParticipant);
		}

		// Calendar bookings

		CalendarBooking calendarBooking =
			CalendarBookingLocalServiceUtil.fetchCalendarBooking(
				powwowMeeting.getCalendarBookingId());

		if (calendarBooking != null) {
			CalendarBookingLocalServiceUtil.deleteCalendarBooking(
				powwowMeeting.getCalendarBookingId());
		}

		return powwowMeeting;
	}

	@Override
	public List<PowwowMeeting> getParticipantPowwowMeetings(
		long userId, int[] statuses, int start, int end,
		OrderByComparator orderByComparator) {

		return powwowMeetingFinder.findByU_S(
			userId, statuses, start, end, orderByComparator);
	}

	@Override
	public int getParticipantPowwowMeetingsCount(long userId, int[] statuses) {
		return powwowMeetingFinder.countByU_S(userId, statuses);
	}

	@Override
	public PowwowMeeting getPowwowMeeting(long powwowMeetingId)
		throws PortalException {

		return powwowMeetingPersistence.findByPrimaryKey(powwowMeetingId);
	}

	@Override
	public List<PowwowMeeting> getPowwowMeetings(int status) {
		return powwowMeetingPersistence.findByStatus(status);
	}

	@Override
	public List<PowwowMeeting> getPowwowMeetings(
		long groupId, int start, int end, OrderByComparator obc) {

		return powwowMeetingPersistence.findByGroupId(groupId, start, end, obc);
	}

	@Override
	public List<PowwowMeeting> getPowwowMeetings(
		long groupId, long userId, String name, String description, int status,
		boolean andSearch, int start, int end, String orderByField,
		String orderByType) {

		DynamicQuery dynamicQuery = buildDynamicQuery(
			groupId, userId, name, description, status, andSearch);

		if (orderByType.equals("desc")) {
			dynamicQuery.addOrder(OrderFactoryUtil.desc(orderByField));
		}
		else {
			dynamicQuery.addOrder(OrderFactoryUtil.asc(orderByField));
		}

		return dynamicQuery(dynamicQuery, start, end);
	}

	@Override
	public int getPowwowMeetingsCount(long groupId) {
		return powwowMeetingPersistence.countByGroupId(groupId);
	}

	@Override
	public int getPowwowMeetingsCount(long powwowServerId, int status) {
		return powwowMeetingPersistence.countByPSI_S(powwowServerId, status);
	}

	@Override
	public int getPowwowMeetingsCount(
		long groupId, long userId, String name, String description, int status,
		boolean andSearch) {

		DynamicQuery dynamicQuery = buildDynamicQuery(
			groupId, userId, name, description, status, andSearch);

		return (int)dynamicQueryCount(dynamicQuery);
	}

	@Override
	public int getUserPowwowMeetingsCount(long userId, int status) {
		return powwowMeetingPersistence.countByU_S(userId, status);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public PowwowMeeting updatePowwowMeeting(
			long powwowMeetingId, long powwowServerId, String name,
			String description, String providerType,
			Map<String, Serializable> providerTypeMetadataMap,
			String languageId, long calendarBookingId, int status,
			List<PowwowParticipant> powwowParticipants,
			ServiceContext serviceContext)
		throws PortalException {

		// Powwow meeting

		PowwowMeeting powwowMeeting = powwowMeetingPersistence.findByPrimaryKey(
			powwowMeetingId);

		powwowMeeting.setModifiedDate(serviceContext.getModifiedDate(null));

		if (powwowServerId > 0) {
			powwowMeeting.setPowwowServerId(powwowServerId);
		}

		powwowMeeting.setName(name);
		powwowMeeting.setDescription(description);
		powwowMeeting.setProviderType(providerType);
		powwowMeeting.setProviderTypeMetadata(
			JSONFactoryUtil.serialize(providerTypeMetadataMap));
		powwowMeeting.setLanguageId(languageId);
		powwowMeeting.setCalendarBookingId(calendarBookingId);
		powwowMeeting.setStatus(status);

		// Powwow participants

		updatePowwowParticipants(
			powwowMeeting.getUserId(), powwowMeeting.getGroupId(),
			powwowMeetingId, powwowParticipants, serviceContext);

		return powwowMeetingPersistence.update(powwowMeeting);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public PowwowMeeting updateStatus(long powwowMeetingId, int status)
		throws PortalException {

		PowwowMeeting powwowMeeting = powwowMeetingPersistence.findByPrimaryKey(
			powwowMeetingId);

		powwowMeeting.setStatus(status);

		powwowMeetingPersistence.update(powwowMeeting);

		return powwowMeeting;
	}

	protected DynamicQuery buildDynamicQuery(
		long groupId, long userId, String name, String description, int status,
		boolean andSearch) {

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			PowwowMeeting.class, getClassLoader());

		if (groupId > 0) {
			Property property = PropertyFactoryUtil.forName("groupId");

			dynamicQuery.add(property.eq(groupId));
		}

		if (userId > 0) {
			Property property = PropertyFactoryUtil.forName("userId");

			dynamicQuery.add(property.eq(userId));
		}

		if (status != PowwowMeetingConstants.STATUS_ANY) {
			Property property = PropertyFactoryUtil.forName("status");

			dynamicQuery.add(property.eq(status));
		}

		Junction junction = null;

		if (andSearch) {
			junction = RestrictionsFactoryUtil.conjunction();
		}
		else {
			junction = RestrictionsFactoryUtil.disjunction();
		}

		if (Validator.isNotNull(name)) {
			Property property = PropertyFactoryUtil.forName("name");

			String value = StringUtil.quote(name, StringPool.PERCENT);

			junction.add(property.like(value));
		}

		if (Validator.isNotNull(description)) {
			Property property = PropertyFactoryUtil.forName("description");

			String value = StringUtil.quote(description, StringPool.PERCENT);

			junction.add(property.like(value));
		}

		dynamicQuery.add(junction);

		return dynamicQuery;
	}

	protected void updatePowwowParticipants(
			long userId, long groupId, long powwowMeetingId,
			List<PowwowParticipant> powwowParticipants,
			ServiceContext serviceContext)
		throws PortalException {

		Set<Long> powwowParticipantIds = new HashSet<>();

		for (PowwowParticipant powwowParticipant : powwowParticipants) {
			long powwowParticipantId =
				powwowParticipant.getPowwowParticipantId();

			if (powwowParticipantId <= 0) {
				powwowParticipant =
					powwowParticipantLocalService.addPowwowParticipant(
						userId, groupId, powwowMeetingId,
						powwowParticipant.getName(),
						powwowParticipant.getParticipantUserId(),
						powwowParticipant.getEmailAddress(),
						powwowParticipant.getType(),
						PowwowParticipantConstants.STATUS_DEFAULT,
						new ServiceContext());

				powwowParticipantId =
					powwowParticipant.getPowwowParticipantId();
			}
			else {
				powwowParticipantLocalService.updatePowwowParticipant(
					powwowParticipantId, powwowMeetingId,
					powwowParticipant.getName(),
					powwowParticipant.getParticipantUserId(),
					powwowParticipant.getEmailAddress(),
					powwowParticipant.getType(), powwowParticipant.getStatus(),
					new ServiceContext());
			}

			powwowParticipantIds.add(powwowParticipantId);
		}

		powwowParticipants =
			powwowParticipantLocalService.getPowwowParticipants(
				powwowMeetingId);

		for (PowwowParticipant powwowParticipant : powwowParticipants) {
			if (!powwowParticipantIds.contains(
					powwowParticipant.getPowwowParticipantId())) {

				powwowParticipantLocalService.deletePowwowParticipant(
					powwowParticipant);
			}
		}

		try {
			PowwowUtil.sendNotifications(powwowMeetingId, serviceContext);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

}