/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.antivirus.async.store.jmx;

/**
 * @author Raymond Augé
 */
public interface AntivirusAsyncStatisticsManagerMBean {

	public int getActiveScanCount();

	public String getLastRefresh();

	public long getPendingScanCount();

	public long getProcessingErrorCount();

	public long getSizeExceededCount();

	public long getTotalScannedCount();

	public long getVirusFoundCount();

	public boolean isAutoRefresh();

	public void refresh();

	public void setAutoRefresh(boolean autoRefresh);

}