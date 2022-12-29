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

import ConnectStep from './ConnectStep';

describe('Connect Step', () => {
	it('render ConnectStep without crashing', () => {
		const {container, getByText} = render(
			<ConnectStep onCancel={() => {}} onChangeStep={() => {}} />
		);

		const connectStepDescription = getByText(
			'use-the-token-genereted-in-your-analytics-cloud-to-connect-this-workspace'
		);

		const connectStepTitleLang = getByText('connect-analytics-cloud');

		expect(connectStepDescription).toBeInTheDocument();

		expect(container.firstChild).toHaveClass('sheet');

		expect(connectStepTitleLang).toBeInTheDocument();
	});

	it('render card of conection with AC without crashing', () => {
		const {getByPlaceholderText, getByRole, getByText} = render(
			<ConnectStep onCancel={() => {}} onChangeStep={() => {}} />
		);

		const wizardCardTitle = getByText('connect-analytics-cloud');

		const wizardCardDescription = getByText(
			'use-the-token-genereted-in-your-analytics-cloud-to-connect-this-workspace'
		);

		const wizardInputTitle = getByText('analytics-cloud-token');

		const wizardInputPlaceholder = getByPlaceholderText('paste-token-here');

		const wizardInputHelp = getByText('analytics-cloud-token-help');

		const buttonCardConnect = getByRole('button', {name: /connect/i});

		expect(wizardCardTitle).toBeInTheDocument();

		expect(wizardCardDescription).toBeInTheDocument();

		expect(wizardInputTitle).toBeInTheDocument();

		expect(wizardInputPlaceholder).toBeInTheDocument();

		expect(wizardInputHelp).toBeInTheDocument();

		expect(buttonCardConnect).toBeInTheDocument();
	});
});
