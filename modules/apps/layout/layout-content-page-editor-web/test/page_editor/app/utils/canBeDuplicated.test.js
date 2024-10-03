/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FRAGMENT_ENTRY_TYPES} from '../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/fragmentEntryTypes';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/layoutDataItemTypes';
import canBeDuplicated from '../../../../src/main/resources/META-INF/resources/page_editor/app/utils/canBeDuplicated';

function getFragment(
	{
		fieldTypes = [],
		fragmentEntryLinkId,
		fragmentEntryType = 'component',
		isWidget = false,
		itemId,
		parentId,
	} = {
		fieldTypes: [],
		fragmentEntryType: 'component',
		isWidget: false,
	}
) {
	return {
		children: [],
		config: {
			fragmentEntryLinkId,
		},
		fieldTypes,
		fragmentEntryType,
		isWidget,
		itemId: itemId || 'fragment-id',
		parentId,
		type: LAYOUT_DATA_ITEM_TYPES.fragment,
	};
}

describe('canBeDuplicated', () => {
	it('can not duplicate a Stepper fragment', () => {
		const stepper = getFragment({
			fieldTypes: ['stepper'],
			fragmentEntryLinkId: 'stepper-fragment',
			fragmentEntryType: FRAGMENT_ENTRY_TYPES.input,
		});

		const fragmentEntryLinks = {
			[stepper.config.fragmentEntryLinkId]: {
				editableValues: {},
				fieldTypes: ['stepper'],
				fragmentEntryLinkId: stepper.config.fragmentEntryLinkId,
			},
		};

		expect(canBeDuplicated(fragmentEntryLinks, stepper, {}, () => [])).toBe(
			false
		);
	});

	it('can only duplicate instanceable widgets', () => {
		const instanceableWidget = getFragment({
			fragmentEntryLinkId: 'instanceable',
		});

		const nonInstanceableWidget = getFragment({
			fragmentEntryLinkId: 'nonInstanceable',
		});

		const fragmentEntryLinks = {
			[instanceableWidget.config.fragmentEntryLinkId]: {
				editableValues: {portletId: 'instanceable'},
				fragmentEntryLinkId:
					instanceableWidget.config.fragmentEntryLinkId,
			},
			[nonInstanceableWidget.config.fragmentEntryLinkId]: {
				editableValues: {portletId: 'nonInstanceable'},
				fragmentEntryLinkId:
					nonInstanceableWidget.config.fragmentEntryLinkId,
			},
		};

		const widgets = [
			{
				categories: [],
				portlets: [
					{
						instanceable: true,
						portletId: 'instanceable',
					},
					{
						instanceable: false,
						portletId: 'nonInstanceable',
					},
				],
			},
		];

		expect(
			canBeDuplicated(
				fragmentEntryLinks,
				instanceableWidget,
				{},
				() => widgets
			)
		).toBe(true);

		expect(
			canBeDuplicated(
				fragmentEntryLinks,
				nonInstanceableWidget,
				{},
				() => widgets
			)
		).toBe(false);
	});
});
