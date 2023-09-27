/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = CTDisplayRenderer.class)
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