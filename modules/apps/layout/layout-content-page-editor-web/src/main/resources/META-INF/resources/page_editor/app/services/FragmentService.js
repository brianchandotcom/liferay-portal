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

import {config} from '../config/index';
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
	 * @param {string} options.fragmentEntryLinkId Id of the Fragment
	 * @param {function} options.onNetworkStatus
	 * @param {number} [options.parentCommentId=0]
	 * @return {Promise<FragmentComment>} Created FragmentComment
	 */
	addComment({
		body,
		fragmentEntryLinkId,
		onNetworkStatus,
		parentCommentId = 0
	}) {
		return serviceFetch(
			config.addFragmentEntryLinkCommentURL,
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
	 * @param {string} options.fragmentEntryKey Key of the Fragment
	 * @param {string} options.groupId GroupId that wraps the Fragment
	 * @param {function} options.onNetworkStatus
	 * @param {string} options.parentItemId
	 * @param {number} options.position
	 * @param {string} options.segmentsExperienceId Current segmentsExperienceId
	 * @return {Promise<FragmentEntryLink>} Created FragmentEntryLink
	 */
	addFragmentEntryLink({
		fragmentEntryKey,
		groupId,
		onNetworkStatus,
		parentItemId,
		position,
		segmentsExperienceId
	}) {
		return serviceFetch(
			config.addFragmentEntryLinkURL,
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
	 * @param {function} options.onNetworkStatus
	 * @return {Promise<void>}
	 */
	deleteComment({commentId, onNetworkStatus}) {
		return serviceFetch(
			config.deleteFragmentEntryLinkCommentURL,
			{
				body: {commentId}
			},
			onNetworkStatus
		);
	},

	/**
	 * Duplicates a fragmentEntryLink
	 * @param {object} options
	 * @param {string} options.itemId id of the item
	 * @param {function} options.onNetworkStatus
	 * @param {string} options.segmentsExperienceId Experience id
	 */
	duplicateItem({itemId, onNetworkStatus, segmentsExperienceId}) {
		return serviceFetch(
			config.duplicateItemURL,
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
	 * @param {function} options.onNetworkStatus
	 * @param {boolean} options.resolved Whether the comment should be marked as resolved or not
	 * @return {Promise<FragmentComment>} Created FragmentComment
	 */
	editComment({body, commentId, onNetworkStatus, resolved}) {
		return serviceFetch(
			config.editFragmentEntryLinkCommentURL,
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
	 * @param {string} options.fragmentEntryLinkId Id of the fragmentEntryLink
	 * @param {function} options.onNetworkStatus
	 * @param {string} options.segmentsExperienceId Experience id
	 */
	renderFragmentEntryLinkContent({
		fragmentEntryLinkId,
		onNetworkStatus,
		segmentsExperienceId
	}) {
		return serviceFetch(
			config.renderFragmentEntryURL,
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
	 * @param {string} options.editableValues New editableValues
	 * @param {string} options.fragmentEntryLinkId Id of the fragmentEntryLink
	 * @param {function} options.onNetworkStatus
	 */
	updateEditableValues({
		editableValues,
		fragmentEntryLinkId,
		onNetworkStatus
	}) {
		return serviceFetch(
			config.editFragmentEntryLinkURL,
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
