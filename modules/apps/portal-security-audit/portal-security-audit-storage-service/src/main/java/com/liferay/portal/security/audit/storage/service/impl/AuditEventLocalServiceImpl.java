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

package com.liferay.portal.security.audit.storage.service.impl;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Junction;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.audit.storage.comparator.AuditEventCreateDateComparator;
import com.liferay.portal.security.audit.storage.model.AuditEvent;
import com.liferay.portal.security.audit.storage.service.base.AuditEventLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.portal.security.audit.storage.model.AuditEvent",
	service = AopService.class
)
@CTAware
public class AuditEventLocalServiceImpl extends AuditEventLocalServiceBaseImpl {

	@Override
	public AuditEvent addAuditEvent(AuditMessage auditMessage) {
		long auditEventId = counterLocalService.increment();

		AuditEvent auditEvent = auditEventPersistence.create(auditEventId);

		auditEvent.setCompanyId(auditMessage.getCompanyId());
		auditEvent.setGroupId(auditMessage.getGroupId());
		auditEvent.setUserId(auditMessage.getUserId());
		auditEvent.setUserName(auditMessage.getUserName());
		auditEvent.setCreateDate(auditMessage.getTimestamp());
		auditEvent.setEventType(auditMessage.getEventType());
		auditEvent.setClassName(auditMessage.getClassName());
		auditEvent.setClassPK(auditMessage.getClassPK());
		auditEvent.setMessage(auditMessage.getMessage());
		auditEvent.setClientHost(auditMessage.getClientHost());
		auditEvent.setClientIP(auditMessage.getClientIP());
		auditEvent.setServerName(auditMessage.getServerName());
		auditEvent.setServerPort(auditMessage.getServerPort());
		auditEvent.setSessionID(auditMessage.getSessionID());
		auditEvent.setAdditionalInfo(
			String.valueOf(auditMessage.getAdditionalInfo()));

		return auditEventPersistence.update(auditEvent);
	}

	@Override
	public AuditEvent fetchAuditEvent(long auditEventId) {
		return auditEventPersistence.fetchByPrimaryKey(auditEventId);
	}

	@Override
	public List<AuditEvent> getAuditEvents(long companyId, int start, int end) {
		return auditEventPersistence.findByCompanyId(
			companyId, start, end, new AuditEventCreateDateComparator());
	}

	@Override
	public List<AuditEvent> getAuditEvents(
		long companyId, int start, int end,
		OrderByComparator<AuditEvent> orderByComparator) {

		return auditEventPersistence.findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	@Override
	public List<AuditEvent> getAuditEvents(
		long companyId, long groupId, long userId, String userName,
		Date createDateGT, Date createDateLT, String eventType,
		String className, String classPK, String clientHost, String clientIP,
		String serverName, int serverPort, String sessionID, boolean andSearch,
		int start, int end) {

		return getAuditEvents(
			companyId, groupId, userId, userName, createDateGT, createDateLT,
			eventType, className, classPK, clientHost, clientIP, serverName,
			serverPort, sessionID, andSearch, start, end,
			new AuditEventCreateDateComparator());
	}

	@Override
	public List<AuditEvent> getAuditEvents(
		long companyId, long groupId, long userId, String userName,
		Date createDateGT, Date createDateLT, String eventType,
		String className, String classPK, String clientHost, String clientIP,
		String serverName, int serverPort, String sessionID, boolean andSearch,
		int start, int end, OrderByComparator<AuditEvent> orderByComparator) {

		DynamicQuery dynamicQuery = _buildDynamicQuery(
			companyId, groupId, userId, userName, createDateGT, createDateLT,
			eventType, className, classPK, clientHost, clientIP, serverName,
			serverPort, sessionID, andSearch);

		return dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	@Deprecated
	@Override
	public List<AuditEvent> getAuditEvents(
		long companyId, long userId, String userName, Date createDateGT,
		Date createDateLT, String eventType, String className, String classPK,
		String clientHost, String clientIP, String serverName, int serverPort,
		String sessionID, boolean andSearch, int start, int end) {

		return getAuditEvents(
			companyId, CompanyConstants.SYSTEM, userId, userName, createDateGT,
			createDateLT, eventType, className, classPK, clientHost, clientIP,
			serverName, serverPort, sessionID, andSearch, start, end,
			new AuditEventCreateDateComparator());
	}

	@Deprecated
	@Override
	public List<AuditEvent> getAuditEvents(
		long companyId, long userId, String userName, Date createDateGT,
		Date createDateLT, String eventType, String className, String classPK,
		String clientHost, String clientIP, String serverName, int serverPort,
		String sessionID, boolean andSearch, int start, int end,
		OrderByComparator<AuditEvent> orderByComparator) {

		return getAuditEvents(
			companyId, CompanyConstants.SYSTEM, userId, userName, createDateGT,
			createDateLT, eventType, className, classPK, clientHost, clientIP,
			serverName, serverPort, sessionID, andSearch, start, end,
			new AuditEventCreateDateComparator());
	}

	@Override
	public int getAuditEventsCount(long companyId) {
		return auditEventPersistence.countByCompanyId(companyId);
	}

	@Override
	public int getAuditEventsCount(
		long companyId, long groupId, long userId, String userName,
		Date createDateGT, Date createDateLT, String eventType,
		String className, String classPK, String clientHost, String clientIP,
		String serverName, int serverPort, String sessionID,
		boolean andSearch) {

		DynamicQuery dynamicQuery = _buildDynamicQuery(
			companyId, groupId, userId, userName, createDateGT, createDateLT,
			eventType, className, classPK, clientHost, clientIP, serverName,
			serverPort, sessionID, andSearch);

		return (int)dynamicQueryCount(dynamicQuery);
	}

	@Deprecated
	@Override
	public int getAuditEventsCount(
		long companyId, long userId, String userName, Date createDateGT,
		Date createDateLT, String eventType, String className, String classPK,
		String clientHost, String clientIP, String serverName, int serverPort,
		String sessionID, boolean andSearch) {

		return getAuditEventsCount(
			companyId, CompanyConstants.SYSTEM, userId, userName, createDateGT,
			createDateLT, eventType, className, classPK, clientHost, clientIP,
			serverName, serverPort, sessionID, andSearch);
	}

	private DynamicQuery _buildDynamicQuery(
		long companyId, long groupId, long userId, String userName,
		Date createDateGT, Date createDateLT, String eventType,
		String className, String classPK, String clientHost, String clientIP,
		String serverName, int serverPort, String sessionID,
		boolean andSearch) {

		Junction junction = null;

		if (andSearch) {
			junction = RestrictionsFactoryUtil.conjunction();
		}
		else {
			junction = RestrictionsFactoryUtil.disjunction();
		}

		if (groupId > 0) {
			Property property = PropertyFactoryUtil.forName("groupId");

			junction.add(property.eq(groupId));
		}

		if (userId > 0) {
			Property property = PropertyFactoryUtil.forName("userId");

			junction.add(property.eq(userId));
		}

		if (Validator.isNotNull(userName)) {
			junction.add(
				RestrictionsFactoryUtil.ilike(
					"userName",
					StringPool.PERCENT + userName + StringPool.PERCENT));
		}

		if (Validator.isNotNull(eventType)) {
			Property property = PropertyFactoryUtil.forName("eventType");

			String value =
				StringPool.PERCENT + StringUtil.toUpperCase(eventType) +
					StringPool.PERCENT;

			junction.add(property.like(value));
		}

		if (Validator.isNotNull(className)) {
			junction.add(
				RestrictionsFactoryUtil.ilike(
					"className",
					StringPool.PERCENT + className + StringPool.PERCENT));
		}

		if (Validator.isNotNull(classPK)) {
			Property property = PropertyFactoryUtil.forName("classPK");

			junction.add(property.eq(classPK));
		}

		if (Validator.isNotNull(clientHost)) {
			junction.add(
				RestrictionsFactoryUtil.ilike(
					"clientHost",
					StringPool.PERCENT + clientHost + StringPool.PERCENT));
		}

		if (Validator.isNotNull(clientIP)) {
			junction.add(
				RestrictionsFactoryUtil.ilike(
					"clientIP",
					StringPool.PERCENT + clientIP + StringPool.PERCENT));
		}

		if (Validator.isNotNull(serverName)) {
			junction.add(
				RestrictionsFactoryUtil.ilike(
					"serverName",
					StringPool.PERCENT + serverName + StringPool.PERCENT));
		}

		if (serverPort > 0) {
			Property property = PropertyFactoryUtil.forName("serverPort");

			junction.add(property.eq(serverPort));
		}

		if (Validator.isNotNull(sessionID)) {
			junction.add(
				RestrictionsFactoryUtil.ilike(
					"sessionID",
					StringPool.PERCENT + sessionID + StringPool.PERCENT));
		}

		DynamicQuery dynamicQuery = dynamicQuery();

		if (companyId > 0) {
			Property property = PropertyFactoryUtil.forName("companyId");

			dynamicQuery.add(property.eq(companyId));
		}

		if (createDateGT != null) {
			Property property = PropertyFactoryUtil.forName("createDate");

			dynamicQuery.add(property.gt(createDateGT));
		}

		if (createDateLT != null) {
			Property property = PropertyFactoryUtil.forName("createDate");

			dynamicQuery.add(property.lt(createDateLT));
		}

		return dynamicQuery.add(junction);
	}

}