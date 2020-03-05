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

import {ADD_FRAGMENT_COMPOSITION} from '../actions/types';

export default function fragmentsReducer(fragments = [], action) {
	let fragmentCollection;

	switch (action.type) {
		case ADD_FRAGMENT_COMPOSITION:
			fragmentCollection = fragments.find(
				fragmentCollection =>
					fragmentCollection.fragmentCollectionId ===
					action.fragmentComposition.fragmentCollectionId
			);

			if (fragmentCollection) {
				fragmentCollection.fragmentEntries.push(
					action.fragmentComposition
				);
			}
			else {
				fragments.push({
					fragmentCollectionId:
						action.fragmentComposition.fragmentCollectionId,
					fragmentEntries: [action.fragmentComposition],
					name: action.fragmentComposition.fragmentCollectionName,
				});
			}

			return fragments;

		default:
			return fragments;
	}
}
