/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/freemarkerFragmentEntryProcessor';
import getTargetCollectionDisplayField from '../../../../src/main/resources/META-INF/resources/page_editor/app/utils/getTargetCollectionDisplayField';

const FRAGMENT_ENTRY_LINK_BASE = {
	comments: [],
	configuration: {},
	content: '',
	cssClass: '',
	defaultConfigurationValues: {},
	editableTypes: {},
	editableValues: {},
	fieldTypes: [],
	fragmentEntryId: '0',
	fragmentEntryKey: 'test-key',
	fragmentEntryLinkId: '1',
	fragmentEntryType: 'component',
	groupId: '0',
	icon: '',
	name: 'Test Fragment',
	removed: false,
	segmentsExperienceId: '0',
};

describe('getTargetCollectionDisplayField', () => {
	it('returns null when fragment has no configuration fieldSets', () => {
		const fragmentEntryLink = {
			...FRAGMENT_ENTRY_LINK_BASE,
			configuration: {},
		};

		expect(getTargetCollectionDisplayField(fragmentEntryLink)).toBeNull();
	});

	it('returns null when fragment has no targetCollectionDisplay field', () => {
		const fragmentEntryLink = {
			...FRAGMENT_ENTRY_LINK_BASE,
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
		};

		expect(getTargetCollectionDisplayField(fragmentEntryLink)).toBeNull();
	});

	it('returns fieldName and targetCollections when field exists with values', () => {
		const fragmentEntryLink = {
			...FRAGMENT_ENTRY_LINK_BASE,
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
					targetCollections: ['item-1', 'item-2'],
				},
			},
		};

		expect(getTargetCollectionDisplayField(fragmentEntryLink)).toEqual({
			fieldName: 'targetCollections',
			targetCollections: ['item-1', 'item-2'],
		});
	});

	it('returns empty targetCollections array when field exists but has no value in editableValues', () => {
		const fragmentEntryLink = {
			...FRAGMENT_ENTRY_LINK_BASE,
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
			editableValues: {},
		};

		expect(getTargetCollectionDisplayField(fragmentEntryLink)).toEqual({
			fieldName: 'targetCollections',
			targetCollections: [],
		});
	});
});
