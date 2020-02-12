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
 * @property {object} author
 * @property {string} body
 * @property {number} commentId
 * @property {string} dateDescription
 * @property {boolean} edited
 * @property {string} modifiedDescription
 * @property {boolean} resolved
 */

/**
 * @typedef FragmentEntryLink
 * @property {string} content
 * @property {string} fragmentEntryLinkId
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
	addComment({
		body,
		config,
		fragmentEntryLinkId,
		onNetworkStatus,
		parentCommentId = 0
	}) {
		const {addFragmentEntryLinkCommentURL} = config;

		return serviceFetch(
			config,
			addFragmentEntryLinkCommentURL,
			{
				body: {
					body,
					fragmentEntryLinkId,
					parentCommentId
				}
			},
			onNetworkStatus
		);
	},

	/**
	 * Adds a new Fragment to the current layout
	 * @param {object} options
	 * @param {object} options.config Application config
	 * @param {string} options.groupId GroupId that wraps the Fragment
	 * @param {string} options.fragmentEntryKey Key of the Fragment
	 * @param {string} options.segmentsExperienceId Current segmentsExperienceId
	 * @return {Promise<FragmentEntryLink>} Created FragmentEntryLink
	 */
	addFragmentEntryLink({
		config,
		fragmentEntryKey,
		groupId,
		onNetworkStatus,
		parentItemId,
		position,
		segmentsExperienceId
	}) {
		const {addFragmentEntryLinkURL} = config;

		return serviceFetch(
			config,
			addFragmentEntryLinkURL,
			{
				body: {
					fragmentEntryKey,
					groupId,
					parentItemId,
					position,
					segmentsExperienceId
				}
			},
			onNetworkStatus,
			{requestGenerateDraft: true}
		);
	},

	/**
	 * Deletes a fragment comment
	 * @param {object} options
	 * @param {string} options.commentId Id of the comment
	 * @param {object} options.config AppConfig
	 * @return {Promise<void>}
	 */
	deleteComment({commentId, config, onNetworkStatus}) {
		const {deleteFragmentEntryLinkCommentURL} = config;

		return serviceFetch(
			config,
			deleteFragmentEntryLinkCommentURL,
			{
				body: {commentId}
			},
			onNetworkStatus
		);
	},

	/**
	 * Duplicates a fragmentEntryLink
	 * @param {object} options
	 * @param {object} options.config Application config
	 * @param {string} options.itemId id of the item
	 * @param {string} options.segmentsExperienceId Experience id
	 */
	duplicateItem({config, itemId, onNetworkStatus, segmentsExperienceId}) {
		const {duplicateItemURL} = config;

		return serviceFetch(
			config,
			duplicateItemURL,
			{
				body: {
					itemId,
					segmentsExperienceId
				}
			},
			onNetworkStatus,
			{requestGenerateDraft: true}
		);
	},

	/**
	 * Edits a fragment comment
	 * @param {object} options
	 * @param {string} options.body Body of the comment
	 * @param {string} options.commentId Id of the comment
	 * @param {object} options.config AppConfig
	 * @param {boolean} options.resolved Whether the comment should be marked as resolved or not
	 * @return {Promise<FragmentComment>} Created FragmentComment
	 */
	editComment({body, commentId, config, onNetworkStatus, resolved}) {
		const {editFragmentEntryLinkCommentURL} = config;

		return serviceFetch(
			config,
			editFragmentEntryLinkCommentURL,
			{
				body: {
					body,
					commentId,
					resolved
				}
			},
			onNetworkStatus
		);
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
		onNetworkStatus,
		segmentsExperienceId
	}) {
		const {renderFragmentEntryURL} = config;

		return serviceFetch(
			config,
			renderFragmentEntryURL,
			{
				body: {
					fragmentEntryLinkId,
					segmentsExperienceId
				}
			},
			onNetworkStatus
		);
	},

	/**
	 * Update editableValues of the fragmentEntryLink with the given fragmentEntryLinkId
	 * @param {object} options
	 * @param {object} options.config Application config
	 * @param {string} options.fragmentEntryLinkId Id of the fragmentEntryLink
	 * @param {string} options.editableValues New editableValues
	 */
	updateEditableValues({
		config,
		editableValues,
		fragmentEntryLinkId,
		onNetworkStatus
	}) {
		const {editFragmentEntryLinkURL} = config;

		return serviceFetch(
			config,
			editFragmentEntryLinkURL,
			{
				body: {
					editableValues: JSON.stringify(editableValues),
					fragmentEntryLinkId,
					updateClassedModel: true
				}
			},
			onNetworkStatus,
			{requestGenerateDraft: true}
		);
	}
};
