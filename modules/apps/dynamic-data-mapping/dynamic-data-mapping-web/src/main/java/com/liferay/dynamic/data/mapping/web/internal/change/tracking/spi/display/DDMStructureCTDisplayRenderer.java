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