/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import parseJSONString from './parse_json_string';
import {CONTRIBUTOR_TYPES} from './types/contributorTypes';

/**
 * Filters out blueprint suggestion contributors in the
 * `suggestionsContributorConfiguration` array if search experiences
 * are not supported.
 * @param {string} suggestionsContributorConfiguration Stringified array of
 * suggestion contributor configurations.
 * @param {boolean} isDXP
 * @param {boolean} isSearchExperiencesSupported
 * @return {Array} The cleaned up list of suggestion contributor configurations.
 */
export default function cleanSuggestionsContributorConfiguration(
	suggestionsContributorConfiguration,
	isDXP,
	isSearchExperiencesSupported = false
) {
	return parseJSONString(suggestionsContributorConfiguration).filter(
		(item) => {
			if (
				!isSearchExperiencesSupported &&
				item.contributorName === CONTRIBUTOR_TYPES.SXP_BLUEPRINT
			) {
				return false;
			}

			if (
				!isDXP &&
				(item.contributorName === CONTRIBUTOR_TYPES.SXP_BLUEPRINT ||
					item.contributorName ===
						CONTRIBUTOR_TYPES.ASAH_RECENT_SEARCH_KEYWORDS ||
					item.contributorName ===
						CONTRIBUTOR_TYPES.ASAH_TOP_SEARCH_KEYWORDS)
			) {
				return false;
			}

			return true;
		}
	);
}
