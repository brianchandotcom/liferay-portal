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
import com.liferay.headless.admin.content.client.serdes.v1_0.OpenGraphSettingsMappingSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class OpenGraphSettingsMapping implements Cloneable, Serializable {

	public static OpenGraphSettingsMapping toDTO(String json) {
		return OpenGraphSettingsMappingSerDes.toDTO(json);
	}

	public String getDescriptionMappingField() {
		return descriptionMappingField;
	}

	public void setDescriptionMappingField(String descriptionMappingField) {
		this.descriptionMappingField = descriptionMappingField;
	}

	public void setDescriptionMappingField(
		UnsafeSupplier<String, Exception>
			descriptionMappingFieldUnsafeSupplier) {

		try {
			descriptionMappingField =
				descriptionMappingFieldUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String descriptionMappingField;

	public String getImageAltMappingField() {
		return imageAltMappingField;
	}

	public void setImageAltMappingField(String imageAltMappingField) {
		this.imageAltMappingField = imageAltMappingField;
	}

	public void setImageAltMappingField(
		UnsafeSupplier<String, Exception> imageAltMappingFieldUnsafeSupplier) {

		try {
			imageAltMappingField = imageAltMappingFieldUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String imageAltMappingField;

	public String getImageMappingField() {
		return imageMappingField;
	}

	public void setImageMappingField(String imageMappingField) {
		this.imageMappingField = imageMappingField;
	}

	public void setImageMappingField(
		UnsafeSupplier<String, Exception> imageMappingFieldUnsafeSupplier) {

		try {
			imageMappingField = imageMappingFieldUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String imageMappingField;

	public String getTitleMappingField() {
		return titleMappingField;
	}

	public void setTitleMappingField(String titleMappingField) {
		this.titleMappingField = titleMappingField;
	}

	public void setTitleMappingField(
		UnsafeSupplier<String, Exception> titleMappingFieldUnsafeSupplier) {

		try {
			titleMappingField = titleMappingFieldUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String titleMappingField;

	@Override
	public OpenGraphSettingsMapping clone() throws CloneNotSupportedException {
		return (OpenGraphSettingsMapping)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof OpenGraphSettingsMapping)) {
			return false;
		}

		OpenGraphSettingsMapping openGraphSettingsMapping =
			(OpenGraphSettingsMapping)object;

		return Objects.equals(toString(), openGraphSettingsMapping.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return OpenGraphSettingsMappingSerDes.toJSON(this);
	}

}