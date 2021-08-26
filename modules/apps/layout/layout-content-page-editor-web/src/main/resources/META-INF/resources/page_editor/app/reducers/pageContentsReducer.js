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

import {
	UPDATE_COLLECTION_DISPLAY_COLLECTION,
	UPDATE_PAGE_CONTENTS,
	UPDATE_PREVIEW_IMAGE,
} from '../actions/types';

const INITIAL_STATE = [];

export default function pageContentsReducer(
	pageContents = INITIAL_STATE,
	action
) {
	switch (action.type) {
		case UPDATE_COLLECTION_DISPLAY_COLLECTION:
		case UPDATE_PAGE_CONTENTS:
			return [...action.pageContents];

		case UPDATE_PREVIEW_IMAGE: {
			const nextPageContents = pageContents.map((pageContent) => {
				if (pageContent.classPK === action.fileEntryId) {
					return {
						...pageContent,
						actions: {
							...pageContent.actions,
							editImage: {
								...pageContent.actions.editImage,
								previewURL: action.previewURL,
							},
						},
					};
				}
				else {
					return pageContent;
				}
			});

			return nextPageContents;
		}

		default:
			return pageContents;
	}
}
