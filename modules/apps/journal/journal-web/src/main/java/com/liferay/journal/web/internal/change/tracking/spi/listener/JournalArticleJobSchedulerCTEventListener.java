/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.change.tracking.spi.listener;

import com.liferay.change.tracking.spi.exception.CTEventException;
import com.liferay.change.tracking.spi.listener.CTEventListener;
import com.liferay.journal.web.internal.scheduler.CheckArticleSchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Noor Najjar
 */
@Component(service = CTEventListener.class)
public class JournalArticleJobSchedulerCTEventListener
	implements CTEventListener {

	@Override
	public void onAfterPublish(long ctCollectionId) throws CTEventException {
		try {
			String name = CheckArticleSchedulerJobConfiguration.class.getName();

			_schedulerEngineHelper.run(
				CompanyThreadLocal.getCompanyId(), name, name,
				StorageType.MEMORY_CLUSTERED);
		}
		catch (Exception exception) {
			throw new CTEventException(exception);
		}
	}

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

}