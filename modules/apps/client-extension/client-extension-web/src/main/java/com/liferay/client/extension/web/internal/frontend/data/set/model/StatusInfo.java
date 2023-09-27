/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.web.internal.frontend.data.set.model;

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