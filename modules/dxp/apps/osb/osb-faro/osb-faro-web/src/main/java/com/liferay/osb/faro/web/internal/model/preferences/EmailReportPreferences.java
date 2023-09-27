/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.preferences;

import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Rachael Koestartyo
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class EmailReportPreferences {

	public EmailReportPreferences() {
	}

	public EmailReportPreferences(Boolean enabled, String frequency) {
		_enabled = enabled;
		_frequency = frequency;
	}

	public Boolean getEnabled() {
		return GetterUtil.getBoolean(_enabled);
	}

	public String getFrequency() {
		return _frequency;
	}

	public void setEnabled(Boolean enabled) {
		_enabled = enabled;
	}

	public void setFrequency(String frequency) {
		_frequency = frequency;
	}

	private Boolean _enabled;
	private String _frequency;

}