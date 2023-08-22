/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {ImportOptionsModal} from '@liferay/layout-js-components-web';
import {render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

const renderComponent = (onImport = () => null, onOpenChange = () => null) => {
	render(
		<ImportOptionsModal
			observer={{
				dispatch: () => {},
				mutation: [true, true],
			}}
			onImport={onImport}
			onOpenChange={onOpenChange}
		/>
	);
};

describe('ImportOptionsModal', () => {
	it('renders text informing the user that some items already exist', () => {
		renderComponent();

		expect(
			screen.getByText(
				'one-or-more-items-from-the-zip-already-exist-in-this-location.-what-action-do-you-want-to-take?'
			)
		).toBeInTheDocument();
	});

	it('renders a radio button with 3 options', () => {
		renderComponent();

		expect(screen.getAllByRole('radio').length).toBe(3);
		expect(
			screen.getByRole('radio', {name: /do-not-import-existing-items/i})
		).toBeInTheDocument();
		expect(
			screen.getByRole('radio', {name: /overwrite-existing-items/i})
		).toBeInTheDocument();
		expect(
			screen.getByRole('radio', {name: /keep-both/i})
		).toBeInTheDocument();
	});

	it('renders cancel and import buttons', () => {
		const onImport = jest.fn();
		const onOpenChange = jest.fn();

		renderComponent(onImport, onOpenChange);

		const cancelButton = screen.getByRole('button', {name: /cancel/i});
		const importButton = screen.getByRole('button', {name: /import/i});

		expect(cancelButton).toBeInTheDocument();
		expect(importButton).toBeInTheDocument();

		userEvent.click(cancelButton);

		expect(onOpenChange).toHaveBeenCalled();

		userEvent.click(importButton);

		expect(onImport).toHaveBeenCalled();
	});
});
