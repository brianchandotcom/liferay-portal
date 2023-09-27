/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.reading.time.web.internal.change.tracking.spi.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.reading.time.model.ReadingTimeEntry;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Cheryl Tang
 */
@Component(service = CTDisplayRenderer.class)
public class ReadingTimeEntryCTDisplayRenderer
	extends BaseCTDisplayRenderer<ReadingTimeEntry> {

	@Override
	public Class<ReadingTimeEntry> getModelClass() {
		return ReadingTimeEntry.class;
	}

	@Override
	public String getTitle(Locale locale, ReadingTimeEntry readingTimeEntry) {
		return String.valueOf(readingTimeEntry.getReadingTimeEntryId());
	}

	@Override
	public boolean isHideable(ReadingTimeEntry readingTimeEntry) {
		return true;
	}

	@Override
	protected void buildDisplay(
		DisplayBuilder<ReadingTimeEntry> displayBuilder) {

		ReadingTimeEntry readingTimeEntry = displayBuilder.getModel();

		displayBuilder.display(
			"class-name", readingTimeEntry.getClassName()
		).display(
			"primary-key", readingTimeEntry.getClassPK()
		).display(
			"reading-time", readingTimeEntry.getReadingTime()
		);
	}

}