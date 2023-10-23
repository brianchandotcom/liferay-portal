/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {render, screen} from '@testing-library/react';
import React from 'react';

import {ObjectDataContainer} from '../../components/ObjectDetails/ObjectDataContainer';

const baseValuesMock = {
	active: false,
	defaultLanguageId: 'en_US',
	externalReferenceCode: '',
	id: 0,
	label: {en_US: 'Label Test'},
	modifiable: false,
	name: 'Object Name',
	pluralLabel: {en_US: 'Plural Label Test'},
	system: false,
	titleObjectFieldName: '',
} as Partial<ObjectDefinition>;

describe('ObjectDataContainer component', () => {
	describe('if the object definition is published', () => {
		it('shows object name input as disabled', () => {
			render(
				<ObjectDataContainer
					dbTableName=""
					errors={{}}
					handleChange={() => {}}
					hasUpdateObjectDefinitionPermission={true}
					isApproved={true}
					isLinkedObjectDefinition={false}
					onSubmit={() => {}}
					setValues={() => {}}
					values={baseValuesMock}
				/>
			);

			const objectNameInput = document.getElementById(
				'lfr-objects__object-data-container-name'
			);

			expect(objectNameInput).toBeDisabled();
		});

		it('shows the active object toggle as checked and not disabled', () => {
			render(
				<ObjectDataContainer
					dbTableName=""
					errors={{}}
					handleChange={() => {}}
					hasUpdateObjectDefinitionPermission={true}
					isApproved={true}
					isLinkedObjectDefinition={false}
					onSubmit={() => {}}
					setValues={() => {}}
					values={{
						...baseValuesMock,
						active: true,
					}}
				/>
			);

			const toggleElement = screen.getByLabelText('activate-x');

			expect(toggleElement).toBeChecked();
			expect(toggleElement).not.toBeDisabled();
		});
	});

	it('shows the object definition name input value and makes it required', () => {
		render(
			<ObjectDataContainer
				dbTableName=""
				errors={{}}
				handleChange={() => {}}
				hasUpdateObjectDefinitionPermission={true}
				isApproved={false}
				isLinkedObjectDefinition={false}
				onSubmit={() => {}}
				setValues={() => {}}
				values={baseValuesMock}
			/>
		);

		const objectNameInput = document.getElementById(
			'lfr-objects__object-data-container-name'
		);

		const objectNameInputLabel = screen.getByText('name');

		expect(objectNameInputLabel).toHaveTextContent('mandatory');
		expect(objectNameInput).toHaveValue('Object Name');
	});

	it('shows the object definition name input value disable when there is no editing permission ', () => {
		render(
			<ObjectDataContainer
				dbTableName=""
				errors={{}}
				handleChange={() => {}}
				hasUpdateObjectDefinitionPermission={false}
				isApproved={false}
				isLinkedObjectDefinition={false}
				onSubmit={() => {}}
				setValues={() => {}}
				values={baseValuesMock}
			/>
		);

		const objectNameInput = document.getElementById(
			'lfr-objects__object-data-container-name'
		);

		expect(objectNameInput).toBeDisabled();
	});

	it('shows the object label input value', () => {
		render(
			<ObjectDataContainer
				dbTableName=""
				errors={{}}
				handleChange={() => {}}
				hasUpdateObjectDefinitionPermission={false}
				isApproved={false}
				isLinkedObjectDefinition={false}
				onSubmit={() => {}}
				setValues={() => {}}
				values={baseValuesMock}
			/>
		);

		expect(screen.getByDisplayValue('Label Test')).toBeInTheDocument();
	});

	it('shows the object definition plural label', () => {
		render(
			<ObjectDataContainer
				dbTableName=""
				errors={{}}
				handleChange={() => {}}
				hasUpdateObjectDefinitionPermission={false}
				isApproved={false}
				isLinkedObjectDefinition={false}
				onSubmit={() => {}}
				setValues={() => {}}
				values={baseValuesMock}
			/>
		);

		expect(
			screen.getByDisplayValue('Plural Label Test')
		).toBeInTheDocument();
	});

	it('shows object definition table name and makes it disabled', () => {
		render(
			<ObjectDataContainer
				dbTableName="DBTableName"
				errors={{}}
				handleChange={() => {}}
				hasUpdateObjectDefinitionPermission={false}
				isApproved={false}
				isLinkedObjectDefinition={false}
				onSubmit={() => {}}
				setValues={() => {}}
				values={baseValuesMock}
			/>
		);

		const tableNameInput = screen.getByDisplayValue('DBTableName');

		expect(tableNameInput).toBeInTheDocument();
		expect(tableNameInput).toBeDisabled();
	});

	it('shows the toggle not checked and disabled by default', () => {
		render(
			<ObjectDataContainer
				dbTableName=""
				errors={{}}
				handleChange={() => {}}
				hasUpdateObjectDefinitionPermission={false}
				isApproved={false}
				isLinkedObjectDefinition={false}
				onSubmit={() => {}}
				setValues={() => {}}
				values={baseValuesMock}
			/>
		);

		const toggleElement = screen.getByLabelText('activate-x');

		expect(toggleElement).not.toBeChecked();
		expect(toggleElement).toBeDisabled();
	});
});
