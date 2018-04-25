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

package com.liferay.portal.kernel.model.staging;

import com.liferay.portal.kernel.model.ColorScheme;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetBranch;
import com.liferay.portal.kernel.model.LayoutSetWrapper;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Preston Crary
 */
public class LayoutSetStagingModelWrapper
	extends LayoutSetWrapper implements Serializable {

	public LayoutSetStagingModelWrapper(
		LayoutSet layoutSet, LayoutSetBranch layoutSetBranch) {

		super(Objects.requireNonNull(layoutSet));

		_layoutSetBranch = Objects.requireNonNull(layoutSetBranch);
	}

	@Override
	public Object clone() {
		LayoutSet layoutSet = getWrappedModel();

		LayoutSetBranch layoutSetBranch = _layoutSetBranch;

		if (layoutSetBranch != null) {
			layoutSetBranch = (LayoutSetBranch)layoutSetBranch.clone();
		}

		return new LayoutSetStagingModelWrapper(
			(LayoutSet)layoutSet.clone(), layoutSetBranch);
	}

	@Override
	public ColorScheme getColorScheme() {
		return _layoutSetBranch.getColorScheme();
	}

	@Override
	public String getColorSchemeId() {
		return _layoutSetBranch.getColorSchemeId();
	}

	@Override
	public String getCss() {
		return _layoutSetBranch.getCss();
	}

	public LayoutSet getLayoutSet() {
		return getWrappedModel();
	}

	public LayoutSetBranch getLayoutSetBranch() {
		return _layoutSetBranch;
	}

	@Override
	public boolean getLayoutSetPrototypeLinkEnabled() {
		return _layoutSetBranch.isLayoutSetPrototypeLinkEnabled();
	}

	@Override
	public String getLayoutSetPrototypeUuid() {
		return _layoutSetBranch.getLayoutSetPrototypeUuid();
	}

	@Override
	public boolean getLogo() {
		return _layoutSetBranch.getLogo();
	}

	@Override
	public long getLogoId() {
		return _layoutSetBranch.getLogoId();
	}

	@Override
	public String getSettings() {
		return _layoutSetBranch.getSettings();
	}

	@Override
	public UnicodeProperties getSettingsProperties() {
		return _layoutSetBranch.getSettingsProperties();
	}

	@Override
	public String getSettingsProperty(String key) {
		return _layoutSetBranch.getSettingsProperty(key);
	}

	@Override
	public Theme getTheme() {
		return _layoutSetBranch.getTheme();
	}

	@Override
	public String getThemeId() {
		return _layoutSetBranch.getThemeId();
	}

	@Override
	public String getThemeSetting(String key, String device) {
		return _layoutSetBranch.getThemeSetting(key, device);
	}

	@Override
	public boolean isEscapedModel() {
		return _layoutSetBranch.isEscapedModel();
	}

	@Override
	public boolean isLayoutSetPrototypeLinkActive() {
		return _layoutSetBranch.isLayoutSetPrototypeLinkActive();
	}

	@Override
	public boolean isLogo() {
		return _layoutSetBranch.isLogo();
	}

	@Override
	public void setColorSchemeId(String colorSchemeId) {
		_layoutSetBranch.setColorSchemeId(colorSchemeId);
	}

	@Override
	public void setCss(String css) {
		_layoutSetBranch.setCss(css);
	}

	@Override
	public void setLayoutSetPrototypeLinkEnabled(
		boolean layoutSetPrototypeLinkEnabled) {

		_layoutSetBranch.setLayoutSetPrototypeLinkEnabled(
			layoutSetPrototypeLinkEnabled);
	}

	@Override
	public void setLayoutSetPrototypeUuid(String layoutSetPrototypeUuid) {
		_layoutSetBranch.setLayoutSetPrototypeUuid(layoutSetPrototypeUuid);
	}

	@Override
	public void setLogoId(long logoId) {
		_layoutSetBranch.setLogoId(logoId);
	}

	@Override
	public void setSettings(String settings) {
		_layoutSetBranch.setSettings(settings);
	}

	@Override
	public void setSettingsProperties(UnicodeProperties settingsProperties) {
		_layoutSetBranch.setSettingsProperties(settingsProperties);
	}

	@Override
	public void setThemeId(String themeId) {
		_layoutSetBranch.setThemeId(themeId);
	}

	@Override
	public LayoutSet toEscapedModel() {
		if (super.isEscapedModel()) {
			return this;
		}

		LayoutSet layoutSet = getWrappedModel();

		return new LayoutSetStagingModelWrapper(
			layoutSet.toEscapedModel(), _layoutSetBranch);
	}

	private final LayoutSetBranch _layoutSetBranch;

}