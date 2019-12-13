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

export default function fragmentEntryLinksReducer(state, action) {
	let nextState = state;

	switch (action.type) {
		case TYPES.ADD_FRAGMENT_ENTRY_LINK:
			nextState = {
				...nextState,
				fragmentEntryLinks: {
					...nextState.fragmentEntryLinks,
					[action.fragmentEntryLink.fragmentEntryLinkId]:
						action.fragmentEntryLink
				}
			};
			break;
		case TYPES.ADD_FRAGMENT_ENTRY_LINK_COMMENT:
			{
				const fragmentEntryLink =
					nextState.fragmentEntryLinks[action.fragmentEntryLinkId];

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
					nextComments = [
						...comments,
						action.fragmentEntryLinkComment
					];
				}

				nextState = {
					...nextState,
					fragmentEntryLinks: {
						...nextState.fragmentEntryLinks,
						[action.fragmentEntryLinkId]: {
							...fragmentEntryLink,
							comments: nextComments
						}
					}
				};
			}
			break;
		case TYPES.DELETE_FRAGMENT_ENTRY_LINK_COMMENT:
			{
				const fragmentEntryLink =
					nextState.fragmentEntryLinks[action.fragmentEntryLinkId];

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

				nextState = {
					...nextState,
					fragmentEntryLinks: {
						...nextState.fragmentEntryLinks,
						[action.fragmentEntryLinkId]: {
							...fragmentEntryLink,
							comments: nextComments
						}
					}
				};
			}
			break;
		case TYPES.EDIT_FRAGMENT_ENTRY_LINK_COMMENT:
			{
				const fragmentEntryLink =
					nextState.fragmentEntryLinks[action.fragmentEntryLinkId];

				const {comments = []} = fragmentEntryLink;

				let nextComments;

				if (action.parentCommentId) {
					nextComments = comments.map(comment =>
						comment.commentId === action.parentCommentId
							? {
									...comment,
									children: comment.children.map(
										childComment =>
											childComment.commentId ===
											action.fragmentEntryLinkComment
												.commentId
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

				nextState = {
					...nextState,
					fragmentEntryLinks: {
						...nextState.fragmentEntryLinks,
						[action.fragmentEntryLinkId]: {
							...fragmentEntryLink,
							comments: nextComments
						}
					}
				};
			}
			break;
		default:
			break;
	}

	return nextState;
}
