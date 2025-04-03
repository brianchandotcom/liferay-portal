/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Eduardo Garcia
 * @generated
 */
@ProviderType
public interface SegmentsEntryFinder {

	public java.util.List<com.liferay.segments.model.SegmentsEntry>
		findBySegmentsEntryIds(
			java.util.List<Long> segmentsEntryIds, int start, int end);

}