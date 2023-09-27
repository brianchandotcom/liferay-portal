/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.web.internal.change.tracking.spi.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.segments.model.SegmentsEntry;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author David Truong
 */
@Component(service = CTDisplayRenderer.class)
public class SegmentEntryCTDisplayRenderer
	extends BaseCTDisplayRenderer<SegmentsEntry> {

	@Override
	public Class<SegmentsEntry> getModelClass() {
		return SegmentsEntry.class;
	}

	@Override
	public String getTitle(Locale locale, SegmentsEntry segmentsEntry)
		throws PortalException {

		return segmentsEntry.getName(locale);
	}

}