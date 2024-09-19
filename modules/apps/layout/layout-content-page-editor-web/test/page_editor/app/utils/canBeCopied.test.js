/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LAYOUT_DATA_ITEM_TYPES} from '../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/layoutDataItemTypes';
import canBeCopied from '../../../../src/main/resources/META-INF/resources/page_editor/app/utils/canBeCopied';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/config/index',
	() => ({
		config: {
			formTypes: [
				{
					value: '01', // same value as classNameId on isFormMapped condition
				},
			],
		},
	})
);

const WIDGET_TYPE = 'widget';
const INPUT_TYPE = 'input';

const getRoot = () => ({
	root01: {
		config: {},
		itemId: 'root01',
		type: LAYOUT_DATA_ITEM_TYPES.root,
	},
});

const getFragment = (type = LAYOUT_DATA_ITEM_TYPES.fragment) => ({
	fragment01: {
		config: {fragmentEntryLinkId: '01'},
		itemId: 'fragment01',
		parentId: getRoot().root01.itemId,
		type,
	},
});

const getCollection = (isCollectionMapped = false) => ({
	collection01: {
		config: {
			...(isCollectionMapped && {
				collection: {},
			}),
		},
		itemId: 'collection01',
		parentId: getRoot().root01.itemId,
		type: LAYOUT_DATA_ITEM_TYPES.collection,
	},
});

const getCollectionItem = () => ({
	collectionItem01: {
		config: {},
		itemId: 'collectionItem01',
		parentId: getCollection().collection01.itemId,
		type: LAYOUT_DATA_ITEM_TYPES.collectionItem,
	},
});

const getForm = (isFormMapped = false) => ({
	form01: {
		config: {
			...(isFormMapped && {
				classNameId: '01',
				classTypeId: '0',
			}),
		},
		itemId: 'form01',
		parentId: getRoot().root01.itemId,
		type: LAYOUT_DATA_ITEM_TYPES.form,
	},
});

const getFragmentEntryLinks = (fragmentEntryType = 'component') => ({
	'01': {
		fragmentEntryLinkId: getFragment().fragment01.fragmentEntryLinkId,
		fragmentEntryType,
		name: 'Fragment',
		...(fragmentEntryType === WIDGET_TYPE && {portletId: 'Widget'}),
	},
});

const getLayoutData = ({
	fragmentType = LAYOUT_DATA_ITEM_TYPES.fragment,
	isCollectionMapped = false,
	isFormMapped = false,
}) => ({
	items: {
		...getFragment(fragmentType),
		...getForm(isFormMapped),
		...getRoot(),
		...getCollection(isCollectionMapped),
		...getCollectionItem(),
	},
	rootItems: {
		main: getRoot().root01.itemId,
	},
});

describe('canBeCopied', () => {
	it('can copy into the root item', () => {
		expect(
			canBeCopied(
				[getFragment().fragment01.itemId],
				getFragmentEntryLinks(),
				[getRoot().root01.itemId],
				getLayoutData({})
			)
		).toBe(true);
	});

	it('can copy into the same fragment', () => {
		expect(
			canBeCopied(
				[getFragment().fragment01.itemId],
				getFragmentEntryLinks(),
				[getFragment().fragment01.itemId],
				getLayoutData({})
			)
		).toBe(true);
	});

	it('can copy the fragment in a container', () => {
		expect(
			canBeCopied(
				[getFragment().fragment01.itemId],
				getFragmentEntryLinks(),
				[getFragment().fragment01.itemId],
				getLayoutData({fragmentType: LAYOUT_DATA_ITEM_TYPES.container})
			)
		).toBe(true);
	});

	it('cannot copy into the master page dropzone fragment', () => {
		expect(
			canBeCopied(
				[getFragment().fragment01.itemId],
				getFragmentEntryLinks(),
				[getFragment().fragment01.itemId],
				getLayoutData({fragmentType: LAYOUT_DATA_ITEM_TYPES.dropZone})
			)
		).toBe(false);
	});

	it('cannot copy the fragment into an unmapped collection', () => {
		expect(
			canBeCopied(
				[getFragment().fragment01.itemId],
				getFragmentEntryLinks(),
				[getCollection().collection01.itemId],
				getLayoutData({})
			)
		).toBe(false);
	});

	it('can copy fragment into a mapped collection', () => {
		expect(
			canBeCopied(
				[getFragment().fragment01.itemId],
				getFragmentEntryLinks(),
				[getCollectionItem().collectionItem01.itemId],
				getLayoutData({isCollectionMapped: true})
			)
		).toBe(true);
	});

	it('can not copy the fragment into an unmapped form', () => {
		expect(
			canBeCopied(
				[getFragment().fragment01.itemId],
				getFragmentEntryLinks(),
				[getForm().form01.itemId],
				getLayoutData({})
			)
		).toBe(false);
	});

	it('can copy the fragment into a mapped form', () => {
		expect(
			canBeCopied(
				[getFragment().fragment01.itemId],
				getFragmentEntryLinks(),
				[getForm().form01.itemId],
				getLayoutData({isFormMapped: true})
			)
		).toBe(true);
	});

	it('cannot copy the widget into a mapped form', () => {
		expect(
			canBeCopied(
				[getFragment().fragment01.itemId],
				getFragmentEntryLinks(WIDGET_TYPE),
				[getForm().form01.itemId],
				getLayoutData({isFormMapped: true})
			)
		).toBe(false);
	});

	it('cannot copy a form field outside of a mapped form', () => {
		expect(
			canBeCopied(
				[getFragment().fragment01.itemId],
				getFragmentEntryLinks(INPUT_TYPE),
				[getCollectionItem().collectionItem01.itemId],
				getLayoutData({isFormMapped: true})
			)
		).toBe(false);
	});

	it('can copy a form field inside of a mapped form', () => {
		expect(
			canBeCopied(
				[getFragment().fragment01.itemId],
				getFragmentEntryLinks(INPUT_TYPE),
				[getForm().form01.itemId],
				getLayoutData({isFormMapped: true})
			)
		).toBe(true);
	});
});
