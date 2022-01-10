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

package com.liferay.search.experiences.web.internal.blueprint.options.portlet.preferences;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Optional;

import javax.portlet.PortletPreferences;

/**
 * @author Kevin Tan
 */
public class SXPBlueprintOptionsPortletPreferences {

	public static final String PREFERENCE_KEY_FEDERATED_SEARCH_KEY =
		"federatedSearchKey";

	public static final String PREFERENCE_KEY_SXP_BLUEPRINT_ID =
		"sxpBlueprintId";

	public SXPBlueprintOptionsPortletPreferences(
		Optional<PortletPreferences> portletPreferencesOptional) {

		_portletPreferencesOptional = portletPreferencesOptional;
	}

	public Optional<String> getFederatedSearchKeyOptional() {
		return _getString(
			SXPBlueprintOptionsPortletPreferences.
				PREFERENCE_KEY_FEDERATED_SEARCH_KEY);
	}

	public String getFederatedSearchKeyString() {
		return getFederatedSearchKeyOptional().orElse(StringPool.BLANK);
	}

	public Optional<String> getSXPBlueprintIdOptional() {
		return _getString(
			SXPBlueprintOptionsPortletPreferences.
				PREFERENCE_KEY_SXP_BLUEPRINT_ID);
	}

	public String getSXPBlueprintIdString() {
		return getSXPBlueprintIdOptional().orElse(StringPool.BLANK);
	}

	private Optional<String> _getString(String key) {
		return _portletPreferencesOptional.flatMap(
			portletPreferences -> _maybe(
				portletPreferences.getValue(key, StringPool.BLANK)));
	}

	private Optional<String> _maybe(String s) {
		s = StringUtil.trim(s);

		if (Validator.isBlank(s)) {
			return Optional.empty();
		}

		return Optional.of(s);
	}

	private final Optional<PortletPreferences> _portletPreferencesOptional;

}