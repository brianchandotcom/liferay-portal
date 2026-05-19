/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import ContentSection, {
	SectionSelection,
} from '../../../../../../src/main/resources/META-INF/resources/revamp/js/components/forms/content_selector/ContentSection';
import {mockPortletDataHandlerSections} from '../../../mocks/mockPortletDataHandlerSections';

const designSection = mockPortletDataHandlerSections[0];

const renderContentSection = (value?: SectionSelection) => {
	const user = userEvent.setup();
	const onChange = jest.fn();

	render(
		<ContentSection
			onChange={onChange}
			section={designSection}
			value={value}
		/>
	);

	return {onChange, user};
};

describe('ContentSection', () => {
	it('renders the section header collapsed by default', () => {
		renderContentSection();

		const toggle = screen.getByRole('button', {name: designSection.label});

		expect(toggle).toHaveAttribute('aria-expanded', 'false');

		designSection.portletDataHandlers.forEach((handler) => {
			expect(screen.queryByText(handler.label)).not.toBeInTheDocument();
		});
	});

	it('expands the section body when the header is clicked', async () => {
		const {user} = renderContentSection();

		const toggle = screen.getByRole('button', {name: designSection.label});

		await user.click(toggle);

		expect(toggle).toHaveAttribute('aria-expanded', 'true');

		designSection.portletDataHandlers.forEach((handler) => {
			expect(screen.getByText(handler.label)).toBeInTheDocument();
		});
	});

	it('collapses the section body again on a second click', async () => {
		const {user} = renderContentSection();

		const toggle = screen.getByRole('button', {name: designSection.label});

		await user.click(toggle);
		await user.click(toggle);

		expect(toggle).toHaveAttribute('aria-expanded', 'false');

		designSection.portletDataHandlers.forEach((handler) => {
			expect(screen.queryByText(handler.label)).not.toBeInTheDocument();
		});
	});

	it('renders the selected handler labels as a comma-separated summary under the title', () => {
		const [first, , third] = designSection.portletDataHandlers;

		renderContentSection({
			[first.name]: true,
			[third.name]: true,
		});

		expect(
			screen.getByText(`${first.label}, ${third.label}`)
		).toBeInTheDocument();
	});

	it('expands the section when the card is clicked while collapsed', async () => {
		const {user} = renderContentSection();

		const toggle = screen.getByRole('button', {name: designSection.label});

		await user.click(screen.getByText(designSection.label));

		expect(toggle).toHaveAttribute('aria-expanded', 'true');
	});

	it('collapses the section when the card is clicked while expanded', async () => {
		const {user} = renderContentSection();

		const toggle = screen.getByRole('button', {name: designSection.label});

		await user.click(toggle);

		expect(toggle).toHaveAttribute('aria-expanded', 'true');

		await user.click(screen.getByText(designSection.label));

		expect(toggle).toHaveAttribute('aria-expanded', 'false');
	});

	it('does not collapse the section when a handler in the body is clicked', async () => {
		const {user} = renderContentSection();

		const toggle = screen.getByRole('button', {name: designSection.label});

		await user.click(toggle);

		await user.click(
			screen.getByText(designSection.portletDataHandlers[0].label)
		);

		expect(toggle).toHaveAttribute('aria-expanded', 'true');
	});

	it('toggling Select All does not change the expanded state', async () => {
		const {user} = renderContentSection();

		const toggle = screen.getByRole('button', {name: designSection.label});
		const selectAll = screen.getAllByRole('checkbox')[0];

		await user.click(selectAll);

		expect(toggle).toHaveAttribute('aria-expanded', 'false');
	});
});
