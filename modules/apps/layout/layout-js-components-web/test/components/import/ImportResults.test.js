/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {ImportResults} from '@liferay/layout-js-components-web';
import {render} from '@testing-library/react';
import React from 'react';

const SUCCESS_RESULT = {
	imported: [
		{
			message: '',
			name: 'fragment 1',
			type: 'fragment',
		},
	],
};

const SUCCESS_WARNING_AND_INVALID_RESULT = {
	'imported': [
		{
			message: '',
			name: 'fragment 1',
			type: 'fragment',
		},
	],
	'imported-draft': [
		{
			message: 'This is a warning message',
			name: 'fragment 2',
			type: 'fragment',
		},
		{
			message: 'This is another warning message',
			name: 'fragment 3',
			type: 'fragment',
		},
	],
	'invalid': [
		{
			message: 'This is an invalid message',
			name: 'fragment 4',
			type: 'fragment',
		},
	],
};

describe('ImportResults', () => {
	it('renders success imported results expanded when there are not imported draft or invalid', () => {
		const {getByRole, getByText} = render(
			<ImportResults
				fileName="example.zip"
				importResults={SUCCESS_RESULT}
			/>
		);

		expect(getByText('fragment 1')).toBeInTheDocument();

		expect(getByText('x-item-was-imported')).toBeInTheDocument();
		expect(getByRole('tab').classList.contains('collapsed')).toBe(false);
	});

	it('renders success imported results collapsed when there are nt imported draft or invalid', () => {
		const {getByRole, getByText} = render(
			<ImportResults
				fileName="example.zip"
				importResults={SUCCESS_WARNING_AND_INVALID_RESULT}
			/>
		);

		expect(getByText('fragment 1')).toBeInTheDocument();

		expect(getByText('x-item-was-imported')).toBeInTheDocument();
		expect(getByRole('tab').classList.contains('collapsed')).toBe(true);
	});

	it('renders warning and invalid results', () => {
		const {getByText} = render(
			<ImportResults
				fileName="example.zip"
				importResults={SUCCESS_WARNING_AND_INVALID_RESULT}
			/>
		);

		expect(getByText('fragment 2')).toBeInTheDocument();
		expect(getByText('fragment 3')).toBeInTheDocument();
		expect(getByText('fragment 4')).toBeInTheDocument();

		expect(
			getByText('x-items-were-imported-with-warnings')
		).toBeInTheDocument();
		expect(getByText('x-item-could-not-be-imported')).toBeInTheDocument();

		expect(getByText('This is a warning message')).toBeInTheDocument();
		expect(
			getByText('This is another warning message')
		).toBeInTheDocument();
		expect(getByText('This is an invalid message')).toBeInTheDocument();
	});
});
