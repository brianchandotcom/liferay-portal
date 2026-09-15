/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';

// eslint-disable-next-line @liferay/portal/no-cross-module-deep-import, @liferay/no-extraneous-dependencies
import {checkAccessibility} from '@liferay/layout-js-components-web/test/__lib__/index';
import {render, screen} from '@testing-library/react';
import React from 'react';

import {CountryCodePicker} from '../../../components/PhoneNumber/CountryCodePicker';

const COUNTRIES = [
	{a2: 'BR', idd: '55', name: 'Brazil'},
	{a2: 'US', idd: '1', name: 'United States'},
];

describe('CountryCodePicker', () => {
	beforeEach(() => {
		jest.spyOn(Liferay.Language, 'get').mockImplementation((key: string) =>
			key === 'x-is-not-available' ? '{0} is not available.' : key
		);
	});

	afterEach(() => {
		jest.restoreAllMocks();
	});

	it('has no accessibility violations', async () => {
		const {container} = render(
			<CountryCodePicker
				countries={COUNTRIES}
				onSelectionChange={() => {}}
				selectedKey="US"
			/>
		);

		await checkAccessibility({bestPractices: true, context: container});
	});

	it('marks the configured country as unavailable when it is not offered', () => {
		render(
			<CountryCodePicker
				countries={COUNTRIES}
				onSelectionChange={() => {}}
				selectedKey="AQ"
			/>
		);

		expect(screen.getByText('AQ')).toHaveClass('text-danger');
		expect(screen.getByRole('combobox')).toHaveAttribute(
			'title',
			'AQ is not available.'
		);
	});

	it('renders no dial code when the field offers no countries', () => {
		render(
			<CountryCodePicker countries={[]} onSelectionChange={() => {}} />
		);

		const trigger = screen.getByRole('combobox');

		expect(trigger).toBeInTheDocument();
		expect(trigger).not.toHaveTextContent('+');
	});

	it('resolves a configured country written in lower case', () => {
		render(
			<CountryCodePicker
				countries={COUNTRIES}
				onSelectionChange={() => {}}
				selectedKey="br"
			/>
		);

		expect(screen.getByRole('combobox')).toHaveTextContent('+55');
	});

	it('selects the country of the default language when none is configured', () => {
		render(
			<CountryCodePicker
				countries={COUNTRIES}
				onSelectionChange={() => {}}
			/>
		);

		expect(screen.getByRole('combobox')).toHaveTextContent('+1');
	});

	it('shows the configured country as is when the field does not offer it', () => {
		render(
			<CountryCodePicker
				countries={COUNTRIES}
				onSelectionChange={() => {}}
				selectedKey="AQ"
			/>
		);

		const trigger = screen.getByRole('combobox');

		expect(trigger).toHaveTextContent('AQ');
		expect(trigger).not.toHaveTextContent('+1');
	});
});
