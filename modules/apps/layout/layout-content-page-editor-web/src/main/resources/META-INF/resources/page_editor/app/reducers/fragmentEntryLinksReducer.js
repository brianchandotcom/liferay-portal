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

import {TYPES} from '../actions/index';

export default function fragmentEntryLinksReducer(fragmentEntryLinks, action) {
	switch (action.type) {
		case TYPES.ADD_FRAGMENT_ENTRY_LINK:
			return {
				...fragmentEntryLinks,
				[action.fragmentEntryLink.fragmentEntryLinkId]:
					action.fragmentEntryLink
			};

		case TYPES.ADD_FRAGMENT_ENTRY_LINK_COMMENT: {
			const fragmentEntryLink =
				fragmentEntryLinks[action.fragmentEntryLinkId];

			const {comments = []} = fragmentEntryLink;

			let nextComments;

			if (action.parentCommentId) {
				nextComments = comments.map(comment =>
					comment.commentId === action.parentCommentId
						? {
								...comment,
								children: [
									...(comment.children || []),
									action.fragmentEntryLinkComment
								]
						  }
						: comment
				);
			} else {
				nextComments = [...comments, action.fragmentEntryLinkComment];
			}

			return {
				...fragmentEntryLinks,
				[action.fragmentEntryLinkId]: {
					...fragmentEntryLink,
					comments: nextComments
				}
			};
		}

		case TYPES.DELETE_FRAGMENT_ENTRY_LINK_COMMENT: {
			const fragmentEntryLink =
				fragmentEntryLinks[action.fragmentEntryLinkId];

			const {comments = []} = fragmentEntryLink;

			let nextComments;

			if (action.parentCommentId) {
				nextComments = comments.map(comment =>
					comment.commentId === action.parentCommentId
						? {
								...comment,
								children: comment.children.filter(
									childComment =>
										childComment.commentId !==
										action.commentId
								)
						  }
						: comment
				);
			} else {
				nextComments = comments.filter(
					comment => comment.commentId !== action.commentId
				);
			}

			return {
				...fragmentEntryLinks,
				[action.fragmentEntryLinkId]: {
					...fragmentEntryLink,
					comments: nextComments
				}
			};
		}

		case TYPES.EDIT_FRAGMENT_ENTRY_LINK_COMMENT: {
			const fragmentEntryLink =
				fragmentEntryLinks[action.fragmentEntryLinkId];

			const {comments = []} = fragmentEntryLink;

			let nextComments;

			if (action.parentCommentId) {
				nextComments = comments.map(comment =>
					comment.commentId === action.parentCommentId
						? {
								...comment,
								children: comment.children.map(childComment =>
									childComment.commentId ===
									action.fragmentEntryLinkComment.commentId
										? action.fragmentEntryLinkComment
										: childComment
								)
						  }
						: comment
				);
			} else {
				nextComments = comments.map(comment =>
					comment.commentId ===
					action.fragmentEntryLinkComment.commentId
						? {...comment, ...action.fragmentEntryLinkComment}
						: comment
				);
			}

			return {
				...fragmentEntryLinks,
				[action.fragmentEntryLinkId]: {
					...fragmentEntryLink,
					comments: nextComments
				}
			};
		}

		case TYPES.UPDATE_EDITABLE_VALUES:
			return {
				...fragmentEntryLinks,
				[action.fragmentEntryLinkId]: {
					...fragmentEntryLinks[action.fragmentEntryLinkId],
					editableValues: action.editableValues
				}
			};

		case TYPES.UPDATE_LAYOUT_DATA: {
			const nextFragmentEntryLinks = {...fragmentEntryLinks};

			action.deletedFragmentEntryLinkIds.forEach(fragmentEntryLinkId => {
				delete nextFragmentEntryLinks[fragmentEntryLinkId];
			});

			action.addedFragmentEntryLinks.forEach(fragmentEntryLink => {
				nextFragmentEntryLinks[
					fragmentEntryLink.fragmentEntryLinkId
				] = fragmentEntryLink;
			});

			return nextFragmentEntryLinks;
		}

		case TYPES.UPDATE_FRAGMENT_ENTRY_LINK_CONTENT:
			return {
				...fragmentEntryLinks,
				[action.fragmentEntryLinkId]: {
					...fragmentEntryLinks[action.fragmentEntryLinkId],
					content: action.content
				}
			};

		default:
			return fragmentEntryLinks;
	}
}
