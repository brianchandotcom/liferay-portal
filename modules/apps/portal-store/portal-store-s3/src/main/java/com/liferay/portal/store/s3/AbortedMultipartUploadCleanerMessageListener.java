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

import com.liferay.document.library.kernel.store.Store;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import java.util.Date;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Minhchau Dang
 * @author Samuel Ziemer
 */
@Component(
	configurationPid = "com.liferay.portal.store.s3.configuration.S3StoreConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, enabled = false,
	service = {}
)
public class AbortedMultipartUploadCleanerMessageListener
	extends BaseMessageListener {

	@Activate
	protected void activate() {
		Class<?> clazz = getClass();

		String className = clazz.getName();

		_schedulerEngineHelper.register(
			this,
			new SchedulerEntryImpl(
				className,
				_triggerFactory.createTrigger(
					className, className, null, null, 1, TimeUnit.DAY)),
			DestinationNames.SCHEDULER_DISPATCH);
	}

	@Deactivate
	protected void deactivate() {
		_schedulerEngineHelper.unregister(this);
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		S3Store s3Store = (S3Store)_store;

		TransferManager transferManager = s3Store.getTransferManager();

		transferManager.abortMultipartUploads(
			s3Store.getBucketName(), _computeStartDate());
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

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	@Reference(target = "(store.type=com.liferay.portal.store.s3.S3Store)")
	private Store _store;

	@Reference
	private TriggerFactory _triggerFactory;

}