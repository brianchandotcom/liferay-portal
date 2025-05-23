/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.DisplayPageTemplateOpenGraphSettingsSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class DisplayPageTemplateOpenGraphSettings
	implements Cloneable, Serializable {

	public static DisplayPageTemplateOpenGraphSettings toDTO(String json) {
		return DisplayPageTemplateOpenGraphSettingsSerDes.toDTO(json);
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

	public String getImageAltTemplate() {
		return imageAltTemplate;
	}

	public void setImageAltTemplate(String imageAltTemplate) {
		this.imageAltTemplate = imageAltTemplate;
	}

	public void setImageAltTemplate(
		UnsafeSupplier<String, Exception> imageAltTemplateUnsafeSupplier) {

		try {
			imageAltTemplate = imageAltTemplateUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String imageAltTemplate;

	public String getImageTemplate() {
		return imageTemplate;
	}

	public void setImageTemplate(String imageTemplate) {
		this.imageTemplate = imageTemplate;
	}

	public void setImageTemplate(
		UnsafeSupplier<String, Exception> imageTemplateUnsafeSupplier) {

		try {
			imageTemplate = imageTemplateUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String imageTemplate;

	public String getTitleTemplate() {
		return titleTemplate;
	}

	public void setTitleTemplate(String titleTemplate) {
		this.titleTemplate = titleTemplate;
	}

	public void setTitleTemplate(
		UnsafeSupplier<String, Exception> titleTemplateUnsafeSupplier) {

		try {
			titleTemplate = titleTemplateUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String titleTemplate;

	@Override
	public DisplayPageTemplateOpenGraphSettings clone()
		throws CloneNotSupportedException {

		return (DisplayPageTemplateOpenGraphSettings)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DisplayPageTemplateOpenGraphSettings)) {
			return false;
		}

		DisplayPageTemplateOpenGraphSettings
			displayPageTemplateOpenGraphSettings =
				(DisplayPageTemplateOpenGraphSettings)object;

		return Objects.equals(
			toString(), displayPageTemplateOpenGraphSettings.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return DisplayPageTemplateOpenGraphSettingsSerDes.toJSON(this);
	}

}