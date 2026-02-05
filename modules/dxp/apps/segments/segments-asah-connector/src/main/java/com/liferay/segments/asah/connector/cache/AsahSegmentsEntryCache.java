/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.connector.cache;

/**
 * @author Rachael Koestartyo
 */
public interface AsahSegmentsEntryCache {

	public long[] getSegmentsEntryIds(String userId);

	public void putSegmentsEntryIds(String userId, long[] segmentsEntryIds);

}