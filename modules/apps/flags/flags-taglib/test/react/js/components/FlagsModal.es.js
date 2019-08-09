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

import {cleanup, render} from '@testing-library/react';
import FlagsModal from '../../../../src/main/resources/META-INF/resources/flags/react/js/components/FlagsModal.es';
import React from 'react';
import ReactDOM from 'react-dom';
import {
	STATUS_ERROR,
	STATUS_LOGIN,
	STATUS_REPORT,
	STATUS_SUCCESS
} from '../../../../src/main/resources/META-INF/resources/flags/react/js/constants.es';

beforeAll(() => {
	ReactDOM.createPortal = jest.fn(element => {
		return element;
	});
});

afterEach(() => {
	jest.restoreAllMocks();
});

function _renderFlagsModalComponent({
	companyName = 'Liferay',
	handleClose = () => {},
	handleInputChange = () => {},
	handleSubmit = () => {},
	isSending = false,
	pathTermsOfUse = '/',
	selectedReason = 'value',
	reasons = {value: 'text', value2: 'text2'},
	signedIn = true,
	status = STATUS_REPORT
} = {}) {
	return render(
		<FlagsModal
			companyName={companyName}
			handleClose={handleClose}
			handleInputChange={handleInputChange}
			handleSubmit={handleSubmit}
			isSending={isSending}
			pathTermsOfUse={pathTermsOfUse}
			reasons={reasons}
			selectedReason={selectedReason}
			signedIn={signedIn}
			status={status}
		/>
	);
}

describe('FlagsModal', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {asFragment} = _renderFlagsModalComponent();

		expect(asFragment()).toMatchSnapshot();
	});

	it('renders as guess and render email field', () => {
		const {getByLabelText} = _renderFlagsModalComponent({
			signedIn: false
		});

		expect(getByLabelText('email', {exact: false})).not.toBe(null);
	});

	it('renders error', () => {
		const {asFragment} = _renderFlagsModalComponent({status: STATUS_ERROR});

		expect(asFragment()).toMatchSnapshot();
	});

	it('renders login', () => {
		const {asFragment} = _renderFlagsModalComponent({status: STATUS_LOGIN});

		expect(asFragment()).toMatchSnapshot();
	});

	it('renders success', () => {
		const {asFragment} = _renderFlagsModalComponent({
			status: STATUS_SUCCESS
		});

		expect(asFragment()).toMatchSnapshot();
	});
});
