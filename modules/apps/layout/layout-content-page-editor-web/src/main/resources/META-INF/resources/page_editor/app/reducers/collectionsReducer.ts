/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import addFragmentComposition from '../actions/addFragmentComposition';
import {ADD_FRAGMENT_COMPOSITION} from '../actions/types';

interface Collection {
	fragmentCollectionId: string;
	name: string;
}

export default function collectionsReducer(
	collections: Collection[] = [],
	action: ReturnType<typeof addFragmentComposition>
) {
	switch (action.type) {
		case ADD_FRAGMENT_COMPOSITION: {
			const composition = action.fragmentComposition;
			const existingCollection = collections.find(
				(collection) =>
					collection.fragmentCollectionId ===
					composition.fragmentCollectionId
			);

			if (!existingCollection) {
				return [
					...collections,
					{
						fragmentCollectionId: composition.fragmentCollectionId,
						name: composition.fragmentCollectionName,
					},
				];
			}

			return collections;
		}

		default:
			return collections;
	}
}
