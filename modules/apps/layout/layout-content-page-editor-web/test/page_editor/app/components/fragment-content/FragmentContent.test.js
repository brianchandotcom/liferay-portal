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

import React from 'react';

import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/editableFragmentEntryProcessor';

import '@testing-library/jest-dom/extend-expect';
import {act, cleanup, render} from '@testing-library/react';

import FragmentContent from '../../../../../src/main/resources/META-INF/resources/page_editor/app/components/fragment-content/FragmentContent';
import {BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/backgroundImageFragmentEntryProcessor';
import {VIEWPORT_SIZES} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/viewportSizes';
import {
	ControlsProvider,
	useSelectItem,
} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/ControlsContext';
import {EditableProcessorContextProvider} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/EditableProcessorContext';
import {StoreAPIContextProvider} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';
import resolveEditableValue from '../../../../../src/main/resources/META-INF/resources/page_editor/app/utils/editable-value/resolveEditableValue';

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/page_editor/app/services/serviceFetch',
	() => jest.fn(() => Promise.resolve({}))
);

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/page_editor/app/utils/editable-value/resolveEditableValue',
	() => jest.fn(() => Promise.resolve(['Default content']))
);

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/page_editor/app/config',
	() => ({
		config: {
			frontendTokens: {},
		},
	})
);

const FRAGMENT_ENTRY_LINK_ID = '1';

const getFragmentEntryLink = ({
	content = '<lfr-editable id="editable-id" class="page-editor__editable" type="text">Default content</lfr-editable>',
	editableValues = {
		[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {
			'editable-id': {},
		},
	},
} = {}) => ({
	comments: [],
	configuration: {
		fieldSets: [
			{
				fields: [
					{
						dataType: 'string',
						defaultValue: 'h1',
						description: '',
						label: 'Heading Level',
						name: 'headingLevel',
						type: 'select',
						typeOptions: {
							validValues: [
								{label: 'H1', value: 'h1'},
								{label: 'H2', value: 'h2'},
								{label: 'H3', value: 'h3'},
								{label: 'H4', value: 'h4'},
							],
						},
					},
				],
				label: '',
			},
		],
	},
	content,
	defaultConfigurationValues: {
		headingLevel: 'h1',
	},
	editableValues,
	fragmentEntryLinkId: FRAGMENT_ENTRY_LINK_ID,
	name: 'Heading',
});

const item = {
	children: [],
	config: {
		fragmentEntryLinkId: FRAGMENT_ENTRY_LINK_ID,
	},
	itemId: '1',
	parentId: '',
	type: '',
};

const renderFragmentContent = ({
	activeItemId,
	fragmentEntryLink,
	hasUpdatePermissions = true,
	lockedExperience = false,
	viewportSize = VIEWPORT_SIZES.desktop,
}) => {
	const state = {
		fragmentEntryLinks: {
			[FRAGMENT_ENTRY_LINK_ID]: fragmentEntryLink,
		},
		languageId: 'en_US',
		permissions: {
			LOCKED_SEGMENTS_EXPERIMENT: lockedExperience,
			UPDATE: hasUpdatePermissions,
		},
		segmentsExperienceId: '0',
		selectedViewportSize: viewportSize,
	};

	const ref = React.createRef();

	const AutoSelect = () => {
		useSelectItem()(activeItemId);

		return null;
	};

	return render(
		<StoreAPIContextProvider dispatch={() => {}} getState={() => state}>
			<EditableProcessorContextProvider>
				<ControlsProvider>
					<AutoSelect />
					<FragmentContent
						elementRef={ref}
						fragmentEntryLinkId={FRAGMENT_ENTRY_LINK_ID}
						getPortals={() => []}
						item={item}
					/>
				</ControlsProvider>
			</EditableProcessorContextProvider>
		</StoreAPIContextProvider>
	);
};

describe('FragmentContent', () => {
	beforeEach(() => {
		cleanup();

		resolveEditableValue.mockClear();
	});

	it('renders the fragment content', async () => {
		const fragmentEntryLink = getFragmentEntryLink();

		await act(async () => {
			renderFragmentContent({fragmentEntryLink});
		});

		const editableContent = document.body.querySelector('#editable-id');

		expect(editableContent.outerHTML).toBe(fragmentEntryLink.content);
	});

	it('calls resolve editable values with the correct parameters when content has a editable', async () => {
		const fragmentEntryLink = getFragmentEntryLink();

		await act(async () => {
			renderFragmentContent({fragmentEntryLink});
		});

		expect(resolveEditableValue).toBeCalledWith(
			{},
			'en_US',
			expect.any(Function)
		);
	});

	it('calls resolve editable values with the correct parameters when content has a data-lfr-editable-id', async () => {
		const fragmentEntryLink = getFragmentEntryLink({
			content:
				'<p data-lfr-editable-id="editable-id" data-lfr-editable-type="text">Default content</p>',
		});

		await act(async () => {
			renderFragmentContent({fragmentEntryLink});
		});

		expect(resolveEditableValue).toBeCalledWith(
			{},
			'en_US',
			expect.any(Function)
		);
	});

	it('calls resolve editable values with the correct parameters when content has a background image editable', async () => {
		const fragmentEntryLink = getFragmentEntryLink({
			content: '<div data-lfr-background-image-id="background-id"></div>',

			editableValues: {
				[BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR]: {
					'background-id': {
						defaultValue: 'image.jpg',
					},
				},
			},
		});

		await act(async () => {
			renderFragmentContent({fragmentEntryLink});
		});

		expect(resolveEditableValue).toBeCalledWith(
			{defaultValue: 'image.jpg'},
			'en_US',
			expect.any(Function)
		);
	});

	it('shows widgets topper even without update permissions', async () => {
		const fragmentEntryLink = getFragmentEntryLink();

		await act(async () => {
			renderFragmentContent({
				fragmentEntryLink,
				hasUpdatePermissions: false,
			});
		});

		expect(
			document.body.querySelector(
				'.page-editor__fragment-content--portlet-topper-hidden'
			)
		).toBeInTheDocument();
	});
});
