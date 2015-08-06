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

package com.liferay.wiki.web.configuration.internal;

import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.TypedSettings;
import com.liferay.wiki.web.configuration.WikiPortletInstanceConfigurationOverride;

import java.io.IOException;

import javax.portlet.ValidatorException;

/**
 * @author Iván Zaera
 */
public class WikiPortletInstanceConfigurationOverrideImpl
	implements WikiPortletInstanceConfigurationOverride {

	public WikiPortletInstanceConfigurationOverrideImpl(
		TypedSettings typedSettings) {

		_typedSettings = typedSettings;
	}

	@Override
	public void setHiddenNodes(String[] hiddenNodes) {
		_typedSettings.setValues("hiddenNodes", hiddenNodes);
	}

	@Override
	public void setVisibleNodes(String[] visibleNodes) {
		_typedSettings.setValues("visibleNodes", visibleNodes);
	}

	@Override
	public void store() throws IOException, ValidatorException {
		Settings settings = _typedSettings.getWrappedSettings();

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		modifiableSettings.store();
	}

	private final TypedSettings _typedSettings;

}