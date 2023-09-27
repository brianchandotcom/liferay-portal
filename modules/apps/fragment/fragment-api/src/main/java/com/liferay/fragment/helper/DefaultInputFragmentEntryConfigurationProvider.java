/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.helper;

import com.liferay.portal.kernel.json.JSONObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Víctor Galán
 */
@ProviderType
public interface DefaultInputFragmentEntryConfigurationProvider {

	public static final String FORM_INPUT_SUBMIT_BUTTON =
		"FORM_INPUT_SUBMIT_BUTTON";

	public JSONObject getDefaultInputFragmentEntryKeysJSONObject(long groupId);

	public void updateDefaultInputFragmentEntryKeysJSONObject(
			JSONObject defaultInputFragmentEntryKeysJSONObject, long groupId)
		throws Exception;

}