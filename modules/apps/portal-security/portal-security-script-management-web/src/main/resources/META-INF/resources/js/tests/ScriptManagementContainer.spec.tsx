/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {render} from '@testing-library/react';
import React, {useState as useStateMock} from 'react';

import ScriptManagementContainer from '../components/ScriptManagementContainer';

jest.mock('react', () => ({
	...(jest.requireActual('react') as {}),
	useState: jest.fn(),
}));

const setAllowScriptContent = jest.fn();

beforeAll(() => {

	// @ts-ignore

	useStateMock.mockImplementation((allowScriptContent: boolean) => [
		allowScriptContent,
		setAllowScriptContent,
	]);
});

describe('ScriptManagementContainer component', () => {
	it('check if checkbox label renders correctly', () => {
		const {getByLabelText} = render(
			<ScriptManagementContainer
				allowScriptContentBeExecutedOrIncluded={false}
				baseResourceURL=""
			/>
		);

		const input = getByLabelText(
			'allow-administrator-to-create-and-execute-code-in-liferay'
		);

		expect(input).toBeInTheDocument();
	});

	it('check if Script Management title renders correctly', () => {
		const {getByText} = render(
			<ScriptManagementContainer
				allowScriptContentBeExecutedOrIncluded={false}
				baseResourceURL=""
			/>
		);

		const scriptManagementTitle = getByText('script-management');

		expect(scriptManagementTitle).toBeInTheDocument();
	});

	it('check if checkbox description renders correctly', () => {
		const {getByText} = render(
			<ScriptManagementContainer
				allowScriptContentBeExecutedOrIncluded={false}
				baseResourceURL=""
			/>
		);

		const checkboxDescription = getByText(
			'administrators-can-create-and-execute-code-in-their-virtual-instance'
		);

		expect(checkboxDescription).toBeInTheDocument();
	});

	it('check if checkbox will be checked if allowScriptContentBeExecutedOrIncluded is true', () => {
		const {getByRole} = render(
			<ScriptManagementContainer
				allowScriptContentBeExecutedOrIncluded={true}
				baseResourceURL=""
			/>
		);

		const checkboxInput = getByRole('checkbox');

		expect(checkboxInput).toBeChecked();
	});

	it('check if checkbox will be not checked if allowScriptContentBeExecutedOrIncluded is false', () => {
		const {getByRole} = render(
			<ScriptManagementContainer
				allowScriptContentBeExecutedOrIncluded={false}
				baseResourceURL=""
			/>
		);

		const checkboxInput = getByRole('checkbox');

		expect(checkboxInput).not.toBeChecked();
	});
});
