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
import com.liferay.dynamic.data.mapping.model.DDMTemplate;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author David Truong
 */
@Component(immediate = true, service = CTDisplayRenderer.class)
public class DDMTemplateCTDisplayRenderer
	extends BaseCTDisplayRenderer<DDMTemplate> {

	@Override
	public String[] getAvailableLanguageIds(DDMTemplate ddmTemplate) {
		return ddmTemplate.getAvailableLanguageIds();
	}

	@Override
	public String getDefaultLanguageId(DDMTemplate ddmTemplate) {
		return ddmTemplate.getDefaultLanguageId();
	}

	@Override
	public Class<DDMTemplate> getModelClass() {
		return DDMTemplate.class;
	}

	@Override
	public String getTitle(Locale locale, DDMTemplate ddmTemplate) {
		return ddmTemplate.getName(locale);
	}

	@Override
	protected void buildDisplay(DisplayBuilder<DDMTemplate> displayBuilder) {
		DDMTemplate ddmTemplate = displayBuilder.getModel();

		Locale locale = displayBuilder.getLocale();

		displayBuilder.display(
			"name", ddmTemplate.getName(locale)
		).display(
			"created-by", ddmTemplate.getUserName()
		).display(
			"create-date", ddmTemplate.getCreateDate()
		).display(
			"last-modified", ddmTemplate.getModifiedDate()
		).display(
			"version", ddmTemplate.getVersion()
		).display(
			"description", ddmTemplate.getDescription(locale)
		).display(
			"type", ddmTemplate.getType()
		).display(
			"mode", ddmTemplate.getMode()
		).display(
			"language", ddmTemplate.getLanguage()
		).display(
			"script", ddmTemplate.getScript()
		).display(
			"cacheable", ddmTemplate.isCacheable()
		);
	}

}