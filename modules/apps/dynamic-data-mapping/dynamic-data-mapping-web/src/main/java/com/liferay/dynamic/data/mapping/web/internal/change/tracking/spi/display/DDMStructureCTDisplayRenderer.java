/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.web.internal.change.tracking.spi.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.dynamic.data.mapping.model.DDMStructure;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author David Truong
 */
@Component(service = CTDisplayRenderer.class)
public class DDMStructureCTDisplayRenderer
	extends BaseCTDisplayRenderer<DDMStructure> {

	@Override
	public String[] getAvailableLanguageIds(DDMStructure ddmStructure) {
		return ddmStructure.getAvailableLanguageIds();
	}

	@Override
	public String getDefaultLanguageId(DDMStructure ddmStructure) {
		return ddmStructure.getDefaultLanguageId();
	}

	@Override
	public Class<DDMStructure> getModelClass() {
		return DDMStructure.class;
	}

	@Override
	public String getTitle(Locale locale, DDMStructure ddmStructure) {
		return ddmStructure.getName(locale);
	}

	@Override
	protected void buildDisplay(DisplayBuilder<DDMStructure> displayBuilder) {
		DDMStructure ddmStructure = displayBuilder.getModel();

		Locale locale = displayBuilder.getLocale();

		displayBuilder.display(
			"name", ddmStructure.getName(locale)
		).display(
			"created-by", ddmStructure.getUserName()
		).display(
			"create-date", ddmStructure.getCreateDate()
		).display(
			"last-modified", ddmStructure.getModifiedDate()
		).display(
			"version", ddmStructure.getVersion()
		).display(
			"description", ddmStructure.getDescription(locale)
		).display(
			"definition", ddmStructure.getDefinition()
		).display(
			"storage-type", ddmStructure.getStorageType()
		).display(
			"type", ddmStructure.getType()
		);
	}

}