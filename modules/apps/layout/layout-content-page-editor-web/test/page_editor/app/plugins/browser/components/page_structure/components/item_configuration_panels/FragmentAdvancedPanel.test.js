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

import '@testing-library/jest-dom/extend-expect';
import {render, screen} from '@testing-library/react';
import React from 'react';

import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/editableFragmentEntryProcessor';
import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/freemarkerFragmentEntryProcessor';
import {FragmentAdvancedPanel} from '../../../../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/browser/components/page_structure/components/item_configuration_panels/FragmentAdvancedPanel';
import StoreMother from '../../../../../../../../../src/main/resources/META-INF/resources/page_editor/test_utils/StoreMother';

const FRAGMENT_ENTRY_LINK_ID = '1';

const FRAGMENT_ENTRY_LINK_WITH_ADVANCED_CONFIG = {
	configuration: {
		fieldSets: [
			{
				configurationRole: 'advanced',
				fields: [
					{
						dataType: 'string',
						defaultValue: '1',
						description: '',
						label: 'Advanced Config Field',
						name: 'advancedConfigField',
						type: 'select',
						typeOptions: {
							validValues: [
								{label: '0', value: '0'},
								{label: '1', value: '1'},
							],
						},
					},
				],
				label: 'Advanced Config Fieldset',
			},
			{
				fields: [
					{
						dataType: 'string',
						defaultValue: '1',
						description: '',
						label: 'Standard Config Field',
						name: 'standardConfigField',
						type: 'select',
						typeOptions: {
							validValues: [
								{label: '0', value: '0'},
								{label: '1', value: '1'},
							],
						},
					},
				],
				label: 'Standard Config Fieldset',
			},
		],
	},
	editableValues: {
		[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {},
		[FREEMARKER_FRAGMENT_ENTRY_PROCESSOR]: {},
	},
	fragmentEntryLinkId: FRAGMENT_ENTRY_LINK_ID,
	name: 'Fragment',
};

const renderComponent = ({fragmentEntryLink = {}} = {}) =>
	render(
		<StoreMother.Component
			getState={() => ({
				fragmentEntryLinks: {
					[FRAGMENT_ENTRY_LINK_ID]: fragmentEntryLink,
				},
				permissions: {UPDATE: true},
			})}
		>
			<FragmentAdvancedPanel
				item={{
					children: [],
					config: {
						fragmentEntryLinkId: FRAGMENT_ENTRY_LINK_ID,
					},
					itemId: '0',
					parentId: '',
					type: '',
				}}
			/>
		</StoreMother.Component>
	);

describe('FragmentAdvancedPanel', () => {
	it('renders fields that are included in fieldsets marked as advanced', () => {
		renderComponent({
			fragmentEntryLink: FRAGMENT_ENTRY_LINK_WITH_ADVANCED_CONFIG,
		});

		expect(screen.getByText('Advanced Config Field')).toBeInTheDocument();
	});

	it('does not render fields that are not included in fieldsets marked as advanced', () => {
		renderComponent({
			fragmentEntryLink: FRAGMENT_ENTRY_LINK_WITH_ADVANCED_CONFIG,
		});

		expect(
			screen.queryByText('Standard Config Field')
		).not.toBeInTheDocument();
	});
});
