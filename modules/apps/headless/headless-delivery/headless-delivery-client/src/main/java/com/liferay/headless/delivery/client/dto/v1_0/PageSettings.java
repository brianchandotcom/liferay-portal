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
import com.liferay.headless.delivery.client.serdes.v1_0.PageSettingsSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class PageSettings implements Cloneable, Serializable {

	public static PageSettings toDTO(String json) {
		return PageSettingsSerDes.toDTO(json);
	}

	public CustomMetaTag[] getCustomMetaTags() {
		return customMetaTags;
	}

	public void setCustomMetaTags(CustomMetaTag[] customMetaTags) {
		this.customMetaTags = customMetaTags;
	}

	public void setCustomMetaTags(
		UnsafeSupplier<CustomMetaTag[], Exception>
			customMetaTagsUnsafeSupplier) {

		try {
			customMetaTags = customMetaTagsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected CustomMetaTag[] customMetaTags;

	public Boolean getHiddenFromNavigation() {
		return hiddenFromNavigation;
	}

	public void setHiddenFromNavigation(Boolean hiddenFromNavigation) {
		this.hiddenFromNavigation = hiddenFromNavigation;
	}

	public void setHiddenFromNavigation(
		UnsafeSupplier<Boolean, Exception> hiddenFromNavigationUnsafeSupplier) {

		try {
			hiddenFromNavigation = hiddenFromNavigationUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean hiddenFromNavigation;

	public OpenGraphSettings getOpenGraphSettings() {
		return openGraphSettings;
	}

	public void setOpenGraphSettings(OpenGraphSettings openGraphSettings) {
		this.openGraphSettings = openGraphSettings;
	}

	public void setOpenGraphSettings(
		UnsafeSupplier<OpenGraphSettings, Exception>
			openGraphSettingsUnsafeSupplier) {

		try {
			openGraphSettings = openGraphSettingsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected OpenGraphSettings openGraphSettings;

	public SEOSettings getSeoSettings() {
		return seoSettings;
	}

	public void setSeoSettings(SEOSettings seoSettings) {
		this.seoSettings = seoSettings;
	}

	public void setSeoSettings(
		UnsafeSupplier<SEOSettings, Exception> seoSettingsUnsafeSupplier) {

		try {
			seoSettings = seoSettingsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected SEOSettings seoSettings;

	@Override
	public PageSettings clone() throws CloneNotSupportedException {
		return (PageSettings)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PageSettings)) {
			return false;
		}

		PageSettings pageSettings = (PageSettings)object;

		return Objects.equals(toString(), pageSettings.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return PageSettingsSerDes.toJSON(this);
	}

}