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
import com.liferay.headless.delivery.client.serdes.v1_0.SettingsSerDes;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Settings {

	public String getColorSchemeName() {
		return colorSchemeName;
	}

	public void setColorSchemeName(String colorSchemeName) {
		this.colorSchemeName = colorSchemeName;
	}

	public void setColorSchemeName(
		UnsafeSupplier<String, Exception> colorSchemeNameUnsafeSupplier) {

		try {
			colorSchemeName = colorSchemeNameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String colorSchemeName;

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public void setCss(UnsafeSupplier<String, Exception> cssUnsafeSupplier) {
		try {
			css = cssUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String css;

	public String getJavascript() {
		return javascript;
	}

	public void setJavascript(String javascript) {
		this.javascript = javascript;
	}

	public void setJavascript(
		UnsafeSupplier<String, Exception> javascriptUnsafeSupplier) {

		try {
			javascript = javascriptUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String javascript;

	public MasterPage getMasterPage() {
		return masterPage;
	}

	public void setMasterPage(MasterPage masterPage) {
		this.masterPage = masterPage;
	}

	public void setMasterPage(
		UnsafeSupplier<MasterPage, Exception> masterPageUnsafeSupplier) {

		try {
			masterPage = masterPageUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected MasterPage masterPage;

	public String getThemeName() {
		return themeName;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}

	public void setThemeName(
		UnsafeSupplier<String, Exception> themeNameUnsafeSupplier) {

		try {
			themeName = themeNameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String themeName;

	public Object getThemeSettings() {
		return themeSettings;
	}

	public void setThemeSettings(Object themeSettings) {
		this.themeSettings = themeSettings;
	}

	public void setThemeSettings(
		UnsafeSupplier<Object, Exception> themeSettingsUnsafeSupplier) {

		try {
			themeSettings = themeSettingsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Object themeSettings;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Settings)) {
			return false;
		}

		Settings settings = (Settings)object;

		return Objects.equals(toString(), settings.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return SettingsSerDes.toJSON(this);
	}

}