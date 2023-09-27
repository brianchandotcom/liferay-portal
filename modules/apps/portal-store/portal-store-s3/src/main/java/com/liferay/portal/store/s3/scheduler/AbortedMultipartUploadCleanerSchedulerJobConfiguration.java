/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.store.s3.scheduler;

import com.amazonaws.services.s3.transfer.TransferManager;

import com.liferay.document.library.kernel.store.Store;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;
import com.liferay.portal.store.s3.S3Store;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Minhchau Dang
 * @author Samuel Ziemer
 */
@Component(enabled = false, service = SchedulerJobConfiguration.class)
public class AbortedMultipartUploadCleanerSchedulerJobConfiguration
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeRunnable<Exception> getJobExecutorUnsafeRunnable() {
		return () -> {
			S3Store s3Store = (S3Store)_store;

			TransferManager transferManager = s3Store.getTransferManager();

			transferManager.abortMultipartUploads(
				s3Store.getBucketName(), _computeStartDate());
		};
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return TriggerConfiguration.createTriggerConfiguration(1, TimeUnit.DAY);
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

	@Reference(target = "(store.type=com.liferay.portal.store.s3.S3Store)")
	private Store _store;

}