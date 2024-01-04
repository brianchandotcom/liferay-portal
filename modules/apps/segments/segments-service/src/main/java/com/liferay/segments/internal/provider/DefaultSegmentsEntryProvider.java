/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.internal.provider;

import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.provider.SegmentsEntryProvider;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eduardo García
 */
@Component(
	property = {
		"segments.entry.provider.order:Integer=100",
		"segments.entry.provider.source=" + SegmentsEntryConstants.SOURCE_DEFAULT
	},
	service = SegmentsEntryProvider.class
)
public class DefaultSegmentsEntryProvider
	extends BaseSegmentsEntryProvider implements SegmentsEntryProvider {

	@Override
	protected String getSource() {
		return SegmentsEntryConstants.SOURCE_DEFAULT;
	}

}