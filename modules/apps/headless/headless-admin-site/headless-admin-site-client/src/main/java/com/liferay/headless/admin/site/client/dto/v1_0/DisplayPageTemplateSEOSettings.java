/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.DisplayPageTemplateSEOSettingsSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class DisplayPageTemplateSEOSettings implements Cloneable, Serializable {

	public static DisplayPageTemplateSEOSettings toDTO(String json) {
		return DisplayPageTemplateSEOSettingsSerDes.toDTO(json);
	}

	public String getDescriptionTemplate() {
		return descriptionTemplate;
	}

	public void setDescriptionTemplate(String descriptionTemplate) {
		this.descriptionTemplate = descriptionTemplate;
	}

	public void setDescriptionTemplate(
		UnsafeSupplier<String, Exception> descriptionTemplateUnsafeSupplier) {

		try {
			descriptionTemplate = descriptionTemplateUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String descriptionTemplate;

	public String getHtmlTitleTemplate() {
		return htmlTitleTemplate;
	}

	public void setHtmlTitleTemplate(String htmlTitleTemplate) {
		this.htmlTitleTemplate = htmlTitleTemplate;
	}

	public void setHtmlTitleTemplate(
		UnsafeSupplier<String, Exception> htmlTitleTemplateUnsafeSupplier) {

		try {
			htmlTitleTemplate = htmlTitleTemplateUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String htmlTitleTemplate;

	public Map<String, String> getRobots_i18n() {
		return robots_i18n;
	}

	public void setRobots_i18n(Map<String, String> robots_i18n) {
		this.robots_i18n = robots_i18n;
	}

	public void setRobots_i18n(
		UnsafeSupplier<Map<String, String>, Exception>
			robots_i18nUnsafeSupplier) {

		try {
			robots_i18n = robots_i18nUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, String> robots_i18n;

	public SitemapSettings getSitemapSettings() {
		return sitemapSettings;
	}

	public void setSitemapSettings(SitemapSettings sitemapSettings) {
		this.sitemapSettings = sitemapSettings;
	}

	public void setSitemapSettings(
		UnsafeSupplier<SitemapSettings, Exception>
			sitemapSettingsUnsafeSupplier) {

		try {
			sitemapSettings = sitemapSettingsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected SitemapSettings sitemapSettings;

	@Override
	public DisplayPageTemplateSEOSettings clone()
		throws CloneNotSupportedException {

		return (DisplayPageTemplateSEOSettings)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DisplayPageTemplateSEOSettings)) {
			return false;
		}

		DisplayPageTemplateSEOSettings displayPageTemplateSEOSettings =
			(DisplayPageTemplateSEOSettings)object;

		return Objects.equals(
			toString(), displayPageTemplateSEOSettings.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return DisplayPageTemplateSEOSettingsSerDes.toJSON(this);
	}

}