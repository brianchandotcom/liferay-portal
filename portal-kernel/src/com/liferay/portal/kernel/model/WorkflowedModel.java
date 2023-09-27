/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public interface WorkflowedModel {

	public int getStatus();

	public long getStatusByUserId();

	public String getStatusByUserName();

	public String getStatusByUserUuid();

	public Date getStatusDate();

	public boolean isApproved();

	public boolean isDenied();

	public boolean isDraft();

	public boolean isExpired();

	public boolean isInactive();

	public boolean isIncomplete();

	public boolean isPending();

	public boolean isScheduled();

	public void setStatus(int status);

	public void setStatusByUserId(long statusByUserId);

	public void setStatusByUserName(String statusByUserName);

	public void setStatusByUserUuid(String statusByUserUuid);

	public void setStatusDate(Date statusDate);

}