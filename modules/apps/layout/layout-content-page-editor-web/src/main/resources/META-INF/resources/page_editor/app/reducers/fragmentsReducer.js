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
} from '../actions/types';

const HIGHLIGHTED_COLLECTION_ID = 'highlighted';

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
				highlighted,
				highlightedFragments,
			} = action;

			const nextFragments = fragments.reduce(
				(collections, collection) => {
					if (
						collection.fragmentCollectionId !==
						HIGHLIGHTED_COLLECTION_ID
					) {
						collections.push({
							...collection,
							fragmentEntries: collection.fragmentEntries.map(
								(fragment) =>
									fragment.fragmentEntryKey ===
									fragmentEntryKey
										? {...fragment, highlighted}
										: fragment
							),
						});
					}

					return collections;
				},
				[]
			);

			if (highlightedFragments.length) {
				nextFragments.unshift({
					...DEFAULT_HIGHLIGHTED_COLLECTION,
					fragmentEntries: highlightedFragments,
				});
			}

			return nextFragments;
		}

		default:
			return fragments;
	}
}
