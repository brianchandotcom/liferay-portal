/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import duplicateItem from '../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/duplicateItem';

const STATE = {
	layoutData: {
		items: {
			button01: {
				children: [],
				itemId: 'button01',
				parentId: 'root',
				type: 'fragment',
			},
			heading01: {
				children: [],
				itemId: 'heading01',
				parentId: 'root',
				type: 'fragment',
			},
			root: {
				children: ['heading01', 'button01'],
				itemId: 'root',
				parentId: '',
				type: 'root',
			},
		},
	},
};

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/services/FragmentService',
	() => ({
		duplicateItem: jest.fn(() =>
			Promise.resolve({
				duplicatedFragmentEntryLinks: [
					{
						fragmentEntryLinkId: '33193',
						name: 'Heading',
					},
					{
						fragmentEntryLinkId: '33194',
						name: 'Button',
					},
				],
				duplicatedItemIds: ['heading02', 'button02'],
				layoutData: {
					items: {
						button01: {
							children: [],
							config: {
								fragmentEntryLinkId: '33192',
							},
							itemId: 'button01',
							parentId: 'root',
							type: 'fragment',
						},
						button02: {
							children: [],
							config: {
								fragmentEntryLinkId: '33194',
							},
							itemId: 'button02',
							parentId: 'root',
							type: 'fragment',
						},
						heading01: {
							children: [],
							config: {
								fragmentEntryLinkId: '33191',
							},
							itemId: 'heading01',
							parentId: 'root',
							type: 'fragment',
						},
						heading02: {
							children: [],
							config: {
								fragmentEntryLinkId: '33193',
							},
							itemId: 'heading02',
							parentId: 'root',
							type: 'fragment',
						},
						root: {
							children: [
								'heading01',
								'heading02',
								'button01',
								'button02',
							],
							config: {},
							itemId: 'root',
							parentId: '',
							type: 'root',
						},
					},
				},
				restrictedItemIds: [],
			})
		),
	})
);

describe('duplicateItem', () => {
	it('calls dispatch with the correct params', async () => {
		const dispatch = jest.fn();
		const selectItems = jest.fn();

		await duplicateItem({itemIds: ['heading01', 'button01'], selectItems})(
			dispatch,
			() => STATE
		);

		expect(dispatch).toBeCalledWith(
			expect.objectContaining({
				addedFragmentEntryLinks: [
					{
						fragmentEntryLinkId: '33193',
						name: 'Heading',
					},
					{
						fragmentEntryLinkId: '33194',
						name: 'Button',
					},
				],
				itemIds: ['heading02', 'button02'],
				restrictedItemIds: [],
			})
		);
	});

	it('calls selecItems with the duplicated ids', async () => {
		const dispatch = jest.fn();
		const selectItems = jest.fn();

		await duplicateItem({itemIds: ['heading01', 'button01'], selectItems})(
			dispatch,
			() => STATE
		);

		expect(selectItems).toBeCalledWith('heading02', {
			origin: 'itemActions',
		});
	});

	it('calls selecItems with the duplicated ids (LPD-18221)', async () => {
		Liferay.FeatureFlags['LPD-18221'] = true;

		const dispatch = jest.fn();
		const selectItems = jest.fn();

		await duplicateItem({itemIds: ['heading01', 'button01'], selectItems})(
			dispatch,
			() => STATE
		);

		expect(selectItems).toBeCalledWith(['heading02', 'button02'], {
			origin: 'itemActions',
		});

		Liferay.FeatureFlags['LPD-18221'] = false;
	});
});
