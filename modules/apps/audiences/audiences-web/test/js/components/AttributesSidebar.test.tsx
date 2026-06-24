/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen, waitFor} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import AttributesSidebar from '../../../src/main/resources/META-INF/resources/js/components/AttributesSidebar';

const AUDIENCES_CRITERIA_TYPES = [
	{
		audiencesCriterias: [
			{
				icon: 'user',
				key: 'age',
				label: 'Age',
				operators: [],
				options: [],
				type: 'number',
			},
			{
				icon: 'user',
				key: 'city',
				label: 'City',
				operators: [],
				options: [],
				type: 'string',
			},
		],
		label: 'User',
	},
	{
		audiencesCriterias: [
			{
				icon: 'globe',
				key: 'browser',
				label: 'Browser',
				operators: [],
				options: [],
				type: 'string',
			},
		],
		label: 'Session',
	},
];

describe('AttributesSidebar', () => {
	it('lists and filters the attributes', async () => {
		render(
			<AttributesSidebar
				audiencesCriteriaTypes={AUDIENCES_CRITERIA_TYPES}
			/>
		);

		expect(screen.getByText('Age')).toBeTruthy();
		expect(screen.getByText('City')).toBeTruthy();
		expect(screen.queryByText('Browser')).toBeNull();

		await userEvent.selectOptions(
			screen.getByLabelText('attributes-types'),
			screen.getByRole('option', {name: 'Session'})
		);

		expect(screen.getByText('Browser')).toBeTruthy();
		expect(screen.queryByText('Age')).toBeNull();

		await userEvent.selectOptions(
			screen.getByLabelText('attributes-types'),
			screen.getByRole('option', {name: 'User'})
		);

		await userEvent.type(screen.getByLabelText('search-attributes'), 'cit');

		await waitFor(() => expect(screen.queryByText('Age')).toBeNull());

		expect(screen.getByText('City')).toBeTruthy();

		await userEvent.clear(screen.getByLabelText('search-attributes'));
		await userEvent.type(screen.getByLabelText('search-attributes'), 'zzz');

		await waitFor(() =>
			expect(screen.getByText('no-attributes-were-found')).toBeTruthy()
		);
	});
});
