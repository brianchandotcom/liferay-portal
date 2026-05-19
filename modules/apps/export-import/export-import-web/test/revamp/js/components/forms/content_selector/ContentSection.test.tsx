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

const [firstHandler, secondHandler, thirdHandler] =
	designSection.portletDataHandlers;

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
	it('hides the handlers by default', () => {
		renderContentSection();

		expect(screen.queryByText(firstHandler.label)).not.toBeInTheDocument();
	});

	it('shows the handlers when the section toggle is clicked', async () => {
		const {user} = renderContentSection();

		await user.click(
			screen.getByRole('button', {name: designSection.label})
		);

		expect(screen.getByText(firstHandler.label)).toBeInTheDocument();
	});

	it('hides the handlers again when the toggle is clicked twice', async () => {
		const {user} = renderContentSection();

		const toggle = screen.getByRole('button', {name: designSection.label});

		await user.click(toggle);
		await user.click(toggle);

		expect(screen.queryByText(firstHandler.label)).not.toBeInTheDocument();
	});

	it('summarizes the selected handlers under the title', () => {
		renderContentSection({
			[firstHandler.name]: true,
			[thirdHandler.name]: true,
		});

		expect(
			screen.getByText(`${firstHandler.label}, ${thirdHandler.label}`)
		).toBeInTheDocument();
	});

	it('stays open when a handler in the body is clicked', async () => {
		const {user} = renderContentSection();

		await user.click(
			screen.getByRole('button', {name: designSection.label})
		);

		await user.click(screen.getByText(firstHandler.label));

		expect(screen.getByText(secondHandler.label)).toBeInTheDocument();
	});

	it('stays closed when the section checkbox is clicked', async () => {
		const {user} = renderContentSection();

		await user.click(
			screen.getByRole('checkbox', {name: designSection.label})
		);

		expect(screen.queryByText(firstHandler.label)).not.toBeInTheDocument();
	});
});
