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

package com.liferay.headless.delivery.client.dto.v1_0;

import com.liferay.headless.delivery.client.function.UnsafeSupplier;
import com.liferay.headless.delivery.client.serdes.v1_0.LanguageSerDes;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Language implements Cloneable, Serializable {

	public static Language toDTO(String json) {
		return LanguageSerDes.toDTO(json);
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setCountry(
		UnsafeSupplier<String, Exception> countryUnsafeSupplier) {

		try {
			country = countryUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String country;

	public Map<String, String> getCountry_i18n() {
		return country_i18n;
	}

	public void setCountry_i18n(Map<String, String> country_i18n) {
		this.country_i18n = country_i18n;
	}

	public void setCountry_i18n(
		UnsafeSupplier<Map<String, String>, Exception>
			country_i18nUnsafeSupplier) {

		try {
			country_i18n = country_i18nUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, String> country_i18n;

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public void setIsDefault(
		UnsafeSupplier<Boolean, Exception> isDefaultUnsafeSupplier) {

		try {
			isDefault = isDefaultUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean isDefault;

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setLanguage(
		UnsafeSupplier<String, Exception> languageUnsafeSupplier) {

		try {
			language = languageUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String language;

	public String getLanguageId() {
		return languageId;
	}

	public void setLanguageId(String languageId) {
		this.languageId = languageId;
	}

	public void setLanguageId(
		UnsafeSupplier<String, Exception> languageIdUnsafeSupplier) {

		try {
			languageId = languageIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String languageId;

	public Map<String, String> getLanguage_i18n() {
		return language_i18n;
	}

	public void setLanguage_i18n(Map<String, String> language_i18n) {
		this.language_i18n = language_i18n;
	}

	public void setLanguage_i18n(
		UnsafeSupplier<Map<String, String>, Exception>
			language_i18nUnsafeSupplier) {

		try {
			language_i18n = language_i18nUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, String> language_i18n;

	@Override
	public Language clone() throws CloneNotSupportedException {
		return (Language)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Language)) {
			return false;
		}

		Language language = (Language)object;

		return Objects.equals(toString(), language.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return LanguageSerDes.toJSON(this);
	}

}