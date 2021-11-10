/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.rest.client.dto.v1_0;

import com.liferay.search.experiences.rest.client.function.UnsafeSupplier;
import com.liferay.search.experiences.rest.client.serdes.v1_0.ElementDefinitionSerDes;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
public class ElementDefinition implements Cloneable, Serializable {

	public static ElementDefinition toDTO(String json) {
		return ElementDefinitionSerDes.toDTO(json);
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setCategory(
		UnsafeSupplier<String, Exception> categoryUnsafeSupplier) {

		try {
			category = categoryUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String category;

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public void setConfiguration(
		UnsafeSupplier<Configuration, Exception> configurationUnsafeSupplier) {

		try {
			configuration = configurationUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Configuration configuration;

	public Map<String, String> getDescription_i18n() {
		return description_i18n;
	}

	public void setDescription_i18n(Map<String, String> description_i18n) {
		this.description_i18n = description_i18n;
	}

	public void setDescription_i18n(
		UnsafeSupplier<Map<String, String>, Exception>
			description_i18nUnsafeSupplier) {

		try {
			description_i18n = description_i18nUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, String> description_i18n;

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setIcon(UnsafeSupplier<String, Exception> iconUnsafeSupplier) {
		try {
			icon = iconUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String icon;

	public Map<String, String> getTitle_i18n() {
		return title_i18n;
	}

	public void setTitle_i18n(Map<String, String> title_i18n) {
		this.title_i18n = title_i18n;
	}

	public void setTitle_i18n(
		UnsafeSupplier<Map<String, String>, Exception>
			title_i18nUnsafeSupplier) {

		try {
			title_i18n = title_i18nUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, String> title_i18n;

	public UiConfiguration getUiConfiguration() {
		return uiConfiguration;
	}

	public void setUiConfiguration(UiConfiguration uiConfiguration) {
		this.uiConfiguration = uiConfiguration;
	}

	public void setUiConfiguration(
		UnsafeSupplier<UiConfiguration, Exception>
			uiConfigurationUnsafeSupplier) {

		try {
			uiConfiguration = uiConfigurationUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected UiConfiguration uiConfiguration;

	public Map<String, ValueDefinition> getUiConfigurationValues() {
		return uiConfigurationValues;
	}

	public void setUiConfigurationValues(
		Map<String, ValueDefinition> uiConfigurationValues) {

		this.uiConfigurationValues = uiConfigurationValues;
	}

	public void setUiConfigurationValues(
		UnsafeSupplier<Map<String, ValueDefinition>, Exception>
			uiConfigurationValuesUnsafeSupplier) {

		try {
			uiConfigurationValues = uiConfigurationValuesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, ValueDefinition> uiConfigurationValues;

	@Override
	public ElementDefinition clone() throws CloneNotSupportedException {
		return (ElementDefinition)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ElementDefinition)) {
			return false;
		}

		ElementDefinition elementDefinition = (ElementDefinition)object;

		return Objects.equals(toString(), elementDefinition.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ElementDefinitionSerDes.toJSON(this);
	}

}