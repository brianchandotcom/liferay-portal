/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.WidgetPageSpecificationSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class WidgetPageSpecification
	extends PageSpecification implements Cloneable, Serializable {

	public static WidgetPageSpecification toDTO(String json) {
		return WidgetPageSpecificationSerDes.toDTO(json);
	}

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}

	public void setSettings(
		UnsafeSupplier<Settings, Exception> settingsUnsafeSupplier) {

		try {
			settings = settingsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Settings settings;

	public String getSiteTemplatePageSpecificationExternalReferenceCode() {
		return siteTemplatePageSpecificationExternalReferenceCode;
	}

	public void setSiteTemplatePageSpecificationExternalReferenceCode(
		String siteTemplatePageSpecificationExternalReferenceCode) {

		this.siteTemplatePageSpecificationExternalReferenceCode =
			siteTemplatePageSpecificationExternalReferenceCode;
	}

	public void setSiteTemplatePageSpecificationExternalReferenceCode(
		UnsafeSupplier<String, Exception>
			siteTemplatePageSpecificationExternalReferenceCodeUnsafeSupplier) {

		try {
			siteTemplatePageSpecificationExternalReferenceCode =
				siteTemplatePageSpecificationExternalReferenceCodeUnsafeSupplier.
					get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String siteTemplatePageSpecificationExternalReferenceCode;

	public WidgetPageSection[] getWidgetPageSections() {
		return widgetPageSections;
	}

	public void setWidgetPageSections(WidgetPageSection[] widgetPageSections) {
		this.widgetPageSections = widgetPageSections;
	}

	public void setWidgetPageSections(
		UnsafeSupplier<WidgetPageSection[], Exception>
			widgetPageSectionsUnsafeSupplier) {

		try {
			widgetPageSections = widgetPageSectionsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected WidgetPageSection[] widgetPageSections;

	@Override
	public WidgetPageSpecification clone() throws CloneNotSupportedException {
		return (WidgetPageSpecification)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof WidgetPageSpecification)) {
			return false;
		}

		WidgetPageSpecification widgetPageSpecification =
			(WidgetPageSpecification)object;

		return Objects.equals(toString(), widgetPageSpecification.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return WidgetPageSpecificationSerDes.toJSON(this);
	}

}