/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import updateCollectionDisplayCollectionAction from '../../../../src/main/resources/META-INF/resources/page_editor/app/actions/updateCollectionDisplayCollection';
import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/freemarkerFragmentEntryProcessor';
import LayoutService from '../../../../src/main/resources/META-INF/resources/page_editor/app/services/LayoutService';
import updateCollectionDisplayCollection from '../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/updateCollectionDisplayCollection';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/actions/updateCollectionDisplayCollection',
	() => jest.fn()
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/services/LayoutService',
	() => ({
		updateCollectionDisplayConfig: jest.fn(),
	})
);

const COLLECTION_ITEM_ID = 'collection-item-1';

const FILTER_FRAGMENT_ENTRY_LINK = {
	configuration: {
		fieldSets: [
			{
				fields: [
					{
						name: 'targetCollections',
						type: 'targetCollectionDisplay',
					},
				],
			},
		],
	},
	editableValues: {
		[FREEMARKER_FRAGMENT_ENTRY_PROCESSOR]: {
			filterKey: 'keywords',
			targetCollections: [COLLECTION_ITEM_ID, 'collection-item-2'],
		},
	},
	fragmentEntryLinkId: 'filter-1',
};

const UNRELATED_FRAGMENT_ENTRY_LINK = {
	configuration: {
		fieldSets: [
			{
				fields: [
					{
						name: 'someField',
						type: 'text',
					},
				],
			},
		],
	},
	editableValues: {},
	fragmentEntryLinkId: 'unrelated-1',
};

describe('updateCollectionDisplayCollection', () => {
	afterEach(() => {
		LayoutService.updateCollectionDisplayConfig.mockClear();
		updateCollectionDisplayCollectionAction.mockClear();
	});

	it('sends editableValuesChanges with the itemId removed from targetCollections', async () => {
		LayoutService.updateCollectionDisplayConfig.mockImplementation(() =>
			Promise.resolve({
				fragmentEntryLinks: [],
				layoutData: {items: {}, version: 1},
			})
		);

		const dispatch = jest.fn();
		const getState = () => ({
			fragmentEntryLinks: {
				'filter-1': FILTER_FRAGMENT_ENTRY_LINK,
				'unrelated-1': UNRELATED_FRAGMENT_ENTRY_LINK,
			},
			languageId: 'en_US',
			segmentsExperienceId: '0',
		});

		await updateCollectionDisplayCollection({
			collection: {title: 'New Collection'},
			itemId: COLLECTION_ITEM_ID,
			listStyle: '',
		})(dispatch, getState);

		const call =
			LayoutService.updateCollectionDisplayConfig.mock.calls[0][0];

		expect(call.editableValuesChanges).toEqual({
			'filter-1': {
				[FREEMARKER_FRAGMENT_ENTRY_PROCESSOR]: {
					filterKey: 'keywords',
					targetCollections: ['collection-item-2'],
				},
			},
		});
	});

	it('clears filterKey when targetCollections becomes empty', async () => {
		const singleTargetFragment = {
			...FILTER_FRAGMENT_ENTRY_LINK,
			editableValues: {
				[FREEMARKER_FRAGMENT_ENTRY_PROCESSOR]: {
					filterKey: 'keywords',
					targetCollections: [COLLECTION_ITEM_ID],
				},
			},
		};

		LayoutService.updateCollectionDisplayConfig.mockImplementation(() =>
			Promise.resolve({
				fragmentEntryLinks: [],
				layoutData: {items: {}, version: 1},
			})
		);

		const dispatch = jest.fn();
		const getState = () => ({
			fragmentEntryLinks: {
				'filter-1': singleTargetFragment,
			},
			languageId: 'en_US',
			segmentsExperienceId: '0',
		});

		await updateCollectionDisplayCollection({
			collection: {title: 'New Collection'},
			itemId: COLLECTION_ITEM_ID,
			listStyle: '',
		})(dispatch, getState);

		const call =
			LayoutService.updateCollectionDisplayConfig.mock.calls[0][0];

		expect(call.editableValuesChanges).toEqual({
			'filter-1': {
				[FREEMARKER_FRAGMENT_ENTRY_PROCESSOR]: {
					filterKey: '',
					targetCollections: [],
				},
			},
		});
	});

	it('sends empty editableValuesChanges when no fragments are affected', async () => {
		LayoutService.updateCollectionDisplayConfig.mockImplementation(() =>
			Promise.resolve({
				fragmentEntryLinks: [],
				layoutData: {items: {}, version: 1},
			})
		);

		const dispatch = jest.fn();
		const getState = () => ({
			fragmentEntryLinks: {
				'unrelated-1': UNRELATED_FRAGMENT_ENTRY_LINK,
			},
			languageId: 'en_US',
			segmentsExperienceId: '0',
		});

		await updateCollectionDisplayCollection({
			collection: {title: 'New Collection'},
			itemId: COLLECTION_ITEM_ID,
			listStyle: '',
		})(dispatch, getState);

		const call =
			LayoutService.updateCollectionDisplayConfig.mock.calls[0][0];

		expect(call.editableValuesChanges).toEqual({});
	});

	it('dispatches the action with the response', async () => {
		const mockResponse = {
			fragmentEntryLinks: [{fragmentEntryLinkId: 'filter-1'}],
			layoutData: {items: {}, version: 1},
		};

		LayoutService.updateCollectionDisplayConfig.mockImplementation(() =>
			Promise.resolve(mockResponse)
		);

		const dispatch = jest.fn();
		const getState = () => ({
			fragmentEntryLinks: {},
			languageId: 'en_US',
			segmentsExperienceId: '0',
		});

		await updateCollectionDisplayCollection({
			collection: {title: 'New Collection'},
			itemId: COLLECTION_ITEM_ID,
			listStyle: '',
		})(dispatch, getState);

		expect(updateCollectionDisplayCollectionAction).toHaveBeenCalledWith({
			fragmentEntryLinks: mockResponse.fragmentEntryLinks,
			itemId: COLLECTION_ITEM_ID,
			layoutData: mockResponse.layoutData,
		});
	});
});
