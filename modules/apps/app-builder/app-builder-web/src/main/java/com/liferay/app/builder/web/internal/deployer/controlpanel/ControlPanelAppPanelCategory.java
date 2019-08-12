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

package com.liferay.app.builder.web.internal.deployer.controlpanel;

import com.liferay.application.list.BasePanelCategory;

import java.util.Dictionary;
import java.util.Locale;

/**
 * @author Jeyvison Nascimento
 */
public class ControlPanelAppPanelCategory extends BasePanelCategory {

	public ControlPanelAppPanelCategory(Dictionary properties) {
		_key = (String)properties.get("key");
		_label = (String)properties.get("label");
	}

	@Override
	public String getKey() {
		return _key;
	}

	@Override
	public String getLabel(Locale locale) {
		return _label;
	}

	private final String _key;
	private final String _label;

}