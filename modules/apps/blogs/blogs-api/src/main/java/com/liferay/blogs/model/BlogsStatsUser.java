/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.model;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Cheryl Tang
 */
@ProviderType
public interface BlogsStatsUser {

	public long getEntryCount();

	public long getGroupId();

	public Date getLastPostDate();

	public double getRatingsAverageScore();

	public long getRatingsTotalEntries();

	public double getRatingsTotalScore();

	public long getStatsUserId();

}