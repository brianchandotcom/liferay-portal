/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {act, render, screen} from '@testing-library/react';
import React from 'react';

import InstallFragmentModal from '../../../src/main/resources/META-INF/resources/js/components/modals/InstallFragmentModal';

const renderComponent = ({name} = {}) =>
	render(<InstallFragmentModal name={name} />);

describe('InstallFragmentModal', () => {
	beforeAll(() => {
		jest.useFakeTimers();
	});

	it('renders correctly', () => {
		renderComponent();

		act(() => {
			jest.runAllTimers();
		});

		expect(screen.getByText('installing-x')).toBeInTheDocument();

		expect(
			screen.getByText(
				'the-installation-process-is-ongoing-and-may-take-a-few-minutes-closing-the-window-will-not-cancel-the-process'
			)
		).toBeInTheDocument();
	});
});
