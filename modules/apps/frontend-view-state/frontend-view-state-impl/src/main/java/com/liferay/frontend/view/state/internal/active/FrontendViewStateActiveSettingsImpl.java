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

package com.liferay.frontend.view.state.internal.active;

import com.liferay.frontend.view.state.active.FrontendViewStateActiveSettings;
import com.liferay.frontend.view.state.model.FrontendViewStateEntry;

/**
 * @author Iván Zaera Avellón
 */
public class FrontendViewStateActiveSettingsImpl
	implements FrontendViewStateActiveSettings {

	public FrontendViewStateActiveSettingsImpl(
		FrontendViewStateEntry frontendViewStateEntry) {

		_frontendViewStateEntry = frontendViewStateEntry;
	}

	public FrontendViewStateEntry getFrontendViewStateEntry() {
		return _frontendViewStateEntry;
	}

	public String getViewState() {
		return _frontendViewStateEntry.getViewState();
	}

	public void setViewState(String viewState) {
		_frontendViewStateEntry.setViewState(viewState);
	}

	private final FrontendViewStateEntry _frontendViewStateEntry;

}