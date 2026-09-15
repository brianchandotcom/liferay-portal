/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {COUNTRY_SOURCE} from '@liferay/object-js-components-web';
import {render} from '@testing-library/react';
import React from 'react';

import PhoneNumber from '../../js/PhoneNumber/PhoneNumber';

const COUNTRIES = [
	{a2: 'BR', idd: '55', name: 'Brazil'},
	{a2: 'US', idd: '1', name: 'United States'},
];

const DEFAULT_PROPS = {
	countries: COUNTRIES,
	countrySource: COUNTRY_SOURCE.FIXED,
	fieldName: 'phoneNumber',
	label: 'Phone Number',
	name: 'phoneNumber',
};

describe('PhoneNumber', () => {
	beforeEach(() => {
		jest.spyOn(Liferay.Language, 'get').mockImplementation((key: string) =>
			key === 'x-is-not-available' ? '{0} is not available.' : key
		);
	});

	afterEach(() => {
		jest.restoreAllMocks();
	});

	it('names the configured country when the field no longer offers it', () => {
		const {container} = render(
			<PhoneNumber {...DEFAULT_PROPS} country="KH" value="+8551234567" />
		);

		expect(
			container.querySelector('.form-feedback-item')
		).toHaveTextContent('KH is not available.');
	});

	it('shows no feedback when the field offers the configured country', () => {
		const {container} = render(
			<PhoneNumber {...DEFAULT_PROPS} country="BR" value="+5551234567" />
		);

		expect(
			container.querySelector('.form-feedback-item')
		).toBeEmptyDOMElement();
	});
});
