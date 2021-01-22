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
import com.liferay.headless.admin.content.client.serdes.v1_0.DisplayPageTemplateSettingsSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class DisplayPageTemplateSettings implements Cloneable, Serializable {

	public static DisplayPageTemplateSettings toDTO(String json) {
		return DisplayPageTemplateSettingsSerDes.toDTO(json);
	}

	public ContentAssociation getContentAssociation() {
		return contentAssociation;
	}

	public void setContentAssociation(ContentAssociation contentAssociation) {
		this.contentAssociation = contentAssociation;
	}

	public void setContentAssociation(
		UnsafeSupplier<ContentAssociation, Exception>
			contentAssociationUnsafeSupplier) {

		try {
			contentAssociation = contentAssociationUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ContentAssociation contentAssociation;

	public OpenGraphSettingsMapping getOpenGraphSettingsMapping() {
		return openGraphSettingsMapping;
	}

	public void setOpenGraphSettingsMapping(
		OpenGraphSettingsMapping openGraphSettingsMapping) {

		this.openGraphSettingsMapping = openGraphSettingsMapping;
	}

	public void setOpenGraphSettingsMapping(
		UnsafeSupplier<OpenGraphSettingsMapping, Exception>
			openGraphSettingsMappingUnsafeSupplier) {

		try {
			openGraphSettingsMapping =
				openGraphSettingsMappingUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected OpenGraphSettingsMapping openGraphSettingsMapping;

	public SEOSettingsMapping getSeoSettingsMapping() {
		return seoSettingsMapping;
	}

	public void setSeoSettingsMapping(SEOSettingsMapping seoSettingsMapping) {
		this.seoSettingsMapping = seoSettingsMapping;
	}

	public void setSeoSettingsMapping(
		UnsafeSupplier<SEOSettingsMapping, Exception>
			seoSettingsMappingUnsafeSupplier) {

		try {
			seoSettingsMapping = seoSettingsMappingUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected SEOSettingsMapping seoSettingsMapping;

	@Override
	public DisplayPageTemplateSettings clone()
		throws CloneNotSupportedException {

		return (DisplayPageTemplateSettings)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DisplayPageTemplateSettings)) {
			return false;
		}

		DisplayPageTemplateSettings displayPageTemplateSettings =
			(DisplayPageTemplateSettings)object;

		return Objects.equals(
			toString(), displayPageTemplateSettings.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return DisplayPageTemplateSettingsSerDes.toJSON(this);
	}

}