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

package com.liferay.analytics.message.storage.service.impl;

import com.liferay.analytics.message.storage.model.AnalyticsDeleteMessage;
import com.liferay.analytics.message.storage.service.base.AnalyticsDeleteMessageLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.analytics.message.storage.model.AnalyticsDeleteMessage",
	service = AopService.class
)
public class AnalyticsDeleteMessageLocalServiceImpl
	extends AnalyticsDeleteMessageLocalServiceBaseImpl {

	@Override
	public List<AnalyticsDeleteMessage> getAnalyticsDeleteMessages(
		long companyId, int start, int end) {

		return analyticsDeleteMessagePersistence.findByCompanyId(
			companyId, start, end);
	}

	@Override
	public int getAnalyticsDeleteMessagesCount(long companyId) {
		return analyticsDeleteMessagePersistence.countByCompanyId(companyId);
	}

	@Override
	public List<AnalyticsDeleteMessage> getGtAnalyticsDeleteMessages(
		long companyId, Date modifiedDate, int start, int end) {

		return analyticsDeleteMessagePersistence.findByC_GtM(
			companyId, modifiedDate, start, end);
	}

	@Override
	public int getGtAnalyticsDeleteMessagesCount(
		long companyId, Date gtModifiedDate) {

		return analyticsDeleteMessagePersistence.countByC_GtM(
			companyId, gtModifiedDate);
	}

}