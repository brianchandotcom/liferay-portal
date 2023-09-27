/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = CTDisplayRenderer.class)
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