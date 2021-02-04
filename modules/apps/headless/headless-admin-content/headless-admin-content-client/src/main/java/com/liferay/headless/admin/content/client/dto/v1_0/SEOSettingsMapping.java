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

package com.liferay.headless.admin.content.client.dto.v1_0;

import com.liferay.headless.admin.content.client.function.UnsafeSupplier;
import com.liferay.headless.admin.content.client.serdes.v1_0.SEOSettingsMappingSerDes;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class SEOSettingsMapping implements Cloneable, Serializable {

	public static SEOSettingsMapping toDTO(String json) {
		return SEOSettingsMappingSerDes.toDTO(json);
	}

	public String getDescriptionMappingFieldKey() {
		return descriptionMappingFieldKey;
	}

	public void setDescriptionMappingFieldKey(
		String descriptionMappingFieldKey) {

		this.descriptionMappingFieldKey = descriptionMappingFieldKey;
	}

	public void setDescriptionMappingFieldKey(
		UnsafeSupplier<String, Exception>
			descriptionMappingFieldKeyUnsafeSupplier) {

		try {
			descriptionMappingFieldKey =
				descriptionMappingFieldKeyUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String descriptionMappingFieldKey;

	public String getHtmlTitleMappingFieldKey() {
		return htmlTitleMappingFieldKey;
	}

	public void setHtmlTitleMappingFieldKey(String htmlTitleMappingFieldKey) {
		this.htmlTitleMappingFieldKey = htmlTitleMappingFieldKey;
	}

	public void setHtmlTitleMappingFieldKey(
		UnsafeSupplier<String, Exception>
			htmlTitleMappingFieldKeyUnsafeSupplier) {

		try {
			htmlTitleMappingFieldKey =
				htmlTitleMappingFieldKeyUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String htmlTitleMappingFieldKey;

	public String getRobots() {
		return robots;
	}

	public void setRobots(String robots) {
		this.robots = robots;
	}

	public void setRobots(
		UnsafeSupplier<String, Exception> robotsUnsafeSupplier) {

		try {
			robots = robotsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String robots;

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

	@Override
	public SEOSettingsMapping clone() throws CloneNotSupportedException {
		return (SEOSettingsMapping)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SEOSettingsMapping)) {
			return false;
		}

		SEOSettingsMapping seoSettingsMapping = (SEOSettingsMapping)object;

		return Objects.equals(toString(), seoSettingsMapping.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return SEOSettingsMappingSerDes.toJSON(this);
	}

}