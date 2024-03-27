/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.scheduler;

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Date;

/**
 * @author Preston Crary
 */
public interface PublishScheduler {

	public ScheduledPublishInfo getScheduledPublishInfo(
			CTCollection ctCollection)
		throws PortalException;

	public void schedulePublish(long ctCollectionId, long userId, Date date)
		throws PortalException;

	public void unschedulePublish(long ctCollectionId) throws PortalException;

}