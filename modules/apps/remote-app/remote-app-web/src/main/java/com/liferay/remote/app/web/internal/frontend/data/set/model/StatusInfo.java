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

package com.liferay.remote.app.web.internal.frontend.data.set.model;

/**
 * @author Javier de Arcos
 */
public class StatusInfo {

	public StatusInfo(String label, String label_i18n) {
		_label = label;
		_label_i18n = label_i18n;
	}

	public String getLabel() {
		return _label;
	}

	public String getLabel_i18n() {
		return _label_i18n;
	}

	private final String _label;
	private final String _label_i18n;

}