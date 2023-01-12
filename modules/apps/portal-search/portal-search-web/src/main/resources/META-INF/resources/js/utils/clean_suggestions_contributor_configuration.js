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

import parseJSONString from './parse_json_string';
import {CONTRIBUTOR_TYPES} from './types/contributorTypes';

/**
 * Filters out blueprint suggestion contributors in the fields array
 * if search experiences are not supported.
 * @param {Array} fields The list of fields.
 * @param {boolean} isSearchExperiencesSupported
 * @return {Array} The cleaned up list of fields.
 */
export default function cleanSuggestionsContributorConfiguration(
	suggestionsContributorConfiguration,
	isSearchExperiencesSupported = false
) {
	return parseJSONString(suggestionsContributorConfiguration).filter(
		(item) => {
			if (isSearchExperiencesSupported) {
				return true;
			}

			if (item.contributorName === CONTRIBUTOR_TYPES.BASIC) {
				return true;
			}

			return false;
		}
	);
}
