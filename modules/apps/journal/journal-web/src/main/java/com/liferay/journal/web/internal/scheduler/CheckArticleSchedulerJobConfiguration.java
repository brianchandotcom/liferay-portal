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

package com.liferay.journal.web.internal.scheduler;

import com.liferay.journal.configuration.JournalServiceConfiguration;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;
import com.liferay.portal.kernel.service.CompanyLocalService;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Tina Tian
 */
@Component(
	configurationPid = "com.liferay.journal.configuration.JournalServiceConfiguration",
	service = SchedulerJobConfiguration.class
)
public class CheckArticleSchedulerJobConfiguration
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeConsumer<Long, Exception>
		getCompanyJobExecutorUnsafeConsumer() {

		return companyId -> _journalArticleLocalService.checkArticles(
			companyId);
	}

	@Override
	public UnsafeRunnable<Exception> getJobExecutorUnsafeRunnable() {
		return () -> _companyLocalService.forEachCompanyId(
			companyId -> _journalArticleLocalService.checkArticles(companyId));
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return TriggerConfiguration.createTriggerConfiguration(
			_journalServiceConfiguration.checkInterval(), TimeUnit.MINUTE);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_journalServiceConfiguration = ConfigurableUtil.createConfigurable(
			JournalServiceConfiguration.class, properties);
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	private JournalServiceConfiguration _journalServiceConfiguration;

}