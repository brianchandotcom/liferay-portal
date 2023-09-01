/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import addFragmentComposition from '../actions/addFragmentComposition';
import toggleFragmentHighlighted from '../actions/toggleFragmentHighlighted';
import {
	ADD_FRAGMENT_COMPOSITION,
	TOGGLE_FRAGMENT_HIGHLIGHTED,
	UPDATE_FRAGMENTS,
} from '../actions/types';
import updateFragments, {FragmentSet} from '../actions/updateFragments';
import {HIGHLIGHTED_COLLECTION_ID} from '../config/constants/highlightedCollectionId';

const DEFAULT_HIGHLIGHTED_COLLECTION: FragmentSet = {
	fragmentCollectionId: HIGHLIGHTED_COLLECTION_ID,
	fragmentEntries: [],
	name: Liferay.Language.get('favorites'),
};

export default function fragmentsReducer(
	fragments: FragmentSet[] = [],
	action: ReturnType<
		| typeof addFragmentComposition
		| typeof toggleFragmentHighlighted
		| typeof updateFragments
	>
): FragmentSet[] {
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
