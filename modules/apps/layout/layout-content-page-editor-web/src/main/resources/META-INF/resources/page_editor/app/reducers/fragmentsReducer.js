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
	INIT,
	TOGGLE_FRAGMENT_HIGHLIGHTED,
} from '../actions/types';
import {LAYOUT_DATA_ITEM_TYPE_LABELS} from '../config/constants/layoutDataItemTypeLabels';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {config} from '../config/index';

const CONTENT_DISPLAY_COLLECTION_ID = 'content-display';

const DEFAULT_CONTENT_DISPLAY_COLLECTION = {
	fragmentCollectionId: 'collection-display',
	fragmentEntries: [],
	name: Liferay.Language.get('collection-display'),
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

		case INIT: {
			const contentDisplayCollection = fragments.find(
				(fragment) =>
					fragment.fragmentCollectionId ===
					CONTENT_DISPLAY_COLLECTION_ID
			);

			const newFragments = fragments.filter(
				(fragment) =>
					fragment.fragmentCollectionId !==
					CONTENT_DISPLAY_COLLECTION_ID
			);

			newFragments.unshift({
				fragmentCollectionId: 'layout-elements',
				fragmentEntries: [
					{
						fragmentEntryKey: 'container',
						icon: 'container',
						itemType: LAYOUT_DATA_ITEM_TYPES.container,
						name:
							LAYOUT_DATA_ITEM_TYPE_LABELS[
								LAYOUT_DATA_ITEM_TYPES.container
							],
					},
					{
						fragmentEntryKey: 'row',
						icon: 'table',
						itemType: LAYOUT_DATA_ITEM_TYPES.row,
						name:
							LAYOUT_DATA_ITEM_TYPE_LABELS[
								LAYOUT_DATA_ITEM_TYPES.row
							],
					},
				],
				name: Liferay.Language.get('layout-elements'),
			});

			let formComponentsCollection = {fragmentEntries: []};

			const formComponentsCollectionIndex = newFragments.findIndex(
				(collection) => collection.fragmentCollectionId === 'INPUTS'
			);

			if (formComponentsCollectionIndex !== -1) {
				[formComponentsCollection] = newFragments.splice(
					formComponentsCollectionIndex,
					1
				);
			}

			if (config.featureFlagLps150277) {
				newFragments.splice(2, 0, {
					fragmentCollectionId: 'form-components',
					fragmentEntries: [
						{
							fragmentEntryKey: 'form',
							icon: 'container',
							itemType: LAYOUT_DATA_ITEM_TYPES.form,
							name:
								LAYOUT_DATA_ITEM_TYPE_LABELS[
									LAYOUT_DATA_ITEM_TYPES.form
								],
						},
						...formComponentsCollection.fragmentEntries,
					],
					name: Liferay.Language.get('form-components'),
				});
			}

			newFragments.splice(3, 0, {
				...(contentDisplayCollection ||
					DEFAULT_CONTENT_DISPLAY_COLLECTION),

				fragmentEntries: [
					...(
						contentDisplayCollection ||
						DEFAULT_CONTENT_DISPLAY_COLLECTION
					).fragmentEntries,

					{
						fragmentEntryKey: 'collection-display',
						icon: 'list',
						itemType: LAYOUT_DATA_ITEM_TYPES.collection,
						name: Liferay.Language.get('collection-display'),
					},
				],
			});

			return newFragments;
		}

		case TOGGLE_FRAGMENT_HIGHLIGHTED: {
			const {fragmentEntryKey, highlighted} = action;

			const nextFragments = fragments.map((collection) => ({
				...collection,
				fragmentEntries: collection.fragmentEntries.map((fragment) =>
					fragment.fragmentEntryKey === fragmentEntryKey
						? {...fragment, highlighted}
						: fragment
				),
			}));

			return nextFragments;
		}

		default:
			return fragments;
	}
}
