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
 * @typedef FragmentComment
 * @author {object} author
 * @property {string} body
 * @property {number} commentId
 * @property {string} dateDescription
 * @property {boolean} edited
 * @property {string} modifiedDescription
 * @property {boolean} resolved
 */

export default {
	/**
	 * Adds a new Fragment to the current layout
	 * @param {object} options
	 * @param {string} options.body Body of the comment
	 * @param {object} options.config AppConfig
	 * @param {string} options.fragmentEntryLinkId Id of the Fragment
	 * @return {Promise<FragmentComment>} Created FragmentComment
	 */
	addFragmentEntryLinkComment({body, config, fragmentEntryLinkId}) {
		const {addFragmentEntryLinkCommentURL} = config;

		return serviceFetch(config, addFragmentEntryLinkCommentURL, {
			body,
			fragmentEntryLinkId
		});
	}
};
