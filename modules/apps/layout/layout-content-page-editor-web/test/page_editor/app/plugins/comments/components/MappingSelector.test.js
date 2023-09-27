/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {
	act,
	fireEvent,
	getByLabelText,
	getByText,
	queryByText,
	render,
} from '@testing-library/react';
import React from 'react';

import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/editableFragmentEntryProcessor';
import {LAYOUT_TYPES} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/layoutTypes';
import {config} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/index';
import {useCollectionConfig} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/CollectionItemContext';
import {StoreAPIContextProvider} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';
import CollectionService from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/services/CollectionService';
import MappingSelector from '../../../../../../src/main/resources/META-INF/resources/page_editor/common/components/MappingSelector';

const defaultMappingFields = {
	'InfoItemClassNameId-infoItemClassTypeId': [
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
	],
	'mappingType-mappingSubtype': [
		{
			fields: [
				{
					key: 'structure-field-1',
					label: 'Structure Field 1',
					type: 'text',
				},
			],
		},
	],
};

const infoItem = {
	className: 'InfoItemClassName',
	classNameId: 'InfoItemClassNameId',
	classPK: 'infoItemClassPK',
	classTypeId: 'infoItemClassTypeId',
	title: 'Info Item',
};

const emptyCollectionConfig = {
	collection: {
		classNameId: 'collectionClassNameId',
		classPK: 'collectionClassPK',
		itemSubtype: 'collectionItemSubtype',
		itemType: 'collectionItemType',
	},
};

jest.mock(
	'../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/index',
	() => ({
		config: {
			layoutType: '0',
			selectedMappingTypes: {
				subtype: {
					id: 'mappingSubtype',
					label: 'mappingSubtype',
				},
				type: {
					id: 'mappingType',
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
	'../../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/CollectionItemContext',
	() => ({
		useCollectionConfig: jest.fn(),
	})
);

jest.mock('frontend-js-web', () => ({
	...jest.requireActual('frontend-js-web'),
	sub: jest.fn((key, arg) => key.replace('x', arg)),
}));

function renderMappingSelector({
	mappedItem = {},
	mappingFields = defaultMappingFields,
	onMappingSelect = () => {},
}) {
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
		mappingFields,
		pageContents: [
			{
				classNameId: 'mappedItemClassNameId',
				classPK: 'mappedItemClassPK',
				classTypeId: 'mappedItemClassTypeId',
				itemSubtype: 'Mapped Item Subtype',
				itemType: 'Mapped Item Type',
				title: 'mappedItemTitle',
			},
		],
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
	it('renders correct selects in content pages', async () => {
		renderMappingSelector({});

		expect(getByText(document.body, 'item')).toBeInTheDocument();
		expect(getByText(document.body, 'field')).toBeInTheDocument();

		expect(queryByText(document.body, 'source')).not.toBeInTheDocument();
	});

	it('renders correct selects in display pages', async () => {
		config.layoutType = LAYOUT_TYPES.display;

		renderMappingSelector({});

		expect(getByText(document.body, 'field')).toBeInTheDocument();
		expect(getByText(document.body, 'source')).toBeInTheDocument();
	});

	it('does not render content select when selecting structure as source', async () => {
		config.layoutType = LAYOUT_TYPES.display;

		const {getByLabelText, getByText, queryByText} = renderMappingSelector(
			{}
		);

		const sourceTypeSelect = getByLabelText('source');

		fireEvent.change(sourceTypeSelect, {
			target: {value: 'structure'},
		});

		expect(getByText('field')).toBeInTheDocument();
		expect(getByText('source')).toBeInTheDocument();

		expect(queryByText('item')).not.toBeInTheDocument();
	});

	it('calls onMappingSelect with correct params when mapping to content', async () => {
		config.layoutType = LAYOUT_TYPES.content;

		const onMappingSelect = jest.fn();

		renderMappingSelector({
			mappedItem: infoItem,
			onMappingSelect,
		});

		const fieldSelect = getByLabelText(document.body, 'field');

		fireEvent.change(fieldSelect, {
			target: {value: 'text-field-1'},
		});

		expect(onMappingSelect).toBeCalledWith({
			className: 'InfoItemClassName',
			classNameId: 'InfoItemClassNameId',
			classPK: 'infoItemClassPK',
			classTypeId: 'infoItemClassTypeId',
			fieldId: 'text-field-1',
			title: 'Info Item',
		});
	});

	it('calls onMappingSelect with correct params when mapping to structure', async () => {
		config.layoutType = LAYOUT_TYPES.display;

		const onMappingSelect = jest.fn();

		renderMappingSelector({
			onMappingSelect,
		});

		const sourceTypeSelect = getByLabelText(document.body, 'source');

		fireEvent.change(sourceTypeSelect, {
			target: {value: 'structure'},
		});

		const fieldSelect = getByLabelText(document.body, 'field');

		fireEvent.change(fieldSelect, {
			target: {value: 'structure-field-1'},
		});

		expect(onMappingSelect).toBeCalledWith({
			mappedField: 'structure-field-1',
		});
	});

	it('calls onMappingSelect with empty object when unmapping', async () => {
		const onMappingSelect = jest.fn();

		renderMappingSelector({
			mappedItem: infoItem,
			onMappingSelect,
		});

		const fieldSelect = getByLabelText(document.body, 'field');

		fireEvent.change(fieldSelect, {
			target: {value: 'unmapped'},
		});

		expect(onMappingSelect).toBeCalledWith({});
	});

	it('renders correct selects when using Collection context', async () => {
		const collectionFields = [
			{key: 'field-1', label: 'Field 1', type: 'text'},
			{key: 'field-2', label: 'Field 2', type: 'text'},
		];

		useCollectionConfig.mockImplementation(() => emptyCollectionConfig);

		CollectionService.getCollectionMappingFields.mockImplementation(() =>
			Promise.resolve({
				mappingFields: [
					{
						fields: collectionFields,
					},
				],
			})
		);

		await act(async () => {
			renderMappingSelector({
				mappingFields: {
					'collectionClassNameId-collectionClassPK': [
						{
							fields: collectionFields,
						},
					],
				},
			});
		});

		useCollectionConfig.mockReset();

		CollectionService.getCollectionMappingFields.mockReset();

		expect(queryByText(document.body, 'source')).not.toBeInTheDocument();
		expect(queryByText(document.body, 'item')).not.toBeInTheDocument();

		expect(getByText(document.body, 'field')).toBeInTheDocument();

		collectionFields.forEach((field) =>
			expect(getByText(document.body, field.label)).toBeInTheDocument()
		);
	});

	it('shows a warning and disables the selector if the fields array is empty', async () => {
		config.layoutType = LAYOUT_TYPES.content;

		renderMappingSelector({
			mappedItem: infoItem,
			mappingFields: {
				'InfoItemClassNameId-infoItemClassTypeId': [],
			},
		});

		const fieldSelect = getByLabelText(document.body, 'field');

		expect(fieldSelect).toBeInTheDocument();
		expect(
			getByText(
				document.body,
				'no-fields-are-available-for-text-editable'
			)
		).toBeInTheDocument();
	});

	it('shows type and subtype label when some item is mapped', async () => {
		config.layoutType = LAYOUT_TYPES.content;

		renderMappingSelector({
			mappedItem: {
				classNameId: 'mappedItemClassNameId',
				classPK: 'mappedItemClassPK',
				fieldId: 'mappedFieldId',
			},
		});

		expect(
			getByText(document.body, 'Mapped Item Type')
		).toBeInTheDocument();

		expect(
			getByText(document.body, 'Mapped Item Subtype')
		).toBeInTheDocument();
	});
});
