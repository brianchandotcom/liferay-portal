/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.connector.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.segments.model.SegmentsEntry;

import java.util.List;

/**
 * @author Rachael Koestartyo
 */
public interface IndividualSegmentsHelper {

	public SegmentsEntry addSegmentsEntry(
		long companyId, String id, String name);

	public void updateIndividualSegmentMemberships(
			String individualPK, List<String> individualSegmentIds,
			Boolean removed, SegmentsEntry segmentsEntry, Long userId)
		throws PortalException;

}