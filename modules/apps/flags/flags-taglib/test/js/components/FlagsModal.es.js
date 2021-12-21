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

import {render} from '@testing-library/react';
import React from 'react';

import FlagsModal from '../../../src/main/resources/META-INF/resources/flags/js/components/FlagsModal.es';
import {
	STATUS_ERROR,
	STATUS_LOGIN,
	STATUS_REPORT,
	STATUS_SUCCESS,
} from '../../../src/main/resources/META-INF/resources/flags/js/constants.es';

function _renderFlagsModalComponent({
	captchaURI = '',
	companyName = 'Liferay',
	handleClose = () => {},
	handleInputChange = () => {},
	handleSubmit = () => {},
	isSending = false,
	pathTermsOfUse = '/',
	selectedReason = 'value',
	reasons = {value: 'text', value2: 'text2'},
	signedIn = true,
	status = STATUS_REPORT,
} = {}) {
	const observer = {
		dispatch: () => {},
		mutation: [1, 1],
	};

	return render(
		<FlagsModal
			captchaURI={captchaURI}
			companyName={companyName}
			handleClose={handleClose}
			handleInputChange={handleInputChange}
			handleSubmit={handleSubmit}
			isSending={isSending}
			observer={observer}
			pathTermsOfUse={pathTermsOfUse}
			reasons={reasons}
			selectedReason={selectedReason}
			signedIn={signedIn}
			status={status}
		/>,
		{
			baseElement: document.body,
		}
	);
}

describe('FlagsModal', () => {
	it('renders', async () => {
		const {findByRole, findByText} = _renderFlagsModalComponent();

		await findByText('report-inappropriate-content');
		await findByRole('form');
	});

	it('renders as guess and render email field', async () => {
		const {findByLabelText} = _renderFlagsModalComponent({
			signedIn: false,
		});

		await findByLabelText('email', {exact: false});
	});

	it('renders error', async () => {
		const {findByText} = _renderFlagsModalComponent({status: STATUS_ERROR});

		await findByText('an-error-occurred', {exact: false});
	});

	it('renders login', async () => {
		const {findByText} = _renderFlagsModalComponent({status: STATUS_LOGIN});

		await findByText('please-sign-in', {exact: false});
	});

	it('renders success', async () => {
		const {findByText} = _renderFlagsModalComponent({
			status: STATUS_SUCCESS,
		});

		await findByText('thank-you', {exact: false});
	});
});
