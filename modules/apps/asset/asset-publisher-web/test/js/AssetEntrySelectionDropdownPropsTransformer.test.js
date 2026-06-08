/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openItemSelectorModal} from '@liferay/frontend-js-item-selector-web';

import propsTransformer from '../../src/main/resources/META-INF/resources/js/AssetEntrySelectionDropdownPropsTransformer';

jest.mock('@liferay/frontend-js-item-selector-web');

const ASSET_ENTRY_TYPES = [
	{classNameId: 1, label: 'Web Content'},
	{classNameId: 2, label: 'Blogs'},
];

function transform(additionalProps = {}) {
	return propsTransformer({
		additionalProps: {
			assetEntryTypes: ASSET_ENTRY_TYPES,
			currentURL: 'currentURL',
			groupId: 123,
			...additionalProps,
		},
		portletNamespace: 'ns',
	});
}

function open(additionalProps) {
	transform(additionalProps).onClick({preventDefault() {}});

	return openItemSelectorModal.mock.calls[0][0];
}

describe('AssetEntrySelectionDropdownPropsTransformer', () => {
	afterEach(() => {
		openItemSelectorModal.mockReset();
	});

	it('opens the asset entries endpoint scoped to the group and permitted types', () => {
		const config = open();

		expect(config.apiURL).toContain(
			'/o/headless-delivery/v1.0/asset-entries'
		);
		expect(config.apiURL).toContain('groupIds=123');
		expect(decodeURIComponent(config.apiURL.replace(/\+/g, '%20'))).toContain(
			'classNameId in (1,2)'
		);
		expect(config.multiSelect).toBe(true);
	});

	it('shows a type filter on classNameId when multiple types are permitted', () => {
		const [filter] = open().fdsProps.filters;

		expect(filter.id).toBe('classNameId');
		expect(filter.items).toEqual([
			{label: 'Web Content', value: '1'},
			{label: 'Blogs', value: '2'},
		]);
	});

	it('pins the type and hides the filter when one type is permitted', () => {
		const config = open({
			assetEntryTypes: [{classNameId: 9, label: 'Document'}],
		});

		expect(config.fdsProps.filters).toHaveLength(0);
		expect(decodeURIComponent(config.apiURL.replace(/\+/g, '%20'))).toContain('classNameId eq 9');
	});

	it('posts the selected asset entry ids and type on selection', () => {
		const form = document.createElement('form');

		document.nsfm = form;

		Liferay.Util.postForm = jest.fn();

		open().onItemsChange([
			{assetEntryId: 10, className: 'com.example.A'},
			{assetEntryId: 11, className: 'com.example.A'},
		]);

		expect(Liferay.Util.postForm).toHaveBeenCalledWith(form, {
			data: {
				assetEntryIds: '10,11',
				assetEntryType: 'com.example.A',
				cmd: 'add-selection',
				redirect: 'currentURL',
			},
		});
	});
});
