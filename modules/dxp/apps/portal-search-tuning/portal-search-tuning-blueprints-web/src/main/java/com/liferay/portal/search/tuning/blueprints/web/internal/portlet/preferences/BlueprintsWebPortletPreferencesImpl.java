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

package com.liferay.portal.search.tuning.blueprints.web.internal.portlet.preferences;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.search.tuning.blueprints.web.internal.constants.BlueprintsWebPortletPreferenceKeys;
import com.liferay.portal.search.tuning.blueprints.web.internal.util.PortletPreferencesHelper;

import java.util.Optional;

import javax.portlet.PortletPreferences;

/**
 * @author Petteri Karttunen
 */
public class BlueprintsWebPortletPreferencesImpl
	implements BlueprintsWebPortletPreferences {

	public BlueprintsWebPortletPreferencesImpl(
		PortletPreferences portletPreferences) {

		_portletPreferencesHelper = new PortletPreferencesHelper(
			portletPreferences);
	}

	@Override
	public long getBlueprintId() {
		return getBlueprintIdOptional().orElse(0L);
	}

	@Override
	public Optional<Long> getBlueprintIdOptional() {
		return _portletPreferencesHelper.getLongOptional(
			BlueprintsWebPortletPreferenceKeys.SEARCH_BLUEPRINT_ID);
	}

	@Override
	public int getDidYouMeanHitsThreshold() {
		return _portletPreferencesHelper.getInteger(
			BlueprintsWebPortletPreferenceKeys.DID_YOU_MEAN_HITS_THRESHOLD, 5);
	}

	@Override
	public int getKeywordIndexingHitsThreshold() {
		return _portletPreferencesHelper.getInteger(
			BlueprintsWebPortletPreferenceKeys.KEYWORD_INDEXING_HITS_THRESHOLD,
			3);
	}

	@Override
	public int getMaxDidYouMeanSuggestions() {
		return _portletPreferencesHelper.getInteger(
			BlueprintsWebPortletPreferenceKeys.
				MAX_DID_YOU_MEAN_QUERY_SUGGESTIONS,
			10);
	}

	@Override
	public int getMaxTypeaheadSuggestions() {
		return _portletPreferencesHelper.getInteger(
			BlueprintsWebPortletPreferenceKeys.MAX_TYPEAHEAD_SUGGESTIONS, 10);
	}

	@Override
	public String getTitleTypeaheadEntryClassNames() {
		StringBundler sb = new StringBundler(3);

		sb.append(JournalArticle.class.getName());
		sb.append(",");
		sb.append(DLFileEntry.class.getName());

		return _portletPreferencesHelper.getString(
			BlueprintsWebPortletPreferenceKeys.
				TITLE_TYPEAHEAD_ENTRY_CLASS_NAMES,
			sb.toString());
	}

	@Override
	public boolean isDidYouMeanEnabled() {
		return _portletPreferencesHelper.getBoolean(
			BlueprintsWebPortletPreferenceKeys.DID_YOU_MEAN_ENABLED, true);
	}

	@Override
	public boolean isKeywordIndexingEnabled() {
		return _portletPreferencesHelper.getBoolean(
			BlueprintsWebPortletPreferenceKeys.KEYWORD_INDEXING_ENABLED, true);
	}

	@Override
	public boolean isMisspellingsEnabled() {
		return _portletPreferencesHelper.getBoolean(
			BlueprintsWebPortletPreferenceKeys.MISSPELLINGS_ENABLED, true);
	}

	@Override
	public boolean isTypeaheadEnabled() {
		return _portletPreferencesHelper.getBoolean(
			BlueprintsWebPortletPreferenceKeys.TYPEAHEAD_ENABLED, true);
	}

	private final PortletPreferencesHelper _portletPreferencesHelper;

}