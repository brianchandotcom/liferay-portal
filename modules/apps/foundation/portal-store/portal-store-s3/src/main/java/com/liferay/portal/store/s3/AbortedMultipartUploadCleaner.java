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

package com.liferay.portal.store.s3;

import com.amazonaws.services.s3.transfer.TransferManager;

import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import java.util.Date;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Minhchau Dang
 * @author Samuel Ziemer
 */
@Component
public class AbortedMultipartUploadCleaner extends BaseMessageListener {

	@Activate
	protected void activate(Map<String, Object> properties) {
		_bucketName = _s3Store.getBucketName();
		_transferManager = _s3Store.getTransferManager();

		Class<?> clazz = getClass();

		String className = clazz.getName();

		Trigger trigger = _triggerFactory.createTrigger(
			className, className, null, null, 1, TimeUnit.DAY);

		SchedulerEntry schedulerEntry = new SchedulerEntryImpl(
			className, trigger);

		_schedulerEngineHelper.register(
			this, schedulerEntry, DestinationNames.SCHEDULER_DISPATCH);
	}

	@Deactivate
	protected void deactivate() {
		_schedulerEngineHelper.unregister(this);
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		_transferManager.abortMultipartUploads(
			_bucketName, _computeStartDate());
	}

	private Date _computeStartDate() {
		Date date = new Date();

		LocalDateTime localDateTime = LocalDateTime.ofInstant(
			date.toInstant(), ZoneId.systemDefault());

		LocalDateTime previousDayLocalDateTime = localDateTime.minus(
			1, ChronoUnit.DAYS);

		ZonedDateTime zonedDateTime = previousDayLocalDateTime.atZone(
			ZoneId.systemDefault());

		return Date.from(zonedDateTime.toInstant());
	}

	private String _bucketName;

	@Reference(unbind = "-")
	private S3Store _s3Store;

	@Reference(unbind = "-")
	private volatile SchedulerEngineHelper _schedulerEngineHelper;

	private TransferManager _transferManager;

	@Reference(unbind = "-")
	private volatile TriggerFactory _triggerFactory;

}