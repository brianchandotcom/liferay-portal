/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import '@testing-library/jest-dom';
import {render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';

import {
	Wizard,
	WizardStep,
} from '../../../../src/main/resources/META-INF/resources/revamp/js/components/Wizard';

const Step = ({children}: {children: React.ReactNode}) => <div>{children}</div>;

const renderWizard = (backURL = '/back') => {
	const user = userEvent.setup();

	render(
		<Wizard backURL={backURL} onSubmit={() => {}} onSubmitLabel="Export">
			<WizardStep description="This is step 1" title="Step 1">
				<Step>
					<h1>Step 1 Content</h1>
				</Step>
			</WizardStep>

			<WizardStep description="This is step 2" title="Step 2">
				<Step>
					<h1>Step 2 Content</h1>
				</Step>
			</WizardStep>
		</Wizard>
	);

	return {user};
};

describe('Wizard', () => {
	it('renders the first step of the Wizard', () => {
		renderWizard();

		expect(screen.getByText('Step 1 Content')).toBeInTheDocument();
		expect(screen.queryByText('Step 2 Content')).not.toBeInTheDocument();
	});

	it('navigates through the steps with "Continue" and "Previous" buttons', async () => {
		const {user} = renderWizard();

		expect(screen.getByText('Step 1 Content')).toBeInTheDocument();

		await user.click(screen.getByRole('button', {name: 'continue'}));

		expect(screen.queryByText('Step 1 Content')).not.toBeInTheDocument();
		expect(screen.getByText('Step 2 Content')).toBeInTheDocument();

		await user.click(screen.getByRole('button', {name: /previous/i}));

		expect(screen.getByText('Step 1 Content')).toBeInTheDocument();
		expect(screen.queryByText('Step 2 Content')).not.toBeInTheDocument();
	});

	it('checks that the "Cancel" button has the correct link', () => {
		const backURL = '/initial-page';

		renderWizard(backURL);

		const cancelButton = screen.getByRole('link', {name: 'cancel'});

		expect(cancelButton).toBeInTheDocument();
		expect(cancelButton).toHaveAttribute('href', backURL);
	});
});
