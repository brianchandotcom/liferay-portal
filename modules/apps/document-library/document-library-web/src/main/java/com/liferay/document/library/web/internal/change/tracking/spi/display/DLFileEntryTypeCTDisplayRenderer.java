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

package com.liferay.document.library.web.internal.change.tracking.spi.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author David Truong
 */
@Component(immediate = true, service = CTDisplayRenderer.class)
public class DLFileEntryTypeCTDisplayRenderer
	extends BaseCTDisplayRenderer<DLFileEntryType> {

	@Override
	public String[] getAvailableLanguageIds(DLFileEntryType dlFileEntryType) {
		return dlFileEntryType.getAvailableLanguageIds();
	}

	@Override
	public String getDefaultLanguageId(DLFileEntryType dlFileEntryType) {
		return dlFileEntryType.getDefaultLanguageId();
	}

	@Override
	public Class<DLFileEntryType> getModelClass() {
		return DLFileEntryType.class;
	}

	@Override
	public String getTitle(Locale locale, DLFileEntryType dlFileEntryType) {
		return dlFileEntryType.getName(locale);
	}

	@Override
	public boolean isHideable(DLFileEntryType dlFileEntryType) {
		return true;
	}

	@Override
	protected void buildDisplay(
		DisplayBuilder<DLFileEntryType> displayBuilder) {

		DLFileEntryType dlFileEntryType = displayBuilder.getModel();

		displayBuilder.display(
			"name", dlFileEntryType.getName(displayBuilder.getLocale())
		).display(
			"description",
			dlFileEntryType.getDescription(displayBuilder.getLocale())
		).display(
			"created-by",
			() -> {
				String userName = dlFileEntryType.getUserName();

				if (Validator.isNotNull(userName)) {
					return userName;
				}

				return null;
			}
		).display(
			"create-date", dlFileEntryType.getCreateDate()
		).display(
			"last-modified", dlFileEntryType.getModifiedDate()
		);
	}

}