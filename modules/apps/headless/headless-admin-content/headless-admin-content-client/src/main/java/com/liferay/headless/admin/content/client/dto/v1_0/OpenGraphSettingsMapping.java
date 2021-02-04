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

	public String getImageAltMappingFieldKey() {
		return imageAltMappingFieldKey;
	}

	public void setImageAltMappingFieldKey(String imageAltMappingFieldKey) {
		this.imageAltMappingFieldKey = imageAltMappingFieldKey;
	}

	public void setImageAltMappingFieldKey(
		UnsafeSupplier<String, Exception>
			imageAltMappingFieldKeyUnsafeSupplier) {

		try {
			imageAltMappingFieldKey =
				imageAltMappingFieldKeyUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String imageAltMappingFieldKey;

	public String getImageMappingFieldKey() {
		return imageMappingFieldKey;
	}

	public void setImageMappingFieldKey(String imageMappingFieldKey) {
		this.imageMappingFieldKey = imageMappingFieldKey;
	}

	public void setImageMappingFieldKey(
		UnsafeSupplier<String, Exception> imageMappingFieldKeyUnsafeSupplier) {

		try {
			imageMappingFieldKey = imageMappingFieldKeyUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String imageMappingFieldKey;

	public String getTitleMappingFieldKey() {
		return titleMappingFieldKey;
	}

	public void setTitleMappingFieldKey(String titleMappingFieldKey) {
		this.titleMappingFieldKey = titleMappingFieldKey;
	}

	public void setTitleMappingFieldKey(
		UnsafeSupplier<String, Exception> titleMappingFieldKeyUnsafeSupplier) {

		try {
			titleMappingFieldKey = titleMappingFieldKeyUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String titleMappingFieldKey;

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