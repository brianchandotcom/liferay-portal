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

package com.liferay.portal.search.tuning.blueprints.options.web.internal.portlet.preferences;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.search.tuning.blueprints.options.web.internal.util.PortletPreferencesHelper;

import java.util.Optional;

import javax.portlet.PortletPreferences;

/**
 * @author Kevin Tan
 */
public class BlueprintsOptionsPortletPreferencesImpl
	implements BlueprintsOptionsPortletPreferences {

	public BlueprintsOptionsPortletPreferencesImpl(
		Optional<PortletPreferences> portletPreferences) {

		_portletPreferencesHelper = new PortletPreferencesHelper(
			portletPreferences);
	}

	@Override
	public Optional<String> getBlueprintIdOptional() {
		return _portletPreferencesHelper.getString(
			BlueprintsOptionsPortletPreferences.PREFERENCE_KEY_BLUEPRINT_ID);
	}

	@Override
	public String getBlueprintIdString() {
		return getBlueprintIdOptional().orElse(StringPool.BLANK);
	}

	private final PortletPreferencesHelper _portletPreferencesHelper;

}