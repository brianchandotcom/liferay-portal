/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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