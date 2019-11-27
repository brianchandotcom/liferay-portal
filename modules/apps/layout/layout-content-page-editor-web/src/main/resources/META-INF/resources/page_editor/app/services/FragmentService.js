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

import serviceFetch from './serviceFetch';

/**
 * @typedef FragmentEntryLink
 * @property {string} content
 * @property {string} fragmentEntryLinkId
 */

export default {
	/**
	 * Adds a new Fragment to the current layout
	 * @param {object} options
	 * @param {object} options.config Application config
	 * @param {string} options.fragmentGroupId GroupId that wraps the Fragment
	 * @param {string} options.fragmentKey Key of the Fragment
	 * @param {string} options.segmentsExperienceId Current segmentsExperienceId
	 * @return {Promise<FragmentEntryLink>} Created FragmentEntryLink
	 */
	addFragmentEntryLink({
		config,
		fragmentGroupId,
		fragmentKey,
		segmentsExperienceId
	}) {
		const {addFragmentEntryLinkURL, classNameId, classPK} = config;

		return serviceFetch(config, addFragmentEntryLinkURL, {
			classNameId,
			classPK,
			fragmentGroupId,
			fragmentKey,
			segmentsExperienceId
		});
	}
};
