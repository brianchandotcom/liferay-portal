/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import {Formik} from 'formik';
import React from 'react';

import SettingsStep from '../../../../../../src/main/resources/META-INF/resources/revamp/js/pages/import/steps/SettingsStep';

const defaultInitialValues = {
	authorshipStrategy: 'useOriginalAuthor',
	updateDataStrategy: 'mirror',
};

const renderSettingsStep = (
	overrides: Partial<typeof defaultInitialValues> = {}
) =>
	render(
		<Formik
			initialValues={{...defaultInitialValues, ...overrides}}
			onSubmit={jest.fn()}
		>
			<SettingsStep />
		</Formik>
	);

describe('SettingsStep', () => {
	it('renders the authorship and update-data sections', () => {
		renderSettingsStep();

		expect(
			screen.getByText('authorship-of-the-content')
		).toBeInTheDocument();
		expect(screen.getByText('update-data')).toBeInTheDocument();
	});

	it('selects use-the-original-author authorship by default', () => {
		renderSettingsStep();

		expect(screen.getByLabelText('use-the-original-author')).toBeChecked();
		expect(
			screen.getByLabelText('use-the-current-user-as-author')
		).not.toBeChecked();
	});

	it('switches authorship to use-the-current-user-as-author when selected', async () => {
		const user = userEvent.setup();

		renderSettingsStep();

		const radio = screen.getByLabelText('use-the-current-user-as-author');

		await user.click(radio);

		expect(radio).toBeChecked();
		expect(
			screen.getByLabelText('use-the-original-author')
		).not.toBeChecked();
	});

	it('selects the mirror update-data strategy by default', () => {
		renderSettingsStep();

		expect(screen.getByLabelText('mirror')).toBeChecked();
		expect(
			screen.getByLabelText('mirror-with-overwriting')
		).not.toBeChecked();
		expect(screen.getByLabelText('copy-as-new')).not.toBeChecked();
	});

	it('switches the update-data strategy when a different option is selected', async () => {
		const user = userEvent.setup();

		renderSettingsStep();

		const radio = screen.getByLabelText('copy-as-new');

		await user.click(radio);

		expect(radio).toBeChecked();
		expect(screen.getByLabelText('mirror')).not.toBeChecked();
	});
});
