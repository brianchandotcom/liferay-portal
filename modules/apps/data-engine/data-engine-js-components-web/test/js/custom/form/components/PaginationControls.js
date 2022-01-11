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
import {render} from '@testing-library/react';
import React from 'react';

import {ConfigProvider} from '../../../../../src/main/resources/META-INF/resources/js/core/hooks/useConfig.es';
import {PaginationControls} from '../../../../../src/main/resources/META-INF/resources/js/custom/form/components/PaginationControls.es';

const INITIAL_CONFIG = {
	cancelLabel: 'Cancel',
	redirectURL: null,
	showCancelButton: false,
	showPartialResultsToRespondents: true,
	showSubmitButton: true,
	submitLabel: 'Submit',
};

const WithProvider = ({children, config}) => (
	<ConfigProvider initialConfig={config}>{children}</ConfigProvider>
);

describe('Pagination Controls', () => {
	it('shows see partial results button if showPartialResultsToRespondents settings is enabled', () => {
		const {queryByRole} = render(
			<WithProvider config={INITIAL_CONFIG}>
				<PaginationControls />
			</WithProvider>
		);

		expect(
			queryByRole('button', {name: /see-partial-results/i})
		).toBeInTheDocument();
	});

	it('hides see partial results button if showPartialResultsToRespondents settings is disabled', () => {
		const {queryByRole} = render(
			<WithProvider
				config={{
					...INITIAL_CONFIG,
					showPartialResultsToRespondents: false,
				}}
			>
				<PaginationControls />
			</WithProvider>
		);

		expect(
			queryByRole('button', {name: /see-partial-results/i})
		).not.toBeInTheDocument();
	});
});
