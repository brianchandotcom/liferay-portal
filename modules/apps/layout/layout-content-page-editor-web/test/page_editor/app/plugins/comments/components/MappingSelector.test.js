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

import {useCollectionConfig} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/components/CollectionItemContext';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/editableFragmentEntryProcessor';
import {LAYOUT_TYPES} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/layoutTypes';
import {config} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/index';
import CollectionService from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/services/CollectionService';
import InfoItemService from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/services/InfoItemService';
import {StoreAPIContextProvider} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/store/index';
import MappingSelector from '../../../../../../src/main/resources/META-INF/resources/page_editor/common/components/MappingSelector';

const infoItem = {
	className: 'infoItemClassName',
	classNameId: 'InfoItemClassNameId',
	classPK: 'infoItemClassPK',
	title: 'Info Item',
};

const emptyCollectionConfig = {
	collection: {
		itemSubtype: '',
		itemType: '',
	},
};

jest.mock(
	'../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/index',
	() => ({
		config: {
			layoutType: '0',
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
	'../../../../../../src/main/resources/META-INF/resources/page_editor/app/services/serviceFetch',
	() => jest.fn(() => Promise.resolve())
);

jest.mock(
	'../../../../../../src/main/resources/META-INF/resources/page_editor/app/services/CollectionService',
	() => ({
		getCollectionMappingFields: jest.fn(),
	})
);

jest.mock(
	'../../../../../../src/main/resources/META-INF/resources/page_editor/app/services/InfoItemService',
	() => ({
		getAvailableInfoItemMappingFields: jest.fn(() =>
			Promise.resolve([
				{
					fields: [
						{key: 'unmapped', label: 'unmapped'},
						{
							key: 'text-field-1',
							label: 'Text Field 1',
							type: 'text',
						},
					],
				},
			])
		),
		getAvailableStructureMappingFields: jest.fn(() =>
			Promise.resolve([
				{
					fields: [
						{
							key: 'structure-field-1',
							label: 'Structure Field 1',
							type: 'text',
						},
					],
				},
			])
		),
	})
);

jest.mock(
	'../../../../../../src/main/resources/META-INF/resources/page_editor/app/components/CollectionItemContext',
	() => ({
		useCollectionConfig: jest.fn(),
	})
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
	Liferay.Util.sub.mockImplementation((langKey, args) =>
		[langKey, args].join('-')
	);

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
		config.layoutType = LAYOUT_TYPES.display;

		await act(async () => {
			renderMappingSelector({});
		});

		expect(getByText(document.body, 'field')).toBeInTheDocument();
		expect(getByText(document.body, 'source')).toBeInTheDocument();
	});

	it('does not render content select when selecting structure as source', async () => {
		config.layoutType = LAYOUT_TYPES.display;

		const {getByLabelText, getByText, queryByText} = renderMappingSelector(
			{}
		);

		const sourceTypeSelect = getByLabelText('source');

		await act(async () => {
			fireEvent.change(sourceTypeSelect, {
				target: {value: 'structure'},
			});
		});

		expect(getByText('field')).toBeInTheDocument();
		expect(getByText('source')).toBeInTheDocument();

		expect(queryByText('content')).not.toBeInTheDocument();
	});

	it('calls onMappingSelect with correct params when mapping to content', async () => {
		config.layoutType = LAYOUT_TYPES.content;

		const onMappingSelect = jest.fn();

		await act(async () => {
			renderMappingSelector({
				mappedItem: infoItem,
				onMappingSelect,
			});
		});

		const fieldSelect = getByLabelText(document.body, 'field');

		await act(async () => {
			fireEvent.change(fieldSelect, {
				target: {value: 'text-field-1'},
			});
		});

		expect(onMappingSelect).toBeCalledWith({
			classNameId: 'InfoItemClassNameId',
			classPK: 'infoItemClassPK',
			fieldId: 'text-field-1',
		});
	});

	it('calls onMappingSelect with correct params when mapping to structure', async () => {
		config.layoutType = LAYOUT_TYPES.display;

		const onMappingSelect = jest.fn();

		await act(async () => {
			renderMappingSelector({
				onMappingSelect,
			});
		});

		const sourceTypeSelect = getByLabelText(document.body, 'source');

		await act(async () => {
			fireEvent.change(sourceTypeSelect, {
				target: {value: 'structure'},
			});
		});

		const fieldSelect = getByLabelText(document.body, 'field');

		await act(async () => {
			fireEvent.change(fieldSelect, {
				target: {value: 'structure-field-1'},
			});
		});

		expect(onMappingSelect).toBeCalledWith({
			mappedField: 'structure-field-1',
		});
	});

	it('calls onMappingSelect with correct params when unmapping', async () => {
		const onMappingSelect = jest.fn();

		await act(async () => {
			renderMappingSelector({
				mappedItem: infoItem,
				onMappingSelect,
			});
		});

		const fieldSelect = getByLabelText(document.body, 'field');

		await act(async () => {
			fireEvent.change(fieldSelect, {
				target: {value: 'unmapped'},
			});
		});

		expect(onMappingSelect).toBeCalledWith({
			classNameId: '',
			classPK: '',
			fieldId: '',
			mappedField: '',
		});
	});

	it('renders correct selects when using Collection context', async () => {
		const collectionFields = [
			{key: 'field-1', label: 'Field 1', type: 'text'},
			{key: 'field-2', label: 'Field 2', type: 'text'},
		];

		useCollectionConfig.mockImplementation(() => emptyCollectionConfig);

		CollectionService.getCollectionMappingFields.mockImplementation(() =>
			Promise.resolve([
				{
					fields: collectionFields,
				},
			])
		);

		await act(async () => {
			renderMappingSelector({});
		});

		expect(queryByText(document.body, 'source')).not.toBeInTheDocument();
		expect(queryByText(document.body, 'content')).not.toBeInTheDocument();

		expect(getByText(document.body, 'field')).toBeInTheDocument();

		collectionFields.forEach((field) =>
			expect(getByText(document.body, field.label)).toBeInTheDocument()
		);

		useCollectionConfig.mockReset();

		CollectionService.getCollectionMappingFields.mockReset();
	});

	it('shows a warning and disables the selector if the fields array is empty', async () => {
		config.layoutType = LAYOUT_TYPES.content;

		InfoItemService.getAvailableInfoItemMappingFields.mockImplementation(
			() => Promise.resolve([])
		);

		await act(async () => {
			renderMappingSelector({
				mappedItem: infoItem,
			});
		});

		const fieldSelect = getByLabelText(document.body, 'field');

		expect(fieldSelect).toBeInTheDocument();
		expect(
			getByText(
				document.body,
				'no-fields-are-available-for-x-editable-text'
			)
		).toBeInTheDocument();
	});
});
