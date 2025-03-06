/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';

import '@testing-library/jest-dom/extend-expect';
import {fireEvent, render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import StructureFieldSettings from '../../../../src/main/resources/META-INF/resources/js/structure_builder/components/StructureFieldSettings';
import {
	Action,
	State,
} from '../../../../src/main/resources/META-INF/resources/js/structure_builder/contexts/StateContext';
import {Field} from '../../../../src/main/resources/META-INF/resources/js/structure_builder/utils/field';
import {MockStateProvider} from '../mocks/MockStateProvider';

const TEXT_FIELD_NAME = 'testField';

const FIELD: Field = {
	erc: 'test-erc',
	indexableConfig: {
		indexed: false,
	},
	label: {
		en_US: 'Test Field',
		es_ES: 'Campo de Prueba',
	},
	localized: false,
	name: TEXT_FIELD_NAME,
	required: false,
	settings: {},
	type: 'text',
};

const DEFAULT_STATE: State = {
	erc: 'structure-erc',
	error: null,
	fields: new Map([[TEXT_FIELD_NAME, FIELD]]),
	id: null,
	label: 'untitled-structure',
	name: 'UntitledStructure',
	publishedFields: new Set(),
	selection: [],
	status: 'new',
};

const renderComponent = ({
	state = DEFAULT_STATE,
	dispatch = jest.fn(),
}: {
	dispatch?: React.Dispatch<Action>;
	state?: State;
} = {}) => {
	return render(
		<MockStateProvider dispatch={dispatch} state={state}>
			<StructureFieldSettings fieldName={TEXT_FIELD_NAME} />
		</MockStateProvider>
	);
};

describe('StructureFieldSettings', () => {
	beforeEach(() => {
		jest.clearAllMocks();

		(global as any).Liferay.Language.direction = {
			en_US: 'rtl',
		};
	});

	it('renders', () => {
		renderComponent();

		expect(screen.getByText('Test Field')).toBeInTheDocument();
		expect(screen.getByText('field-type')).toBeInTheDocument();
		expect(screen.getByText('text')).toBeInTheDocument();
	});

	it('updates field name when input changes', async () => {
		const mockDispatch = jest.fn();

		renderComponent({dispatch: mockDispatch});

		const nameInput = screen.getByLabelText('field-name');

		await userEvent.clear(nameInput);
		await userEvent.type(nameInput, 'newFieldName');

		fireEvent.blur(nameInput);

		expect(mockDispatch).toHaveBeenCalledWith({
			name: TEXT_FIELD_NAME,
			newName: 'newFieldName',
			type: 'update-field',
		});
	});

	it('toggles required and localizable fields', async () => {
		const mockDispatch = jest.fn();

		renderComponent({dispatch: mockDispatch});

		await userEvent.click(screen.getByLabelText('mandatory'));

		expect(mockDispatch).toHaveBeenCalledWith({
			name: TEXT_FIELD_NAME,
			required: true,
			type: 'update-field',
		});

		await userEvent.click(screen.getByLabelText('localizable'));

		expect(mockDispatch).toHaveBeenCalledWith({
			localized: true,
			name: TEXT_FIELD_NAME,
			type: 'update-field',
		});
	});

	it('updates searchable configuration', async () => {
		const mockDispatch = jest.fn();

		renderComponent({dispatch: mockDispatch});

		await userEvent.click(screen.getByText('search'));

		await userEvent.click(screen.getByLabelText('searchable'));

		expect(mockDispatch).toHaveBeenCalledWith({
			indexableConfig: {
				indexed: true,
				indexedAsKeyword: false,
				indexedLanguageId: 'en_US',
			},
			name: TEXT_FIELD_NAME,
			type: 'update-field',
		});
	});

	it('updates keyword configuration', async () => {
		const mockDispatch = jest.fn();

		renderComponent({
			dispatch: mockDispatch,
			state: {
				...DEFAULT_STATE,
				fields: new Map([
					[
						TEXT_FIELD_NAME,
						{
							...FIELD,
							indexableConfig: {
								indexed: true,
								indexedAsKeyword: false,
								indexedLanguageId: 'en_US',
							},
						},
					],
				]),
			},
		});

		await userEvent.click(screen.getByLabelText('keyword'));

		expect(mockDispatch).toHaveBeenCalledWith({
			indexableConfig: {
				indexed: true,
				indexedAsKeyword: true,
				indexedLanguageId: undefined,
			},
			name: TEXT_FIELD_NAME,
			type: 'update-field',
		});
	});

	it('updates specific text configuration', async () => {
		const mockDispatch = jest.fn();

		renderComponent({dispatch: mockDispatch});

		await userEvent.click(
			screen.getByLabelText('accept-unique-values-only')
		);

		expect(mockDispatch).toHaveBeenCalledWith({
			name: TEXT_FIELD_NAME,
			settings: {
				uniqueValues: true,
			},
			type: 'update-field',
		});

		expect(
			screen.queryByLabelText('maximun-number-of-characters')
		).not.toBeInTheDocument();

		await userEvent.click(screen.getByLabelText('limit-characters'));

		const numberOfCharactersInput = screen.getByLabelText(
			'maximum-number-of-characters'
		);

		await userEvent.type(numberOfCharactersInput, '10');
		fireEvent.blur(numberOfCharactersInput);

		expect(mockDispatch).toHaveBeenCalledWith({
			name: TEXT_FIELD_NAME,
			settings: {
				maxLength: 10,
				showCounter: true,
			},
			type: 'update-field',
		});
	});

	it('disables fields when structure is published', () => {
		const mockDispatch = jest.fn();

		renderComponent({
			dispatch: mockDispatch,
			state: {
				...DEFAULT_STATE,
				publishedFields: new Set([TEXT_FIELD_NAME]),
				status: 'published',
			},
		});

		expect(
			screen.getByLabelText('accept-unique-values-only')
		).toBeDisabled();
		expect(screen.getByLabelText('field-name')).toBeDisabled();
		expect(screen.getByLabelText('localizable')).toBeDisabled();
		expect(screen.getByLabelText('mandatory')).toBeDisabled();

		expect(screen.getByLabelText('erc')).not.toBeDisabled();
		expect(screen.getByLabelText('limit-characters')).not.toBeDisabled();
	});
});
