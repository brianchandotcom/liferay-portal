/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {act, renderHook} from '@testing-library/react';
import React, {ReactNode} from 'react';

import StateContextProvider, {
	State,
	useSelector,
	useStateDispatch,
} from '../../../../src/main/resources/META-INF/resources/js/structure_builder/contexts/StateContext';
import {Field} from '../../../../src/main/resources/META-INF/resources/js/structure_builder/utils/field';
import getUuid from '../../../../src/main/resources/META-INF/resources/js/structure_builder/utils/getUuid';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/structure_builder/config',
	() => ({
		config: {
			objectFolderExternalReferenceCode: 'L_CMS_CONTENT_STRUCTURES',
		},
	})
);

const STRUCTURE_UUID = getUuid();

function buildField(overrides: Partial<Field> = {}): Field {
	return {
		erc: 'field-erc',
		indexableConfig: {indexed: false},
		label: {en_US: 'Original Label'},
		localized: false,
		locked: false,
		name: 'originalName',
		parent: STRUCTURE_UUID,
		required: false,
		settings: {},
		type: 'text',
		uuid: getUuid(),
		...overrides,
	};
}

function buildInitialState(field: Field): State {
	const children = new Map();

	children.set(field.uuid, field);

	return {
		history: {
			deletedChildren: [],
			deletedGroupERCs: [],
			deletedRelationships: [],
			modifiedNames: new Set(),
		},
		invalids: new Map(),
		publishedChildren: new Set(),
		renamingItemUuid: null,
		selection: [],
		structure: {
			children,
			erc: 'structure-erc',
			label: {en_US: 'Structure'},
			name: 'myStructure',
			path: '',
			spaces: [],
			status: 'draft',
			system: false,
			type: 'L_CMS_CONTENT_STRUCTURES',
			uuid: STRUCTURE_UUID,
			workflows: {},
		},
		unsavedChanges: false,
	};
}

function renderReducerHook(field: Field) {
	const wrapper = ({children}: {children: ReactNode}) => (
		<StateContextProvider initialState={buildInitialState(field)}>
			{children}
		</StateContextProvider>
	);

	return renderHook(
		() => ({
			dispatch: useStateDispatch(),
			field: useSelector(
				(state) => state.structure.children.get(field.uuid) as Field
			),
		}),
		{wrapper}
	);
}

describe('StateContext reducer', () => {
	describe('update-field', () => {
		it('keeps the name of a locked field when its label changes', () => {
			const lockedField = buildField({locked: true, name: 'title'});

			const {result} = renderReducerHook(lockedField);

			act(() => {
				result.current.dispatch({
					label: {en_US: 'Headline'},
					type: 'update-field',
					uuid: lockedField.uuid,
				});
			});

			expect(result.current.field.label).toEqual({en_US: 'Headline'});
			expect(result.current.field.name).toBe('title');
		});

		it('auto-renames an unlocked field when its label changes', () => {
			const unlockedField = buildField({
				locked: false,
				name: 'originalName',
			});

			const {result} = renderReducerHook(unlockedField);

			act(() => {
				result.current.dispatch({
					label: {en_US: 'Headline'},
					type: 'update-field',
					uuid: unlockedField.uuid,
				});
			});

			expect(result.current.field.label).toEqual({en_US: 'Headline'});
			expect(result.current.field.name).toBe('headline');
		});
	});
});
