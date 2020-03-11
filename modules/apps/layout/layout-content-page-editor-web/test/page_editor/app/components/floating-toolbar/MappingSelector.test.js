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
import {
	act,
	cleanup,
	fireEvent,
	getByLabelText,
	getByText,
	queryByText,
	render,
} from '@testing-library/react';
import React from 'react';

import MappingSelector from '../../../../../src/main/resources/META-INF/resources/page_editor/app/components/floating-toolbar/MappingSelector';
import {config} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/config';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/editableFragmentEntryProcessor';
import {PAGE_TYPES} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/pageTypes';
import {StoreAPIContextProvider} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/store/index';

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/page_editor/app/config',
	() => ({
		config: {
			pageType: '0',
			selectedMappingTypes: {
				subtype: {
					id: '0',
					label: 'mappingSubtype',
				},
				type: {
					id: '1',
					label: 'mappingType',
				},
			},
		},
	})
);

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/page_editor/app/services/serviceFetch',
	() => jest.fn(() => Promise.resolve())
);

function renderMappingSelector({mappedItem = {}, onMappingSelect = () => {}}) {
	const state = {
		fragmentEntryLinks: {
			0: {
				editableValues: {
					[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {
						'editable-id-0': {
							config: {},
						},
					},
				},
			},
		},
		mappedInfoItems: [],
		segmentsExperienceId: 0,
	};

	return render(
		<StoreAPIContextProvider dispatch={() => {}} getState={() => state}>
			<MappingSelector
				fieldType="text"
				mappedItem={mappedItem}
				onMappingSelect={onMappingSelect}
			/>
		</StoreAPIContextProvider>,
		{
			baseElement: document.body,
		}
	);
}

describe('MappingSelector', () => {
	afterEach(() => {
		cleanup();
	});

	it('renders correct selects in content pages', async () => {
		await act(async () => {
			renderMappingSelector({});
		});

		expect(getByText(document.body, 'content')).toBeInTheDocument();
		expect(getByText(document.body, 'field')).toBeInTheDocument();

		expect(queryByText(document.body, 'source')).not.toBeInTheDocument();
	});

	it('renders correct selects in display pages', async () => {
		config.pageType = PAGE_TYPES.display;

		await act(async () => {
			renderMappingSelector({});
		});

		expect(getByText(document.body, 'content')).toBeInTheDocument();
		expect(getByText(document.body, 'field')).toBeInTheDocument();
		expect(getByText(document.body, 'source')).toBeInTheDocument();
	});

	it('does not render content select when selecting structure as source', async () => {
		config.pageType = PAGE_TYPES.display;

		const {getByLabelText, getByText, queryByText} = renderMappingSelector(
			{}
		);

		const sourceTypeInput = getByLabelText('source');

		await act(async () => {
			fireEvent.change(sourceTypeInput, {
				target: {value: 'structure'},
			});
		});

		expect(getByText('field')).toBeInTheDocument();
		expect(getByText('source')).toBeInTheDocument();

		expect(queryByText('content')).not.toBeInTheDocument();
	});
});
