/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import DraftLayoutSelector from '../../src/main/resources/META-INF/resources/js/components/DraftLayoutSelector';

const MAIN_DRAFT = {
	active: true,
	disabled: false,
	label: 'Main',
	launchSetId: 0,
};

const LAUNCH_SET_DRAFT = {
	active: false,
	disabled: false,
	href: '/web/site/launch-a',
	label: 'Launch A',
	launchSetId: 10,
};

const RESTRICTED_DRAFT = {
	active: false,
	disabled: true,
	label: 'restricted-launch',
	launchSetId: 20,
};

describe('DraftLayoutSelector', () => {
	it('renders nothing when there are no draft layouts', () => {
		const {container} = render(<DraftLayoutSelector draftLayouts={[]} />);

		expect(container).toBeEmptyDOMElement();
	});

	it('renders the active draft label on the trigger', () => {
		render(
			<DraftLayoutSelector
				draftLayouts={[MAIN_DRAFT, LAUNCH_SET_DRAFT]}
			/>
		);

		expect(
			screen.getByLabelText('select-draft', {selector: 'button'})
		).toHaveTextContent('Main');
	});

	it('does not render a divider when there is a single draft', async () => {
		render(<DraftLayoutSelector draftLayouts={[MAIN_DRAFT]} />);

		await userEvent.click(screen.getByLabelText('select-draft'));

		expect(screen.queryByRole('separator')).not.toBeInTheDocument();
	});

	it('renders a divider after the first item when there is more than one', async () => {
		render(
			<DraftLayoutSelector
				draftLayouts={[MAIN_DRAFT, LAUNCH_SET_DRAFT]}
			/>
		);

		await userEvent.click(screen.getByLabelText('select-draft'));

		expect(screen.getByRole('separator')).toBeInTheDocument();
	});

	it('marks the active item as active', async () => {
		render(
			<DraftLayoutSelector
				draftLayouts={[MAIN_DRAFT, LAUNCH_SET_DRAFT]}
			/>
		);

		await userEvent.click(screen.getByLabelText('select-draft'));

		const items = screen.getAllByRole('menuitem');

		expect(items[0]).toHaveAttribute('aria-selected', 'true');
		expect(items[1]).not.toHaveAttribute('aria-selected', 'true');
	});

	it('marks a restricted item as disabled with no href', async () => {
		render(
			<DraftLayoutSelector
				draftLayouts={[MAIN_DRAFT, RESTRICTED_DRAFT]}
			/>
		);

		await userEvent.click(screen.getByLabelText('select-draft'));

		const restrictedItem = screen.getByText('restricted-launch');

		const restrictedMenuItem = restrictedItem.closest('[role="menuitem"]');

		expect(restrictedMenuItem).toHaveAttribute('aria-disabled', 'true');
		expect(restrictedMenuItem).not.toHaveAttribute('href');
	});

	it('exposes the href on a non-active non-disabled item', async () => {
		render(
			<DraftLayoutSelector
				draftLayouts={[MAIN_DRAFT, LAUNCH_SET_DRAFT]}
			/>
		);

		await userEvent.click(screen.getByLabelText('select-draft'));

		const launchItem = screen.getByText('Launch A').closest('a');

		expect(launchItem).toHaveAttribute('href', '/web/site/launch-a');
	});
});
