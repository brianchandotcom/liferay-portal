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
	ADD_FRAGMENT_COMPOSITION,
	TOGGLE_FRAGMENT_HIGHLIGHTED,
	UPDATE_FRAGMENTS,
} from '../actions/types';
import {HIGHLIGHTED_COLLECTION_ID} from '../config/constants/highlightedCollectionId';

const DEFAULT_HIGHLIGHTED_COLLECTION = {
	fragmentCollectionId: HIGHLIGHTED_COLLECTION_ID,
	fragmentEntries: [],
	name: Liferay.Language.get('favorites'),
};

export default function fragmentsReducer(fragments = [], action) {
	switch (action.type) {
		case ADD_FRAGMENT_COMPOSITION: {
			const composition = action.fragmentComposition;
			const existingCollection = fragments.find(
				(collection) =>
					collection.fragmentCollectionId ===
					composition.fragmentCollectionId
			);

			const newCollection = existingCollection
				? {
						...existingCollection,
						fragmentEntries: [
							...existingCollection.fragmentEntries,
							composition,
						],
				  }
				: {
						fragmentCollectionId: composition.fragmentCollectionId,
						fragmentEntries: [composition],
						name: composition.fragmentCollectionName,
				  };

			return [
				...fragments.filter(
					(collection) =>
						collection.fragmentCollectionId !==
						newCollection.fragmentCollectionId
				),

				newCollection,
			];
		}

		case TOGGLE_FRAGMENT_HIGHLIGHTED: {
			const {
				fragmentEntryKey,
				groupId,
				highlighted,
				highlightedFragments,
			} = action;

			const nextFragments = [];

			fragments.forEach((collection) => {
				if (
					collection.fragmentCollectionId ===
					HIGHLIGHTED_COLLECTION_ID
				) {
					return;
				}

				nextFragments.push({
					...collection,
					fragmentEntries: collection.fragmentEntries.map(
						(fragment) => {
							const fragmentGroupId = fragment.groupId || '0';

							if (
								fragment.fragmentEntryKey !==
									fragmentEntryKey ||
								fragmentGroupId !== groupId
							) {
								return fragment;
							}

							return {...fragment, highlighted};
						}
					),
				});
			});

			if (highlightedFragments.length) {
				nextFragments.unshift({
					...DEFAULT_HIGHLIGHTED_COLLECTION,
					fragmentEntries: highlightedFragments,
				});
			}

			return nextFragments;
		}

		case UPDATE_FRAGMENTS: {
			return action.fragments || fragments;
		}

		default:
			return fragments;
	}
}
