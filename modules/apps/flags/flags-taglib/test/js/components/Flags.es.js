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
import {fireEvent, render} from '@testing-library/react';
import React from 'react';
import {act} from 'react-dom/test-utils';

import Flags from '../../../src/main/resources/META-INF/resources/flags/js/components/Flags.es';

const formDataToObject = (formData) =>
	Array.from(formData).reduce(
		(memo, [key, val]) => ({
			...memo,
			[key]: val,
		}),
		{}
	);

function _renderFlagsComponent({
	captchaURI = '',
	companyName = 'Liferay',
	baseData = {},
	onlyIcon = false,
	pathTermsOfUse = '/',
	reasons = {value: 'text', value2: 'text2'},
	signedIn = false,
	uri = '//',
	viewMode = true,
} = {}) {
	return render(
		<Flags
			baseData={baseData}
			captchaURI={captchaURI}
			companyName={companyName}
			onlyIcon={onlyIcon}
			pathTermsOfUse={pathTermsOfUse}
			reasons={reasons}
			signedIn={signedIn}
			uri={uri}
			viewMode={viewMode}
		/>,
		{
			baseElement: document.body,
		}
	);
}

describe('Flags', () => {
	it('renders', () => {
		const {getByRole, getByText} = _renderFlagsComponent();

		expect(getByText('report')).toBeTruthy();
		expect(getByRole('button')).toBeTruthy();
	});

	it('renders with only icon visible (text visually hidden)', () => {
		const {getByText} = _renderFlagsComponent({onlyIcon: true});

		expect(getByText('report')).toHaveClass('sr-only');
	});

	it('disables interaction when the view mode is not active', () => {
		const {getByRole} = _renderFlagsComponent({viewMode: false});

		expect(getByRole('button')).toBeDisabled();
	});

	it('submits a report successfully with baseData', async () => {
		const {findByRole, getByRole} = _renderFlagsComponent({
			baseData: {
				testingField: 'testingValue',
			},
		});
		fetch.mockResponse('');

		await act(async () => {
			fireEvent.click(getByRole('button'));

			const form = await findByRole('form');

			[...form.elements].forEach((element) => {
				element.value = 'someValue';
			});

			fireEvent.submit(form);
		});

		expect(fetch).toHaveBeenCalledTimes(1);

		const formDataObj = formDataToObject(fetch.mock.calls[0][1].body);
		expect(formDataObj.testingField).toBe('testingValue');
	});
});
