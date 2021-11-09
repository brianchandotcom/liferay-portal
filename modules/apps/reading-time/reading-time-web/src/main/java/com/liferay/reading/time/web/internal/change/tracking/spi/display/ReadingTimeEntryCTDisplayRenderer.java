/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
@Component(immediate = true, service = CTDisplayRenderer.class)
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