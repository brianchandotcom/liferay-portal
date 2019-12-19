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
		parentId,
		position,
		segmentsExperienceId
	}) {
		const {addFragmentEntryLinkURL, classNameId, classPK} = config;

		return serviceFetch(config, addFragmentEntryLinkURL, {
			classNameId,
			classPK,
			fragmentGroupId,
			fragmentKey,
			parentId,
			position,
			segmentsExperienceId
		});
	},

	/**
	 * Duplicates a fragmentEntryLink
	 * @param {object} options
	 * @param {object} options.config Application config
	 * @param {string} options.fragmentEntryLinkId Id of the fragmentEntryLink
	 * @param {string} options.itemId id of the item
	 * @param {string} options.segmentsExperienceId Experience id
	 */
	duplicateFragmentEntryLink({
		config,
		fragmentEntryLinkId,
		itemId,
		segmentsExperienceId
	}) {
		const {duplicateFragmentEntryLinkURL} = config;

		return serviceFetch(config, duplicateFragmentEntryLinkURL, {
			fragmentEntryLinkId,
			itemId,
			segmentsExperienceId
		});
	},

	/**
	 * Render the content of a fragmentEntryLink
	 * @param {object} options
	 * @param {object} options.config Application config
	 * @param {string} options.fragmentEntryLinkId Id of the fragmentEntryLink
	 * @param {string} options.segmentsExperienceId Experience id
	 */
	renderFragmentEntryLinkContent({
		config,
		fragmentEntryLinkId,
		segmentsExperienceId
	}) {
		const {renderFragmentEntryURL} = config;

		return serviceFetch(config, renderFragmentEntryURL, {
			fragmentEntryLinkId,
			segmentsExperienceId
		});
	},

	/**
	 * Update editableValues of the fragmentEntryLink with the given fragmentEntryLinkId
	 * @param {object} options
	 * @param {object} options.config Application config
	 * @param {string} options.fragmentEntryLinkId Id of the fragmentEntryLink
	 * @param {string} options.editableValues New editableValues
	 */
	updateEditableValues({config, editableValues, fragmentEntryLinkId}) {
		const {editFragmentEntryLinkURL} = config;

		return serviceFetch(config, editFragmentEntryLinkURL, {
			editableValues: JSON.stringify(editableValues),
			fragmentEntryLinkId,
			updateClassedModel: true
		});
	}
};
