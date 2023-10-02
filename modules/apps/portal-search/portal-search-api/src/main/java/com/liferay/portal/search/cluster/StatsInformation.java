/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.cluster;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Felipe Lorenz
 */
@ProviderType
public interface StatsInformation {

	public double getAvailableDiskSpace();

	public double getSizeOfLargestIndex();

	public double getUsedDiskSpace();

}