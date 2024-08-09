/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import duplicateItem from '../../../../../src/main/resources/META-INF/resources/page_editor/app/actions/duplicateItem';
import {getItemNameFromAction} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/components/undo/getItemNameFromAction';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/layoutDataItemTypes';

const STATE = {
	layoutData: {
		items: {
			item01: {
				itemId: 'item01',
				type: LAYOUT_DATA_ITEM_TYPES.container,
			},
			item02: {
				itemId: 'item02',
				type: LAYOUT_DATA_ITEM_TYPES.container,
			},
		},
	},
};

describe('getItemNameFromAction', () => {
	const getAction = (itemIds) => ({
		...duplicateItem({itemIds}),
		rule: null,
	});

	it('returns the fragment type when the action is on a single item', () => {
		expect(
			getItemNameFromAction({
				action: getAction(['item01']),
				state: STATE,
			})
		).toStrictEqual('container');
	});

	it('returns "elements" when the action is on multiple items', () => {
		expect(
			getItemNameFromAction({
				action: getAction(['item01', 'item02']),
				state: STATE,
			})
		).toStrictEqual('elements');
	});
});
